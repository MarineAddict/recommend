<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form id="addgroup" action="${pageContext.request.contextPath}/productGroup/insertProductGroup/createSource/1/add-productGroup">
组合名称：<input name="Groupname"></br>
<div style="margin-top:10px;">
		产品类型1：<select name="product_type" style="width:200px;">
			<option value="1">理财</option>
			<option value="2">基金</option>
			<option value="3">贵金属</option>
			<option value="4">存单</option>
		</select>
		产品代码1：<input name="product_code">
		所占权重1：<input name="proportion">
</div>
		<!-- 产品类型1：<input name="product_type"> -->
		<br>
<div style="margin-top:10px;">
		产品类型2：<select name="product_type" style="width:200px;">
			<option value="1">理财</option>
			<option value="2">基金</option>
			<option value="3">贵金属</option>
			<option value="4">存单</option>
		</select>
		产品代码2：<input name="product_code">
		所占权重2：<input name="proportion" type="text">
		<!-- 产品类型2：<input name="product_type"> -->
</div>
		</br>
</form>
<input type="button" value="新增产品" onclick="addproduct();"> 
<input type="button" value="提交" onclick="submitform();">
<!-- <input type="button" value="回归测试" onclick="groupBackTest();">  -->

<script type="text/javascript">
var i=3;
function addproduct(){
	var content="";
	content+='<div style="margin-top:10px;">'+'产品类型'+i+'：<select name="product_type" style="width:200px;"><option value="1">理财</option><option value="2">基金</option><option value="3">贵金属</option><option value="4">存单</option></select>&nbsp;产品代码'+i+'：<input name="product_code">&nbsp;所占权重'+i+'：<input name="proportion"></div></br>';
	document.getElementById("addgroup").innerHTML+=content;
	i++;
}

function submitform(){
	//验证产品占比是否为1
	var sum=0;
	obj = document.getElementsByName("proportion");
	for(var i=0;i<obj.length;i++){
	  sum+=Number(obj[i].value);
	}
	if(sum==1){
		$.ajax({
			type : "POST",//方法类型
			dataType : "json",//预期服务器返回的数据类型
			url : "${pageContext.request.contextPath}/productGroup/insertProductGroup/createSource/1/add-productGroup",//url
			data : $('#addgroup').serialize(),
			success : function(result) {
				alert("组合保存成功！");
			},
			error : function() {
				alert("数据提交失败！");
			}
		});
	}else{
		alert("产品占比之和要等于1");
	}
	
}

/* function groupBackTest(){
	$.ajax({
		type : "POST",//方法类型
		dataType : "json",//预期服务器返回的数据类型
		url : "${pageContext.request.contextPath}/backtest/insertProductGroup/backTest",//url
		data : $('#addgroup').serialize(),
		success : function(result) {
			alert("成功！");
		},
		error : function() {
			alert("数据提交失败！");
		}
	});
	} */
</script>
</body>
</html>