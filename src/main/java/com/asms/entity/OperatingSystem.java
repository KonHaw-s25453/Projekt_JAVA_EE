package com.asms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "operating_systems")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OperatingSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "os_id")
    private Long osId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "version", length = 50)
    private String version;

    @Column(name = "architecture", length = 20)
    private String architecture;
}
