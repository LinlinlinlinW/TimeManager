package ui;

import exceptions.ImproperInputException;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class WeatherPanel{
    //fields for cards
    private CardLayout cardLayout = new CardLayout();
    private JPanel cards = new JPanel(cardLayout);

    //fields for card1
    private JPanel card1 = new JPanel();
    private JPanel cityPanel = new JPanel();
    private JPanel weatherChoicePanel = new JPanel();
    private JTextField textField;
    private String responseFromNet;//the name of the city
    private String city;
    private JPanel cityPanelBranch = new JPanel(new FlowLayout());
    private int numOfChoice=-1;
    private JPanel buttonPane = new JPanel();
    private JButton back = new JButton("BACK");
    private JButton next = new JButton("NEXT");
    private JButton confirm = new JButton("CONFIRM");
    private JLabel choiceLabel; //text message of "which city do you...?"
    private GridLayout gridLayout2 = new GridLayout(2,1);
    private JPanel buttonPanel = new JPanel(gridLayout2);
    private JButton buttonCurrent = new JButton("current weather");
    private JButton buttonForecast = new JButton("forecast");
    private JButton buttonApply = new JButton("APPLY");
    private JButton buttonClear = new JButton("CLEAR");

    //fields for popup currentWeather frame
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
    private JSONObject responseForecast;
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


    /************************ initialize card 1 *****************************/

    //Effects: initialize card 1
    private void initializeCard1(){
        paintCard1();
        card1.add(cityPanel);
        card1.add(weatherChoicePanel);
        card1.add(buttonPane);
    }

    //Effects: paint card1
    private void paintCard1(){
        // set up card1
        card1.setLayout(new GridLayout(3,1));
        card1.setOpaque(false);

        // paint cityNamePane
        paintCityNamePane();

        // paint weatherChoicePane - step 1: save space for this pane
        weatherChoicePanel.setOpaque(false);

        // paint buttonPane
        buttonPane.setOpaque(false);

        GridLayout gridLayout = new GridLayout(1,3);
        gridLayout.setHgap(30);
        buttonPane.setLayout(gridLayout);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(60,140,25,140));
        setButtonsInCard1();
        buttonPane.add(back);
        buttonPane.add(next);
        buttonPane.add(confirm);

        back.setEnabled(true);
        back.setVisible(true);

        next.setVisible(true);
        next.setEnabled(false);

        confirm.setVisible(true);
        confirm.setEnabled(false);
    }

    //Effects: set the looking of BACK CONFIRM NEXT button
    private void setButtonsInCard1(){
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
    }

    // Effects: initialize buttons in cityNamePane
    private void setCityNamePaneButtons(){
        buttonApply.setPreferredSize(new Dimension(72,40));
        buttonApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    getNameOfCity();
                    paintWeatherChoicePane();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "please input correctly: " + ex);
                }
            }
        });

        buttonClear.setPreferredSize(new Dimension(72,40));
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
    }

    //Effects: initialize buttons in weatherChoicePane
    private void setWeatherChoicePaneButtons(){
        //current weather button
        buttonCurrent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCurrentWeather();
                paintCurrentWeatherPanel();
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

    }

    //Effects: get the name of the city which the user want to know
    private void paintCityNamePane(){
        GridLayout gridLayout = new GridLayout(2,1);
        gridLayout.setHgap(50);
        gridLayout.setVgap(20);
        cityPanel.setLayout(gridLayout);
        cityPanel.setOpaque(false);

        JLabel cityLabel = new JLabel("Which city do you want to know about weather?");
        cityLabel.setBorder(BorderFactory.createEmptyBorder(75,160,50,120));
        cityLabel.setFont(new Font("Serif", Font.PLAIN,25));
        cityLabel.setOpaque(false);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(240,40));
        textField.setFont(new Font("Serif",Font.PLAIN,20));

        setCityNamePaneButtons();

        cityPanelBranch.setBorder(BorderFactory.createEmptyBorder(0,60,0,60));
        cityPanelBranch.add(textField);
        cityPanelBranch.add(buttonApply);
        cityPanelBranch.add(buttonClear);
        cityPanelBranch.setOpaque(false);

        cityPanel.add(cityLabel);
        cityPanel.add(cityPanelBranch);
    }

    //Effects: paint weatherChoicePane - step 2: paint the detail
    private void paintWeatherChoicePane(){
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

        setWeatherChoicePaneButtons();

        buttonPanel.add(buttonCurrent);
        buttonPanel.add(buttonForecast);

        weatherChoicePanel.add(choiceLabel);
        weatherChoicePanel.add(buttonPanel);

        weatherChoicePanel.setVisible(true);
    }

    //Effects: get name of city from API
    private void getNameOfCity(){
        try {
            String userInput = textField.getText();
            responseFromNet = getJSONFromAPI(userInput, "name").getJSONObject("location").getString("name");
            System.out.println("response from net is " + responseFromNet);
            city = responseFromNet;
        } catch(Exception e) {
            System.out.println("there is an exception in get name of city "+e);
        }
    }

    //Modifies: currentTemp and currentFeel
    //Effects: get current weather from API, not painting anything, just receiving from web
    private void getCurrentWeather(){
        try {
            JSONObject response = getJSONFromAPI(responseFromNet, "current");
            currentTemp = response.getJSONObject("current").getInt("temperature") + " centigrade";
            currentFeel = response.getJSONObject("current").getInt("feelslike") + " centigrade";

            System.out.println("currentTemp: "+currentTemp);
            System.out.println("currentFeel: "+currentFeel);
        }catch(Exception exception){
            System.out.println("error in getting currentTemp and currentFeel from API!" + exception);
        }
    }

    // Effects: helper function: given user's input, receive response from API
    private JSONObject getJSONFromAPI(String input, String type){
        try {
            String urlString = null;
            switch(type){
                case "name" :
                    urlString = ("http://api.weatherstack.com/current?access_key=0b5cdceaef29c5d6d19857b81ee9ac72&query=" + input);
                    break;
                case "current" :
                    urlString = ("http://api.weatherstack.com/current?access_key=0b5cdceaef29c5d6d19857b81ee9ac72&query=" + input);
                    break;
                case "forecast" :
                    urlString = ("http://api.weatherstack.com/forecast?access_key=b5ed8a29ca1dbdd127e5a5b7be3214c4&query=" + city + "&forecast_days=" + input);
                    //urlString = ("http://api.openweathermap.org/data/2.5/forecast/daily?q="+city+"&cnt="+input+"&appid=3d5db63369c34a5176e132a0b0ff461a");
                    break;
                default:
                    System.out.println("not a valid type of query!");
                    break;
            }


            URL url = new URL(urlString);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();
            String result = null;
            while ((result = br.readLine()) != null) {
                sb.append(result);
                sb.append(System.lineSeparator());
            }
            result = sb.toString();
            System.out.println("JSON from api is "+ result);
            JSONObject jsonObject = new JSONObject(result);
            return jsonObject;

        } catch (Exception exc) {
            System.out.println("Exception from API "+exc);
        }
        System.out.println("Fail to read from API");
        return null;
    }

    //Modifies: currentWeatherFrame, lp
    //Effects: just paint pop-up frame for CURRENT WEATHER,
    //         purposely set currentWeatherFrame invisible, purposely initialized without any concrete messages
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

    //Modifies: showUp, message1,message2
    //Effects: pass in params of currentTemp and currentFeel, prepare to print in the window
    private void setCurrentWeatherPanel(){
        showUp.setText(responseFromNet);
        message1.setText("current temperature : "+currentTemp);
        message2.setText("current feeling : "+currentFeel);
    }

    //Effects: pass the BACK button to UI
    public JButton getBACKButton(){
        return back;
    }


    /************************ initialize card 2 *****************************/

    //Effects: initialize card2. If user click "forecast weather"
    //         cardLayout will display card2, purposely set invisible
    private void initializeCard2(){
        GridLayout card2Layout = new GridLayout(3,1);
        card2Layout.setVgap(18);
        card2.setBorder(BorderFactory.createEmptyBorder(50,10,50,10));
        card2.setLayout(card2Layout);
        card2.setVisible(true);
        card2.setOpaque(false);

        paintMainPanel();
        setButtonsInCard2();
        paintForecastFrame();

        card2.add(labelPanel2);
        card2.add(textPanel);
    }

    //Effects: set buttons in card2
    private void setButtonsInCard2(){
        //set buttons' function
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get effective numOfDay
                try {
                    getForecastInfo();
                    setForecastBranchPane();
                }catch(ImproperInputException e2){
                    JOptionPane.showMessageDialog(null,"please input an integer between 1-7 "+ e2 );
                }catch(JSONException e3){
                    JOptionPane.showMessageDialog(null,"error in API "+e3);
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
    }

    //Effects: paint the main panel of card2
    private void paintMainPanel(){
        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        GridLayout buttonsLO = new GridLayout(1,3);
        buttonsLO.setHgap(30);
        buttons.setBorder(BorderFactory.createEmptyBorder(0,100,0,100));
        buttons.setLayout(buttonsLO);

        apply.setFont(new Font("Serif", Font.PLAIN,20));
        reset.setFont(new Font("Serif", Font.PLAIN,20));
        backToPre.setFont(new Font("Serif", Font.PLAIN,20));

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
        textPanel.setBorder(BorderFactory.createEmptyBorder(0,180,0,180));
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
    private void getForecastInfo()throws ImproperInputException{

        //get numOfDay from user
        numOfDay = Integer.parseInt(text2.getText());

        //debug numOfDay and city
        System.out.println("numOfDay is "+numOfDay);
        System.out.println("city is "+city);

        //the integer should be in the range 0 - 7
        if(numOfDay>7 | numOfDay <=0)
            throw new ImproperInputException("Input out of range !");

        //get INFO from API
        responseForecast = getJSONFromAPI(Integer.toString(numOfDay), "forecast");

        // see whether the JSON is what we want
        try{
            FileWriter file = new FileWriter("./src/JSON.txt");
            file.write(responseForecast.toString());
        } catch (Exception exc) {
            System.out.println("error in forecast JSON " + exc);
        }
//        String netAdd ="http://api.weatherstack.com/forecast?access_key=0b5cdceaef29c5d6d19857b81ee9ac72&query="+responseFromNet;
//        URL url = new URL(netAdd);
//        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
//        String result;
//        StringBuilder sb = new StringBuilder();
//        while ((result=br.readLine())!=null){
//            sb.append(result);
//            sb.append(System.lineSeparator());
//        }
//        result=sb.toString();
//        response = new JSONObject(result);
    }

    //Effects: initialize panels shown in the frame
    private void setForecastBranchPane()throws JSONException{
        forecastPanelBranch = new JPanel[numOfDay];//store days' dayMessage, maxTemp, minTemp, condition
        dayMessage = new JLabel[numOfDay];//store the number of forecast day(max 10)
        maxTemp = new JLabel[numOfDay];//store the max temp of each forecast day(max 10)
        minTemp = new JLabel[numOfDay];//store the min temp of each forecast day(max 10)
        condition = new JLabel[numOfDay];//store the feeling of each forecast day(max 10)


        //debug the message in the console
        for(int i=0;i<numOfDay;i++) {
            System.out.println("The weather in "+(i+1)+" day:");
            System.out.println("The highest temperature  is " + responseForecast.getJSONObject("forecast").
                    getJSONArray("forecastday").getJSONObject(i).getJSONObject("day").getInt("maxtemp_c") + " centigrade.");
            System.out.println("The lowest temperature  is " + responseForecast.getJSONObject("forecast").
                    getJSONArray("forecastday").getJSONObject(i).getJSONObject("day").getInt("mintemp_c") + " centigrade.");
            System.out.println("The weather is likely to be " + responseForecast.getJSONObject("forecast").
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

            maxTemp[i] = new JLabel("The highest temperature  is " + responseForecast.getJSONObject("forecast").
                    getJSONArray("forecastday").getJSONObject(i).getJSONObject("day").getInt("maxtemp_c") + " centigrade.");
            maxTemp[i].setFont(new Font("Serif", Font.PLAIN,23));
            maxTemp[i].setOpaque(false);
            forecastPanelBranch[i].add(maxTemp[i]);

            minTemp[i] = new JLabel("The lowest temperature  is " + responseForecast.getJSONObject("forecast").
                    getJSONArray("forecastday").getJSONObject(i).getJSONObject("day").getInt("mintemp_c") + " centigrade.");
            minTemp[i].setFont(new Font("Serif", Font.PLAIN,23));
            minTemp[i].setOpaque(false);
            forecastPanelBranch[i].add(minTemp[i]);

            condition[i] = new JLabel("The weather is likely to be " + responseForecast.getJSONObject("forecast").
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





