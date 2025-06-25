package com.humanbooster.springexam.controllers;

import com.humanbooster.springexam.entities.Project;
import com.humanbooster.springexam.entities.Task;
import com.humanbooster.springexam.enums.TaskStatus;
import com.humanbooster.springexam.entities.User;
import com.humanbooster.springexam.repositories.ProjectRepository;
import com.humanbooster.springexam.repositories.TaskRepository;
import com.humanbooster.springexam.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    // POST /tasks - Créer une tâche (avec ID du projet et de l'utilisateur assigné)
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Map<String, Object> payload) {
        String title = (String) payload.get("title");
        Long projectId = Long.valueOf(payload.get("projectId").toString());
        Long assigneeId = Long.valueOf(payload.get("assigneeId").toString());
        
        Optional<Project> project = projectRepository.findById(projectId);
        Optional<User> assignee = userRepository.findById(assigneeId);
        
        if (project.isPresent() && assignee.isPresent()) {
            Task task = new Task(title, project.get(), assignee.get());
            Task savedTask = taskRepository.save(task);
            return ResponseEntity.ok(savedTask);
        }
        return ResponseEntity.badRequest().build();
    }
    
    // PATCH /tasks/{id} - Modifier le statut d'une tâche
    @PatchMapping("/{id}")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            String statusStr = payload.get("status");
            
            try {
                TaskStatus status = TaskStatus.valueOf(statusStr);
                task.setStatus(status);
                Task updatedTask = taskRepository.save(task);
                return ResponseEntity.ok(updatedTask);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
    

}