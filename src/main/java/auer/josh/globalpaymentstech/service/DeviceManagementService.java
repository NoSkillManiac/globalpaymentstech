package auer.josh.globalpaymentstech.service;

import auer.josh.globalpaymentstech.dto.Device;
import auer.josh.globalpaymentstech.dto.DeviceDao;
import auer.josh.globalpaymentstech.dto.DeviceId;
import auer.josh.globalpaymentstech.dto.Error;
import auer.josh.globalpaymentstech.exception.DeviceManagementException;
import auer.josh.globalpaymentstech.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DeviceManagementService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceManagementService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public String createDevice(Device device) {
        validateRequiredFieldsExist(device.getSerialNumber(), device.getMachineCode());
        DeviceDao deviceDao = new DeviceDao();
        deviceDao.setDeviceName(device.getDeviceName());
        deviceDao.setDeviceId(DeviceId.builder()
                .serialNumber(device.getSerialNumber())
                .machineCode(device.getMachineCode())
                .build());
        deviceRepository.saveAndFlush(deviceDao);
        return "Record Created successfully";
    }

    public Device updateDevice(Device device) {
        getDevice(device.getSerialNumber(), device.getMachineCode());
        deviceRepository.setDeviceName(device.getDeviceName());
        return device;
    }

    public Device getDevice(String serialNumber, String machineCode) {
        validateRequiredFieldsExist(serialNumber, machineCode);
        DeviceDao deviceDao = deviceRepository.findDeviceById(DeviceId.builder()
                .serialNumber(serialNumber)
                .machineCode(machineCode)
                .build());
        if (deviceDao == null) {
            throw new DeviceManagementException(produceDeviceIdErrors(serialNumber, machineCode));
        }
        return Device.builder()
                .serialNumber(serialNumber)
                .machineCode(machineCode)
                .deviceName(deviceDao.getDeviceName())
                .build();
    }

    private void validateRequiredFieldsExist(String serialNumber, String machineCode) {
        validateSerialNumber(serialNumber);
        validateMachineCode(machineCode);
    }

    private void validateSerialNumber(String serialNumber) {
        if (!(serialNumber.matches("[0-9a-zA-Z]{2}-[0-9a-zA-Z]{4}")
                || serialNumber.matches("[0-9a-zA-Z]{7}-[0-9a-zA-Z]{5}")
                || serialNumber.matches(" [0-9a-zA-Z]-[0-9a-zA-Z]{8}"))) {
            throw new DeviceManagementException(Collections.singletonList(Error.builder()
                    .message("The serial number entered can include a - z, A - Z, 0 - 9 and hyphen. Please correct your entry.")
                    .errorCode("ER003")
                    .resourceKey("serial.number.invalid")
                    .build()));
        }
    }

    private void validateMachineCode(String machineCode) {
        if (machineCode.isBlank()) {
            throw new DeviceManagementException(Collections.singletonList(Error.builder()
                            .message("The machine code is incorrect. Check the Machine code you provided and try again.")
                            .errorCode("ER001")
                            .resourceKey("machine.code.invalid")
                            .build()));
        }
    }

    private List<Error> produceDeviceIdErrors(String serialNumber, String machineCode) {
        List<Error> errors = new ArrayList<>();
        if (deviceRepository.findDeviceBySerialNumber(serialNumber) == null) {
            errors.add(Error.builder()
                    .message("The serial number does not match our records.")
                    .errorCode("ER004")
                    .resourceKey("serial.number.not.found")
                    .build());
        }
        if (deviceRepository.findDeviceByMachineCode(machineCode) == null) {
            errors.add(Error.builder()
                    .message("The machine code does not match our records.")
                    .errorCode("ER002")
                    .resourceKey("machine.code.not.found")
                    .build());
        }
        return errors;
    }

}
