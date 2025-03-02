<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Task</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <div class="add-task-container">
        <h1>Add a New Task</h1>

        <form action="/tasks/create" method="POST">
            <!-- Task Name -->
            <label for="taskName">Task Name</label>
            <input
                id="taskName"
                type="text"
                name="taskName"
            />
            
            <label for="description">Description</label>
            <textarea id="description" name="description"></textarea>

            <!-- Date -->
            <label for="date">Date</label>
            <input
                id="date"
                type="date"
                name="date"
            />

            <button type="submit">Add Task</button>
        </form>

        <p>
            <a href="/dashboard" class="dashboard-btn">Back to Dashboard</a>
        </p>
    </div>
</body>
</html>
