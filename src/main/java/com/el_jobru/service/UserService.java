package com.el_jobru.service;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.dto.auth.LoginDTO;
import com.el_jobru.dto.auth.RegisterDTO;
import com.el_jobru.dto.mission.MissionDTO;
import com.el_jobru.dto.profile.ProfileResponseDTO;
import com.el_jobru.models.book.Book;
import com.el_jobru.models.level.Level;
import com.el_jobru.models.mission.Mission;
import com.el_jobru.models.user.*;
import com.el_jobru.repository.LevelRepository;
import com.el_jobru.repository.MissionRepository;
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
        Long exp = (long) 0;

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

        LevelRepository repo = new LevelRepository();
        Optional<Level> optLvl = repo.findById(1, Level.class);
        Level lvlOne =  optLvl.orElseThrow(() -> new RuntimeException("Erro ao buscar primeiro nível."));
        User newUser = new User(age, registerDTO.name(), exp, email, password, UserRole.USER, lvlOne);

        return userRepository.saveOrUpdate(newUser);
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

    public ProfileResponseDTO claimMission(MissionDTO missionDTO, User user) {
        MissionRepository missionRepository = new MissionRepository();
        Mission mission = missionRepository.findByTitle(missionDTO.title());

        mission.setStatus(true);
        missionRepository.saveOrUpdate(mission);

        Long newExp = user.getExp() + missionDTO.reward();
        user.setExp(newExp);
        user = this.lvlUp(user);

        return new ProfileResponseDTO(
                userRepository.saveOrUpdate(user)
        );
    }

    public User lvlUp(User user){
        if(user.getExp() <= user.getLvl().getMaxXp()) { return user; }

        LevelRepository repo =  new LevelRepository();
        Optional<Level> optLvl = repo.findById(user.getLvl().getId()+1, Level.class);
        Level newLvl = optLvl.orElseThrow(() -> new RuntimeException("Nível não existe."));

        if(newLvl != null) {
            user.setLvl(newLvl);
        }
        else {
            System.out.println("Nível máximo atingido.");
        }

        return user;
    }
}
