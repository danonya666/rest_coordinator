package Domain;//import java.util.ArrayList;
import java.util.Map;

public class Restaraunt {
    private Cost averageCost_;
    private Time openTime_;
    private Time closeTime_;
    private Map<Integer, Integer> seatsAndHoursMap_; // Needed Hour, Available Seats
    private int id_;
    private Coordinates coordinates_;
    private String name_;
    private RestaurantManager restaurantManager;

    public Restaraunt(){

    }

    private Restaraunt(Builder builder) {
        setAverageCost_(builder.averageCost_);
        setOpenTime_(builder.openTime_);
        setCloseTime_(builder.closeTime_);
        setSeatsAndHoursMap_(builder.seatsAndHoursMap_);
        id_ = builder.id_;
        setCoordinates_(builder.coordinates_);
        name_ = builder.name_;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public void setAverageCost_(Cost averageCost_) {
        this.averageCost_ = averageCost_;
    }

    public void setOpenTime_(Time openTime_) {
        this.openTime_ = openTime_;
    }

    public void setCloseTime_(Time closeTime_) {
        this.closeTime_ = closeTime_;
    }

    public void setSeatsAndHoursMap_(Map<Integer, Integer> seatsAndHoursMap) {
        seatsAndHoursMap_ = seatsAndHoursMap;
    }

    public void setCoordinates_(Coordinates coordinates_) {
        this.coordinates_ = coordinates_;
    }

    public Cost getAverageCost_() {
        return averageCost_;
    }

    public Time getOpenTime_() {
        return openTime_;
    }

    public Time getCloseTime_() {
        return closeTime_;
    }

    public Map<Integer, Integer> getSeatsAndHoursMap__() {
        return seatsAndHoursMap_;
    }

    public int getId_() {
        return id_;
    }

    public String getName_() {
        return name_;
    }

    public Coordinates getCoordinates_() {
        return coordinates_;
    }

    private Restaraunt(Cost averageCost, Time openTime, Time closeTime, Map<Integer, Integer> seatsAndHoursMap,
                       Coordinates coordinates, int id, String name, RestaurantManager restaurantManager) {
        averageCost_ = averageCost;
        openTime_ = openTime;
        closeTime_ = closeTime;
        seatsAndHoursMap_ = seatsAndHoursMap;
        coordinates_ = coordinates;
        id_ = id;
        name_ = name;
        this.restaurantManager = restaurantManager;
    }

    public RestaurantManager getRestaurantManager() {
        return restaurantManager;
    }

    public void setRestaurantManager(RestaurantManager restaurantManager) {
        this.restaurantManager = restaurantManager;
    }


    public static final class Builder {
        private Cost averageCost_;
        private Time openTime_;
        private Time closeTime_;
        private Map<Integer, Integer> seatsAndHoursMap_;
        private int id_;
        private Coordinates coordinates_;
        private String name_;
        private RestaurantManager restaurantManager;

        private Builder() {
        }

        public Builder setAverageCost_(Cost val) {
            averageCost_ = val;
            return this;
        }

        public Builder setOpenTime_(Time val) {
            openTime_ = val;
            return this;
        }

        public Builder setCloseTime_(Time val) {
            closeTime_ = val;
            return this;
        }

        public Builder setSeatsAndHoursMap_(Map<Integer, Integer> val) {
            seatsAndHoursMap_ = val;
            return this;
        }

        public Builder setId_(int val) {
            id_ = val;
            return this;
        }

        public Builder setCoordinates_(Coordinates val) {
            coordinates_ = val;
            return this;
        }

        public Builder setName_(String val) {
            name_ = val;
            return this;
        }

        public String getName_() {
            return name_;
        }

        public Restaraunt build() {
            for (int i = openTime_.getHours_(); i <= closeTime_.lastNeededHour(); ++i) {
                if (!seatsAndHoursMap_.containsKey(i)) {
                    seatsAndHoursMap_.put(i, null);
                }
            }

            return (new Restaraunt(this));
        }

        public void setRestaurantManager(RestaurantManager restaurantManager) {
            this.restaurantManager = restaurantManager;
        }
    }
}
