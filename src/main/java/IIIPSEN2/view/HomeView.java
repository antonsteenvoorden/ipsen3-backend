package IIIPSEN2.view;

import IIIPSEN2.control.*;
import IIIPSEN2.interfaces.BestelkaartenHandler;
import IIIPSEN2.interfaces.Knop;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import IIIPSEN2.knopHandlers.*;
import IIIPSEN2.knoppen.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Steenvoorden
 * @author dennis
 */
public class HomeView {
    private Stage stage;
    private Scene scene;
    private double height, width, scale;
    private HBox topBox, centerBox, bottomBox;
    private BorderPane mainPane;
    private Button klanten, wijnen, bestellingen, mailen, lijsten, settings, about;

    private Label title;
    private ImageView icon;

    public HomeView() {
        stage = Main.getInstance().mainStage;
        height = Main.getInstance().height;
        //scale = Main.getInstance().scale;
        width = Main.getInstance().width;

        //instantiate pane and scene
        mainPane = new BorderPane();
        topBox = new HBox();
        centerBox = new HBox();
        centerBox.getStyleClass().add("hbox");
        bottomBox = new HBox();
        bottomBox.getStyleClass().add("hbox");
        bottomBox.setAlignment(Pos.CENTER_RIGHT);

        scene = new Scene(mainPane);

        scene.getStylesheets().clear();
        scene.getStylesheets().add("/IIIPSEN2/view/style.css");

        display();
    }

    public void display() {
        //init buttons
        klanten = new Button("Gasten");
        wijnen = new Button("Wijnen");
        bestellingen = new Button("Bestellingen");
        mailen = new Button("Mailen");
        lijsten = new Button("Lijsten");

        about = new Button("About");
        about.setOnAction(e -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText("Gemaakt door studenten van de Hogeschool Leiden");
            alert.setContentText("Anton Steenvoorden" +
                    "\nDennis van Bohemen\nJordan Munk\nRoger Bosman\nSidney de Geus");

            alert.showAndWait();
        });
        about.setAlignment(Pos.CENTER_LEFT);
        settings = new Button("");
        settings.setOnAction(e -> new SettingsView());
        settings.setGraphic(new ImageView(new Image("/IIIPSEN2/images/settings.png")));

        //set button size
        createButton(klanten);
        createButton(wijnen);
        createButton(bestellingen);
        createButton(mailen);
        createButton(lijsten);

        //set on action buttons
        //klanten.setOnAction(e -> new KeuzeMenu(new KlantenKeuze(), "Klant aanmaken", "Klant overzicht", "Terug"));
        klanten.setOnAction(e -> {
            List<Knop> temp = new ArrayList<Knop>();
            temp.add(new KlantBeherenKnop((KlantAanmakenHandler) new KlantKeuzeController()));
            temp.add(new KlantOverzichtKnop((KlantOverzichtHandler) new KlantKeuzeController()));
            temp.add(new TerugKnop((TerugKnopHandler) new KlantKeuzeController()));
            new KeuzeView(temp);
        });


        //wijnen.setOnAction(e ->  new KeuzeMenu(new WijnenKeuze(), "Wijnen beheren", "Wijnen overzicht", "Terug"));
        wijnen.setOnAction(e -> {
            List<Knop> temp = new ArrayList<Knop>();
            temp.add(new WijnBeherenKnop((WijnAanmakenHandler) new WijnKeuzeController()));
            temp.add(new WijnOverzichtKnop((WijnOverzichtHandler) new WijnKeuzeController()));
            temp.add(new TerugKnop((TerugKnopHandler) new WijnKeuzeController()));
            new KeuzeView(temp);
        });

        //bestellingen.setOnAction(e->  new KeuzeMenu(new BestellingKeuze(), "Bestelling plaatsen", "Bestelling
        // overzicht", "Terug"));
        bestellingen.setOnAction(e -> {
            List<Knop> temp = new ArrayList<Knop>();
            temp.add(new BestellingPlaatsenKnop((BestellingPlaatsenHandler) new BestellingKeuzeController()));
            temp.add(new BestellingOverzichtKnop((BestellingOverzichtHandler) new BestellingKeuzeController()));
            temp.add(new TerugKnop((TerugKnopHandler) new BestellingKeuzeController()));
            new KeuzeView(temp);
        });

        //genereren.setOnAction(e -> new KeuzeMenu(new GenererenKeuze(), "Mailen", "Lijsten","Terug"));
        mailen.setOnAction(e -> {
            List<Knop> temp = new ArrayList<Knop>();
            temp.add(new UitnodigingenKnop((UitnodigingenHandler) new MailKeuzeController(new OrderController(), new
                    KlantController(), new FactuurController())));
            temp.add(new HerinneringenKnop((HerinneringenKnopHandler) new MailKeuzeController(new OrderController(),
                    new KlantController(), new FactuurController())));
            temp.add(new MailFacturenKnop((FacturenKnopHandler) new MailKeuzeController(new OrderController(), new
                    KlantController(), new FactuurController())));
            temp.add(new TerugKnop((TerugKnopHandler) new MailKeuzeController(new OrderController(), new
                    KlantController(), new FactuurController())));
            new KeuzeView(temp);
        });

        lijsten.setOnAction(e -> {
            List<Knop> temp = new ArrayList<Knop>();
            temp.add(new GroothandelBestellingKnop((GroothandelBestellingHandler) new LijstenKeuzeController()));
            temp.add(new BestelkaartenKnop((BestelkaartenHandler) new LijstenKeuzeController()));
            temp.add(new DebiteurenLijstKnop(new LijstenKeuzeController()));
            temp.add(new TerugKnop((TerugKnopHandler) new LijstenKeuzeController()));
            new KeuzeView(temp);
        });


        //topbox items
        title = new Label("Lions-club Oegstgeest/Warmond");
        title.setId("title");
        title.setTextFill(Color.web("#FFCF03"));
        title.autosize();

        icon = new ImageView(new Image("/IIIPSEN2/images/icon.png"));
        icon.setPreserveRatio(true);
        icon.autosize();


        //add elements to panes
        topBox.getChildren().addAll(icon, title);
        topBox.setAlignment(Pos.CENTER);

        centerBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(klanten, wijnen, bestellingen, mailen, lijsten);
        bottomBox.getChildren().addAll(about, settings);
        mainPane.setTop(topBox);
        mainPane.setCenter(centerBox);
        mainPane.setBottom(bottomBox);

        stage.setScene(scene);
        stage.setHeight(height);
        stage.show();
    }

    /**
     * set alle buttons met de pref size en font size
     *
     * @param b
     */
    public void createButton(Button b) {
        b.setPrefSize(200, 140);
        b.setFont(Font.font(0.018 * stage.widthProperty().doubleValue()));
    }

}