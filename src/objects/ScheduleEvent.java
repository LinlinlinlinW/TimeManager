package objects;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ScheduleEvent {
    private State state;
    private String event;
    private Set<Schedule> schedules=new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleEvent event1 = (ScheduleEvent) o;//casting

        return  Objects.equals(event, event1.event);
    }

    @Override
    public int hashCode() {
        String s = event.trim();
        int i=Integer.valueOf(s);
        return i;
    }

    //constructor
    //EFFECTS: every event is initialized, set its state and content
    public ScheduleEvent(String event){
        this.event = event;
        state = State.PROCESSING;
    }

    //EFFECTS: To get the content of the event
    public String getStringOfEvent(){
        return this.event;
    }

    //EFFECTS: [reflexiveness] To store which schedule is this event in
    public void addSchedule(Schedule schedule){
        if(!this.schedules.contains(schedule)){
            this.schedules.add(schedule);
            schedule.addEvent(this);
        }
    }

    //EFFECTS: once the event is finished, turn the state
    public void doneEvent(){
        this.state = State.DONE;
    }

}
