package com.albionhelper.helper.domain.battle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Alliance implements Comparable<Alliance> {

    @JsonProperty("name")
    private String name;
    @JsonProperty("kills")
    private int kills;
    @JsonProperty("deaths")
    private int deaths;
    @JsonProperty("killFame")
    private int killFame;
    @JsonProperty("id")
    private String id;

    private int playerCount;
    private int averageIp;

    @Override
    public int compareTo(Alliance o) {
        if(o.getPlayerCount() - this.playerCount == 0){
            return o.getKills() - this.kills;
        }
        return  o.getPlayerCount() - this.playerCount;
    }
}
