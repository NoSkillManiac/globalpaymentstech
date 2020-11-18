package auer.josh.globalpaymentstech.service

import auer.josh.globalpaymentstech.dto.Device
import auer.josh.globalpaymentstech.dto.DeviceId
import auer.josh.globalpaymentstech.exception.DeviceManagementException
import auer.josh.globalpaymentstech.repository.DeviceRepository
import spock.lang.Specification

class DeviceManagementServiceSpec extends Specification {

    DeviceManagementService deviceManagementService = new DeviceManagementService()

    def setup() {
        deviceManagementService.deviceRepository = Mock(DeviceRepository)
    }

    def "Validate error thrown when a serial number does not match expected patterns"() {
        when: "a serial number is validated, but does not match patterns"
        deviceManagementService.validateSerialNumber(serialNumber)

        then: "an error is thrown"
        thrown(DeviceManagementException.class)

        where:
        serialNumber << ["123-456-hello", "hello-moto", "iambadsn", "j0sh-au3r-d3m0"]
    }

    def "Validate no error thrown when a serial number matches expectations"() {
        when: "The serial number is validated and it matches expected patterns"
        deviceManagementService.validateSerialNumber(serialNumber)

        then: "no error is thrown"
        notThrown(DeviceManagementException.class)

        where:
        serialNumber << ["12-1222", "3455670-22222", "1-00022221"]
    }

    def "Validate machine code does not match expectations and an error is thrown"() {
        when:
        deviceManagementService.validateMachineCode(null)

        then:
        thrown(DeviceManagementException.class)
    }

    def "Validate machine code matches expectations and no error is thrown"() {
        when:
        deviceManagementService.validateMachineCode("iammachinecode")

        then:
        notThrown(DeviceManagementException.class)
    }

    def "Ensure update method updates deviceName"() {
        given:
        Device device = Device.builder()
                .serialNumber("12-1222")
                .machineCode("iammachinecode")
                .deviceName("This is device")
                .build()
        deviceManagementService.deviceRepository.findDeviceById(_ as DeviceId) >> new Device()

        when:
        Device result = deviceManagementService.updateDevice()

        then:
        result == device
    }

}
