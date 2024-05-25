package com.finance.financecontrol.domain.category;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "categories")
@Entity(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    @Column(name = "user_id")
    private String userId;

    public Category(String title, String description, String userId) {
        this.title = title;
        this.description = description;
        this.userId = userId;
    }
}
