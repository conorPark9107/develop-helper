package com.albionhelper.helper.domain.killboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Inventory {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Count")
    private String count;

    @JsonProperty("Quality")
    private String quality;
}
