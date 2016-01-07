package IIIPSEN2;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Sidney de Geus
 *         <p>
 *         ExportDebiteuren is een class dat ervoor zorgt dat er een dialog omhoog komt en
 *         vervolgens er een locatie kan worden gekozen waar het bestand opgeslagen kan worden.
 *         Het bestand zal vervolgens als een CSV file worden opgeslagen.
 */

public class ExportDebiteuren {

    private ArrayList<String> debiteuren;

    // Constructor

    /**
     * De constructor ExportDebitueren(...) zorgt ervoor dat alle debiteuren meegegeven worden
     * en vervolgens worden opgeslagen in een attribuut. Ook roept het de saveDialog() method aan.
     */
    public ExportDebiteuren(ArrayList<String> debiteuren) {
        this.debiteuren = debiteuren;
        saveDialog();
    }

    /**
     * saveDialog() laat een scherm omhoog komen om een locatie uit te zoeken
     * om het bestand op te slaan.
     */
    public void saveDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save debiteuren lijst");
        fileChooser.setInitialFileName(
                "Debiteuren " + Calendar.getInstance().get(Calendar.YEAR)
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV File", "*.csv")
        );
        File file = fileChooser.showSaveDialog(new Stage());

        createCSV(file);
    }

    /**
     * createCSV maakt een CSV bestand aan met de data die in de constructor meegegeven is.
     */
    private void createCSV(File file) {
        try {
            FileWriter writer = new FileWriter(file);

            writer.append("Naam");
            writer.append(';');
            writer.append("Order ID");
            writer.append(';');
            writer.append("Email");
            writer.append(';');
            writer.append("Totaal prijs");
            writer.append(';');
            writer.append("Datum");
            writer.append(';');
            writer.append("Betaald");
            writer.append("\n\n");

            for (String debiteur : debiteuren) {
                writer.append(debiteur);
                writer.append("\n");
            }

            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
