/**
 *
 */
package IIIPSEN2.view;

import IIIPSEN2.interfaces.Knop;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

/**
 * @author Anton Steenvoorden
 */
public class KeuzeView {
    private Stage stage;
    private Scene scene;
    private Button keuze1, keuze2, keuze3, keuze4, keuze5;
    private HBox centerBox;

    private BorderPane mainPane;
    private HBox topBox;

    private Label title;
    private ImageView icon;

    private void init() {
        stage = Main.getInstance().mainStage;

        mainPane = new BorderPane();
        topBox = new HBox();

        centerBox = new HBox();
        centerBox.getStyleClass().add("hbox");
        centerBox.setAlignment(Pos.CENTER);

        scene = new Scene(mainPane);
        scene.getStylesheets().add("/IIIPSEN2/view/style.css");
    }

    private void display() {
        //topbox items
        title = new Label("Lions-club Oegstgeest/Warmond");
        title.setId("title");
        title.setTextFill(Color.web("#FFCF03"));
        title.autosize();

        icon = new ImageView(new Image("/IIIPSEN2/images/icon.png"));
        icon.setPreserveRatio(true);
        icon.autosize();

        //add elements to panes
        topBox.getChildren().addAll(icon, title);
        topBox.setAlignment(Pos.CENTER);

        mainPane.setTop(topBox);
        mainPane.setCenter(centerBox);

        stage.setScene(scene);
    }

    public KeuzeView(List<Knop> knoppen) {
        init();
        for (int i = 0; i < knoppen.size(); i++) {
            Knop k = knoppen.get(i);
            Button b = new Button(k.getKnopTekst());
            b.setOnAction(e -> k.onClick());
            createButton(b);
            centerBox.getChildren().add(b);

        }
        display();
    }

    public void createButton(Button b) {
        b.setPrefSize(200, 200);
        b.setFont(Font.font(0.015 * stage.widthProperty().doubleValue()));

    }
}
