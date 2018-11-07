package UserInterface;

import Domain.Config;
import Domain.RestaurantManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewRestaurantListFrame extends Frame{
    Frame mainFrame;
    private JTable restaurantJTable;
    private Button closeButton;

    ViewRestaurantListFrame(RestaurantManager rM){
        prepareFrame(rM);
    }

    private void prepareFrame(RestaurantManager rM){
        mainFrame = new Frame();
        mainFrame.setSize(Config.getScreenResolutionWidth(),Config.getScreenResolutionHeight());
        restaurantJTable = new JTable(rM.restaurantCount(), 7);
        closeButton = new Button("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
            }
        });
        closeButton.setBackground(Color.RED);
        fillTheTable(rM);
        int index = 0;
        JScrollPane scrollPane= new  JScrollPane(restaurantJTable);
        mainFrame.add(scrollPane, index++);
        mainFrame.add(closeButton, index++);
        mainFrame.setLayout(new GridLayout(0, 1));
        mainFrame.setVisible(true);
        if(rM.restaurantCount() == 0){
            JOptionPane.showMessageDialog(mainFrame, "View list is empty");
            mainFrame.dispose();
        }
        mainFrame.setTitle("LIST OF RESTS");
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.dispose();
            }
        });
    }

    private void fillTheTable(RestaurantManager rM){
        int restaurantCount = rM.restaurantCount();
        restaurantJTable.getColumnModel().getColumn(0).setHeaderValue("ID");
        restaurantJTable.getColumnModel().getColumn(1).setHeaderValue("NAME");
        restaurantJTable.getColumnModel().getColumn(2).setHeaderValue("OPEN HOUR");
        restaurantJTable.getColumnModel().getColumn(3).setHeaderValue("CLOSE HOUR");
        restaurantJTable.getColumnModel().getColumn(4).setHeaderValue("AVERAGE COST");
        restaurantJTable.getColumnModel().getColumn(5).setHeaderValue("X COORD");
        restaurantJTable.getColumnModel().getColumn(6).setHeaderValue("Y COORD");

        restaurantJTable.updateUI();
        for(int i = 0; i < restaurantCount; ++i){
            int j = 0;
            restaurantJTable.setValueAt(rM.getByIndex(i).getId_(), i, j++);
            restaurantJTable.setValueAt(rM.getByIndex(i).getName_(), i, j++);
            restaurantJTable.setValueAt(rM.getByIndex(i).getOpenTime_().getHours_(), i, j++);
            restaurantJTable.setValueAt(rM.getByIndex(i).getCloseTime_().getHours_(), i, j++);
            restaurantJTable.setValueAt(rM.getByIndex(i).getAverageCost_().getBigValue_(), i, j++);
            restaurantJTable.setValueAt(rM.getByIndex(i).getCoordinates_().getLattitude_(), i, j++);
            restaurantJTable.setValueAt(rM.getByIndex(i).getCoordinates_().getLongtitude_(), i, j++);
        }
    }
}
