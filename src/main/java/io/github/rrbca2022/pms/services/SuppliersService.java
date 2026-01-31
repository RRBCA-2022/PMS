package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.Supplier;
import io.github.rrbca2022.pms.repository.SuppliersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SuppliersService {
    private final SuppliersRepository suppliersRepository;

    public List<Supplier> getAllSuppliers() {
        return suppliersRepository.findAll();
    }

    public Supplier getSupplierById(String id) {
        return suppliersRepository.findById(id).orElse(null);
    }

    public Supplier saveSupplier(Supplier supplier) {
        return suppliersRepository.save(supplier);
    }

    public void deleteSupplier(String id) {
        suppliersRepository.deleteById(id);
    }

    public boolean existsById(String id) {
        return suppliersRepository.existsById(id);
    }
}

