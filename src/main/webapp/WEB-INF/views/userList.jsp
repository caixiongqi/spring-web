<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户集合列表</title>
</head>
<body>
	
	<div>
		<h1 align="center" style="color: red">用户列表</h1>
	</div>

	<div>
		<form action="">
			<table align="center" border="2px" bordercolor="red" width="100%"
				style="text-align: center" cellspacing="0" height="150px">
				<tr>
					<th>序号</th>
					<th>名字</th>
					<th>年龄</th>
					<th>性别</th>
				</tr>
				<c:forEach items="${userList }" var="user" varStatus="status">
					<tr>
						<td>${status.index + 1 }</td>
						<td>${user.name }</td>
						<td>${user.age }</td>
						<td>${user.sex }</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>

</body>
</html>