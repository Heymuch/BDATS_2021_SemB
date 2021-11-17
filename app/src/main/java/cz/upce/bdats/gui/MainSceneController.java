package cz.upce.bdats.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import cz.upce.bdats.autopujcovna.Auto;
import cz.upce.bdats.autopujcovna.IAutopujcovna;
import cz.upce.bdats.autopujcovna.IPobocka;
import cz.upce.bdats.autopujcovna.IteratorTyp;
import cz.upce.bdats.autopujcovna.Pobocka;
import cz.upce.bdats.autopujcovna.Pozice;
import cz.upce.bdats.data.Generator;
import cz.upce.bdats.data.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

public class MainSceneController implements Initializable {
    // Atributy
    private IAutopujcovna autopujcovna;

    // *** GUI prvky
    @FXML
    private ListView<Auto> gAutomobily;
    @FXML
    private Label gNazevAutopujcovny;
    @FXML
    private ListView<IPobocka> gPobocky;
    @FXML
    private ListView<Auto> gVypujcene;

    @Override
    public void initialize(URL url, ResourceBundle rs) {
    }

    private void updateAll() throws Exception {
        updateNazevAutopujcovny();
        updatePobocky();
        updateAutomobily();
        updateVypujcene();
    }

    private void updateNazevAutopujcovny() {
        gNazevAutopujcovny.setText("");

        if (Objects.isNull(autopujcovna))
            return;
        gNazevAutopujcovny.setText("Autopujčovna " + autopujcovna.getNazev());
    }

    private void updatePobocky() throws Exception {
        gPobocky.getItems().clear();

        if (Objects.isNull(autopujcovna))
            return;
        Iterator<IPobocka> it = autopujcovna.iterator(IteratorTyp.POBOCKY);
        while (it.hasNext())
            gPobocky.getItems().add(it.next());

        try {
            IPobocka p = autopujcovna.zpristupniPobocku(Pozice.AKTUALNI);
            gPobocky.getSelectionModel().select(p);
        } catch (Exception e) {
        }
    }

    private void updateAutomobily() throws Exception {
        gAutomobily.getItems().clear();

        try {
            if (Objects.isNull(autopujcovna))
                return;
            Iterator<Auto> it = autopujcovna.iterator(IteratorTyp.AUTOMOBILY);
            while (it.hasNext())
                gAutomobily.getItems().add(it.next());
        } catch (Exception e) {
        }
    }

    private void updateVypujcene() throws Exception {
        gVypujcene.getItems().clear();

        if (Objects.isNull(autopujcovna))
            return;
        Iterator<Auto> it = autopujcovna.iterator(IteratorTyp.VYPUJCENE_AUTOMOBILY);
        while (it.hasNext())
            gVypujcene.getItems().add(it.next());
    }

    private String handleSPZ() {
        Dialog<String> dialog = new TextInputDialog();
        dialog.setTitle("SPZ automobilu");
        dialog.setHeaderText("Zadejte SPZ automobilu");

        Optional<String> opt = dialog.showAndWait();
        if (!opt.isPresent())
            return null;

        return opt.get();
    }

    private void handleException(Exception e) {
        Alert a = new Alert(AlertType.ERROR);
        a.setHeaderText(e.getMessage());
        a.setContentText(e.getCause().getMessage());
        a.showAndWait();

        e.printStackTrace();
    }

    // *** Handlers
    @FXML
    void borrowAutomobil(ActionEvent event) {
        try {
            String spz = handleSPZ();
            if (Objects.isNull(spz) || spz.length() == 0)
                return;

            autopujcovna.vypujcAuto(spz);
            updateAutomobily();
            updateVypujcene();
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void generateAutomobil(ActionEvent event) {
        try {
            Auto a = Generator.genAuto();
            autopujcovna.zpristupniPobocku(Pozice.AKTUALNI).vlozAuto(a);
            updateAutomobily();
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void generateAutopujcovna(ActionEvent event) {
        try {
            autopujcovna = Generator.genAutopujcovna(10, 5, 5);
            updateAll();
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void generatePobockaPrvni(ActionEvent event) {
        try {
            IPobocka p = Generator.genPobocka(5);
            autopujcovna.vlozPobocku(p, Pozice.PRVNI);
            updatePobocky();
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void generatePobockaPosledni(ActionEvent event) {
        try {
            IPobocka p = Generator.genPobocka(5);
            autopujcovna.vlozPobocku(p, Pozice.POSLEDNI);
            updatePobocky();
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void generatePobockaPredchozi(ActionEvent event) {
        try {
            IPobocka p = Generator.genPobocka(5);
            autopujcovna.vlozPobocku(p, Pozice.PREDCHUDCE);
            updatePobocky();
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void generatePobockaNasledujici(ActionEvent event) {
        try {
            IPobocka p = Generator.genPobocka(5);
            autopujcovna.vlozPobocku(p, Pozice.NASLEDNIK);
            updatePobocky();
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void loadFromBIN(ActionEvent event) { // TODO
    }

    @FXML
    void loadFromCSV(ActionEvent event) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("backup.csv"));
            String[] csv = br.lines().toArray(String[]::new);
            br.close();

            autopujcovna = Persistence.Autopujcovny.fromCSV(csv);
            updateAll();
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void moveToFirstPobocka(ActionEvent event) {
        try {
            IPobocka p = autopujcovna.zpristupniPobocku(Pozice.PRVNI);
            gPobocky.getSelectionModel().select(p);
            updateAutomobily();
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void moveToFirstVypujcene(ActionEvent event) {
        try {
            Auto a = autopujcovna.zpristupniVypujceneAuto(Pozice.PRVNI);
            gVypujcene.getSelectionModel().select(a);
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void moveToLastPobocka(ActionEvent event) {
        try {
            IPobocka p = autopujcovna.zpristupniPobocku(Pozice.POSLEDNI);
            gPobocky.getSelectionModel().select(p);
            updateAutomobily();
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void moveToLastVypujcene(ActionEvent event) {
        try {
            Auto a = autopujcovna.zpristupniVypujceneAuto(Pozice.POSLEDNI);
            gVypujcene.getSelectionModel().select(a);
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void moveToNextPobocka(ActionEvent event) {
        try {
            IPobocka p = autopujcovna.zpristupniPobocku(Pozice.NASLEDNIK);
            gPobocky.getSelectionModel().select(p);
            updateAutomobily();
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void moveToNextVypujcene(ActionEvent event) {
        try {
            Auto a = autopujcovna.zpristupniVypujceneAuto(Pozice.NASLEDNIK);
            gVypujcene.getSelectionModel().select(a);
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void moveToPrevPobocka(ActionEvent event) {
        try {
            IPobocka p = autopujcovna.zpristupniPobocku(Pozice.PREDCHUDCE);
            gPobocky.getSelectionModel().select(p);
            updateAutomobily();
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void moveToPrevVypujcene(ActionEvent event) {
        try {
            Auto a = autopujcovna.zpristupniVypujceneAuto(Pozice.PREDCHUDCE);
            gVypujcene.getSelectionModel().select(a);
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void newAutomobil(ActionEvent event) {
        // TODO
    }

    @FXML
    void newAutopujcovna(ActionEvent event) {
        // TODO
    }

    @FXML
    void newPobocka(ActionEvent event) {
        // TODO
    }

    @FXML
    void removeAutomobil(ActionEvent event) {
        try {
            String spz = handleSPZ();
            if (Objects.isNull(spz) || spz.length() == 0)
                return;

            autopujcovna.odeberAuto(spz);
            updateAutomobily();
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void removePobockaAktualni(ActionEvent event) {
        try {
            IPobocka p = autopujcovna.odeberPobocku(Pozice.AKTUALNI);
            updatePobocky();
            updateAutomobily();

            System.out.println(p);
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void removePobockaPrvni(ActionEvent event) {
        try {
            IPobocka p = autopujcovna.odeberPobocku(Pozice.PRVNI);
            updatePobocky();
            updateAutomobily();

            System.out.println(p);
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void removePobockaPosledni(ActionEvent event) {
        try {
            IPobocka p = autopujcovna.odeberPobocku(Pozice.POSLEDNI);
            updatePobocky();
            updateAutomobily();

            System.out.println(p);
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void removePobockaNasledujici(ActionEvent event) {
        try {
            IPobocka p = autopujcovna.odeberPobocku(Pozice.NASLEDNIK);
            updatePobocky();
            updateAutomobily();

            System.out.println(p);
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void removePobockaPredchozi(ActionEvent event) {
        try {
            IPobocka p = autopujcovna.odeberPobocku(Pozice.PREDCHUDCE);
            updatePobocky();
            updateAutomobily();

            System.out.println(p);
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void returnAutomobil(ActionEvent event) {
        try {
            autopujcovna.vratAuto(Pozice.AKTUALNI);
            updateAutomobily();
            updateVypujcene();
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void saveToBin(ActionEvent event) { // TODO saveToBin
    }

    @FXML
    void saveToCSV(ActionEvent event) {
        try {
            String[] csv = Persistence.Autopujcovny.toCSV(autopujcovna);
            PrintWriter pw = new PrintWriter(new File("backup.csv"));
            Arrays.asList(csv).stream().forEach(line -> pw.println(line));
            pw.close();
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void selectAutomobil(ActionEvent event) {
        try {
            String spz = handleSPZ();
            if (Objects.isNull(spz) || spz.length() == 0)
                return;

            Auto a = autopujcovna.zpristupniAuto(spz);
            gAutomobily.getSelectionModel().select(a);
        } catch (Exception e) {
            handleException(e);
        }
    }

    @FXML
    void selectAutomobilAll(ActionEvent event) {
        try {
            String spz = handleSPZ();
            if (Objects.isNull(spz) || spz.length() == 0) return;

            Iterator<IPobocka> pobocky = autopujcovna.iterator(IteratorTyp.POBOCKY);
            Auto a = null;
            IPobocka p = null;
            while (pobocky.hasNext()) {
                try {
                    p = pobocky.next();
                    a = p.zpristupniAuto(spz);
                    break;
                } catch (Exception e) {}
            }

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Vyhledání automobilu");
            if (Objects.nonNull(a)) {
                alert.setHeaderText("Automobil nalezen: pobočka " + p.toString());
                alert.setContentText(a.toString());
            } else {
                alert.setHeaderText("Automobil nenalezen");
                alert.setContentText("Hledaná SPZ: " + spz);
            }
            alert.showAndWait();
        } catch (Exception e) {
            handleException(e);
        }
    }
}