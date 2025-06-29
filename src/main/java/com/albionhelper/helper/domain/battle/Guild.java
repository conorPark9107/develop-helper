package com.albionhelper.helper.domain.battle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Guild implements Comparable<Guild> {

    @JsonProperty("name")
    private String name;
    @JsonProperty("kills")
    private int kills;
    @JsonProperty("deaths")
    private int deaths;
    @JsonProperty("killFame")
    private long killFame;
    @JsonProperty("alliance")
    private String alliance;
    @JsonProperty("allianceId")
    private String allianceId;
    @JsonProperty("id")
    private String id;

    private int playerCount;
    private int averageIp;

    @Override
    public String toString() {
        return "Guild{" +
                "name='" + name + '\'' +
                ", kills=" + kills +
                ", deaths=" + deaths +
                ", killFame=" + killFame +
                ", alliance='" + alliance + '\'' +
                ", allianceId='" + allianceId + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public int compareTo(Guild o) {
        if(o.getKills() - this.kills == 0){
            return o.getPlayerCount() - this.playerCount;
        }
        return o.getKills() - this.kills;
    }
}
