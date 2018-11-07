import Domain.RestaurantManager;
import UserInterface.AwtDemo;

import java.awt.*;

public class Main {
    public static void main(String args[]) {
        RestaurantManager restaurantManager = new RestaurantManager();
        AwtDemo awtControlDemo = new AwtDemo(restaurantManager);
        awtControlDemo.initialize(restaurantManager);
        System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
}
}
