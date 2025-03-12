package com.albionhelper.helper.domain.metaBuild;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "tierListComment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TierListComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tier_list_id", nullable = false)
    private TierList tierList;

    @Column(name = "userId", nullable = false)
    private String userId;

    @Column(name = "comment", nullable = false, length = 1000)
    private String comment;

    @Column(name = "writeDate", nullable = false)
    private LocalDateTime writeDate;
}
