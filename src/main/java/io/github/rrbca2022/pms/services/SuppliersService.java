package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.Supplier;
import io.github.rrbca2022.pms.repository.SuppliersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuppliersService {

    private final SuppliersRepository suppliersRepository;

    public SuppliersService(SuppliersRepository suppliersRepository){this.suppliersRepository = suppliersRepository;}

    public List<Supplier> getAllSuppliers(){
        return suppliersRepository.findAll();
    }
    public Supplier getSupplierById(Long id){
        return suppliersRepository.findById(id).orElse(null);
    }
    public void saveSupplier(Supplier supplier){
        suppliersRepository.save(supplier);
    }
    public void  deleteSupplier(Long id){
        suppliersRepository.deleteById(id);
    }





}
