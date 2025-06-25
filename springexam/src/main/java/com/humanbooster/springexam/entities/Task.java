package com.humanbooster.springexam.entities;

import com.humanbooster.springexam.enums.TaskStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.TODO;
    
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;
    

    public Task() {}
    
    public Task(String title, Project project, User assignee) {
        this.title = title;
        this.project = project;
        this.assignee = assignee;
        this.status = TaskStatus.TODO;
    }
    

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
    
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
    
    public User getAssignee() { return assignee; }
    public void setAssignee(User assignee) { this.assignee = assignee; }
}