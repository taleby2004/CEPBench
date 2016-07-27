package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohammadreza on 7/12/2016.
 */
public class Event implements Serializable{
    public String name;
    public double timestamp;
    public List<Item> items;
    public Event(){
        items = new ArrayList<Item>();
    }
}
