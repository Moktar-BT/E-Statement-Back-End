package org.estatement.estatementsystemback.dao;

import org.estatement.estatementsystemback.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationDAO extends JpaRepository<Notification, Long> {
}
