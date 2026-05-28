package controller;

import DAO.EventoDAO;
import DAO.ParticipacionDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import model.Evento;
import model.Usuario;
import java.util.List;
import javafx.stage.Stage;
import util.ManejoSesion;

public class DetalleEventoController {

    @FXML
    private Button btnParticipar;

    @FXML
    private Button btnDesapuntarse;

    @FXML
    private Label lblNumeroParticipantes;

    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblFecha;

    @FXML
    private Label lblPromotor;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private ListView<String> listParticipantes;

    private Evento evento;

    private EventoDAO eventoDAO;

    public void initialize() {

        eventoDAO =
                new EventoDAO();
    }

    /**
     * Método que recibe un evento para mostrar en la página
     * @param evento Evento a enseñar
     */

    public void setEvento(
            Evento evento
    ) {

        this.evento =
                evento;

        cargarDatos();
    }

    /**
     * Método que carga los datos de un evento
     */
    private void cargarDatos() {

        if (evento == null) {

            return;
        }

        lblTitulo.setText(
                evento.getNombre()
        );

        lblFecha.setText(
                "Fecha: " + evento.getFechaEvento()
        );

        txtDescripcion.setText(
                evento.getDescripcion()
        );

        if (evento.getEntidad() != null) {

            lblPromotor.setText(
                    "Promotor: "
                            + evento.getEntidad().getNombre()
            );

        } else {

            lblPromotor.setText(
                    "Promotor no disponible"
            );
        }

        cargarParticipantes();
        actualizarBotones();
    }

    /**
     * Método que carga los participantes apuntados a un evento
     */

    private void cargarParticipantes() {

        List<Usuario> participantes =
                eventoDAO.getParticipantesEvento(
                        evento.getIdEvento()
                );

        listParticipantes.getItems().clear();

        List<String> nombres =
                participantes.stream()
                        .filter(usuario -> usuario != null)
                        .map(Usuario::getNombre)
                        .toList();

        listParticipantes.getItems().setAll(
                nombres
        );

        lblNumeroParticipantes.setText(
                participantes.size() + " participantes"
        );
    }

    /**
     * Método que maneja la capacidad de apuntarse a un evento
     */

    @FXML
    public void handleParticipar() {

        int idUsuario =
                ManejoSesion
                        .getUsuarioActual()
                        .getId();

        if(
                eventoDAO.participa(
                        idUsuario,
                        evento.getIdEvento()
                )
        ) {

            mostrarError(
                    "Ya participas"
            );

            return;
        }

        boolean ok =
                eventoDAO.apuntarseEvento(

                        idUsuario,

                        evento.getIdEvento()
                );

        if(ok) {

            mostrarInfo(
                    "Te has apuntado"
            );

            cargarDatos();
            cargarParticipantes();
            actualizarDashboardParticipante();
        }
    }

    /**
     * Método que maneja la capacidad de desapuntarse de un evento
     */

    @FXML
    public void handleDesapuntarse() {

        int idUsuario =
                ManejoSesion
                        .getUsuarioActual()
                        .getId();

        boolean ok =
                eventoDAO.desapuntarseEvento(

                        idUsuario,

                        evento.getIdEvento()
                );

        if(ok) {

            mostrarInfo(
                    "Te has desapuntado"
            );

            cargarDatos();
            cargarParticipantes();
            actualizarBotones();
            actualizarDashboardParticipante();
        }
    }

    /**
     * Método que actualiza el Dashboard Participante en caso de que se produzcan cambios como apuntarse o desapuntarse de un evento
     */
    private void actualizarDashboardParticipante() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/vista/dashboardParticipante.fxml"
                            )
                    );

        } catch(Exception e) {

            e.printStackTrace();
        }
    }


    /**
     * Método que cierra la página
     */

    @FXML
    public void handleCerrar() {

        Stage stage =
                (Stage) lblTitulo
                        .getScene()
                        .getWindow();

        stage.close();
    }

    /**
     * Método que muestra un mensaje en caso de que sea necesario
     * @param mensaje Mensaje a enseñar
     */

    private void mostrarInfo(
            String mensaje
    ) {

        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alert.setHeaderText(
                "Información"
        );

        alert.setContentText(
                mensaje
        );

        alert.showAndWait();
    }

    /**
     * Mensaje que salta cuando se produce un error
     * @param mensaje Mensaje de error
     */
    private void mostrarError(
            String mensaje
    ) {

        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alert.setHeaderText(
                "Error"
        );

        alert.setContentText(
                mensaje
        );

        alert.showAndWait();
    }

    /**
     * Método que se encarga de que los botones sean reactivos en base a las funciones posibles
     */
    private void actualizarBotones() {

        int idUsuario =
                ManejoSesion
                        .getUsuarioActual()
                        .getId();

        boolean participa =
                eventoDAO.participa(
                        idUsuario,
                        evento.getIdEvento()
                );

        btnParticipar.setDisable(
                participa
        );

        btnDesapuntarse.setDisable(
                !participa
        );
    }

}