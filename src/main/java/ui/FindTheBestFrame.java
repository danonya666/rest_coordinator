package ui;

import common.Coordinates;
import common.Cost;
import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;

public class FindTheBestFrame extends Frame{
    private Frame findTheBestFrame;
    private Panel controlPanel;
    private TextField radiusTF;
    private TextField prefferedCostTF;
    private TextField quantityOfPeopleTF;
    private TextField lonTF;
    private TextField latTF;
    private Button findTheBestBtn;
    private Button closeBtn;

    FindTheBestFrame(RestaurantManager restaurantManager){
        prepareFrame(restaurantManager);
    }

    private void prepareFrame(RestaurantManager restaurantManager){
        findTheBestFrame = new Frame();
        findTheBestFrame.setSize(555, 555);
        findTheBestFrame.setLayout(new GridLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        findTheBestFrame.setVisible(true);
        findTheBestFrame.setTitle("Finding the best restaurant");
        findTheBestFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                findTheBestFrame.dispose();
            }
        });
        controlPanel = new Panel();
        controlPanel.setLayout(new FlowLayout());

        findTheBestFrame.add(controlPanel);

        radiusTF = new TextField("radius");
        prefferedCostTF = new TextField("cost");
        quantityOfPeopleTF = new TextField("people");
        lonTF = new TextField("lon");
        latTF = new TextField("lat");

        controlPanel.add(radiusTF);
        controlPanel.add(prefferedCostTF);
        controlPanel.add(quantityOfPeopleTF);
        controlPanel.add(lonTF);
        controlPanel.add(latTF);

        closeBtn = new Button("Close");
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findTheBestFrame.dispose();
            }
        });
        controlPanel.add(closeBtn);
        findTheBestBtn = new Button("FIND");
        findTheBestBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client testClient = new Client("test", "test", 0, new ArrayList<Client>()); // TODO should do smth with the client database in future
                try{
                    int radius = CustomFunctions.toInt(radiusTF.getText());
                    int prefCost = CustomFunctions.toInt(prefferedCostTF.getText());
                    int quantity = CustomFunctions.toInt(quantityOfPeopleTF.getText());
                    int lon = CustomFunctions.toInt(lonTF.getText());
                    int lat = CustomFunctions.toInt(latTF.getText());

                    Restaurant optimal = testClient.getOptimalRestaurant(radius, new Cost(prefCost, 0, Config.standardCurrencyStr),
                            quantity, new Coordinates(lon, lat), restaurantManager);

                    String dialougeString;
                    if(optimal != null){
                        dialougeString = MessageFormat.format("The optimal restaurant is {0}", optimal.getName_());
                    }
                    else{
                        dialougeString = "No restaurants are working right now. Try again later";
                    }
                    JOptionPane.showMessageDialog(findTheBestFrame, dialougeString);
                }
                catch (NumberFormatException e1){
                    String dialougeString = "Please fill all the fields correctly";
                    JOptionPane.showMessageDialog(findTheBestFrame, dialougeString);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
        controlPanel.add(findTheBestBtn);
    }

}
