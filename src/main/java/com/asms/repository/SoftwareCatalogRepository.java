package com.asms.repository;

import com.asms.entity.SoftwareCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SoftwareCatalogRepository extends JpaRepository<SoftwareCatalog, Long> {
    List<SoftwareCatalog> findByNameContainingIgnoreCase(String name);
    List<SoftwareCatalog> findByIsActiveTrue();
}
