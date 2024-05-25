package com.finance.financecontrol.domain.transaction;

import com.finance.financecontrol.domain.category.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TransactionResponseDTO {
    String id;
    String user_id;
    String title;
    String description;
    String type;
    List<CategoryResponse> categories;
    String transaction_value;
    Date transaction_date;

    @Getter
    @Setter
    public static class CategoryResponse {
        private String id;
        private String title;
        private String description;
    }
}


