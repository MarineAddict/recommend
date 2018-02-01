<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
产品组合代码：<input id="id" type="text">
产品组合名称：<input id="product_group_name" type="text"></br>
sharp比率：<input id="sharpeRatio" type="text">
创建时间：<input id="create_time" type="text">
<table id="productgroup"></table>
<script type="text/javascript">
//获取数据
var id=<%=request.getParameter("id")%>;
$.ajax({
	type:"POST",
	url:"${pageContext.request.contextPath}/productGroup/productGroupId/"+id+"/getProductGroupDetailsInfo",
	timeout:20000,
	cache:false,
	success:function(data){
		console.log(data);
		$('#id').val(data.id);
		$('#product_group_name').val(data.product_group_name);
		$('#sharpeRatio').val(data.sharpeRatio);
		$('#create_time').val(data.create_time);
		
		var content="";
		for(var i=0;i<data.pgdList.length;i++){
			content+="<tr>";
			content+="<th>产品代码</th>";
			content+="<td>"+data.pgdList[i].product_code+"</td>";
			content+="<th>产品占比</th>";
			content+="<td>"+data.pgdList[i].proportion*100+"%</td>";
			content+="<th>最后修改时间</th>";
			content+="<td>"+data.pgdList[i].last_mod_time.substring(0,19)+"</td>";
			content+="</tr>";
		}
		document.getElementById("productgroup").innerHTML=content;
	},
	error:function(){
		alert("请求失败");
	}
});
</script>

</body>
</html>