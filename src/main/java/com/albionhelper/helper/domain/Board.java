package com.albionhelper.helper.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "title")
    private String title;

    @Column(name = "password")
    private String password;

    @Column(name = "category")
    private String category;

    @Column(name = "contents")
    private String contents;

    @Column(name = "write_date")
    private LocalDateTime write_date;

    @Column(name = "view_count")
    private int view_count;


    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", nickName='" + nickname + '\'' +
                ", title='" + title + '\'' +
                ", password='" + password + '\'' +
                ", category='" + category + '\'' +
                ", contents='" + contents + '\'' +
                ", write_date=" + write_date +
                ", view_count=" + view_count +
                '}';
    }
}
