<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>收藏页面</title>
</head>
<body>
<form action="list" method="post">
<input type="text" name="copytext" value="${copytext}">
<input type="submit" value="搜索">
<table>
 <tr>
  <td>ID</td>
  <td>收藏内容</td>
  <td>登录人ID</td>
  <td>收藏时间</td>
  <td>操作</td>
   </tr>
<c:forEach items="${page.list}" var="c">
 <tr>
  <td>${c.id}</td>
  <td>${c.copytext}</td>
  <td>${c.uid}</td>
  <td>${c.created}</td>
  <td>
  <a href="del?id=${c.id}">删除</a>
  </td>
   </tr>
</c:forEach> 
</table>
<table>
 <tr>
  <td>
  <button name="pageNum" value="1">首页</button>
  <button name="pageNum" value="${page.prePage==0?1:page.prePage}">上一页</button>
  <button name="pageNum" value="${page.nextPage==0?page.pages:page.nextPage}">下一页</button>
  <button name="pageNum" value="${page.pages}">尾页</button>
  </td>
 </tr>
</table>
</form>
</body>
</html>