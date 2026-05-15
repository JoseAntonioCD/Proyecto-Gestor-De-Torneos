package controller;

import DAO.UsuarioDAO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import model.Usuario;

public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    private UsuarioDAO usuarioDAO;

    /*
     * Se ejecuta automáticamente
     * al cargar el FXML
     */

    public void initialize() {

        usuarioDAO = new UsuarioDAO();
    }

    /*
     * BOTÓN LOGIN
     */

    @FXML
    public void handleLogin() {

        String email =
                txtEmail.getText();

        String password =
                txtPassword.getText();

        /*
         * Validación básica
         */

        if(email.isEmpty() ||
                password.isEmpty()) {

            mostrarError(
                    "Completa todos los campos"
            );

            return;
        }

        /*
         * Intento login
         */

        Usuario usuario =
                usuarioDAO.login(
                        email,
                        password
                );

        /*
         * Si no existe
         */

        if(usuario == null) {

            mostrarError(
                    "Email o contraseña incorrectos"
            );

            return;
        }

        /*
         * Abrir dashboard correcto
         */

        abrirDashboard(usuario);
    }

    /*
     * Detecta el tipo de usuario
     * y abre la ventana adecuada
     */

    private void abrirDashboard(
            Usuario usuario
    ) {

        try {

            FXMLLoader loader;

            /*
             * Participante
             */

            if(usuario.getTipo()
                    .equals("PARTICIPANTE")) {

                loader =
                        new FXMLLoader(

                                getClass()
                                        .getResource(
                                                "/view/dashboardParticipante.fxml"
                                        )
                        );
            }

            /*
             * Promotor
             */

            else {

                loader =
                        new FXMLLoader(

                                getClass()
                                        .getResource(
                                                "/view/dashboardPromotor.fxml"
                                        )
                        );
            }

            Scene scene =
                    new Scene(
                            loader.load()
                    );

            Stage stage =
                    new Stage();

            stage.setScene(scene);

            stage.setTitle(
                    "Gestor de Torneos"
            );

            stage.show();

            /*
             * Cierra login
             */

            txtEmail.getScene()
                    .getWindow()
                    .hide();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    /*
     * Abrir registro
     */

    @FXML
    public void abrirRegistro() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(

                            getClass()
                                    .getResource(
                                            "/view/register.fxml"
                                    )
                    );

            Scene scene =
                    new Scene(
                            loader.load()
                    );

            Stage stage =
                    new Stage();

            stage.setScene(scene);

            stage.setTitle(
                    "Registro"
            );

            stage.show();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    /*
     * Alerts de error
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