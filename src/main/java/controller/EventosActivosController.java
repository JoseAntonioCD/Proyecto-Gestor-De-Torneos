package controller;

import DAO.EventoDAO;
import DAO.ParticipacionDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Evento;
import model.Usuario;
import util.ManejoSesion;

import java.util.ArrayList;
import java.util.List;

public class EventosActivosController {

    private List<Evento> eventosActivos;

    private List<Evento> eventosInactivos;

    @FXML
    private ListView<Evento> listEventosInactivos;

    @FXML
    private Button btnParticipar;

    @FXML
    private Button btnDesapuntarse;

    @FXML
    private TextField txtBuscar;

    @FXML
    private ListView<Evento> listEventos;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblFecha;

    @FXML
    private Label lblPromotor;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private ListView<Usuario> listParticipantes;

    private EventoDAO eventoDAO;

    private List<Evento> eventos = new ArrayList<>();

    public EventosActivosController() {}


    @FXML
    public void initialize() {

        eventoDAO =
                new EventoDAO();

        cargarEventos();

        listEventos.setCellFactory(param -> new ListCell<>() {

            @Override
            protected void updateItem(
                    Evento evento,
                    boolean empty
            ) {

                super.updateItem(
                        evento,
                        empty
                );

                if(empty || evento == null) {

                    setText(null);

                } else {

                    setText(
                            evento.getNombre()
                    );
                }
            }
        });

        listEventosInactivos.setCellFactory(param -> new ListCell<>() {

            @Override
            protected void updateItem(
                    Evento evento,
                    boolean empty
            ) {

                super.updateItem(
                        evento,
                        empty
                );

                if(empty || evento == null) {

                    setText(null);

                } else {

                    setText(
                            evento.getNombre()
                    );
                }
            }
        });

        actualizarBotones();

        listEventos.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, old, nuevoEvento) -> {

                    if(nuevoEvento != null) {

                        mostrarEvento(
                                nuevoEvento
                        );
                    }

                    actualizarBotones();
                });

        listEventosInactivos.setItems(

                FXCollections.observableArrayList(

                        eventoDAO.getEventosInactivos()
                )
        );

        txtBuscar.textProperty().addListener(
                (obs, oldValue, newValue) -> {

                    filtrarEventos(
                            newValue
                    );
                }
        );
    }

    /**
     * Método que muestra los eventos con sus detalles en los espacios respectivos
     * @param evento Evento a enseñar
     */
    private void mostrarEvento(
            Evento evento
    ) {

        lblNombre.setText(
                evento.getNombre()
        );

        lblFecha.setText(
                "Fecha: "
                        + evento.getFechaEvento()
        );

        txtDescripcion.setText(
                evento.getDescripcion()
        );

        if(evento.getEntidad() != null) {

            lblPromotor.setText(
                    "Promotor: "
                            + evento.getEntidad().getNombre()
            );

        } else {

            lblPromotor.setText(
                    "Promotor no disponible"
            );
        }

        List<Usuario> participantes =
                eventoDAO.getParticipantesEvento(
                        evento.getIdEvento()
                );

        listParticipantes.getItems().clear();

        for(Usuario usuario : participantes) {

            listParticipantes.getItems().add(
                    usuario
            );
        }
    }

    /**
     * Método para filtrar eventos en base a lo que se busque en la barra de búsqueda
     * @param texto Texto sobre el que se basa la filtración de eventos
     */
    private void filtrarEventos(
            String texto
    ) {

        if(texto == null) {

            texto = "";
        }

        String filtro =
                texto.toLowerCase();

        List<Evento> activosFiltrados =

                eventosActivos.stream()

                        .filter(evento ->

                                evento.getNombre()
                                        .toLowerCase()
                                        .contains(filtro)
                        )

                        .toList();

        List<Evento> inactivosFiltrados =

                eventosInactivos.stream()

                        .filter(evento ->

                                evento.getNombre()
                                        .toLowerCase()
                                        .contains(filtro)
                        )

                        .toList();

        listEventos.getSelectionModel()
                .clearSelection();

        listEventosInactivos
                .getSelectionModel()
                .clearSelection();

        listEventos.setItems(

                FXCollections.observableArrayList(
                        activosFiltrados
                )
        );

        listEventosInactivos.setItems(

                FXCollections.observableArrayList(
                        inactivosFiltrados
                )
        );
    }

    /**
     * Método que llama al usuario actual
     * @param usuario Usuario actual
     */
    public void setUsuarioActual(
            Usuario usuario
    ) {

    }

    /**
     * Método que carga los eventos en sus listas respectivas, las Activas e Inactivas
     */
    private void cargarEventos() {

        eventosActivos =
                eventoDAO.getEventosActivos();

        eventosInactivos =
                eventoDAO.getEventosInactivos();

        System.out.println(
                "EVENTOS ACTIVOS: "
                        + eventosActivos.size()
        );

        System.out.println(
                "EVENTOS INACTIVOS: "
                        + eventosInactivos.size()
        );

        listEventos.setItems(

                FXCollections.observableArrayList(
                        eventosActivos
                )
        );

        listEventosInactivos.setItems(

                FXCollections.observableArrayList(
                        eventosInactivos
                )
        );
    }

    /**
     * Método que maneja las funciones de la barra de buscar
     */
    @FXML
    private void handleBuscar() {

        if(eventos == null) {

            return;
        }

        String texto =
                txtBuscar.getText()
                        .toLowerCase();

        List<Evento> filtrados =
                eventos.stream()

                        .filter(evento ->

                                evento.getNombre()
                                        .toLowerCase()
                                        .contains(texto)
                        )

                        .toList();

        listEventos.setItems(

                FXCollections.observableArrayList(
                        filtrados
                )
        );
    }

    /**
     * Método que maneja la función de apuntarse a eventos
     */
    @FXML
    private void handleParticipar() {

        Evento evento =
                listEventos.getSelectionModel()
                        .getSelectedItem();

        if(evento == null) {

            mostrarAlerta(
                    "Selecciona un evento"
            );

            return;
        }

        Usuario usuario =
                ManejoSesion.getUsuarioActual();

        boolean ok =
                ParticipacionDAO.participar(
                        usuario.getId(),
                        evento.getIdEvento()
                );

        if(ok) {

            mostrarAlerta(
                    "Te has apuntado correctamente"
            );

        } else {

            mostrarAlerta(
                    "Ya estás apuntado"
            );
        }
    }

    /**
     * Método que maneja el desapuntarse a un evento ya previamente apuntado
     */
    @FXML
    private void handleDesapuntarse() {

        Evento evento =
                listEventos.getSelectionModel()
                        .getSelectedItem();

        if(evento == null) {

            mostrarAlerta(
                    "Selecciona un evento"
            );

            return;
        }

        Usuario usuario =
                ManejoSesion.getUsuarioActual();

        boolean ok =
                ParticipacionDAO.desapuntarse(
                        usuario.getId(),
                        evento.getIdEvento()
                );

        if(ok) {

            mostrarAlerta(
                    "Te has desapuntado"
            );

        } else {

            mostrarAlerta(
                    "No estabas apuntado"
            );
        }
    }

    /**
     * Método que hace que cierra la página
     */
    @FXML
    private void handleVolver() {

        Stage stage =
                (Stage) listEventos.getScene()
                        .getWindow();

        stage.close();
    }

    /**
     * Método que muestra un mensaje de alerta en caso de que sea necesario
     * @param mensaje Mensaje de alerta
     */
    private void mostrarAlerta(
            String mensaje
    ) {

        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();
    }

    /**
     * Método que maneja que los botones sean responsivos en base a la situación
     */
    private void actualizarBotones() {

        Evento seleccionado =
                listEventos.getSelectionModel()
                        .getSelectedItem();

        if(seleccionado == null) {

            btnParticipar.setDisable(true);
            btnDesapuntarse.setDisable(true);

            return;
        }

        int idUsuario =
                ManejoSesion
                        .getUsuarioActual()
                        .getId();

        boolean participa =
                eventoDAO.participa(
                        idUsuario,
                        seleccionado.getIdEvento()
                );

        btnParticipar.setDisable(
                participa
        );

        btnDesapuntarse.setDisable(
                !participa
        );
    }
}