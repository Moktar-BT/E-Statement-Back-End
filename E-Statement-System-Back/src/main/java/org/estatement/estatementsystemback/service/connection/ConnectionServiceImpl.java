package org.estatement.estatementsystemback.service.connection;

import lombok.RequiredArgsConstructor;
import org.estatement.estatementsystemback.dao.ConnectionDAO;
import org.estatement.estatementsystemback.dao.UserDAO;
import org.estatement.estatementsystemback.dto.SettingsDTO.ConnectionDTO;
import org.estatement.estatementsystemback.entity.Connection;
import org.estatement.estatementsystemback.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ConnectionDAO connectionDAO;
    @Override
    public boolean saveConnectionByEmail(String email ,String location, String deviceType, String deviceName, boolean status) {

        Optional<User> optionalUser = userDAO.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Connection connection = new Connection();
            connection.setDate_time(LocalDateTime.now());
            connection.setLocation(location);
            connection.setDeviceType(deviceType);
            connection.setDeviceName(deviceName);
            connection.setStatus(status);
            connection.setUser(user);
            connectionDAO.save(connection);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ConnectionDTO> getConnectionsByUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return connectionDAO.findConnectionDTOsByUserEmail(email);
    }

}
