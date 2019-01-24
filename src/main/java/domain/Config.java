package domain;


import java.awt.*;

public class Config {
    public static final int maxAverageCostInRubles = 100000000;
    public static final String standardCurrencyStr = "standard";
    public static final String standardCurrency = "rubles";

    public static int getScreenResolutionHeight(){
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }
    public static int getScreenResolutionWidth(){
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }
}
