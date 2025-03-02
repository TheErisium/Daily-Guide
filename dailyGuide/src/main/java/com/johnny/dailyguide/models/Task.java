package com.johnny.dailyguide.models;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.*;

import com.johnny.dailyguide.models.User;

@Entity
@Table(name="tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskName;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean completed = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
   
    public Task() {}
    
    public Task(String taskName, LocalDate date, String description, boolean completed, User user) {
        this.taskName = taskName;
        this.date = date;
        this.description = description;
        this.completed = completed;
        this.user = user;
    }
    
    public Long getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
