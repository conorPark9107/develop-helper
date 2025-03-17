package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.metaBuild.TierList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaBuildRepository extends PagingAndSortingRepository<TierList, Long> {

    void save(TierList entity);

    // 자식 객체인 댓글의 개수에 따른 페이징 처리
    @Query(
            value = "SELECT t FROM TierList t LEFT JOIN t.tierListComments c " +
                    "WHERE (:category IS NULL OR t.category = :category) " +
                    "GROUP BY t.id " +
                    "ORDER BY COUNT(c) DESC",
            countQuery = "SELECT COUNT(t) FROM TierList t WHERE (:category IS NULL OR t.category = :category)"
    )
    Page<TierList> findAllByCategoryOrderByCommentCountDesc(@Param("category") String category, Pageable pageable);


    // 카테고리에 따른 페이징 처리
    @Query(
            value = "SELECT t FROM TierList t WHERE (:category IS NULL OR t.category = :category) "
    )
    Page<TierList> findAllByCategory(@Param("category") String category, Pageable pageable);

}
