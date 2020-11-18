package auer.josh.globalpaymentstech.controller;

import auer.josh.globalpaymentstech.dto.Device;
import auer.josh.globalpaymentstech.service.DeviceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeviceManagementController {

    private final DeviceManagementService deviceManagementService;

    @Autowired
    public DeviceManagementController(DeviceManagementService deviceManagementService) {
        this.deviceManagementService = deviceManagementService;
    }

    @PostMapping(value = "/device", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createDevice(@RequestBody Device device) {
        return new ResponseEntity<>(deviceManagementService.createDevice(device), HttpStatus.CREATED);
    }

    @PutMapping(value = "/device", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Device> updateDevice(@RequestBody Device device) {
        return new ResponseEntity<>(deviceManagementService.updateDevice(device), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/device", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Device> getDevice(@RequestParam String serialNumber, @RequestParam String machineCode) {
        return new ResponseEntity<>(deviceManagementService.getDevice(serialNumber, machineCode), HttpStatus.OK);
    }
}
