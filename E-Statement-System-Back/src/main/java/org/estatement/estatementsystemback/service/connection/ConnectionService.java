package org.estatement.estatementsystemback.service.connection;

import org.estatement.estatementsystemback.dto.SettingsDTO.ConnectionDTO;

import java.util.List;

public interface ConnectionService {
    boolean saveConnectionByEmail(String email ,String location, String deviceType, String deviceName, boolean status);
    List<ConnectionDTO> getConnectionsByUserEmail();
}
