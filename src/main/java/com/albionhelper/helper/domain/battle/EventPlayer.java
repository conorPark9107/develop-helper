package com.albionhelper.helper.domain.battle;

import com.albionhelper.helper.domain.killboard.Equipment;
import com.albionhelper.helper.domain.killboard.Inventory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventPlayer implements Comparator<EventPlayer> {

    @JsonProperty("AverageItemPower")
    private int averageItemPower;

    private int kills;
    private int deaths;

    @JsonProperty("Equipment")
    private Equipment equipment;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("GuildName")
    private String guildName;

    @JsonProperty("AllianceName")
    private String allianceName;

    @JsonProperty("KillFame")
    private long killFame;

    @JsonProperty("DeathFame")
    private long deathFame;

    @JsonProperty("DamageDone")
    private int damageDone;

    @JsonProperty("SupportHealingDone")
    private int supportHealingDone;

    private List<Inventory> inventory;

    @Override
    public String toString() {
        return "EventPlayer{" +
                "averageItemPower=" + averageItemPower +
                ", kills=" + kills +
                ", deaths=" + deaths +
                ", equipment=" + equipment +
                ", name='" + name + '\'' +
                ", guildName='" + guildName + '\'' +
                ", allianceName='" + allianceName + '\'' +
                ", killFame=" + killFame +
                ", deathFame=" + deathFame +
                ", damageDone=" + damageDone +
                ", supportHealingDone=" + supportHealingDone +
                ", inventory=" + inventory +
                '}';
    }

    @Override
    public int compare(EventPlayer o1, EventPlayer o2) {
        return o2.getAverageItemPower() - o1.getAverageItemPower();
    }
}
