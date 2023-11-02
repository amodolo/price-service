package dev.me.price.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "percentage_discount")
public class PercentageDiscount implements Discount<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    UUID id;

    @Column
    UUID productId;

    @Column(nullable = false)
    @Positive
    int quantity;

    @Column(nullable = false)
    @Positive
    @Max(100)
    Integer amount;

}
