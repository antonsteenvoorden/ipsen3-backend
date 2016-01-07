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
public class UitnodigingInstellingenView {
    private Stage stage;
    private Scene scene;
    private double height, width, scale;
    private BorderPane mainPane;
    private GridPane gridPane;
    private HBox buttons;

    private Label lbl_onderwerp, lbl_aanhef, lbl_body;
    private TextField tf_onderwerp, tf_aanhef;
    private TextArea body;
    private Button opslaan, cancel;
    private Preferences prefs;
    private SettingsController settingsController;

    public UitnodigingInstellingenView(SettingsController settingsController) {
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
        lbl_onderwerp = new Label("Onderwerp :");
        lbl_onderwerp.setId("form-label");
        lbl_onderwerp.setTextFill((Color.valueOf("#FFFC00")));

        lbl_aanhef = new Label("Aanhef :");
        lbl_aanhef.setId("form-label");
        lbl_aanhef.setTextFill((Color.valueOf("#FFFC00")));

        lbl_body = new Label("Bericht: ");
        lbl_body.setId("form-label");
        lbl_body.setTextFill((Color.valueOf("#FFFC00")));

        tf_onderwerp = new TextField();
        tf_onderwerp.setPromptText("Onderwerp");
        tf_aanhef = new TextField();
        tf_aanhef.setPromptText("bijv: Geachte heer/mevrouw");
        body = new TextArea();
        body.setPromptText("bijvoorbeeld: Hierbij bent u uitgenodigd voor het wijnfestijn 2015! ");

        tf_aanhef.setText(prefs.get("uitnodigingMail_aanhef", ""));
        tf_onderwerp.setText(prefs.get("uitnodigingMail_onderwerp", ""));
        body.setText(prefs.get("uitnodigingMail_body", ""));
        opslaan = new Button("Opslaan");
        opslaan.setOnAction(e -> opslaan());

        cancel = new Button("Annuleren");
        cancel.setOnAction(e -> stage.close());

        gridPane.addRow(0, lbl_onderwerp, tf_onderwerp);
        gridPane.addRow(1, lbl_aanhef, tf_aanhef);
        gridPane.addRow(2, lbl_body);

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
        settingsController.saveUitnodigingMail(tf_onderwerp.getText(), tf_aanhef.getText().toString(), body.getText()
                .toString());
        stage.close();
    }

}
