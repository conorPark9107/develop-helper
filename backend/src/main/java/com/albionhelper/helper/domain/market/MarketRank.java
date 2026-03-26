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

    public MarketRankDTO toDto(){
        return MarketRankDTO.builder()
                .itemId(this.itemId)
                .itemName(this.itemName)
                .count(this.count)
                .build();
    }


}
