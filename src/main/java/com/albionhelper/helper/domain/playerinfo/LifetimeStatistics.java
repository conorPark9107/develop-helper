package com.albionhelper.helper.domain.playerinfo;

import com.albionhelper.helper.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LifetimeStatistics {

    @JsonProperty("PvE")
    private StatisticPlace pve;

    @JsonProperty("Gathering")
    private Gathering gathering;

    // 크래프팅
    @JsonProperty("Crafting")
    private StatisticPlace crafting;

    // 낚시 페임
    @JsonProperty("FishingFame")
    private long fishingFame;

    // 농사 페임
    @JsonProperty("FarmingFame")
    private long farmingFame;

    // 이 정보들이 업데이트된 시간
    @JsonProperty("Timestamp")
    private String timeStamp;

    private String fishingFameStr;
    private String farmingFameStr;

    public void setFishingFame(long fishingFame) {
        this.fishingFame = fishingFame;
        this.fishingFameStr = Util.getUnit(this.fishingFame);
    }
    public void setFarmingFame(long farmingFame) {
        this.farmingFame = farmingFame;
        this.farmingFameStr = Util.getUnit(this.farmingFame);
    }
    public void setTimeStamp(String timeStamp) {
        if(timeStamp != null){
            LocalDateTime ldt = LocalDateTime.parse(timeStamp.replaceAll("Z", ""));
            this.timeStamp = ldt.plusHours(9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }

    @Override
    public String toString() {
        return "LifetimeStatistics{" +
                "pve=" + pve +
                ", gathering=" + gathering +
                ", crafting=" + crafting +
                ", fishingFame=" + fishingFameStr +
                ", farmingFame=" + farmingFameStr +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
