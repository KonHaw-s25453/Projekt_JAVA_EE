package com.asms.repository;

import com.asms.entity.InstalledSoftware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InstalledSoftwareRepository extends JpaRepository<InstalledSoftware, Long> {
    List<InstalledSoftware> findByComputerComputerId(Long computerId);

    @Query("SELECT i FROM InstalledSoftware i WHERE i.updateStatus = 'OUTDATED' OR i.flaggedByUser = true")
    List<InstalledSoftware> findOutdatedSoftware();
}
