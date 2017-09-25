<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.itstep.entities.*,java.sql.*,java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>All Probes view</title>
</head>
<body>
	<%
		List<AllProbesResult> allProbes = (List<AllProbesResult>) request.getAttribute("AllProbes");
	%>
	<h2>Select two devices:</h2>
	<hr>
	<table><thead><tr>
				<th>Probe Relation Id</th>
				<th>Probe Id</th>
				<th>Probe Application</th>
				<th>Probe Type</th>
				<th>Probe Source Device</th>
				<th>Probe Destination Device</th>
			</tr></thead>
		<tbody>
			<c:forEach items="${allProbes}" var="probe">
				<tr>
					<td>${probe.probeRel}</td>
					<td>${probe.probeId}</td>
					<td>${probe.probeApp}</td>
					<td>${probe.probeType}</td>
					<td>${probe.probeSourceDevName} (port ${probe.probeSourceDevPort})</td>
					<td>${probe.probeDestDevName} (port ${probe.probeDestDevPort})</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<hr>
	<a href="/">Add new probe</a>
</body>
</html>