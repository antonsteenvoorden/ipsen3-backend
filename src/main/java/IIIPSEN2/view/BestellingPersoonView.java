// voor wie is de order ??
// button -> new BestellingBeherenView(klant);
package IIIPSEN2.view;

import IIIPSEN2.control.BestellingController;
import IIIPSEN2.control.BestellingKeuzeController;
import IIIPSEN2.control.KlantController;
import IIIPSEN2.interfaces.Knop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import IIIPSEN2.knopHandlers.BestellingOverzichtHandler;
import IIIPSEN2.knopHandlers.BestellingPlaatsenHandler;
import IIIPSEN2.knopHandlers.TerugKnopHandler;
import IIIPSEN2.knoppen.BestellingOverzichtKnop;
import IIIPSEN2.knoppen.BestellingPlaatsenKnop;
import IIIPSEN2.knoppen.TerugKnop;
import IIIPSEN2.model.Klant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Steenvoorden
 *         In dit scherm kun je een gast selecteren voor wie de bestelling is
 *         Na het drukken van enter of op zoeken wordt een SQL query uitgevoerd met een wildcard en het ingetikte
 *         In de constructor van de klasse wordt display aangeroepen, die zet alle properties en wijzigt de scene.
 */
public class BestellingPersoonView {
    private Stage stage;
    private Scene scene;
    private double height, width, scale;
    private HBox topBox, bottomBox, zoekBox;
    private BorderPane mainPane;
    private VBox centerBox;
    private TextField textField;
    private Label title;
    private ImageView icon;
    private Button terug, home, selecteren, zoeken;

    private KlantController klantController;
    private TableView<Klant> klantOverzicht;

    public BestellingPersoonView(KlantController klantController) {
        this.klantController = klantController;
        stage = Main.getInstance().mainStage;
        height = Main.getInstance().height;
        //scale = Main.getInstance().scale;
        width = Main.getInstance().width;

        mainPane = new BorderPane();
        topBox = new HBox();
        centerBox = new VBox();
        centerBox.getStyleClass().add("hbox");
        bottomBox = new HBox();

        bottomBox.getStyleClass().add("hbox");
        bottomBox.setAlignment(Pos.CENTER);

        zoekBox = new HBox();
        zoekBox.setAlignment(Pos.CENTER);
        scene = new Scene(mainPane);

        scene.getStylesheets().clear();
        scene.getStylesheets().add("/IIIPSEN2/view/style.css");

        display();
    }

    public void display() {
        title = new Label("Voor wie is de bestelling ?");
        title.setId("title");
        title.setTextFill(Color.web("#FFCF03"));
        title.autosize();

        icon = new ImageView(new Image("/IIIPSEN2/images/icon.png"));
        icon.setPreserveRatio(true);
        icon.autosize();

        textField = new TextField();
        textField.getStyleClass().add("search");
        textField.setOnAction(e -> getKlantByString());
        textField.setMinWidth(200);

        zoeken = new Button("Zoeken");
        zoeken.setOnAction(e -> getKlantByString());
        zoeken.setFocusTraversable(false);

        selecteren = new Button("Selecteer deze gast");
        selecteren.setFocusTraversable(false);
        selecteren.setOnAction(e -> selecteren());

        terug = new Button("terug");
        terug.setMinWidth(50);
        terug.setOnAction(e -> {
            List<Knop> temp = new ArrayList<Knop>();
            temp.add(new BestellingPlaatsenKnop((BestellingPlaatsenHandler) new BestellingKeuzeController()));
            temp.add(new BestellingOverzichtKnop((BestellingOverzichtHandler) new BestellingKeuzeController()));
            temp.add(new TerugKnop((TerugKnopHandler) new BestellingKeuzeController()));
            new KeuzeView(temp);
        });

        home = new Button("home");
        home.setOnAction(e -> new HomeView());

        ////////// CREATING THE COLUMNS
        //id, voornaam, tus,achter,straat,huis,toev,post,toev,plaats,tel,mail,not,is
        TableColumn<Klant, String> voornaamColumn = new TableColumn<>("Voornaam");
        voornaamColumn.setPrefWidth(100);
        voornaamColumn.setCellValueFactory(new PropertyValueFactory<>("voornaam"));

        TableColumn<Klant, String> tussenvoegselColumn = new TableColumn<>("Tussenvoegsel");
        tussenvoegselColumn.setPrefWidth(40);
        tussenvoegselColumn.setCellValueFactory(new PropertyValueFactory<>("tussenvoegsel"));

        TableColumn<Klant, String> achternaamColumn = new TableColumn<>("Achternaam");
        achternaamColumn.setPrefWidth(150);
        achternaamColumn.setCellValueFactory(new PropertyValueFactory<>("achternaam"));

        TableColumn<Klant, String> straatnaamColumn = new TableColumn<>("Straat");
        straatnaamColumn.setPrefWidth(150);
        straatnaamColumn.setCellValueFactory(new PropertyValueFactory<>("straatnaam"));

        TableColumn<Klant, Integer> huisNummerColumn = new TableColumn<>("Huisnr");
        huisNummerColumn.setPrefWidth(20);
        huisNummerColumn.setCellValueFactory(new PropertyValueFactory<>("huisNummer"));

        TableColumn<Klant, Integer> huisNummerToevoegingColumn = new TableColumn<>("Toevoeging");
        huisNummerToevoegingColumn.setPrefWidth(5);
        huisNummerToevoegingColumn.setCellValueFactory(new PropertyValueFactory<>("huisNummerToevoeging"));


        TableColumn<Klant, String> postcodeColumn = new TableColumn<>("Postcode");
        postcodeColumn.setPrefWidth(50);
        postcodeColumn.setCellValueFactory(new PropertyValueFactory<>("postcode"));

        TableColumn<Klant, String> postcodeToevoegingColumn = new TableColumn<>("Postcode toevoeging");
        postcodeToevoegingColumn.setPrefWidth(40);
        postcodeToevoegingColumn.setCellValueFactory(new PropertyValueFactory<>("postcodeToevoeging"));

        TableColumn<Klant, String> woonplaatsColumn = new TableColumn<>("Woonplaats");
        woonplaatsColumn.setPrefWidth(40);
        woonplaatsColumn.setCellValueFactory(new PropertyValueFactory<>("plaatsNaam"));

        TableColumn<Klant, String> telefoonColumn = new TableColumn<>("Telefoon nr");
        telefoonColumn.setPrefWidth(60);
        telefoonColumn.setCellValueFactory(new PropertyValueFactory<>("telefoon"));

        TableColumn<Klant, String> emailColumn = new TableColumn<>("E-Mail");
        emailColumn.setPrefWidth(150);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Klant, String> notitieColumn = new TableColumn<>("Notitie");
        notitieColumn.setPrefWidth(100);
        notitieColumn.setCellValueFactory(new PropertyValueFactory<>("notitie"));

        //ADDING TO TABLEVIEW
        klantOverzicht = new TableView<>();
        klantOverzicht.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        klantOverzicht.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        klantOverzicht.getColumns().addAll(voornaamColumn, tussenvoegselColumn,
                achternaamColumn, straatnaamColumn, huisNummerColumn, huisNummerToevoegingColumn, postcodeColumn,
                postcodeToevoegingColumn, woonplaatsColumn,
                telefoonColumn, emailColumn, notitieColumn);

        klantOverzicht.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode().equals(KeyCode.ENTER)) {
                    selecteren();
                }
            }
        });

        //add elements to panes
        topBox.getChildren().addAll(icon, title);
        topBox.setAlignment(Pos.CENTER);

        zoekBox.getStyleClass().add("hbox");
        zoekBox.getChildren().addAll(textField, zoeken, selecteren);

        centerBox.getChildren().addAll(zoekBox, klantOverzicht);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(0, 0, 20, 0));
        bottomBox.getChildren().addAll(terug, home);


        mainPane.setTop(topBox);
        mainPane.setCenter(centerBox);
        mainPane.setBottom(bottomBox);

        stage.setScene(scene);
        stage.setHeight(height);
        stage.show();
    }

    /**
     * Geeft de geselecteerde gast mee aan een nieuw bestellingbeherenview
     */
    private void selecteren() {
        if (!klantOverzicht.getSelectionModel().isEmpty()) {
            new BestellingBeherenView(new BestellingController(), klantOverzicht.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * haalt de gasten uit de database op die een overeenkomst hebben(qua voornaam of achternaam) met het
     * ingevoerde.
     */
    private void getKlantByString() {
        ObservableList<Klant> klanten = FXCollections.observableArrayList(klantController.getKlantByString(textField
                .getText()));
        klantOverzicht.setItems(klanten);
    }
}
