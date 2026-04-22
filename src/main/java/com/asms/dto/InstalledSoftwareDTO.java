package com.asms.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InstalledSoftwareDTO {
    @NotNull(message = "Computer ID is required")
    private Long computerId;

    @NotNull(message = "Software version ID is required")
    private Long softwareVersionId;

    private LocalDate installationDate;

    @Pattern(regexp = "UP_TO_DATE|OUTDATED|UNKNOWN", message = "Update status must be UP_TO_DATE, OUTDATED, or UNKNOWN")
    private String updateStatus;
}
