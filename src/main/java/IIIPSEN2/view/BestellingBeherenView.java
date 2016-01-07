package IIIPSEN2.view;

import IIIPSEN2.control.BestellingController;
import IIIPSEN2.control.KlantController;
import IIIPSEN2.control.OrderController;
import IIIPSEN2.control.WijnController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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
import IIIPSEN2.model.Klant;
import IIIPSEN2.model.Order;
import IIIPSEN2.model.OrderRegel;
import IIIPSEN2.model.Wijn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Steenvoorden
 *         Klasse om bestellingen mee te plaatsen en te bewerken.
 *         Maakt gebruik van wijnen, een klant, en orderregels en order.
 */
public class BestellingBeherenView {
    private Stage stage;
    private Scene scene;

    private Button cancel, opslaan, add, remove;
    private VBox leftBox, rightBox;
    private HBox centerBox, bottomBox, topBox;
    private BorderPane mainPane;
    private ScrollPane scrollPane;

    private Label title, klantInfo, aantal;
    private TextField tf_aantal, zoekBox;
    private BestellingController bestellingController;
    private WijnController wijnController;
    private TableView<Wijn> wijnen;
    private ObservableList<Wijn> wijnenLijst;
    private ObservableList<OrderRegel> orderRegels = FXCollections.observableArrayList();
    private TableView<OrderRegel> orderTableView = new TableView<OrderRegel>();
    private ArrayList<OrderRegel> tmpRemoveOrderRegels;
    private ObservableList<OrderRegel> tmpAddOrderRegels;

    private ImageView icon;
    private Klant klant;
    private Order order;

    /**
     * Accepteert een order om bij het bewerken de velden in te vullen.
     *
     * @param bestellingController
     * @param order
     * @param klant
     */
    public BestellingBeherenView(BestellingController bestellingController, Order order, Klant klant) {
        this.bestellingController = bestellingController;
        this.order = order;
        this.klant = klant;
        init();
        display();
        changeForEdit();
    }

    public BestellingBeherenView(BestellingController bestellingController, Klant klant) {
        this.bestellingController = bestellingController;
        this.klant = klant;
        init();
        display();
    }

    private void init() {
        stage = Main.getInstance().mainStage;

        mainPane = new BorderPane();
        topBox = new HBox();
        topBox.setAlignment(Pos.CENTER);

        centerBox = new HBox();
        centerBox.getStyleClass().add("hbox");

        bottomBox = new HBox();
        bottomBox.getStyleClass().add("hbox");
        bottomBox.setPrefWidth(250);
        bottomBox.setAlignment(Pos.CENTER);

        rightBox = new VBox();
        rightBox.getStyleClass().add("form");
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setMinWidth(300);

        leftBox = new VBox();
        leftBox.setSpacing(10);
        leftBox.getStyleClass().add("form");
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setMinWidth(300);

        scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        scene = new Scene(scrollPane);
        scene.getStylesheets().add("/IIIPSEN2/view/style.css");

        wijnController = new WijnController();
    }

    private void display() {
        wijnenLijst = FXCollections.observableArrayList(wijnController.getActieveWijnen());
        wijnen = new TableView<Wijn>();

        zoekBox = new TextField();
        zoekBox.setPromptText("Voer een wijnnaam in :");
        setOrderTableView();

        aantal = new Label("Aantal dozen(6 flessen)");
        aantal.setId("form-label");

        aantal.setMinWidth(200);
        aantal.setTextFill((Color.valueOf("#FFFC00")));

        tf_aantal = new TextField("1");
        tf_aantal.setFocusTraversable(true);
        tf_aantal.setPrefWidth(50);
        tf_aantal.setOnAction(e -> addOrderRegel(wijnen.getSelectionModel().getSelectedItem(), null));

        add = new Button("Voeg regel toe");
        add.setFocusTraversable(false);
        add.setMinWidth(100);
        add.setOnAction(e -> addOrderRegel(wijnen.getSelectionModel().getSelectedItem(), null));

        klantInfo = new Label("Bestelling voor: " + klant.getVoornaam() + " " + klant.getAchternaam());
        klantInfo.setMinWidth(150);
        klantInfo.setId("form-label");
        klantInfo.setTextFill((Color.valueOf("#FFFC00")));


        //buttons
        cancel = new Button("Annuleren");
        cancel.setOnAction(e -> new HomeView());
        cancel.setFocusTraversable(false);

        opslaan = new Button("Bestelling plaatsen");
        opslaan.setOnAction(e -> {
            toevoegen();
        });
        opslaan.setFocusTraversable(false);

        remove = new Button("Verwijder");
        remove.setOnAction(e -> {
            orderRegels.remove(orderTableView.getSelectionModel().getSelectedItem());
        });
        remove.setFocusTraversable(false);
        setWijnTableView();

        setFilterList();

        //topbox items
        title = new Label("Lions-club Oegstgeest/Warmond");
        title.setId("title");
        title.setTextFill(Color.web("#FFCF03"));
        title.autosize();

        icon = new ImageView(new Image("/IIIPSEN2/images/icon.png"));
        icon.setPreserveRatio(true);
        icon.autosize();

        //add elements to panes
        leftBox.getChildren().addAll(zoekBox, wijnen, aantal, tf_aantal, add);
        rightBox.getChildren().addAll(klantInfo, orderTableView, remove);
        centerBox.getChildren().addAll(leftBox, rightBox);
        topBox.getChildren().addAll(icon, title);

        bottomBox.getChildren().addAll(cancel, opslaan);
        bottomBox.setLayoutY(stage.heightProperty().doubleValue() - 80);
        mainPane.setCenter(centerBox);
        mainPane.setTop(topBox);
        mainPane.setBottom(bottomBox);
        scrollPane.setContent(mainPane);
        stage.setScene(scene);
    }

    /**
     * Verandert een aantal dingen in de IIIPSEN2.view zodat alles gekoppeld staat aan het bewerken van de bestelling.
     */
    private void changeForEdit() {
        opslaan.setText("Opslaan");
        opslaan.setOnAction(e -> {
            opslaan();
        });

        tmpRemoveOrderRegels = new ArrayList<OrderRegel>();
        remove.setOnAction(e -> {
            OrderRegel tmp = orderTableView.getSelectionModel().getSelectedItem();
            tmpRemoveOrderRegels.add(tmp);
            orderRegels.remove(tmp);
        });

        tmpAddOrderRegels = FXCollections.observableArrayList();
        add.setOnAction(e -> addOrderRegel(wijnen.getSelectionModel().getSelectedItem(), tmpAddOrderRegels));
        tf_aantal.setOnAction(e -> addOrderRegel(wijnen.getSelectionModel().getSelectedItem(), tmpAddOrderRegels));

        orderRegels = FXCollections.observableArrayList(order.getOrderRegels(new OrderController()));
        orderTableView.setItems(orderRegels);

        wijnen.setFocusTraversable(false);
    }

    /**
     * vult de ordertableview
     */
    private void setOrderTableView() {
        //tableview testyooo
        TableColumn<OrderRegel, Integer> aantalColumn = new TableColumn<>("Aantal");
        aantalColumn.setPrefWidth(40);
        aantalColumn.setCellValueFactory(new PropertyValueFactory<>("aantal"));

        TableColumn<OrderRegel, String> wijnNaamColumn = new TableColumn<>("Wijn");
        wijnNaamColumn.setPrefWidth(150);
        wijnNaamColumn.setCellValueFactory(new PropertyValueFactory<>("wijnNaam"));
        wijnNaamColumn.setEditable(false);

        TableColumn<OrderRegel, Integer> wijnPrijsColumn = new TableColumn<>("Prijs");
        wijnPrijsColumn.setPrefWidth(40);
        wijnPrijsColumn.setCellValueFactory(new PropertyValueFactory<>("wijnPrijs"));
        wijnPrijsColumn.setEditable(false);

        TableColumn<OrderRegel, Integer> totaalColumn = new TableColumn<>("Totaal");
        totaalColumn.setPrefWidth(80);
        totaalColumn.setCellValueFactory(new PropertyValueFactory<>("totaalPrijs"));
        totaalColumn.setEditable(false);

        orderTableView.setItems(orderRegels);
        orderTableView.setFocusTraversable(false);
        orderTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        orderTableView.getColumns().addAll(aantalColumn, wijnNaamColumn, wijnPrijsColumn, totaalColumn);
        orderTableView.setMinWidth(200);
    }

    /**
     * vult de wijntableview
     */
    private void setWijnTableView() {
        //Wijn lijst
        TableColumn<Wijn, Integer> wijnIDColumn = new TableColumn<>("Id");
        wijnIDColumn.setPrefWidth(40);
        wijnIDColumn.setCellValueFactory(new PropertyValueFactory<>("wijnSerieID"));

        TableColumn<Wijn, String> wijnNaam = new TableColumn<>("Wijn");
        wijnNaam.setPrefWidth(150);
        wijnNaam.setCellValueFactory(new PropertyValueFactory<>("wijnNaam"));
        wijnNaam.setEditable(false);

        TableColumn<Wijn, Double> wijnPrijs = new TableColumn<>("Prijs");
        wijnPrijs.setPrefWidth(80);
        wijnPrijs.setCellValueFactory(new PropertyValueFactory<>("prijs"));
        wijnPrijs.setEditable(false);

        wijnen.getColumns().addAll(wijnIDColumn, wijnNaam, wijnPrijs);
        wijnen.setMinWidth(200);
        wijnen.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        wijnen.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        wijnen.setItems(wijnenLijst);
        wijnen.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode().equals(KeyCode.ENTER)) {
                    if (!wijnen.getSelectionModel().isEmpty())
                        addOrderRegel(wijnen.getSelectionModel().getSelectedItem(), null);
                }
            }
        });
    }

    private void setFilterList() {
        FilteredList<Wijn> filterData = new FilteredList<Wijn>(wijnenLijst);

        zoekBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(wijn -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                try {
                    if (wijn.getWijnSerieID() == Integer.parseInt(lowerCaseFilter)) {
                        return true;
                    }
                } catch (Exception e) {
                    if (wijn.getWijnNaam().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }


                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Wijn> sortedData = new SortedList<Wijn>(filterData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(wijnen.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        wijnen.setItems(sortedData);
    }

    /**
     * Algoritme om een order te plaatsen.
     */
    private void toevoegen() {
        try {
            if (checkIfOrder()) {
                bestellingController.createOrder(klant, orderRegels);
                new BestellingPersoonView(new KlantController());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Algorithme om op te slaan
     */
    private void opslaan() {
        try {
            if (checkIfOrder()) {
                bestellingController.opslaan(orderRegels, tmpRemoveOrderRegels);
                new BestellingPersoonView(new KlantController());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Kijkt of er een wijn geselecteerd is en of de wijn neit al inde orderRegels staat.
     * Kijkt ook of het nog aan de tijdelijke lijst moet worden toegevoegd. Voor bij het bewerken.
     *
     * @param wijn
     * @param list
     */
    private void addOrderRegel(Wijn wijn, List<OrderRegel> list) {
        if (!wijnen.getSelectionModel().isEmpty() && !checkIfItemDuplicate(wijn.getWijnID())) {
            OrderRegel tmp = new OrderRegel(
                    wijn.getWijnID(),
                    wijn.getWijnNaam(),
                    wijn.getWijnJaartal(),
                    Integer.parseInt(tf_aantal.getText()),
                    wijn.getPrijs(),
                    1
            );
            orderRegels.add(tmp);
            if (list != null) {
                tmp.setOrderID(order.getOrderID());
                list.add(tmp);
            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Deze wijn staat al in de lijst");
            alert.setContentText("Verwijder de wijn uit de lijst en voeg hem opnieuw toe met het juiste aantal dozen.");
            alert.showAndWait();
        }
    }

    /**
     * kijkt of er wel een orderregel is
     *
     * @return boolean
     */
    private boolean checkIfOrder() {
        boolean temp = false;
        if (orderRegels.size() > 0) {
            temp = true;
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Er is geen wijn toegevoegd aan de bestelling!");
            alert.setContentText("Voeg eerst een wijn toe.");
            alert.showAndWait();
            temp = false;
        }
        return temp;
    }

    /**
     * kijkt of de wijn nog niet in de bestellijst staat
     *
     * @param id
     * @return boolean
     */
    private boolean checkIfItemDuplicate(int id) {
        boolean duplicateWijn = false;
        for (OrderRegel o : orderRegels) {
            if (o.getWijnID() == id) {
                duplicateWijn = true;
            }
        }
        return duplicateWijn;
    }
}
