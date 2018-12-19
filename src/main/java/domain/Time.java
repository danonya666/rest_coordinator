package domain;

public class Time {
    private int hours_;
    private int minutes_;

    public Time(int hours_, int minutes_) {
        this.hours_ = hours_;
        this.minutes_ = minutes_;
    }

    public Time(int big){
        hours_ = big;
        minutes_ = 0;
    }

    public Time(){
        this.hours_ = 0;
        this.minutes_ = 0;
    }

    public int getHours_() {
        return hours_;
    }

    public int getMinutes_() {
        return minutes_;
    }

    public void setHours_(int hours_) {
        this.hours_ = hours_;
    }

    public void setMinutes_(int minutes_) {
        this.minutes_ = minutes_;
    }

    public Time substract(Time other){
        Time result = new Time();
        if (this.minutes_ >= other.minutes_){
            result.minutes_ = this.minutes_ - other.minutes_;
            result.hours_ = this.hours_ - other.hours_;
        }
        else{
            result.minutes_ = this.minutes_ - other.minutes_ + 60;
            result.hours_ = this.hours_ - other.hours_ - 1;
        }
        return result;
    }

    public int softSubstract(Time other){
        int result = this.hours_ - other.hours_ + 1;
        if (other.minutes_ == 0){
            result--;
        }
        return result;
    }

    public Time plus(Time other){
        Time result = new Time();
        result.setHours_(this.hours_ + other.hours_);
        result.setMinutes_(this.minutes_ + other.minutes_);
        if(result.minutes_ > 59){
            result.minutes_ -= 60;
            result.hours_++;
        }
        return result;
    }

    public int compare(Time other){
        if(this.hours_ > other.hours_){
            return 1;
        }
        else{
            if (this.hours_ == other.hours_){
                if(this.minutes_ > other.minutes_){
                    return 1;
                }
                else if(this.minutes_ == other.minutes_){
                    return 0;
                }
                else return -1;
            }
            else return -1;
        }
    }

    public int lastNeededHour(){
        if (this.minutes_ == 0)
            return (this.hours_ - 1);
        else
            return this.hours_;
    }
}