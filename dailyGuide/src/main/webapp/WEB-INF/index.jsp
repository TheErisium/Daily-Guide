<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login & Register</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>

    <h1 class="auth-header">Daily Guide</h1>
    
    <div class="auth-container">
    
    <div class="register-container">
        <h2>Register</h2>
        <form action="/register" method="POST">
        
            <label for="firstName">First Name</label>
            <input id="firstName" type="text" name="firstName" />
            <c:if test="${not empty firstNameError}">
                <p class="error-msg"><c:out value="${firstNameError}" /></p>
            </c:if>
            
            <label for="email">Email</label>
            <input id="email" type="text" name="email" />
            <c:if test="${not empty emailError}">
                <p class="error-msg"><c:out value="${emailError}" /></p>
            </c:if>
            
            <label for="password">Password</label>
            <input id="password" type="password" name="password" />
            <c:if test="${not empty passwordError}">
                <p class="error-msg"><c:out value="${passwordError}" /></p>
            </c:if>
            
            <label for="confirmPassword">Confirm Password</label>
            <input id="confirmPassword" type="password" name="confirmPassword" />
            <c:if test="${not empty confirmPasswordError}">
                <p class="error-msg"><c:out value="${confirmPasswordError}" /></p>
            </c:if>
            
            <c:if test="${not empty passwordMismatchError}">
                <p class="error-msg"><c:out value="${passwordMismatchError}" /></p>
            </c:if>
            <c:if test="${not empty passwordLengthError}">
                <p class="error-msg"><c:out value="${passwordLengthError}" /></p>
            </c:if>
            <c:if test="${not empty emailExistsError}">
                <p class="error-msg"><c:out value="${emailExistsError}" /></p>
            </c:if>

            <button type="submit">Register</button>
        </form>
    </div>
    
    <div class="login-container">
        <h2>Login</h2>
        <form action="/login" method="POST">
        
            <label for="loginEmail">Email</label>
            <input id="loginEmail" type="text" name="email" />
            <c:if test="${not empty loginEmailError}">
                <p class="error-msg"><c:out value="${loginEmailError}" /></p>
            </c:if>
            
            <label for="loginPassword">Password</label>
            <input id="loginPassword" type="password" name="password" />
            <c:if test="${not empty loginPasswordError}">
                <p class="error-msg"><c:out value="${loginPasswordError}" /></p>
            </c:if>
            
            <c:if test="${not empty loginError}">
                <p class="error-msg"><c:out value="${loginError}" /></p>
            </c:if>

            <button type="submit">Login</button>
        </form>
    </div>
</body>
</html>
