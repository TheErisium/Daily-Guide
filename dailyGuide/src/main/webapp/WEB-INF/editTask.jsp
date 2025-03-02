<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Task</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <div class="edit-task-container">
        <h1>Edit Task</h1>

        <form action="/tasks/${task.id}/update" method="POST">
        
            <label for="taskName">Task Name</label>
            <input
                id="taskName"
                type="text"
                name="taskName"
                value="<c:out value='${task.taskName}'/>"
            />
            
            <label for="description">Description</label>
            <textarea
                id="description"
                name="description"
            ><c:out value="${task.description}" /></textarea>
            
            <label for="date">Date</label>
            <input
                id="date"
                type="date"
                name="date"
                value="<c:out value='${task.date}'/>"
            />
            
            <button type="submit">Update Task</button>
        </form>
        
        <p>
            <a href="/dashboard" class="dashboard-btn">Back to Dashboard</a>
        </p>
    </div>
</body>
</html>
