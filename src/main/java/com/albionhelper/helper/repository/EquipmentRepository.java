package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.battleapi.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
