package com.johnny.dailyguide.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.johnny.dailyguide.models.User;
import com.johnny.dailyguide.models.Task;
import com.johnny.dailyguide.services.UserService;
import com.johnny.dailyguide.services.TaskService;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/register")
    public String register(
        @RequestParam(value = "firstName", required = false) String firstName,
        @RequestParam(value = "email", required = false) String email,
        @RequestParam(value = "password", required = false) String password,
        @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
        Model model,
        HttpSession session
    ) {
        boolean hasErrors = false;

        if (firstName == null || firstName.isBlank()) {
            model.addAttribute("firstNameError", "First name is required!");
            hasErrors = true;
        }
        if (email == null || email.isBlank()) {
            model.addAttribute("emailError", "Email is required!");
            hasErrors = true;
        }
        if (password == null || password.isBlank()) {
            model.addAttribute("passwordError", "Password is required!");
            hasErrors = true;
        }
        if (confirmPassword == null || confirmPassword.isBlank()) {
            model.addAttribute("confirmPasswordError", "Confirm Password is required!");
            hasErrors = true;
        }
        if (password != null && password.length() < 8) {
            model.addAttribute("passwordLengthError", "Password must be at least 8 characters long!");
            hasErrors = true;
        }
        if (password != null && confirmPassword != null && !password.equals(confirmPassword)) {
            model.addAttribute("passwordMismatchError", "Passwords do not match!");
            hasErrors = true;
        }
        if (email != null && userService.findByEmail(email) != null) {
            model.addAttribute("emailExistsError", "Email is already in use!");
            hasErrors = true;
        }
        if (hasErrors) {
            return "index";
        }

        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setEmail(email);
        newUser.setPassword(password);

        userService.registerUser(newUser);
        session.setAttribute("userId", newUser.getId());

        return "redirect:/dashboard";
    }

    @PostMapping("/login")
    public String login(
        @RequestParam(value = "email", required = false) String email,
        @RequestParam(value = "password", required = false) String password,
        Model model,
        HttpSession session
    ) {
        boolean hasErrors = false;

        if (email == null || email.isBlank()) {
            model.addAttribute("loginEmailError", "Email is required!");
            hasErrors = true;
        }
        if (password == null || password.isBlank()) {
            model.addAttribute("loginPasswordError", "Password is required!");
            hasErrors = true;
        }
        if (hasErrors) {
            return "index";
        }

        boolean isAuth = userService.authenticateUser(email, password);
        if (isAuth) {
            User user = userService.findByEmail(email);
            session.setAttribute("userId", user.getId());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("loginError", "Invalid credentials. Please try again.");
            return "index";
        }
    }
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        User loggedUser = userService.findById(userId);
        if (loggedUser == null) {
            session.invalidate();
            return "redirect:/";
        }
        
        List<Task> userTasks = taskService.tasksForUser(loggedUser);
        model.addAttribute("tasks", userTasks);
        model.addAttribute("user", loggedUser);

        return "dashboard";
    }
    
    @GetMapping("/tasks/new")
    public String newTask(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        model.addAttribute("task", new Task());
        return "add";
    }
    
    @PostMapping("/tasks/create")
    public String createTask(
        @RequestParam("taskName") String taskName,
        @RequestParam("description") String description,
        @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
        HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }
        
        User currentUser = userService.findById(userId);
        if (currentUser == null) {
            return "redirect:/";
        }
        
        Task newTask = new Task();
        newTask.setTaskName(taskName);
        newTask.setDescription(description);
        newTask.setDate(date);
        newTask.setCompleted(false);
        newTask.setUser(currentUser);

        taskService.createTask(newTask);
        return "redirect:/dashboard";
    }
    
    @GetMapping("/tasks/{id}")
    public String showTask(
        @PathVariable("id") Long taskId,
        Model model,
        HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        Task task = taskService.findTask(taskId);
        if (task == null) {
            return "redirect:/dashboard";
        }

        model.addAttribute("task", task);
        return "viewTask";
    }
    
    @PostMapping("/tasks/{id}/complete")
    public String completeTask(
        @PathVariable("id") Long taskId,
        @RequestParam(value="isComplete", required=false) String isComplete,
        HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        Task task = taskService.findTask(taskId);
        if (task == null) {
            return "redirect:/dashboard";
        }

        boolean completed = (isComplete != null && isComplete.equals("true"));
        task.setCompleted(completed);
        taskService.updateTask(task);

        return "redirect:/dashboard";
    }
    
    @GetMapping("/tasks/{id}/edit")
    public String editTask(
        @PathVariable("id") Long taskId,
        HttpSession session,
        Model model
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        Task existingTask = taskService.findTask(taskId);
        if (existingTask == null) {
            return "redirect:/dashboard";
        }

        model.addAttribute("task", existingTask);
        return "editTask";
    }
    
    @GetMapping("/tasks/{id}/delete")
    public String deleteTask(
        @PathVariable("id") Long taskId,
        HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        Task task = taskService.findTask(taskId);
        if (task != null) {
            taskService.deleteTask(taskId);
        }

        return "redirect:/dashboard";
    }
    
    @PostMapping("/tasks/{id}/update")
    public String updateTask(
        @PathVariable("id") Long taskId,
        @RequestParam("taskName") String taskName,
        @RequestParam("description") String description,
        @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
        HttpSession session
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/";
        }

        Task existingTask = taskService.findTask(taskId);
        if (existingTask == null) {
            return "redirect:/dashboard";
        }

        existingTask.setTaskName(taskName);
        existingTask.setDescription(description);
        existingTask.setDate(date);

        taskService.updateTask(existingTask);
        return "redirect:/dashboard";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}