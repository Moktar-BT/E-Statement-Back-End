package org.estatement.estatementsystemback.dao;

import jakarta.transaction.Transactional;
import org.estatement.estatementsystemback.dto.NotifcationsDTO.NotificationDTO;
import org.estatement.estatementsystemback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserDAO extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.receiveEmailNotifications = :email, " +
            "u.receiveSmsNotifications = :sms, " +
            "u.receivePushNotifications = :push " +
            "WHERE u.email = :userEmail")
    void updateNotificationPreferencesByEmail(
            @Param("userEmail") String userEmail,
            @Param("email") boolean email,
            @Param("sms") boolean sms,
            @Param("push") boolean push
    );

    @Query("SELECT NEW org.estatement.estatementsystemback.dto.NotifcationsDTO.NotificationDTO(" +
            "u.receiveEmailNotifications, " +
            "u.receiveSmsNotifications, " +
            "u.receivePushNotifications) " +
            "FROM User u WHERE u.email = :email")
    NotificationDTO findNotificationPreferencesByEmail(@Param("email") String email);


}
