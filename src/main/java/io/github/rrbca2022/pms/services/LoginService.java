package io.github.rrbca2022.pms.services;

import io.github.rrbca2022.pms.entity.User;
import io.github.rrbca2022.pms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor()
public class LoginService {
	private final UserRepository userRepository;

	public User authenticate(String username, String password) {

		Optional<User> opt = userRepository.findByUsername(username);
		if (opt.isEmpty()) return null;

		User user = opt.get();

		if (!user.getPassword().equals(password)) {
			return null;
		}

		return user;
	}

}
