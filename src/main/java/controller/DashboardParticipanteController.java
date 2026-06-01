package controller;

import DAO.EventoDAO;
import DAO.ParticipacionDAO;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import javafx.stage.Stage;

import model.Evento;
import model.Usuario;

import util.ManejoSesion;

import java.io.IOException;
import java.util.List;

public class DashboardParticipanteController {

    @FXML
    private ListView<Evento> listEventosActivos;

    @FXML
    private ListView<Evento> listMisEventos;

    private EventoDAO eventoDAO;

    private ParticipacionDAO participacionDAO;

    private Usuario usuarioActual;

    @FXML
    public void initialize() {

        eventoDAO =
                new EventoDAO();

        participacionDAO =
                new ParticipacionDAO();

        usuarioActual =
                ManejoSesion.getUsuarioActual();

        if(usuarioActual != null) {

            cargarEventos();
        }

        listEventosActivos.setOnMouseClicked(event -> {

            if(event.getClickCount() == 2) {

                Evento eventoSeleccionado =
                        listEventosActivos
                                .getSelectionModel()
                                .getSelectedItem();

                if(eventoSeleccionado != null) {

                    abrirDetalleEvento(
                            eventoSeleccionado
                    );

                    listMisEventos.setItems(

                            FXCollections.observableArrayList(

                                    eventoDAO.getEventosParticipados(
                                            usuarioActual.getId()
                                    )
                            )
                    );
                }
            }
        });

        listMisEventos.setOnMouseClicked(event -> {

            if(event.getClickCount() == 2) {

                Evento eventoSeleccionado =
                        listMisEventos
                                .getSelectionModel()
                                .getSelectedItem();

                if(eventoSeleccionado != null) {

                    abrirDetalleEvento(
                            eventoSeleccionado
                    );

                    listMisEventos.setItems(

                            FXCollections.observableArrayList(

                                    eventoDAO.getEventosParticipados(
                                            usuarioActual.getId()
                                    )
                            )
                    );
                }
            }
        });
    }

    /**
     * Método que recibe a un usuario
     * @param usuario usuario que inicia sesión
     */

    public void setUsuarioActual(
            Usuario usuario
    ) {

        this.usuarioActual =
                usuario;

        if(usuarioActual != null) {

            cargarEventos();
        }
    }

    /**
     * Método que se encarga de actualizar la sección de Mis Eventos
     */

    public void recargarMisEventos() {

        if(usuarioActual == null) {

            return;
        }

        listMisEventos.setItems(

                FXCollections.observableArrayList(

                        eventoDAO.getEventosParticipados(
                                usuarioActual.getId()
                        )
                )
        );
    }

    /**
     * Método que se encarga de cargar los eventos, tanto activos como los de la sección
     * de Mis Eventos
     */

    private void cargarEventos() {

        if(usuarioActual == null) {

            return;
        }

        listEventosActivos.setItems(

                FXCollections.observableArrayList(

                        eventoDAO.getEventosActivos()
                )
        );

        recargarMisEventos();
    }

    /**
     * Método que abre la ventana Detalles Evento cargando el evento seleccionado
     * @param evento Evento seleccionado
     */

    private void abrirDetalleEvento(
            Evento evento
    ) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/vista/detalleEvento.fxml"
                            )
                    );

            Scene scene =
                    new Scene(loader.load());

            DetalleEventoController controller =
                    loader.getController();

            controller.setEvento(evento);

            Stage stage =
                    new Stage();

            stage.setTitle(
                    "Detalle Evento"
            );

            stage.setScene(scene);

            stage.show();

        } catch(IOException e) {

            e.printStackTrace();

            mostrarError(
                    "No se pudo abrir el detalle"
            );
        }
    }

    /**
     * Método que abre la página de Eventos Activos
     */

    @FXML
    public void handleVerEventosActivos() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/vista/eventosActivos.fxml"
                            )
                    );

            Scene scene =
                    new Scene(loader.load());

            EventosActivosController controller =
                    loader.getController();

            controller.setUsuarioActual(
                    usuarioActual
            );

            Stage stage =
                    new Stage();

            stage.setScene(scene);

            stage.setTitle(
                    "Eventos Activos"
            );

            stage.show();

        } catch(IOException e) {

            e.printStackTrace();
        }
    }


    /**
     * Método que permite cerrar sesión, volviendo a la pantalla de inicioSesion
     */

    @FXML
    public void handleLogout() {

        ManejoSesion.logout();

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/vista/inicioSesion.fxml"
                            )
                    );

            Scene scene =
                    new Scene(loader.load());

            Stage stage =
                    (Stage) listEventosActivos
                            .getScene()
                            .getWindow();

            stage.setScene(scene);

            stage.setTitle(
                    "Inicio Sesión"
            );

            stage.show();

        } catch(IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Método que lanza un mensaje en caso de que ocurra un error
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
}