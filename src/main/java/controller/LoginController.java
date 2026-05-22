package controller;

import DAO.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Usuario;
import util.ManejoSesion;

public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnRegistro;

    private UsuarioDAO usuarioDAO;


    @FXML
    public void initialize() {

        usuarioDAO = new UsuarioDAO();
    }


    @FXML
    public void handleLogin() {

        String email =
                txtEmail.getText();

        String password =
                txtPassword.getText();

        if(email.isEmpty() ||
                password.isEmpty()) {

            mostrarError(
                    "Completa todos los campos"
            );

            return;
        }

        Usuario usuario =
                usuarioDAO.login(
                        email,
                        password
                );

        if(usuario == null) {

            mostrarError(
                    "Usuario o contraseña incorrectos"
            );

            return;
        }

        ManejoSesion.login(usuario);

        abrirDashboard(usuario);
    }


    private void abrirDashboard(
            Usuario usuario
    ) {

        try {

            FXMLLoader loader;

            if(usuario.getTipo()
                    .equalsIgnoreCase(
                            "PROMOTOR"
                    )) {

                loader =
                        new FXMLLoader(
                                getClass().getResource(
                                        "/vista/dashboardPromotor.fxml"
                                )
                        );

            } else {

                loader =
                        new FXMLLoader(
                                getClass().getResource(
                                        "/vista/dashboardParticipante.fxml"
                                )
                        );
            }

            Scene scene =
                    new Scene(
                            loader.load()
                    );

            Stage stage =
                    new Stage();

            stage.setTitle(
                    "Gestor de Torneos"
            );

            stage.setScene(scene);

            stage.show();

            txtEmail.getScene()
                    .getWindow()
                    .hide();

        }

        catch(Exception e) {

            e.printStackTrace();
        }
    }


    @FXML
    public void handleRegistro() {

        System.out.println(
                "Botón registro pulsado"
        );

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/vista/registro.fxml"
                            )
                    );

            Scene scene =
                    new Scene(
                            loader.load()
                    );

            Stage stage =
                    (Stage) btnRegistro
                            .getScene()
                            .getWindow();

            stage.setScene(scene);

            stage.show();

        }

        catch(Exception e) {

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

        alert.setTitle(
                "Error"
        );

        alert.setHeaderText(null);

        alert.setContentText(
                mensaje
        );

        alert.showAndWait();
    }
}