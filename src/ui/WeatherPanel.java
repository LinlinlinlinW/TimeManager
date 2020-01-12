package ui;

import exceptions.ImproperInputException;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class WeatherPanel{
    //fields for cards
    private CardLayout cardLayout = new CardLayout();
    private JPanel cards = new JPanel(cardLayout);

    //fields for card1
    private JPanel card1 = new JPanel();
    private JPanel cityPanel = new JPanel();
    private JTextField textField;
    private String responseFromNet;//the name of the city
    private String city;
    private JPanel weatherChoicePanel = new JPanel();
    private JPanel cityPanelBranch = new JPanel(new FlowLayout());
    private int numOfChoice=0;
    private JButton back = new JButton("BACK");
    private JButton next = new JButton("NEXT");
    private JButton confirm = new JButton("CONFIRM");
    private JLabel choiceLabel;//text message of "which city do you...?"
    private JButton buttonCurrent = new JButton("current weather");
    private JButton buttonForecast = new JButton("forecast");
    private JButton buttonApply = new JButton("APPLY");
    private JButton buttonClear = new JButton("CLEAR");
    private GridLayout gridLayout2 = new GridLayout(2,1);
    private JPanel buttonPanel = new JPanel(gridLayout2);

    //little popup frame
    private ImageIcon bkCurrentWeather = new ImageIcon("src\\bkgWeather.jpg");
    private JFrame currentWeatherFrame = new JFrame("CURRENT TEMPERATURE");
    private JLabel imgLabel = new JLabel(bkCurrentWeather);
    private JLabel showUp;
    private JLabel message1;
    private JLabel message2;


    //fields for card2
    private JPanel card2 = new JPanel();
    private int numOfDay=0;
    private String currentTemp;
    private String currentFeel;
    private JPanel labelPanel2 = new JPanel(new FlowLayout());

    private JPanel textPanel = new JPanel();
    private JTextField text2 = new JTextField();
    private JButton apply = new JButton("apply");
    private JButton reset = new JButton("reset");
    private JButton backToPre = new JButton("back");

    //pop-up frame
    private ImageIcon bkForecastWeather = bkCurrentWeather;
    private JFrame forecastWeatherFrame = new JFrame("FORECAST");
    private JLabel imgLabel2 = new JLabel(bkForecastWeather);
    private JPanel forecastContentPanel;
    private JSONObject response;
    private JPanel[] forecastPanelBranch;
    private JLabel[] dayMessage;
    private JLabel[] maxTemp;
    private JLabel[] minTemp;
    private JLabel[] condition;

    //Effects: return all the things in WEATHER button
    public JPanel getWeatherPanel(){
        return cards;
    }

    //constructor
    public WeatherPanel(){
        initializeCard1();
        initializeCard2();
        cards.setOpaque(false);
        cards.add("card1",card1);
        cards.add("card2",card2);
        cardLayout.show(cards,"card1");
    }


    /************************initialize card 1*****************************/

    //Effects: initialize card 1
    private void initializeCard1(){
        card1.setLayout(new GridLayout(3,1));
        card1.setOpaque(false);
        initializeCityPanel();
        card1.add(cityPanel);
        initializeWeatherChoicePanel();
        setButtonsInCard1();
    }

    //Effects: get the city's name that user want to inquiry about
    private void initializeCityPanel(){
        GridLayout gridLayout = new GridLayout(2,1);
        gridLayout.setHgap(50);
        gridLayout.setVgap(20);
        cityPanel.setLayout(gridLayout);
        cityPanel.setOpaque(false);

        JLabel cityLabel = new JLabel("Which city do you want to know about weather?");
        cityLabel.setBorder(BorderFactory.createEmptyBorder(75,120,45,0));
        cityLabel.setFont(new Font("Serif", Font.PLAIN,23));
        cityLabel.setOpaque(false);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(260,35));
        textField.setFont(new Font("Serif",Font.PLAIN,20));

        buttonApply.setPreferredSize(new Dimension(72,35));
        buttonApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    getNameOfCity();
                    getCurrentWeatherFromAPI();//get current weather information, prepared for CURRENT
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "please input effective name!");
                }
            }
        });


        buttonClear.setPreferredSize(new Dimension(72,35));
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
                city = " ";
                weatherChoicePanel.setVisible(false);
                next.setEnabled(false);
                confirm.setEnabled(false);
            }
        });

        cityPanelBranch.setBorder(BorderFactory.createEmptyBorder(0,60,0,60));
        cityPanelBranch.add(textField);
        cityPanelBranch.add(buttonApply);
        cityPanelBranch.add(buttonClear);
        cityPanelBranch.setOpaque(false);

        cityPanel.add(cityLabel);
        cityPanel.add(cityPanelBranch);

    }

    //Effects: get user's answer of the name of the city
    private void getNameOfCity(){
        try {
            String userInput = textField.getText();
            String nameOfCity = ("http://api.weatherstack.com/current?access_key=0b5cdceaef29c5d6d19857b81ee9ac72&query=" + userInput);
            URL url = new URL(nameOfCity);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String result;
            StringBuilder sb = new StringBuilder();
            while ((result = br.readLine()) != null) {
                sb.append(result);
                sb.append(System.lineSeparator());
            }
            result = sb.toString();
            JSONObject response = new JSONObject(result);
            responseFromNet = response.getJSONObject("location").getString("name");
            System.out.println("response from net is " + responseFromNet);

            city = responseFromNet;
            getWeatherChoice();
            weatherChoicePanel.setVisible(true);
        } catch(Exception e) {
            System.out.println("there is an exception in get name of city "+e);
        }

    }

    //Effects: initialize weatherChoicePanel
    //         purposely set invisible
    private void initializeWeatherChoicePanel(){
        weatherChoicePanel.setOpaque(false);
        weatherChoicePanel.setVisible(false);
        paintCurrentWeatherPanel();
        card1.add(weatherChoicePanel);
    }

    //Effects: prepare the weatherChoicePanel for APPLY button
    //         once APPLY button clicked, the weatherChoicePanel get shown
    //         purposely set invisible
    private void getWeatherChoice(){
        if (choiceLabel == null) {
            choiceLabel = new JLabel();
        }
        choiceLabel.setText("What do you want to know about the weather in " + city +"?");
        choiceLabel.repaint();
        choiceLabel.setVisible(true);
        choiceLabel.setBorder(BorderFactory.createEmptyBorder(10,10,0,0));
        choiceLabel.setFont(new Font("Serif", Font.PLAIN,23));
        choiceLabel.setOpaque(false);

        gridLayout2.setVgap(10);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(8,70,3,70));
        buttonPanel.setOpaque(false);

        //current weather button
        buttonCurrent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numOfChoice = 1;
                confirm.setEnabled(true);
                next.setEnabled(false);
            }
        });

        //forecast weather button
        buttonForecast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numOfChoice = 2;
                next.setEnabled(true);
                confirm.setEnabled(false);
            }
        });

        buttonPanel.add(buttonCurrent);
        buttonPanel.add(buttonForecast);

        weatherChoicePanel.add(choiceLabel);
        weatherChoicePanel.add(buttonPanel);
        weatherChoicePanel.setVisible(false);

    }

    //Effects: set the looking of BACK CONFIRM NEXT button
    private void setButtonsInCard1(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        GridLayout gridLayout = new GridLayout(1,3);
        gridLayout.setHgap(30);
        buttonPanel.setLayout(gridLayout);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(60,140,25,140));

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
                confirm.setEnabled(false);
                next.setEnabled(false);
                weatherChoicePanel.setVisible(false);
            }
        });

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCurrentWeatherPanel();
                currentWeatherFrame.setVisible(true);
            }
        });

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards,"card2");
            }
        });

        buttonPanel.add(back);
        buttonPanel.add(next);
        buttonPanel.add(confirm);

        back.setEnabled(true);
        back.setVisible(true);

        next.setVisible(true);
        next.setEnabled(false);

        confirm.setVisible(true);
        confirm.setEnabled(false);

        card1.add(buttonPanel);
    }

    //Modifies: currentTemp and currentFeel
    //Effects: get current weather from API
    //         not paint anything, just receiving from web
    private void getCurrentWeatherFromAPI(){
        try {
            URL url = new URL("http://api.weatherstack.com/current?access_key=0b5cdceaef29c5d6d19857b81ee9ac72&query="+responseFromNet);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String result;
            StringBuilder sb = new StringBuilder();
            while ((result = br.readLine()) != null) {
                sb.append(result);
                sb.append(System.lineSeparator());
            }
            result = sb.toString();

            JSONObject response = new JSONObject(result);
            currentTemp = response.getJSONObject("current").getInt("temperature") + " centigrade";
            currentFeel = response.getJSONObject("current").getInt("feelslike") + " centigrade";

            System.out.println("currentTemp: "+currentTemp);
            System.out.println("currentFeel: "+currentFeel);
        }catch(IOException | JSONException exception){
            System.out.println("error in getting currentTemp and currentFeel from API!" + exception);
        }
    }

    //Modifies: showUp, message1,message2
    //Effects: pass in params of currentTemp and currentFeel, prepare to print in the window
    private void setCurrentWeatherPanel(){
        showUp.setText(responseFromNet);
        message1.setText("current temperature : "+currentTemp);
        message2.setText("current feeling : "+currentFeel);
    }

    //Modifies: currentWeatherFrame, lp
    //Effects: just paint pop-up frame for CURRENT WEATHER
    //         purposely set currentWeatherFrame invisible
    //         purposely initialized without any concrete messages
    private void paintCurrentWeatherPanel() {
        currentWeatherFrame.setBounds(700,380, bkCurrentWeather.getIconWidth()/2, bkCurrentWeather.getIconHeight()/2);
        currentWeatherFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        currentWeatherFrame.setResizable(false);
        currentWeatherFrame.setVisible(false);

        JLayeredPane lp = currentWeatherFrame.getLayeredPane();
        imgLabel.setBounds(0,0, bkCurrentWeather.getIconWidth()/2, bkCurrentWeather.getIconHeight()/2);
        lp.add(imgLabel,new Integer(Integer.MIN_VALUE));
        lp.setOpaque(true);

        //add message labels to a panel
        JPanel panelMessage = (JPanel) currentWeatherFrame.getContentPane();
        panelMessage.setBorder(BorderFactory.createEmptyBorder(50,68,68,50));
        panelMessage.setLayout(new GridLayout(3,1));

        showUp = new JLabel();
        message1 = new JLabel();
        message2 = new JLabel();
        showUp.setFont(new Font("Serif",Font.BOLD,30));
        message1.setFont(new Font("Serif",Font.BOLD,22));
        message2.setFont(new Font("Serif",Font.BOLD,22));

        panelMessage.setOpaque(false);
        panelMessage.add(showUp);
        panelMessage.add(message1);
        panelMessage.add(message2);
    }

    //Effects: pass the BACK button to UI
    public JButton getBACKButton(){
        return back;
    }



    /************************initialize card 2*****************************/

    //Effects: initialize card2
    //         if user click "forecast weather"
    //         cardLayout will display card2
    //         purposely set invisible
    private void initializeCard2(){
        //GridLayout card2Layout = new GridLayout(3,1);
        //card2Layout.setVgap(18);
        //card2.setBorder(BorderFactory.createEmptyBorder(50,10,50,10));
        //card2.setLayout(card2Layout);
        card2.setVisible(true);
        card2.setOpaque(false);

        card2.add(poor());

        //paintMainPanel();
        //paintForecastFrame();
        //card2.add(labelPanel2);
        //card2.add(textPanel);
    }

    //Effects: paint the poor panel of card2
    private JPanel poor(){
        JPanel jPanel = new JPanel(new GridLayout(2,1));
        JLabel text1 = new JLabel("Due to the limited expenditure, current API plan does not support forecast function(but it used to!!)");
        JLabel text2 = new JLabel("Thus I am forwardly looking for a COOP job, to support my tech-development!");
        text1.setFont(new Font("Serif", Font.BOLD, 17));
        text2.setFont(new Font("Serif", Font.BOLD, 17));
        //ImageIcon cry = new ImageIcon("src\\cry.png");
        //JLabel imgL = new JLabel(cry);
        jPanel.add(text1);
        jPanel.add(text2);
        //jPanel.add(imgL);
        jPanel.setBorder(BorderFactory.createEmptyBorder(100,5,5,5));
        jPanel.setOpaque(false);
        return jPanel;
    }

    //Effects: paint the main panel of card2
    private void paintMainPanel(){
        //set buttons' function
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get effective numOfDay
                try {
                    getInfoFromNet();
                    initializeBranchPanel();
                }catch(IOException e1){
                    JOptionPane.showMessageDialog(null, "please input an INTEGER !");
                }catch(ImproperInputException e2){
                    JOptionPane.showMessageDialog(null,"please input an integer between 1-7 !");
                }catch(JSONException e3){
                    JOptionPane.showMessageDialog(null,"error in API !");
                }

                setForecastFrame();
                forecastWeatherFrame.setVisible(true);
            }
        });

        backToPre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards,"card1");
                text2.setText("");
                resetForecastPanel();
                numOfDay = 0;
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text2.setText("");
                resetForecastPanel();
                numOfDay = 0;
            }
        });

        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        GridLayout buttonsLO = new GridLayout(1,3);
        buttonsLO.setHgap(40);
        buttons.setBorder(BorderFactory.createEmptyBorder(0,100,0,100));
        buttons.setLayout(buttonsLO);

        buttons.add(apply);
        buttons.add(reset);
        buttons.add(backToPre);

        //set message in labelPanel2: how many days do you want to forecast
        JLabel message = new JLabel("How many days do you want to forecast?");
        message.setFont(new Font("Serif", Font.PLAIN,23));
        labelPanel2.add(message);
        labelPanel2.setOpaque(false);
        labelPanel2.setBorder(BorderFactory.createEmptyBorder(40,50,0,40));

        //set buttons in the textPanel
        GridLayout textPanelLayout = new GridLayout(2,1);
        textPanelLayout.setVgap(20);
        textPanel.setBorder(BorderFactory.createEmptyBorder(0,100,0,100));
        textPanel.setLayout(textPanelLayout);
        textPanel.setOpaque(false);
        text2.setFont(new Font("Serif", Font.PLAIN,23));

        //add textField(text2) and buttons panel to the textPanel
        textPanel.add(text2);
        textPanel.add(buttons);
        textPanel.setOpaque(false);
    }

    //Effects: paint pop-up window of forecast weather
    private void paintForecastFrame(){
        forecastWeatherFrame.setBounds(380,0,bkForecastWeather.getIconWidth(),bkForecastWeather.getIconHeight());
        forecastWeatherFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        forecastWeatherFrame.setVisible(false);
        forecastWeatherFrame.setResizable(false);

        JLayeredPane forecastLP = forecastWeatherFrame.getLayeredPane();
        imgLabel2.setBounds(0,0,bkForecastWeather.getIconWidth(), bkForecastWeather.getIconHeight());
        forecastLP.add(imgLabel2,new Integer(Integer.MIN_VALUE));
        forecastLP.setOpaque(true);
    }

    //Effects: get forecast information from API
    private void getInfoFromNet()throws JSONException, IOException,ImproperInputException{
        //get numOfDay from user
        numOfDay = Integer.parseInt(text2.getText());

        //debug numOfDay and city
        System.out.println("numOfDay is "+numOfDay);
        System.out.println("city is "+city);

        //the integer should be in the range 0 - 10
        if(numOfDay>7 | numOfDay <=0)
            throw new ImproperInputException("Input out of range !");

        //get INFO from API
        String netAdd ="http://api.weatherstack.com/forecast?access_key=0b5cdceaef29c5d6d19857b81ee9ac72&query="+responseFromNet;
        URL url = new URL(netAdd);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String result;
        StringBuilder sb = new StringBuilder();
        while ((result=br.readLine())!=null){
            sb.append(result);
            sb.append(System.lineSeparator());
        }
        result=sb.toString();
        response = new JSONObject(result);
    }

    //Effects: initialize panels shown in the frame
    private void initializeBranchPanel()throws JSONException{
        forecastPanelBranch = new JPanel[numOfDay];//store 10 days' dayMessage, maxTemp, minTemp, condition
        dayMessage = new JLabel[numOfDay];//store the number of forecast day(max 10)
        maxTemp = new JLabel[numOfDay];//store the max temp of each forecast day(max 10)
        minTemp = new JLabel[numOfDay];//store the min temp of each forecast day(max 10)
        condition = new JLabel[numOfDay];//store the feeling of each forecast day(max 10)


        //debug the message in the console
        for(int i=0;i<numOfDay;i++) {
            System.out.println("The weather in "+(i+1)+" day:");
            System.out.println("The highest temperature  is " + response.getJSONObject("forecast").
                    getJSONArray("forecastday").getJSONObject(i).getJSONObject("day").getInt("maxtemp_c") + " centigrade.");
            System.out.println("The lowest temperature  is " + response.getJSONObject("forecast").
                    getJSONArray("forecastday").getJSONObject(i).getJSONObject("day").getInt("mintemp_c") + " centigrade.");
            System.out.println("The weather is likely to be " + response.getJSONObject("forecast").
                    getJSONArray("forecastday").getJSONObject(i).getJSONObject("day").getJSONObject("condition").getString("text")+"\n");
        }

        GridLayout gridLayoutForBranchPanel = new GridLayout(4,1);

        for(int i=0;i<numOfDay;i++){
            forecastPanelBranch[i] = new JPanel(gridLayoutForBranchPanel);
            forecastPanelBranch[i].setBorder(BorderFactory.createEmptyBorder(3,200,3,200));
            forecastPanelBranch[i].setOpaque(false);

            dayMessage[i] = new JLabel("The weather in day "+(i+1));
            dayMessage[i].setFont(new Font("Serif", Font.PLAIN,25));
            dayMessage[i].setOpaque(false);
            forecastPanelBranch[i].add(dayMessage[i]);

            maxTemp[i] = new JLabel("The highest temperature  is " + response.getJSONObject("forecast").
                    getJSONArray("forecastday").getJSONObject(i).getJSONObject("day").getInt("maxtemp_c") + " centigrade.");
            maxTemp[i].setFont(new Font("Serif", Font.PLAIN,23));
            maxTemp[i].setOpaque(false);
            forecastPanelBranch[i].add(maxTemp[i]);

            minTemp[i] = new JLabel("The lowest temperature  is " + response.getJSONObject("forecast").
                    getJSONArray("forecastday").getJSONObject(i).getJSONObject("day").getInt("mintemp_c") + " centigrade.");
            minTemp[i].setFont(new Font("Serif", Font.PLAIN,23));
            minTemp[i].setOpaque(false);
            forecastPanelBranch[i].add(minTemp[i]);

            condition[i] = new JLabel("The weather is likely to be " + response.getJSONObject("forecast").
                    getJSONArray("forecastday").getJSONObject(i).getJSONObject("day").getJSONObject("condition").getString("text"));
            condition[i].setFont(new Font("Serif", Font.PLAIN,23));
            condition[i].setOpaque(false);
            forecastPanelBranch[i].add(condition[i]);

        }

    }

    //Effects: pass in params and paint final frame
    private void setForecastFrame(){
        GridLayout contentPanelLayout = new GridLayout(numOfDay,1);
        contentPanelLayout.setVgap(25);
        forecastContentPanel = (JPanel) forecastWeatherFrame.getContentPane();
        forecastContentPanel.setOpaque(false);
        forecastContentPanel.setLayout(contentPanelLayout);
        forecastContentPanel.setBorder(BorderFactory.createEmptyBorder(10,80,10,50));

        for(int i=0;i<numOfDay;i++)
            forecastContentPanel.add(forecastPanelBranch[i]);

    }

    //Effects: clear all the things in forecastContentPanel
    private void resetForecastPanel() {
        for (int i = 0; i < numOfDay; i++) {
            forecastPanelBranch[i].removeAll();
            dayMessage[i].removeAll();
            maxTemp[i].removeAll();
            minTemp[i].removeAll();
            condition[i].removeAll();
            forecastContentPanel.remove(forecastPanelBranch[i]);
        }
    }
}





