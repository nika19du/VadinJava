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
        Vehicle vehicle3 = new Vehicle("Toyota", "AX2222BX", Type.CAR, 4,
                LocalDateTime.of(2022, 10, 20, 12, 30),
                LocalDateTime.of(2023, 10, 20, 12, 30));
        repo.save(vehicle3);

        Vehicle vehicle4 = new Vehicle("Ford", "KA1234CD", Type.CAR, 3,
                LocalDateTime.of(2022, 11, 15, 10, 15),
                LocalDateTime.of(2023, 11, 15, 10, 15));
        repo.save(vehicle4);

        Vehicle vehicle5 = new Vehicle("BMW", "BX5555FF", Type.CAR, 2,
                LocalDateTime.of(2022, 9, 5, 8, 45),
                LocalDateTime.of(2023, 9, 5, 8, 45));
        repo.save(vehicle5);
        Vehicle vehicle6 = new Vehicle("Citroen", "C3", Type.CAR, 2,
                LocalDateTime.of(2022, 9, 5, 8, 45),
                LocalDateTime.of(2023, 9, 5, 8, 45));
        repo.save(vehicle6);
        Vehicle vehicle7 = new Vehicle("Mercedes", "G-Class", Type.CAR, 2,
                LocalDateTime.of(2022, 9, 5, 8, 45),
                LocalDateTime.of(2023, 9, 5, 8, 45));
        repo.save(vehicle7);
    }

}
