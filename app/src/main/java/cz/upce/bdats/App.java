package cz.upce.bdats;

import java.util.Iterator;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.Objects;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

// Autopujcovna
import cz.upce.bdats.autopujcovna.*;
import cz.upce.bdats.data.*;

// JavaFX
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class App extends Application {
    private static final String TITLE = "Hejduk - Semestrální práce A";
    private static final double WIDTH = 1200;
    private static final double HEIGHT = 700;

    private IAutopujcovna autopujcovna;

    private Label lbTitle = new Label();
    private ListView<IPobocka> lvPobocky = new ListView<>();
    private ListView<Auto> lvAutaPobocky = new ListView<>();
    private ListView<Auto> lvAutaVypujcene = new ListView<>();

    private Button btnLoad = new Button("Načtení dat");
    private Button btnSave = new Button("Ulož data");

    private Button btnPobockyFirst = new Button("Prvni");
    private Button btnPobockyLast = new Button("Poslední");
    private Button btnPobockyNext = new Button("Další");
    private Button btnPobockyPrev = new Button("Předchozí");
    private Button btnPobockyNovy = new Button("Nová");
    private Button btnPobockyOdeber = new Button("Odeber");
    private Button btnPobockyZrus = new Button("Zruš");

    private Button btnAutaFirst1 = new Button("První");
    private Button btnAutaLast1 = new Button("Posledni");
    private Button btnAutaNext1 = new Button("Další");
    private Button btnAutaPrev1 = new Button("Předchozí");
    private Button btnAutaNovy = new Button("Nové");
    private Button btnAutaOdeber = new Button("Odeber");
    private Button btnAutaZrus = new Button("Zruš");

    private Button btnAutaFirst2 = new Button("První");
    private Button btnAutaLast2 = new Button("Poslední");
    private Button btnAutaNext2 = new Button("Další");
    private Button btnAutaPrev2 = new Button("Předchozí");
    private Button btnAutaVrat = new Button("Vrátit auto");
    private Button btnAutaVypujci = new Button("Vypůjčit auto");

    private VBox vb1 = new VBox();
    private VBox vb2 = new VBox();
    private VBox vb3 = new VBox();
    //private GridPane gp1 = new GridPane();
    //private GridPane gp2 = new GridPane();
    //private GridPane gp3 = new GridPane();
    //private HBox hb1 = new HBox();
    private HBox hb2 = new HBox();
    private HBox hb3 = new HBox();
    private HBox hb4 = new HBox();
    private HBox hb5 = new HBox();
    private BorderPane root = new BorderPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        autopujcovna = Generator.genAutopujcovna(0, 0, 0);
        handleShowAutopujcovna();

        btnLoad.setOnAction(e -> {
            handleLoadAutopujcovna();
            handleShowPobocky();
        });
        btnSave.setOnAction(e -> handleSaveAutopujcovna());
        btnPobockyFirst.setOnAction(e -> handlePobockaFirst());
        btnPobockyLast.setOnAction(e -> handlePobockaLast());
        btnPobockyNext.setOnAction(e -> handlePobockaNext());
        btnPobockyPrev.setOnAction(e -> handlePobockaPrev());
        btnPobockyOdeber.setOnAction(e -> handlePobockaOdeber());
        btnPobockyNovy.setOnAction(e -> handlePobockaNova());
        btnPobockyZrus.setOnAction(e -> handlePobockyZrus());

        btnAutaFirst1.setOnAction(e -> handleAutoFirst());
        btnAutaLast1.setOnAction(e -> handleAutoLast());
        btnAutaNext1.setOnAction(e -> handleAutoNext());
        btnAutaPrev1.setOnAction(e -> handleAutoPrev());
        btnAutaOdeber.setOnAction(e -> handleAutoOdeber());
        btnAutaNovy.setOnAction(e -> handleAutoNove());
        btnAutaZrus.setOnAction(e -> handleAutaZrus());

        btnAutaFirst2.setOnAction(e -> handleAutoVypujceneFirst());
        btnAutaLast2.setOnAction(e -> handleAutoVypujceneLast());
        btnAutaNext2.setOnAction(e -> handleAutoVypujceneNext());
        btnAutaPrev2.setOnAction(e -> handleAutoVypujcenePrev());
        btnAutaVrat.setOnAction(e -> handleVratAuto());
        btnAutaVypujci.setOnAction(e -> handleVypujciAuto());

        //HBox.setHgrow(lvPobocky, Priority.ALWAYS);
        //HBox.setHgrow(lvAutaPobocky, Priority.ALWAYS);
        //HBox.setHgrow(lvAutaVypujcene, Priority.ALWAYS);
        VBox.setVgrow(hb3, Priority.ALWAYS);
        VBox.setVgrow(hb4, Priority.ALWAYS);
        VBox.setVgrow(hb5, Priority.ALWAYS);

        // hb1.setSpacing(10);
        // hb1.getChildren().addAll(lvPobocky, lvAutaPobocky, lvAutaVypujcene);

        hb2.setSpacing(10);
        hb2.getChildren().addAll(btnLoad, btnSave);

        hb3.getChildren().addAll(btnPobockyNovy, btnPobockyOdeber, btnPobockyZrus, btnPobockyFirst, btnPobockyLast, btnPobockyPrev, btnPobockyNext);
        hb4.getChildren().addAll(btnAutaNovy, btnAutaOdeber, btnAutaZrus, btnAutaFirst1, btnAutaLast1, btnAutaPrev1, btnAutaNext1);
        hb5.getChildren().addAll(btnAutaVypujci, btnAutaVrat, btnAutaFirst2, btnAutaLast2, btnAutaPrev2, btnAutaNext2);

        vb1.getChildren().addAll(lvPobocky, hb3);
        vb2.getChildren().addAll(lvAutaPobocky, hb4);
        vb3.getChildren().addAll(lvAutaVypujcene, hb5);

        root.setTop(lbTitle);
        root.setBottom(hb2);
        root.setLeft(vb1);
        root.setCenter(vb2);
        root.setRight(vb3);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    private void handleShowAutopujcovna() {
        try {
            Objects.requireNonNull(autopujcovna);
            lbTitle.setText(autopujcovna.toString());
            handleShowPobocky();
            handleShowVypujcenaAuta();
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleShowAutopujcovna", e);
        }
    }

    private void handleShowPobocky() {
        try {
            lvPobocky.getItems().clear();
            Iterator<IPobocka> pobocky = autopujcovna.iterator(IteratorTyp.POBOCKY);
            while (pobocky.hasNext())
                lvPobocky.getItems().add(pobocky.next());
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleShowPobocky", e);
        }
    }

    private void handleShowAuta() {
        try {
            lvAutaPobocky.getItems().clear();
            IPobocka pobocka = autopujcovna.zpristupniPobocku(Pozice.AKTUALNI);
            Iterator<Auto> auta = pobocka.iterator();
            while (auta.hasNext())
                lvAutaPobocky.getItems().add(auta.next());
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleShowAuta", e);
        }
    }

    private void handleShowVypujcenaAuta() {
        try {
            lvAutaVypujcene.getItems().clear();
            Iterator<Auto> auta = autopujcovna.iterator(IteratorTyp.VYPUJCENE_AUTOMOBILY);
            while (auta.hasNext())
                lvAutaVypujcene.getItems().add(auta.next());
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleShowVypujcenaAuta", e);
        }
    }

    private void handleSaveAutopujcovna() {
        try {
            PrintWriter pw = new PrintWriter(new File("./data.txt"));
            String[] csv = Persistence.Autopujcovny.toCSV(autopujcovna);
            for (String s : csv)
                pw.println(s);
            pw.close();
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleSaveAutopujcovna", e);
        }
    }

    private void handleLoadAutopujcovna() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("./data.txt"));
            List<String> lines = new ArrayList<>();
            br.lines().forEach(lines::add);
            br.close();
            autopujcovna = Persistence.Autopujcovny.fromCSV(lines.toArray(new String[lines.size()]));
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleLoadAutopujcovna", e);
        }

        handleShowAutopujcovna();
    }

    private void handlePobockaFirst() {
        try {
            IPobocka pobocka = autopujcovna.zpristupniPobocku(Pozice.PRVNI);
            lvPobocky.getSelectionModel().select(pobocka);
            handleShowAuta();
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handlePobockaFirst", e);
        }
    }

    private void handleAutoFirst() {
        try {
            Auto auto = autopujcovna.zpristupniAuto(Pozice.PRVNI);
            lvAutaPobocky.getSelectionModel().select(auto);
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleAutoFirst", e);
        }
    }

    private void handleAutoVypujceneFirst() {
        try {
            Auto auto = autopujcovna.zpristupniVypujceneAuto(Pozice.PRVNI);
            lvAutaVypujcene.getSelectionModel().select(auto);
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleAutoVypujceneFirst", e);
        }
    }

    private void handlePobockaLast() {
        try {
            IPobocka pobocka = autopujcovna.zpristupniPobocku(Pozice.POSLEDNI);
            lvPobocky.getSelectionModel().select(pobocka);
            handleShowAuta();
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handlePobockaLast", e);
        }
    }

    private void handleAutoLast() {
        try {
            Auto auto = autopujcovna.zpristupniAuto(Pozice.POSLEDNI);
            lvAutaPobocky.getSelectionModel().select(auto);
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleAutoLast", e);
        }
    }

    private void handleAutoVypujceneLast() {
        try {
            Auto auto = autopujcovna.zpristupniVypujceneAuto(Pozice.POSLEDNI);
            lvAutaVypujcene.getSelectionModel().select(auto);
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleAutoVypujceneLast", e);
        }
    }

    private void handlePobockaNext() {
        try {
            IPobocka pobocka = autopujcovna.zpristupniPobocku(Pozice.NASLEDNIK);
            lvPobocky.getSelectionModel().select(pobocka);
            handleShowAuta();
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handlePobockaNext", e);
        }
    }

    private void handleAutoNext() {
        try {
            Auto auto = autopujcovna.zpristupniAuto(Pozice.NASLEDNIK);
            lvAutaPobocky.getSelectionModel().select(auto);
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleAutoNext", e);
        }
    }

    private void handleAutoVypujceneNext() {
        try {
            Auto auto = autopujcovna.zpristupniVypujceneAuto(Pozice.NASLEDNIK);
            lvAutaVypujcene.getSelectionModel().select(auto);
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleAutoVypujceneNext", e);
        }
    }

    private void handlePobockaPrev() {
        try {
            IPobocka pobocka = autopujcovna.zpristupniPobocku(Pozice.PREDCHUDCE);
            lvPobocky.getSelectionModel().select(pobocka);
            handleShowAuta();
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handlePobockaPrev", e);
        }
    }

    private void handleAutoPrev() {
        try {
            Auto auto = autopujcovna.zpristupniAuto(Pozice.PREDCHUDCE);
            lvAutaPobocky.getSelectionModel().select(auto);
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleAutoPrev", e);
        }
    }

    private void handleAutoVypujcenePrev() {
        try {
            Auto auto = autopujcovna.zpristupniVypujceneAuto(Pozice.PREDCHUDCE);
            lvAutaVypujcene.getSelectionModel().select(auto);
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleAutoVypujcenePrev", e);
        }
    }

    private void handlePobockaOdeber() {
        try {
            autopujcovna.odeberPobocku(Pozice.AKTUALNI);
            handleShowPobocky();
            handleShowAuta();
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handlePobockaOdeber", e);
        }
    }

    private void handleAutoOdeber() {
        try {
            autopujcovna.odeberAuto(Pozice.AKTUALNI);
            handleShowAuta();
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleAutoOdeber", e);
        }
    }

    private void handleAutaZrus() {
        try {
            autopujcovna.zrusPobocku();
            handleShowAuta();
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handlePobockyZrus", e);
        }
    }

    private void handlePobockyZrus() {
        try {
            autopujcovna.zrus();
            handleShowAuta();
            handleShowPobocky();
            handleShowVypujcenaAuta();
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handlePobockyZrus", e);
        }
    }

    private void handlePobockaNova() {
        try {
            autopujcovna.vlozPobocku(Generator.genPobocka(0), Pozice.POSLEDNI);
            handleShowPobocky();
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handlePobockaNova", e);
        }
    }

    private void handleAutoNove() {
        try {
            autopujcovna.vlozAuto(Generator.genAuto(), Pozice.POSLEDNI);
            handleShowAuta();
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleAutoNove", e);
        }
    }

    private void handleVypujciAuto() {
        try {
            autopujcovna.vypujcAuto(Pozice.AKTUALNI);
            handleShowAuta();
            handleShowVypujcenaAuta();
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleVypujciAuto", e);
        }
    }

    private void handleVratAuto() {
        try {
            autopujcovna.vratAuto(Pozice.AKTUALNI);
            handleShowAuta();
            handleShowVypujcenaAuta();
        } catch (Exception e) {
            Logger.getGlobal().throwing("App", "handleVratAuto", e);
        }
    }
}
