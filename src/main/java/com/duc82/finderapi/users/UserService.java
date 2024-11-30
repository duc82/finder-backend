package com.duc82.finderapi.users;

import com.duc82.finderapi.auth.dtos.SignUpRequest;
import com.duc82.finderapi.dtos.MessageResponse;
import com.duc82.finderapi.exceptions.NotFoundException;
import com.duc82.finderapi.users.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public void save(SignUpRequest signUpRequest) {
        User user = new User();
        user.setFullName(signUpRequest.getFullName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);
    }

    public MessageResponse delete(UUID id) {
        User user = userRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(() -> new NotFoundException("User not found"));

        user.softDelete();
        userRepository.save(user);

        return new MessageResponse("Deleted a user successfully");
    }

    public MessageResponse restore(UUID id) {
        User user = userRepository.findByIdAndDeletedAtIsNotNull(id).orElseThrow(() -> new NotFoundException("User not found"));

        user.restore();
        userRepository.save(user);

        return new MessageResponse("Restored a user successfully");
    }

}
