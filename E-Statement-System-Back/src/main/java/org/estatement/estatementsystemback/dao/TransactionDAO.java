package org.estatement.estatementsystemback.dao;


import org.estatement.estatementsystemback.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDAO extends JpaRepository<Transaction, Long> {
}
