package com.el_jobru.service;

import com.el_jobru.dto.LoginDTO;
import com.el_jobru.dto.RegisterDTO;
import com.el_jobru.models.User;
import com.el_jobru.models.UserRole;
import com.el_jobru.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class UserService {

    // TODO -> Adicionar validações e regras de negócio na camada Service/DAO

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(RegisterDTO registerDTO) throws Exception {
        if (userRepository.findByEmail(registerDTO.email()).isPresent()) {
            throw new Exception("Email já cadastrado");
        }

        String hashedPassword = BCrypt.hashpw(registerDTO.password(), BCrypt.gensalt());

        User newUser = new User();
        newUser.setName(registerDTO.name());
        newUser.setEmail(registerDTO.email());
        newUser.setPassword(hashedPassword);
        newUser.setRole(UserRole.USER);
        newUser.setAge(registerDTO.age());

        return userRepository.save(newUser);
    }

    public Optional<User> validateLogin(LoginDTO loginDTO) {
        Optional<User> userOptional = userRepository.findByEmail(loginDTO.email());

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        if (BCrypt.checkpw(loginDTO.password(), user.getPassword())) {
            return Optional.of(user);
        }

        return Optional.empty();
    }
}
