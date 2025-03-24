package org.estatement.estatementsystemback.dao;

import org.estatement.estatementsystemback.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDAO extends JpaRepository<Account, Long> {
}
