package controller;

import DAO.EventoDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.EntidadPromotora;
import model.EstadoEvento;
import model.Evento;
import javafx.scene.control.Button;
import java.util.List;

public class DashboardPromotorController {
    @FXML
    ComboBox<EstadoEvento> comboEstado;
    @FXML
    private Button btnCrearEvento;

    @FXML
    private Button btnEditarEvento;

    @FXML
    private Button btnEliminarEvento;

    @FXML
    private ListView<Evento> listEventos;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private DatePicker dateFecha;

    private EventoDAO eventoDAO;

    private EntidadPromotora promotorActual;

    @FXML
    public void initialize() {

        eventoDAO =
                new EventoDAO();

        comboEstado.getItems().addAll(

                EstadoEvento.ACTIVO,
                EstadoEvento.INACTIVO
        );

        btnEditarEvento.setDisable(true);

        btnEliminarEvento.setDisable(true);

        btnCrearEvento.setDisable(true);

        listEventos.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> {

                    boolean haySeleccion =
                            newValue != null;

                    btnCrearEvento.setDisable(
                            haySeleccion
                    );

                    btnEditarEvento.setDisable(
                            !haySeleccion
                    );

                    btnEliminarEvento.setDisable(
                            !haySeleccion
                    );

                    if(haySeleccion) {

                        cargarEventoEnFormulario(
                                newValue
                        );

                    } else {

                        limpiarFormulario();
                    }
                });

        txtNombre.textProperty().addListener(
                (obs, oldVal, newVal) -> validarFormulario()
        );

        txtDescripcion.textProperty().addListener(
                (obs, oldVal, newVal) -> validarFormulario()
        );

        dateFecha.valueProperty().addListener(
                (obs, oldVal, newVal) -> validarFormulario()
        );

        comboEstado.valueProperty().addListener(
                (obs, oldVal, newVal) -> validarFormulario()
        );
    }

    /**
     * Método que valida los campos de creación de eventos, comprobando que tengan contenidos o no
     * ,comprobando que si hay un evento seleccionado no se pueda usar la funcion de crear
     */
    private void validarFormulario() {

        boolean camposCompletos =

                !txtNombre.getText().isEmpty()

                        && !txtDescripcion.getText().isEmpty()

                        && dateFecha.getValue() != null

                        && comboEstado.getValue() != null;

        boolean haySeleccion =

                listEventos.getSelectionModel()
                        .getSelectedItem() != null;

        btnCrearEvento.setDisable(

                !camposCompletos || haySeleccion
        );
    }

    /**
     * Método que carga los datos de la cuenta del promotor que ha iniciado sesión
     * @param promotor promotor que ha iniciado sesión
     */
    public void setPromotorActual(
            EntidadPromotora promotor
    ) {

        this.promotorActual =
                promotor;

        cargarEventos();
    }

    /**
     * Método que carga los eventos creados por el promotor
     */
    void cargarEventos() {
        if(promotorActual == null) {

            return;
        }
        List<Evento> eventos =
                eventoDAO.getEventosPromotor(
                        promotorActual.getId()
                );

        listEventos.setItems(

                FXCollections.observableArrayList(
                        eventos
                )
        );
    }

    /**
     * Método que maneja la creación de eventos
     */
    @FXML
    private void handleCrearEvento() {

        if(txtNombre.getText().isBlank()
                || txtDescripcion.getText().isBlank()
                || dateFecha.getValue() == null) {

            mostrarMensaje(
                    "Completa todos los campos"
            );

            return;
        }

        Evento evento =
                new Evento();

        evento.setNombre(
                txtNombre.getText()
        );

        evento.setDescripcion(
                txtDescripcion.getText()
        );

        evento.setEstado(
                comboEstado.getValue()
        );

        evento.setFechaEvento(
                dateFecha.getValue()
        );

        if(promotorActual == null) {

            mostrarMensaje(
                    "No hay promotor logueado"
            );

            return;
        }

        boolean ok =
                eventoDAO.insertarEvento(

                        evento,

                        promotorActual.getId()
                );

        if(ok) {

            mostrarMensaje(
                    "Evento creado correctamente"
            );

            limpiarFormulario();

            cargarEventos();

        } else {

            mostrarMensaje(
                    "No se pudo crear el evento"
            );
        }
    }

    /**
     * Método que se encarga de la edición de eventos
     */
    @FXML
    private void handleEditarEvento() {

        Evento seleccionado =
                listEventos.getSelectionModel()
                        .getSelectedItem();

        if(seleccionado == null) {

            mostrarMensaje(
                    "Selecciona un evento"
            );

            return;
        }

        seleccionado.setNombre(
                txtNombre.getText()
        );

        seleccionado.setDescripcion(
                txtDescripcion.getText()
        );

        seleccionado.setFechaEvento(
                dateFecha.getValue()
        );

        seleccionado.setEstado(
                comboEstado.getValue()
        );

        boolean ok =
                eventoDAO.actualizarEvento(
                        seleccionado,
                        promotorActual.getId()
                );

        if(ok) {

            mostrarMensaje(
                    "Evento editado correctamente"
            );

            cargarEventos();
            listEventos.getSelectionModel()
                    .clearSelection();
            validarFormulario();
        }
    }

    /**
     * Método que se encarga de borrar eventos
     */
    @FXML
    private void handleBorrarEvento() {

        Evento seleccionado =
                listEventos.getSelectionModel()
                        .getSelectedItem();

        if(seleccionado == null) {

            mostrarMensaje(
                    "Selecciona un evento"
            );

            return;
        }

        boolean confirmado =

                new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "¿Seguro que quieres borrar el evento?"
                )

                        .showAndWait()

                        .filter(
                                response ->
                                        response == ButtonType.OK
                        )

                        .isPresent();

        if(!confirmado) {

            return;
        }

        boolean ok =
                eventoDAO.borrarEvento(

                        seleccionado.getIdEvento()
                );

        if(ok) {

            mostrarMensaje(
                    "Evento borrado correctamente"
            );

            cargarEventos();
            listEventos.getSelectionModel()
                    .clearSelection();
            limpiarFormulario();

        } else {

            mostrarMensaje(
                    "No se pudo borrar el evento"
            );
        }
    }

    /**
     * Método que maneja el cierre de sesión
     */
    @FXML
    private void handleLogout() {

        Stage stage =
                (Stage) listEventos.getScene()
                        .getWindow();

        stage.close();
    }

    /**
     * Método que se encarga de limpiar el formulario una vez que se hace una acción (crear, editar o borrar)
     */
    private void limpiarFormulario() {

        txtNombre.clear();

        txtDescripcion.clear();

        dateFecha.setValue(null);

        comboEstado.setValue(null);
    }

    /**
     * Método que muestra mensaje en caso de error
     * @param mensaje Mensaje a enseñar
     */
    private void mostrarMensaje(
            String mensaje
    ) {

        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();
    }

    /**
     * Método que carga los datos de un evento en el formulario al seleccionarlo
     * @param evento Evento seleccionado de la lista de eventos
     */
    private void cargarEventoEnFormulario(
            Evento evento
    ) {

        if(evento == null) {

            return;
        }

        txtNombre.setText(
                evento.getNombre()
        );

        txtDescripcion.setText(
                evento.getDescripcion()
        );

        dateFecha.setValue(
                evento.getFechaEvento()
        );

        comboEstado.setValue(
                evento.getEstado()
        );
    }
}