package com.albionhelper.helper.domain.board;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "Inquire")
@DynamicInsert
public class Inquire {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "content", columnDefinition = "longtext")
    private String content;

    @Lob
    @Column(name = "answer", columnDefinition = "longtext")
    @ColumnDefault("'아직 답변 전입니다.'")
    private String answer;

    @Override
    public String toString() {
        answer = (answer == null)? "아직 답변 전 입니다." : answer;
        return "{\"id\": " + id + "," +
                "\"content\": \"" + content + "\"," +
                "\"answer\": \"" + answer + "\"" +
                "}";
    }
}
