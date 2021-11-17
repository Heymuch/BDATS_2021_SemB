package cz.upce.bdats.gui;

import java.util.ResourceBundle;

import cz.upce.bdats.autopujcovna.IAutopujcovna;
import cz.upce.bdats.data.Generator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application {
    // *** Atributy
    private IAutopujcovna autopujcovna;
    private ResourceBundle resources;

    // *** Metody třídy Application
    @Override
    public void init() throws Exception {
        super.init();
        autopujcovna = Generator.genAutopujcovna(3, 5, 5);
        resources = new MyResources(autopujcovna);
    }

    @Override
    public void start(Stage theStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"), resources);

        Scene scene = new Scene(root);

        theStage.setTitle("Jiří Hejduk - Semestrální práce B");
        theStage.setScene(scene);
        theStage.show();
    }
}
