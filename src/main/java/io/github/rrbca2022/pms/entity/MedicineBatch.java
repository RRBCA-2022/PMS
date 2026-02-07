package io.github.rrbca2022.pms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // If manufacturer batch is missing, this can store a System-ID
    @Column(nullable = false)
    private String batchNumber;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    private Integer stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    @JsonBackReference
    private Medicine medicine;

    /**
     * Helper method to handle missing batch numbers automatically
     * before saving to the database.
     */
    @PrePersist
    public void ensureBatchNumber() {
        if (this.batchNumber == null || this.batchNumber.isEmpty()) {
            // Generates a fallback like SYS-20260131-12345
            this.batchNumber = "SYS-" + System.currentTimeMillis();
        }
    }
}