package ui;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimePanel extends JPanel {
    private JLabel timeLabel;
    private JLabel displayArea;
    private String DEFAULT_TIME_FORMAT = "yyyy-M-dd HH:mm:ss";
    private String time;
    private int ONE_SECOND = 1000;

    public TimePanel(){
        timeLabel = new JLabel("CurrentTime: ");
        Font myFont = new Font("Serif", Font.BOLD, 20);
        timeLabel.setFont(myFont);
        displayArea = new JLabel();
        displayArea.setFont(myFont);
        configTimeArea();
        add(timeLabel);
        add(displayArea);
        setOpaque(false);
    }

    //Effects: update time per second
    private void configTimeArea() {
        Timer tmr = new Timer();
        tmr.scheduleAtFixedRate(new JLabelTimerTask(),new Date(), ONE_SECOND);
    }

    //Effects: display updated time
    protected class JLabelTimerTask extends TimerTask {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
        @Override
        public void run() {
            time = dateFormatter.format(Calendar.getInstance().getTime());
            displayArea.setText(time);
        }
    }
}
