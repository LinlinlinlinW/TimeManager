package ui;

import jdk.nashorn.internal.scripts.JO;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;

import static javax.swing.JFrame.EXIT_ON_CLOSE;


public class UI{
    private ImageIcon background = new ImageIcon("D:\\2018 winter\\CPSC 210\\projectw1_team999\\src\\img\\bkgImg.jpg");
    private JFrame frame = new JFrame(" TimeManager");
    private JPanel contentPane = (JPanel) frame.getContentPane();
    private JPanel cards;
    private CardLayout cardLayout;
    private Dimension dim = frame.getToolkit().getScreenSize();
    private JButton weather = new JButton("Weather");
    private JButton schedule = new JButton("Schedules");
    private JButton quit = new JButton("quit");
    private JButton play = new JButton ();
    private boolean isPlay;
    private Font myFont = new Font("Serif", Font.BOLD, 40);
    private WeatherPanel weatherPanel;
    private SchedulePanel schedulePanel;
    private InputStream music;
    private AudioStream audios;

    public UI(){
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        contentPane.setOpaque(false);
        contentPane.setLayout(new BorderLayout());

        setFrameLocation();

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.setOpaque(false);

        cards.add("card H",card1());
        cards.add("card W",card2());
        cards.add("card S",card3());

        setButtonsAction();

        cardLayout.show(cards,"card H");
        contentPane.add(cards,BorderLayout.CENTER);

        JLayeredPane layeredPane = frame.getLayeredPane();
        layeredPane.add(setBackgroundImage(),new Integer(Integer.MIN_VALUE));
        layeredPane.setOpaque(true);
    }

    //Effects: home page of program
    private JPanel card1(){
        TimePanel timePanel = new TimePanel();


        JPanel cardHome = new JPanel(new BorderLayout());
        JPanel labelPanel_Home = new JPanel();
        labelPanel_Home.setOpaque(false);
        JLabel label_Home = new JLabel("Manage your schedule");
        label_Home.setBorder(BorderFactory.createEmptyBorder(30,20,10,20));
        label_Home.setFont(myFont);
        labelPanel_Home.add(label_Home);


        JPanel buttonPanel_Home = new JPanel();
        buttonPanel_Home.setBorder(BorderFactory.createEmptyBorder(80,20,50,20));
        buttonPanel_Home.setOpaque(false);

        weather.setPreferredSize(new Dimension(120,50));
        buttonPanel_Home.add(weather);

        schedule.setPreferredSize(new Dimension(120,50));
        buttonPanel_Home.add(schedule);

        play.setPreferredSize(new Dimension(120,50));
        play.setText("Music");
        buttonPanel_Home.add(play);
        isPlay = false;

        quit.setPreferredSize(new Dimension(120,50));
        buttonPanel_Home.add(quit);



        cardHome.add(labelPanel_Home,BorderLayout.NORTH);
        cardHome.add(buttonPanel_Home,BorderLayout.CENTER);
        cardHome.add(timePanel,BorderLayout.SOUTH);
        cardHome.setOpaque(false);

        return  cardHome;
    }

    //Effects: card2 -- weather panel
    private JPanel card2(){
        weatherPanel = new WeatherPanel();
        return weatherPanel.getWeatherPanel();
    }

    //Effects: card3 -- schedule panel
    private JPanel card3(){
        schedulePanel = new SchedulePanel();
        return schedulePanel.getSchedulePanel();
    }

    //Effects: set button action's
    private void setButtonsAction(){
        JButton back = weatherPanel.getBACKButton();
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards,"card H");
            }
        });

        JButton back1 = schedulePanel.getBACKbuttonInSchedule();
        back1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards,"card H");
            }
        });

        weather.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards,"card W");
            }
        });

        schedule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cardLayout.show(cards,"card S");
            }
        });

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        try {
            music = new FileInputStream(new File("D:\\2018 winter\\CPSC 210\\projectw1_team999\\src\\bkgmusic.wav"));
            audios = new AudioStream(music);
        }catch(IOException e1){
            JOptionPane.showMessageDialog(null,"error");
        }

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!isPlay) {
                        isPlay = true;
                        play.setText("sounds on");
                        System.out.println("isPlay state = " + isPlay);
                        AudioPlayer.player.start(audios);
                    }else if (isPlay) {
                        isPlay = false;
                        play.setText("sounds off");
                        System.out.println("isPlay state = " + isPlay);
                        AudioPlayer.player.stop(audios);
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "error");
                }
            }
        });
    }

    //Effects: set background image
    private JLabel setBackgroundImage(){
        JLabel imgLabel = new JLabel(background);
        imgLabel.setBounds(0,0,background.getIconWidth(), background.getIconHeight());
        imgLabel.setOpaque(true);
        return imgLabel;
    }

    //Effects: set the size of the frame
    private void setFrameLocation(){
        frame.setSize(background.getIconWidth(),background.getIconHeight());
        frame.setLocation(dim.width/2,dim.height/2);
        frame.setLocationRelativeTo(null);
    }

    //Effects: start point of the whole program
    public static void main(String[] args) {
        UI ui = new UI();
    }
}