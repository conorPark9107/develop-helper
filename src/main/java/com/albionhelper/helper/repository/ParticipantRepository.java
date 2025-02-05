package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.battleapi.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
