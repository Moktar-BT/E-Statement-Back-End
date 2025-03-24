package org.estatement.estatementsystemback.dao;

import org.estatement.estatementsystemback.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionDAO extends JpaRepository<Connection, Integer> {
}
