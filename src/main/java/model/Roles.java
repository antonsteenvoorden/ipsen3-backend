package model;

/**
 * Edited by:
 * - Roger
 */
public enum Roles {
    role1("KLANT"),
    role2("LIONS_LID"),
    role3("LIONS_M&S"),
    role4("ADMIN");

    private final String name;

    private Roles(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

}
