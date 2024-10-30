package com.albionhelper.helper.domain.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "title")
    private String title;

    @Column(name = "password")
    private String password;

    @Column(name = "category")
    private int category;

    @Column(name = "contents")
    private String contents;

    @CreationTimestamp
    @Column(name = "write_date")
    private LocalDateTime write_date;

    @Column(name = "view_count")
    private int view_count;

    @Column(name = "updown")
    private int updown;

    public BoardResponseDTO toResponseDTO(){
        return new BoardResponseDTO().builder()
                .id(id)
                .nickname(nickname)
                .title(title)
                .category(category)
                .contents(contents)
                .write_date(write_date)
                .view_count(view_count)
                .updown(updown)
                .build();
    }



    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", title='" + title + '\'' +
                ", password='" + password + '\'' +
                ", category=" + category +
                ", contents='" + contents + '\'' +
                ", write_date=" + write_date +
                ", view_count=" + view_count +
                ", updown=" + updown +
                '}';
    }
}
