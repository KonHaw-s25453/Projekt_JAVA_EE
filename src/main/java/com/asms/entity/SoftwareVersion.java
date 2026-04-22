package com.asms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "software_versions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SoftwareVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "software_version_id")
    private Long softwareVersionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "software_id", nullable = false)
    private SoftwareCatalog softwareCatalog;

    @Column(name = "version_number", nullable = false, length = 50)
    private String versionNumber;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "download_url", length = 500)
    private String downloadUrl;

    @Column(name = "changelog", columnDefinition = "TEXT")
    private String changelog;
}
