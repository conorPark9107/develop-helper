package com.albionhelper.helper.domain.battle;

import com.albionhelper.helper.domain.killboard.Killer;
import com.albionhelper.helper.domain.killboard.Victim;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    @JsonProperty("EventId")
    private long eventId;

    @JsonProperty("TimeStamp")
    private String timeStamp;

    @JsonProperty("Killer")
    private Killer killer;

    @JsonProperty("Victim")
    private Victim victim;

    @JsonProperty("Participants")
    private List<EventPlayer> participants;

    @JsonProperty("GroupMembers")
    private List<EventPlayer> groupMembers;

    @JsonProperty("BattleId")
    private String battleId;

    public void setTimeStamp(String timeStamp) {
        LocalDateTime ldt = LocalDateTime.parse(timeStamp.replaceAll("Z", "").substring(0, 19));
        this.timeStamp = ldt.plusHours(9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return "Event{" +
                "killer=" + killer +
                ", victim=" + victim +
                '}';
    }
}
