package com.albionhelper.helper.domain.market;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MarketRankDTO {

    private Long id;
    private String itemId;
    private String itemName;
    private Long count;



}
