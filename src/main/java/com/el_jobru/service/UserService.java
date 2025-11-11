package com.el_jobru.service;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.dto.auth.LoginDTO;
import com.el_jobru.dto.auth.RegisterDTO;
import com.el_jobru.dto.profile.ProfileResponseDTO;
import com.el_jobru.models.book.Book;
import com.el_jobru.models.user.*;
import com.el_jobru.repository.UserRepository;
import io.javalin.http.NotFoundResponse;
import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;

import java.util.Optional;

public class UserService {

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

    public ProfileResponseDTO getUserProfile(User user) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            User managedUser = em.merge(user);

            Hibernate.initialize(managedUser.getBooks());

            return new ProfileResponseDTO(managedUser);
        }
    }

    public ProfileResponseDTO addBookToUser(User user, long bookId) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            em.getTransaction().begin();

            User managedUser = em.merge(user);

            Book book = em.find(Book.class, bookId);
            if (book == null) {
                em.getTransaction().rollback();
                throw new NotFoundResponse("Livro não encontrado");
            }

            managedUser.addBook(book);

            em.getTransaction().commit();

            return new ProfileResponseDTO(managedUser);
        }
    }
}
