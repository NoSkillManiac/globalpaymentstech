package auer.josh.globalpaymentstech.repository;

import auer.josh.globalpaymentstech.dto.DeviceDao;
import auer.josh.globalpaymentstech.dto.DeviceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<DeviceDao, DeviceId> {

    DeviceDao findDeviceById(DeviceId id);

    DeviceDao findDeviceBySerialNumber(String serialNumber);

    DeviceDao findDeviceByMachineCode(String machineCode);

    void setDeviceName(String deviceName);
}
