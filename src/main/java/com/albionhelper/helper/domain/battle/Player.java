package com.albionhelper.helper.domain.battle;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {

    @JsonProperty("name")
    private String name;

    @JsonProperty("kills")
    private int kills;

    @JsonProperty("deaths")
    private int deaths;

    @JsonProperty("killFame")
    private long killFame;

    @JsonProperty("guildName")
    private String guildName;

    @JsonProperty("guildId")
    private String guildId;

    @JsonProperty("allianceName")
    private String allianceName;

    @JsonProperty("allianceId")
    private String allianceId;

    @JsonProperty("id")
    private String id;


    public EventPlayer convert(){
        EventPlayer e = new EventPlayer();
        e.setAverageItemPower(0);
        e.setEquipment(null);
        e.setName(this.name);
        e.setGuildName(this.guildName);
        e.setAllianceName(this.allianceName);
        e.setKillFame(0);
        e.setDeathFame(0);
        e.setDamageDone(0);
        e.setSupportHealingDone(0);
        return e;
    }

}
