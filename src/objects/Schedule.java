package objects;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Schedule{
    private List<Integer> timeSet;
    private List<ScheduleEvent> events;

    private List<String> eventsBuilder;

    private List<String> timeList = new ArrayList<>();
    private List<String> scheduleEvents = new ArrayList<>();

    //constructor
    public Schedule(User user, List<String> eventsBuilder){
        this.timeSet = new ArrayList<>();
        this.events = new ArrayList<>();
        this.eventsBuilder=eventsBuilder;

        getTime(user);
        shuffle(this.eventsBuilder);
        printSchedule(this.eventsBuilder);

        setTimeList();
        setScheduleEvents();
    }
    //EFFECTS: shuffle the events list
    private void shuffle(List<String> toBeS){
        Collections.shuffle(toBeS);
    }
    //EFFECTS: calculate user's time
    private void getTime(User user){
        int time = user.getGetUpTime();
        int sleepTime = user.getSleepTime();

        while (time < sleepTime) {
            timeSet.add(time);
            time++;
        }
    }

    private void setTimeList(){
        for(int i=0;i<timeSet.size();i++){
            timeList.add(timeSet.get(i)+":00 - "+(timeSet.get(i)+1)+":00");
        }
    }

    private void setScheduleEvents(){
        for(int i=0;i<timeSet.size();i++){
            scheduleEvents.add(this.eventsBuilder.get(i));
        }
    }

    public List<String> getTimeList(){
        return timeList;
    }

    public List<String> getScheduleEvents(){
        return scheduleEvents;
    }

    //EFFECTS: print out schedule
    private void printSchedule(List<String> shuffled){
        for(int i=0;i<timeSet.size();i++){
            System.out.println(timeSet.get(i)+":00 - "+(timeSet.get(i)+1)+":00");
            System.out.println(shuffled.get(i));
        }
        System.out.println("************\n");
    }

    //EFFECTS: [reflexiveness]
    //         this function is for the time when
    //         user want to add a personal event during the process of carrying out the schedule
    public void addEvent(ScheduleEvent event){
        if(!this.events.contains(event)){
            this.events.add(event);
            event.addSchedule(this);
        }
    }
}
