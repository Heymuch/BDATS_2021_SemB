package cz.upce.bdats.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class GUI extends Application {
    @Override
    public void start(Stage theStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));

        Scene scene = new Scene(root);

        theStage.setTitle("Jiří Hejduk - Semestrální práce B");
        theStage.setScene(scene);
        theStage.show();
    }
}
