package com.example.application.services;

import com.example.application.data.Vehicle; // Import the Vehicle class if necessary
import com.example.application.data.VehicleRepo; // Import the VehicleRepo interface

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {
    private final VehicleRepo repository;

    public VehicleService(VehicleRepo repository) {
        this.repository = repository;
    }

    public Vehicle add(Vehicle entity) {
        return repository.save(entity);
    }

    public Optional<Vehicle> get(Long id) {
        return repository.findById(id);
    }

    public Vehicle update(Vehicle entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Vehicle> getAllVehicles(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }
}
