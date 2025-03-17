package com.albionhelper.helper.domain.metaBuild;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
                .id(dto.getId())
                .userId(dto.getUserId())
                .title(dto.getTitle())
                .category(dto.getCategory())
                .t1(String.join(" ", dto.getT1()))
                .t2(String.join(" ", dto.getT2()))
                .t3(String.join(" ", dto.getT3()))
                .t4(String.join(" ", dto.getT4()))
                .t5(String.join(" ", dto.getT5()))
                .t6(String.join(" ", dto.getT6()))
                .t7(String.join(" ", dto.getT7()))
                .t8(String.join(" ", dto.getT8()))
                .t9(String.join(" ", dto.getT9()))
                .t10(String.join(" ", dto.getT10()))
                .up(dto.getUp())
                .tierListComments(dto.getTierListComments())
                .build();
    }

    public TierListDTO toDTO(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = this.writeDate.format(formatter);
        LocalDateTime parse = LocalDateTime.parse(formattedDate, formatter);
        return TierListDTO.builder()
                .id(id)
                .userId(this.userId)
                .title(this.title)
                .writeDate(parse)
                .category(this.category)
                .t1(this.t1.split(" "))
                .t2(this.t2.split(" "))
                .t3(this.t3.split(" "))
                .t4(this.t4.split(" "))
                .t5(this.t5.split(" "))
                .t6(this.t6.split(" "))
                .t7(this.t7.split(" "))
                .t8(this.t8.split(" "))
                .t9(this.t9.split(" "))
                .t10(this.t10.split(" "))
                .up(this.up)
                .tierListComments(this.tierListComments)
                .build();
    }


}
