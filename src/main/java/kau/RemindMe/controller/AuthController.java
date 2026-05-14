package kau.RemindMe.controller;

import kau.RemindMe.model.User;
import kau.RemindMe.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public Map<String, String> signup(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return Map.of("status", "error", "message", "Email already exists!");
        }
        userRepository.save(user);
        return Map.of("status", "success", "message", "Signup successful!");
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return Map.of("status", "success", "user", userOpt.get());
        }
        return Map.of("status", "error", "message", "Invalid email or password");
    }
}