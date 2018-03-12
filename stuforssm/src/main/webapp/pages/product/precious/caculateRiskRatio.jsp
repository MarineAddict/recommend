<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
table{
width:1000px;
}
</style>
</head>
<body>
	<div data-options="region:'north',title:'查询'"
		style="height: 40px; background: #F4F4F4;">
		<form id="pmRiskRationForm">
			<table>
				<tr>
					<th>产品代码：</th>
					<td><input id="pmriskRatioProductCode" name="productCode" /></td>
					<th>开始时间</th>
					<td><input id="pmRiskRatioStartTime" class="easyui-datebox" editable="false"
						name="startTime" /></td>
					<th>结束时间</th>
					<td><input id="pmRiskRatioEndTime" class="easyui-datebox" editable="false"
						name="endTime" /></td>
					<th>类型</th>
					<td><select id="pm_type" class="easyui-combobox" data-options="editable:false" style="width:100px;">
					  <option value ="day">天</option>
					  <option value ="week">周</option>
					  <option value="month">月</option>
					  <option value="year">年</option>
					</select></td>
					<th>风险计算算法</th>
					<td><select id="pm_item" class="easyui-combobox" data-options="editable:false" style="width:100px;">
					  <option value ="day">标准差</option>
					  <option value ="week">半方差</option>
					  <option value="month">左偏动差</option>
					</select></td>
					<!--由于datebox框架上面的数据必须是时间格式的，所以我们用editable="false"来禁止用户手动输入，以免报错-->
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="pmRiskRatioCaculate();">计算</a></td>
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="clearPmRiskRationForm();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	预期收益率：<input id="pm_expected_annualized_return" type="text">
	预期风险率：<input id="pm_expected_risk_ratio"  type="text">

<script type="text/javascript">

//点击查找按钮出发事件
function pmRiskRatioCaculate() {
	
	var productCode=$('#pmriskRatioProductCode').val();
	var startTime=$("#pmRiskRatioStartTime").datebox("getValue");
	var endTime=$("#pmRiskRatioEndTime").datebox("getValue");
	var cycle=$("#pm_type").combobox('getValue');
	
	if(productCode==''||productCode==null){
		alert("产品代码不能为空!");
		return ;
	}
	
	//获取预期收益率和预期风险率
	$.ajax({
		type:"POST",
		url:"${pageContext.request.contextPath}/pm/getCaculateValue",
		data:{productCode:productCode,startTime:startTime,endTime:endTime,cycle:cycle},
		timeout:20000,
		cache:false,
		success:function(data){
			console.log(data);
			$('#pm_expected_annualized_return').val(Number(data.expected_annualized_return).toFixed(4));
			$('#pm_expected_risk_ratio').val(Number(data.expected_risk_ratio).toFixed(4));
		},
		error:function(){
			alert("请求失败");
		}
	});
	
	//请求后台数据 
	$.ajax({
		type:"POST",
		url:"${pageContext.request.contextPath}/pm/yieldRatioLine",
		data:{productCode:productCode,startTime:startTime,endTime:endTime},
		timeout:20000,
		cache:false,
		success:function(data){
			if(startTime==''||startTime==null){
				$("#pmRiskRatioStartTime").datebox("setValue", data[0].navDate.substring(0,10));   
			}
			if(endTime==''||endTime==null){
				$("#pmRiskRatioEndTime").datebox("setValue", data[data.length-1].navDate.substring(0,10));  
			}
		},
		error:function(){
			alert("请求失败");
		}
	});
	
}

//点击清空按钮出发事件
function clearPmRiskRationForm() {
    $("#pmRiskRationForm").find("input").val("");//找到form表单下的所有input标签并清空
}
</script>
</body>
</html>