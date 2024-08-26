package com.dennis.web.security.controller;

import com.dennis.web.security.model.User;
import org.owasp.encoder.Encode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/api/users")
public class UserController {

    private List<User> users = new ArrayList<>();

    @GetMapping("/home")
    public String hello() {
        return "You are logged in! oAuth2";
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Validated @RequestBody User user) {
        users.add(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<String> getUserByEmail(@PathVariable("email") String email) {
        String safeEmail = Encode.forHtml(email);

        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(safeEmail)) {
                return new ResponseEntity<>("User found: " + Encode.forHtml(user.getName()), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }


}
