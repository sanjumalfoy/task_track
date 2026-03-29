package sajeevan.task_track.Controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

import sajeevan.task_track.Entity.User;
import sajeevan.task_track.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody User user) {
        System.out.println("Register __________ API_________________________ HIT");

        String result = userService.registerUser(user);
        if (!"SUCCESS".equalsIgnoreCase(result)) {
            return ResponseEntity.badRequest().body(Map.of("message", result));
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody User user) {
        System.out.println("LOGIN____________________ API______________________ HIT");
        String result = userService.verifyUser(user);

        if ("User name is invalid".equalsIgnoreCase(result) || "Invalid password".equalsIgnoreCase(result)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", result));
        }

        return ResponseEntity.ok(Map.of("token", result));
    }

}
