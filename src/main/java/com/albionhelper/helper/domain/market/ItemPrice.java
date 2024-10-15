package com.albionhelper.helper.domain.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemPrice {

    @JsonProperty("item_id")
    private String itemName;

    @JsonProperty("city")
    private String city;

    @JsonProperty("quality")
    private int quality;

    @JsonProperty("sell_price_min")
    private long sellPriceMin;

//    @JsonProperty("sellPriceMinDate")
//    private LocalDateTime sellPriceMinDate;

    @JsonProperty("sell_price_max")
    private long sellPriceMax;

//    @JsonProperty("sellPriceMaxDate")
//    private LocalDateTime sellPriceMaxDate;

    @JsonProperty("buy_price_min")
    private long buyPriceMin;

//    @JsonProperty("buyPriceMinDate")
//    private LocalDateTime buyPriceMinDate;

    @JsonProperty("buy_price_max")
    private long buyPriceMax;

//    @JsonProperty("buyPriceMaxDate")
//    private LocalDateTime buyPriceMaxDate;


    public void setCity(String city) {
        this.city = city.replaceAll(" ", "").toLowerCase(Locale.ROOT);
    }
}
