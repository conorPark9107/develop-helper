package com.albionhelper.helper.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class Util {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static String getUnit(int number){
        double num = 0;
        String unit = "";

        if(number >= 1000000000){
            num = number / 1000000000.0;
            unit = "b";
        }else if(number >= 1000000){
            num = number / 1000000.0;
            unit = "m";
        }else if(number >= 1000){
            num = number / 1000.0;
            unit = "k";
        }
        double rounded = Math.round(num * 10) / 10.0;

        if(rounded == (int) rounded) {
            return ((int) rounded) + unit;
        }else{
            return rounded + unit;
        }
    }



}
