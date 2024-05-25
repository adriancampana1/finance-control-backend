package com.finance.financecontrol.domain.transaction_category;

import com.finance.financecontrol.domain.category.Category;
import com.finance.financecontrol.domain.transaction.Transaction;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "transactions_categories")
@Entity(name = "transactions_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TransactionCategory {

    @EmbeddedId
    private TransactionCategoryId id;

    @ManyToOne
    @JoinColumn(name = "id_transaction", referencedColumnName = "id", insertable = false, updatable = false)
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "id", insertable = false, updatable = false)
    private Category category;
}
