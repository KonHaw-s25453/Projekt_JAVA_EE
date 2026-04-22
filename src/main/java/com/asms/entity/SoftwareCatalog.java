package com.asms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "software_catalog")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SoftwareCatalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "software_id")
    private Long softwareId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "license_type", length = 50)
    private String licenseType;

    @Column(name = "official_site", length = 255)
    private String officialSite;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
