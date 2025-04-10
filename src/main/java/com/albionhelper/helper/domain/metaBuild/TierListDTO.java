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
    private String content;

    private Long up;
    private List<TierListComment> tierListComments;
    
    public void setT1(String t1) {
        this.t1 = convertStringToArray(t1);
    }

    public void setT2(String t2) {
        this.t2 = convertStringToArray(t2);
    }

    public void setT3(String t3) {
        this.t3 = convertStringToArray(t3);
    }

    public void setT4(String t4) {
        this.t4 = convertStringToArray(t4);
    }

    public void setT5(String t5) {
        this.t5 = convertStringToArray(t5);
    }

    public void setT6(String t6) {
        this.t6 = convertStringToArray(t6);
    }

    public void setT7(String t7) {
        this.t7 = convertStringToArray(t7);
    }

    public void setT8(String t8) {
        this.t8 = convertStringToArray(t8);
    }

    public void setT9(String t9) {
        this.t9 = convertStringToArray(t9);
    }

    public void setT10(String t10) {
        this.t10 = convertStringToArray(t10);
    }

    public static String[] convertStringToArray(String tn){
        if(tn.isEmpty()){
            return null;
        }else{
            return tn.split(" ");
        }
    }

}