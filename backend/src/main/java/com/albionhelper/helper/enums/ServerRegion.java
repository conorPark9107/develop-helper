package com.albionhelper.helper.enums;

public enum ServerRegion {
    EAST("https://gameinfo-sgp.albiononline.com/api/gameinfo"),
    WEST("https://gameinfo.albiononline.com/api/gameinfo"),
    EUROPE("https://gameinfo-ams.albiononline.com/api/gameinfo"),
    EAST_GOLD("https://east.albion-online-data.com/api/v2/stats/gold?"),
    WEST_GOLD("https://west.albion-online-data.com/api/v2/stats/gold?"),
    EUROPE_GOLD("https://europe.albion-online-data.com/api/v2/stats//gold?");

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
