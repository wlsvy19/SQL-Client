<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>SQL-Client</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
		$(document).ready(function() {
	 $('#execute').click(function() {
	 var input = document.getElementById("input").value;
	 var system = document.getElementById("system").value;
	 })
	 }); 

</script>

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

			<div class="">
				<h2>결과출력</h2>
				<input type="text" name="output" id="output"
					style="width: 700px; height: 100px;" readonly="readonly" />
			</div>
		</fieldset>
	</form>
</body>
</html>