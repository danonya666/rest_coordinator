package app;

import domain.RestaurantManager;
import ui.AwtDemo;
import ui.FillDataBaseFrame;

import java.sql.SQLException;

public class RestApp {
    public static final RestApp INSTANCE = new RestApp();

    private FillDataBaseFrame ui;

    void start() throws SQLException {
        RestaurantManager restaurantManager = new RestaurantManager();
        FillDataBaseFrame fillDataBaseFrame = new FillDataBaseFrame(restaurantManager);
    }

    private RestApp() {}

    public FillDataBaseFrame getUI(){
        return ui;
    }
    public void setUI(FillDataBaseFrame ui) {
        this.ui = ui;
    }

}
