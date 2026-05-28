package controller;

import DAO.EventoDAO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.control.ListView;

import javafx.stage.Stage;

import model.Evento;

import java.util.List;

public class VerEventosActivosController {

    @FXML
    private ListView<Evento> listEventos;

    private EventoDAO eventoDAO;

    public void initialize() {

        eventoDAO =
                new EventoDAO();

        cargarEventos();
    }

    /**
     * Método que carga los eventos disponibles
     */

    private void cargarEventos() {

        listEventos
                .getItems()
                .clear();

        List<Evento> eventos =
                eventoDAO.getEventosActivos();

        listEventos
                .getItems()
                .addAll(eventos);
    }

    /**
     * Método que actualiza los eventos al realizar una acción
     */

    @FXML
    public void handleActualizar() {

        cargarEventos();
    }

    /**
     * Método que abre la página de Detalle Evento
     */

    @FXML
    public void handleAbrirDetalle() {

        Evento eventoSeleccionado =
                listEventos
                        .getSelectionModel()
                        .getSelectedItem();

        if(eventoSeleccionado == null) {

            return;
        }

        try {

            FXMLLoader loader =
                    new FXMLLoader(

                            getClass()
                                    .getResource(
                                            "/vista/detalleEvento.fxml"
                                    )
                    );

            Scene scene =
                    new Scene(
                            loader.load()
                    );

            DetalleEventoController controller =
                    loader.getController();

            controller.setEvento(
                    eventoSeleccionado
            );

            Stage stage =
                    new Stage();

            stage.setScene(scene);

            stage.setTitle(
                    "Detalle Evento"
            );

            stage.show();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * Método que maneja la vuelta a la página de Dashboard Participante
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
                    new Scene(
                            loader.load()
                    );

            Stage stage =
                    (Stage) listEventos
                            .getScene()
                            .getWindow();

            stage.setScene(scene);

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}