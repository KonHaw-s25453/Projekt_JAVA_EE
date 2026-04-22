package com.asms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateStatusDTO {
    @NotBlank(message = "Status is required")
    @Pattern(regexp = "UP_TO_DATE|OUTDATED|UNKNOWN", message = "Status must be UP_TO_DATE, OUTDATED, or UNKNOWN")
    private String status;
}
