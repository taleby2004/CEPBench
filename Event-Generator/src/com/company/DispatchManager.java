package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by mohammadreza on 7/15/2016.
 */
public class DispatchManager {
    ArrayList<EventType> eventTypeList;
    ArrayList<Machine> machineList;

    public DispatchManager(ArrayList<EventType> etl , ArrayList<Machine> ml){
        eventTypeList = etl;
        machineList = ml;
    }

    public void dispatch(){
        for (EventType et: eventTypeList) {
            switch (et.dispatchType) {
                case "normal":
                    normal(et);
                case "RR":
                    roundRobin(et);
                case "percentage":
                    byPercentage(et);

            }
        }
        machinesSortQueueByTimestamp(machineList);

    }
    public void normal(EventType eventType){

            for (EventTypeMachine etm : eventType.eventTypeMachineList) {
                for (Machine m : machineList) {
                    if (m.name.equals(etm.name) ) {
                        m.eventQueue.addAll(eventType.instances);
                    }
                }
            }

    }

    public void roundRobin(EventType eventType){

            ArrayList<Machine> destMachines = new ArrayList<Machine>();
            for (EventTypeMachine etm : eventType.eventTypeMachineList){
                for (Machine m : machineList) {
                    if (m.name.equals( etm.name) ) {
                        destMachines.add(m);
                    }
                }
            }
            int i = 0 ;
            for(Event e: eventType.instances){
                    destMachines.get(i%destMachines.size()).eventQueue.add(e);
                i++;
                }

    }

    public void byPercentage(EventType et) {
        ArrayList<Integer> numberOfAddedEvents = new ArrayList<Integer>();
        ArrayList<Integer> finalNumberOfEvents = new ArrayList<Integer>();
        ArrayList<String> currentState = new ArrayList<String>();
        currentState.clear();
        numberOfAddedEvents.clear();
        finalNumberOfEvents.clear();
        ArrayList<Machine> destMachines = new ArrayList<Machine>();

        for (EventTypeMachine etm : et.eventTypeMachineList) {
            for (Machine m : machineList) {
                if (m.name.equals(etm.name)) {
                    destMachines.add(m);
                    numberOfAddedEvents.add(0);
                    finalNumberOfEvents.add((int) (et.instances.size() * etm.percentage / 100));
                    if (etm.percentage != 0 ){
                    currentState.add("ready");}
                    else{
                        currentState.add("full");
                    }

                }
            }
        }


        int i = 0;
        int ci = 0;
        for (Event e : et.instances) {
            int busyMachines = 0;
            while (currentState.get(ci) != "ready")
            {
                i++;
                ci = i % destMachines.size();
                busyMachines++;
            }
            destMachines.get(ci).eventQueue.add(e);
            numberOfAddedEvents.set(ci, numberOfAddedEvents.get(ci) + 1);
            if (numberOfAddedEvents.get(ci) >= finalNumberOfEvents.get(ci)) {
                currentState.set(ci, "full");
            }
            i++;
            ci = i % destMachines.size();
        }
    }




    private void machinesSortQueueByTimestamp(ArrayList<Machine> ml){
        for (Machine m:ml) {
            Collections.sort(m.eventQueue, new Comparator<Event>() {
                @Override
                public int compare(Event o1, Event o2) {
                    return Double.compare(o1.timestamp,o2.timestamp);
                }
            });
        }
    }



}
