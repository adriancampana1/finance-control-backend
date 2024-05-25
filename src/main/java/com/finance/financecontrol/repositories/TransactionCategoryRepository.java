package com.finance.financecontrol.repositories;

import com.finance.financecontrol.domain.transaction_category.TransactionCategory;
import com.finance.financecontrol.domain.transaction_category.TransactionCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, TransactionCategoryId> {
    List<TransactionCategory> findById_IdTransaction(String idTransaction);
    void deleteById_IdTransaction(String transactionId);
}
