package io.github.rrbca2022.pms.repository;

import io.github.rrbca2022.pms.entity.AccountType;
import io.github.rrbca2022.pms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

    boolean findByAccountType(AccountType accountType);

    boolean existsByAccountType(AccountType accountType);

}