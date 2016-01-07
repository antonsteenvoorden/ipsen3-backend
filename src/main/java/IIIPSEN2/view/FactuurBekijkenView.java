package IIIPSEN2.view;

import IIIPSEN2.control.KlantController;
import IIIPSEN2.control.OrderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import IIIPSEN2.model.FactuurGenerator;
import IIIPSEN2.model.Klant;
import IIIPSEN2.model.Order;
import IIIPSEN2.model.OrderRegel;

import java.util.Date;

/**
 * Dit is de IIIPSEN2.view voor de orderregels. Een factuur kan hier worden bekeken
 * Ook kan een factuur worden geexporteerd
 *
 * @author Jordan
 */
public class FactuurBekijkenView {

    private Stage stage;
    private Scene scene;
    private Button cancel;
    private HBox centerBox;
    private HBox bottomBox;

    private BorderPane mainPane;
    private HBox topBox;

    private GridPane form;

    private Label title;

    private ImageView icon;

    private String klantVoornaam, klantAchternaam, klantAdres, klantEmail, klantNummer;
    private int factuurNummer, debiteurenNummer, orderID;
    private Date factuurDatum;
    private Order order;
    private KlantController klantController;
    private OrderController orderController;
    private TableView<OrderRegel> table = new TableView<OrderRegel>();
    private FactuurGenerator factuurGenerator;

    // CONSTRUCTOR
    public FactuurBekijkenView(Order order, int debiteurenNummer, KlantController klantController, OrderController
            orderController) {
        this.order = order;
        this.debiteurenNummer = debiteurenNummer;
        this.klantController = klantController;
        this.orderController = orderController;

        Klant klant = klantController.getKlantByOrder(order);
        klantVoornaam = klant.getVoornaam();
        klantAchternaam = klant.getAchternaam();
        klantNummer = klant.getTelefoon();

        stage = Main.getInstance().mainStage;

        mainPane = new BorderPane();
        topBox = new HBox();

        centerBox = new HBox();
        centerBox.getStyleClass().add("hbox");
        centerBox.setAlignment(Pos.CENTER);
        bottomBox = new HBox();
        bottomBox.getStyleClass().add("hbox");
        bottomBox.setAlignment(Pos.CENTER);

        form = new GridPane();
        form.setHgap(5);
        form.setVgap(5);
        form.getStyleClass().add("form");

        scene = new Scene(mainPane);
        scene.getStylesheets().add("/IIIPSEN2/view/style.css");
        display();
    }

    private void display() {

        // create labels and textfields
        Label lbl_klantNaam = new Label("Klant naam:");
        lbl_klantNaam.setTextFill((Color.valueOf("#FFFC00")));
        Label lbl_klantID = new Label("Klant email:");
        lbl_klantID.setTextFill((Color.valueOf("#FFFC00")));
        Label lbl_klantAdres = new Label("Klant adres:");
        lbl_klantAdres.setTextFill((Color.valueOf("#FFFC00")));
        Label lbl_factuurNummer = new Label("Factuur nummer:");
        lbl_factuurNummer.setTextFill((Color.valueOf("#FFFC00")));
        Label lbl_factuurDatum = new Label("Factuur datum:");
        lbl_factuurDatum.setTextFill((Color.valueOf("#FFFC00")));
        Label lbl_debiteurenNummer = new Label("Debiteuren nummer:");
        lbl_debiteurenNummer.setTextFill((Color.valueOf("#FFFC00")));

        String klantHeleNaam = klantVoornaam + " " + klantAchternaam;
        Label lbl_DklantNaam = new Label(klantHeleNaam);
        lbl_DklantNaam.setTextFill((Color.valueOf("#FFFC00")));
        Label lbl_DklantID = new Label(order.getKlantEmail());
        lbl_DklantID.setTextFill((Color.valueOf("#FFFC00")));
        Label lbl_DklantAdres = new Label(order.getFactuurAdres());
        lbl_DklantAdres.setTextFill((Color.valueOf("#FFFC00")));
        Label lbl_DfactuurNummer = new Label(Integer.toString(order.getOrderID()));
        lbl_DfactuurNummer.setTextFill((Color.valueOf("#FFFC00")));
        Label lbl_DfactuurDatum = new Label(order.getOrderDatum().toString());
        lbl_DfactuurDatum.setTextFill((Color.valueOf("#FFFC00")));
        Label lbl_DdebiteurenNummer = new Label(Integer.toString(debiteurenNummer));
        lbl_DdebiteurenNummer.setTextFill((Color.valueOf("#FFFC00")));

        TextField tf_wijnnaam = new TextField();
        TextField tf_prijs = new TextField();

        table.setEditable(true);

        TableColumn<OrderRegel, Integer> codeCol = new TableColumn<>("Nr");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("orderRegelID"));

        TableColumn<OrderRegel, Integer> aantalCol = new TableColumn("Aantal");
        aantalCol.setCellValueFactory(new PropertyValueFactory<>("aantal"));

        TableColumn<OrderRegel, Integer> wijnCol = new TableColumn("Wijn serie nr");
        wijnCol.setCellValueFactory(new PropertyValueFactory<>("wijnID"));

        TableColumn<OrderRegel, String> naamCol = new TableColumn("Naam");
        naamCol.setCellValueFactory(new PropertyValueFactory<>("wijnNaam"));

        TableColumn<OrderRegel, Integer> jaartalCol = new TableColumn("Jaartal");
        jaartalCol.setCellValueFactory(new PropertyValueFactory<>("wijnJaartal"));

        TableColumn<OrderRegel, Integer> perflesCol = new TableColumn("Per fles");
        perflesCol.setCellValueFactory(new PropertyValueFactory<>("wijnPrijs"));

        TableColumn<OrderRegel, Double> bedragCol = new TableColumn("Bedrag");
        bedragCol.setCellValueFactory(new PropertyValueFactory<>("totaalPrijs"));

        table = new TableView<>();
        ObservableList<OrderRegel> orders = FXCollections.observableArrayList(orderController.getAlleOrderRegels
                (order.getOrderID()));
        table.setItems(orders);
        table.getColumns().addAll(wijnCol, naamCol, jaartalCol, aantalCol, perflesCol, bedragCol);

        // topbox items
        title = new Label("Lions-club Oegstgeest/Warmond");
        title.setId("title");
        title.setTextFill(Color.web("#FFCF03"));
        title.autosize();

        icon = new ImageView(new Image("/IIIPSEN2/images/icon.png"));
        icon.setPreserveRatio(true);
        icon.autosize();

        // buttons
        cancel = new Button("Terug");
        cancel.setOnAction(e -> new BestellingOverzichtView(new OrderController(), new KlantController()));

        Button exporteer = new Button("Exporteer naar PDF");
        exporteer.setOnAction(e -> {
            factuurGenerator = new FactuurGenerator(klantController.getKlantByOrder(order), order, orders);
            factuurGenerator.factuur();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Exporteer naar PDF");
            alert.setContentText("De factuur is succesvol ge�xporteerd. ");

            alert.showAndWait();
        });

        // add elements to panes
        topBox.getChildren().addAll(icon, title);
        topBox.setAlignment(Pos.CENTER);

        centerBox.getChildren().add(form);
        centerBox.getChildren().add(table);

        form.addColumn(0, lbl_klantNaam, lbl_klantID, lbl_klantAdres, lbl_factuurNummer, lbl_factuurDatum,
                lbl_debiteurenNummer);
        form.addColumn(1, lbl_DklantNaam, lbl_DklantID, lbl_DklantAdres, lbl_DfactuurNummer, lbl_DfactuurDatum,
                lbl_DdebiteurenNummer);
        form.addColumn(2, exporteer);

        bottomBox.getChildren().add(cancel);
        bottomBox.getChildren().add(exporteer);

        mainPane.setTop(topBox);
        mainPane.setCenter(centerBox);
        mainPane.setBottom(bottomBox);
        stage.setScene(scene);
    }


}
