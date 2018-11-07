package UserInterface;

public class single {
    private static single ourInstance = new single();

    public static single getInstance() {
        return ourInstance;
    }

    private single() {
    }
}
