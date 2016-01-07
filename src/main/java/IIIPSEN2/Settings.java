/**
 *
 */
package IIIPSEN2;

import IIIPSEN2.view.Main;

import java.util.prefs.Preferences;

/**
 * @author Anton
 *         Maakt hier de verbinding met het register aan.
 *         <p>
 *         Go into your Start Menu and type regedit into the search field.
 *         Navigate to path HKEY_LOCAL_MACHINE\Software\JavaSoft
 *         Right click on the JavaSoft folder and click on New -> Key
 *         Name the new Key Prefs and everything should work.
 *         <p>
 *         Voer insert.reg uit om de sheit te maken
 */
public class Settings {
    private Preferences prefs;
    private static Settings uniqueInstance = new Settings();
    ;

    /**
     * singleton constructor
     */
    private Settings() {
        super();
        synchronized (Main.class) {
            if (uniqueInstance != null) throw new UnsupportedOperationException(
                    getClass() + " is singleton but constructor called more than once");
            prefs = Preferences.userNodeForPackage(this.getClass());
            uniqueInstance = this;
        }
    }

    public static synchronized Settings getInstance() {
        return uniqueInstance;
    }

    public Preferences getPrefs() {
        return prefs;
    }
}
