package auer.josh.globalpaymentstech.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    private String machineCode;
    private String serialNumber;
    private String deviceName;
}
