package org.estatement.estatementsystemback.dao;

import org.estatement.estatementsystemback.dto.SettingsDTO.ConnectionDTO;
import org.estatement.estatementsystemback.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionDAO extends JpaRepository<Connection, Integer> {

    @Query("SELECT new org.estatement.estatementsystemback.dto.SettingsDTO.ConnectionDTO(" +
            "c.date_time, c.location, c.deviceType, c.deviceName, c.status) " +
            "FROM Connection c WHERE c.user.email = :email")
    List<ConnectionDTO> findConnectionDTOsByUserEmail(@Param("email") String email);


}
