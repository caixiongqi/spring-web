<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件</title>
</head>
<body>
	<form method="POST" enctype="multipart/form-data" action="/upload/file">
        <div>
            <input type="file" name="file" class="file" data-preview-file-type="text""/>
            <button type="submit">导入</button>
        </div>
    </form>
</body>
</html>