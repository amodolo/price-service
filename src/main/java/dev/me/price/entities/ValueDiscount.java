package dev.me.price.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "value_discount")
public class ValueDiscount implements Discount<Float> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column
    private UUID productId;

    @Column(nullable = false)
    @Positive
    private int quantity;

    @Column(nullable = false)
    @Positive
    private Float amount;

}
