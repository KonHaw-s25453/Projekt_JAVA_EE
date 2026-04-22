package com.asms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "installed_software")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InstalledSoftware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "installed_software_id")
    private Long installedSoftwareId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "computer_id", nullable = false)
    private Computer computer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "software_version_id", nullable = false)
    private SoftwareVersion softwareVersion;

    @Column(name = "installation_date")
    private LocalDate installationDate;

    @Column(name = "update_status", length = 20)
    private String updateStatus;

    @Column(name = "flagged_by_user")
    private Boolean flaggedByUser = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_admin")
    private User assignedToAdmin;
}
