package controller;

import DAO.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.EntidadPromotora;
import model.Participante;
import model.Usuario;

public class RegistroController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ComboBox<String> comboTipo;

    private UsuarioDAO usuarioDAO;

    @FXML
    public void initialize() {

        usuarioDAO = new UsuarioDAO();

        comboTipo.getItems().addAll(
                "PARTICIPANTE",
                "PROMOTOR"
        );
    }

    @FXML
    public void handleRegister() {

        System.out.println(
                "Botón registrar pulsado"
        );

        String nombre =
                txtNombre.getText();

        String correo =
                txtCorreo.getText();

        String password =
                txtPassword.getText();

        String tipo =
                comboTipo.getValue();

        if(nombre.isEmpty() ||
                correo.isEmpty() ||
                password.isEmpty() ||
                tipo == null) {

            mostrarError(
                    "Completa todos los campos"
            );

            return;
        }

        Usuario usuario;


        if(tipo.equals("PARTICIPANTE")) {

            usuario =
                    new Participante();

        } else {

            usuario =
                    new EntidadPromotora();
        }

        usuario.setNombre(nombre);

        usuario.setEmail(correo);

        usuario.setPassword(password);

        usuario.setTipo(tipo);

        boolean registrado =
                usuarioDAO.registrarUsuario(
                        usuario
                );

        if(registrado) {

            mostrarInfo(
                    "Usuario registrado correctamente"
            );

            limpiarCampos();

        } else {

            mostrarError(
                    "No se pudo registrar usuario"
            );
        }
    }

    private void mostrarError(String mensaje) {

        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alert.setTitle("Error");

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();
    }

    private void mostrarInfo(String mensaje) {

        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alert.setTitle("Información");

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();
    }


    private void limpiarCampos() {

        txtNombre.clear();

        txtCorreo.clear();

        txtPassword.clear();

        comboTipo.setValue(null);
    }

    @FXML
    public void handleVolverLogin() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(

                            getClass().getResource(
                                    "/vista/inicioSesion.fxml"
                            )
                    );

            Scene scene =
                    new Scene(
                            loader.load()
                    );

            Stage stage =
                    (Stage) txtNombre
                            .getScene()
                            .getWindow();

            stage.setScene(scene);

            stage.show();

        }

        catch(Exception e) {

            e.printStackTrace();
        }
    }
}