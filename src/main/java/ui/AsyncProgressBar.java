package ui;

import javafx.scene.control.ProgressBar;

import javax.swing.*;

public class AsyncProgressBar {
    private int percents = 0;
    private JProgressBar progressBar;
    public void addPercents(int howmany){
        percents += howmany;
        if(percents >= 100){
            percents = 100;
        }
        progressBar.setValue(percents);
    }
    private void test(){
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        };

        task.run();

        Thread thread = new Thread(task);
        thread.start();

        System.out.println("Done!");
    }
    public AsyncProgressBar(){
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        test();
    }
}
