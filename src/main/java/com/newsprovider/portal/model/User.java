package com.newsprovider.portal.model;

import com.newsprovider.portal.model.enums.Language;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "native_language")
    private Language nativeLanguage;

    @OneToMany
    private List<Subscription> subscriptions;

    @ManyToMany
    @JoinTable(
            name = "category_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

}
