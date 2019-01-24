package domain;

import common.Time;
import db.DaoBase;
import db.DbConfig;
import db.RestDao;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.lang.Thread.sleep;

public class RestaurantManager {
    private List<Restaurant> restaurantArrayList;
    private int id;
    private DaoBase restDao;
    private DaoBase restaurantDao;
    public RestaurantManager(ArrayList<Restaurant> restaurantArrayList) {
        DaoBase daoBase = new RestDao();
        this.restaurantArrayList = restaurantArrayList;
        id = 1;
    }

    public int getId() {
        return id;
    }

    public RestaurantManager(DaoBase restDao){
        restaurantDao = restDao;
    }

    public RestaurantManager(){
        restaurantArrayList = new ArrayList<>();
        restDao = new RestDao();
    }

    public void addRestaurant(Restaurant rest){
        restDao.create(rest);
    }

    public void updateList() throws SQLException, InterruptedException {
        updateList(null);
    }

    public void updateList(JProgressBar jProgressBar) throws SQLException, InterruptedException {
        List<Restaurant> restaurants = new LinkedList<>();
        restaurants = restDao.getAll();
        restaurantArrayList = restaurants;
    }


     public int countRests() throws SQLException {
        return restDao.count();
     }

    public int deleteRestaurant(String rest) throws SQLException {
        if(restDao.exists(rest)){
            restDao.delete(rest);
            return 1;
            }
        if(restDao.getAll() == null)
            return 0;
        else
        return -1; // If restaurant with this name doesn't exist in DB
    }

    public int size(){
        return restaurantArrayList.size();
    }

    public Restaurant getByIndex(int i){
        return restaurantArrayList.get(i);
    }


    public int restaurantCount(){
        return this.restaurantArrayList.size();
    }

    public DaoBase getDao() {
        return restDao;
    }
}
