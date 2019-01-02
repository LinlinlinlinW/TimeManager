package objects;


/* this is the class before designing UI*/
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class TimeManager extends Subject{
    private User user;
    private EventsManager eventsManager;
 //   private QuestionsArray questionsArray;

    //Effects: the constructor of class TimeManager
    public TimeManager() {
        user=new User();
        eventsManager=new EventsManager();
    //    questionsArray = new QuestionsArray(6);

        addObserver(user);

    //    setQuestions(questionsArray);
        interaction();
        makeSure();
        Schedule choosedSchedule = chooseSchedule(user);

        System.out.println("test add new event:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = br.readLine();
            eventsManager.addEventsToDB(line);
        }catch(IOException e){
            System.out.println("nothing");
        }

        if(notifyObservers()==1){
            removeObserver(user);
        }
    }

    @Override
    public int notifyObservers(){
        int confirm =0;
        for(Observer o:observers) {
            System.out.println("\n\nDid you finish all the events?\n[1]yes [2]no");
            confirm = new Scanner(System.in).nextInt();
            if (confirm == 1) {
                o.update(1);
            } else {
                o.update(2);
            }
        }
        return confirm;
    }

    //Effects: (for the use of reducing duplication) branch of interaction
    private void branchOfInteraction(int i){
        boolean retry;
        int haveEvent;
        //  printQuestion(questionsArray, i);
        if(i==0) {
            user.setIdentity(Integer.parseInt(getUserAnswer()));
        }
            else if(i==1){
            user.setGetUpTime(Integer.parseInt(getUserAnswer()));
        }
            else if(i==2){
            user.setSleepTime(Integer.parseInt(getUserAnswer()));
        }
            else if(i==3){
            haveEvent = Integer.parseInt(getUserAnswer());
            user.setJudgeEvent(haveEvent);
            if (haveEvent == 1) {
                int num;
                //     printQuestion(questionsArray, 4);
                num = Integer.parseInt(getUserAnswer());
                user.setNumOfEvents(num);
                //    printQuestion(questionsArray, 5);
//                        for (int j = 0; j < num; j++) {
//                            user.setEvents(getUserAnswer());
//                        }
                eventsManager.initializeEvents(user.getEvents(),user.getSleepTime()-user.getGetUpTime(),user.getIdentity());
                }else{
                eventsManager.generateEvents(user.getSleepTime()-user.getGetUpTime(),user.getIdentity());
                }
        }
    }

    //Effects: show the questions to user and get answers back
    private void interaction(){
        for(int j=0;j<4;j++){
            branchOfInteraction(j);
        }
    }

    //Modifies: QuestionsArray qa
    //Effects: add questions to the questions array
//    private void setQuestions(QuestionsArray qa){
//        //question 1,index 0
//        qa.setQuestion("Are you a student or a worker?\n[1]student [2]worker");
//
//        //question 2,index 1
//        qa.setQuestion("When do you plan to get up (24-hour format)?");
//
//        //question 3,index 2
//        qa.setQuestion("When do you plan to go to bed (24-hour format)?");
//
//        //question 4,index 3
//        qa.setQuestion("Do you have special event for tomorrow?\n[1]yes [2]no");
//
//        //question 5,index 4
//        qa.setQuestion("How many known events do you have for tomorrow?");
//
//        //question 6,index 5
//        qa.setQuestion("Please input planned events:");
//    }
//
//    //Effects: print the question of index i
//    private void printQuestion(QuestionsArray qa, int i){
//        System.out.println(qa.getAPieceOfQuestion(i));
//    }

    //Effects: get and return user's answer
    private String getUserAnswer(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line = null;
        try {
            line = br.readLine();
        } catch (IOException ioe) {
            System.out.println("IO error trying to read a line of text.");
            System.exit(1);
        }
        return line;
    }

    //Effects: publish user's personal information and save to a txt file
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
            for(int i=0;i<user.getNumEvents();i++) {
                System.out.println("You have events to do :");
                System.out.println(user.getEvents().get(i));
            }
        }else{
            System.out.println("You do not have events for schedule. Don't worry.");
        }

        try {
            save();
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    //Effects: save user's information to a txt file
    private void save() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("userData.txt"));
            PrintWriter writer = new PrintWriter("userData.txt");
            if (user.getIdentity() == 1) {
                lines.add("Student\n");
            } else {
                lines.add("Worker\n");
            }

            lines.add("Get up at " + user.getGetUpTime() + ":00\n");
            lines.add("Go to bed at " + user.getSleepTime() + ":00\n");

            if (user.getJudgeE() == 1) {
                for(int i = 0; i <user.getNumEvents(); ++i) {
                    lines.add("Have special events: " + user.getEvents().get(i) +"\n");
                }
            }


            for(int i = 0; i < lines.size(); ++i) {
                writer.println(lines.get(i));
            }

            writer.close();
        }

    //EFFECTS : let user choose the schedule they like
    private Schedule chooseSchedule(User user){
        Schedule schedule;
        boolean retry;
        do {
            System.out.println("***This is the personalized schedule for you***");
            schedule = new Schedule(user,eventsManager.getEventsBuilder());
            System.out.println("Do you like this schedule?\n[1] yes [2] no");
            if(Integer.parseInt(getUserAnswer())==2)
                retry = true;
            else
                retry = false;
        }while(retry);
        return schedule;
    }

}
