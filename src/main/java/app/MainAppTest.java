package app;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.stage.Stage;

public class MainAppTest
        extends Application {

    @Override
    public void start(Stage stage)
            throws Exception {

        FXMLLoader loader =
                new FXMLLoader(

                        getClass()
                                .getResource(
                                        "/vista/inicioSesion.fxml"
                                )
                );

        Scene scene =
                new Scene(loader.load());

        stage.setScene(scene);

        stage.setTitle(
                "Gestor de Torneos"
        );

        stage.setMinWidth(900);

        stage.setMinHeight(600);

        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}