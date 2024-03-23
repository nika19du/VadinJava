package com.example.application.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleRepo extends JpaRepository<Vehicle, Long> {
    Page<Vehicle> findAll(Pageable pageable);
}
