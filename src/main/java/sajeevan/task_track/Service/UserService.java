package sajeevan.task_track.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sajeevan.task_track.Entity.User;
import sajeevan.task_track.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JWTService jwtService;

    private final org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder(
            12);

    private static final java.util.regex.Pattern EMAIL_PATTERN = java.util.regex.Pattern
            .compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public String registerUser(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isBlank()) {
            return "User name is invalid";
        }

        if (user.getEmail() == null || user.getEmail().isBlank()
                || !EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            return "Enter a valid email";
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return "No password found, enter a password to proceed";
        }

        if (userRepo.findByUsername(user.getUsername()) != null) {
            return "Username already exists";
        }

        if (userRepo.findByEmail(user.getEmail()) != null) {
            return "Email already exists";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return "SUCCESS";
    }

    public String verifyUser(User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isBlank()) {
            return "User name is invalid";
        }

        User existing = userRepo.findByUsername(user.getUsername());
        if (existing == null) {
            return "User name is invalid";
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            return "Invalid password";
        }

        if (!encoder.matches(user.getPassword(), existing.getPassword())) {
            return "Invalid password";
        }

        return jwtService.generateToken(existing.getUsername(),existing.getId());
    }

}
