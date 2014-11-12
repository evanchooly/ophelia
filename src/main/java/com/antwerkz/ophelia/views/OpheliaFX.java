package com.antwerkz.ophelia.views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.fxml.FXMLLoader.load;

public class OpheliaFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        Scene scene = new Scene(load(getClass().getResource("/Ophelia.fxml")));
        stage.setTitle("Ophelia");
        stage.setScene(scene);
        stage.show();

    }
}
