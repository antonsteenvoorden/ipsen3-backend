package View;

/**
 * Created by Anton on 07/01/2016.
 */
public class View {
    public static class Internal extends Private {}

    public static class Private extends Protected {}

    public static class Protected extends Public {}

    public static class Public {}
}
