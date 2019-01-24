package pbar;

import common.Coordinates;
import common.Cost;
import common.Time;
import domain.Restaurant;
import domain.RestaurantManager;
import ui.FillDataBaseFrame;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class FillHandler {
    public static boolean inProgress = false;

    public static void perform(){
        ProgressObserver observer = new ProgressObserver();
        ProgressWorker worker = new ProgressWorker(observer);
        Thread thread = new Thread(worker);
        thread.start();
    }
}

class ProgressObserver implements Observer{

    @Override
    public void stateChanged(int state) {
        FillDataBaseFrame.progressBar.setValue(state);
    }
}

class ProgressWorker implements Runnable{
    int counter;
    private Observer observer;
    RestaurantManager restaurantManager;
    ProgressWorker(Observer observer){
        counter = 0;
        this.observer = observer;
        restaurantManager = new RestaurantManager();
    }
    @Override
    public void run() {
        FillHandler.inProgress = true;
        try{
            while (counter < 100){
                Restaurant restaurant = generateRest();
                restaurantManager.addRestaurant(restaurant);
                observer.stateChanged(counter);
                FillDataBaseFrame.restCountLabel.setText(String.valueOf(restaurantManager.countRests()) + " restaurants in your database");
                Thread.sleep(200);
                counter++;
            }
            FillHandler.inProgress = false;
        }
        catch (InterruptedException e){
            e.printStackTrace();
            FillHandler.inProgress = false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Restaurant generateRest() {
        Random random = new Random();
        Map map = new HashMap();
        for (int i = 0; i <= 23; ++i) {
                map.put(i, 100);
        }
        Restaurant restaurant = Restaurant.newBuilder().setAverageCost_(new Cost(random.nextInt(500) + 500))
                                                            .setOpenTime_(new Time(random.nextInt(3) + 8))
                                                            .setCloseTime_(new Time(random.nextInt(3) + 18))
                                                            .setCoordinates_(new Coordinates((double) random.nextInt(100) + 150, (double) random.nextInt(100) + 150))
                                                            .setName_(generateString(new Random(), 6)).setSeatsAndHoursMap_(map).build();
        return restaurant;
    }

    private String generateString(Random rng, int length)
    {
        String characters = "qwertyuiopasdfghjklzxcvbnm";
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
}