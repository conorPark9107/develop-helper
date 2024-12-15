package com.albionhelper.helper.domain.battle;

import com.albionhelper.helper.domain.killboard.Killer;
import com.albionhelper.helper.domain.killboard.Victim;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {



    @JsonProperty("Killer")
    private Killer killer;

    @JsonProperty("victim")
    private Victim victim;



}
