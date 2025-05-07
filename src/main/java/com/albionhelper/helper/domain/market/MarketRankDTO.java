package com.albionhelper.helper.domain.market;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MarketRankDTO {

    private String itemId;
    private String itemName;
    private Long count;

    public MarketRank toEntity(){
        return MarketRank.builder()
                .itemId(this.getItemId())
                .itemName(this.getItemName())
                .count(this.getCount())
                .build();
    }

}
