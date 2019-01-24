package ui;

import domain.Config;
import domain.RestaurantManager;
import pbar.FillHandler;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;

public class FillDataBaseFrame<fillDbButton> {
    private Frame mainFrame_;
    private Button fillDbButton;
    public static JProgressBar progressBar;
    private ProgressBar pB;
    private BufferedImage progressBarTrue;
    RestaurantManager restaurantManager;
    public static Label restCountLabel;
    public void stateChanged(int state){
        progressBar.setValue(state);
    }

    public FillDataBaseFrame(RestaurantManager rM) throws SQLException {
        prepareFrame();
    }

    private void generateData() throws InterruptedException {
            Thread.sleep(100);
            pB.run();
            mainFrame_.resize(domain.Config.getScreenResolutionWidth(), domain.Config.getScreenResolutionHeight() + 1);
            mainFrame_.resize(domain.Config.getScreenResolutionWidth(), domain.Config.getScreenResolutionHeight() - 1);
    }

    private void prepareFrame() throws SQLException {
        restCountLabel = new Label();
        restaurantManager = new RestaurantManager();
        String count = String.valueOf(restaurantManager.countRests() + " restaurants in your database");
        restCountLabel.setText(count);
        restCountLabel.setVisible(true);
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setValue(50);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        pB = new ProgressBar(progressBar);
        mainFrame_ = new Frame("FillDataBaseFrame");
        mainFrame_.setLayout(new GridLayout(3, 5));
        mainFrame_.setSize(Config.getScreenResolutionWidth(), Config.getScreenResolutionHeight());
        mainFrame_.setVisible(true);
        mainFrame_.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame_.dispose();
            }
        });
        fillDbButton = new Button("Fill Db");

        fillDbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FillHandler.perform();
            }
        });
        mainFrame_.add(fillDbButton);
        mainFrame_.add(pB.progressBar);
        mainFrame_.add(restCountLabel);
    }

}
