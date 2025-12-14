package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Map;

@Data
public class MappingSchemaDTO {

    @NotBlank(message = "mappingKey is required")
    @Pattern(
            regexp = "^[a-zA-Z0-9_-]{3,20}$",
            message = "mappingKey must be 3-20 chars, only letters, numbers, _ and -"
    )
    private String mappingKey;

    @NotBlank(message = "protocolIn is required")
    @Pattern(
            regexp = "^(REST|SOAP|TCP|MQTT)$",
            message = "protocolIn must be REST, SOAP, TCP or MQTT"
    )
    private String protocolIn;

    @NotBlank(message = "protocolOut is required")
    @Pattern(
            regexp = "^(REST|SOAP|TCP|MQTT)$",
            message = "protocolOut must be REST, SOAP, TCP or MQTT"
    )
    private String protocolOut;

    @NotBlank(message = "formatIn is required")
    @Pattern(
            regexp = "^(JSON|XML|TEXT)$",
            message = "formatIn must be JSON, XML or TEXT"
    )
    private String formatIn;

    @NotBlank(message = "formatOut is required")
    @Pattern(
            regexp = "^(JSON|XML|TEXT)$",
            message = "formatOut must be JSON, XML or TEXT"
    )
    private String formatOut;

    @NotNull(message = "requestMapping cannot be null")
    private Map<String, Object> requestMapping;

    @NotNull(message = "responseMapping cannot be null")
    private Map<String, Object> responseMapping;

    @NotNull(message = "staticFields cannot be null")
    private Map<String, Object> staticFields;
}
