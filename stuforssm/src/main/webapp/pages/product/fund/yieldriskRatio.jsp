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
	<h2>基金净值收益率查询：</h2>
	<div style="width:100%;height: 100px;">
		<form id="searchForm">
			<table>
				<tr>
					<th>产品代码：</th>
					<td><input id="productCode" name="productCode" /></td>
					<th>开始时间</th>
					<td><input id="fundRiskRatioStartTime" class="easyui-datebox" editable="false" name="startTime" /></td>
					<th>结束时间</th>
					<td><input id="fundRiskRatioEndTime" class="easyui-datebox" editable="false" name="endTime" /></td>
					
					<td><a class="easyui-linkbutton" href="javascript:void(0);"onclick="caculate();">查询</a></td>
				</tr>
				<tr>
					<th>涨幅：</th>
					<td><input id="expected_annualized_return" type="text"></td>
					<th>最大回撤：</th>
					<td><input id="expected_risk_ratio"  type="text"></td>
				</tr>
			
			</table>
		</form>
	</div>

	<h2>预期收益率贺风险率计算：</h2>
	<div style="width:100%;height: 100px;">
		<form id="searchForm">
			<table>
				<tr>
					<th>产品代码：</th>
					<td><input id="productCode" name="productCode" /></td>
					<th>开始时间</th>
					<td><input id="fundRiskRatioStartTime" class="easyui-datebox" editable="false" name="startTime" /></td>
					<th>结束时间</th>
					<td><input id="fundRiskRatioEndTime" class="easyui-datebox" editable="false" name="endTime" /></td>
					<th>类型</th>
					<td><select id="type" class="easyui-combobox" data-options="editable:false" style="width:100px;">
						  <option value ="day">天</option>
						  <option value ="week">周</option>
						  <option value="month">月</option>
						  <option value="year">年</option>
						</select>
					</td>
					<th>风险计算算法</th>
					<td><select id="item" class="easyui-combobox" data-options="editable:false" style="width:100px;">
						<option value ="day">标准差</option>
						<option value ="week">半方差</option>
						<option value="month">左偏动差</option>
					</select>
					</td>
					<td><a class="easyui-linkbutton" href="javascript:void(0);"onclick="caculate();">计算</a></td>
					<td><a class="easyui-linkbutton" href="javascript:void(0);"onclick="clearSearch();">清空</a></td>
				</tr>
				<tr>
				<th>预期收益率：</th>
				<td><input id="expected_annualized_return" type="text"></td>
				<th>预期风险率：</th>
				<td><input id="expected_risk_ratio"  type="text"></td>
			</tr>
			
			</table>
		</form>
	</div>
	

<script type="text/javascript">
//点击查找按钮出发事件
function caculate() {

	var productCode=$('#productCode').val();
	var startTime=$("#fundRiskRatioStartTime").datebox("getValue");
	var endTime=$("#fundRiskRatioEndTime").datebox("getValue");
	var url="";
	
	
	if(productCode==''||productCode==null){
		alert("产品代码不能为空!");
		return ;
	}
	
	//根据不同的产品类型去调用不同的url
	var selected=$("#type").combobox("getValue");
	
	if(selected=='day'){
		url="${pageContext.request.contextPath}/fund/cycle/day/getFundBasicInfo";
	}else if(selected=='week'){
		url="${pageContext.request.contextPath}/fund/cycle/week/getFundBasicInfo";
	}else if(selected=='month'){
		url="${pageContext.request.contextPath}/fund/cycle/month/getFundBasicInfo";
	}else if(selected=='year'){
		url="${pageContext.request.contextPath}/fund/cycle/year/getFundBasicInfo";
	}
	//获取预期收益率和预期风险率
	$.ajax({
		type:"POST",
		url:url,
		data:{productCode:productCode,startTime:startTime,endTime:endTime},
		timeout:20000,
		cache:false,
		success:function(data){
			if(startTime==''||startTime==null){
				$("#fundRiskRatioStartTime").datebox("setValue", data[0].ipo_START_DATE.substring(0,10));   
			}
			if(endTime==''||endTime==null){
				$("#fundRiskRatioEndTime").datebox("setValue", data[0].ipo_END_DATE.substring(0,10));  
			}
			$('#expected_annualized_return').val(Number(data[0].expected_annualized_return).toFixed(4));
			$('#expected_risk_ratio').val(Number(data[0].expected_risk_ratio).toFixed(4));
		},
		error:function(){
			alert("请求失败");
		}
	});
}

//点击清空按钮出发事件
function clearSearch() {
    $("#searchForm").find("input").val("");//找到form表单下的所有input标签并清空
}
</script>
</body>
</html>