package com.newsprovider.portal.repository;

import com.newsprovider.portal.model.SubscriptionKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionKindRepository extends JpaRepository<SubscriptionKind, Long> {
    SubscriptionKind findByName(String name);

}
