package IIIPSEN2;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import IIIPSEN2.control.WijnController;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.Date;

/**
 * @author Jordan
 *         Dit is om de bestellijst te genereren als PDF.
 */
public class BestelkaartGenerator {

    WijnController wijn = new WijnController();
    public String voornaam, achternaam, plaatsnaam, postcodeToevoeging, email, telefoon, straatnaam, tussenvoegsel,
            huisnummerToevoeging;
    public int postcode, huisnummer;
    public Klant klant;
    public PDDocument doc;
    public PDPage page;
    public PDPageContentStream content;

    public BestelkaartGenerator(Klant klant, PDDocument doc) {
        this.klant = klant;
        this.doc = doc;
        genereerBestellijst(doc);
    }

    /**
     * Dit is om de PDF te maken. Dit wordt met PDF Box gedaan.
     *
     * @param doc
     */
    public void genereerBestellijst(PDDocument doc) {
        // PDF Generator | Author: Jordan Munk
        try {
            page = new PDPage();
            doc.addPage(page);

            content = new PDPageContentStream(doc, page);
            // of

            // Bestellijst header
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 20);
            content.moveTextPositionByAmount(180, 750);
            content.drawString("Bestellijst Benefiet Wijnfestijn");
            content.endText();

            // Informatie
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 10);
            content.moveTextPositionByAmount(80, 700);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            content.drawString(dateFormat.format(date));
            content.endText();

            content.beginText();
            content.moveTextPositionByAmount(80, 685);
            content.drawString("Kasteel Oud Poelgeest");
            content.endText();

            // Gegevens klant
            content.beginText();
            content.moveTextPositionByAmount(80, 650);
            content.drawString("Naam:");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(80, 635);
            content.drawString("Adres");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(80, 620);
            content.drawString("Woonplaats:");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(80, 605);
            content.drawString("Postcode:");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(80, 590);
            content.drawString("Telefoon nummer:");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(80, 575);
            content.drawString("E-mail adres:");
            content.endText();

            content.beginText();
            content.moveTextPositionByAmount(250, 650);
            if (klant.getTussenvoegsel() == null || klant.getTussenvoegsel().isEmpty()) {
                content.drawString(klant.getVoornaam() + " " + klant.getAchternaam());
            } else {
                content.drawString(klant.getVoornaam() + " " + klant.getTussenvoegsel() + " " + klant.getAchternaam());
            }
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(250, 635);
            content.drawString(klant.getStraatnaam() + " " + klant.getHuisNummer() + klant.getHuisNummerToevoeging());
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(250, 620);
            content.drawString(klant.getPlaatsNaam());
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(250, 605);
            content.drawString(klant.getPostcode() + " " + klant.getPostcodeToevoeging());
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(250, 590);
            content.drawString("-");

            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(250, 575);
            content.drawString(klant.getEmail());
            content.endText();

            content.setLineWidth(1);
            content.addLine(80, 565, 420, 565);
            content.closeAndStroke();

            content.setLineWidth(1);
            content.addLine(80, 670, 420, 670);
            content.closeAndStroke();

            content.beginText();
            content.moveTextPositionByAmount(80, 530);
            content.drawString("Besteleenheid is 6 flessen");
            content.endText();

            // Tabel
            // De kopjes

            content.setLineWidth(1);
            content.addLine(20, 500, 585, 500);
            content.closeAndStroke();
            content.setLineWidth(1);
            content.addLine(20, 465, 585, 465);
            content.closeAndStroke();

            content.setLineWidth(1);
            content.addLine(20, 500, 20, 465);
            content.closeAndStroke();
            content.setLineWidth(1);
            content.addLine(45, 500, 45, 465);
            content.closeAndStroke();
            content.setLineWidth(1);
            content.addLine(95, 500, 95, 465);
            content.closeAndStroke();
            content.setLineWidth(1);
            content.addLine(145, 500, 145, 465);
            content.closeAndStroke();
            content.setLineWidth(1);
            content.addLine(305, 500, 305, 465);
            content.closeAndStroke();
            content.setLineWidth(1);
            content.addLine(465, 500, 465, 465);
            content.closeAndStroke();
            content.setLineWidth(1);
            content.addLine(505, 500, 505, 465);
            content.closeAndStroke();
            content.setLineWidth(1);
            content.addLine(545, 500, 545, 465);
            content.closeAndStroke();
            content.setLineWidth(1);
            content.addLine(585, 500, 585, 465);
            content.closeAndStroke();

            content.beginText();
            content.moveTextPositionByAmount(25, 485);
            content.drawString("Nr.");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(50, 485);
            content.drawString("Aantal");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(50, 470);
            content.drawString("dozen");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(100, 485);
            content.drawString("Wijn type");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(150, 485);
            content.drawString("Wijn naam");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(310, 485);
            content.drawString("Wijn afkomst");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(470, 485);
            content.drawString("Jaar");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(510, 485);
            content.drawString("Prijs");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(510, 470);
            content.drawString("fles");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(550, 485);
            content.drawString("Prijs");
            content.endText();
            content.beginText();
            content.moveTextPositionByAmount(550, 470);
            content.drawString("doos");
            content.endText();

            int count = 0;
            int count2 = 0;
            String currentCategory = "";
            for (Wijn e : wijn.getWijnBestellijst()) {
                count += 1;
                count2 += 1;

                if (!currentCategory.equals(e.getWijnCategory())) {
                    content.setFont(PDType1Font.HELVETICA_BOLD, 10);
                    content.setLineWidth(1);
                    content.addLine(20, 445 - (20 * (count2 - 1)), 585, 445 - (20 * (count2 - 1)));
                    content.closeAndStroke();

                    content.setLineWidth(1);
                    content.addLine(20, 465 - (20 * (count2 - 1)), 20, 445 - (20 * (count2 - 1)));
                    content.closeAndStroke();
                    content.setLineWidth(1);
                    content.addLine(45, 465 - (20 * (count2 - 1)), 45, 445 - (20 * (count2 - 1)));
                    content.closeAndStroke();
                    content.setLineWidth(1);
                    content.addLine(95, 465 - (20 * (count2 - 1)), 95, 445 - (20 * (count2 - 1)));
                    content.closeAndStroke();
                    content.setLineWidth(1);
                    content.addLine(145, 465 - (20 * (count2 - 1)), 145, 445 - (20 * (count2 - 1)));
                    content.closeAndStroke();
                    content.setLineWidth(1);
                    content.addLine(305, 465 - (20 * (count2 - 1)), 305, 445 - (20 * (count2 - 1)));
                    content.closeAndStroke();
                    content.setLineWidth(1);
                    content.addLine(465, 465 - (20 * (count2 - 1)), 465, 445 - (20 * (count2 - 1)));
                    content.closeAndStroke();
                    content.setLineWidth(1);
                    content.addLine(505, 465 - (20 * (count2 - 1)), 505, 445 - (20 * (count2 - 1)));
                    content.closeAndStroke();
                    content.setLineWidth(1);
                    content.addLine(545, 465 - (20 * (count2 - 1)), 545, 445 - (20 * (count2 - 1)));
                    content.closeAndStroke();
                    content.setLineWidth(1);
                    content.addLine(585, 465 - (20 * (count2 - 1)), 585, 445 - (20 * (count2 - 1)));
                    content.closeAndStroke();

                    content.beginText();
                    content.moveTextPositionByAmount(150, 450 - (20 * (count2 - 1)));
                    content.drawString(e.getWijnCategory());
                    content.endText();

                    currentCategory = e.getWijnCategory();
                    count += 1;
                    count2 += 1;
                }

                content.setFont(PDType1Font.HELVETICA, 10);

                if (count2 == 21) {
                    content.close();

                    PDPage page1 = new PDPage();
                    doc.addPage(page1);

                    content = new PDPageContentStream(doc, page1);

                    // Bestellijst header
                    content.beginText();
                    content.setFont(PDType1Font.HELVETICA, 10);
                    content.endText();

                    count2 = -14;

                    content.setLineWidth(1);
                    content.addLine(20, 764, 585, 764);
                    content.closeAndStroke();

                    content.beginText();
                    content.moveTextPositionByAmount(470, 450 - (20 * (count2 - 1)));
                    content.drawString(Double.toString(e.getPrijs()));
                    content.endText();
                }
                content.setLineWidth(1);
                content.addLine(20, 445 - (20 * (count2 - 1)), 585, 445 - (20 * (count2 - 1)));
                content.closeAndStroke();

                content.setLineWidth(1);
                content.addLine(20, 465 - (20 * (count2 - 1)), 20, 445 - (20 * (count2 - 1)));
                content.closeAndStroke();
                content.setLineWidth(1);
                content.addLine(45, 465 - (20 * (count2 - 1)), 45, 445 - (20 * (count2 - 1)));
                content.closeAndStroke();
                content.setLineWidth(1);
                content.addLine(95, 465 - (20 * (count2 - 1)), 95, 445 - (20 * (count2 - 1)));
                content.closeAndStroke();
                content.setLineWidth(1);
                content.addLine(145, 465 - (20 * (count2 - 1)), 145, 445 - (20 * (count2 - 1)));
                content.closeAndStroke();
                content.setLineWidth(1);
                content.addLine(305, 465 - (20 * (count2 - 1)), 305, 445 - (20 * (count2 - 1)));
                content.closeAndStroke();
                content.setLineWidth(1);
                content.addLine(465, 465 - (20 * (count2 - 1)), 465, 445 - (20 * (count2 - 1)));
                content.closeAndStroke();
                content.setLineWidth(1);
                content.addLine(505, 465 - (20 * (count2 - 1)), 505, 445 - (20 * (count2 - 1)));
                content.closeAndStroke();
                content.setLineWidth(1);
                content.addLine(545, 465 - (20 * (count2 - 1)), 545, 445 - (20 * (count2 - 1)));
                content.closeAndStroke();
                content.setLineWidth(1);
                content.addLine(585, 465 - (20 * (count2 - 1)), 585, 445 - (20 * (count2 - 1)));
                content.closeAndStroke();

                content.beginText();
                content.moveTextPositionByAmount(25, 450 - (20 * (count2 - 1)));
                content.drawString(Integer.toString(e.getWijnSerieID()));
                content.endText();
                content.beginText();
                content.moveTextPositionByAmount(100, 450 - (20 * (count2 - 1)));
                content.drawString(e.getStringWijnType());
                content.endText();
                content.beginText();
                content.moveTextPositionByAmount(150, 450 - (20 * (count2 - 1)));
                content.drawString(e.getWijnNaam());
                content.endText();
                content.beginText();
                content.moveTextPositionByAmount(310, 450 - (20 * (count2 - 1)));
                content.drawString(e.getWijnAfkomst());
                content.endText();
                content.beginText();
                content.moveTextPositionByAmount(470, 450 - (20 * (count2 - 1)));
                content.drawString(Integer.toString(e.getWijnJaartal()));
                content.endText();
                content.beginText();
                content.moveTextPositionByAmount(510, 450 - (20 * (count2 - 1)));
                content.drawString(round(e.getPrijs()));
                content.endText();
                content.beginText();
                content.moveTextPositionByAmount(550, 450 - (20 * (count2 - 1)));
                content.drawString(round(e.getPrijs() * 6));
                content.endText();
            }

            content.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    /**
     * Dit is om af te ronden op 2 decimalen
     *
     * @param value
     * @return
     */
    public static String round(double value) {
        String result = String.format("%.2f", value);

        return result;
    }
}
