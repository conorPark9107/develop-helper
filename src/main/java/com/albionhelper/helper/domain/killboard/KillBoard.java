package com.albionhelper.helper.domain.killboard;


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
public class KillBoard {

    // 이벤트 넘버와 이벤트 발생일
    @JsonProperty("EventId")
    private long eventId;
    @JsonProperty("TimeStamp")
    private String timeStamp;
    
    // 데스 페임.
    @JsonProperty("TotalVictimKillFame")
    private String totalVictimKillFame;


    // 어시스트.
    @JsonProperty("numberOfParticipants")
    private int numberOfParticipants;

    // 죽인사람.
    @JsonProperty("Killer")
    private Killer killer;

    // 죽은사람.
    @JsonProperty("Victim")
    private Victim victim;

    // 어시스트
    @JsonProperty("Participants")
    private List<Participants> participants;

    public void setTimeStamp(String timeStamp) {
        LocalDateTime ldt = LocalDateTime.parse(timeStamp.replaceAll("Z", ""));
        this.timeStamp = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
