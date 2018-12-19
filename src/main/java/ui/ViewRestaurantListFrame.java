package ui;

import db.DaoPostgres;
import domain.Config;
import domain.RestaurantManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ViewRestaurantListFrame extends Frame{
    Frame mainFrame;
    private JTable restaurantJTable;
    private Button closeButton;
    private Button restButton[];
    private DaoPostgres DaoPostgres;
    private JProgressBar progressBar;
    ViewRestaurantListFrame(RestaurantManager rM) throws SQLException, InterruptedException {
        DaoPostgres = rM.getDao();
        prepareFrame(rM);
    }

    private void prepareProgressBar(int currIndex){
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        mainFrame.add(progressBar, currIndex);
    }

    private void prepareFrame(RestaurantManager rM) throws SQLException, InterruptedException {
        Map<Button, Integer> buttonIndex = new HashMap<Button, Integer>();
        Map<Button, JScrollPane> buttonsAndScrollPanes = new HashMap<Button, JScrollPane>();
        mainFrame = new Frame();
        mainFrame.setLayout(new GridLayout(15, 100000));
        final int[] index = {0};
        prepareProgressBar(index[0]++);
        rM.updateList(progressBar);
        mainFrame.setSize(Config.getScreenResolutionWidth(),Config.getScreenResolutionHeight());
        int restCount = DaoPostgres.count();
        System.out.println(restCount);
        restButton = new Button[restCount];
        for(int i = 0; i < restCount; ++i){
            restButton[i] = new Button();
            restButton[i].setLabel(rM.getByIndex(i).getName_());
            int finalI = i;
            int finalI1 = i;
            int finalI2 = i;
            restButton[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("table");
                    mainFrame.remove(restButton[finalI2]);
                    JTable table = createRestaurantDescription(finalI, rM);
                    //JTable table = new JTable(5, 5);
                    JScrollPane pane = new JScrollPane(table);
                    Button closeDescriptionBtn = new Button("Close description");
                    buttonsAndScrollPanes.put(closeDescriptionBtn, pane);
                    closeDescriptionBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            mainFrame.remove(closeDescriptionBtn);
                            mainFrame.remove(buttonsAndScrollPanes.get(closeDescriptionBtn));
                            mainFrame.add(restButton[finalI2], buttonIndex.get(restButton[finalI2]));
                            mainFrame.repaint();
                            mainFrame.resize(Config.getScreenResolutionWidth() - 1,Config.getScreenResolutionHeight() + 1);
                        }
                    });
                    mainFrame.add(closeDescriptionBtn, buttonIndex.get(restButton[finalI2]));
                    mainFrame.add(pane, buttonIndex.get(restButton[finalI1]));
                    mainFrame.resize(Config.getScreenResolutionWidth(),Config.getScreenResolutionHeight() + 1);
                    //pane.add(table);
                }
            });
            mainFrame.add(restButton[i], index[0]++);
            buttonIndex.put(restButton[i], index[0] - 1);
            mainFrame.repaint();
        }

        //restaurantJTable = new JTable(restCount, 7);
        closeButton = new Button("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
            }
        });
        closeButton.setBackground(Color.RED);
//        fillTheTable(rM);
        //JScrollPane scrollPane= new  JScrollPane(restaurantJTable);
        //mainFrame.add(scrollPane, index++);
        mainFrame.add(closeButton, index[0]++);

        mainFrame.setVisible(true);
        rM.updateList(progressBar);
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

    private void fillTheTable(RestaurantManager rM) throws SQLException, InterruptedException {
        rM.updateList(progressBar);
        int restaurantCount = rM.countRests();
        System.out.println(rM.getByIndex(0).getName_());
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
