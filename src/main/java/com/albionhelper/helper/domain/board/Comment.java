package com.albionhelper.helper.domain.board;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "comment")
    private String comment;

    @Column(name = "comment_group")
    private Long comment_group;

    @CreationTimestamp
    @Column(name = "write_date")
    private LocalDateTime write_date;

    public CommentResponseDTO toResponseDTO(){
        return new CommentResponseDTO().builder()
                .id(id)
                .board_id(boardId)
                .nickname(nickname)
                .password(password)
                .write_date(write_date)
                .comment(comment)
                .comment_group(comment_group)
                .build();
    }


    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", board_id=" + boardId +
                ", nickname='" + nickname + '\'' +
                ", write_date=" + write_date +
                ", comment='" + comment + '\'' +
                '}';
    }
}
