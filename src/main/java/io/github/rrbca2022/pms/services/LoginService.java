package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.User;
import io.github.rrbca2022.pms.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LoginService {

	private final UserRepository userRepository;

	public LoginService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public boolean authenticate(String username, String password) {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isEmpty()) return false;
		else return user.get().getPassword().equals(password);
	}
}
