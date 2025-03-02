package com.johnny.dailyguide.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.johnny.dailyguide.models.Task;
import com.johnny.dailyguide.models.User;
import com.johnny.dailyguide.repositories.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepo;
    
    public Task createTask(Task task) {
        return taskRepo.save(task);
    }
    
    public Task findTask(Long id) {
        return taskRepo.findById(id).orElse(null);
    }
    
    public Task updateTask(Task task) {
        return taskRepo.save(task);
    }
    
    public void deleteTask(Long id) {
        taskRepo.deleteById(id);
    }
    
    public List<Task> allTasks() {
        return (List<Task>) taskRepo.findAll();
    }
    
    public List<Task> tasksForUser(User user) {
        return taskRepo.findByUser(user);
    }
}
