package ui;

public final class Singleton {
    private static Singleton instance;
    private Singleton(){}
    private static Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        if(instance == null){
            instance = new Singleton();
        }
        return ourInstance;
    }
}
