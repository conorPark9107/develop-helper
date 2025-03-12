package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.metaBuild.TierList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaBuildRepository extends JpaRepository<TierList, Long> {

}
