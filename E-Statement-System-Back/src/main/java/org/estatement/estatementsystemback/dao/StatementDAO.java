package org.estatement.estatementsystemback.dao;

import org.estatement.estatementsystemback.entity.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementDAO extends JpaRepository<Statement, Long> {
}
