package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.metaBuild.TierList;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaBuildRepository extends PagingAndSortingRepository<TierList, Long> {

    void save(TierList entity);
}
