package IIIPSEN2.view;

import IIIPSEN2.control.KlantController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import IIIPSEN2.model.Klant;

import java.util.Optional;

/**
 * @author Anton Steenvoorden
 *         Scherm met daarin het aanmaken of bewerken van een gast
 *         dubbele constructor omdat voor het bewerken de waarden van de klant worden ingevuld.
 */
public class KlantBeherenView {
    private Stage stage;
    private Scene scene;
    private Button cancel, opslaan, opslaanHome;
    private HBox centerBox;
    private HBox bottomBox;

    private BorderPane mainPane;
    private HBox topBox;

    private GridPane form;

    private Label title;
    private Klant klant;
    //form labels ..

    private Label lbl_voornaam, lbl_tussenvoegsel, lbl_achternaam, lbl_straat,
            lbl_postcode, lbl_postcodeToevoeging, lbl_woonplaats, lbl_huisnummer, lbl_huisnummerToevoeging,
            lbl_notitie, lbl_email, lbl_telefoon, lbl_gastLid, lbl_actief;
    private TextField tf_voornaam, tf_tussenvoegsel, tf_achternaam, tf_straat,
            tf_postcode, tf_postcodeToevoeging, tf_woonplaats, tf_huisnummer, tf_huisnummerToevoeging, tf_email,
            tf_telefoon;
    private TextArea tf_notitie;

    private ComboBox gastLid, actief;

    private KlantController klantController;

    // dropdown actief ?..

    private ImageView icon;
    private int tmpActief = 0;

    public KlantBeherenView(KlantController klantController) {
        this.klantController = klantController;
        init();
        display();
    }

    public KlantBeherenView(Klant klant, KlantController klantController) {
        this.klant = klant;
        this.klantController = klantController;

        init();
        display();
        fillTextFields();
        changeButtons();
    }

    private void init() {
        stage = Main.getInstance().mainStage;

        mainPane = new BorderPane();
        topBox = new HBox();

        centerBox = new HBox();
        centerBox.getStyleClass().add("hbox");
        centerBox.setAlignment(Pos.CENTER);
        bottomBox = new HBox();
        bottomBox.getStyleClass().add("hbox");
        bottomBox.setAlignment(Pos.CENTER);

        form = new GridPane();
        form.setHgap(5);
        form.setVgap(5);
        form.getStyleClass().add("form");

        scene = new Scene(mainPane);
        scene.getStylesheets().add("/IIIPSEN2/view/style.css");
    }

    private void display() {
        int minWidth = 100;
        int maxWidth = 512;
        int prefWidth = 256;
        //create labels and textfields
        lbl_voornaam = new Label("Voornaam :");
        lbl_voornaam.setMinWidth(minWidth);

        lbl_voornaam.setId("form-label");
        lbl_voornaam.setTextFill((Color.valueOf("#FFFC00")));

        lbl_tussenvoegsel = new Label("Tussenvoegsel :");
        lbl_tussenvoegsel.setMinWidth(minWidth);

        lbl_tussenvoegsel.setId("form-label");
        lbl_tussenvoegsel.setTextFill((Color.valueOf("#FFFC00")));

        lbl_achternaam = new Label("Achternaam :");
        lbl_achternaam.setMinWidth(minWidth);

        lbl_achternaam.setId("form-label");
        lbl_achternaam.setTextFill((Color.valueOf("#FFFC00")));

        lbl_straat = new Label("Straat :");
        lbl_straat.setMinWidth(minWidth);

        lbl_straat.setId("form-label");
        lbl_straat.setTextFill((Color.valueOf("#FFFC00")));

        lbl_postcode = new Label("Postcode :");
        lbl_postcode.setMinWidth(minWidth);
        lbl_postcode.setId("form-label");
        lbl_postcode.setTextFill((Color.valueOf("#FFFC00")));

        lbl_postcodeToevoeging = new Label("Letters :");
        lbl_postcodeToevoeging.setMinWidth(minWidth);
        lbl_postcodeToevoeging.setId("form-label");
        lbl_postcodeToevoeging.setTextFill((Color.valueOf("#FFFC00")));

        lbl_woonplaats = new Label("Woonplaats :");
        lbl_woonplaats.setMinWidth(minWidth);

        lbl_woonplaats.setId("form-label");
        lbl_woonplaats.setTextFill((Color.valueOf("#FFFC00")));

        lbl_huisnummer = new Label("Huis nr. :");
        lbl_huisnummer.setMinWidth(minWidth);
        lbl_huisnummer.setId("form-label");
        lbl_huisnummer.setTextFill((Color.valueOf("#FFFC00")));

        lbl_huisnummerToevoeging = new Label("Toevoeging :");
        lbl_huisnummerToevoeging.setMinWidth(minWidth);
        lbl_huisnummerToevoeging.setId("form-label");
        lbl_huisnummerToevoeging.setTextFill((Color.valueOf("#FFFC00")));

        lbl_notitie = new Label("Notitie :");
        lbl_notitie.setMinWidth(minWidth);

        lbl_notitie.setId("form-label");
        lbl_notitie.setTextFill((Color.valueOf("#FFFC00")));

        lbl_email = new Label("*E-mail :");
        lbl_email.setMinWidth(minWidth);

        lbl_email.setId("form-label");
        lbl_email.setTextFill((Color.valueOf("#FFFC00")));

        lbl_telefoon = new Label("Telefoon :");
        lbl_telefoon.setMinWidth(minWidth);

        lbl_telefoon.setId("form-label");
        lbl_telefoon.setTextFill((Color.valueOf("#FFFC00")));

        lbl_gastLid = new Label("Gast of lid :");
        lbl_gastLid.setMinWidth(minWidth);

        lbl_gastLid.setId("form-label");
        lbl_gastLid.setTextFill((Color.valueOf("#FFFC00")));

        lbl_actief = new Label("Actief :");
        lbl_actief.setMinWidth(minWidth);
        lbl_actief.setId("form-label");
        lbl_actief.setTextFill((Color.valueOf("#FFFC00")));

        tf_voornaam = new TextField();
        tf_voornaam.setMinWidth(minWidth);
        tf_voornaam.setPrefWidth(prefWidth);
        tf_voornaam.setMaxWidth(maxWidth);

        tf_tussenvoegsel = new TextField();
        tf_tussenvoegsel.setPrefWidth(prefWidth);
        tf_tussenvoegsel.setMaxWidth(maxWidth);

        tf_achternaam = new TextField();
        tf_achternaam.setMinWidth(minWidth);
        tf_achternaam.setPrefWidth(prefWidth);
        tf_achternaam.setMaxWidth(maxWidth);

        tf_straat = new TextField();
        tf_straat.setMinWidth(minWidth);
        tf_straat.setPrefWidth(prefWidth);
        tf_straat.setMaxWidth(maxWidth);

        tf_postcode = new TextField();
        tf_postcode.setMaxWidth(80);

        tf_postcodeToevoeging = new TextField();
        tf_postcodeToevoeging.setMaxWidth(40);
        tf_postcodeToevoeging.setMinWidth(40);
        tf_woonplaats = new TextField();
        tf_woonplaats.setMinWidth(minWidth);
        tf_woonplaats.setPrefWidth(prefWidth);
        tf_woonplaats.setMaxWidth(maxWidth);

        tf_huisnummer = new TextField();
        tf_huisnummer.setMaxWidth(40);
        tf_huisnummerToevoeging = new TextField();
        tf_huisnummerToevoeging.setMaxWidth(40);
        tf_huisnummerToevoeging.setMinWidth(40);

        tf_email = new TextField();
        tf_email.setMinWidth(40);
        tf_email.setPrefWidth(prefWidth);
        tf_email.setMaxWidth(minWidth);

        tf_telefoon = new TextField();
        tf_telefoon.setMinWidth(40);
        tf_telefoon.setMaxWidth(minWidth);
        tf_notitie = new TextArea();
        tf_notitie.setPrefWidth(prefWidth);
        tf_notitie.setMinWidth(40);
        tf_notitie.setMaxWidth(minWidth);

        actief = new ComboBox();
        actief.getItems().add("Ja");
        actief.getItems().add("Nee");
        actief.setValue("Ja");
        actief.setMaxWidth(maxWidth);

        gastLid = new ComboBox();
        gastLid.getItems().add("Gast");
        gastLid.getItems().add("Lid");
        gastLid.setValue("Gast");
        gastLid.setMaxWidth(maxWidth);


        //topbox items
        title = new Label("Lions-club Oegstgeest/Warmond");
        title.setId("title");
        title.setTextFill(Color.web("#FFCF03"));
        title.autosize();

        icon = new ImageView(new Image("/IIIPSEN2/images/icon.png"));
        icon.setPreserveRatio(true);
        icon.autosize();

        //buttons
        cancel = new Button("Cancel");
        cancel.setOnAction(e -> new HomeView());

        opslaan = new Button("Toevoegen");
        opslaan.setOnAction(e -> {
            opslaan();
            new KlantBeherenView(new KlantController());
        });

        opslaanHome = new Button("Toevoegen en naar overzicht");
        opslaanHome.setOnAction(e -> {
            opslaan();
            new KlantOverzichtView(new KlantController());
        });

        //add elements to panes
        topBox.getChildren().addAll(icon, title);
        topBox.setAlignment(Pos.CENTER);

        centerBox.getChildren().add(form);
        form.addRow(0, lbl_voornaam, tf_voornaam);
        form.addRow(1, lbl_tussenvoegsel, tf_tussenvoegsel);
        form.addRow(2, lbl_achternaam, tf_achternaam);
        form.addRow(3, lbl_straat, tf_straat);
        form.addRow(4, lbl_huisnummer, tf_huisnummer, lbl_huisnummerToevoeging, tf_huisnummerToevoeging);
        form.addRow(5, lbl_postcode, tf_postcode, lbl_postcodeToevoeging, tf_postcodeToevoeging);
        form.addRow(6, lbl_woonplaats, tf_woonplaats);
        form.addRow(7, lbl_telefoon, tf_telefoon);
        form.addRow(8, lbl_email, tf_email);
        form.addRow(9, lbl_notitie, tf_notitie);
        form.addRow(10, lbl_gastLid, gastLid);
        form.addRow(11, lbl_actief, actief);

        bottomBox.getChildren().addAll(cancel, opslaan, opslaanHome);
        bottomBox.setLayoutY(stage.heightProperty().doubleValue() - 100);
        mainPane.setTop(topBox);
        mainPane.setCenter(centerBox);
        mainPane.setBottom(bottomBox);
        stage.setScene(scene);
    }

    private void opslaan() {
        klantController.addKlant(getNewKlant());
    }

    private void checkValues() {
        if (tf_email.getText().isEmpty() || !tf_email.getText().contains("@")) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Een e-mail adres is vereist ! ");
            alert.showAndWait();
        }

//        if(tf_email.getText().isEmpty()) tf_email.setText("");
//        if(tf_voornaam.getText().isEmpty()) tf_voornaam.setText("");
//
//        if(tf_achternaam.getText().isEmpty()) tf_achternaam.setText("");
//        if(tf_straat.getText().isEmpty()) tf_straat.setText("");
//        if(tf_huisnummer.getText().isEmpty()) tf_huisnummer.setText("0");
//        if(tf_postcode.getText().isEmpty()) tf_huisnummer.setText("0");
//        if(tf_postcodeToevoeging.getText().isEmpty()) tf_postcodeToevoeging.setText("");
//        if(tf_woonplaats.getText().isEmpty()) tf_woonplaats.setText("");
//        if(tf_telefoon.getText().isEmpty()) tf_telefoon.setText("");
//        if(tf_tussenvoegsel.getText().isEmpty()) tf_tussenvoegsel.setText("");
//        if(tf_huisnummerToevoeging.getText().isEmpty()) tf_huisnummerToevoeging.setText("");
//        if(tf_notitie.getText().isEmpty()) tf_notitie.setText("");

        if (actief.getValue().toString().equals("Ja") || actief.getValue().toString().equals("1")) {
            tmpActief = 1;
        } else {
            tmpActief = 0;
        }
    }

    private Klant getNewKlant() {

        checkValues();
        Klant k = null;
        Optional<Integer> optionalHuisnummer = null;
        Optional<Integer> optionalPostcode = null;
        if (tf_huisnummer.getText().toString() != null && !tf_huisnummer.getText().equals("") && !tf_huisnummer
                .getText().isEmpty()) {
            optionalHuisnummer = Optional.of(Integer.parseInt(tf_huisnummer.getText()));
        }
        if (tf_postcode.getText().toString() != null && !tf_huisnummer.getText().equals("")) {
            optionalPostcode = Optional.of(Integer.parseInt(tf_huisnummer.getText()));
        }
        try {
            k = new Klant(
                    tf_email.getText(),
                    tf_voornaam.getText(),
                    tf_tussenvoegsel.getText(),
                    tf_achternaam.getText(),
                    tf_straat.getText(),
                    optionalHuisnummer,
                    tf_huisnummerToevoeging.getText(),
                    optionalPostcode,
                    tf_postcodeToevoeging.getText(),
                    tf_woonplaats.getText(),
                    tf_telefoon.getText(),
                    gastLid.getValue().toString(),
                    tf_notitie.getText(),
                    tmpActief,
                    null
            );
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Er is iets misgegaan");
            alert.setContentText("Let goed op dat postcode en huisnummer getallen zijn !");
            alert.showAndWait();
        }
        return k;
    }

    private void fillTextFields() {
        tf_voornaam.setText(klant.getVoornaam());
        tf_tussenvoegsel.setText(klant.getTussenvoegsel());
        tf_achternaam.setText(klant.getAchternaam());
        tf_straat.setText(klant.getStraatnaam());
        tf_postcode.setText(Integer.toString(klant.getPostcode()));
        tf_postcodeToevoeging.setText(klant.getPostcodeToevoeging());
        tf_woonplaats.setText(klant.getPlaatsNaam());
        tf_huisnummer.setText(Integer.toString(klant.getHuisNummer()));
        tf_huisnummerToevoeging.setText(klant.getHuisNummerToevoeging());
        tf_notitie.setText(klant.getNotitie());
        tf_email.setText(klant.getEmail());
        tf_telefoon.setText(klant.getTelefoon());
        gastLid.setValue(klant.getGastLid());
        if (klant.getIsActief() == 1) {
            actief.setValue("Ja");
        } else {
            actief.setValue("Nee");
        }


    }

    private void changeButtons() {
        cancel.setOnAction(e -> new KlantOverzichtView(new KlantController()));
        opslaan.setText("Opslaan");
        opslaan.setOnAction(e -> {
            klantController.updateKlant(getNewKlant());
            new KlantOverzichtView(new KlantController());
        });
        opslaanHome.setVisible(false);
    }
}