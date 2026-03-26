package com.albionhelper.helper.domain.market;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "itemLog")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "itemName")
    private String itemName;

    @Column(name = "searchDate")
    private LocalDateTime searchDate;

    @PrePersist
    public void prePersist(){
        if(searchDate == null){
            searchDate = LocalDateTime.now();
        }
    }
}
