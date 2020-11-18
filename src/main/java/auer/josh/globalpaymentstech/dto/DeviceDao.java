package auer.josh.globalpaymentstech.dto;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
public class DeviceDao {
    @EmbeddedId
    private DeviceId deviceId;
    private String deviceName;
}
