package org.estatement.estatementsystemback.dto.NotifcationsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private boolean receiveEmailNotifications;
    private boolean receiveSmsNotifications;
    private boolean receivePushNotifications;
}
