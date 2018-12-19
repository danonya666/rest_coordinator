package ui;

import db.DaoPostgres;
import db.DbConfig;
import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class AwtDemo {
    /* GENERAL COMPONENTS */
    private Frame mainFrame_;
    private DaoPostgres DaoPostgres;
    private Label headerLabel_;
    //private Panel mainFrame_;
    private Button addRestaurantButton_;
    private Button deleteRestaurantButton_;
    private Button findOptimalRestaurantButton_;
    private Button viewRestaurantListBtn_; // TODO

    /* ADD RESTAURANT COMPONENTS */

    private TextField nameTextField;
    private Label nameLabel;
    private TextField openHoursTextField;
    private Label openHoursLabel;
    private TextField closeHoursTextField;
    private Label closeHoursLabel;
    private TextField averageCostTextField;
    private Label averageCostLabel;
    private TextField lon;
    private Label lonLabel;
    private TextField lat;
    private Label latLabel;
    private JTable seatsAndHoursTable;
    private Button createSeatsAndHoursTable;
    private Button createNewRestaurantBtn;
    private Button closeBtn;
    private boolean initialized;
    private JScrollPane scrollPane;
    private Button[] restButton;

    /**/
    public AwtDemo(RestaurantManager restaurantManager) throws SQLException {
        prepareGui(restaurantManager);
    }


    private void prepareViewList(RestaurantManager rM) throws SQLException {

    }

    private JTable createRestaurantDescription(int index, RestaurantManager rM) {
                    JTable restaurantJTable1 = new JTable(1, 7);
                    restaurantJTable1.getColumnModel().getColumn(0).setHeaderValue("ID");
                    restaurantJTable1.getColumnModel().getColumn(1).setHeaderValue("NAME");
                    restaurantJTable1.getColumnModel().getColumn(2).setHeaderValue("OPEN HOUR");
                    restaurantJTable1.getColumnModel().getColumn(3).setHeaderValue("CLOSE HOUR");
                    restaurantJTable1.getColumnModel().getColumn(4).setHeaderValue("AVERAGE COST");
                    restaurantJTable1.getColumnModel().getColumn(5).setHeaderValue("X COORD");
                    restaurantJTable1.getColumnModel().getColumn(6).setHeaderValue("Y COORD");
                    restaurantJTable1.updateUI();
                    int j = 0;
                    restaurantJTable1.setValueAt(rM.getByIndex(index).getId_(), 0, j++);
                    restaurantJTable1.setValueAt(rM.getByIndex(index).getName_(), 0, j++);
                    restaurantJTable1.setValueAt(rM.getByIndex(index).getOpenTime_().getHours_(), 0, j++);
                    restaurantJTable1.setValueAt(rM.getByIndex(index).getCloseTime_().getHours_(), 0, j++);
                    restaurantJTable1.setValueAt(rM.getByIndex(index).getAverageCost_().getBigValue_(), 0, j++);
                    restaurantJTable1.setValueAt(rM.getByIndex(index).getCoordinates_().getLattitude_(), 0, j++);
                    restaurantJTable1.setValueAt(rM.getByIndex(index).getCoordinates_().getLongtitude_(), 0, j++);
                    return restaurantJTable1;
                }

    private void prepareGui(RestaurantManager restaurantManager) throws SQLException {
        //prepareViewList(restaurantManager);
        mainFrame_ = new Frame("Restaraunt Coordinator");
        mainFrame_.setSize(Config.getScreenResolutionWidth(), Config.getScreenResolutionHeight());
        mainFrame_.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        headerLabel_ = new Label();
        headerLabel_.setAlignment(Label.CENTER);
        headerLabel_.setText("What to do with the restaurant database?");
        headerLabel_.setAlignment(Label.CENTER);
        headerLabel_.setBackground(Color.WHITE);
        headerLabel_.setForeground(Color.BLACK);

        mainFrame_.setBackground(Color.PINK);
        addRestaurantButton_ = new Button();
        addRestaurantButton_.setLabel("ADD");
        addRestaurantButton_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Frame addRestaurantFrame = new AddRestaurantFrame(restaurantManager);
                //mainFrame_ = new AddRestaurantFrame(restaurantManager);
                defaultMenuOff();
                headerLabel_.setVisible(false);
                if(!initialized){
                    addRestInitialize(restaurantManager);
                    initialized = true;
                }
                addRestOn();
                mainFrame_.resize(Config.getScreenResolutionWidth(), Config.getScreenResolutionHeight() - 1);
                mainFrame_.resize(Config.getScreenResolutionWidth(), Config.getScreenResolutionHeight() - 2);
                //resize is for updating the frame (Costil')
            }
        });

        deleteRestaurantButton_ = new Button();
        deleteRestaurantButton_.setLabel("DELETE");
        deleteRestaurantButton_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteRestaurantFrame deleteRestaurantFrame = new DeleteRestaurantFrame(restaurantManager);
            }
        });

        findOptimalRestaurantButton_ = new Button();
        findOptimalRestaurantButton_.setLabel("FIND THE BEST");
        findOptimalRestaurantButton_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame findOptimal = new FindTheBestFrame(restaurantManager);
            }
        });

        viewRestaurantListBtn_ = new Button();
        viewRestaurantListBtn_.setLabel("View List");
        viewRestaurantListBtn_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Frame viewRestaurantListFrame = new ViewRestaurantListFrame(restaurantManager);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    public void initialize(RestaurantManager restaurantManager) {
        mainFrame_.add(headerLabel_);
        mainFrame_.setVisible(true);

        mainFrame_.add(addRestaurantButton_);
        mainFrame_.add(deleteRestaurantButton_);
        mainFrame_.add(findOptimalRestaurantButton_);
        mainFrame_.add(viewRestaurantListBtn_);
        mainFrame_.setLayout(new GridLayout(15, 4));
    }

    private void clearMainFrame(){
        for(int i = 0; i < mainFrame_.getComponentCount(); ++i){
            Component comp = mainFrame_.getComponent(i);
            comp.setVisible(false);
        }
    }
    private void addRestInitialize(RestaurantManager restaurantManager){
        /*GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;*/
        clearMainFrame();
        int index = 0;
        nameTextField = new TextField("Enter Name of the restaurant");
        mainFrame_.add(nameTextField, index++);


        nameLabel = new Label("The name of the restaurant");
        nameLabel.setBackground(Color.lightGray);
        mainFrame_.add(nameLabel, index++);

        openHoursTextField = new TextField("Enter opening hour");
        mainFrame_.add(openHoursTextField, index++);

        openHoursLabel = new Label("Open hours");
        openHoursLabel.setBackground(Color.lightGray);
        mainFrame_.add(openHoursLabel, index++);

        closeHoursTextField = new TextField("Enter closing hour");
        mainFrame_.add((closeHoursTextField), index++);

        closeHoursLabel = new Label("Close Hours");
        closeHoursLabel.setBackground(Color.lightGray);
        mainFrame_.add(closeHoursLabel, index++);

        averageCostTextField = new TextField("Enter average Cost");
        mainFrame_.add(averageCostTextField, index++);

        averageCostLabel = new Label("Average Cost");
        averageCostLabel.setBackground(Color.lightGray);
        mainFrame_.add(averageCostLabel, index++);

        lon = new TextField("Enter Y");
        mainFrame_.add(lon, index++);

        lonLabel = new Label("Longitude");
        lonLabel.setBackground(Color.lightGray);
        mainFrame_.add(lonLabel, index++);

        lat = new TextField("Enter X");
        mainFrame_.add(lat, index++);

        latLabel = new Label("Latitude");
        latLabel.setBackground(Color.lightGray);
        mainFrame_.add(latLabel, index++);

        closeBtn = new Button("Close");
        closeBtn.addActionListener(new ActionListener() {
            //            @Override
            public void actionPerformed(ActionEvent e) {
                addRestOff();
                defaultMenuOn();
            }
        });
        mainFrame_.add(closeBtn, index++);

        createSeatsAndHoursTable = new Button();
        createSeatsAndHoursTable.setLabel("Create SeatTable");
        createSeatsAndHoursTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fieldsAreCorrect()){
                    seatsAndHoursTable = new JTable(getCloseHoursTF() - getOpenHoursTF() + 1, 2);
                    seatsAndHoursTable.setBackground(Color.LIGHT_GRAY);
                    seatsAndHoursTable.getColumnModel().getColumn(0).setHeaderValue("Час");
                    seatsAndHoursTable.getColumnModel().getColumn(1).setHeaderValue("Среднее количество свобожных мест");
                    scrollPane= new  JScrollPane(seatsAndHoursTable);
                    scrollPane.setVisible(true);
                    mainFrame_.add(scrollPane);
                    for(int i = getOpenHoursTF(); i <= getCloseHoursTF(); ++i){
                        int counter = i - getOpenHoursTF();
                        seatsAndHoursTable.setValueAt(i, counter, 0);
                    }

                    createNewRestaurantBtn = new Button("Add to database");
                    createNewRestaurantBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Restaraunt.Builder builder = Restaraunt.newBuilder();
                            try{
                                builder.setAverageCost_(getAverageCost());
                            }
                            catch (NumberFormatException er){
                                JOptionPane.showMessageDialog(mainFrame_, "Fill the average cost field correctly pls ;)");
                                return;
                            }
                            try{
                                builder.setCloseTime_(getCloseTime());
                            }
                            catch (NumberFormatException er){
                                JOptionPane.showMessageDialog(mainFrame_, "Fill the close time field correctly pls ;)");
                                return;
                            }
                            try{
                                builder.setOpenTime_(getOpenTime());
                            }
                            catch (NumberFormatException er){
                                JOptionPane.showMessageDialog(mainFrame_, "Fill the open time field correctly pls ;)");
                                return;
                            }
                            try{
                                builder.setCoordinates_(new Coordinates(Integer.valueOf(lon.getText()), Integer.valueOf(lat.getText())));
                            }
                            catch (NumberFormatException er){
                                JOptionPane.showMessageDialog(mainFrame_, "Fill the coordinates fields correctly pls ;)");
                                return;
                            }
                            builder.setName_(nameTextField.getText());
                            Map<Integer, Integer> seatsAndHoursMap = new HashMap<>();
                            try{
                                for(int i = getOpenHoursTF(); i <= getCloseHoursTF(); ++i){
                                    seatsAndHoursMap.put((Integer)seatsAndHoursTable.getValueAt(i - getOpenHoursTF(), 0),
                                            CustomFunctions.toInt(seatsAndHoursTable.getValueAt(i - getOpenHoursTF(), 1)));
                                }
                            }
                            catch (NullPointerException er){
                                JOptionPane.showMessageDialog(mainFrame_, "Fill all the requested values pls ;)");
                                return;
                            }
                            builder.setRestaurantManager(restaurantManager);
                            builder.setSeatsAndHoursMap_(seatsAndHoursMap);
                            Restaraunt restaraunt = builder.build();
                            DaoPostgres dao_postgres = new DaoPostgres();
                            dao_postgres.Connect("postgres", DbConfig.dbPassword);
                            try {
                                dao_postgres.insert(restaraunt);
                                System.out.println("Restaurant successfully added");
                            } catch (SQLException e1) {
                                System.out.println("ERROR");
                                e1.printStackTrace();
                            }
                            restaurantManager.addRestaurant(restaraunt);
                            //System.out.println(Restaraunt.RestarauntsArrayList.getByIndex(0).getName_());
                            String dialougeString = MessageFormat.format("Restaurant {0} was successfully added to the database!", builder.getName_());
                            JOptionPane.showMessageDialog(mainFrame_, dialougeString);
                        }
                    });
                    mainFrame_.add(createNewRestaurantBtn);
                    mainFrame_.resize(Config.getScreenResolutionWidth(),Config.getScreenResolutionHeight() - 1);
                    mainFrame_.resize(Config.getScreenResolutionWidth(),Config.getScreenResolutionHeight() + 1);
                    //For repainting the frame
                    //Didn't find how to repaint in other way
                    //repaint() doesn't work
                }
                else{
                    JOptionPane.showMessageDialog(mainFrame_, "Fill the hours fields correctly pls ;)");
                }
            }
        });
        mainFrame_.add(createSeatsAndHoursTable, index++);
        mainFrame_.resize(Config.getScreenResolutionWidth() - 1, Config.getScreenResolutionHeight());
        //resize is for updating frame
    index = 0;
    }


    private boolean fieldsAreCorrect(){
        if(getOpenHoursTF() != -1 && getCloseHoursTF() != -1){
            return true;
        }
        else return false;
    }

    private int getOpenHoursTF(){
        String openHoursText = new String();
        openHoursText = openHoursTextField.getText();
        if(!CustomFunctions.isNumeric(openHoursText)){
            return -1;
        }
        else return (Integer.parseInt(openHoursText));
    }

    private int getCloseHoursTF() {
        String closeHoursText = new String();
        closeHoursText = closeHoursTextField.getText();
        if(!CustomFunctions.isNumeric(closeHoursText)){
            return -1;
        }
        else return (Integer.parseInt(closeHoursText));
    }

    public Cost getAverageCost(){
        Double cost = Double.valueOf(averageCostTextField.getText());
        Cost cost1 = new Cost();
        cost1.setBigValue_(cost.intValue());
        cost1.setSmallValue(0);
        cost1.setCurrency_("standard");
        return cost1;
    }

    public Time getCloseTime(){
        int closeTime = getCloseHoursTF();
        Time CloseTime1 = new Time(closeTime, 0);
        return CloseTime1;
    }

    public Time getOpenTime(){
        int openTime = getOpenHoursTF();
        Time openTime1 = new Time(openTime, 0);
        return openTime1;
    }


    private void addRestOn(){
        addRestComponentsSetVisible(true);
    }

    private void addRestComponentsSetVisible(boolean visibillity){
        nameTextField.setVisible(visibillity);
        nameLabel.setVisible(visibillity);
        //headerLabel_.setVisible(visibillity);
        openHoursTextField.setVisible(visibillity);
        openHoursLabel.setVisible(visibillity);
        closeHoursTextField.setVisible(visibillity);
        closeHoursLabel.setVisible(visibillity);
        averageCostTextField.setVisible(visibillity);
        averageCostLabel.setVisible(visibillity);
        lon.setVisible(visibillity);
        lonLabel.setVisible(visibillity);
        lat.setVisible(visibillity);
        latLabel.setVisible(visibillity);
        createSeatsAndHoursTable.setVisible(visibillity);
        closeBtn.setVisible(visibillity);
            try {
                seatsAndHoursTable.setVisible(visibillity);
                createNewRestaurantBtn.setVisible(visibillity);
                scrollPane.setVisible(visibillity);
            }
            catch (Exception e){    }

    }

    private void addRestOff(){
        addRestComponentsSetVisible(false);
    }
    private void defaultMenuOn(){
        addRestaurantButton_.setVisible(true);
        System.out.println("yayayaya");
        deleteRestaurantButton_.setVisible(true);
        findOptimalRestaurantButton_.setVisible(true);
        headerLabel_.setVisible(true);
        viewRestaurantListBtn_.setVisible(true);
    }

    private void defaultMenuOff(){
        addRestaurantButton_.setVisible(false);
        deleteRestaurantButton_.setVisible(false);
        findOptimalRestaurantButton_.setVisible(false);
        headerLabel_.setVisible(false);
        viewRestaurantListBtn_.setVisible(false);
    }
}