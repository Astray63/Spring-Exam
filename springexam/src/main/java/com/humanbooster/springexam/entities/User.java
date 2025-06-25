package com.humanbooster.springexam.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String username;
    
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Project> projects;
    
    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Task> tasks;
    

    public User() {}
    
    public User(String username) {
        this.username = username;
    }
    

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public List<Project> getProjects() { return projects; }
    public void setProjects(List<Project> projects) { this.projects = projects; }
    
    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }
}