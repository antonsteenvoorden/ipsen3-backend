package IIIPSEN2;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Sidney de Geus
 *         ImportKlanten is een class dat ervoor zorgt dat er een dialog omhoog komt en
 *         vervolgens een bestand gekozen kan worden om te importeren. Indien het
 *         bestand het juiste formaat is zullen de klanten in het bestand ingelezen worden
 *         en zullen ze vervolgens worden toegevoegd in de database. (indien ze er nog niet in staan)
 */

public class ImportKlanten {

    /**
     * openDialog() laat een scherm omhoog komen om een locatie uit te zoeken
     * om een bestand te kiezen.
     * Returns een ArrayList<Klant>
     */
    public ArrayList<Klant> openDialog() {
        ArrayList<Klant> klanten = new ArrayList<Klant>();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Kies een CSV of XLS(X) bestand om klanten te importeren");
        fileChooser.getExtensionFilters().addAll(
                //new FileChooser.ExtensionFilter("CSV, XLS, XLSX File", "*.csv", "*.xls", "*.xlsx")
                new FileChooser.ExtensionFilter("CSV File", "*.csv")
                //new FileChooser.ExtensionFilter("XLSX File", "*.xlsx")
        );

        File file = fileChooser.showOpenDialog(new Stage());
        String extension = "";

        if (file != null) {
            int i = file.getName().lastIndexOf('.');
            if (i > 0) {
                extension = file.getName().substring(i + 1);
            }

            if (extension.equals("csv")) {
                klanten = readCSV(file);
            }
            /*
            else if (extension.equals("xls")) {
                klanten = readXLS(file);
            } else if (extension.equals("xlsx")) {
                klanten = readXLSX(file);
            }
            */
        }
        return klanten;
    }


    /**
     * Leest een CSV bestand dat gekozen is in het dialoog. 
     * Returns een ArrayList<Klant>.
     */
    private ArrayList<Klant> readCSV(File file) {
        ArrayList<Klant> klanten = new ArrayList<Klant>();
        BufferedReader bufferedReader = null;
        String line;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            boolean firstLine = true;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals(";;;;;")) {
                    break;
                }
                if (firstLine) {
                    String[] columns = line.split(",|;");
                    boolean correctFormat = correctFormat(columns);
                    if (!correctFormat) {
                        wrongFormatAlert();
                        break;
                    }
                    firstLine = false;
                } else {
                    try {
                        String[] tmpValues = line.split(",|;");
                        String[] fullname = tmpValues[1].split(" ");
                        String[] fulladress = tmpValues[3].split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
                        String[] fullpostalcode = tmpValues[4].split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

                        String timeStamp = tmpValues[0];
                        String email = tmpValues[2];
                        String voornaam = fullname[0];
                        String tussenvoegsel = "";
                        String achternaam = "";
                        String straatnaam = fulladress[0];
                        String huisnummer = fulladress[1];
                        String huisnummerToevoeging = "";
                        String postcode = fullpostalcode[0];
                        String postcodeToevoeging = fullpostalcode[1];
                        String plaatsnaam = tmpValues[5];
                        DateFormat format = new SimpleDateFormat("MM/dd/yyy HH:mm:ss");
                        Date parsedDate = format.parse(timeStamp);

                        // tussenvoegsel
                        if (fullname.length > 2) {
                            for (int i = 1; i < (fullname.length - 1); i++) {
                                tussenvoegsel += fullname[i] + " ";
                            }
                        }
                        // achternaam
                        if (fullname.length != 1) {
                            achternaam = fullname[fullname.length - 1];
                        }

                        klanten.add(
                                new Klant(
                                        email,
                                        voornaam,
                                        tussenvoegsel,
                                        achternaam,
                                        straatnaam,
                                        Integer.parseInt(huisnummer),
                                        huisnummerToevoeging,
                                        Integer.parseInt(postcode),
                                        postcodeToevoeging,
                                        plaatsnaam,
                                        null,
                                        "Gast",
                                        null,
                                        1,
                                        new java.sql.Timestamp(parsedDate.getTime())
                                )
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return klanten;
    }

    /**
     * correctFormat() kijkt of het formaat van het ingelezen bestand wel goed is. 
     * Returns true of false.
     */
    private boolean correctFormat(String[] columns) {
        boolean correctFormat = true;
        if (!columns[0].equals("Timestamp")) {
            correctFormat = false;
        } else if (!columns[1].equals("Naam")) {
            correctFormat = false;
        } else if (!columns[2].equals("E-mail")) {
            correctFormat = false;
        } else if (!columns[3].equals("Straat + huisnummer")) {
            correctFormat = false;
        } else if (!columns[4].equals("Postcode")) {
            correctFormat = false;
        } else if (!columns[5].equals("Woonplaats")) {
            correctFormat = false;
        } else if (columns.length > 6) {
            correctFormat = false;
        }
        return correctFormat;
    }

    /**
     * wrongFormatAlert() geeft een error dat het formaat fout is.
     */
    private void wrongFormatAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Wrong format");
        alert.setHeaderText("Het formaat is fout");
        alert.setContentText(
                "Het formaat moet bestaan uit de volgende kolommen op volgorde: \n"
                        + "Timestamp, Naam, E-mail, Straat + huisnummer, Postcode, Woonplaats");
        alert.showAndWait();
    }
}
