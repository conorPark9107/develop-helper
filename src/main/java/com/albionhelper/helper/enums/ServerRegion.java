package com.albionhelper.helper.enums;

import java.util.Locale;

public enum ServerRegion {
    EAST("https://gameinfo-sgp.albiononline.com/api/gameinfo"),
    WEST("https://gameinfo.albiononline.com/api/gameinfo"),
    EUROPE("https://gameinfo-ams.albiononline.com/api/gameinfo");

    private final String url;

    ServerRegion(String url) {
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }

    public static ServerRegion from(String serverName){
        try{
            return ServerRegion.valueOf(serverName.toUpperCase());
        }catch(Exception e){
            return EAST;
        }
    }



}
