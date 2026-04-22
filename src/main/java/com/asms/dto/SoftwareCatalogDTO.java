package com.asms.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SoftwareCatalogDTO {
    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;

    private String description;

    @Size(max = 50)
    private String licenseType;

    @Size(max = 255)
    private String officialSite;

    private Boolean isActive = true;
}
