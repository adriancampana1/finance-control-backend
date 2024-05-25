package com.finance.financecontrol.domain.transaction;

import com.finance.financecontrol.domain.category.Category;

import java.util.Date;
import java.util.List;

public record TransactionDTO(String userId,
                             String title,
                             String description,
                             String type,
                             List<String> categoryIds,
                             String transaction_value,
                             Date transaction_date) {
}
