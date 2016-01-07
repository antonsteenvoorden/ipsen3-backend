/**
 *
 */
package IIIPSEN2.view;

import IIIPSEN2.control.SettingsController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import IIIPSEN2.model.Settings;

import java.util.prefs.Preferences;

/**
 * @author Anton
 */
public class FactuurInstellingenView {
    private Stage stage;
    private Scene scene;
    private double height, width, scale;
    private BorderPane mainPane;
    private GridPane gridPane;
    private HBox buttons;

    private Label lbl_bankRekening, lbl_inschrijfNummer, lbl_adres, lbl_postcode, lbl_bericht;
    private TextField tf_bankRekening, tf_inschrijfNummer, tf_adres, tf_postcode;
    private TextArea body;
    private Button opslaan, cancel;
    private Preferences prefs;
    private SettingsController settingsController;

    public FactuurInstellingenView(SettingsController settingsController) {
        stage = new Stage();
        height = Main.getInstance().height;
        //scale = Main.getInstance().scale;
        width = Main.getInstance().width;
        mainPane = new BorderPane();
        mainPane.getStyleClass().add("hbox");
        gridPane = new GridPane();
        buttons = new HBox();
        scene = new Scene(mainPane, 400, 500);
        scene.getStylesheets().clear();
        scene.getStylesheets().add("/IIIPSEN2/view/style.css");
        prefs = Settings.getInstance().getPrefs();
        this.settingsController = settingsController;
        display();

    }

    private void display() {
        lbl_bankRekening = new Label("Bankrekening: ");
        lbl_bankRekening.setId("form-label");

        lbl_bankRekening.setTextFill((Color.valueOf("#FFFC00")));

        lbl_inschrijfNummer = new Label("KvK: ");
        lbl_inschrijfNummer.setId("form-label");
        lbl_inschrijfNummer.setTextFill((Color.valueOf("#FFFC00")));

        lbl_adres = new Label("Adres: ");
        lbl_adres.setId("form-label");
        lbl_adres.setTextFill((Color.valueOf("#FFFC00")));

        lbl_postcode = new Label("Postcode: ");
        lbl_postcode.setId("form-label");
        lbl_postcode.setTextFill((Color.valueOf("#FFFC00")));

        lbl_bericht = new Label("Bericht: ");
        lbl_bericht.setId("form-label");
        lbl_bericht.setTextFill((Color.valueOf("#FFFC00")));

        tf_bankRekening = new TextField();
        tf_bankRekening.setPromptText("IBAN nummer");

        tf_inschrijfNummer = new TextField();
        tf_inschrijfNummer.setPromptText("KVK nummer");

        tf_bankRekening = new TextField();
        tf_bankRekening.setPromptText("IBAN nummer");

        tf_adres = new TextField();
        tf_adres.setPromptText("Straatnaam ");

        tf_postcode = new TextField();
        tf_postcode.setPromptText("1111 XX");

        body = new TextArea();
        body.setPromptText("U kunt uw bestelling ophalen op .. tussen .. en .. uur");

        tf_inschrijfNummer.setText(prefs.get("factuur_inschrijfNummer", ""));
        tf_bankRekening.setText(prefs.get("factuur_bankRekening", ""));
        tf_adres.setText(prefs.get("factuur_adres", ""));
        tf_postcode.setText(prefs.get("factuur_postcode", ""));

        body.setText(prefs.get("factuur_body", ""));
        opslaan = new Button("Opslaan");
        opslaan.setOnAction(e -> opslaan());

        cancel = new Button("Annuleren");
        cancel.setOnAction(e -> stage.close());
        gridPane.addRow(0, lbl_bankRekening, tf_bankRekening);
        gridPane.addRow(1, lbl_inschrijfNummer, tf_inschrijfNummer);
        gridPane.addRow(2, lbl_adres, tf_adres);
        gridPane.addRow(3, lbl_postcode, tf_postcode);
        gridPane.addRow(4, lbl_bericht);


        buttons.setPadding(new Insets(20));
        buttons.getChildren().addAll(opslaan, cancel);

        mainPane.setTop(gridPane);
        mainPane.setCenter(body);
        mainPane.setBottom(buttons);
        stage.setTitle("Settings");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private void opslaan() {
        settingsController.saveFactuur(
                tf_bankRekening.getText(),
                tf_inschrijfNummer.getText().toString(),
                tf_adres.getText().toString(),
                tf_postcode.getText().toString(),
                body.getText().toString()
        );
        stage.close();
    }

}
