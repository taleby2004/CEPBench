package com.company;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by mohammadreza on 7/20/2016.
 */
public class eventReader {

    public static void main(String[] args){
        ArrayList<Event> gatheredEvents = new ArrayList<Event>();
        try{
            //FileInputStream fi = new FileInputStream("testy.hex");
            byte[] fileAsBytes = Files.readAllBytes(Paths.get("m1"));
            HeaderManager h = new HeaderManager();
            int h_lenght = 4;
            byte[] header = new byte[h_lenght];

            ByteBuffer bb = ByteBuffer.wrap(fileAsBytes,0,h_lenght);

            for (int i = 0 ; i <h_lenght ; i++){
                header[i] = bb.get();
            }
            h.readHeader(header);
            ByteBuffer events = ByteBuffer.wrap(fileAsBytes,h_lenght,fileAsBytes.length-h_lenght);
            //events.flip();
            System.out.println(events.remaining());
            System.out.println(fileAsBytes.length);

            while (events.hasRemaining()){
                int lenght = events.getInt();
                byte cc = events.get();
                byte aa = events.get();
                double timestamp = events.getDouble();

                byte[] currentEvent = new byte[lenght];
                Event newEvent ;
                for (int i = 0; i < lenght; i++) {
                    currentEvent[i] = events.get();
                }
                if(false) {
                    try {
                        ByteArrayInputStream bis = new ByteArrayInputStream(currentEvent);
                        ObjectInput in = new ObjectInputStream(bis);
                        newEvent = (Event) in.readObject();
                        gatheredEvents.add(newEvent);
                    }
                    catch (Exception e){

                    }

                }
                else {
                    try {
                        System.out.print("d");
                    Pevent.event tempPEvent = Pevent.event.parseFrom(currentEvent);
                    newEvent = new Event();
                    newEvent.name = tempPEvent.getName();
                    newEvent.timestamp = tempPEvent.getTimestamp();
                    for (Pevent.item pit : tempPEvent.getItemsList()) {
                        Item tempItem = new Item();
                        tempItem.key = pit.getKey();
                        tempItem.value = pit.getValue();
                        newEvent.items.add(tempItem);
                        }
                        gatheredEvents.add(newEvent);
                    }

                    catch (Exception e){

                    }
                }


            }

        System.out.print("done");

        }
        catch(Exception e){
            System.out.print("errror IO");
        }

    }



}
