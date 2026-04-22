package com.asms.controller;

import com.asms.dto.ComputerDTO;
import com.asms.entity.Computer;
import com.asms.service.ComputerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/computers")
@RequiredArgsConstructor
public class ComputerController {

    private final ComputerService computerService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Computer> create(@Valid @RequestBody ComputerDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(computerService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<Computer>> getAll() {
        return ResponseEntity.ok(computerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Computer> getById(@PathVariable Long id) {
        return ResponseEntity.ok(computerService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Computer> update(@PathVariable Long id, @Valid @RequestBody ComputerDTO dto) {
        return ResponseEntity.ok(computerService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        computerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Computer>> search(@RequestParam(required = false) String location,
                                                  @RequestParam(required = false) String status) {
        if (location != null) return ResponseEntity.ok(computerService.findByLocation(location));
        if (status != null) return ResponseEntity.ok(computerService.findByStatus(status));
        return ResponseEntity.ok(computerService.getAll());
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(computerService.getStats());
    }
}
