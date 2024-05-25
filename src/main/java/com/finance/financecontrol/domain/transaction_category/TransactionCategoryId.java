package com.finance.financecontrol.domain.transaction_category;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class TransactionCategoryId implements Serializable {
    @Column(name = "id_transaction")
    private String idTransaction;

    @Column(name = "id_category")
    private String idCategory;
}
