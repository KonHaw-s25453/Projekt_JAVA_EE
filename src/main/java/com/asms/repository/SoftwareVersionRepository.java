package com.asms.repository;

import com.asms.entity.SoftwareVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SoftwareVersionRepository extends JpaRepository<SoftwareVersion, Long> {
    @Query("SELECT sv FROM SoftwareVersion sv WHERE sv.softwareCatalog.softwareId = :softwareId")
    List<SoftwareVersion> findBySoftwareId(@Param("softwareId") Long softwareId);
}
