import domain.RestaurantManager;
import ui.AwtDemo;

import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws SQLException {
        RestaurantManager restaurantManager = new RestaurantManager();
        AwtDemo awtDemo = new AwtDemo(restaurantManager);
        awtDemo.initialize(restaurantManager);
    }
}
