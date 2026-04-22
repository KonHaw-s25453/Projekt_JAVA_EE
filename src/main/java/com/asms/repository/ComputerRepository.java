package com.asms.repository;

import com.asms.entity.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {
    List<Computer> findByStatus(String status);
    List<Computer> findByLocation(String location);
    List<Computer> findByNameContainingIgnoreCase(String name);
    long countByStatus(String status);
}
