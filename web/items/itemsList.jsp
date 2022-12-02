<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>商品列表</title>
	<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/index.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/fenye.js"></script>
</head>
<body>
<div style="padding: 0 20px">
	<div class="row" style="margin-top: 10px">
		<div class="col-md-6 col-lg-6">
			<div class="input-group">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" onclick="viewAll()">查找</button>
                    </span>
				<input type="text" class="form-control" placeholder="Search by name" id="searchName"  value="${vo.query1}">
			</div>
		</div>
		<div class="col-md-4 col-lg-4">
			<input class="btn btn-success" type="button" value="增加商品" onclick="showAdd()">
			<input class="btn btn-danger" type="button" value="删除所有" onclick="deleteAll('all')">
		</div>
	</div>
	<div class="row" style="margin-top: 20px">
		<table class="table table-bordered">
			<tr align="center">
				<th style="text-align:center"><input type="checkbox" class="choose_all"></th>
				<th style="text-align:center">商品编号</th>
				<th style="text-align:center">商品名称</th>
				<th style="text-align:center">商品描述</th>
				<th style="text-align:center">商品价格</th>
				<th style="text-align:center">生产日期</th>
				<th style="text-align:center">商品图片</th>
				<th style="text-align:center">操作</th>
			</tr>
			<c:forEach items="${vo.list}" var="items">
				<tr class="data" align="center">
					<td class="datachoose"><input type="checkbox" class="single"></td>
					<td class="id">${items.id}</td>
					<td>${items.name}</td>
					<td>${items.detail}</td>
					<td>${items.price}</td>
					<td><fmt:formatDate value="${items.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
						<c:if test="${items.pic!=null}">
							<img src="${items.pic}" width="64px" height="auto"/>
						</c:if>
					</td>
					<td>
						<a href="javascript:void(0)" class="btn btn-info" onclick="viewOne(${items.id})">修改</a>|
						<a href="javascript:void(0)" class="btn btn-danger" onclick="deleteAll(${items.id})">删除</a>
					</td>
				</tr>
			</c:forEach>
			<tr align="center">
				<td colspan="8">
					<input class="btn btn-success" type="button" value="首页"
						   onclick="first()"/>&nbsp;&nbsp;
					<input class="btn btn-success" type="button" id="pre" value="上一页"
						   onclick="pre()"/>&nbsp;&nbsp;
					<!-- 当前页 -->
					<input type="text" id="pageNow" value="${vo.pageNow}" style="text-align:center"/>&nbsp;&nbsp;
					<input class="btn btn-success" type="button" value="跳转"
						   onclick="skip(${vo.myPages})"/>&nbsp;&nbsp;
					<input class="btn btn-success" type="button" id="next" value="下一页"
						   onclick="next(${vo.myPages})"/>&nbsp;&nbsp;
					<input class="btn btn-success" type="button" value="末页"
						   onclick="last(${vo.myPages})"/>&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	</div>
</div>

</body>

</html>