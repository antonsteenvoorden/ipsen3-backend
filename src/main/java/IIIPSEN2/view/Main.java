package IIIPSEN2.view;

import IIIPSEN2.control.KlantController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * @author Anton Steenvoorden
 *         Singleton met daarin de stage.
 */
public class Main extends Application {
    protected Stage mainStage;
    private static Main uniqueInstance;
    protected double width = 1024;
    protected double height = 720;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method called by launch
     * maakt de stage aan met eigenschappen.
     */
    public void start(Stage primaryStage) {
        enableLogging();
        this.mainStage = primaryStage; // Create stage
        this.mainStage.setTitle("Wijnfestijn"); // Set title of stage
        this.mainStage.centerOnScreen();
        this.mainStage.setMinWidth(width / 2);
        this.mainStage.setHeight(height);
        this.mainStage.setMinHeight(height - 100);
        this.mainStage.setWidth(width);
        this.mainStage.setOnCloseRequest(e -> {
            e.consume();
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Weet u het zeker? ");
            alert.setHeaderText("U staat op het punt de applicatie te sluiten ! ");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                this.mainStage.close();
            } else if (result.get() == ButtonType.CANCEL) {

            }
        });

        new HomeView();
    }

    /**
     * singleton constructor als er al een instantie is maakt hij geen nieuwe aan.
     */
    public Main() {
        super();
        synchronized (Main.class) {
            if (uniqueInstance != null) throw new UnsupportedOperationException(
                    getClass() + " is singleton but constructor called more than once");
            uniqueInstance = this;
        }
    }

    public static synchronized Main getInstance() {
        return uniqueInstance;
    }

    private void enableLogging() {
        try {
            KlantController klantController = new KlantController();
            klantController.enableLogging();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Er is iets misgegaan. ");
            alert.setHeaderText("Let er op dat de database draait !");
            Optional<ButtonType> result = alert.showAndWait();
            Platform.exit();
        }
    }
}