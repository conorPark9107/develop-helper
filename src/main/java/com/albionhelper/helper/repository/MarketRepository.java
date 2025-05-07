package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.market.MarketRank;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MarketRepository extends JpaRepository<MarketRank, Long> {

    Optional<MarketRank> findByItemId(String itemId);

    @Query(
            "SELECT m FROM MarketRank m ORDER BY m.count DESC LIMIT 20"
    )
    List<MarketRank> findAllTop20(Pageable pageable);

}
