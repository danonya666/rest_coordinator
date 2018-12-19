package domain;

public class Coordinates {
    private double longtitude_;
    private double lattitude_;

    public Coordinates(double lon, double lat){
        longtitude_ = lon;
        lattitude_ = lat;
    }

    public Coordinates() {
        longtitude_ = 0;
        lattitude_ = 0;
    }

    public double getLongtitude_() {
        return longtitude_;
    }

    public double getLattitude_() {
        return lattitude_;
    }

    public void setLongtitude_(double longtitude_) {
        this.longtitude_ = longtitude_;
    }

    public void setLattitude_(double lattitude_) {
        this.lattitude_ = lattitude_;
    }


    public double distance(Coordinates other){
        double result = Math.pow((this.lattitude_ - other.lattitude_), 2) + Math.pow((this.longtitude_ - other.longtitude_), 2);
        return result;
    }
}
