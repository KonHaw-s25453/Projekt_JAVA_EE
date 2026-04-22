package com.asms.controller;

import com.asms.dto.AssignAdminDTO;
import com.asms.dto.InstalledSoftwareDTO;
import com.asms.dto.UpdateStatusDTO;
import com.asms.entity.InstalledSoftware;
import com.asms.service.InstalledSoftwareService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/installed-software")
@RequiredArgsConstructor
public class InstalledSoftwareController {

    private final InstalledSoftwareService installedSoftwareService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstalledSoftware> create(@Valid @RequestBody InstalledSoftwareDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(installedSoftwareService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<InstalledSoftware>> getAll() {
        return ResponseEntity.ok(installedSoftwareService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstalledSoftware> getById(@PathVariable Long id) {
        return ResponseEntity.ok(installedSoftwareService.getById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        installedSoftwareService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/flag")
    public ResponseEntity<InstalledSoftware> flag(@PathVariable Long id) {
        return ResponseEntity.ok(installedSoftwareService.flagForUpdate(id));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstalledSoftware> changeStatus(@PathVariable Long id, @Valid @RequestBody UpdateStatusDTO body) {
        return ResponseEntity.ok(installedSoftwareService.changeStatus(id, body.getStatus()));
    }

    @GetMapping("/outdated")
    public ResponseEntity<List<InstalledSoftware>> getOutdated() {
        return ResponseEntity.ok(installedSoftwareService.findOutdated());
    }

    @PutMapping("/{id}/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstalledSoftware> assignToAdmin(@PathVariable Long id, @Valid @RequestBody AssignAdminDTO body) {
        return ResponseEntity.ok(installedSoftwareService.assignToAdmin(id, body.getAdminId()));
    }
}
