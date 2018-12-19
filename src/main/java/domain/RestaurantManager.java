package domain;

import db.DaoPostgres;
import db.DbConfig;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

public class RestaurantManager {
    private List<Restaraunt> restarauntArrayList;
    private int id;
    private DaoPostgres DaoPostgres;
    public RestaurantManager(ArrayList<Restaraunt> restarauntArrayList) {
        DaoPostgres dao_postgres = new DaoPostgres();
        dao_postgres.Connect(DbConfig.user, DbConfig.dbPassword);
        this.restarauntArrayList = restarauntArrayList;
        id = 1;
    }


    public int getId() {
        return id;
    }

    public RestaurantManager(){
        restarauntArrayList = new ArrayList<>();
        DaoPostgres = new DaoPostgres();
        DaoPostgres.Connect(DbConfig.user, DbConfig.dbPassword);
    }

    public void addRestaurant(Restaraunt rest){
        restarauntArrayList.add(rest);

    }

    public void updateList() throws SQLException, InterruptedException {
        updateList(null);
    }

    public void updateList(JProgressBar jProgressBar) throws SQLException, InterruptedException {
        jProgressBar.setValue(10);
        ResultSet RS = DaoPostgres.selectSchema("SELECT * FROM restaurants");
        jProgressBar.setValue(50);
        Restaraunt.Builder builder = Restaraunt.newBuilder();
        int i = 0;
        int restCount = DaoPostgres.count();
        float percentperRest = 50 / restCount;
        while(RS.next()){
            jProgressBar.setValue((int) (jProgressBar.getValue() + percentperRest));
            /*for (int i = 1; i <= restaurantCount; i++){
                Map<Integer, Integer> map = new HashMap<>();
                for(int j = 0; i < 24; ++j){
                    Array arr  = RS.getArray("seatsandhours");
                    map.put(i, arr[i]);
                }*/

                builder.setSeatsAndHoursMap_(new HashMap<>());
                builder.setAverageCost_(new Cost(RS.getInt("averagecost")));
                builder.setCloseTime_(new Time(RS.getInt("closetime")));
                builder.setOpenTime_(new Time(RS.getInt("opentime")));
                builder.setCoordinates_(new Coordinates(RS.getInt("lon"), RS.getInt("lat")));
                builder.setName_(RS.getString("name"));
                builder.setRestaurantManager(this);
                builder.setId_(RS.getInt("id"));
                Restaraunt rest = builder.build();
                try {
                    this.restarauntArrayList.set(i, rest);
                }
                catch (java.lang.IndexOutOfBoundsException e){
                    this.restarauntArrayList.add(i, rest);
                }
                ++i;
            }
        }

     public int countRests(){
        return restarauntArrayList.size();
     }

    public int deleteRestaurant(String rest) throws SQLException {
        if(DaoPostgres.exists(rest)){
            DaoPostgres.delete(rest);
            return 1;
            }
        if(DaoPostgres.selectAll() == "")
            return 0;
        else
        return -1; // If restaurant with this name doesn't exist in DB
    }

    public int size(){
        return restarauntArrayList.size();
    }

    public Restaraunt getByIndex(int i){
        return restarauntArrayList.get(i);
    }


    public int restaurantCount(){
        return this.restarauntArrayList.size();
    }

    public DaoPostgres getDao() {
        return DaoPostgres;
    }
}
