package com.asms.controller;

import com.asms.entity.SoftwareCatalog;
import com.asms.entity.SoftwareVersion;
import com.asms.service.SoftwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/software")
@RequiredArgsConstructor
public class SoftwareController {

    private final SoftwareService softwareService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SoftwareCatalog> create(@RequestBody SoftwareCatalog software) {
        return ResponseEntity.status(HttpStatus.CREATED).body(softwareService.createSoftware(software));
    }

    @GetMapping
    public ResponseEntity<List<SoftwareCatalog>> getAll() {
        return ResponseEntity.ok(softwareService.getAllSoftware());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoftwareCatalog> getById(@PathVariable Long id) {
        return ResponseEntity.ok(softwareService.getSoftwareById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SoftwareCatalog> update(@PathVariable Long id, @RequestBody SoftwareCatalog software) {
        return ResponseEntity.ok(softwareService.updateSoftware(id, software));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        softwareService.deleteSoftware(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/versions")
    public ResponseEntity<List<SoftwareVersion>> getVersions(@PathVariable Long id) {
        return ResponseEntity.ok(softwareService.getSoftwareVersions(id));
    }

    @PostMapping("/{id}/versions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SoftwareVersion> addVersion(@PathVariable Long id, @RequestBody SoftwareVersion version) {
        return ResponseEntity.status(HttpStatus.CREATED).body(softwareService.addVersion(id, version));
    }
}
