package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.Supplier;
import io.github.rrbca2022.pms.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository){this.supplierRepository=supplierRepository;}

    public List<Supplier> getAllSuppliers(){
        return supplierRepository.findAll();
    }
    public Supplier getSupplierById(Long id){
        return supplierRepository.findById(id).orElse(null);
    }
    public void saveSupplier(Supplier supplier){
        supplierRepository.save(supplier);
    }
    public void  deleteSupplier(Long id){
        supplierRepository.deleteById(id);
    }





}
