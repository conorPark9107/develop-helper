package com.albionhelper.helper.domain.killboard;

import com.albionhelper.helper.domain.battle.EventPlayer;
import com.albionhelper.helper.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Killer {

    @JsonProperty("AverageItemPower")
    private int averageItemPower;

    @JsonProperty("Equipment")
    private Equipment equipment;


    // 인게임 id
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;

    // 길드
    @JsonProperty("GuildId")
    private String guildId;
    @JsonProperty("GuildName")
    private String guildName;

    // 동맹
    @JsonProperty("AllianceName")
    private String allianceName;

    // 킬페임
    @JsonProperty("KillFame")
    private int killFame;

    private String killFameStr;

    public void setKillFame(int killFame) {
        this.killFame = killFame;
        this.killFameStr = Util.getUnit(this.killFame);
    }

    @Override
    public String toString() {
        return "Killer{" +
                "averageItemPower=" + averageItemPower +
                ", equipment=" + equipment +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", guildId='" + guildId + '\'' +
                ", guildName='" + guildName + '\'' +
                ", allianceName='" + allianceName + '\'' +
                '}';
    }

    public EventPlayer convert(){
        EventPlayer e = new EventPlayer();
        e.setAverageItemPower(this.averageItemPower);
        e.setEquipment(this.equipment);
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
