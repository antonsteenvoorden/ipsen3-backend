package IIIPSEN2.control;

import java.util.prefs.Preferences;

/**
 * @author Anton Steenvoorden
 *         Handelt alle instelling af door middel van de Settings klasse.
 */
public class SettingsController {
    private Preferences prefs;

    public SettingsController(Preferences prefs) {
        this.prefs = prefs;
    }

    /**
     * @param id, ingevoerd bij mailadres textfield
     * @param pw, het ingevoerde wachtwoord;
     */
    public void opslaanLogin(String id, String pw) {
        prefs.put("mail_id", id);
        prefs.put("mail_pw", pw);
    }

    /**
     * @param aanhef, dit is de bovenste regel van de mail
     * @param de      rest van de text in het bericht ( komt uit de textarea)
     */
    public void saveDankMail(String onderwerp, String aanhef, String body) {
        prefs.put("dankMail_onderwerp", onderwerp);
        prefs.put("dankMail_aanhef", aanhef);
        prefs.put("dankMail_body", body);
    }

    /**
     * @param aanhef, dit is de bovenste regel van de mail
     * @param de      rest van de text in het bericht ( komt uit de textarea)
     */
    public void saveHerinneringMail(String onderwerp, String aanhef, String body) {
        prefs.put("herinneringMail_onderwerp", onderwerp);
        prefs.put("herinneringMail_aanhef", aanhef);
        prefs.put("herinneringMail_body", body);
    }

    /**
     * @param aanhef, dit is de bovenste regel van de mail
     * @param de      rest van de text in het bericht ( komt uit de textarea)
     */
    public void saveUitnodigingMail(String onderwerp, String aanhef, String body) {
        prefs.put("uitnodigingMail_onderwerp", onderwerp);
        prefs.put("uitnodigingMail_aanhef", aanhef);
        prefs.put("uitnodigingMail_body", body);
    }

    public void saveFactuur(String bankRekening, String inschrijfNummer, String adres, String postcode, String
            bericht) {
        prefs.put("factuur_bankRekening", bankRekening);
        prefs.put("factuur_inschrijfNummer", inschrijfNummer);
        prefs.put("factuur_adres", adres);
        prefs.put("factuur_postcode", postcode);
        prefs.put("factuur_body", bericht);
    }
}
