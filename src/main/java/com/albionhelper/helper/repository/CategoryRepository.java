package com.albionhelper.helper.repository;

import com.albionhelper.helper.domain.board.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
