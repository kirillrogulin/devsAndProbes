<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.itstep.entities.*,java.sql.*,java.util.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<title>Select devices</title>
</head>
<body>
	<%
		List<Device> devices = (List<Device>) request.getAttribute("devices");
	%>
	<h2>Select two devices:</h2>
	<br>
	<form action="" method="POST">
		<div>Source device:</div>
		<select name="source">
			<c:forEach items="${devices}" var="dev">
				<option value="${dev.id}">${dev.name} (port ${dev.port})</option>
			</c:forEach>
		</select>
		<br><br>
		<div>Destination device:</div>
		<select name="destination">
			<c:forEach items="${devices}" var="dev">
				<option value="${dev.id}">${dev.name} (port ${dev.port})</option>
			</c:forEach>
		</select>
		<br><br>
		<div>Applications:</div>
		<select name="app">
			<option>Skype</option>
			<option>Viber</option>
			<option>IMO</option>
		</select>
		<br><br>
		<div>Probe types:</div>
		<select name="probe_type">
			<option>jitter</option>
			<option>lose</option>
			<option>delay</option>
		</select>
		<br><hr>
		<button type="submit">Create probe</button>
	</form>
	<br><hr>
	<a href="showAll">Show all probes</a>
</body>
</html>