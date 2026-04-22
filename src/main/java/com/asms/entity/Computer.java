package com.asms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "computers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "computer_id")
    private Long computerId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "serial_number", unique = true, length = 100)
    private String serialNumber;

    @Column(name = "model", length = 100)
    private String model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "os_id")
    private OperatingSystem operatingSystem;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "status", length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
}
