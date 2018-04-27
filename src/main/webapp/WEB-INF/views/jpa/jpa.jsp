<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ajax get</title>
</head>
<body>
	<div>
		姓名：<input type="text" id="name" name="name" value="${user.name }" readonly="readonly">
	</div>
	<div>
		年龄：<input type="text" id="age" name="age" value="${user.age }" readonly="readonly">
	</div>
	<div>
		性别：<input type="text" id="sex" name="sex" value="${user.sex }" readonly="readonly">
	</div>
	<div>
		<input type="button" onclick="getUser()" value="获取实体">
	</div>
	
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript">
		function getUser() {
			$.ajax({
	  			type : "GET",
	  			url : "/jpa/test?name=张三",
				success : function(msg) {
					console.log(msg);
					$('#name').val(msg.name);
					$('#age').val(msg.age);
					$('#sex').val(msg.sex);
					
				},
				error:function(){
					alert("error");
				}
	  		});
		}
	</script>
</body>
</html>