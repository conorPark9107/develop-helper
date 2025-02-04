package com.albionhelper.helper.domain.battleapi;
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
    private Long eventId;
    private Instant eventTime;
    private int numberOfParticipants;
    private int groupMemberCount;

    @OneToOne(cascade = CascadeType.ALL)
    private Player killer;

    @OneToOne(cascade = CascadeType.ALL)
    private Player victim;

    @OneToMany(mappedBy = "killEvent", cascade = CascadeType.ALL)
    private List<Participant> participants;
}
