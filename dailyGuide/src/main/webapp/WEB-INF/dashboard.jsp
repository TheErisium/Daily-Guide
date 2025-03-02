<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <div class="dashboard-container">
        <h1>Welcome, <c:out value="${user.firstName}" />!</h1>
        <a href="/logout" class="logout-btn">Logout</a>
        
        <div style="clear: both;"></div>
        
        <p>
            <a href="/tasks/new" class="btn">Add New Task</a>
        </p>
        
        <table class="task-table">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Task</th>
                    <th class="comp-column">Completed</th>
                    <th class="action-column">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="task" items="${tasks}">
                    <tr>
                    
                        <td><c:out value="${task.date}" /></td>

                        <td><c:out value="${task.taskName}" /></td>

                        <td>
                            <c:choose>
                                <c:when test="${task.completed}">Yes</c:when>
                                <c:otherwise>No</c:otherwise>
                            </c:choose>
                        </td>

                        <td>
                            <a class="dash-btn" href="/tasks/${task.id}">View</a>
                            <a class="dash-btn" href="/tasks/${task.id}/edit">Edit</a>
                            <a class="delete-btn" href="/tasks/${task.id}/delete">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
