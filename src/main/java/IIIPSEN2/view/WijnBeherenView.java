package IIIPSEN2.view;

import IIIPSEN2.control.WijnController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import IIIPSEN2.model.Wijn;

import java.util.ArrayList;

/**
 * @author Anton Steenvoorden,
 *         Sidney de Geus
 *         <p>
 *         WijnBeherenView is een class dat zorgt voor de grafische weergaven van het toevoegen
 *         of bewerken van een wijn. Deze IIIPSEN2.view wordt voor toevoegen en bewerken gebruikt omdat
 *         de opzet het zelfde is, behalve dat in het ene geval de text boxes ingevuld moeten
 *         zijn en in de andere niet. Vandaar dat er 2 constructors zijn. Standaard is voor
 *         wijn toevoegen, de andere voor het bewerken van een wijn.
 */
public class WijnBeherenView {
    private Stage stage;
    private Scene scene;
    private Button cancel, opslaan, opslaanHome;
    private HBox centerBox;
    private HBox bottomBox;

    private BorderPane mainPane;
    private HBox topBox;

    private GridPane form;

    private Label title;
    //form labels ..

    private Label lbl_wijnSerieID, lbl_wijnNaam, lbl_inkoopPrijs, lbl_prijs, lbl_wijnType, lbl_wijnMerk, lbl_wijnLand,
            lbl_wijnAfkomst, lbl_wijnJaartal, lbl_isActief;
    private TextField tf_wijnSerieID, tf_wijnNaam, tf_inkoopPrijs, tf_prijs, tf_wijnMerk, tf_wijnAfkomst,
            tf_wijnJaartal, tf_isActief;
    private ComboBox actief, category, types;

    // dropdown actief ?..

    private ImageView icon;
    private int tmpActief = 0;
    private int tmpJaartal = 0;
    private WijnController wijnController;
    private Wijn wijn;


    //CONSTRUCTOR

    /**  
     *  Er zijn 2 constructors. 1 standaard voor wijnen toevoegen en 1 voor het wijnen bewerken
     *  door een wijn object mee te geven aan de constructor worden vervolgens alle velden ingevuld
     *  met de data in het wijn object 
     */
    public WijnBeherenView(WijnController wijnController) {
        this.wijnController = wijnController;
        init();
        display();
    }

    public WijnBeherenView(Wijn wijn, WijnController wijnController) {
        this.wijn = wijn;
        this.wijnController = wijnController;
        init();
        display();
        fillTextFields();
        changeButtons();
    }


    /**
     * init() maakt het standaard scherm
     */
    private void init() {
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
    }


    /**
     * display() is zorgt voor de labels, buttons, textfields en voegt ze aan het scherm toe
     */
    private void display() {
        int minWidth = 150;
        int maxWidth = 200;
        //create labels and textfields

        lbl_wijnSerieID = new Label("*Wijn Serie ID :");
        lbl_wijnSerieID.setMinWidth(minWidth);
        lbl_wijnSerieID.setId("wijn-label");
        lbl_wijnSerieID.setTextFill((Color.valueOf("#FFFC00")));

        lbl_wijnNaam = new Label("*Wijn naam :");
        lbl_wijnNaam.setMinWidth(minWidth);
        lbl_wijnNaam.setId("wijn-label");
        lbl_wijnNaam.setTextFill((Color.valueOf("#FFFC00")));

        lbl_inkoopPrijs = new Label("*Inkoop Prijs :");
        lbl_inkoopPrijs.setMinWidth(minWidth);
        lbl_inkoopPrijs.setId("wijn-label");
        lbl_inkoopPrijs.setTextFill((Color.valueOf("#FFFC00")));

        lbl_prijs = new Label("*Prijs :");
        lbl_prijs.setMinWidth(minWidth);
        lbl_prijs.setId("wijn-label");
        lbl_prijs.setTextFill((Color.valueOf("#FFFC00")));

        lbl_wijnType = new Label("*Soort :");
        lbl_wijnType.setMinWidth(minWidth);
        lbl_wijnType.setId("wijn-label");
        lbl_wijnType.setTextFill((Color.valueOf("#FFFC00")));

        /*
        lbl_wijnMerk = new Label("*Merk :");
        lbl_wijnMerk.setMinWidth(minWidth);
        lbl_wijnMerk.setId("wijn-label");
        lbl_wijnMerk.setTextFill((Color.valueOf("#FFFC00")));
		*/

        lbl_wijnLand = new Label("*Categorie :");
        lbl_wijnLand.setMinWidth(minWidth);
        lbl_wijnLand.setId("wijn-label");
        lbl_wijnLand.setTextFill((Color.valueOf("#FFFC00")));

        lbl_wijnAfkomst = new Label("*Wijn afkomst :");
        lbl_wijnAfkomst.setMinWidth(minWidth);
        lbl_wijnAfkomst.setId("wijn-label");
        lbl_wijnAfkomst.setTextFill((Color.valueOf("#FFFC00")));

        lbl_wijnJaartal = new Label("Wijn jaartal :");
        lbl_wijnJaartal.setMinWidth(minWidth);
        lbl_wijnJaartal.setId("wijn-label");
        lbl_wijnJaartal.setTextFill((Color.valueOf("#FFFC00")));

        lbl_isActief = new Label("Actief :");
        lbl_isActief.setMinWidth(minWidth);
        lbl_isActief.setId("wijn-label");
        lbl_isActief.setTextFill((Color.valueOf("#FFFC00")));

        tf_wijnSerieID = new TextField();
        tf_wijnSerieID.setMaxWidth(maxWidth);
        tf_wijnSerieID.setText(Integer.toString(wijnController.getLastSerieID()));
        tf_wijnNaam = new TextField();
        tf_wijnNaam.setMaxWidth(maxWidth);
        tf_inkoopPrijs = new TextField();
        tf_inkoopPrijs.setMaxWidth(maxWidth);
        tf_prijs = new TextField();
        tf_prijs.setMaxWidth(maxWidth);
        tf_wijnMerk = new TextField();
        tf_wijnMerk.setMaxWidth(maxWidth);
        tf_wijnAfkomst = new TextField();
        tf_wijnAfkomst.setMaxWidth(maxWidth);
        tf_wijnJaartal = new TextField();
        tf_wijnJaartal.setMaxWidth(maxWidth);
        tf_isActief = new TextField();
        tf_isActief.setPrefWidth(80);

        actief = new ComboBox();
        actief.getItems().add("Ja");
        actief.getItems().add("Nee");
        actief.setValue("Ja");
        actief.setMaxWidth(maxWidth);


        types = new ComboBox();
        String[] typeArray = wijnController.getTypes();
        types.setValue(typeArray[0]);
        for (int i = 0; i < typeArray.length; i++) {
            types.getItems().add(typeArray[i]);
            if ((wijn != null) && (i == wijn.getWijnType())) {
                types.setValue(typeArray[i]);
            }
        }

        category = new ComboBox();
        ArrayList<String> categories = wijnController.getCategories();
        category.setValue(categories.get(0));
        for (String c : categories) {
            category.getItems().add(c);
            if (wijn != null) {
                category.setValue(wijn.getWijnCategory());
            }
        }


        //topbox items
        title = new Label("Lions-club Oegstgeest/Warmond");
        title.setId("title");
        title.setTextFill(Color.web("#FFCF03"));
        title.autosize();

        icon = new ImageView(new Image("/IIIPSEN2/images/icon.png"));
        icon.setPreserveRatio(true);
        icon.autosize();

        //buttons
        cancel = new Button("Cancel");
        cancel.setOnAction(e -> new HomeView());
        opslaan = new Button("Toevoegen");
        opslaan.setOnAction(e -> opslaan());
        opslaanHome = new Button("Toevoegen en naar overzicht");
        opslaan.setOnAction(e -> {
            opslaan();
            new WijnOverzichtView(new WijnController());
        });

        //add elements to panes
        topBox.getChildren().addAll(icon, title);
        topBox.setAlignment(Pos.CENTER);

        centerBox.getChildren().add(form);
        form.addRow(0, lbl_wijnSerieID, tf_wijnSerieID);
        form.addRow(1, lbl_wijnNaam, tf_wijnNaam);
        form.addRow(2, lbl_inkoopPrijs, tf_inkoopPrijs);
        form.addRow(3, lbl_prijs, tf_prijs);
        form.addRow(4, lbl_wijnType, types);
        // form.addRow(4, lbl_wijnMerk, tf_wijnMerk);
        form.addRow(5, lbl_wijnLand, category);
        form.addRow(6, lbl_wijnAfkomst, tf_wijnAfkomst);
        form.addRow(7, lbl_wijnJaartal, tf_wijnJaartal);
        form.addRow(8, lbl_isActief, actief);

        bottomBox.getChildren().addAll(cancel, opslaan);
        bottomBox.setLayoutY(stage.heightProperty().doubleValue() - 80);
        mainPane.setTop(topBox);
        mainPane.setCenter(centerBox);
        mainPane.setBottom(bottomBox);
        stage.setScene(scene);
    }


    /**
     * checkValues() dient de textvelden te checken zodra opslaan() of updateWijn() aangeroepen wordt.
     */
    private void checkValues() {
        if (tf_wijnNaam.getText().isEmpty() || tf_prijs.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("Niet alle verplichte velden zijn ingevuld ! ");
            alert.showAndWait();
        }
//        if (tf_prijs.getText().isEmpty()) tf_prijs.setText("");
//        if (tf_wijnAfkomst.getText().isEmpty()) tf_wijnAfkomst.setText("");
        if (tf_wijnJaartal.getText().isEmpty()) tmpJaartal = 0;

        if (actief.getValue().toString().equals("Ja")) tmpActief = 1;
        else tmpActief = 0;
    }

    /**
     * opslaan() is voor het toevoegen van een wijn.
     */
    private void opslaan() {
        checkValues();

        wijnController.addWijn(
                new Wijn(
                        Integer.parseInt(tf_wijnSerieID.getText().toString()),
                        tf_wijnNaam.getText().toString(),
                        Double.parseDouble(tf_inkoopPrijs.getText().toString()),
                        Double.parseDouble(tf_prijs.getText().toString()),
                        wijnController.wijnTypeToInt(types.getSelectionModel().getSelectedItem().toString()),
                        tmpJaartal,
                        tmpActief,
                        tf_wijnAfkomst.getText().toString(),
                        category.getSelectionModel().getSelectedItem().toString()
                )
        );
    }


    /**
     * updateWijn() update de huidige wijn met nieuwe waardes. 
     */
    private void updateWijn() {
        checkValues();

        wijn.setWijnNaam(tf_wijnNaam.getText().toString());
        wijn.setInkoopPrijs(Double.parseDouble(tf_inkoopPrijs.getText().toString()));
        wijn.setPrijs(Double.parseDouble(tf_prijs.getText().toString()));
        wijn.setWijnType(wijnController.wijnTypeToInt(types.getSelectionModel().getSelectedItem().toString()));
        //wijn.setWijnMerk(tf_wijnMerk.getText().toString());
        wijn.setWijnJaartal(Integer.parseInt(tf_wijnJaartal.getText().toString()));
        wijn.setIsActief(tmpActief);
        wijn.setWijnAfkomst(tf_wijnAfkomst.getText().toString());
        wijn.setWijnCategory(category.getSelectionModel().getSelectedItem().toString());

        wijnController.updateWijn(wijn);
    }


    /*
     * fillTextFields() vult alle velden in als er een wijn bij de constructor meegegeven wordt.
     * Het vult dus alle velden met de wijn data.
     */
    private void fillTextFields() {
        tf_wijnSerieID.setText(Integer.toString(wijn.getWijnSerieID()));
        tf_wijnNaam.setText(wijn.getWijnNaam());
        tf_inkoopPrijs.setText(Double.toString(wijn.getInkoopPrijs()));
        tf_prijs.setText(Double.toString(wijn.getPrijs()));
        //tf_wijnMerk.setText(wijn.getWijnMerk());
        tf_wijnAfkomst.setText(wijn.getWijnAfkomst());
        tf_wijnJaartal.setText(Integer.toString(wijn.getWijnJaartal()));
    }


    private void changeButtons() {
        cancel.setOnAction(e -> new WijnOverzichtView(wijnController));
        opslaan.setText("Opslaan");
        opslaan.setOnAction(e -> {
            updateWijn();
            new WijnOverzichtView(new WijnController());
        });
        opslaanHome.setVisible(false);
    }


}
