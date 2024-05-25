package com.finance.financecontrol.repositories;

import com.finance.financecontrol.domain.category.Category;
import com.finance.financecontrol.domain.category.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findByUserId(String userId);
    Optional<Category> findByIdAndUserId(String id, String userId);
}
