package ui;

//思路：
//1 paint different cards
//2 get user responses
//3 pass in responses to the final schedule
//4 generate schedule

import exceptions.ImproperInputException;
import exceptions.NotLogicalInputException;
import objects.EventsManager;
import objects.Schedule;
import objects.User;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SchedulePanel {
    //main frame fields
    private CardLayout cardLayout;
    private JPanel cards;
    private JPanel card1;
    private JPanel card2;
    private JPanel card3;
    private JPanel card4 = new JPanel();
    private User user = new User();
    private EventsManager eventsManager;

    //card 1 fields
    private JRadioButton studentRadioButton;
    private JRadioButton workerRadioButton;
    private JButton next1;
    private JButton back1;
    private ButtonGroup buttonsGp1 = new ButtonGroup();
    private int identity = 0;

    //card 2 fields
    private JButton apply2;
    private JButton next2;
    private JButton back2;
    private int getUpTime;
    private int sleepTime;
    private JTextField textField1;
    private JTextField textField2;

    //preCard34 fields
    private int judgeEvent;

    //card 3 fields
    private JButton apply3;
    private JButton next3;
    private JButton back3;
    private int numOfEvent;
    private JTextArea getEvents;
    private List<String> events= new ArrayList<>();


    //card 4 fields
    private JButton generate;
    private JButton back4;
    private ImageIcon studentIcon = new ImageIcon("D:\\2018 winter\\CPSC 210\\projectw1_team999\\src\\img\\student.png");
    private ImageIcon workerIcon = new ImageIcon("D:\\2018 winter\\CPSC 210\\projectw1_team999\\src\\img\\worker.png");
    private JPanel schedulePanel;
    private JLabel[] scheduleLables;
    private Schedule schedule;
    private JPanel infoPanel;
    private JLabel imageLabel;
    private JPanel msgPanel;
    private JLabel msgGetUpTime;
    private JLabel msgSleepTime;
    private JLabel msgKnownEvent;
    private JPanel buttonsPanel4;



    //Effects: return cards to main frame
    public JPanel getSchedulePanel(){
        return cards;
    }

    //constructor : set cards' fields and initialize 4 cards
    public SchedulePanel(){
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.setOpaque(false);

        initializeCard1();
        initializeCard2();
        initializeCard3();
        initializeCard4();

        cards.add("card1",card1);
        cards.add("card2",card2);
        cards.add("card3",card3);
        cards.add("card4",card4);
        cardLayout.show(cards,"card1");
    }

    public JButton getBACKbuttonInSchedule(){
        return back1;
    }

    //Effects: paint card1
    private void initializeCard1(){
        GridLayout gd1 = new GridLayout(3,1);
        card1 = new JPanel(gd1);
        card1.setBorder(BorderFactory.createEmptyBorder(30,100,70,100));
        card1.setOpaque(false);

        //paint question label: Are you a student or a worker?
        JLabel text1 = new JLabel("Are you a student or a worker?");
        text1.setFont((new Font("Serif", Font.PLAIN,23)));
        text1.setBorder(BorderFactory.createEmptyBorder(65,110,0,0));
        card1.add(text1);

        //paint radio buttons panel
        studentRadioButton = new JRadioButton("student");
        workerRadioButton = new JRadioButton("worker");
        studentRadioButton.setFont((new Font("Serif", Font.PLAIN,23)));
        workerRadioButton.setFont((new Font("Serif", Font.PLAIN,23)));
        studentRadioButton.setOpaque(false);
        workerRadioButton.setOpaque(false);
        studentRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                identity = 1;
                System.out.println("identity = "+identity);
                user.setIdentity(1);
                next1.setEnabled(true);
            }
        });
        workerRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                identity = 2;
                System.out.println("identity = "+identity);
                user.setIdentity(2);
                next1.setEnabled(true);
            }
        });

        buttonsGp1.add(studentRadioButton);
        buttonsGp1.add(workerRadioButton);

        GridLayout gd1_1 = new GridLayout(1,2);
        JPanel buttonsPanel = new JPanel(gd1_1);
        buttonsPanel.setOpaque(false);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10,150,10,100));
        buttonsPanel.add(studentRadioButton);
        buttonsPanel.add(workerRadioButton);
        card1.add(buttonsPanel);

        //paint next1, back1
        next1 = new JButton("NEXT");
        next1.setEnabled(false);
        next1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards,"card2");
            }
        });

        back1 = new JButton("BACK");
        back1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                identity = 0;
                next1.setEnabled(false);
                System.out.println("identity reset is "+identity);
            }
        });
        GridLayout gd1_2 = new GridLayout(1,2);
        gd1_2.setHgap(30);
        JPanel buttonsBN1 = new JPanel(gd1_2);
        buttonsBN1.setBorder(BorderFactory.createEmptyBorder(10,80,50,80));
        buttonsBN1.setOpaque(false);
        buttonsBN1.add(back1);
        buttonsBN1.add(next1);

        card1.add(buttonsBN1);
    }

    //Effects: paint card2
    private void initializeCard2(){
        GridLayout gd2 = new GridLayout(3,1);
        gd2.setVgap(20);
        card2 = new JPanel(gd2);
        card2.setOpaque(false);
        card2.setBorder(BorderFactory.createEmptyBorder(50,80,50,80));

        //paint getUpPane
        GridLayout gd2_1 = new GridLayout(2,1);
        gd2_1.setVgap(5);
        JPanel getUpPane = new JPanel(gd2_1);
        getUpPane.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));
        JLabel message1 = new JLabel("When do you plan to get up (24-hour format)?");
        message1.setFont(new Font("Serif", Font.PLAIN,23));
        textField1 = new JTextField();
        textField1.setFont(new Font("Serif", Font.PLAIN,20));
        getUpPane.add(message1);
        getUpPane.add(textField1);
        getUpPane.setOpaque(false);
        card2.add(getUpPane);

        //paint sleepPane
        JPanel sleepPane = new JPanel(gd2_1);
        sleepPane.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));
        JLabel message2 = new JLabel("When do you plan to sleep (24-hour format)?");
        message2.setFont(new Font("Serif", Font.PLAIN,23));
        textField2 = new JTextField();
        textField2.setFont(new Font("Serif", Font.PLAIN,20));
        sleepPane.add(message2);
        sleepPane.add(textField2);
        sleepPane.setOpaque(false);
        card2.add(sleepPane);

        //paint buttons apply2, reset2, back2,
        apply2 = new JButton("APPLY");
        apply2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    judgeTime();
                    System.out.println("getUpTime = "+getUpTime);
                    System.out.println("sleepTime = "+sleepTime);
                    next2.setEnabled(true);
                }catch(ImproperInputException | NumberFormatException e1){
                    JOptionPane.showMessageDialog(null,"input integer between 0 - 23 !");
                    getUpTime=0;
                    sleepTime=0;
                    textField1.setText("");
                    textField2.setText("");
                    next2.setEnabled(false);
                }catch(NotLogicalInputException e2){
                    JOptionPane.showMessageDialog(null,"getUpTime should be smaller than sleepTime !");
                    getUpTime=0;
                    sleepTime=0;
                    textField1.setText("");
                    textField2.setText("");
                    next2.setEnabled(false);
                }
            }
        });

        next2 = new JButton("NEXT");
        next2.setEnabled(false);
        next2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setGetUpTime(getUpTime);
                user.setSleepTime(sleepTime);
                textField1.setText("");
                textField2.setText("");
                preCard34();
            }
        });

        back2 = new JButton("BACK");
        back2.setEnabled(true);
        back2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards,"card1");
                getUpTime=0;
                sleepTime=0;
                textField1.setText("");
                textField2.setText("");
                next2.setEnabled(false);
            }
        });

        GridLayout gd2_2 = new GridLayout(1,3);
        gd2_2.setHgap(20);
        JPanel buttonPane2 = new JPanel(gd2_2);
        buttonPane2.setBorder(BorderFactory.createEmptyBorder(30,40,30,40));
        buttonPane2.setOpaque(false);

        buttonPane2.add(apply2);
        buttonPane2.add(next2);
        buttonPane2.add(back2);
        card2.add(buttonPane2);
    }

    //Effects:judge whether user input proper getUpTime and sleepTime, auxiliary function of card2
    private void judgeTime()throws ImproperInputException,NotLogicalInputException,NumberFormatException{
        getUpTime = Integer.parseInt(textField1.getText());
        sleepTime = Integer.parseInt(textField2.getText());

        if(getUpTime >24 | getUpTime < 0 | sleepTime >24 | sleepTime <0) {
            throw new ImproperInputException("improper input of getUpTime and sleepTime");
        }
        else if(getUpTime>sleepTime) {
            throw new NotLogicalInputException("getUpTime should be smaller than sleepTime");
        }
    }

    //Effects:inquire whether user have known events, if any, show card3, otherwise show card4
    private void preCard34(){
        JFrame preCard3 = new JFrame("Event Inquiry");
        preCard3.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        preCard3.setBounds(700,380,400,300);
        preCard3.setVisible(true);

        JLabel msg = new JLabel("            Do you have known event?");
        msg.setFont(new Font("Serif", Font.PLAIN,20));

        JButton yes = new JButton("yes");
        JButton no = new JButton("no");
        JButton go = new JButton("GO!");
        JPanel yesORno = new JPanel(new GridLayout(2,1));
        yesORno.setBorder(BorderFactory.createEmptyBorder(0,30,15,30));

        GridLayout little = new GridLayout(3,1);
        little.setVgap(10);
        JPanel bYN = new JPanel(little);
        bYN.setBorder(BorderFactory.createEmptyBorder(0,100,5,100));
        bYN.add(yes);
        bYN.add(no);
        bYN.add(go);

        yesORno.add(msg);
        yesORno.add(bYN);
        preCard3.getContentPane().add(yesORno);

        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                judgeEvent = 1;
                user.setJudgeEvent(1);
                System.out.println("judgeEvent = "+judgeEvent);
            }
        });

        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                judgeEvent=2;
                user.setJudgeEvent(2);
                System.out.println("judgeEvent = "+judgeEvent);

            }
        });

        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(judgeEvent==1) {
                    preCard3.setVisible(false);
                    cardLayout.show(cards, "card3");
                }else if(judgeEvent==2) {
                    eventsManager = new EventsManager();
                    eventsManager.generateEvents(sleepTime-getUpTime,identity);
                    events.addAll(eventsManager.getEventsBuilder());
                    user.setEvents(events);
                    System.out.println("\n");
                    for(String s:events)
                        System.out.println("event :"+s);

                    preCard3.setVisible(false);
                    setCard4();
                    cardLayout.show(cards, "card4");
                }
            }
        });
    }

    //Effects: paint card3 - have known events
    private void initializeCard3(){
        GridLayout gd3 = new GridLayout(1,2);
        gd3.setHgap(20);
        card3 = new JPanel(gd3);
        card3.setBorder(BorderFactory.createEmptyBorder(40,30,40,50));
        card3.setOpaque(false);
        card3.setVisible(true);

        //paint buttons panel
        GridLayout buttonsLO = new GridLayout(3,1);
        buttonsLO.setVgap(30);
        JPanel buttons = new JPanel(buttonsLO);
        buttons.setBorder(BorderFactory.createEmptyBorder(80,80,80,50));

        apply3 = new JButton("apply");
        apply3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    events.clear();
                    getInputEvents();
                    next3.setEnabled(true);
                }catch(ImproperInputException e1){
                    JOptionPane.showMessageDialog(null,"please input any known event !");
                }
            }
        });

        next3 = new JButton("next");
        next3.setEnabled(false);
        next3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventsManager = new EventsManager();
                eventsManager.initializeEvents(events,sleepTime-getUpTime,identity);
                user.setEvents(eventsManager.getEventsBuilder());
                events = eventsManager.getEventsBuilder();
                numOfEvent = events.size();
                user.setNumOfEvents(numOfEvent);
                getEvents.setText("");
                setCard4();
                cardLayout.show(cards,"card4");
            }
        });

        back3 = new JButton("back");
        back3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventsManager = new EventsManager();
                judgeEvent=0;

                events=new ArrayList<>();
                user.setEvents(events);
                numOfEvent=0;
                user.setNumOfEvents(0);
                getEvents.setText("");

                cardLayout.show(cards,"card2");
                getEvents.setText("");
                textField1.setText("");
                textField2.setText("");

                next3.setEnabled(false);
            }
        });

        buttons.add(apply3);
        buttons.add(next3);
        buttons.add(back3);
        buttons.setOpaque(false);
        card3.add(buttons);

        //paint scroll textPane
        getEvents = new JTextArea();
        getEvents.setFont(new Font("Serif", Font.PLAIN,18));

        JScrollPane getEventsScrollPane = new JScrollPane(getEvents,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        getEventsScrollPane.setOpaque(false);
        card3.add(getEventsScrollPane);
    }

    //Effects: judge whether user input events, auxiliary function of card 3
    private void getInputEvents()throws ImproperInputException{
        if(getEvents.getText().length()==0)
            throw new ImproperInputException("did not input any event");

        //get events from text area
        String[] lineString;
        lineString = getEvents.getText().split("\n");
        for(int i=0;i<lineString.length;i++){
            if(!lineString[i].isEmpty()) {
                events.add(lineString[i]);
                System.out.println(lineString[i]);
            }else{ }
        }
    }

    //Effects: pass params to user's information before card4
//    private void setInfo(){
//        user.setIdentity(identity);
//        user.setGetUpTime(getUpTime);
//        user.setSleepTime(sleepTime);
//        user.setJudgeEvent(judgeEvent);
//
//        if(judgeEvent==1) {
//            user.setEvents(events);
//            eventsManager.initializeEvents(events,sleepTime-getUpTime,identity);
//        }else{
//            eventsManager.generateEvents(sleepTime-getUpTime,identity);
//            events.addAll(eventsManager.getEventsBuilder());
//        }
//    }

    //Effects: publish user's personal information and save to a txt file
    //         used for debugging
    private void makeSure() {
        System.out.println("\n\nHere is your basic information:");
        if (user.getIdentity() == 1) {
            System.out.println("You are a student.");
        } else {
            System.out.println("You are a worker.");
        }

        System.out.println("You get up at " + user.getGetUpTime() + ":00.");
        System.out.println("You go to bed at " + user.getSleepTime() + ":00.");

        if(user.getJudgeE()==1){
            System.out.println("You have events to do :");
            for(int i=0;i<user.getNumEvents();i++) {
                System.out.println((i+1)+" : "+user.getEvents().get(i));
            }
        }else{
            System.out.println("You do not have events for schedule. Don't worry.");
        }

    }

    //Effects: save user's information to a txt file
    private void save(){
        try {
            List<String> lines = Files.readAllLines(Paths.get("D:\\2018 winter\\CPSC 210\\projectw1_team999\\userData.txt"));
            PrintWriter writer = new PrintWriter("userData.txt");

            if (user.getIdentity() == 1) {
                lines.add("Student");
            } else {
                lines.add("Worker");
            }

            lines.add("Get up at " + user.getGetUpTime() + ":00");
            lines.add("Go to bed at " + user.getSleepTime() + ":00");

            if (user.getJudgeE() == 1) {
                for (int i = 0; i < user.getNumEvents(); ++i) {
                    lines.add("Have special events: " + user.getEvents().get(i));
                }
            }else{
                lines.add("No special events.");
            }

            for (int i = 0; i < lines.size(); ++i) {
                writer.println(lines.get(i));
            }

            writer.close();
        }catch(IOException io){
            io.getStackTrace();
        }
    }

    //Effects: set params for card4
    private void setCard4(){
        makeSure();
        save();

        GridLayout gd4 = new GridLayout(1,2);
        gd4.setHgap(40);
        card4.setLayout(gd4);
        card4.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        //paint info panel (left)
        GridLayout gd4_1 = new GridLayout(3,1);
        gd4_1.setVgap(8);
        infoPanel = new JPanel(gd4_1);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

        //paint identity panel
        imageLabel = new JLabel();
        if(identity==1) {
            imageLabel.setIcon(studentIcon);
            imageLabel.setBounds(0,0,studentIcon.getIconWidth(),studentIcon.getIconHeight());
        }
        else if(identity==2) {
            imageLabel.setIcon(workerIcon);
            imageLabel.setBounds(0,0,workerIcon.getIconWidth(),workerIcon.getIconHeight());
        }
        imageLabel.setOpaque(false);
        infoPanel.add(imageLabel);

        //paint getUpTime label and sleepTime label
        GridLayout gd4_1_1 = new GridLayout(3,1);
        gd4_1_1.setVgap(2);
        msgPanel = new JPanel(gd4_1_1);
        msgPanel.setBorder(BorderFactory.createEmptyBorder(5,25,5,10));

        msgGetUpTime = new JLabel("Get up time : "+getUpTime);
        msgGetUpTime.setFont(new Font("Serif", Font.PLAIN,20));

        msgSleepTime = new JLabel("Sleep time : "+sleepTime);
        msgSleepTime.setFont(new Font("Serif", Font.PLAIN,20));

        msgKnownEvent = new JLabel();
        msgKnownEvent.setFont(new Font("Serif", Font.PLAIN,20));

        if(judgeEvent==1)
            msgKnownEvent.setText("Have plans.");
        else if(judgeEvent==2)
            msgKnownEvent.setText("No plans.");

        msgPanel.setForeground(new Color(82,6,16));
        msgPanel.add(msgGetUpTime);
        msgPanel.add(msgSleepTime);
        msgPanel.add(msgKnownEvent);
        msgPanel.setOpaque(false);
        infoPanel.add(msgPanel);

        GridLayout gd4_1_2 = new GridLayout(1,2);
        gd4_1_2.setHgap(10);
        buttonsPanel4 = new JPanel(gd4_1_2);
        buttonsPanel4.setBorder(BorderFactory.createEmptyBorder(30,15,30,10));
        generate = new JButton("generate");
        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<numOfEvent;i++) {
                    scheduleLables[i].setText("");
                }
                schedule = new Schedule(user,events);
                for(int i=0;i<numOfEvent;i++) {
                    scheduleLables[i].setText(schedule.getTimeList().get(i)+"   "+schedule.getScheduleEvents().get(i));
                    schedulePanel.add(scheduleLables[i]);
                }
            }
        });

        back4 = new JButton("back");
        back4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(judgeEvent==1)
                    cardLayout.show(cards,"card3");
                else if(judgeEvent==2)
                    cardLayout.show(cards,"card2");
                resetCard4();
                next1.setEnabled(false);
                next2.setEnabled(false);
                next3.setEnabled(false);
            }

        });

        buttonsPanel4.add(generate);
        buttonsPanel4.add(back4);
        buttonsPanel4.setOpaque(false);
        infoPanel.add(buttonsPanel4);

        infoPanel.setOpaque(false);
        card4.add(infoPanel);

        //paint schedule panel (right)
        paintSchedulePanel();
        card4.add(schedulePanel);
    }

    //Effects: paint schedulePanel
    private void paintSchedulePanel(){
        schedule = new Schedule(user,events);
        numOfEvent = schedule.getTimeList().size();

        GridLayout gd4_2 = new GridLayout(numOfEvent,1);
        schedulePanel = new JPanel(gd4_2);
        scheduleLables = new JLabel[numOfEvent];

        for(int i=0;i<numOfEvent;i++) {
            scheduleLables[i] = new JLabel();
        }
    }

    //Effects: paint card4 - show the schedule
    private void initializeCard4(){
        card4.setOpaque(false);
        card4.setVisible(true);
    }

    //Effects: reset Card4
    private void resetCard4(){
        card4.remove(infoPanel);
        card4.remove(schedulePanel);
    }
}
