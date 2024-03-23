package com.example.application.data;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DataGenerator {
    @Autowired
    private VehicleRepo repo;
    @PostConstruct
    public void generateData() {
        Vehicle vehicle1 = new Vehicle("nissan", "PR4444RD", Type.CAR, 5,
                LocalDateTime.of(2023, 12, 12, 15, 25),
                LocalDateTime.of(2023, 11, 12, 15, 25));
        repo.save(vehicle1);
        Vehicle vehicle2 = new Vehicle("mazda", "PA4444RD", Type.CAR, 2,
                LocalDateTime.of(2023, 12, 12, 15, 25),
                LocalDateTime.of(2024, 1, 12, 15, 25));
        repo.save(vehicle2);
    }

}
