<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Task</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <div class="view-task-container">
        <h1>Task Details</h1>

        <p>
            <strong>Task Name:</strong> <c:out value="${task.taskName}" />
        </p>
        <p>
            <strong>Description:</strong><br>
            <c:out value="${task.description}" />
        </p>
        <p>
            <strong>Date:</strong> <c:out value="${task.date}" />
        </p>

        <form action="/tasks/${task.id}/complete" method="POST">
            <label>
                <input
                    type="checkbox"
                    name="isComplete"
                    value="true"
                    <c:if test="${task.completed}">checked="checked"</c:if>
                />
                Mark Complete
            </label>
            <button type="submit">Update Status</button>
        </form>

        <p>
            <a href="/dashboard" class="dashboard-btn">Back to Dashboard</a>
        </p>
    </div>
</body>
</html>