package IIIPSEN2;

import IIIPSEN2.control.MailController;
import IIIPSEN2.interfaces.MailInhoud;

import java.util.prefs.Preferences;

/**
 * In deze klas staat alle informatie van een herinnering e-mail.
 * De informatie staat in de klas zelf of wordt opgehaald vanaf het template in het register.
 *
 * @author dennis
 */
public class HerinneringMail implements MailInhoud {

    private MailController mc;
    private String subject;
    private String ordernummer;
    private String aanhef;
    private String naam;
    private String body;
    private Preferences prefs;

    /**
     * CONSTRUCTOR
     * Hierin worden de prefrences voor het template in het register opgehaald.
     *
     * @param mailcontroller
     */
    public HerinneringMail(MailController mc) {
        prefs = Settings.getInstance().getPrefs();
        this.mc = mc;
    }

    /**
     * In deze methode wordt de default aanhef opgesslagen.
     * Default wordt de aanheft opgehaald uit het register
     * Wanneer het template leeg is wordt een foutmelding dialog gegeven.
     */
    public void setDefaultAanhef() {
        aanhef = prefs.get("herinneringMail_aanhef", "");
        if (aanhef.isEmpty()) {
            mc.giveTemplateNotFilledAlert(0);
            mc.reset();
        }
    }

    /**
     * Hierin wordt de default name opgeslagen.
     * default is <name>
     */
    public void setDefaulName() {
        this.naam = "<name>,";
    }

    /**
     * Deze methode set de naam
     *
     * @param name
     */
    public void setName(String name) {
        this.naam = name;
    }

    /**
     * deze methode set het ordernummer
     *
     * @param ordernumer
     */
    public void setOrderNummer(String ordernummer) {
        this.ordernummer = ordernummer;
    }

    /**
     * Deze methode set het default ordernummer
     * default is <ordernummer>
     */
    public void setDefaultOrdernummer() {
        ordernummer = "<ordernummer>";
    }

    /**
     * In deze methode wordt de default body opgeslagen.
     * Default wordt de body opgehaald uit het register.
     * Wanneer het template leeg is wordt een foutmelding dialog gegeven.
     */
    public void setDefaultBody() {
        this.body = prefs.get("herinneringMail_body", "");
        if (body.isEmpty()) {
            mc.giveTemplateNotFilledAlert(1);
            mc.reset();
        }
    }

    /**
     * In deze methode wordt de body opgeslagen
     *
     * @param body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * In deze methode wordt het default onderwerp opgeslagen.
     * Default wordt het onderwerp opgehaald uit het register.
     * Wanneer het template leeg is wordt een foutmelding dialog gegeven.
     */
    public void setDefaultSubject() {
        this.subject = prefs.get("herinneringMail_onderwerp", "");
        if (subject.isEmpty()) {
            mc.giveTemplateNotFilledAlert(2);
            mc.reset();
        }
    }

    /**
     * In deze methode wordt het onderwerp opgeslagen
     *
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * In deze methode wordt het onderwerp opgehaald.
     *
     * @return onderwerp + het ordernummer
     */
    public String getMailSubject() {
        String subjectResult = subject + " ordernummer " + ordernummer;
        return subjectResult;
    }

    /**
     * In deze methode wordt de body opgehaald.
     *
     * @return aanhef + naam + body
     */
    public String getMailBody() {
        String bodyResult = aanhef + " " + naam + ",\n\n" + body;
        return bodyResult;
    }
}