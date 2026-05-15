package controller;

import DAO.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Usuario;

import java.sql.Connection;

public class RegistroController {
    private Connection conn;
    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ComboBox<String> comboTipo;

    private UsuarioDAO usuarioDAO;

    public void initialize() {

        usuarioDAO = new UsuarioDAO();

        comboTipo.getItems().addAll(
                "PARTICIPANTE",
                "PROMOTOR"
        );
    }

    @FXML
    public void handleRegister() {

        String nombre = txtNombre.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String tipo = comboTipo.getValue();

        if(nombre.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty() ||
                tipo == null) {

            mostrarError("Completa todos los campos");
            return;
        }

        Usuario usuario = new Usuario(
                0,
                nombre,
                email,
                password,
                tipo
        ) {};

        boolean registrado = usuarioDAO.registrarUsuario(usuario);

        if(registrado) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setContentText("Usuario registrado correctamente");

            alert.showAndWait();

        } else {

            mostrarError("No se pudo registrar");
        }
    }

    private void mostrarError(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setHeaderText("Error");
        alert.setContentText(mensaje);

        alert.showAndWait();
    }
}