package com.company;


public class Main {


    public static void main(String[] args) {
        // write your code here

        EventGenerator eg = new EventGenerator();
        eg.run();
        HeaderManager h = new HeaderManager();
        h.generateHeader();

        System.out.print("jj");

    }
}
