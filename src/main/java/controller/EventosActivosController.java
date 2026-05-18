package controller;

import DAO.EventoDAO;
import DAO.ParticipacionDAO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import javafx.stage.Stage;

import model.Evento;

public class EventosActivosController {

    @FXML
    private ListView<Evento> listEventos;

    private EventoDAO eventoDAO;

    private ParticipacionDAO participacionDAO;

    /*
     * Usuario temporal
     */

    private int idParticipante = 1;

    /*
     * Inicialización automática
     */

    public void initialize() {

        eventoDAO =
                new EventoDAO();

        participacionDAO =
                new ParticipacionDAO();

        cargarEventos();
    }

    /*
     * Carga eventos activos
     */

    private void cargarEventos() {

        listEventos
                .getItems()
                .addAll(

                        eventoDAO
                                .getEventosActivos()
                );
    }

    /*
     * Participar en evento
     */

    @FXML
    public void handleParticipar() {

        Evento seleccionado =
                listEventos
                        .getSelectionModel()
                        .getSelectedItem();

        if(seleccionado == null) {

            mostrarError(
                    "Selecciona un evento"
            );

            return;
        }

        boolean ok =
                participacionDAO
                        .participar(

                                idParticipante,

                                seleccionado.getIdEvento()
                        );

        if(ok) {

            mostrarInfo(
                    "Participación registrada"
            );
        }
    }

    /*
     * Volver al dashboard
     */

    @FXML
    public void handleVolver() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(

                            getClass()
                                    .getResource(
                                            "/vista/dashboardParticipante.fxml"
                                    )
                    );

            Scene scene =
                    new Scene(loader.load());

            Stage stage =
                    new Stage();

            stage.setScene(scene);

            stage.setTitle(
                    "Dashboard"
            );

            stage.show();

            listEventos
                    .getScene()
                    .getWindow()
                    .hide();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    /*
     * Alerts
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
}
