package org.app.analyticservice.repositories;

import org.app.analyticservice.entities.AccountAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountAnalyticsRepo extends JpaRepository<AccountAnalytics,Long> {
    AccountAnalytics findByAccountId(String accountId);
}