package Domain;

import java.util.ArrayList;
import java.util.List;

public class RestaurantManager {
    private List<Restaraunt> restarauntArrayList;

    public RestaurantManager(ArrayList<Restaraunt> restarauntArrayList) {
        this.restarauntArrayList = restarauntArrayList;
    }

    public RestaurantManager(){
        restarauntArrayList = new ArrayList<>();
    }

    public void addRestaurant(Restaraunt rest){
        restarauntArrayList.add(rest);
    }

    public int deleteRestaurant(String rest){
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
