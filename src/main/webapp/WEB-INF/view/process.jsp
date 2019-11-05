<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>SQL-Client</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script type="text/javascript">
	var error = request.getAttribute("e");
	alert(error);

	
	
	
</script>
<style>
</style>
</head>
<body>
	<form action="process.do" method="post">
		<fieldset style="width: 100">
			<legend>SQL-Client</legend>
			<select name="system" style="height: 40px;">
				<option value="mysql1">MySQL ism</option>
				<option value="mysql2">MySQL ism2</option>
				<option value="oracle1">Oracle ism</option>
			</select><br /> <br /> <br /> <br />


			<table style="border-collapse: collapse;">
				<tr>
					<th style="border-style: hidden;"><input type="text"
						id="input" value="쿼리입력"
						style="width: 100%; height: 40px; border: 0; font-size: 30px;" /></th>

					<th style="border-style: hidden;"><input type="submit"
						value="실행" style="margin-left: 185px; width: 50px; height: 30px;" /></th>
				</tr>
			</table>

			<table>
				<tr>
					<th><input type="text" name="input" id="input" value="${sql}"
						style="width: 600px; height: 200px; font-size: 20px;" /></th>
				</tr>
			</table>

			<%
				Object e1 = request.getAttribute("e1");
				Object e2 = request.getAttribute("e2");
			%>

			<h2>결과출력</h2>
			<tr>
				<td>수행건수 : ${count}건</td>
				<td>수행시간 : ${executeTime}초</td>
			</tr>
			<br />

			<table style="border: 1px solid; width: 50%;">

				<%
					if (e1 != null || e2 != null) {
				%>
				<input type="text" name="output" id="output" value="${error}"
					style="width: 700px; height: 100px; color: red;"
					readonly="readonly" />
				<br />
				<c:forEach var="stackTrace" items="${error.getStackTrace()}">
					<p>${stackTrace}</p>
				</c:forEach>
				<%
					} else {
				%>

				<tr>
					<c:forEach var="columnName" items="${columnName}">
						<th style="border: 1px solid;"><c:out value="${columnName}" /></th>
					</c:forEach>
				</tr>

				<%-- 	<c:forEach var="data" items="${listData}" varStatus="status">
					<c:if test="${status.first}">
						<tr>
					</c:if>
					<td style="border: 1px solid;"><c:out value="${data}" /></td>
					<c:if test="${status.count % columnCount == 0 && !status.last}">
						</tr>
						<tr>
					</c:if>
					<c:if test="${status.last}">
						</tr>
					</c:if>
				</c:forEach> --%>

				<c:forEach var="data" items="${listData2}" varStatus="status">
					<tr>
						<c:forEach var="data2" items="${data}" varStatus="status">
							<td style="border: 1px solid;"><c:out value="${data2}" /></td>
						</c:forEach>
					</tr>
				</c:forEach>

				<table style="width: 50%;">
					<tr>
					<!-- 	<th style="border: 1px solid;">Index</th>
						<th style="border: 1px solid;">name</th>
						<th style="border: 1px solid;">type</th>
						<th style="border: 1px solid;">precision</th>
						<th style="border: 1px solid;">nullable</th> -->
					</tr>

					<%-- <c:forEach var="listIndex" items="${listIndex}" varStatus="status">
						<tr>
							<td style="border: 1px solid;"><c:out value="${listIndex}" /></td>
						</tr>
					</c:forEach>

					<c:forEach var="columnName" items="${columnName}"
						varStatus="status">
						<tr>
							<td style="border: 1px solid;"><c:out value="${columnName}" /></td>
						</tr>
					</c:forEach>

					<c:forEach var="listType" items="${listType}" varStatus="status">
						<tr>
							<td style="border: 1px solid;"><c:out value="${listType}" /></td>
						</tr>
					</c:forEach>

					<c:forEach var="listPrecision" items="${listPrecision}"
						varStatus="status">
						<tr>
							<td style="border: 1px solid;"><c:out
									value="${listPrecision}" /></td>
						</tr>
					</c:forEach>

					<c:forEach var="listNullable" items="${listNullable}"
						varStatus="status">
						<tr>
							<td style="border: 1px solid;"><c:out
									value="${listNullable}" /></td>
						</tr>
					</c:forEach> --%>
				</table>
				<%
					}
				%>
			</table>
		</fieldset>
	</form>	
	
		$('#showTable').empty();
				$('#showTable').show();
				$('#showMetaData').hide();
				$('#executeTime').show();
				$('#executeTime').empty();
				$('#executeCount').show();
				$('#executeCount').empty();
	
	
</body>
</html>