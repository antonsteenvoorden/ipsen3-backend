package IIIPSEN2;

import java.io.File;

/**
 * Created by Roger Bosman on 9-10-2015.
 */
public class Factuur {
    private File Factuur;
    private boolean isVerzonden;
    private int orderID;

    public File getFactuur() {
        return Factuur;
    }

    public void setFactuur(File factuur) {
        Factuur = factuur;
    }

    public boolean isVerzonden() {
        return isVerzonden;
    }

    public void setIsVerzonden(boolean isVerzonden) {
        this.isVerzonden = isVerzonden;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Factuur(File factuur, boolean isVerzonden, int orderID) {

        Factuur = factuur;
        this.isVerzonden = isVerzonden;
        this.orderID = orderID;
    }
}
