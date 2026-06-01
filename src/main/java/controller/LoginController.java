package controller;

import DAO.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Usuario;
import util.ManejoSesion;
import controller.DashboardPromotorController;
import controller.DashboardParticipanteController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.EntidadPromotora;
import model.Usuario;

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

    /**
     * Método que maneja el proceso de Login
     */
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

    /**
     * Método que abre el dashboard correspondiente del usuario que acaba de iniciar sesión
     * @param usuario
     */
    private void abrirDashboard(
            Usuario usuario
    ) {

        try {

            FXMLLoader loader;

            if(usuario instanceof EntidadPromotora) {

                loader =
                        new FXMLLoader(
                                getClass().getResource(
                                        "/vista/dashboardPromotor.fxml"
                                )
                        );

                Parent root =
                        loader.load();

                DashboardPromotorController controller =
                        loader.getController();

                controller.setPromotorActual(
                        (EntidadPromotora) usuario
                );

                Stage stage =
                        new Stage();

                stage.setScene(
                        new Scene(root)
                );

                stage.show();

            } else {

                loader =
                        new FXMLLoader(
                                getClass().getResource(
                                        "/vista/dashboardParticipante.fxml"
                                )
                        );

                Parent root =
                        loader.load();

                DashboardParticipanteController controller =
                        loader.getController();

                controller.setUsuarioActual(usuario);

                Stage stage =
                        new Stage();

                stage.setScene(
                        new Scene(root)
                );

                stage.show();
            }

            Stage loginStage =
                    (Stage) txtEmail.getScene()
                            .getWindow();

            loginStage.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * Método que abre la página de registro al darle al botón de Registro
     */
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

    /**
     * Método que muestra un mensaje de error en caso de que el usuario cometa algún fallo en el login
     * @param mensaje Mensaje de error
     */
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