package controller;

import DAO.ParticipacionDAO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.control.ListView;

import javafx.stage.Stage;

import model.Evento;

public class HistorialController {

    @FXML
    private ListView<Evento> listHistorial;

    private ParticipacionDAO participacionDAO;

    /*
     * Usuario temporal
     */

    private int idParticipante = 1;

    /*
     * Inicialización automática
     */

    public void initialize() {

        participacionDAO =
                new ParticipacionDAO();

        cargarHistorial();
    }

    /*
     * Carga historial completo
     */

    private void cargarHistorial() {

        listHistorial
                .getItems()
                .addAll(

                        participacionDAO
                                .getHistorialCompleto(
                                        idParticipante
                                )
                );
    }

    /*
     * Volver dashboard
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

            listHistorial
                    .getScene()
                    .getWindow()
                    .hide();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}