package controller;

import DAO.ParticipacionDAO;
import util.ManejoSesion;

import model.Usuario;
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

    public void initialize() {

        participacionDAO =
                new ParticipacionDAO();

        cargarHistorial();
    }

    private void cargarHistorial() {

        Usuario usuario =
                ManejoSesion.getUsuarioActual();

        listHistorial
                .getItems()
                .addAll(

                        participacionDAO
                                .getHistorialCompleto(

                                        usuario.getId()
                                )
                );
    }

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