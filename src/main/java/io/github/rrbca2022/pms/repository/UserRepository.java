package io.github.rrbca2022.pms.repository;

import io.github.rrbca2022.pms.entity.AccountType;
import io.github.rrbca2022.pms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean findByAccountType(AccountType accountType);

    boolean find
}