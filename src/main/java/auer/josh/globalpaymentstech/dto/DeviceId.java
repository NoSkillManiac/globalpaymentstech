package auer.josh.globalpaymentstech.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Builder
@Embeddable
public class DeviceId {
    private String machineCode;
    private String serialNumber;
}
