package app;

import dataAccess.ConnectionBD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainAppTest extends Application {

    @Override
    public void start(Stage stage) {

        try {

            ConnectionBD.getConnection();

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

            stage.setTitle(
                    "Gestor de Torneos"
            );

            stage.setScene(scene);

            stage.setResizable(true);

            stage.show();

        }

        catch (Exception e) {

            System.out.println(
                    "Error al iniciar aplicación"
            );

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        launch(args);
    }
}