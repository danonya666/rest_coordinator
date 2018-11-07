package UserInterface;

import Domain.Restaraunt;
import Domain.RestaurantManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;

public class DeleteRestaurantFrame {
    private Frame deleteRestaurantFrame;
    private Panel controlPanel;
    private TextField nameTF;
    private Button deleteRestaurantBtn;
    private Button closeBtn;

    private void prepareFrame(RestaurantManager restaurantManager){
        deleteRestaurantFrame = new Frame();
        deleteRestaurantFrame.setSize(300, 300);
        deleteRestaurantFrame.setLayout(new GridLayout());
        deleteRestaurantFrame.setVisible(true);
        deleteRestaurantFrame.setTitle("Delete Restaurant");
        deleteRestaurantFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                deleteRestaurantFrame.dispose();
            }
        });
        controlPanel = new Panel();
        deleteRestaurantFrame.add(controlPanel);

        nameTF = new TextField("Enter Name of the Restaurant");
        controlPanel.add(nameTF);

        closeBtn = new Button("Close");
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRestaurantFrame.dispose();
            }
        });
        controlPanel.add(closeBtn);

        deleteRestaurantBtn = new Button("Delete");
        deleteRestaurantBtn.addActionListener(new ActionListener() {
            //TODO debug Restaraunt.delete function
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = restaurantManager.deleteRestaurant(nameTF.getText());
                String dialougeString = new String();
                if(result == 1)
                    dialougeString = MessageFormat.format("Restaurant {0} was successfully deleted", nameTF.getText());
                else if(result == 0)
                    dialougeString = "Sorry, but restaurant database is empty";
                else if(result == -1)
                    dialougeString = "Sorry, but there is no restaurant with this name in the database";
                JOptionPane.showMessageDialog(deleteRestaurantFrame, dialougeString);
            }
        });
        controlPanel.add(deleteRestaurantBtn);
    }

    public DeleteRestaurantFrame(RestaurantManager restaurantManager){
        prepareFrame(restaurantManager);
    }
}
