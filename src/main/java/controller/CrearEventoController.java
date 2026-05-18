package controller;

import DAO.EventoDAO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import model.Evento;

public class CrearEventoController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private DatePicker dateEvento;

    private EventoDAO eventoDAO;

    /*
     * ID temporal del promotor
     */

    private int idEntidad = 1;

    public void initialize() {

        eventoDAO =
                new EventoDAO();
    }

    /*
     * Crear evento
     */

    @FXML
    public void handleCrearEvento() {

        String nombre =
                txtNombre.getText();

        String descripcion =
                txtDescripcion.getText();

        if(nombre.isEmpty() ||
                descripcion.isEmpty() ||
                dateEvento.getValue() == null) {

            mostrarError(
                    "Completa todos los campos"
            );

            return;
        }

        Evento evento =
                new Evento();

        evento.setNombre(nombre);

        evento.setDescripcion(descripcion);

        evento.setFechaEvento(
                dateEvento.getValue()
        );

        evento.setEstado("ACTIVO");

        boolean ok =
                eventoDAO.insertarEvento(
                        evento,
                        idEntidad
                );

        if(ok) {

            mostrarInfo(
                    "Evento creado correctamente"
            );

            limpiarCampos();
        }
    }

    /*
     * Limpia formulario
     */

    private void limpiarCampos() {

        txtNombre.clear();

        txtDescripcion.clear();

        dateEvento.setValue(null);
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
                                            "/vista/dashboardPromotor.fxml"
                                    )
                    );

            Scene scene =
                    new Scene(loader.load());

            Stage stage =
                    new Stage();

            stage.setScene(scene);

            stage.setTitle(
                    "Dashboard Promotor"
            );

            stage.show();

            txtNombre
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