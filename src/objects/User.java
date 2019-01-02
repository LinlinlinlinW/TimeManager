package objects;

import exceptions.ImproperInputException;
import exceptions.NotLogicalInputException;

import java.util.ArrayList;
import java.util.List;

public class User implements Observer{
    private int identity;
    private int getUpTime;
    private int sleepTime;
    private List<String> events;
    private int judgeE;
    private int numEvents;

    //constructor of class Users
    public User() {
        events = new ArrayList();
    }

    //Modifies: this
    //Effects: get the user's identity
    public void setIdentity(int identity){
        this.identity = identity;
    }

    //Modifies: this
    //Effects: get the user's identity
    public void setGetUpTime(int getUp){
        this.getUpTime =getUp;
    }

    //Modifies: this
    //Effects: get the user's identity
    public void setSleepTime(int goToBed) {
        this.sleepTime = goToBed;
    }

    //Modifies: this
    //Effects: get the user's identity
    public void setJudgeEvent(int m){
        this.judgeE = m;
    }

    //Modifies: this
    //Effects: get the number of planned events
    public void setNumOfEvents(int i){
        this.numEvents = i;
    }

    //Modifies: this
    //Effects: get the user's planned events
    public void setEvents(List<String> s){
        events.addAll(s);
    }

    //Effects: return user's identity
    public int getIdentity(){
        return this.identity;
    }

    //Effects: return user's getUpTime
    public int getGetUpTime(){return this.getUpTime;}

    //Effects: return user's sleepTime
    public int getSleepTime(){return this.sleepTime;}

    //Effects: return user's events
    public List<String> getEvents(){return this.events;}

    //Effects: return user's judge for events
    public int getJudgeE(){return this.judgeE;}

    //Effects: return the number of events
    public int getNumEvents(){
        this.numEvents = events.size();
        return this.numEvents;
    }

    @Override
    //Effects: return the state of process
    public void update(int a){
        if(a==1) {
            System.out.println("The user's state: " + State.DONE);
            System.out.println("well done");
        }
        else{
            System.out.println("The user's state: "+State.PROCESSING);
            System.out.println("That's ok");
        }
    }
}
