package io.github.rrbca2022.pms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class DemandHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Medicine medicine;

    private LocalDate periodDate;
    private int actualSales;     // Dt
    private double forecastedValue; // Ft
    private double error;        // Actual - Forecast
    private Double alphaUsed; // The alpha setting at the time of calculation

    @PrePersist
    @PreUpdate
    public void calculateMetrics() {
        this.error = (double) actualSales - forecastedValue;
    }
}