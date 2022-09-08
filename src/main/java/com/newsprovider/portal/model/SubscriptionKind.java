package com.newsprovider.portal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "subscription_kind")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionKind {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private Integer days;

    @Column
    private BigDecimal price;

    @Column
    private String name;

    @OneToMany
    private List<Payment> payment;
}
