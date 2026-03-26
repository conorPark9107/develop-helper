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
    @Column(name = "t1Name")
    private String t1Name;
    @Column(name = "t1")
    private String t1;
    @Column(name = "t2Name")
    private String t2Name;
    @Column(name = "t2")
    private String t2;
    @Column(name = "t3Name")
    private String t3Name;
    @Column(name = "t3")
    private String t3;
    @Column(name = "t4Name")
    private String t4Name;
    @Column(name = "t4")
    private String t4;
    @Column(name = "t5Name")
    private String t5Name;
    @Column(name = "t5")
    private String t5;
    @Column(name = "t6Name")
    private String t6Name;
    @Column(name = "t6")
    private String t6;
    @Column(name = "t7Name")
    private String t7Name;
    @Column(name = "t7")
    private String t7;
    @Column(name = "t8Name")
    private String t8Name;
    @Column(name = "t8")
    private String t8;
    @Column(name = "t9Name")
    private String t9Name;
    @Column(name = "t9")
    private String t9;
    @Column(name = "t10Name")
    private String t10Name;
    @Column(name = "t10")
    private String t10;

    @Column(name = "content", columnDefinition = "longtext")
    private String content;


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
                .t1Name(dto.getT1Name())
                .t2Name(dto.getT2Name())
                .t3Name(dto.getT3Name())
                .t4Name(dto.getT4Name())
                .t5Name(dto.getT5Name())
                .t6Name(dto.getT6Name())
                .t7Name(dto.getT7Name())
                .t8Name(dto.getT8Name())
                .t9Name(dto.getT9Name())
                .t10Name(dto.getT10Name())
                .t1(arrayToString(dto.getT1()))
                .t2(arrayToString(dto.getT2()))
                .t3(arrayToString(dto.getT3()))
                .t4(arrayToString(dto.getT4()))
                .t5(arrayToString(dto.getT5()))
                .t6(arrayToString(dto.getT6()))
                .t7(arrayToString(dto.getT7()))
                .t8(arrayToString(dto.getT8()))
                .t9(arrayToString(dto.getT9()))
                .t10(arrayToString(dto.getT10()))
                .content(dto.getContent())
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
                .t1Name(this.t1Name)
                .t2Name(this.t2Name)
                .t3Name(this.t3Name)
                .t4Name(this.t4Name)
                .t5Name(this.t5Name)
                .t6Name(this.t6Name)
                .t7Name(this.t7Name)
                .t8Name(this.t8Name)
                .t9Name(this.t9Name)
                .t10Name(this.t10Name)
                .t1(stringToArray(this.t1))
                .t2(stringToArray(this.t2))
                .t3(stringToArray(this.t3))
                .t4(stringToArray(this.t4))
                .t5(stringToArray(this.t5))
                .t6(stringToArray(this.t6))
                .t7(stringToArray(this.t7))
                .t8(stringToArray(this.t8))
                .t9(stringToArray(this.t9))
                .t10(stringToArray(this.t10))
                .up(this.up)
                .content(this.content)
                .tierListComments(this.tierListComments)
                .build();
    }

    public String arrayToString(String[] arr){
        if(arr != null && !arr[0].isEmpty()){
            return String.join(" ", arr);
        }else{
            return null;
        }
    }

    public String[] stringToArray(String tn){
        if(tn == null){
            return null;
        }else{
            return tn.split(" ");
        }
    }


}
