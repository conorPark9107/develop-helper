package com.albionhelper.helper.domain.metaBuild;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TierListDTO {

    private Long id;
    private String userId;
    @NotNull
    private String title;
    private LocalDateTime writeDate;
    private String category;
    private String t1Name;
    private String[] t1;
    private String t2Name;
    private String[] t2;
    private String t3Name;
    private String[] t3;
    private String t4Name;
    private String[] t4;
    private String t5Name;
    private String[] t5;
    private String t6Name;
    private String[] t6;
    private String t7Name;
    private String[] t7;
    private String t8Name;
    private String[] t8;
    private String t9Name;
    private String[] t9;
    private String t10Name;
    private String[] t10;

    private Long up;    
    private List<TierListComment> tierListComments;
    
    public void setT1(String t1) {
        this.t1 = t1.split(" ");
    }

    public void setT2(String t2) {
        this.t2 = t2.split(" ");
    }

    public void setT3(String t3) {
        this.t3 = t3.split(" ");
    }

    public void setT4(String t4) {
        this.t4 = t4.split(" ");
    }

    public void setT5(String t5) {
        this.t5 = t5.split(" ");
    }

    public void setT6(String t6) {
        this.t6 = t6.split(" ");
    }

    public void setT7(String t7) {
        this.t7 = t7.split(" ");
    }

    public void setT8(String t8) {
        this.t8 = t8.split(" ");
    }

    public void setT9(String t9) {
        this.t9 = t9.split(" ");
    }

    public void setT10(String t10) {
        this.t10 = t10.split(" ");
    }
}