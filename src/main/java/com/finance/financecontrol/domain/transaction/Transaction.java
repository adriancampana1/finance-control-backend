package com.finance.financecontrol.domain.transaction;

import com.finance.financecontrol.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "transactions")
@Entity(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    private String type;
    private String transaction_value;
    private Date transaction_date;

    @Column(name = "user_id")
    private String userId;
}
