package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.crypto.Mac;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohammadreza on 7/12/2016.
 */
public class ConfigManager {


    private DocumentBuilderFactory factory ;
    private DocumentBuilder builder ;
    private Document document;
    private String version;
    private String isTimestamp;
    private String serializer;

    public String getVersion(){
        return version;
    }

    public String getIsTimestamp() {
        return isTimestamp;
    }

    public String getSerializer() {
        return serializer;
    }
    public ConfigManager(){
        try {
            //eventTypeList = new ArrayList<EventType>();
            //machineList = new ArrayList<Machine>();
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    public void readEventTypes(ArrayList<EventType> eventTypeList){
            try {


                //Build Document
                document = builder.parse(new File("C:\\Users\\mohammadreza\\IdeaProjects\\cepGen\\src\\config.xml"));
                //Normalize the XML Structure; It's just too important !!
                document.getDocumentElement().normalize();

                NodeList nList = document.getElementsByTagName("eventtype");
                isTimestamp = document.getElementsByTagName("istimestamp").item(0).getTextContent().toString();
                version = document.getElementsByTagName("version").item(0).getTextContent().toString();
                serializer = document.getElementsByTagName("serializer").item(0).getTextContent().toString();
                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node node = nList.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) node;

                        EventType tempEventType = new EventType();
                        tempEventType.name = eElement.getElementsByTagName("name").item(0).getTextContent().toString();
                        tempEventType.rate = Double.parseDouble(eElement.getElementsByTagName("rate").item(0).getTextContent());
                        tempEventType.dispatchType = eElement.getElementsByTagName("dispatchtype").item(0).getTextContent().toString();


                        NodeList aList = eElement.getElementsByTagName("attribute");
                        for (int temp2 = 0; temp2 < aList.getLength(); temp2++) {
                            Node node2 = aList.item(temp2);
                            if (node2.getNodeType() == Node.ELEMENT_NODE) {
                                Element aElement = (Element) node2;
                                EventTypeAttribute tempEventTypeAttribute = new EventTypeAttribute(aElement.getElementsByTagName("name").item(0).getTextContent().toString(), aElement.getElementsByTagName("distribution").item(0).getTextContent().toString());
                                tempEventType.eventTypeAttributeList.add(tempEventTypeAttribute);
                            }
                        }


                        NodeList bList = eElement.getElementsByTagName("machine");
                        for (int temp3 = 0; temp3 < bList.getLength(); temp3++) {
                            Node node3 = bList.item(temp3);
                            if (node3.getNodeType() == Node.ELEMENT_NODE) {
                                Element bElement = (Element) node3;
                                EventTypeMachine tempEventTypeMachine = new EventTypeMachine();
                                tempEventTypeMachine.name = bElement.getElementsByTagName("name").item(0).getTextContent().toString();
                                tempEventTypeMachine.percentage = Double.parseDouble(bElement.getElementsByTagName("percentage").item(0).getTextContent().toString());
                                tempEventType.eventTypeMachineList.add(tempEventTypeMachine);
                            }
                        }

                        eventTypeList.add(tempEventType);


                    }
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }


    }
    public void readMachinesInfo(ArrayList<Machine> machineList){
        try {
            document = builder.parse(new File("C:\\Users\\mohammadreza\\IdeaProjects\\cepGen\\src\\machineinfo.xml"));
            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("machine");
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node node = nList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element mElement = (Element) node;
                    Machine tempMachine = new Machine();
                    tempMachine.name = mElement.getElementsByTagName("name").item(0).getTextContent().toString();
                    tempMachine.ip = mElement.getElementsByTagName("ip").item(0).getTextContent().toString();
                    tempMachine.port= Integer.parseInt( mElement.getElementsByTagName("port").item(0).getTextContent().toString());
                    tempMachine.eventFiles= mElement.getElementsByTagName("eventfiles").item(0).getTextContent().toString();;
                    machineList.add(tempMachine);
                }

            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}
