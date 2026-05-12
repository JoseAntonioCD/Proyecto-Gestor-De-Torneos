package controller;

import DAO.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Usuario;

import java.sql.Connection;

public class LoginController {
    private Connection conn;
    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    private UsuarioDAO usuarioDAO;

    public void initialize() {
        usuarioDAO = new UsuarioDAO(conn);
    }

    @FXML
    public void handleLogin() {

        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (email.isEmpty() || password.isEmpty()) {

            mostrarError("Todos los campos son obligatorios");
            return;
        }

        Usuario usuario = usuarioDAO.login(email, password);

        if (usuario != null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Bienvenido " + usuario.getNombre());
            alert.showAndWait();

            abrirDashboard(usuario);

        } else {
            mostrarError("Credenciales incorrectas");
        }
    }

    private void mostrarError(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void abrirDashboard(Usuario usuario) {

        System.out.println("Abrir dashboard de: " + usuario.getTipo());

        // luego aquí cambias escena
    }
}