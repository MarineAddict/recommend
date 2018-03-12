<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div data-options="region:'north',title:'查询'"
		style="height: 40px; background: #F4F4F4;">
		<form id="assetsSearchForm">
			<table>
				<tr>
				<th>组合代码：</th>
                <td><input name="groupCode" id="assetsGroupCode"/></td>
                <th>开始时间</th>
                <td><input class="easyui-datebox" editable="false" name="startTime" id="assetsStartTime" /></td>
                <th>结束时间</th>
                <td><input class="easyui-datebox" editable="false"name="endTime" id="assetsEndTime"/></td>
                <th>初始金额</th>
                <td><input editable="false" class="easyui-numberbox" ata-options="min:0,precision:2" name="initmoney" id="assetsInitmoney"/></td>
                <th>再平衡</th>
                <td>
                	<select id="assetsRebalance" name="rebalance">
                	<option value="" selected>请选择</option>
                		<option value="None">没有平衡</option>
                		<option value="rebalance_monthly">每月重新平衡</option>
                		<option value="rebalance_quarterly">每季度重新平衡</option>
                		<option value="rebalance_semi-annually">每半年重新平衡</option>
                		<option value="rebalance_annually">每年重新平衡</option>
                	</select>
                </td>
                 <th>基准</th>
                <td>
                	<select id="benchmark" name="rebalance">
                		<option value="" selected>请选择</option>
                		<option value="000001.SH">上证</option>
                		<option value="000300.SH">沪深300</option>
                		<option value="512500.SH">中证500</option>
                	</select>
                </td>
                <th>组合来源</th>
                <td>
                	<select id="basicGroupType" name="groupType">
                		<option value="" selected>请选择</option>
                		<option value="asset">资产小类</option>
                		<option value="financial">金融产品</option>
                	</select>
                </td>
					<!--由于datebox框架上面的数据必须是时间格式的，所以我们用editable="false"来禁止用户手动输入，以免报错-->
					<td><a class="easyui-linkbutton" href="javascript:void(0);" onclick="searchGroupAssets();">分析</a></td>
					<td><a class="easyui-linkbutton" href="javascript:void(0);" onclick="clearSearch();">清空</a></td>
					<td><a class="easyui-linkbutton" href="javascript:void(0);"onclick="searchAssetsOtherInfo();">查看其他指标信息</a></td>
				</tr>
			</table>
		</form>
	</div>
	<label id="table1" style="display:none;font-size:14px;font-weight:600;margin-top:10px;">投资组合</label>
	<div style="margin-top:10px;">
		<table id="table-1" style="width:90%;border-bottom-width: 1px;border-bottom-style: solid;border-bottom-color: rgb(211, 202, 221);border:none;  border-collapse:collapse;border-spacing:0;"></table>
	</div>
	<label id="table2" style="display:none;">投资组合</label>
	<div>
		<table id="table-2" style="width:90%;border-bottom-width: 1px;border-bottom-style: solid;border-bottom-color: rgb(211, 202, 221);"></table>
	</div>

<script type="text/javascript">

var initGroupCode=<%=request.getParameter("groupCode")%>;
var initStartTime=<%=request.getParameter("startTime")%>;
var initEndTime=<%=request.getParameter("endTime")%>;
var initRebalance=<%=request.getParameter("rebalance")%>;
var initInitmoney=<%=request.getParameter("initmoney")%>;

$(function(){
	if(initGroupCode==null||initStartTime==null||initEndTime==null||initRebalance==null||initInitmoney==null){
		return ;
	}else{
		/* alert(initGroupCode+","+initStartTime+","+initEndTime+","+initRebalance+","+initInitmoney); */
		$('#assetsGroupCode').val(initGroupCode);
		$('#assetsStartTime').datebox('setValue', initStartTime);
		$('#assetsEndTime').datebox('setValue', initEndTime);
		$('#assetsRebalance').val(initRebalance);
		$('#assetsInitmoney').val(initInitmoney);
		searchGroupAssets();
	}
});

//点击查找按钮出发事件
function searchGroupAssets() {
	
	var groupCode=$('#assetsGroupCode').val();
	var startTime=$("#assetsStartTime").datebox("getValue"); 
	var endTime=$("#assetsEndTime").datebox("getValue"); 
	var rebalance=$('#assetsRebalance').val();
    var initmoney=$('#assetsInitmoney').val();
   /*  alert(groupCode+","+startTime+","+endTime+","+rebalance+","+initmoney); */
	 if(rebalance==""||groupCode==""||startTime==""||endTime==""||initmoney==""){
     	alert("所有选项为必填项");
     	return ;
     } 
	
	$.ajax({
		type:"POST",
		url:"${pageContext.request.contextPath}/backtest/getBacktestMetricsData",
		data:{groupCode:groupCode,startTime:startTime,endTime:endTime,rebalance:rebalance,initmoney:initmoney},
		timeout:60000,
		cache:false,
		 beforeSend: function () {
         	load();
         },
         complete: function () {
             disLoad();
         },
		success:function(data){
            //提示信息
            if("Failed to call python"==data[0].errorInfo){
                disLoad();
                alert("Failed to call python!");
            }

			//表头
			console.log(data[0].assetIndexTitleArr);
			
			//表格数据
			console.log(data[0].assetIndexValuesList);
			
			//构造table表格
			$("#table-1").empty();
			createTable(data[0].assetIndexTitleArr,data[0].assetIndexValuesList);
		},
		error:function(){
			alert("请求失败");
		}
	});
}

//创建表格
function createTable(headData,bodyData){
	var percentArray=[1,2,5,7,8];
	var tableContent="";
	var headContent="<thead style='border-top-width: 1px;border-top-style: solid;border-top-color: rgb(211, 202, 221);'><tr>";
	var bodyContent="<tbody>";
	for(var i=0;i<headData.length;i++){
		headContent+="<th style='padding: 5px 10px;font-size: 12px;font-family: Verdana;color: rgb(95, 74, 121);'>"+headData[i]+"</th>";
	}
	headContent+="</tr></thead>";
	
	for(var i=0;i<bodyData.length;i++){
		bodyContent+="<tr style='border-top-width: 1px;border-top-style: solid;border-top-color: rgb(211, 202, 221)'>";
		for(var j=0;j<bodyData[i].length;j++){
			console.log(bodyData[i]);
			if(j!=0&& !isNaN(bodyData[i][j]) && inArray(j, percentArray)){
				bodyContent+="<td style='padding: 5px 10px;font-size: 12px;font-family: Verdana;color: rgb(95, 74, 121);'>"+(Number(bodyData[i][j])*100).toFixed(2)+"%"+"</td>";
			}else if(j!=0&& !isNaN(bodyData[i][j])){
				bodyContent+="<td style='padding: 5px 10px;font-size: 12px;font-family: Verdana;color: rgb(95, 74, 121);'>"+Number(bodyData[i][j]).toFixed(4)+"</td>";
			}else{
				bodyContent+="<td style='padding: 5px 10px;font-size: 12px;font-family: Verdana;color: rgb(95, 74, 121);'>"+bodyData[i][j]+"</td>";
			}
			
		}
		bodyContent+="</tr>";
	}
	headContent+="</tr></thead>";
	bodyContent+="<tbody>";
	console.log(headContent);
	console.log(bodyContent);
	
	tableContent=headContent+bodyContent;
	document.getElementById("table-1").innerHTML=tableContent;
	$("#table-1 tr:nth-child(odd)").css("background-color", "#FFF"); 
	$("#table-1 tr:nth-child(even)").css("background-color", "rgb(223, 216, 232)"); 
	document.getElementById("table1").style.display = "block";
}

//点击清空按钮出发事件
function clearSearch() {
    $("#assetsSearchForm").find("input").val("");//找到form表单下的所有input标签并清空
    $("#assetsSearchForm option:first").prop("selected", 'selected');
}


//查看其他信息页面
function searchAssetsOtherInfo(){
	var groupCode=$('#assetsGroupCode').val();
    var startTime=$('#assetsStartTime').datebox('getValue');
    var endTime=$('#assetsEndTime').datebox('getValue');
   	var rebalance=$('#assetsRebalance').val();
    var initmoney=$('#assetsInitmoney').val();
    if(rebalance==""||groupCode==""||startTime==""||endTime==""||initmoney==""){
    	alert("所有选项为必填项");
    	return ;
    } 
    
	 $('#wu-tabs').tabs('close','组合指标分析');
	 $('#wu-tabs').tabs('close','组合回撤分析');
	 $('#wu-tabs').tabs('close','组合基本数据');
	 addTab("组合指标分析", "pages/backtest/groupMetrics.jsp?groupCode='"+groupCode+"'&startTime='"+startTime+"'&endTime='"+endTime+"'&rebalance='"+rebalance+"'&initmoney='"+initmoney+"'", "icon-chart-organisation", false);
	 addTab("组合回撤分析", "pages/backtest/groupIndexAnalyse.jsp?groupCode='"+groupCode+"'&startTime='"+startTime+"'&endTime='"+endTime+"'&rebalance='"+rebalance+"'&initmoney='"+initmoney+"'", "icon-chart-organisation", false);
	 addTab("组合基本数据", "pages/backtest/test/groupBasic.jsp?groupCode='"+groupCode+"'&startTime='"+startTime+"'&endTime='"+endTime+"'&rebalance='"+rebalance+"'&initmoney='"+initmoney+"'", "icon-chart-organisation", false);
}
</script>
</body>
</html>