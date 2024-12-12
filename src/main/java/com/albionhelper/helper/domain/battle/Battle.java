package com.albionhelper.helper.domain.battle;

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
public class Battle {

    @JsonProperty("id")
    private String id;

    @JsonProperty("endTime")
    private String endTime;

    private String utcTime;

    @JsonProperty("totalFame")
    private String totalFame;

    @JsonProperty("totalKills")
    private String totalKills;

    @JsonProperty("players")
    private List<Player> players;

    @JsonProperty("guilds")
    private List<Guild> guilds;

    @JsonProperty("alliances")
    private List<Alliance> alliances;

    public void setEndTime(String endTime) {
        LocalDateTime ldt = LocalDateTime.parse(endTime.replaceAll("Z", "").substring(1, 20));
        this.endTime = ldt.plusHours(9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.utcTime = ldt.format(DateTimeFormatter.ofPattern("HH:mm"));
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
