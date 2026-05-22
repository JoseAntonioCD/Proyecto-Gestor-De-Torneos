package controller;

import DAO.EventoDAO;
import DAO.ParticipacionDAO;
import util.ManejoSesion;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Evento;

public class DashboardParticipanteController {

    @FXML
    private ListView<Evento> listParticipados;


    @FXML
    private ListView<Evento> listActivos;


    private EventoDAO eventoDAO;

    private ParticipacionDAO participacionDAO;

    public void initialize() {

        eventoDAO =
                new EventoDAO();

        participacionDAO =
                new ParticipacionDAO();

        cargarEventos();
    }

    private void cargarEventos() {

        Usuario usuario =
                ManejoSesion.getUsuarioActual();

        int idUsuario =
                usuario.getId();

        /*
         * Eventos activos
         */

        listActivos
                .getItems()
                .addAll(

                        eventoDAO
                                .getEventosActivos()
                );

        listParticipados
                .getItems()
                .addAll(

                        participacionDAO
                                .getUltimosEventosParticipados(
                                        idUsuario
                                )
                );
    }

    @FXML
    public void handleLogout() {


        ManejoSesion.logout();

        try {

            /*
             * Abrir login
             */

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


            listActivos
                    .getScene()
                    .getWindow()
                    .hide();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    public void handleEventosActivos() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(

                            getClass()
                                    .getResource(
                                            "/vista/eventosActivos.fxml"
                                    )
                    );

            Scene scene =
                    new Scene(loader.load());

            Stage stage =
                    new Stage();

            stage.setScene(scene);

            stage.setTitle(
                    "Eventos Activos"
            );

            stage.show();

            listActivos
                    .getScene()
                    .getWindow()
                    .hide();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    public void handleHistorial() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(

                            getClass()
                                    .getResource(
                                            "/vista/historial.fxml"
                                    )
                    );

            Scene scene =
                    new Scene(loader.load());

            Stage stage =
                    new Stage();

            stage.setScene(scene);

            stage.setTitle(
                    "Historial"
            );

            stage.show();

            listActivos
                    .getScene()
                    .getWindow()
                    .hide();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}