package com.albionhelper.helper.domain.market;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MarketRank")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String itemId;

    @Column
    private String itemName;

    @Column(name = "count")
    private Long count;

    @Column(name = "searchDate")
    private LocalDateTime searchDate;

    @PrePersist
    public void prePersist(){
        if(searchDate == null){
            searchDate = LocalDateTime.now();
        }
        if(count == null){
            count = 1L;
        }
    }

    public MarketRank toEntity(MarketRankDTO dto){
        return MarketRank.builder()
                .itemId(dto.getItemId())
                .itemName(dto.getItemName())
                .count(dto.getCount())
                .build();
    }


}
