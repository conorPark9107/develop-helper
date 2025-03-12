package com.albionhelper.helper.domain.metaBuild;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tierList")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TierList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userId")
    private String userId;
    @Column(name = "title")
    private String title;
    @Column(name = "writeDate")
    private LocalDateTime writeDate;
    @Column(name = "category")
    private String category;
    @Column(name = "t1")
    private String t1;
    @Column(name = "t2")
    private String t2;
    @Column(name = "t3")
    private String t3;
    @Column(name = "t4")
    private String t4;
    @Column(name = "t5")
    private String t5;
    @Column(name = "t6")
    private String t6;
    @Column(name = "t7")
    private String t7;
    @Column(name = "t8")
    private String t8;
    @Column(name = "t9")
    private String t9;
    @Column(name = "t10")
    private String t10;

    @Column(name = "up")
    private Long up;

    @OneToMany(mappedBy = "tierList", cascade = CascadeType.ALL)
    private List<TierListComment> tierListComments;

    @PrePersist
    public void prePersist() {
        if (writeDate == null) {
            writeDate = LocalDateTime.now();  // 기본값을 현재 시간으로 설정
        }
        if (up == null) {
            up = 0L;  // 기본값을 0으로 설정
        }
    }

    // DTO -> Entity
    public TierList toEntity(TierListDTO dto){
        return TierList.builder()
                .userId(dto.getUserId())
                .title(dto.getTitle())
                .category(dto.getCategory())
                .t1(dto.getT1())
                .t2(dto.getT2())
                .t3(dto.getT3())
                .t4(dto.getT4())
                .t5(dto.getT5())
                .t6(dto.getT6())
                .t7(dto.getT7())
                .t8(dto.getT8())
                .t9(dto.getT9())
                .t10(dto.getT10())
                .up(dto.getUp())
                .build();
    }

    public TierListDTO toDTO(){
        return TierListDTO.builder()
                .userId(this.userId)
                .title(this.title)
                .writeDate(this.writeDate)
                .category(this.category)
                .t1(this.t1)
                .t2(this.t2)
                .t3(this.t3)
                .t4(this.t4)
                .t5(this.t5)
                .t6(this.t6)
                .t7(this.t7)
                .t8(this.t8)
                .t9(this.t9)
                .t10(this.t10)
                .up(this.up)
                .build();
    }


}
