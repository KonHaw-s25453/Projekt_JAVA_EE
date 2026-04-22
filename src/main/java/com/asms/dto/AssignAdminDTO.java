package com.asms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AssignAdminDTO {
    @NotNull(message = "Admin ID is required")
    private Long adminId;
}
