package IIIPSEN2.view;

import com.sun.prism.impl.Disposer.Record;
import IIIPSEN2.control.BestellingController;
import IIIPSEN2.control.BestellingKeuzeController;
import IIIPSEN2.control.KlantController;
import IIIPSEN2.control.OrderController;
import IIIPSEN2.interfaces.Knop;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import IIIPSEN2.knopHandlers.BestellingOverzichtHandler;
import IIIPSEN2.knopHandlers.BestellingPlaatsenHandler;
import IIIPSEN2.knopHandlers.TerugKnopHandler;
import IIIPSEN2.knoppen.BestellingOverzichtKnop;
import IIIPSEN2.knoppen.BestellingPlaatsenKnop;
import IIIPSEN2.knoppen.TerugKnop;
import IIIPSEN2.model.Order;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Jordan Munk
 *         De IIIPSEN2.view van de bestellingen. Alle orders zijn hier te vinden.
 */
public class BestellingOverzichtView {
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
    private Button factuurBekijken, terug, factuurOverzicht, home, toevoegen, bewerken, verwijderen, inActiefStellen,
            actieveLijst, refresh;
    private OrderController orderController;
    private KlantController klantController;
    private ObservableList<Order> actieveOverzicht;
    private ObservableList<Order> inActieveOverzicht;
    private TableView<Order> bestellingOverzicht = new TableView<Order>();
    // private ObservableList klanten;

    public BestellingOverzichtView(OrderController orderController, KlantController klantController) {

        this.orderController = orderController;
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
        textField.setPromptText("Voer een email in");
        textField.setMinWidth(200);
        textField.setFocusTraversable(false);

        toevoegen = new Button("Toevoegen");
        toevoegen.setOnAction(e -> new BestellingPersoonView(new KlantController()));

        inActiefStellen = new Button("In-actief stellen");
        inActiefStellen.setOnAction(e -> inActiefStellenAction());

        actieveLijst = new Button("In-actieve bestellingen");
        actieveLijst.setOnAction(e -> actieveAction());

        refresh = new Button("Verversen");
        refresh.setOnAction(e -> new BestellingOverzichtView(new OrderController(), new KlantController()));

        bewerken = new Button("Bewerken");
        bewerken.setOnAction(e -> editAction());


        terug = new Button("Terug");
        terug.setMinWidth(50);
        terug.setOnAction(e -> {
            List<Knop> temp = new ArrayList<Knop>();
            temp.add(new BestellingPlaatsenKnop((BestellingPlaatsenHandler) new BestellingKeuzeController()));
            temp.add(new BestellingOverzichtKnop((BestellingOverzichtHandler) new BestellingKeuzeController()));
            temp.add(new TerugKnop((TerugKnopHandler) new BestellingKeuzeController()));
            new KeuzeView(temp);
        });

        home = new Button("Home");
        home.setOnAction(e -> new HomeView());

        factuurOverzicht = new Button("Factuur overzicht");
        factuurOverzicht.setOnAction(e -> factuur());

        // Column

        ////////// CREATING THE COLUMNS

        TableColumn<Order, Integer> orderIDColumn = new TableColumn<>("ID");
        orderIDColumn.setPrefWidth(40);
        orderIDColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));

        TableColumn<Order, String> klantColumn = new TableColumn<>("Klant email");
        klantColumn.setPrefWidth(200);
        klantColumn.setCellValueFactory(new PropertyValueFactory<>("klantEmail"));

        TableColumn<Order, String> adresColumn = new TableColumn<>("Klant adres");
        adresColumn.setPrefWidth(200);
        adresColumn.setCellValueFactory(new PropertyValueFactory<>("factuurAdres"));

        TableColumn<Order, Date> datumColumn = new TableColumn<>("Bestelling datum");
        datumColumn.setPrefWidth(150);
        datumColumn.setCellValueFactory(new PropertyValueFactory<>("orderDatum"));

        TableColumn<Order, Integer> actiefColumn = new TableColumn<>("Actief");
        actiefColumn.setPrefWidth(40);
        actiefColumn.setCellValueFactory(new PropertyValueFactory<>("isActief"));

        TableColumn bestellingColumn = new TableColumn<>("Bestelling");
        bestellingColumn.setSortable(false);

        bestellingColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        bestellingColumn.setCellFactory(new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

            @Override
            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                return new ButtonCell();
            }

        });

        ///////////////// ADDING TO TABLEVIEW
        actieveOverzicht = FXCollections.observableArrayList(orderController.getAlleActieveOrders());
        inActieveOverzicht = FXCollections.observableArrayList(orderController.getInactieveOrders());

        bestellingOverzicht = new TableView<Order>();
        bestellingOverzicht.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        bestellingOverzicht.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        bestellingOverzicht.setItems(searchFilter(actieveOverzicht, bestellingOverzicht));
        bestellingOverzicht.getColumns().addAll(orderIDColumn, klantColumn, adresColumn, bestellingColumn, datumColumn,
                actiefColumn);

        // add elements to panes
        topBox.getChildren().addAll(icon, title);
        topBox.setAlignment(Pos.CENTER);

        zoekBox.getStyleClass().add("hbox");
        zoekBox.getChildren().addAll(textField, toevoegen, bewerken, inActiefStellen, actieveLijst, refresh,
                factuurOverzicht);

        centerBox.getChildren().addAll(zoekBox, bestellingOverzicht);
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
     * Het in-actief stellen van een bestelling. Dit verandert de status van isActief.
     */
    public void inActiefStellenAction() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Weet u het zeker ?");
        alert.setHeaderText("U staat op het punt een of meerdere bestellingen in-actief of actief te stellen.");
        alert.setContentText("Weet u het zeker ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (!bestellingOverzicht.getSelectionModel().isEmpty() && !activeList) {
                orderController.deactivateOrders(bestellingOverzicht.getSelectionModel().getSelectedItems());
            } else if (!bestellingOverzicht.getSelectionModel().isEmpty() && activeList) {
                orderController.activateOrders(bestellingOverzicht.getSelectionModel().getSelectedItems());
            }
        } else {
            return;
        }
    }

    /**
     * Het zoeken van bestellingen op emailadres
     *
     * @param orderLijst
     * @param bestellingOverzicht
     * @return
     */
    private SortedList<Order> searchFilter(ObservableList<Order> orderLijst, TableView<Order> bestellingOverzicht) {
        FilteredList<Order> filterData = new FilteredList<Order>(orderLijst);

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(order -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter
                // text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (order.getKlantEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Order> sortedData = new SortedList<Order>(filterData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(bestellingOverzicht.comparatorProperty());

        return sortedData;
    }

    /**
     * Map facturen wordt aangemaakt als het er niet is.
     */
    public void factuur() {
        String mapNaam = "facturen";

        File theDir = new File(mapNaam);

        // if the directory does not exist, create it
        if (!theDir.exists()) {
            boolean result = false;

            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }

        try {
            Desktop.getDesktop().open(new File(mapNaam));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Verandert de button qua tekst
     */

    public void actieveAction() {
        if (activeList) {
            bestellingOverzicht.setItems(searchFilter(actieveOverzicht, bestellingOverzicht));
            activeList = false;
            actieveLijst.setText("In-actieve bestellingen");
            inActiefStellen.setText("In-actief stellen");
        } else if (!activeList) {
            bestellingOverzicht.setItems(searchFilter(inActieveOverzicht, bestellingOverzicht));
            activeList = true;
            actieveLijst.setText("Actieve bestellingen");
            inActiefStellen.setText("Actief stellen");
        }
    }

    /**
     * Methode om een button met "Bekijk bestelling" te maken
     *
     * @author Jordan
     */
    // Define the button cell
    private class ButtonCell extends TableCell<Record, Boolean> {
        final Button cellButton = new Button("Bekijk bestelling");

        ButtonCell() {

            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    // do something when button clicked
                    // ...
                    Order currentOrder = (Order) ButtonCell.this.getTableView().getItems()
                            .get(ButtonCell.this.getIndex());
                    // remove selected item from the table list
                    new FactuurBekijkenView(currentOrder, 1, klantController, orderController);
                }
            });
        }

        // Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }

    /**
     * wanneer er op bewerken wordt gedrukt wordt er gecontroleerd of er maar 1 gast geselecteerd is
     */
    public void editAction() {
        if (bestellingOverzicht.getSelectionModel().getSelectedItems().size() > 1) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Er kan maar een bestelling tegelijk worden bewerkt !");
            alert.showAndWait();
        }
        if (bestellingOverzicht.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Er is geen bestelling geselecteerd ! ");
            alert.showAndWait();
        }
        if (bestellingOverzicht.getSelectionModel().getSelectedItems().size() == 1) {
            Order temp = bestellingOverzicht.getSelectionModel().getSelectedItem();
            new BestellingController().editView(temp);
        }
    }
}
