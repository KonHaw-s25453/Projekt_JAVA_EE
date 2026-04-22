package com.asms.service;

import com.asms.dto.ComputerDTO;
import com.asms.entity.Computer;
import com.asms.entity.OperatingSystem;
import com.asms.exception.ResourceNotFoundException;
import com.asms.repository.ComputerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Transactional
public class ComputerService {

    private final ComputerRepository computerRepository;

    public Computer create(ComputerDTO dto) {
        Computer computer = Computer.builder()
                .name(dto.getName())
                .serialNumber(dto.getSerialNumber())
                .model(dto.getModel())
                .location(dto.getLocation())
                .status(dto.getStatus() != null ? dto.getStatus() : "ACTIVE")
                .build();
        if (dto.getOsId() != null) {
            OperatingSystem os = new OperatingSystem();
            os.setOsId(dto.getOsId());
            computer.setOperatingSystem(os);
        }
        return computerRepository.save(computer);
    }

    @Transactional(readOnly = true)
    public Computer getById(Long id) {
        return computerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Computer", "id", id));
    }

    @Transactional(readOnly = true)
    public List<Computer> getAll() {
        return computerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Computer> findByLocation(String location) {
        return computerRepository.findByLocation(location);
    }

    @Transactional(readOnly = true)
    public List<Computer> findByStatus(String status) {
        return computerRepository.findByStatus(status);
    }

    public Computer update(Long id, ComputerDTO dto) {
        Computer computer = getById(id);
        computer.setName(dto.getName());
        computer.setSerialNumber(dto.getSerialNumber());
        computer.setModel(dto.getModel());
        computer.setLocation(dto.getLocation());
        computer.setStatus(dto.getStatus());
        return computerRepository.save(computer);
    }

    public void delete(Long id) {
        Computer computer = getById(id);
        computerRepository.delete(computer);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", computerRepository.count());
        stats.put("active", computerRepository.findByStatus("ACTIVE").size());
        stats.put("inactive", computerRepository.findByStatus("INACTIVE").size());
        return stats;
    }
}
