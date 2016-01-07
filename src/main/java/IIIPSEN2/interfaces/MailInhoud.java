package IIIPSEN2.interfaces;

/**
 * Interface die de mailinhoud methoden afdwingt te implementeren.
 *
 * @author dennis
 */
public interface MailInhoud {
    public void setDefaultAanhef();

    public void setDefaulName();

    public void setName(String name);

    public void setOrderNummer(String ordernummer);

    public void setDefaultOrdernummer();

    public void setDefaultBody();

    public void setBody(String body);

    public void setDefaultSubject();

    public void setSubject(String subject);

    public String getMailSubject();

    public String getMailBody();
}
