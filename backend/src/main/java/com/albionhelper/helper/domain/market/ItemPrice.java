package com.albionhelper.helper.domain.market;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @JsonProperty("sell_price_min_date")
    private String sellPriceMinDate;

    @JsonProperty("sell_price_max")
    private long sellPriceMax;

//    @JsonProperty("sell_price_max_date")
//    private LocalDateTime sellPriceMaxDate;

    @JsonProperty("buy_price_min")
    private long buyPriceMin;

//    @JsonProperty("buy_price_min_date")
//    private LocalDateTime buyPriceMinDate;

    @JsonProperty("buy_price_max")
    private long buyPriceMax;

    @JsonProperty("buy_price_max_date")
    private String buyPriceMaxDate;

    private String kst;

    public void setSellPriceMinDate(String sellPriceMinDate) {
        LocalDateTime ldt = LocalDateTime.parse(sellPriceMinDate);
        this.sellPriceMinDate = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.kst = ldt.plusHours(9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setBuyPriceMaxDate(String buyPriceMaxDate) {
        LocalDateTime ldt = LocalDateTime.parse(buyPriceMaxDate);
        this.buyPriceMaxDate = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setCity(String city) {
        this.city = city.replaceAll(" ", "").toLowerCase(Locale.ROOT);
    }
}
