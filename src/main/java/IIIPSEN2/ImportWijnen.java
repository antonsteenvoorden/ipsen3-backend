package IIIPSEN2;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Sidney de Geus
 *         <p>
 *         ImportWijnen is een class dat ervoor zorgt dat er een dialog omhoog komt en
 *         vervolgens een bestand gekozen kan worden om te importeren. Indien het
 *         bestand het juiste formaat is zullen de wijnen in het bestand ingelezen worden
 *         en zullen ze vervolgens worden toegevoegd in de database. (indien ze er nog niet in staan)
 */

public class ImportWijnen {

    /**
     * openDialog() laat een scherm omhoog komen om een locatie uit te zoeken
     * om een bestand te kiezen.
     * Returns een ArrayList.
     */
    public ArrayList<Wijn> openDialog() {
        ArrayList<Wijn> wijnen = new ArrayList<Wijn>();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Kies XLSX bestand om wijnen te importeren");
        fileChooser.getExtensionFilters().addAll(
                //new FileChooser.ExtensionFilter("CSV, XLS, XLSX File", "*.csv", "*.xls", "*.xlsx")
                //new FileChooser.ExtensionFilter("CSV File", "*.csv")
                new FileChooser.ExtensionFilter("XLSX File", "*.xlsx")
        );

        File file = fileChooser.showOpenDialog(new Stage());
        String extension = "";

        if (file != null) {
            int i = file.getName().lastIndexOf('.');
            if (i > 0) {
                extension = file.getName().substring(i + 1);
            }

            if (extension.equals("xlsx")) {
                wijnen = readXLSX(file);
            }
            /*else if (extension.equals("xls")) {
                wijnen = readXLS(file);
    		}*/
        }
        return wijnen;
    }


    /**
     * Leest een XLSX bestand dat gekozen is in het dialoog.
     * Returns een arraylist met de ingelezen gegevens.
     */
    private ArrayList<Wijn> readXLSX(File file) {
        ArrayList<Wijn> wijnen = new ArrayList<Wijn>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
            workbook.setMissingCellPolicy(Row.RETURN_BLANK_AS_NULL);
            int sheetNumber = 0;
            if (workbook.getNumberOfSheets() > 1) {
                for (int o = 0; o < workbook.getNumberOfSheets(); o++) {
                    if (workbook.getSheetName(o).equals("Wijnlijst in de database")) {
                        sheetNumber = o;
                    }
                }
            }
            XSSFSheet sheet = workbook.getSheetAt(sheetNumber);

            Iterator<Row> rowIterator = sheet.iterator();
            int firstRows = 0;
            int emptyRow = 0;
            String currentCategory = "";

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (firstRows < 9) {
                    firstRows++;
                } else {
                    String[] tempWijn = new String[8];
                    if ((row.getCell(2) == null) && (emptyRow < 3)) {
                        emptyRow++;
                    } else if (row.getCell(2) != null) {
                        emptyRow = 0;
                        if (row.getCell(0) == null && row.getCell(2) != null) {
                            currentCategory = row.getCell(2).toString();
                        }
                        for (int i = 0; i < 8; i++) {
                            Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
                            if (row.getCell(0) != null) {
                                if (i == 0 || i == 4) {
                                    int tempValue = (int) cell.getNumericCellValue();
                                    tempWijn[i] = Integer.toString(tempValue);
                                } else {
                                    tempWijn[i] = cell.toString();
                                }
                            }
                        }
                        try {
                            wijnen.add(
                                    new Wijn(
                                            Integer.parseInt(tempWijn[0]),        // serie_id
                                            tempWijn[2],                        // naam
                                            Double.parseDouble(tempWijn[5]),    //inkoop
                                            Double.parseDouble(tempWijn[7]),    //prijs
                                            getType(tempWijn[1]),                // type
                                            Integer.parseInt(tempWijn[4]),        // jaartal
                                            1,                                    // isactief
                                            tempWijn[3],                        // afkomst
                                            currentCategory                        // category
                                    )
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        break;
                    }
                } // end else
            } // end while
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (wijnen.isEmpty()) {
            wrongFormatAlert();
        }
        return wijnen;
    }

    /**
     * getType() zet de ingelezen wijntype string om naar een int voor het wijn IIIPSEN2.model.
     * Returns een int
     */
    private int getType(String type) {
        int typeID = 0;
        if (type.toLowerCase().equals("w")) {
            typeID = 0;
        } else if (type.toLowerCase().equals("r")) {
            typeID = 1;
        } else if (type.toLowerCase().equals("rose")) {
            typeID = 2;
        }
        return typeID;
    }

    /**
     * wrongFormatAlert() geeft een error dat het formaat fout is.
     */
    private void wrongFormatAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Wrong format");
        alert.setHeaderText("Het formaat is fout");
        alert.setContentText(
                "Het formaat moet er precies uit zien als de LionsClub in september hebben aangeleverd");
        alert.showAndWait();
    }
}
