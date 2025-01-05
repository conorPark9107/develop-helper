package com.albionhelper.helper.domain.battle;

import com.albionhelper.helper.util.Util;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Battle{

    @JsonProperty("id")
    private String id;

    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("endTime")
    private String endTime;

    private String utcTime;

    @JsonProperty("totalFame")
    private int totalFame;

    @JsonProperty("totalKills")
    private int totalKills;

    @JsonProperty("players")
    private List<Player> players;

    @JsonProperty("guilds")
    private List<Guild> guilds;

    @JsonProperty("alliances")
    private List<Alliance> alliances;

    private String totalFameStr;

    public void setTotalFame(int totalFame) {
        this.totalFame = totalFame;
        totalFameStr = Util.getUnit(this.totalFame);
    }

    public void setStartTime(String startTime) {
        LocalDateTime ldt = LocalDateTime.parse(startTime.replaceAll("Z", "").substring(1, 20));
        this.startTime = ldt.plusHours(9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.utcTime = ldt.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void setEndTime(String endTime) {
        LocalDateTime ldt = LocalDateTime.parse(endTime.replaceAll("Z", "").substring(1, 20));
        this.endTime = ldt.plusHours(9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return "Battle{" +
                "id='" + id + '\'' +
                ", endTime='" + endTime + '\'' +
                ", totalFame=" + totalFame +
                ", totalKills=" + totalKills +
                ", players=" + players +
                ", guilds=" + guilds +
                ", alliances=" + alliances +
                '}';
    }


}
