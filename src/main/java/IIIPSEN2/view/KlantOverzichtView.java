package IIIPSEN2.view;

import IIIPSEN2.control.BestellingController;
import IIIPSEN2.control.KlantController;
import IIIPSEN2.control.KlantKeuzeController;
import IIIPSEN2.interfaces.Knop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.stage.Stage;
import IIIPSEN2.knopHandlers.KlantAanmakenHandler;
import IIIPSEN2.knopHandlers.KlantOverzichtHandler;
import IIIPSEN2.knopHandlers.TerugKnopHandler;
import IIIPSEN2.knoppen.KlantBeherenKnop;
import IIIPSEN2.knoppen.KlantOverzichtKnop;
import IIIPSEN2.knoppen.TerugKnop;
import IIIPSEN2.model.Klant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Anton Steenvoorden
 *         Venster met daarin alle klanten in een tableview
 */
public class KlantOverzichtView {
    boolean activeList;
    private Stage stage;
    private Scene scene;
    private double height, width, scale;
    private HBox topBox, bottomBox, zoekBox;
    private BorderPane mainPane;
    private VBox centerBox;
    private TextField textField;
    private Label title;
    private ImageView icon;
    private Button terug, home, toevoegen, bewerken, inActiefStellen, bestellen, actieveLijst, refresh, importCsvXls;
    private KlantController klantController;
    private TableView<Klant> klantOverzicht;
    private ObservableList<Klant> actieveGasten;
    private ObservableList<Klant> inActieveGasten;

    public KlantOverzichtView(KlantController klantController) {
        stage = Main.getInstance().mainStage;
        height = Main.getInstance().height;
        //scale = Main.getInstance().scale;
        width = Main.getInstance().width;

        this.klantController = klantController;

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
        textField.setPromptText("Voer een voor of achternaam in");
        textField.setMinWidth(200);
        textField.setFocusTraversable(false);

        toevoegen = new Button("Toevoegen");
        toevoegen.setOnAction(e -> new KlantBeherenView(new KlantController()));

        bewerken = new Button("Bewerken");
        bewerken.setOnAction(e -> editAction());

        inActiefStellen = new Button("In-actief stellen");
        inActiefStellen.setOnAction(e -> inActiefStellenAction());

        bestellen = new Button("Bestellen");
        bestellen.setOnAction(e -> bestellenAction());

        actieveLijst = new Button("In-actieve gasten");
        actieveLijst.setOnAction(e -> actieveLijstAction());

        refresh = new Button("Verversen");
        refresh.setOnAction(e -> new KlantOverzichtView(new KlantController()));

        importCsvXls = new Button("Importeer");
        importCsvXls.setOnAction(e -> klantController.importKlanten());

        terug = new Button("terug");
        terug.setMinWidth(50);
        terug.setOnAction(e -> {
            List<Knop> temp = new ArrayList<Knop>();
            temp.add(new KlantBeherenKnop((KlantAanmakenHandler) new KlantKeuzeController()));
            temp.add(new KlantOverzichtKnop((KlantOverzichtHandler) new KlantKeuzeController()));
            temp.add(new TerugKnop((TerugKnopHandler) new KlantKeuzeController()));
            new KeuzeView(temp);
        });

        home = new Button("home");
        home.setOnAction(e -> new HomeView());
        createTableView();

        //add elements to panes
        topBox.getChildren().addAll(icon, title);
        topBox.setAlignment(Pos.CENTER);

        zoekBox.getStyleClass().add("hbox");
        zoekBox.getChildren().addAll(textField, toevoegen, bewerken, bestellen, inActiefStellen, actieveLijst,
                importCsvXls);

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
     * zoekfunctie van de tableview, zoekt op voor en achternaam
     *
     * @param klantLijst
     * @param klantOverzicht
     * @return SortedList<Klant>
     */
    private SortedList<Klant> searchFilter(ObservableList<Klant> klantLijst, TableView<Klant> klantOverzicht) {
        FilteredList<Klant> filterData = new FilteredList<Klant>(klantLijst);

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(klant -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (klant.getAchternaam() != null) {
                    if (klant.getAchternaam().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                if (klant.getVoornaam() != null) {
                    if (klant.getVoornaam().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Klant> sortedData = new SortedList<Klant>(filterData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(klantOverzicht.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        return sortedData;

    }

    /**
     * stelt de tableview op
     */
    private void createTableView() {
        //////////CREATING THE COLUMNS
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

        TableColumn<Klant, String> gastLidColumn = new TableColumn<>("Gast/Lid");
        gastLidColumn.setPrefWidth(30);
        gastLidColumn.setCellValueFactory(new PropertyValueFactory<>("gastLid"));

        TableColumn<Klant, String> notitieColumn = new TableColumn<>("Notitie");
        notitieColumn.setPrefWidth(100);
        notitieColumn.setCellValueFactory(new PropertyValueFactory<>("notitie"));

        TableColumn<Klant, Integer> isActiefColumn = new TableColumn<>("Actief");
        isActiefColumn.setPrefWidth(30);
        isActiefColumn.setCellValueFactory(new PropertyValueFactory<>("isActief"));

        TableColumn<Klant, String> dateColumn = new TableColumn<>("Datum");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        //id, voornaam, tus,achter,straat,huis,toev,post,toev,plaats,tel,mail,not,is

        ///////////////// ADDING TO TABLEVIEW
        actieveGasten = FXCollections.observableArrayList(klantController.getActieveKlanten());
        inActieveGasten = FXCollections.observableArrayList(klantController.getInactieveKlanten());

        klantOverzicht = new TableView<>();
        klantOverzicht.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        klantOverzicht.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        klantOverzicht.setItems(searchFilter(actieveGasten, klantOverzicht));
        klantOverzicht.getColumns().addAll(voornaamColumn, tussenvoegselColumn,
                achternaamColumn, straatnaamColumn, huisNummerColumn, huisNummerToevoegingColumn, postcodeColumn,
                postcodeToevoegingColumn, woonplaatsColumn,
                telefoonColumn, emailColumn, gastLidColumn, notitieColumn, dateColumn, isActiefColumn);

    }

    /**
     * wanneer er op bewerken wordt gedrukt wordt er gecontroleerd of er maar 1 gast geselecteerd is
     */
    public void editAction() {
        if (klantOverzicht.getSelectionModel().getSelectedItems().size() > 1) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Er kan maar een klant tegelijk worden bewerkt !");
            alert.showAndWait();
        }
        if (klantOverzicht.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Er is geen klant geselecteerd ! ");
            alert.showAndWait();
        }
        if (klantOverzicht.getSelectionModel().getSelectedItems().size() == 1) {
            new KlantBeherenView(klantOverzicht.getSelectionModel().getSelectedItem(), new KlantController());
        }
    }

    /**
     * Verandert de status van de geselecteerde gasten en maakt opnieuw het venster aan zodat het de vernieuwde
     * status krijgt
     */
    public void inActiefStellenAction() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Weet u het zeker ?");
        alert.setHeaderText("U staat op het punt een of meerdere gasten in-actief of actief te stellen.");
        alert.setContentText("Weet u het zeker ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (!klantOverzicht.getSelectionModel().isEmpty() && !activeList) {
                klantController.deActivateKlanten(klantOverzicht.getSelectionModel().getSelectedItems());
                refresh();
            } else if (!klantOverzicht.getSelectionModel().isEmpty() && activeList) {
                klantController.activateKlanten(klantOverzicht.getSelectionModel().getSelectedItems());
                refresh();
            }
        } else {
            return;
        }
    }

    /**
     * maakt een bestellingbeherenview aan met de geselecteerde gast
     */
    public void bestellenAction() {
        if (!klantOverzicht.getSelectionModel().isEmpty() && klantOverzicht.getSelectionModel().getSelectedItems()
                .size() == 1) {
            new BestellingBeherenView(new BestellingController(), klantOverzicht.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * wisselt tussen actief en inactieve gasten weergave.
     */
    public void actieveLijstAction() {
        if (activeList) {
            klantOverzicht.setItems(searchFilter(actieveGasten, klantOverzicht));
            activeList = false;
            actieveLijst.setText("In-actieve gasten");
            inActiefStellen.setText("In-actief stellen");
        } else if (!activeList) {
            klantOverzicht.setItems(searchFilter(inActieveGasten, klantOverzicht));
            activeList = true;
            actieveLijst.setText("Actieve gasten");
            inActiefStellen.setText("Actief stellen");
        }
    }

    /**
     * ververst het scherm
     */
    private void refresh() {
        new KlantOverzichtView(klantController);
    }
}
