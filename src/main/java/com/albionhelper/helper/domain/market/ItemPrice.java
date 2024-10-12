package com.albionhelper.helper.domain.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ItemPrice {

    @JsonProperty("itemTypeId")
    private String itemName;

    @JsonProperty("city")
    private String city;

    @JsonProperty("qualityLevel")
    private int qualityLevel;

    @JsonProperty("sellPriceMin")
    private long sellPriceMin;

    @JsonProperty("sellPriceMinDate")
    private LocalDateTime sellPriceMinDate;

    @JsonProperty("sellPriceMax")
    private long sellPriceMax;

    @JsonProperty("sellPriceMaxDate")
    private LocalDateTime sellPriceMaxDate;

    @JsonProperty("buyPriceMin")
    private long buyPriceMin;

    @JsonProperty("buyPriceMinDate")
    private LocalDateTime buyPriceMinDate;

    @JsonProperty("buyPriceMax")
    private long buyPriceMax;

    @JsonProperty("buyPriceMaxDate")
    private LocalDateTime buyPriceMaxDate;


}
