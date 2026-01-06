package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.User;
import io.github.rrbca2022.pms.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){this.userRepository=userRepository;}

    public List<User> getAllUsers(){return userRepository.findAll();}
    public User getUserById(Long id){return userRepository.findById(id).orElse(null);}
    public void saveUser(User user){userRepository.save(user);}
    public void deleteUser(Long id){userRepository.deleteById(id);}

}
