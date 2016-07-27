package com.company;


import java.util.ArrayList;
import java.util.List;
/**
 * Created by mohammadreza on 7/2/2016.
 */
public class EventType implements java.io.Serializable {
    public String name;
    public double rate;
    public String dispatchType;
    public List<EventTypeAttribute> eventTypeAttributeList;
    public List<EventTypeMachine> eventTypeMachineList;
    public List<Event> instances;
        public EventType(){
            eventTypeAttributeList = new ArrayList<EventTypeAttribute>();
            eventTypeMachineList = new ArrayList<EventTypeMachine>();
            instances = new ArrayList<Event>();
        }
}