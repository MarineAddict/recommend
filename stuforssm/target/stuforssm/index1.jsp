<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>我是首页</h1>
	<a href="test/test1">测试SUCCESS</a><br>
	<a href="pages\STU_JS\Js_02.jsp">测试STU_JS/js_01</a><br>

	<a href="pages\STU_JS\yieldRatioLine.jsp">查询产品收益率</a><br>
	<a href="pages\STU_JS\groupYieldRatioLine.jsp">查询产品组合收益率</a><br>
	<a href="pages\STU_JS\custGroupInfo.jsp">个人组合信息</a><br>

	<form action="${pageContext.request.contextPath}/productGroup/insertProductGroup/xx/1/add-productGroup">
		产品代码1：<input name="product_code">
		所占权重1：<input name="proportion">
		<br>
		产品代码2：<input name="product_code">
		所占权重2：<input name="proportion">
		<input type="submit">
	</form>
</body>
</html>