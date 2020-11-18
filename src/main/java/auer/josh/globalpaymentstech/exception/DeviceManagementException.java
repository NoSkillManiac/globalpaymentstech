package auer.josh.globalpaymentstech.exception;

import auer.josh.globalpaymentstech.dto.Error;
import lombok.Getter;

import java.util.List;

@Getter
public class DeviceManagementException extends RuntimeException {

    private final List<Error> errors;

    public DeviceManagementException(List<Error> errors){
        super("Device Management Exception Occurred");
        this.errors = errors;
    }

}
