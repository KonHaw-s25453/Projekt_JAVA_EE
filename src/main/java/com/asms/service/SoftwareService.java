package com.asms.service;

import com.asms.dto.SoftwareCatalogDTO;
import com.asms.dto.SoftwareVersionDTO;
import com.asms.entity.SoftwareCatalog;
import com.asms.entity.SoftwareVersion;
import com.asms.exception.ResourceNotFoundException;
import com.asms.repository.SoftwareCatalogRepository;
import com.asms.repository.SoftwareVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SoftwareService {

    private final SoftwareCatalogRepository softwareCatalogRepository;
    private final SoftwareVersionRepository softwareVersionRepository;

    public SoftwareCatalog createSoftware(SoftwareCatalogDTO dto) {
        SoftwareCatalog software = SoftwareCatalog.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .licenseType(dto.getLicenseType())
                .officialSite(dto.getOfficialSite())
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
                .build();
        return softwareCatalogRepository.save(software);
    }

    @Transactional(readOnly = true)
    public SoftwareCatalog getSoftwareById(Long id) {
        return softwareCatalogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Software", "id", id));
    }

    @Transactional(readOnly = true)
    public List<SoftwareCatalog> getAllSoftware() {
        return softwareCatalogRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<SoftwareCatalog> getActiveSoftware() {
        return softwareCatalogRepository.findByIsActiveTrue();
    }

    public SoftwareCatalog updateSoftware(Long id, SoftwareCatalogDTO dto) {
        SoftwareCatalog existing = getSoftwareById(id);
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setLicenseType(dto.getLicenseType());
        existing.setOfficialSite(dto.getOfficialSite());
        if (dto.getIsActive() != null) {
            existing.setIsActive(dto.getIsActive());
        }
        return softwareCatalogRepository.save(existing);
    }

    public void deleteSoftware(Long id) {
        SoftwareCatalog software = getSoftwareById(id);
        softwareCatalogRepository.delete(software);
    }

    @Transactional(readOnly = true)
    public List<SoftwareVersion> getSoftwareVersions(Long softwareId) {
        return softwareVersionRepository.findBySoftwareId(softwareId);
    }

    public SoftwareVersion addVersion(Long softwareId, SoftwareVersionDTO dto) {
        SoftwareCatalog software = getSoftwareById(softwareId);
        SoftwareVersion version = SoftwareVersion.builder()
                .softwareCatalog(software)
                .versionNumber(dto.getVersionNumber())
                .releaseDate(dto.getReleaseDate())
                .downloadUrl(dto.getDownloadUrl())
                .changelog(dto.getChangelog())
                .build();
        return softwareVersionRepository.save(version);
    }
}
