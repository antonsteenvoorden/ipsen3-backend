package IIIPSEN2.view;

import IIIPSEN2.control.KlantController;
import IIIPSEN2.control.LijstenKeuzeController;
import IIIPSEN2.interfaces.BestelkaartenHandler;
import IIIPSEN2.interfaces.Knop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import IIIPSEN2.knopHandlers.GroothandelBestellingHandler;
import IIIPSEN2.knopHandlers.TerugKnopHandler;
import IIIPSEN2.knoppen.BestelkaartenKnop;
import IIIPSEN2.knoppen.DebiteurenLijstKnop;
import IIIPSEN2.knoppen.GroothandelBestellingKnop;
import IIIPSEN2.knoppen.TerugKnop;
import IIIPSEN2.model.BestelkaartGenerator;
import IIIPSEN2.model.Klant;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jordan Munk
 *         De IIIPSEN2.view voor bestelkaart. Alle gasten staan in een tableview. De gasten kunnen geselecteerd worden en er
 *         kan dan
 *         een bestellijst gegenereerd worden
 */
public class BestelkaartView {
    PDDocument doc;
    PDPage page;
    PDPageContentStream content;
    private Stage stage;
    private Scene scene;
    private double height, width, scale;
    private HBox topBox, bottomBox, zoekBox;
    private BorderPane mainPane;
    private VBox centerBox;
    private TextField textField;
    private Label title;
    private ImageView icon;
    private Button terug, home, selecteren, zoeken, selecteren1;
    private KlantController klantController;
    private TableView<Klant> klantOverzicht;
    // private ObservableList klanten;

    public BestelkaartView(KlantController klantController) {
        this.klantController = klantController;
        stage = Main.getInstance().mainStage;
        height = Main.getInstance().height;
        // scale = Main.getInstance().scale;
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
        title = new Label("Lions-club Oegstgeest/Warmond");
        title.setId("title");
        title.setTextFill(Color.web("#FFCF03"));
        title.autosize();

        icon = new ImageView(new Image("/IIIPSEN2/images/icon.png"));
        icon.setPreserveRatio(true);
        icon.autosize();

        textField = new TextField();
        textField.getStyleClass().add("search");
        textField.setMinWidth(200);

        zoeken = new Button("Zoeken");

        selecteren = new Button("Exporteer bestellijst voor geselecteerde gasten");
        selecteren.setOnAction(e -> exporteer());

        terug = new Button("Terug");
        terug.setMinWidth(50);
        terug.setOnAction(e -> {
            List<Knop> temp = new ArrayList<Knop>();
            temp.add(new GroothandelBestellingKnop((GroothandelBestellingHandler) new LijstenKeuzeController()));
            temp.add(new BestelkaartenKnop((BestelkaartenHandler) new LijstenKeuzeController()));
            temp.add(new DebiteurenLijstKnop(new LijstenKeuzeController()));
            temp.add(new TerugKnop((TerugKnopHandler) new LijstenKeuzeController()));
            new KeuzeView(temp);
        });

        home = new Button("Home");
        home.setOnAction(e -> new HomeView());

        ////////// CREATING THE COLUMNS
        // id, voornaam,
        ////////// tus,achter,straat,huis,toev,post,toev,plaats,tel,mail,not,is
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


        // id, voornaam,
        // tus,achter,straat,huis,toev,post,toev,plaats,tel,mail,not,is

        ///////////////// ADDING TO TABLEVIEW

        klantOverzicht = new TableView<>();
        klantOverzicht.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        klantOverzicht.getColumns().addAll(voornaamColumn, tussenvoegselColumn, achternaamColumn, straatnaamColumn,
                huisNummerColumn, huisNummerToevoegingColumn, postcodeColumn, postcodeToevoegingColumn,
                woonplaatsColumn, telefoonColumn, emailColumn);

        ObservableList<Klant> klanten = FXCollections.observableArrayList(klantController.getActieveKlanten());
        klantOverzicht.setItems(klanten);

        // add elements to panes
        topBox.getChildren().addAll(icon, title);
        topBox.setAlignment(Pos.CENTER);

        zoekBox.getStyleClass().add("hbox");
        zoekBox.getChildren().addAll(selecteren);

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
     * Dit is voor de knop exporteer als pdf. Een pdf wordt gegenereerd in een loop. Als er meerdere
     * gasten zijn geselecteerd zal het in 1 PDF worden opgeslagen i.p.v. allemaal verschillende bestanden
     */

    public void exporteer() {
        //Maak pdf document aan
        doc = new PDDocument();

        for (Klant e : klantOverzicht.getSelectionModel().getSelectedItems()) {
            BestelkaartGenerator gen = new BestelkaartGenerator(e, doc);
        }

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Bestellijsten opslaan");
            fileChooser.setInitialFileName(
                    "Bestellijsten"
            );
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PDF File", "*.pdf")
            );
            File file = fileChooser.showSaveDialog(new Stage());

            doc.save(file);
            doc.close();
        } catch (COSVisitorException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Bestellijsten exporteren");
        alert.setContentText("Bestellijst is succesvol geexporteerd!");

        alert.showAndWait();


    }
}
