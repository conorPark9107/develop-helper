package com.albionhelper.helper.domain.killboard;


import com.albionhelper.helper.domain.battle.EventPlayer;
import com.albionhelper.helper.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Victim {

    @JsonProperty("AverageItemPower")
    private int averageItemPower;

    @JsonProperty("Equipment")
    private Equipment equipment;

    // 인벤토리
    @JsonProperty("Inventory")
    private List<Inventory> inventory;

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

    @JsonProperty("DeathFame")
    private int deathFame;

    private String deathFameStr;

    public void setDeathFame(int deathFame) {
        this.deathFame = deathFame;
        this.deathFameStr = Util.getUnit(this.deathFame);
    }

    @Override
    public String toString() {
        return "Victim{" +
                "averageItemPower=" + averageItemPower +
                ", equipment=" + equipment +
                ", inventory=" + inventory +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", guildId='" + guildId + '\'' +
                ", guildName='" + guildName + '\'' +
                ", allianceName='" + allianceName + '\'' +
                ", deathFame=" + deathFame +
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
        e.setDeathFame(this.deathFame);
        e.setDamageDone(0);
        e.setSupportHealingDone(0);
        e.setInventory(this.inventory);
        return e;
    }
}
