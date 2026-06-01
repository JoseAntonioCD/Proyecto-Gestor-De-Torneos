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

    /**
     * Método que maneja el registro de un nuevo usuario en la aplicación
     */
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

        if(
                nombre.isBlank()
                        ||
                        correo.isBlank()
                        ||
                        password.isBlank()
                        ||
                        tipo == null
        ) {

            mostrarError(
                    "Completa todos los campos"
            );

            return;
        }

        if(
                !correo.matches(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
                )
        ) {

            mostrarError(
                    "Correo electrónico inválido"
            );

            return;
        }

        if(password.length() < 4) {

            mostrarError(
                    "La contraseña debe tener mínimo 4 caracteres"
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

    /**
     * Método que lanza un mensaje de error en caso de que sea necesario
     * @param mensaje mensaje de error
     */
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

    /**
     * Método que maneja los mensajes que verá el usuario al realizar una acción
     * @param mensaje Mensaje de feedback
     */
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

    /**
     * Método que limpia los campos de registro al terminar el proceso
     */
    private void limpiarCampos() {

        txtNombre.clear();

        txtCorreo.clear();

        txtPassword.clear();

        comboTipo.setValue(null);
    }

    /**
     * Método con el que se vuelve a la página de Login
     */
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