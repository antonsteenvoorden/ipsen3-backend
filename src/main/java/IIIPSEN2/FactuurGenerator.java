package IIIPSEN2;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import IIIPSEN2.control.FactuurController;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.prefs.Preferences;

/**
 * Dit is om een factuur te maken als PDF. Het wordt via PDFBox ontworpen.
 *
 * @author Jordan
 */

public class FactuurGenerator {
    public int[] codes;
    public int[] aantal;
    public String[] wijn;
    public int[] jaar;
    public double[] fles;
    public double[] bedrag;
    PDDocument doc;
    private FactuurController factuurController;
    private int aantalOrders;
    private String klantNaam, klantAchternaam, klantAdres;
    private String bankRekening;
    private String inschrijfNummer;
    private String bericht;
    private String adres;
    private String postcode;
    private int klantNummer, factuurID;
    private Date factuurDatum;
    private ObservableList<OrderRegel> orderList;
    private Order order;
    private Klant klant;
    private PDPageContentStream content;
    private int test;
    private Preferences prefs;

    public FactuurGenerator(Klant klant, Order order, ObservableList<OrderRegel> orderList) {
        prefs = Settings.getInstance().getPrefs();
        bankRekening = prefs.get("factuur_bankRekening", "");
        inschrijfNummer = prefs.get("factuur_inschrijfNummer", "");
        bericht = prefs.get("factuur_body", "");
        adres = prefs.get("factuur_adres", "");
        postcode = prefs.get("factuur_postcode", "");
        this.order = order;
        this.klant = klant;
        this.orderList = orderList;

        this.factuurController = new FactuurController();
    }

    /**
     * Afronden op 2 decimalen
     *
     * @param value
     * @return
     */
    public static String round(double value) {
        String result = String.format("%.2f", value);

        return result;
    }

    /**
     * Dit is de methode om de factuur op te slaan in de Database.
     */

    public void factuurDB() {
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
            String fileName = mapNaam + "/Factuur_" + klant.getEmail() + "_"
                    + order.getOrderID() + ".pdf"; // name

            doc = new PDDocument();
            PDPage page = new PDPage();

            doc.addPage(page);

            content = new PDPageContentStream(doc, page);

            generate();

            content.close();

            File file = new File(fileName);

            doc.save(file);

            doc.close();

			/* Author Dennis */
            Factuur factuur = new Factuur(file, false, order.getOrderID());
            factuurController.insertFactuur(factuur);
            /*---------------*/

        } catch (IOException | COSVisitorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Dit is de normale methode om een factuur te genereren. Dit wordt gedaan via PDFBox.
     */

    public void factuur() {
        try {
            String fileName = "Factuur_" + klant.getEmail() + "_" + order.getOrderID()
                    + ".pdf"; // name

            doc = new PDDocument();
            PDPage page = new PDPage();

            doc.addPage(page);

            content = new PDPageContentStream(doc, page);

            generate();

            content.close();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Factuur opslaan");
            fileChooser.setInitialFileName(fileName);
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF File", "*.pdf"));
            File file = fileChooser.showSaveDialog(new Stage());

            doc.save(file);

            doc.close();

        } catch (IOException | COSVisitorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Als de string leeg is geef het "".
     *
     * @param string
     * @return
     */
    public String testString(String string) {
        String tmpString;
        if (string == null || string.isEmpty()) {
            tmpString = "";
        } else {
            tmpString = string;
        }
        return tmpString;
    }

    /**
     * Het genereren van het PDF bestand. Hier wordt het ontworpen.
     */
    public void generate() {
        try {
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 20);
            content.moveTextPositionByAmount(180, 750);
            content.drawString("Lionsclub Oegstgeest/Warmond");
            content.endText();

            // Gegevens klant
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.moveTextPositionByAmount(80, 700);
            if (klant.getTussenvoegsel() == null || klant.getTussenvoegsel().isEmpty()) {
                content.drawString(testString(klant.getVoornaam()) + " " + testString(klant.getAchternaam()));
            } else {
                content.drawString(klant.getVoornaam() + " " + klant.getTussenvoegsel() + " " + klant.getAchternaam());
            }
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(80, 685);
            content.drawString(order.getFactuurAdres());
            content.endText();

            content.beginText();
            content.moveTextPositionByAmount(80, 670);
            content.endText();

            // Factuur gegevens
            content.beginText();
            content.moveTextPositionByAmount(80, 630);
            content.drawString("Factuurdatum		:");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(210, 630);
            content.drawString(order.getOrderDatum().toString());
            content.endText();

            content.beginText();
            content.moveTextPositionByAmount(80, 615);
            content.drawString("Factuurnummer		:");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(210, 615);
            content.drawString(Integer.toString(order.getOrderID()));
            content.endText();

            content.beginText();
            content.moveTextPositionByAmount(80, 600);
            content.drawString("Debiteurennummer		:");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(210, 600);
            content.drawString("-");
            content.endText();

            // Betreft
            content.beginText();
            content.moveTextPositionByAmount(80, 560);
            content.drawString("Betreft		:");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(210, 560);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            content.drawString("Wijnbestelling Benefiet Wijnfestijn Oud Poelgeest " + dateFormat.format(date));
            content.endText();

            // Factuur
            content.beginText();
            content.moveTextPositionByAmount(80, 500);
            content.drawString("Code");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(120, 500);
            content.drawString("Aantal");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(170, 500);
            content.drawString("Wijn");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(400, 500);
            content.drawString("Jaar");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(440, 500);
            content.drawString("Per fles");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(520, 500);
            content.drawString("Bedrag");
            content.endText();

            content.setLineWidth(1);
            content.addLine(60, 480, 580, 480);
            content.closeAndStroke();

            aantalOrders = 0;
            int code = 0;
            double ordersTotaal = 0;
            for (OrderRegel e : orderList) {
                code += 1;

                int height = 460 - (aantalOrders * 15);
                ordersTotaal += e.getTotaalPrijs();

                content.beginText();
                content.moveTextPositionByAmount(95, height);
                content.drawString(Integer.toString(code));
                content.endText();
                content.beginText();
                content.moveTextPositionByAmount(140, height);
                content.drawString(Integer.toString(e.getAantal()));
                content.endText();
                content.beginText();
                content.moveTextPositionByAmount(170, height);
                content.drawString(e.getWijnNaam());
                content.endText();
                content.beginText();
                content.moveTextPositionByAmount(400, height);
                content.drawString(Integer.toString(e.getWijnJaartal()));
                content.endText();
                content.beginText();
                content.moveTextPositionByAmount(450, height);
                content.drawString(round(e.getWijnPrijs()));
                content.endText();
                content.beginText();
                content.moveTextPositionByAmount(523, height);
                content.drawString(round(e.getTotaalPrijs()));
                content.endText();

                aantalOrders += 1;

                if (aantalOrders == 23) {
                    content.close();

                    PDPage page1 = new PDPage();
                    doc.addPage(page1);

                    content = new PDPageContentStream(doc, page1);

                    // Bestellijst header
                    content.beginText();
                    content.setFont(PDType1Font.HELVETICA, 12);
                    content.endText();

                    aantalOrders = -21;
                }
            }

            // Factuur totaal
            content.setLineWidth(1);
            content.addLine(500, 440 - (aantalOrders - 1) * 15, 580, 440 - (aantalOrders - 1) * 15);
            content.closeAndStroke();
            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 12);
            content.moveTextPositionByAmount(80, 420 - (aantalOrders - 1) * 15);
            content.drawString("Totaal");
            content.endText();
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 12);
            content.moveTextPositionByAmount(523, 420 - (aantalOrders - 1) * 15);
            content.drawString(round(ordersTotaal));
            content.endText();

            // Footer
            content.setLineWidth(1);
            content.addLine(60, 90, 600, 90);
            content.closeAndStroke();
            content.beginText();
            content.moveTextPositionByAmount(80, 70);
            content.drawString("Lionsclub Oegstgeest/Warmond");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(80, 55);
            content.drawString("Bankrekening: " + bankRekening);
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(80, 40);
            content.drawString("Inschrijfnummer KvK Rijnland: " + inschrijfNummer);
            content.endText();

            if (aantalOrders >= 8 && aantalOrders <= 23) {
                content.close();

                PDPage page1 = new PDPage();
                doc.addPage(page1);

                content = new PDPageContentStream(doc, page1);

                aantalOrders = -23;

                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 12);
                content.endText();

                // Footer
                content.setLineWidth(1);
                content.addLine(60, 90, 600, 90);
                content.closeAndStroke();
                content.beginText();
                content.moveTextPositionByAmount(80, 70);
                content.drawString("Lionsclub Oegstgeest/Warmond");
                content.endText();
                content.beginText();
                content.moveTextPositionByAmount(80, 55);
                content.drawString("Bankrekening: " + bankRekening);
                content.endText();
                content.beginText();
                content.moveTextPositionByAmount(80, 40);
                content.drawString("Inschrijfnummer KvK Rijnland: " + inschrijfNummer);
                content.endText();
            }

            // Verzoek
            content.beginText();
            content.moveTextPositionByAmount(80, 370 - (aantalOrders - 1) * 15);
            content.drawString("Wij verzoeken u vriendelijk het totaalbedrag binnen 7 dagen "
                    + "na factuurdatum over te maken op");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(80, 355 - (aantalOrders - 1) * 15);
            content.drawString("bankrekening Lionsclub Oegstgeest/Warmond onder vermelding van het factuurnummer");
            content.endText();

            // Adres

            content.beginText();
            content.moveTextPositionByAmount(80, 280 - (aantalOrders - 1) * 15);
            content.drawString(bericht);
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(80, 260 - (aantalOrders - 1) * 15);
            content.drawString("Adres:");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(100, 240 - (aantalOrders - 1) * 15);
            content.drawString("Noordman Wijnimport");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(100, 225 - (aantalOrders - 1) * 15);
            content.drawString(adres);
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(100, 210 - (aantalOrders - 1) * 15);
            content.drawString(postcode);
            content.endText();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
