package com.duc82.finderapi.users;

import com.duc82.finderapi.dtos.MessageResponse;
import com.duc82.finderapi.users.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping()
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/me")
    public User me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(authentication.getName());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.delete(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/restore/{id}")
    public ResponseEntity<MessageResponse> restore(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.restore(id));
    }

}
