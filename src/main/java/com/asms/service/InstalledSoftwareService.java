package com.asms.service;

import com.asms.dto.InstalledSoftwareDTO;
import com.asms.entity.*;
import com.asms.exception.ResourceNotFoundException;
import com.asms.repository.InstalledSoftwareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InstalledSoftwareService {

    private final InstalledSoftwareRepository installedSoftwareRepository;

    public InstalledSoftware create(InstalledSoftwareDTO dto) {
        Computer computer = new Computer();
        computer.setComputerId(dto.getComputerId());
        SoftwareVersion version = new SoftwareVersion();
        version.setSoftwareVersionId(dto.getSoftwareVersionId());
        InstalledSoftware installed = InstalledSoftware.builder()
                .computer(computer)
                .softwareVersion(version)
                .installationDate(dto.getInstallationDate())
                .updateStatus(dto.getUpdateStatus() != null ? dto.getUpdateStatus() : "UNKNOWN")
                .flaggedByUser(false)
                .build();
        return installedSoftwareRepository.save(installed);
    }

    @Transactional(readOnly = true)
    public InstalledSoftware getById(Long id) {
        return installedSoftwareRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InstalledSoftware", "id", id));
    }

    @Transactional(readOnly = true)
    public List<InstalledSoftware> getAll() {
        return installedSoftwareRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<InstalledSoftware> getByComputer(Long computerId) {
        return installedSoftwareRepository.findByComputerComputerId(computerId);
    }

    @Transactional(readOnly = true)
    public List<InstalledSoftware> findOutdated() {
        return installedSoftwareRepository.findOutdatedSoftware();
    }

    public InstalledSoftware flagForUpdate(Long id) {
        InstalledSoftware installed = getById(id);
        installed.setFlaggedByUser(true);
        return installedSoftwareRepository.save(installed);
    }

    public InstalledSoftware changeStatus(Long id, String status) {
        InstalledSoftware installed = getById(id);
        installed.setUpdateStatus(status);
        return installedSoftwareRepository.save(installed);
    }

    public InstalledSoftware assignToAdmin(Long id, Long adminId) {
        InstalledSoftware installed = getById(id);
        User admin = new User();
        admin.setUserId(adminId);
        installed.setAssignedToAdmin(admin);
        return installedSoftwareRepository.save(installed);
    }

    public void delete(Long id) {
        InstalledSoftware installed = getById(id);
        installedSoftwareRepository.delete(installed);
    }
}
