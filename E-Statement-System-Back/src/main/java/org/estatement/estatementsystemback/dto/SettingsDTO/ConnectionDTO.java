package org.estatement.estatementsystemback.dto.SettingsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDTO {
    private LocalDateTime date_time;
    private String location ;
    private String deviceType;
    private String deviceName;
    private boolean status ;
}
