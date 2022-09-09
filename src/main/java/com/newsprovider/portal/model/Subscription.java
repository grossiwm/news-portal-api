package com.newsprovider.portal.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "subscription")
@Data
public class Subscription {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Calendar initialDate;

    @Column
    private Calendar endDate;

    @Column
    private Long paymentId;

    @ManyToOne
    private User user;


}
