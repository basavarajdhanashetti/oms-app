<!DOCTYPE html>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <jsp:include page="styleAndCss.jsp"></jsp:include>

</head>

<body>

    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
                        
                        <form:form commandName="loginForm">
							<fieldset>
								<div class="form-group">
									<form:input path="userName" class="form-control" placeholder="Login Name" id="userName"/>	&nbsp; <form:errors path="userName" cssClass="errorMsg"/>
								</div>
								<div class="form-group">
									<form:password path="password" class="form-control" placeholder="Password"/> 	&nbsp; <form:errors path="password" cssClass="errorMsg"/>		
								</div>
								<!-- Change this to a button or input when using this as a form -->
								<button type="submit" formaction="login" formmethod="post" class="btn btn-lg btn-success btn-block" id="loginBtn">
									Login
								</button>
								<c:if test="${errorMsg !=null || errorMsg !=''}">
									<div class="form-group, error"><font color="red">
										${errorMsg}</font>
									</div>
								</c:if>
							</fieldset>
						</form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>


</body>

</html>
