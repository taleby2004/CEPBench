package com.company;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;
import java.util.Map;

/**
 * Created by mohammadreza on 7/13/2016.
 */
public class Machine {
    public String name;
    public String ip;
    public int port;
    String eventFiles;
    ArrayList<Event> eventQueue;
    String serializer;
    public Machine(){
        eventQueue = new ArrayList<Event>();
    }
    public void serialize(String serializerType){
        serializer = serializerType;
        if(eventFiles.equals("normal")){
            serilizeToSingleFile();
        }
        else if (eventFiles.equals("split")){
            serializeToMultiFiles();
        }
    }

    private void serilizeToSingleFile(){
        saveEventListToFile(name,eventQueue);
    }
    private void serializeToMultiFiles(){
        HeaderManager h = new HeaderManager();

        HashMap<String,ArrayList<Event>> fileList = new HashMap<>();
        byte[] events = h.generateHeader();
        for (Event e : eventQueue) {
            if(fileList.containsKey(e.name)){
                fileList.get(e.name).add(e);
            }
            else{
                ArrayList<Event> tmpArrayList = new ArrayList<>();
                tmpArrayList.add(e);
                fileList.put(e.name,tmpArrayList);
            }
        }

        for(HashMap.Entry<String ,ArrayList<Event>> entry: fileList.entrySet()){
            saveEventListToFile(name+"_"+entry.getKey(),entry.getValue());
        }

    }
    private void saveEventListToFile(String destionationFile , ArrayList<Event> eventArray){

        HeaderManager h = new HeaderManager();
        byte[] events = h.generateHeader();
        int sequenceId = 0 ;
        for (Event e : eventArray) {
            try {
                byte cc = 0x01;
                byte aa = 0x10;
                int eLenght ;
                byte[] eventAsBytes = {};
                if (serializer.equals("java")) {
                    eventAsBytes = serializeEventJava(e);
                }
                else if (serializer.equals("protobuf")){
                    eventAsBytes = serializeEventProtoBuf(e);
                }
                eLenght = eventAsBytes.length;
                ByteBuffer bb =  ByteBuffer.allocate(eLenght+4+1+1+8) ;
                bb.putInt(eLenght);
                bb.put(cc);
                bb.put(aa);
                bb.putDouble(e.timestamp);
                bb.put(eventAsBytes);

                events = mergeByteArrays(events,bb.array());
            }
            catch(Exception i) {
                i.printStackTrace();
            }
        }
        try {

            FileOutputStream fo = new FileOutputStream(destionationFile);
            fo.write(events);
            fo.close();
        }
        catch (Exception e){
            //TODO
        }
    }
    private byte[] serializeEventProtoBuf(Event e ){
        byte[] returnEventArray ;
        Pevent.event.Builder tempPevent = Pevent.event.newBuilder();
        tempPevent.setName(e.name);
        tempPevent.setTimestamp(e.timestamp);
        for(Item it : e.items){
            Pevent.item.Builder tempItem = Pevent.item.newBuilder();
            tempItem.setKey(it.key);
            tempItem.setValue(it.value);
            tempPevent.addItems(tempItem.build());
        }
        returnEventArray = tempPevent.build().toByteArray();
        return returnEventArray;
    }
    private byte[] serializeEventJava(Event e ){
        byte[] returnEventArray = {};
        try {
            byte[] tmpByteArray ;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(e);
            tmpByteArray = bos.toByteArray();
            out.close();
            bos.close();
            returnEventArray = tmpByteArray;
        }
        catch (Exception ie)
        {
        }
        return returnEventArray;
    }
    private byte[] mergeByteArrays(byte[] one , byte[] two){
        byte[] combined = new byte[one.length + two.length];

        System.arraycopy(one,0,combined,0         ,one.length);
        System.arraycopy(two,0,combined,one.length,two.length);
        return combined;
    }
}
