package com.asms.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SoftwareVersionDTO {
    @NotBlank(message = "Version number is required")
    @Size(max = 50)
    private String versionNumber;

    private LocalDate releaseDate;

    @Size(max = 500)
    private String downloadUrl;

    private String changelog;
}
