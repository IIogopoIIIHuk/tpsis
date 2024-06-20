package com.example.ITired.Services;

import com.example.ITired.Role;
import com.example.ITired.User;
import com.example.ITired.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("Deleted user with id = {}", id);
    }

    public boolean createUser (User user) {
        String username = user.getUsername();
        if (userRepository.findByUsername(username) != null) return false;//такой user присутствует - ошибка значит
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        user.setName(user.getName());
        log.info("Saving new User with username: {}", username);
        userRepository.save(user);
        return true;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public List<User> list() {
        return userRepository.findAll();
    }


    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if(user.isActive()) {
                user.setActive(false);
                log.info("Ban user with id = {}; username: {}", user.getId(), user.getUsername());
            } else {
                user.setActive(true);
                log.info("Unban user with id = {}; username: {}", user.getId(), user.getUsername());
            }
        }
        userRepository.save(user);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findByUsername(username);
        }

        return null;
    }


    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


    public List<User> findAllUsers() {
        return userRepository.findByRoles(Role.ROLE_USER);
    }
}
