package Domain;

import java.sql.SQLException;
import java.time.LocalDate;

import java.time.LocalTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Client {
    private String surename_;
    private String lastname_;
    private int id_;
    protected List<Client> clientArrayList_;

    public List<Client> getClientArrayList_() {
        return clientArrayList_;
    }

    public String getSurename_() {
        return surename_;
    }

    public String getLastname_() {
        return lastname_;
    }

    public int getId_() {
        return id_;
    }

    public void getClientInformation(){
        System.out.println(surename_);
        System.out.println(lastname_);
        System.out.println(id_);
    }
    public Client(String surename, String lastname, int id, ArrayList<Client> clientArrayList) {
        this.surename_ = surename;
        this.lastname_ = lastname;
        this.id_ = id;
        this.clientArrayList_ = clientArrayList;
    }



    public static class ClientFactory{
        public static int currentId_ = 0;
        public static Client newClient(String surename, String lastname, ArrayList<Client> clientArrayList){
            Client client = new Client(surename, lastname, currentId_++, clientArrayList);
            clientArrayList.add(client);
            return client;
        }
    }

    public void attend(Cost cost, int rating, LocalDate date, int restId){
        Attendance attendance = new Attendance(cost, rating, date, this.getId_(), restId,
                Attendance.AttendanceList.getAttendanceArrayList_());
        attendance.addToAttendanceList();
    }

    public Restaraunt getOptimalRestaraunt(int radius, Cost supposedCost, int quantityOfPeople,
                                           Coordinates currCoords, RestaurantManager restaurantManager) throws SQLException {
        Map<Integer, Restaraunt> goodRestaraunts = new HashMap<>();
        restaurantManager.updateList();
        for(int i = 0; i < restaurantManager.size(); ++i){

            Restaraunt restaraunt = restaurantManager.getByIndex(i);
            double distance = currCoords.distance(restaraunt.getCoordinates_());
            if(distance > radius) continue;
            int costDifference = Math.abs(supposedCost.substraction(restaraunt.getAverageCost_()));
            Time currTime = new Time(LocalTime.now().getHour(), LocalTime.now().getMinute());
            Time timeNeededToComeToRestaraunt = new Time();
            double dTimeNeededToComeToRestaraunt = distance * 1000; // in minutes

            int minutesNeededToComeToRestaraunt = (int)Math.round(dTimeNeededToComeToRestaraunt);


            int hoursNeededToComeToRestaraunt = minutesNeededToComeToRestaraunt / 60;

            int realMinutesNeededToComeToRestaraunt = minutesNeededToComeToRestaraunt - hoursNeededToComeToRestaraunt * 60;

            timeNeededToComeToRestaraunt.setHours_(hoursNeededToComeToRestaraunt);

            timeNeededToComeToRestaraunt.setMinutes_(realMinutesNeededToComeToRestaraunt);

            //Domain.Time estimatedArrivalTime = timeNeededToComeToRestaraunt.plus(currTime); //Commented because restaraunt doesnt work at this time
            Time estimatedArrivalTime = new Time(LocalTime.now().getHour(),0);
            if(estimatedArrivalTime.compare(restaraunt.getCloseTime_()) == 1 ||
                    estimatedArrivalTime.compare(restaraunt.getOpenTime_()) == -1) continue;

            //if(quantityOfPeople < restaraunt.getSeatsAndHoursMap__().get(currTime.getHours_()))
            if(quantityOfPeople < 50)
                goodRestaraunts.put(costDifference, restaraunt);
        }
        for(int i = 0; i < Config.maxAverageCostInRubles; ++i){
            if(!goodRestaraunts.containsKey(i)) continue;
            else return goodRestaraunts.get(i);
        }
        return new Restaraunt(); // if no restaurant is inside the criteria
    }
}
