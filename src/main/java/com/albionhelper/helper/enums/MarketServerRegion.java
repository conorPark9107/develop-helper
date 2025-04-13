package com.albionhelper.helper.enums;

public enum MarketServerRegion {
    EAST("https://east.albion-online-data.com/api/v2/stats/prices/"),
    WEST("https://west.albion-online-data.com/api/v2/stats/prices/"),
    EUROPE("https://europe.albion-online-data.com/api/v2/stats/prices/");

    private final String url;

    MarketServerRegion(String url) {
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }

    public static MarketServerRegion from(String name) {
        try{
            return MarketServerRegion.valueOf(name.toUpperCase());
        }catch(Exception e){
            return EAST;
        }
    }
}
