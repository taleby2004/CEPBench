package com.company;

import java.util.ArrayList;

/**
 * Created by mohammadreza on 7/12/2016.
 */
public class EventGenerator {

    private double event_Time_Limit;
    private int event_Count_Limit;
    private NumberGenerator numberGenerator ;
    private ConfigManager configManager;
    private DispatchManager dispatchManager;
    private ArrayList<EventType> eventTypesList ;
    private ArrayList<Machine> machineList;

    public EventGenerator(){
        event_Time_Limit = 10;
        numberGenerator = new NumberGenerator();
        configManager = new ConfigManager();
        eventTypesList = new ArrayList<EventType>();
        machineList = new ArrayList<Machine>();

    }


    public void generateEvents(ArrayList<EventType> eTL){
        double CurrentTimestamp  ;
        for (EventType eT : eTL) {
            CurrentTimestamp = 0;
            while (CurrentTimestamp< event_Time_Limit){
                Event newEvent = new Event();
                newEvent.timestamp = CurrentTimestamp;
                newEvent.name = eT.name;
                //Generate Attributes
                generateAttibutes(eT,newEvent);
                eT.instances.add(newEvent);
                CurrentTimestamp += 1/eT.rate ;
            }

        }

    }

    public ArrayList<Event> generateEvents(EventType eT){
        double CurrentTimestamp  ;
        ArrayList<Event> returnList = new ArrayList<Event>();
        CurrentTimestamp = 0;
        while (CurrentTimestamp< event_Time_Limit){
            Event newEvent = new Event();
            newEvent.timestamp = CurrentTimestamp;
            newEvent.name = eT.name;
            generateAttibutes(eT,newEvent);
            returnList.add(newEvent);
            CurrentTimestamp += 1/eT.rate ;
        }
        return returnList;
    }

    public void generateAttibutes(EventType eT , Event ne){
        //Generate Attributes
        eT.eventTypeAttributeList.forEach((at)->{
            String distribution = at.distribution;
            double tempAttributeValue = numberGenerator.generate(distribution);
            Item tempItem = new Item();
            tempItem.key = at.name ;
            tempItem.value = Double.toString(tempAttributeValue) ;
            ne.items.add(tempItem);
        });
    }

    public void run(){
        configManager.readMachinesInfo(machineList);
        configManager.readEventTypes(eventTypesList);
        generateEvents(eventTypesList);
        dispatchManager = new DispatchManager(eventTypesList,machineList);
        dispatchManager.dispatch();
        machineList.get(0).serialize(configManager.getSerializer());
    }

}
