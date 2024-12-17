package com.albionhelper.helper.domain.battle;

import com.albionhelper.helper.domain.killboard.Killer;
import com.albionhelper.helper.domain.killboard.Victim;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {



    @JsonProperty("Killer")
    private Killer killer;

    @JsonProperty("Victim")
    private Victim victim;

    @JsonProperty("Participants")
    private List<EventPlayer> participants;

    @JsonProperty("GroupMembers")
    private List<EventPlayer> groupMembers;

    @Override
    public String toString() {
        return "Event{" +
                "killer=" + killer +
                ", victim=" + victim +
                '}';
    }
}
