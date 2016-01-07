package IIIPSEN2.view;

import IIIPSEN2.control.SettingsController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
 * @author: Anton Steenvoorden
 * View in which you can manage your settings. Makes use of the Settings class (Singleton).
 * Allows user to change mail settings.
 */
public class SettingsView {
    private Stage stage;
    private Scene scene;
    private double height, width, scale;
    private BorderPane mainPane;
    private GridPane gridPane;
    private HBox buttons, mailBox;

    private Label lbl_mailAdres, lbl_wachtwoord;
    private TextField tf_mailAdres;
    private PasswordField tf_wachtwoord;
    private Button bt_dankMail, bt_herrineringMail, bt_uitnodigingMail, bt_factuur;
    private Button opslaan, cancel;
    private Preferences prefs;
    private SettingsController settingsController;

    public SettingsView() {
        stage = new Stage();
        height = Main.getInstance().height;
        //scale = Main.getInstance().scale;
        width = Main.getInstance().width;
        mainPane = new BorderPane();
        mainPane.getStyleClass().add("hbox");
        gridPane = new GridPane();
        buttons = new HBox();
        mailBox = new HBox();
        scene = new Scene(mainPane, width / 2, height - 200);
        scene.getStylesheets().clear();
        scene.getStylesheets().add("/IIIPSEN2/view/style.css");
        prefs = Settings.getInstance().getPrefs();
        settingsController = new SettingsController(prefs);
        display();
    }

    private void display() {
        lbl_mailAdres = new Label("E-mail van verzender :");
        lbl_mailAdres.setId("form-label");
        lbl_mailAdres.setTextFill((Color.valueOf("#FFFC00")));

        lbl_wachtwoord = new Label("Wachtwoord");
        lbl_wachtwoord.setId("form-label");
        lbl_wachtwoord.setTextFill((Color.valueOf("#FFFC00")));

        tf_mailAdres = new TextField();
        tf_mailAdres.setPrefWidth(width / 4);
        tf_mailAdres.setPromptText("example@example.example");
        tf_mailAdres.setFocusTraversable(false);
        tf_wachtwoord = new PasswordField();

        bt_dankMail = new Button("Dank mail");
        bt_dankMail.setOnAction(e -> new DankInstellingenView(settingsController));

        bt_herrineringMail = new Button("Herinnering mail");
        bt_herrineringMail.setOnAction(e -> new HerinneringInstellingenView(settingsController));

        bt_uitnodigingMail = new Button("Uitnodiging mail");
        bt_uitnodigingMail.setOnAction(e -> new UitnodigingInstellingenView(settingsController));

        bt_factuur = new Button("Factuur");
        bt_factuur.setOnAction(e -> new FactuurInstellingenView(settingsController));

        tf_mailAdres.setText(prefs.get("mail_id", ""));
        tf_wachtwoord.setText(prefs.get("mail_pw", ""));

        opslaan = new Button("Opslaan");
        opslaan.setOnAction(e -> opslaan());

        cancel = new Button("Annuleren");
        cancel.setOnAction(e -> stage.close());


        mailBox.setPadding(new Insets(20));
        mailBox.getStyleClass().add("hbox");
        mailBox.getChildren().addAll(bt_uitnodigingMail, bt_herrineringMail, bt_dankMail, bt_factuur);

        gridPane.addRow(0, lbl_mailAdres, tf_mailAdres);
        gridPane.addRow(1, lbl_wachtwoord, tf_wachtwoord);

        buttons.setPadding(new Insets(20));
        buttons.getStyleClass().add("hbox");
        buttons.getChildren().addAll(opslaan, cancel);

        mainPane.setTop(gridPane);
        mainPane.setCenter(mailBox);
        mainPane.setBottom(buttons);
        stage.setTitle("Settings");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * slaat de email en wachtwoord velden op in het register via de controller
     */
    private void opslaan() {
        settingsController.opslaanLogin(tf_mailAdres.getText().toString(), tf_wachtwoord.getText().toString());
        stage.close();
    }
}
