package Domain;

public class Cost {
    private int bigValue_;
    private int smallValue;
    private String currency_;

    public Cost(int bigValue_, int smallValue, String currency) {
        this.bigValue_ = bigValue_;
        this.smallValue = smallValue;
        if(currency == Config.standardCurrencyStr){
            this.currency_ = Config.standardCurrency;
        }
        else
            this.currency_ = currency_;
    }

    public Cost(int bigValue){
        bigValue_ = bigValue;
        smallValue = 0;
        currency_ = "rubles";
    }
    public Cost() {
    }

    public void setBigValue_(int bigValue_) {
        this.bigValue_ = bigValue_;
    }

    public void setSmallValue(int smallValue) {
        this.smallValue = smallValue;
    }

    public void setCurrency_(String currency) {
        if(currency == Config.standardCurrencyStr){
            setCurrency_(Config.standardCurrency);
        }
        this.currency_ = currency;
    }

    public int substraction(Cost other){
        if(this.currency_ == other.currency_)
            return(this.bigValue_ - other.bigValue_ + this.smallValue / 100 - other.smallValue / 100);
        else
            return -1;
    }

    public int getBigValue_() {
        return bigValue_;
    }

    public int getSmallValue() {
        return smallValue;
    }

    public String getCurrency_() {
        return currency_;
    }
}
