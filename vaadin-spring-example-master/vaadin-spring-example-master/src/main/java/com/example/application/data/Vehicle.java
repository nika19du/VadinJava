package com.example.application.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
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
    private String model;
    private String registration;
    private Type type;
    private int entryCount;
    private LocalDateTime lastStayIn;
    private LocalDateTime lastStayOut;

    public boolean isParked() {
        return (lastStayOut == null) || lastStayIn.isAfter(lastStayOut);
    }
}

