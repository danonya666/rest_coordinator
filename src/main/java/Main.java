import DataBase.JdbcInitialization;
import Domain.RestaurantManager;
import UserInterface.AwtDemo;

import javax.naming.NamingException;
import java.awt.*;
import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws SQLException, NamingException, ClassNotFoundException {/*
        RestaurantManager restaurantManager = new RestaurantManager();
        AwtDemo awtControlDemo = new AwtDemo(restaurantManager);
        awtControlDemo.initialize(restaurantManager);
        System.out.println(Toolkit.getDefaultToolkit().getScreenSize());*/

        JdbcInitialization test = new JdbcInitialization();
}
}
