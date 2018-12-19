package ui;

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class AddRestaurantFrame extends Frame{
    private Frame addRestaurantFrame;
    private Panel controlPanel;
    private TextField nameTextField;
    private TextField openHoursTextField;
    private TextField closeHoursTextField;
    private TextField averageCostTextField;
    private TextField lon;
    private TextField lat;
    private JTable seatsAndHoursTable;
    private Button createSeatsAndHoursTable;
    private Button createNewRestaurantBtn;
    private Button closeBtn;

    private void prepareFrame(RestaurantManager restaurantManager){
        addRestaurantFrame = new Frame();
        addRestaurantFrame.setSize(1000, 1000);
        addRestaurantFrame.setLayout(new GridLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addRestaurantFrame.setVisible(true);
        addRestaurantFrame.setTitle("New restaurant");
        addRestaurantFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                addRestaurantFrame.dispose();
            }
        });
        controlPanel = new Panel();
        controlPanel.setLayout(new FlowLayout());

        addRestaurantFrame.add(controlPanel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        nameTextField = new TextField("Enter Name of the restaurant");
        controlPanel.add(nameTextField);

        gbc.gridy = 1;
        openHoursTextField = new TextField("Enter opening hour");
        controlPanel.add(openHoursTextField);

        closeHoursTextField = new TextField("Enter closing hour");
        controlPanel.add((closeHoursTextField));

        averageCostTextField = new TextField("Enter average Cost");
        controlPanel.add(averageCostTextField);

        lon = new TextField("Enter Y");
        controlPanel.add(lon);

        lat = new TextField("Enter X");
        controlPanel.add(lat);

        closeBtn = new Button("Close");
        closeBtn.addActionListener(new ActionListener() {
//            @Override
            public void actionPerformed(ActionEvent e) {
                addRestaurantFrame.dispose();
            }
        });
        controlPanel.add(closeBtn);

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
                    JScrollPane scrollPane= new  JScrollPane(seatsAndHoursTable);
                    controlPanel.add(scrollPane);
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
                                JOptionPane.showMessageDialog(addRestaurantFrame, "Fill the average cost field correctly pls ;)");
                                return;
                            }
                            try{
                                builder.setCloseTime_(getCloseTime());
                            }
                            catch (NumberFormatException er){
                                JOptionPane.showMessageDialog(addRestaurantFrame, "Fill the close time field correctly pls ;)");
                                return;
                            }
                            try{
                                builder.setOpenTime_(getOpenTime());
                            }
                            catch (NumberFormatException er){
                                JOptionPane.showMessageDialog(addRestaurantFrame, "Fill the open time field correctly pls ;)");
                                return;
                            }
                            try{
                                builder.setCoordinates_(new Coordinates(Integer.valueOf(lon.getText()), Integer.valueOf(lat.getText())));
                            }
                            catch (NumberFormatException er){
                                JOptionPane.showMessageDialog(addRestaurantFrame, "Fill the coordinates fields correctly pls ;)");
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
                                JOptionPane.showMessageDialog(addRestaurantFrame, "Fill all the requested values pls ;)");
                                return;
                            }
                            builder.setRestaurantManager(restaurantManager);
                            builder.setSeatsAndHoursMap_(seatsAndHoursMap);
                            Restaraunt restaraunt = builder.build();

                            restaurantManager.addRestaurant(restaraunt);
                            //System.out.println(Restaraunt.RestarauntsArrayList.getByIndex(0).getName_());
                            String dialougeString = MessageFormat.format("Restaurant {0} was successfully added to the database!", builder.getName_());
                            JOptionPane.showMessageDialog(addRestaurantFrame, dialougeString);
                        }
                    });
                    controlPanel.add(createNewRestaurantBtn);
                }
            }
        });
        controlPanel.add(createSeatsAndHoursTable, 6);

    }

    private boolean fieldsAreCorrect(){
        return getOpenHoursTF() != -1 && getCloseHoursTF() != -1;
    }

    AddRestaurantFrame(RestaurantManager restaurantManager){
        prepareFrame(restaurantManager);
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

}
