package com.el_jobru.service;

import com.el_jobru.dto.LoginDTO;
import com.el_jobru.dto.RegisterDTO;
import com.el_jobru.models.user.*;
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

        Email email;
        Password password;
        Age age;

        try {

            email = new Email(registerDTO.email());
            password = Password.createPasswordFromPlainText(registerDTO.password());
            age = new Age(registerDTO.age());

        } catch (IllegalArgumentException e) {
            throw new Exception("Dados de registro inválidos: " + e.getMessage());
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new Exception("Email já cadastrado");
        }

        String hashedPassword = BCrypt.hashpw(registerDTO.password(), BCrypt.gensalt());

        User newUser = new User();
        newUser.setName(registerDTO.name());
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(UserRole.USER);
        newUser.setAge(age);

        return userRepository.save(newUser);
    }

    public Optional<User> validateLogin(LoginDTO loginDTO) {
        Email email;

        try {
            email = new Email(loginDTO.email());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return Optional.empty();
        }

        User user = userOptional.get();

        if (user.getPassword().matches(loginDTO.password())) {
            return Optional.of(user);
        }

        return Optional.empty();
    }
}
