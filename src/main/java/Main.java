import DataBase.dao_postgres;
import DataBase.JdbcInitialization;
import DataBase.dbConfig;
import Domain.*;
import Domain.Time;
import UserInterface.AwtDemo;

import javax.naming.NamingException;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String args[]) throws SQLException, NamingException, ClassNotFoundException {/*
        RestaurantManager restaurantManager = new RestaurantManager();
        AwtDemo awtControlDemo = new AwtDemo(restaurantManager);
        awtControlDemo.initialize(restaurantManager);
        System.out.println(Toolkit.getDefaultToolkit().getScreenSize());*/
        //dao_postgres dp = new dao_postgres();
        //dp.Connect("postgres", dbConfig.dbPassword);
        //String rs = dp.select("Select name FROM restaurants;");
        //System.out.println(rs);
        //RestaurantManager rM = new RestaurantManager();
        //rM.updateList();
        //System.out.println(rM.getByIndex(1).getName_());
        RestaurantManager restaurantManager = new RestaurantManager();
        AwtDemo awtDemo = new AwtDemo(restaurantManager);
        awtDemo.initialize(restaurantManager);
        /*//JdbcInitialization test = new JdbcInitialization();
        dao_postgres dao_postgres = new dao_postgres();

        String result = dao_postgres.select("SELECT * FROM restaurants");
        Restaraunt.Builder builder = Restaraunt.newBuilder();
        builder.setRestaurantManager(new RestaurantManager());
        //builder.setSeatsAndHoursMap_(new Ma)
        builder.setName_("CraftBier");
        builder.setCoordinates_(new Coordinates(100, 100));
        builder.setOpenTime_(new Time(12, 0));
        builder.setCloseTime_(new Time(23, 0));
        builder.setAverageCost_(new Cost(322));
        Map map = new HashMap<Integer, Integer>();
        map.put(0, 5);
        map.put(1, 10);
        builder.setSeatsAndHoursMap_(map);
        Restaraunt restaraunt = builder.build();
        System.out.println(restaraunt.getSeatsAndHoursMap__().get(0));
        dao_postgres.insert(restaraunt);
    */}
}
