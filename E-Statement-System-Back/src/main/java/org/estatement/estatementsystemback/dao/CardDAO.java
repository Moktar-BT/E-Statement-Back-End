package org.estatement.estatementsystemback.dao;

import org.estatement.estatementsystemback.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDAO extends JpaRepository<Card, Long> {
}
