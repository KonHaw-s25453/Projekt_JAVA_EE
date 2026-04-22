package com.asms.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ComputerDTO {
    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String serialNumber;

    @Size(max = 100)
    private String model;

    @Size(max = 100)
    private String location;

    @Pattern(regexp = "ACTIVE|INACTIVE|MAINTENANCE", message = "Status must be ACTIVE, INACTIVE, or MAINTENANCE")
    private String status;

    private Long osId;
}
