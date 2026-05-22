package controller;

import DAO.EventoDAO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import util.ManejoSesion;

import model.Usuario;
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


    public void initialize() {

        eventoDAO =
                new EventoDAO();
    }

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



        Usuario usuarioActual =
                ManejoSesion.getUsuarioActual();



        boolean creado =
                eventoDAO.insertarEvento(

                        evento,

                        usuarioActual.getId()
                );



        if(creado) {

            mostrarInfo(
                    "Evento creado correctamente"
            );

            limpiarCampos();
        }

        else {

            mostrarError(
                    "Error al crear evento"
            );
        }
    }

    private void limpiarCampos() {

        txtNombre.clear();

        txtDescripcion.clear();

        dateEvento.setValue(null);
    }


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