package Domain;

import DataBase.dao_postgres;
import DataBase.dbConfig;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantManager {
    private List<Restaraunt> restarauntArrayList;
    private int id;
    private dao_postgres dao_postgres;
    public RestaurantManager(ArrayList<Restaraunt> restarauntArrayList) {
        dao_postgres dao_postgres = new dao_postgres();
        dao_postgres.Connect(dbConfig.user, dbConfig.dbPassword);
        this.restarauntArrayList = restarauntArrayList;
        id = 1;
    }


    public int getId() {
        return id;
    }

    public RestaurantManager(){
        restarauntArrayList = new ArrayList<>();
        dao_postgres = new dao_postgres();
        dao_postgres.Connect(dbConfig.user, dbConfig.dbPassword);
    }

    public void addRestaurant(Restaraunt rest){
        restarauntArrayList.add(rest);

    }

    public void updateList() throws SQLException {
        int restaurantCount = dao_postgres.count();
        ResultSet RS = dao_postgres.selectSchema("SELECT * FROM restaurants");
        Restaraunt.Builder builder = Restaraunt.newBuilder();
        int i = 0;
        while(RS.next()){
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

    public int deleteRestaurant(String rest){
        dao_postgres dao_postgres = new dao_postgres();
        dao_postgres.Connect("postgres", dbConfig.dbPassword);
        try {
            String rs = dao_postgres.select("SELECT name FROM restaurants WHERE name = \'" + rest + "\'");
            if(rs == ""){
                return -1;
            }
            else{
                System.out.println("rs = " + rs);
                dao_postgres.delete(rs);
                return 1;
            }
        } catch (SQLException e1) {
            System.out.println("ERROR");
            e1.printStackTrace();
        }
        if(restarauntArrayList.isEmpty())
            return 0;
        else{
            for(int i = 0; i < restarauntArrayList.size(); ++i){
                Restaraunt restaraunt = restarauntArrayList.get(i);
                if(restaraunt.getName_().equals(rest)){
                    restarauntArrayList.remove(i);
                    return 1;
                }
            }
            return -1;
        }
    }

    public int size(){
        return restarauntArrayList.size();
    }

    public Restaraunt getByIndex(int i){
        return restarauntArrayList.get(i);
    }

    public void update(){

    }

    public int restaurantCount(){
        return this.restarauntArrayList.size();
    }
}
