package IIIPSEN2;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.record.cf.PatternFormatting;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

//import javax.swing.JFileChooser;
//import javax.swing.JFrame;

/**
 * @author Sidney de Geus
 *         <p>
 *         GrootHandelBestelling is een class dat ervoor zorgt dat er een dialog omhoog komt en
 *         vervolgens er een locatie kan worden gekozen waar het bestand opgeslagen kan worden.
 *         Het bestand zal vervolgens als een XLS file worden opgeslagen.
 */

public class GrootHandelBestelling {

    private ArrayList<Wijn> wijnen;

    // Constructor

    /**
     * De constructor GrootHandelBestelling(...) zorgt ervoor dat alle wijnen meegegeven worden
     * en vervolgens worden opgeslagen in een attribuut. Ook roept het de saveDialog() method aan.
     */
    public GrootHandelBestelling(ArrayList<Wijn> wijnen) {
        this.wijnen = wijnen;
        saveDialog();
    }

    /**
     * saveDialog() laat een scherm omhoog komen om een locatie uit te zoeken
     * om het bestand op te slaan.
     */
    public void saveDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save GroothandelBestelling");
        fileChooser.setInitialFileName(
                "GroothandelBestelling " + Calendar.getInstance().get(Calendar.YEAR)
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel File", "*.xls")
        );
        File file = fileChooser.showSaveDialog(new Stage());

        createExcelFile(file);
    }

    /**
     * createExcelFile maakt een XLS bestand aan met de data die in de constructor meegegeven is.
     */
    private void createExcelFile(File file) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");


            // Styles
            HSSFCellStyle titleStyle = workbook.createCellStyle();
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            HSSFCellStyle categoryStyle = workbook.createCellStyle();
            HSSFCellStyle wijnStyle = workbook.createCellStyle();
            CellStyle margeStyle = workbook.createCellStyle();
            CellStyle priceStyle = workbook.createCellStyle();

            // set color
            categoryStyle.setFillForegroundColor(HSSFColor.VIOLET.index);
            categoryStyle.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

            //set data formats
            margeStyle.setDataFormat(workbook.createDataFormat().getFormat("00%"));
            priceStyle.setDataFormat(workbook.createDataFormat().getFormat(".00"));

            // set fonts
            HSSFFont titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 14);

            HSSFFont headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 10);

            HSSFFont categoryFont = workbook.createFont();
            categoryFont.setFontHeightInPoints((short) 12);
            categoryFont.setColor((short) HSSFColor.WHITE.index);

            HSSFFont wijnFont = workbook.createFont();
            wijnFont.setFontHeightInPoints((short) 9);


            //add font to style
            titleStyle.setFont(titleFont);
            headerStyle.setFont(headerFont);
            categoryStyle.setFont(categoryFont);
            wijnStyle.setFont(wijnFont);


            // set column width
            sheet.setColumnWidth(0, 1500);
            sheet.setColumnWidth(1, 2000);
            sheet.setColumnWidth(2, 9000);
            sheet.setColumnWidth(3, 9000);
            sheet.setColumnWidth(4, 1500);


            // START FILLING HEADER

            //row 3
            HSSFRow row3 = sheet.createRow((short) 2);
            HSSFCell title = row3.createCell(0);
            title.setCellStyle(titleStyle);
            title.setCellValue("INKOOPLIJST   Lions  PROEVERIJ");
            HSSFCell euroSign = row3.createCell(6);
            euroSign.setCellStyle(headerStyle);
            euroSign.setCellValue("��");

            //row 4
            HSSFRow row4 = sheet.createRow((short) 3);
            row4.setRowStyle(headerStyle);
            row4.createCell(5).setCellValue("inkoop");
            row4.createCell(6).setCellValue("prijs");
            row4.createCell(7).setCellValue("marge");
            row4.createCell(8).setCellValue("aantal");

            //row 5
            HSSFRow row5 = sheet.createRow((short) 4);
            HSSFCell info = row5.createCell((short) 2);
            info.setCellStyle(headerStyle);
            info.setCellValue("BESTELEENHEID IS  6 FLESSEN (= 1 doos)");

            row5.setRowStyle(headerStyle);
            row5.createCell(5).setCellValue("incl. btw");
            row5.createCell(6).setCellValue("per");
            row5.createCell(7).setCellValue("per");
            row5.createCell(8).setCellValue("dozen");

            //row 6
            HSSFRow row6 = sheet.createRow((short) 5);
            row6.setRowStyle(headerStyle);

            row6.createCell(5).setCellValue("21%");
            row6.createCell(6).setCellValue("fles");
            row6.createCell(7).setCellValue("fles");
            row6.createCell(8).setCellValue("besteld");

            // DONE FILLING HEADER

            //DecimalFormat decimalFormat = new DecimalFormat("#.00");
            int i = 7;
            String currentCategory = "";
            for (Wijn w : wijnen) {
                if (!currentCategory.equals(w.getWijnCategory())) {
                    currentCategory = w.getWijnCategory();
                    i++;
                    HSSFRow row = sheet.createRow((short) i);
                    for (int c = 0; c < 9; c++) {
                        HSSFCell colorCell = row.createCell(c);
                        colorCell.setCellStyle(categoryStyle);
                    }
                    HSSFCell categoryCell = row.createCell(2);
                    categoryCell.setCellStyle(categoryStyle);
                    categoryCell.setCellValue(w.getWijnCategory());


                    i += 2;
                }
                HSSFRow row = sheet.createRow((short) i);
                i++;
                row.createCell(0).setCellValue(w.getWijnSerieID());
                row.createCell(1).setCellValue(w.getStringWijnType());
                row.createCell(2).setCellValue(w.getWijnNaam());
                row.createCell(3).setCellValue(w.getWijnAfkomst());
                row.createCell(4).setCellValue(w.getWijnJaartal());
                //row.createCell(4).setCellValue(w.getWijnMerk());

                HSSFCell inkoopPrijsCell = row.createCell(5);
                inkoopPrijsCell.setCellStyle(priceStyle);
                inkoopPrijsCell.setCellValue(w.getInkoopPrijs());

                HSSFCell prijsCell = row.createCell(6);
                prijsCell.setCellStyle(priceStyle);
                prijsCell.setCellValue(new Double(String.format(Locale.US, "%.2f", w.getPrijs())));

                HSSFCell margeCell = row.createCell(7);
                margeCell.setCellStyle(margeStyle);
                margeCell.setCellFormula("(G" + i + "-F" + i + ")/G" + i);

                if (w.getTotaalAantalDozen() != 0) {
                    row.createCell(8).setCellValue(w.getTotaalAantalDozen());
                }
            }

            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
