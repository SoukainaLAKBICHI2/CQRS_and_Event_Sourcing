package org.app.escqrsaxon.query.repositories;

import org.app.escqrsaxon.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
