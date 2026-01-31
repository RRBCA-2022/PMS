package io.github.rrbca2022.pms.repository;

import io.github.rrbca2022.pms.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuppliersRepository extends JpaRepository<Supplier, String> {

}
