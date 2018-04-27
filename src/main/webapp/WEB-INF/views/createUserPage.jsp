<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap-table.css">
</head>
<title>用户创建页面</title>
</head>
<body>
	<div>
		<h1 align="center" style="color: red">创建用户页面</h1>
	</div>
	<div class="container bgw">
		<form class="form-horizontal" role="form" action="/web/create"
			method="post">
			<input type=hidden id="id" name="id" value="${user.id }">

			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">姓名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="name" name="name"
						placeholder="请输入姓名" value="${user.name }">
				</div>
			</div>
			<div class="form-group">
				<label for="age" class="col-sm-2 control-label">年龄</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="age" name="age"
						placeholder="请输入年龄" value="${user.age }">
				</div>
			</div>
			<div class="form-group">
				<label for="sex" class="col-sm-2 control-label">性别</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="sex" name="sex"
						placeholder="请输入性别" value="${user.sex }">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="button" class="btn btn-default" onclick="save()">保存</button>
					<button type="reset" class="btn btn-default">重置</button>
				</div>
			</div>

		</form>
	</div>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript">
		function save() {
			var data = {
					id : $('#id').val(),
					name : $('#name').val(),
					age : $('#age').val(),
					sex : $('#sex').val()
			}
			
			$.ajax({
	  			type : "POST",
	  			url : "/web/create",
	  			contentType : "application/json; charset=utf-8",
				data : JSON.stringify(data),
				success : function() {
					window.location.href = "/web/userPage";
				},
				error:function(){
					alert("error");
				}
	  		});
		}
	</script>

</body>
</html>