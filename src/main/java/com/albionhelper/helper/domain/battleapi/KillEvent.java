package com.albionhelper.helper.domain.battleapi;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "kill_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KillEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 기본 키 자동 증가 설정
    private Long id;

    @JsonProperty("EventId")
    private Long eventId;
    @JsonProperty("TimeStamp")
    private Instant eventTime;

    @JsonProperty("groupMemberCount")
    private int groupMemberCount;

    @JsonProperty("numberOfParticipants")
    private int numberOfParticipants;

    @JsonProperty("Killer")
    @OneToOne(cascade = CascadeType.ALL)
    private Player killer;

    @JsonProperty("Victim")
    @OneToOne(cascade = CascadeType.ALL)
    private Player victim;

    @JsonProperty("Participants")
    @OneToMany(mappedBy = "killEvent", cascade = CascadeType.ALL)
    private List<Participant> participants;
}
