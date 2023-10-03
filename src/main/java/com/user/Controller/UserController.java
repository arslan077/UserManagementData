package com.user.Controller;

import com.user.DTO.UserRequest;
import com.user.Entity.User;
import com.user.Repository.UserRepository;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserRepository userRepository;
//
//    @Autowired
//    private RestTemplate restTemplate;
//    Constants constants=new Constants();

    private static final Logger log = LoggerFactory.getLogger(UserController.class);



    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Long userId) {
        User user = userRepository.findByUserId(userId);

        return user;

    }

    @GetMapping("/AllUsers")
    public List<User> getAllUser() {
        List<User> user = userRepository.findAll();
        return user;

    }

    @PostMapping("/NewUser")
    public ResponseEntity<?> createroles(@Valid @RequestBody UserRequest userRequest) {

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPhone(userRequest.getPhone());
        user = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/verifyUser/{userId}")
    public ResponseEntity<Map<String, String>> verifyUser(@PathVariable Long userId) {
        try {
            User user = userRepository.findByUserId(userId);

            if (user != null) {
                log.info("User with ID {} exists.", userId);
                Map<String, String> response = new HashMap<>();
                response.put("message", "User exists");
                return ResponseEntity.ok(response);
            } else {
                log.info("User with ID {} does not exist.", userId);
                Map<String, String> response = new HashMap<>();
                response.put("message", "User does not exist");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception ex) {
            log.error("Error while verifying user with ID {}", userId, ex);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}



