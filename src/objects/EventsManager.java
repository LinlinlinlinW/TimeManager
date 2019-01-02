package objects;

import java.util.*;

public class EventsManager {

    //to store selected events(if user don't have known events)
    //or to store known events(input from user)
    private List<String> eventsBuilder;
    //store eventIndex and string of events database
    private Map<Integer, ScheduleEvent> eventsDB;
    private List<String> eventContent;
    private int eventIndex;

    //constructor
    public EventsManager(){
        eventContent = new ArrayList<>();
        eventIndex = 0;
        eventsBuilder = new ArrayList<>();
        eventsDB = new HashMap<>();
        eventsDB();
    }

    //EFFECTS: store all the events ,each is with the state PROCESSING
    private void eventsDB(){
        eventContent.add("do cleaning");
        eventContent.add("do gardening");
        eventContent.add("shopping");
        eventContent.add("playing chess");
        eventContent.add("swimming");
        eventContent.add("go to a concert");
        eventContent.add("cooking");
        eventContent.add("watching a movie");
        eventContent.add("learning jazz dancing");
        eventContent.add("read a chapter of a novel");
        eventContent.add("visiting a friends");
        eventContent.add("hike");
        eventContent.add("go to a party");
        eventContent.add("watching TED talk");
        eventContent.add("baking");
        eventContent.add("do assignments");
        eventContent.add("take a break");
        eventContent.add("eat something");
        eventContent.add("fishing");

        for(eventIndex=0;eventIndex<eventContent.size();eventIndex++)
            eventsDB.put(eventIndex,new ScheduleEvent(eventContent.get(eventIndex)));
    }

    //EFFECTS: get random number ranging from 0 to 12 and select corresponding events with that value
    private String getOneEventsFromDB(){
        int j = (int)(Math.random() * (eventContent.size()));
        return eventsDB.get(j).getStringOfEvent();
    }

    //MODIFIES: this
    //EFFECTS: system generate events for them based on timeSlot
    //         timeUnit = user's sleepTime-getUpTime
    public void generateEvents(int timeUnit,int identity){
        for(int j=0;j<timeUnit-1;) {
            String event = getOneEventsFromDB();
            if(!this.eventsBuilder.contains(event)) {
                this.eventsBuilder.add(event);
                j++;
            }
        }
        addLastEvent(identity);
    }

    //MODIFIES: this
    //EFFECTS: pass in events list(from user's input) and initialize eventsBuilder
    //         timeUnit = sleepTime-getUpTime
    public void initializeEvents(List<String> eventsList,int timeUnit,int identity){
        if(eventsList.size()<timeUnit-1) {
            this.eventsBuilder.addAll(eventsList);

            for(int k=eventsList.size(); k<timeUnit-1;){
                String x = getOneEventsFromDB();
                if(!this.eventsBuilder.contains(x)) {
                    this.eventsBuilder.add(x);
                    k++;
                }
            }

        }else if(eventsList.size()==timeUnit-1){
            this.eventsBuilder.addAll(eventsList);
        }
        addLastEvent(identity);
    }

    //Effects: add 'exercise' event to the list of eventsBuilder
    private void addLastEvent(int identity){
        if(identity==1){
            this.eventsBuilder.add("exercise 50min & stretch 10min");
        }
        else{
            this.eventsBuilder.add("exercise 40min & stretch 20min");
        }
    }

    //EFFECTS: get eventsBuilder
    public List<String> getEventsBuilder(){
        return this.eventsBuilder;
    }

    //Modifies: this
    //Effects: if user's input doesn't have conflict with existed events in database, then add it to database
    public void addEventsToDB(String s){
        ScheduleEvent newEvent = new ScheduleEvent(s);
        if(!eventsDB.containsValue(newEvent)) {
            eventsDB.put(eventIndex++, newEvent);
            System.out.println("successfully add event "+s+" to the database");
        }
        else{
            System.out.println("did not successfully add event "+s+" to the database");
        }
    }

    //MODIFIES: this
    //EFFECTS: once a piece of event is finished, delete it from the eventsBuilder
    public void deleteEvent(ScheduleEvent event){
        eventsBuilder.remove(event);
    }
}
