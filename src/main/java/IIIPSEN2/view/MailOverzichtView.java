package IIIPSEN2.view;

import IIIPSEN2.control.MailController;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import IIIPSEN2.model.Klant;
import IIIPSEN2.model.Order;

/**
 * Deze klas is de GUI van het versturen van een dank e-mail.
 *
 * @author dennis
 */
public class MailOverzichtView {
    private Stage stage;
    private Scene scene;

    private Button cancel, verzenden, add, remove, verzondenType;
    private VBox leftBox, rightBox;
    private HBox centerBox, bottomBox, topBox;
    private BorderPane mainPane;


    private Label title, selectie, geenSelectie;
    private TextField zoekBox;
    private MailController mailController;
    private TableView<Klant> ontvangersSelectie;
    private TableView<Klant> geenOntvangersSelectie;

    private ImageView icon;

    private Klant klant;
    private Order order;

    /**
     * CONSTRUCTOR
     * Initieren en laten zien van GUI onderdelen.
     *
     * @param mailController
     */
    public MailOverzichtView(MailController mailController) {
        this.mailController = mailController;
        init();
        display();
    }

    /**
     * Deze methode initieerd onderdelen van de GUI.
     */
    private void init() {
        stage = Main.getInstance().mainStage;

        mainPane = new BorderPane();
        topBox = new HBox();

        centerBox = new HBox();
        centerBox.getStyleClass().add("hbox");

        bottomBox = new HBox();
        bottomBox.getStyleClass().add("hbox");
        bottomBox.setAlignment(Pos.CENTER);

        rightBox = new VBox();
        rightBox.getStyleClass().add("hbox");
        rightBox.setAlignment(Pos.CENTER);

        leftBox = new VBox();
        leftBox.setSpacing(10);
        leftBox.getStyleClass().add("form");
        leftBox.setAlignment(Pos.BOTTOM_CENTER);

        scene = new Scene(mainPane);
        scene.getStylesheets().add("/IIIPSEN2/view/style.css");
    }

    /**
     * Deze methode laat ontvangers selectie en geen ontvanges selectie zien in een tableview
     * Ook wordt hier de zoekbox toegevoegd die filterd op achternaam.
     * In deze GUI is een knop toegevoegd die kan wisselen tussen verzonden en niet verzonden facturen.
     * De GUI wordt hier daadwerkelijk als "scene" gezet.
     */
    private void display() {

        ontvangersSelectie = new TableView<Klant>();
        geenOntvangersSelectie = new TableView<Klant>();

        zoekBox = new TextField();
        zoekBox.setPromptText("Zoek op achternaam");

        remove = new Button("verwijder ontvanger");
        remove.setOnAction(e -> {
            if (!ontvangersSelectie.getSelectionModel().isEmpty()) {
                mailController.removeKlant(ontvangersSelectie.getSelectionModel().getSelectedItem());
            }
        });

        selectie = new Label("geselecteerd.");
        selectie.setMinWidth(150);
        selectie.setId("form-label");
        selectie.setTextFill((Color.valueOf("#FFFC00")));

        geenSelectie = new Label("niet geselecteerd.");
        geenSelectie.setMinWidth(150);
        geenSelectie.setId("form-label");
        geenSelectie.setTextFill((Color.valueOf("#FFFC00")));

        //buttons
        cancel = new Button("Annuleren");
        cancel.setOnAction(e -> new HomeView());

        verzenden = new Button("Verzenden");
        verzenden.setOnAction(e -> {
            mailController.sendDankMail();
            new HomeView();
        });

        add = new Button("voeg ontvanger toe");
        add.setOnAction(e -> {
            if (!geenOntvangersSelectie.getSelectionModel().isEmpty()) {
                mailController.removeGeenSelectieKlant((geenOntvangersSelectie.getSelectionModel().getSelectedItem()));
            }
        });

        verzondenType = new Button();
        if (mailController.isFacturenIsVerzondenView()) {
            verzondenType.setText("bekijk niet verzonden e-mails");
        } else {
            verzondenType.setText("bekijk verzonden e-mails");
        }
        verzondenType.setOnAction(e -> {
            if (mailController.isFacturenIsVerzondenView()) {
                mailController.setFacturenIsVerzondenView(false);
                mailController.reset();
                mailController.facturenClicked();
            } else {
                mailController.setFacturenIsVerzondenView(true);
                mailController.reset();
                mailController.facturenClicked();
            }
        });

        /// selectie
        TableColumn<Klant, String> voornaam = new TableColumn<>("Voornaam");//naam
        voornaam.setPrefWidth(100);
        voornaam.setCellValueFactory(new PropertyValueFactory<>("voornaam"));//property

        TableColumn<Klant, String> tussenvoegsel = new TableColumn<>("Tussenvoegsel");
        tussenvoegsel.setPrefWidth(40);
        tussenvoegsel.setCellValueFactory(new PropertyValueFactory<>("tussenvoegsel"));

        TableColumn<Klant, String> achternaam = new TableColumn<>("Achternaam");
        achternaam.setPrefWidth(100);
        achternaam.setCellValueFactory(new PropertyValueFactory<>("achternaam"));

        TableColumn<Klant, String> email = new TableColumn<>("Email");
        email.setPrefWidth(100);
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

        ontvangersSelectie.getColumns().addAll(voornaam, tussenvoegsel, achternaam, email);
        ontvangersSelectie.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ontvangersSelectie.setItems(mailController.getKlantLijst());

        /// geen selectie
        TableColumn<Klant, String> voornaam2 = new TableColumn<>("Voornaam");//naam
        voornaam2.setPrefWidth(100);
        voornaam2.setCellValueFactory(new PropertyValueFactory<>("voornaam"));//property

        TableColumn<Klant, String> tussenvoegsel2 = new TableColumn<>("Tussenvoegsel");
        tussenvoegsel2.setPrefWidth(50);
        tussenvoegsel2.setCellValueFactory(new PropertyValueFactory<>("tussenvoegsel"));

        TableColumn<Klant, String> achternaam2 = new TableColumn<>("Achternaam");
        achternaam2.setPrefWidth(100);
        achternaam2.setCellValueFactory(new PropertyValueFactory<>("achternaam"));

        TableColumn<Klant, String> email2 = new TableColumn<>("Email");
        email2.setPrefWidth(100);
        email2.setCellValueFactory(new PropertyValueFactory<>("email"));

        geenOntvangersSelectie.getColumns().addAll(voornaam2, tussenvoegsel2, achternaam2, email2);
        geenOntvangersSelectie.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        geenOntvangersSelectie.setItems(mailController.getGeenSelectieLijst());

        FilteredList<Klant> filterData = new FilteredList<Klant>(mailController.getKlantLijst());

        zoekBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(klant -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (klant.getAchternaam().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Klant> sortedData = new SortedList<Klant>(filterData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(ontvangersSelectie.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        ontvangersSelectie.setItems(sortedData);

        //topbox items
        title = new Label("Lions-club Oegstgeest/Warmond");
        title.setId("title");
        title.setTextFill(Color.web("#FFCF03"));
        title.autosize();

        icon = new ImageView(new Image("/IIIPSEN2/images/icon.png"));
        icon.setPreserveRatio(true);
        icon.autosize();

        //add elements to panes
        leftBox.getChildren().addAll(geenSelectie, geenOntvangersSelectie, add, verzondenType);
        rightBox.getChildren().addAll(selectie, zoekBox, ontvangersSelectie, remove /*,verzondenType*/);//nog niet
        // werkend omdat er geen update door observer wordt uitgevoerd.
        centerBox.getChildren().addAll(leftBox, rightBox);
        topBox.getChildren().addAll(icon, title);
        topBox.setAlignment(Pos.CENTER);

        bottomBox.getChildren().addAll(cancel, verzenden);
        bottomBox.setLayoutY(stage.heightProperty().doubleValue() - 80);

        mainPane.setTop(topBox);
        mainPane.setCenter(centerBox);
        mainPane.setBottom(bottomBox);

        stage.setScene(scene);
    }
}


