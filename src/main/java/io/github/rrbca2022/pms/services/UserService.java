package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.AccountType;
import io.github.rrbca2022.pms.entity.User;
import io.github.rrbca2022.pms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor()
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers(){ return userRepository.findAll(); }
    public User getUserById(String id){ return userRepository.findById(id).orElse(null); }
    public User getUserByUsername(String username){ return userRepository.findByUsername(username).orElse(null); }
    public void saveUser(User user){ userRepository.save(user); }
    public void deleteUser(String id){ userRepository.deleteById(id); }

    public boolean findById(String id){ return getUserById(id) != null; }
    public boolean findByUsername(String username) { return getUserByUsername(username) != null; }

    public boolean adminExists() {
        return userRepository.existsByAccountType(AccountType.ADMIN);
    }
}
