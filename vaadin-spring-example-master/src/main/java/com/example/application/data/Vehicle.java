package com.example.application.data;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vehicle extends AbstractEntity implements Serializable {
    public Vehicle(String registrationNumber, String model, Type type) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.type = type;
    }
    public Vehicle(String model, String registrationNumber, Type type, int entryCount, LocalDateTime lastStayIn,
                   LocalDateTime lastStayOut) {
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.type = type;
        this.entryCount = entryCount;
        this.lastStayIn = lastStayIn;
        this.lastStayOut = lastStayOut;
        this.isInParking=isInParking;
    }
    public Vehicle(String model, String registrationNumber, Type type, int entryCount, LocalDateTime lastStayIn,
                   LocalDateTime lastStayOut, boolean isInParking) {
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.type = type;
        this.entryCount = entryCount;
        this.lastStayIn = lastStayIn;
        this.lastStayOut = lastStayOut;
        this.isInParking=isInParking;
    }
    private boolean isInParking;
    private Type type;
    private int entryCount;
    private LocalDateTime lastStayIn;
    private LocalDateTime lastStayOut;

    public boolean isParked() {
        return (lastStayOut == null) || lastStayIn.isAfter(lastStayOut);
    }
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    private String registrationNumber;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(int entryCount) {
        this.entryCount = entryCount;
    }

    public LocalDateTime getLastStayIn() {
        return lastStayIn;
    }

    public void setLastStayIn(LocalDateTime lastStayIn) {
        this.lastStayIn = lastStayIn;
    }

    public LocalDateTime getLastStayOut() {
        return lastStayOut;
    }

    public void setLastStayOut(LocalDateTime lastStayOut) {
        this.lastStayOut = lastStayOut;
    }


}

