package com.humanbooster.springexam.controllers;


import com.humanbooster.springexam.entities.User;
import com.humanbooster.springexam.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    // POST /users - Créer un utilisateur
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }
    
    // GET /users/{id} - Afficher un utilisateur
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    // GET /users/{id}/projects - Projets créés par l'utilisateur
    @GetMapping("/{id}/projects")
    public ResponseEntity<List<com.humanbooster.springexam.entities.Project>> getUserProjects(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getProjects());
        }
        return ResponseEntity.notFound().build();
    }
    
    // GET /users/{id}/tasks - Tâches assignées à l'utilisateur
    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<com.humanbooster.springexam.entities.Task>> getUserTasks(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get().getTasks());
        }
        return ResponseEntity.notFound().build();
    }
}