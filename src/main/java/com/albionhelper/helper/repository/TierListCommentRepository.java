package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.metaBuild.TierListComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TierListCommentRepository extends JpaRepository<TierListComment, Long> {
    Optional<TierListComment> findByIdAndPassword(Long id, String pw);
}
