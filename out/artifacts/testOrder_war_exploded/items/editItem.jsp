<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>修改商品信息</title>
	<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="container">
	<h2 class="form-group">修改商品</h2>
	<form id="editForm" action="" method="post" enctype="multipart/form-data">
		<div class="form-group">
			<label for="id">商品编号</label>
			<input type="text" id="id" placeholder="id" name="id" value="${items.id}" readonly>
		</div>
		<div class="form-group">
			<label for="name">商品名称</label>
			<input type="text" id="name" placeholder="name" name="name"
				   value="${items.name}" required="required" onblur="validateModifyName('${items.name}')">
			<span id="nameMsg"></span>
		</div>
		<div class="form-group">
			<label for="detail">商品描述</label>
			<input type="text" id="detail" placeholder="desc" name="detail" value="${items.detail}">
		</div>
		<div class="form-group">
			<label for="price">商品价格</label>
			<input type="text" id="price" placeholder="price" name="price"
				   value="${items.price}" required="required">
		</div>
		<div class="form-group">
			<label for="pic">商品图片</label>
			<img id="pic" name="pic" width="100" height="100" src="${items.pic}" />
			<input type="file" id="file" placeholder="file" name="file" onchange="showPreview(this)">
		</div>
		<div class="form-group">
			<label for="createtime">生产日期</label>
			<input type="text" id="createtime" class="Wdate" placeholder="createtime" name="createtime"
				   value="<fmt:formatDate value='${items.createtime}' pattern='yyyy-MM-dd HH:mm:ss' />"
				   onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
		</div>
		<button type="button" class="btn btn-success" onclick="modify()">提交</button>
	</form>
</div>
</body>
<script type="text/javascript">
	//将文件流以url形式读取，实现图片实时显示：
	function showPreview(source){
		var file = source.files[0];
		if(window.FileReader){
			var fr = new FileReader();
			fr.onloadend = function(e){
				document.getElementById("pic").src=e.target.result;
			}
			fr.readAsDataURL(file);
		}
	}
</script>

</html>