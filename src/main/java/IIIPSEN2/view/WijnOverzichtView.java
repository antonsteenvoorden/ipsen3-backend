package IIIPSEN2.view;

import IIIPSEN2.control.WijnController;
import IIIPSEN2.control.WijnKeuzeController;
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
import IIIPSEN2.knopHandlers.TerugKnopHandler;
import IIIPSEN2.knopHandlers.WijnAanmakenHandler;
import IIIPSEN2.knopHandlers.WijnOverzichtHandler;
import IIIPSEN2.knoppen.TerugKnop;
import IIIPSEN2.knoppen.WijnBeherenKnop;
import IIIPSEN2.knoppen.WijnOverzichtKnop;
import IIIPSEN2.model.Wijn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Anton Steenvoorden,
 *         Sidney de Geus
 *         <p>
 *         WijnOverzichtView is een class dat zorgt voor een grafische weergaven van de wijnen in de
 *         database. Al deze wijnen worden in een tabel gezet voor weergaven.
 *         <p>
 *         Ook worden de opties weergeven om een wijn te bewerken, inactief te stellen of een bestand
 *         te importeren.
 */

public class WijnOverzichtView {


    // ATTRIBUTES

    private Stage stage;
    private Scene scene;
    private double height, width, scale;
    private HBox topBox, bottomBox, zoekBox;
    private BorderPane mainPane;
    private VBox centerBox;
    private TextField textField;
    private Label title;
    private ImageView icon;
    private Button zoek, terug, home, toevoegen, bewerken, inActiefStellen, actieveLijst, importWijnen;

    private TableView<Wijn> wijnOverzicht;
    private WijnController wijnController;


    //private ObservableList<Wijn> actieveWijnLijst;
    //private ObservableList<Wijn> inActieveWijnLijst;
    private ObservableList<Wijn> wijnLijst;
    boolean activeList;

    // CONSTRUCTOR

    /*
     * 2 constructors. Een met een wijnController en 1 met een boolean.
     * Standaard staat de actieve lijst op true. Indien dit niet zo is
     * en in de constructor meegegeven wordt dan wordt de inactieve lijst weegeven.
     */
    public WijnOverzichtView(WijnController wijnController) {
        init();
        this.wijnController = wijnController;
        this.activeList = true;
        display();
    }

    public WijnOverzichtView(WijnController wijnController, boolean activeList) {
        init();
        this.wijnController = wijnController;
        this.activeList = activeList;
        display();
    }


    //
    private void init() {
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
    }


    //
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
        textField.setPromptText("Voer een wijn naam in");
        textField.setMinWidth(200);
        textField.setFocusTraversable(false);

        toevoegen = new Button("Toevoegen");
        toevoegen.setOnAction(e -> new WijnBeherenView(new WijnController()));

        bewerken = new Button("Bewerken");
        bewerken.setOnAction(e -> editAction());

        if (activeList) {
            inActiefStellen = new Button("In-actief stellen");
            actieveLijst = new Button("In-actieve wijnen");
            actieveLijst.setOnAction(e -> new WijnOverzichtView(wijnController, false));
        } else {
            inActiefStellen = new Button("Aactief stellen");
            actieveLijst = new Button("Actieve wijnen");
            actieveLijst.setOnAction(e -> new WijnOverzichtView(wijnController, true));
        }

        inActiefStellen.setOnAction(e -> inActiefStellenAction());

        //refresh = new Button("Verversen");
        //refresh.setOnAction(e -> new WijnOverzichtView(new WijnController()));

        importWijnen = new Button("Importeren");
        importWijnen.setOnAction(e -> wijnController.importWijnen());

        terug = new Button("terug");
        terug.setMinWidth(50);
        terug.setOnAction(e -> {
            List<Knop> temp = new ArrayList<Knop>();
            temp.add(new WijnBeherenKnop((WijnAanmakenHandler) new WijnKeuzeController()));
            temp.add(new WijnOverzichtKnop((WijnOverzichtHandler) new WijnKeuzeController()));
            temp.add(new TerugKnop((TerugKnopHandler) new WijnKeuzeController()));
            new KeuzeView(temp);
        });

        home = new Button("home");
        home.setOnAction(e -> new HomeView());

        ////////// CREATING THE COLUMNS
        TableColumn<Wijn, Integer> wijnSerieIDColumn = new TableColumn<>("ID");
        wijnSerieIDColumn.setPrefWidth(30);
        wijnSerieIDColumn.setCellValueFactory(new PropertyValueFactory<>("wijnSerieID"));

        TableColumn<Wijn, String> wijnNaamColumn = new TableColumn<>("Wijn naam");
        wijnNaamColumn.setPrefWidth(200);
        wijnNaamColumn.setCellValueFactory(new PropertyValueFactory<>("wijnNaam"));

        TableColumn<Wijn, String> wijnTypeColumn = new TableColumn<>("Soort");
        wijnTypeColumn.setPrefWidth(75);
        wijnTypeColumn.setCellValueFactory(new PropertyValueFactory<>("stringWijnType"));

        /*
        TableColumn<Wijn, String> wijnMerkColumn = new TableColumn<>("Merk");
        wijnMerkColumn.setPrefWidth(150);
        wijnMerkColumn.setCellValueFactory(new PropertyValueFactory<>("wijnMerk"));
		*/

        TableColumn<Wijn, String> wijnCategoryColumn = new TableColumn<>("Categorie");
        wijnCategoryColumn.setPrefWidth(150);
        wijnCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("wijnCategory"));

        TableColumn<Wijn, String> wijnAfkomstColumn = new TableColumn<>("Afkomst");
        wijnAfkomstColumn.setPrefWidth(200);
        wijnAfkomstColumn.setCellValueFactory(new PropertyValueFactory<>("wijnAfkomst"));

        TableColumn<Wijn, Integer> wijnJaartalColumn = new TableColumn<>("Jaartal");
        wijnJaartalColumn.setPrefWidth(75);
        wijnJaartalColumn.setCellValueFactory(new PropertyValueFactory<>("wijnJaartal"));

        TableColumn<Wijn, Double> inkoopPrijsColumn = new TableColumn<>("Inkoop prijs");
        inkoopPrijsColumn.setPrefWidth(75);
        inkoopPrijsColumn.setCellValueFactory(new PropertyValueFactory<>("inkoopPrijs"));

        TableColumn<Wijn, Double> prijsColumn = new TableColumn<>("Prijs");
        prijsColumn.setPrefWidth(75);
        prijsColumn.setCellValueFactory(new PropertyValueFactory<>("prijs"));


        ///////////////// ADDING TO TABLEVIEW

        /*
        actieveWijnLijst = FXCollections.observableArrayList(wijnController.getActieveWijnen());
        inActieveWijnLijst = FXCollections.observableArrayList(wijnController.getInactieveWijnen());
         */
        if (activeList) {
            wijnLijst = FXCollections.observableArrayList(wijnController.getActieveWijnen());
        } else {
            wijnLijst = FXCollections.observableArrayList(wijnController.getInactieveWijnen());
        }

        wijnOverzicht = new TableView<Wijn>();
        wijnOverzicht.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        wijnOverzicht.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        wijnOverzicht.setItems(searchFilter(wijnLijst, wijnOverzicht));
        wijnOverzicht.getColumns().addAll(
                wijnSerieIDColumn,
                wijnNaamColumn,
                wijnTypeColumn,
                wijnCategoryColumn,
                wijnAfkomstColumn,
                wijnJaartalColumn,
                prijsColumn
        );

        //add elements to panes
        topBox.getChildren().addAll(icon, title);
        topBox.setAlignment(Pos.CENTER);

        zoekBox.getStyleClass().add("hbox");
        zoekBox.getChildren().addAll(textField, toevoegen, bewerken, inActiefStellen, actieveLijst, importWijnen);

        centerBox.getChildren().addAll(zoekBox, wijnOverzicht);
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


    //
    public void editAction() {
        if (wijnOverzicht.getSelectionModel().getSelectedItems().size() > 1) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Er kan maar een wijn tegelijk worden bewerkt !");
            alert.showAndWait();
        }
        if (wijnOverzicht.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Er is geen wijn geselecteerd ! ");
            alert.showAndWait();
        }
        if (wijnOverzicht.getSelectionModel().getSelectedItems().size() == 1) {
            new WijnBeherenView(wijnOverzicht.getSelectionModel().getSelectedItem(), wijnController);
        }
    }


    //
    private SortedList<Wijn> searchFilter(ObservableList<Wijn> wijnLijst, TableView<Wijn> wijnOverzicht) {
        FilteredList<Wijn> filterData = new FilteredList<Wijn>(wijnLijst);

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(wijn -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (wijn.getWijnNaam().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Wijn> sortedData = new SortedList<Wijn>(filterData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(wijnOverzicht.comparatorProperty());

        return sortedData;
    }


    /**
     * inActiefStellenAction() zet de geselecteerde wijnen op inactief (of actief)
     */
    public void inActiefStellenAction() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Weet u het zeker ?");
        alert.setHeaderText("U staat op het punt een of meerdere wijnen in-actief of actief te stellen.");
        alert.setContentText("Weet u het zeker ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (!wijnOverzicht.getSelectionModel().isEmpty() && activeList) {
                wijnController.deActivateWijnen(wijnOverzicht.getSelectionModel().getSelectedItems());
            } else if (!wijnOverzicht.getSelectionModel().isEmpty() && !activeList) {
                wijnController.activateWijnen(wijnOverzicht.getSelectionModel().getSelectedItems());
            }
        } else {
            return;
        }
    }
}
