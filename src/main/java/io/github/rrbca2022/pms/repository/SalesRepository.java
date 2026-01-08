package io.github.rrbca2022.pms.repository;

import io.github.rrbca2022.pms.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sale ,Long> {
}
