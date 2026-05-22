package controller;

import DAO.EventoDAO;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.stage.Stage;

import util.ManejoSesion;
import javafx.fxml.FXML;

import javafx.scene.control.ListView;

import model.Evento;

public class DashboardPromotorController {


    @FXML
    private ListView<Evento> listTodos;



    @FXML
    private ListView<Evento> listActivos;


    private EventoDAO eventoDAO;



    public void initialize() {

        eventoDAO =
                new EventoDAO();

        cargarEventos();
    }



    private void cargarEventos() {

        int idPromotor =

                ManejoSesion
                        .getUsuarioActual()
                        .getId();

        listTodos
                .getItems()
                .addAll(

                        eventoDAO
                                .getEventosPromotor(
                                        idPromotor
                                )
                );

        listActivos
                .getItems()
                .addAll(

                        eventoDAO
                                .getEventosActivosPromotor(
                                        idPromotor
                                )
                );
    }
    @FXML
    public void handleLogout() {

        ManejoSesion.logout();

        try {

            FXMLLoader loader =
                    new FXMLLoader(

                            getClass()
                                    .getResource(
                                            "/vista/inicioSesion.fxml"
                                    )
                    );

            Scene scene =
                    new Scene(loader.load());

            Stage stage =
                    new Stage();

            stage.setScene(scene);

            stage.setTitle(
                    "Inicio de sesión"
            );

            stage.show();


            listTodos
                    .getScene()
                    .getWindow()
                    .hide();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    public void handleCrearEvento() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(

                            getClass()
                                    .getResource(
                                            "/vista/crearEvento.fxml"
                                    )
                    );

            Scene scene =
                    new Scene(loader.load());

            Stage stage =
                    new Stage();

            stage.setScene(scene);

            stage.setTitle(
                    "Crear Evento"
            );

            stage.show();

            listTodos
                    .getScene()
                    .getWindow()
                    .hide();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}