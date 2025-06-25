package com.humanbooster.springexam.controllers;

import com.humanbooster.springexam.entities.Project;
import com.humanbooster.springexam.entities.Task;
import com.humanbooster.springexam.entities.User;
import com.humanbooster.springexam.repositories.ProjectRepository;
import com.humanbooster.springexam.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    // POST /projects - Créer un projet (avec ID du créateur)
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("name");
        Long creatorId = Long.valueOf(payload.get("creatorId").toString());
        
        Optional<User> creator = userRepository.findById(creatorId);
        if (creator.isPresent()) {
            Project project = new Project(name, creator.get());
            Project savedProject = projectRepository.save(project);
            return ResponseEntity.ok(savedProject);
        }
        return ResponseEntity.badRequest().build();
    }
    
    // GET /projects/{id}/tasks - Lister les tâches d'un projet
    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<Task>> getProjectTasks(@PathVariable Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return ResponseEntity.ok(project.get().getTasks());
        }
        return ResponseEntity.notFound().build();
    }
    
    // GET /projects/{id} - Détails d'un projet avec ses tâches
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
}