package com.asms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "compatibility")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Compatibility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compatibility_id")
    private Long compatibilityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "software_version_id", nullable = false)
    private SoftwareVersion softwareVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "os_id", nullable = false)
    private OperatingSystem operatingSystem;

    @Column(name = "is_compatible")
    private Boolean isCompatible = true;

    @Column(name = "min_ram_gb")
    private Integer minRamGb;

    @Column(name = "min_storage_gb")
    private Integer minStorageGb;
}
