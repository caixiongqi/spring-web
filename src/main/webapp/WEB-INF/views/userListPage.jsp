<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>list</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap-table.css">
</head>
<body>
	<div>
		<h1 align="center" style="color: red">用户列表</h1>
	</div>
	
	<section class="container-fluid" id="userList">
		<div class="contender">
			<form action="#" class="form-horizontal">
				<div class="form-group">
					<label for="name" class="col-sm-1 control-label">姓名</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="name"
							name="name">
					</div>
				</div>
				<div class="form-group">
					<label for="sex" class="col-sm-1 control-label">性别</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="sex"
							name="sex">
					</div>
				</div>
				
				<div class="btn-group text-center">
					<button class="btn btn-primary navbar-btn" type="button"
						onclick="search()">
						<i class="ti-plus"></i> 查询
					</button>
					<button class="btn btn-primary navbar-btn" type="reset">
						<i class="ti-plus"></i> 清空
					</button>
				</div>
			</form>
		</div>
	</section>
	

	<div class="pull-left offscreen-right">
		<button class="btn btn-danger navbar-btn" href="#" onclick="createUser()"><i class="ti-plus"></i>创建用户</button>
		<button class="btn btn-danger navbar-btn" href="#" onclick="batchDoDel()"><i class="ti-plus"></i>批量删除</button>
	</div>
	<div class="content-wrap">
		<table id="table-list"></table>
	</div>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/bootstrap-table.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/bootstrap-table-zh-CN.js"></script>
	<script type="text/javascript">
		$(init());
		function init() {
			//初始化table
			$('#table-list').bootstrapTable('destroy');
			//构建table
			$('#table-list').bootstrapTable({
				method : 'get',
				url : '/web/pageUserList',
				striped : true,
				pagination : true,
				pageNumber : 1,
				pageSize : 3,
				pageList : [ 3, 6, 9, 12 ],
				search : true,
				showColumns : false,
				showRefresh : true,
				minimumCountColumns : 2,
				clickToSelect : true,
				sortName : 'id',
				sidePagination : 'server',
				sortOrder : 'desc',
				striped : true,
				sortStable : true,
				idField : 'id',
				queryParamsType : 'undefined',
				queryParams : function queryParams(params) { //设置查询参数  
					/* var param = {
						pageSize : this.pageSize, //页面大小
						pageNumber : this.pageNumber, //页码
						sortName : this.sortName,
						sortOrder : this.sortOrder,
						name : $('#name').val(),
						sex : $('#sex').val()
					};
					return param; */
					params.name = $('#name').val();
					params.sex = $('#sex').val();
					return params;
				},
				columns : [ {
					checkbox : true
				}, {
					title : '序号',
					halign : 'center',
					align : 'center',
					valign : 'center',
					formatter : function(value, row, index) {
						return index + 1;
					}
				}, {
					field : 'name',
					title : '姓名',
					halign : 'center',
					align : 'center',
					valign : 'center',
					visible : true
				}, {
					field : 'age',
					title : '年龄',
					halign : 'center',
					align : 'center',
					valign : 'center',
					visible : true
				}, {
					field : 'sex',
					title : '性别',
					halign : 'center',
					align : 'center',
					valign : 'center',
					visible : true
				},{
					field: 'id',
					title: '操作',
					halign : 'center',
					align :'center',
					valign : 'center',
					formatter:function(value,row,index){
							return '<a onclick="updateUser(\''+ row.id + '\')">修改</a>	|	'+
		                		'<a onclick="deleteUser(\''+ row.id + '\')">删除</a>';
					}
				} ]
			});
		}
		
		function search() {
			$("#table-list").bootstrapTable('refresh');
		}
		
		
		function updateUser(id) {
			window.location.href="/web/createPage?id="+id;
		}
		
		function deleteUser(id) {
			$.ajax({
	  			type : "POST",
	  			url : "/web/delete",
	  			data : {ids : id},
	  			dataType : "text",
				success : function() {
					$("#table-list").bootstrapTable('destroy');//先要将table销毁，否则会保留上次加载的内容
					init();//重新初使化表格。
				},
				error:function(){
					alert("error");
				}
	  		});
		}
		
		function batchDoDel() {
			var row = $('#table-list').bootstrapTable('getAllSelections');
	  		var ids="";
	  		for(var i=0;i<row.length;i++){
		  		if(i==0 || i=="0"){
		  			ids+=row[i].id;
		  		}else{
		  			ids+=","+row[i].id;
		  		}
	  		}
	  		console.log(ids);
	  		$.ajax({
				type : "POST",
				url : "/web/delete",
				data : {ids:ids},
				dataType : "text",
				success : function(msg) {
					$("#table-list").bootstrapTable('destroy');//先要将table销毁，否则会保留上次加载的内容
					init();//重新初使化表格。
				},
				error:function(){
					alert("error");
				}
					
			});
		}
		
		function createUser() {
			window.location.href="/web/createPage";
		}
		
	</script>

</body>
</html>