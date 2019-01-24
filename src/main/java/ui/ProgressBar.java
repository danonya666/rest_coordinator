package ui;

import javax.swing.*;

public class ProgressBar implements Runnable{
    public JProgressBar progressBar;
    ProgressBar(JProgressBar jpb){
        progressBar = jpb;
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
    }
    @Override
    public void run() {
        for(int i = 0; i < 2; ++i){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(progressBar.getValue());
            progressBar.setValue(progressBar.getValue() + 5);
            progressBar.updateUI();
        }
    }
}
