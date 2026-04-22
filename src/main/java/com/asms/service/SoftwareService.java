package com.asms.service;

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

    public SoftwareCatalog createSoftware(SoftwareCatalog software) {
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

    public SoftwareCatalog updateSoftware(Long id, SoftwareCatalog software) {
        SoftwareCatalog existing = getSoftwareById(id);
        existing.setName(software.getName());
        existing.setDescription(software.getDescription());
        existing.setLicenseType(software.getLicenseType());
        existing.setOfficialSite(software.getOfficialSite());
        existing.setIsActive(software.getIsActive());
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

    public SoftwareVersion addVersion(Long softwareId, SoftwareVersion version) {
        SoftwareCatalog software = getSoftwareById(softwareId);
        version.setSoftwareCatalog(software);
        return softwareVersionRepository.save(version);
    }
}
