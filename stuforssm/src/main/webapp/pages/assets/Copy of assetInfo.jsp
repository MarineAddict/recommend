<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<c:url value="/js/echars/echarts.js"/>"></script>
<!-- <style type="text/css">
	table{
		width:1000px;
		}
</style> -->
</head>
	
<div data-options="region:'north',title:'查询'"style="height: 40px; background: #F4F4F4;">
		<table> 
			<tr style="margin:20px auto">
				<td>请选择体系：</td>
				<td><a class="easyui-linkbutton" onclick="searchConbank();">建行</a></td>
				<td><a class="easyui-linkbutton" onclick="searchNetBank();">互联网</a></td>
			</tr>
		</table>
	</div>
	
	<div id="dt1" style="display:none;">
		<table id="catBigConList"></table>
		
		<div id="biddingLine" style="width:90%;height:800px;margin:100px 50px 100px 50px;"></div>
	</div>
	<div  id="dt2" style="display:none;">
		<table id="catBigNetList" style="border :black 1px solid"> 
		<tr style="margin:20px auto">
			<td>测试1</td>
			<td>测试2</td>
			<td>测试3</td>
		</tr>
	</table>
	</div>

	<div id="biddetail">Dialog Content.</div>

	
	
	
	
<script type="text/javascript">
window.onload=function (){}
function searchConbank(){
	$('#dt2').css('display', 'none');
	$('#dt1').css('display', 'block');
	$('#catBigConList').datagrid({
		width : '100%',
		url : "${pageContext.request.contextPath}/category/qryCategory",
		loadMsg : '数据加载中,请稍后……',
		pagination : true,
		singleSelect : true,
		rownumbers : true,
		nowrap : true,
		height : 'auto',
		/* fit : true, */
		fitColumns : true,
		striped : true,
		idField : 'bondId',
		pageSize : 10,
		pageNumber:1,
		pageList : [ 10, 30, 50 ],
		columns : [ [{
			field : 'bgName',
			title : '大类名称',
			width : 200,
			align : 'center'
		/* }, {
			field : 'smCode',
			title : '小类代码',
			width : 200,
			align : 'center' */
		}, {
			field : 'smName',
			title : '小类名称',
			width : 200,
			align : 'center'
		}, {
			field :'bidName' ,
			title : '标的',
			width : 200,
			align : 'center'
		}, {
			field : 'caozuo' ,
			title : '标的详情',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				return "<a onclick='openDialog2("+row.bidCode+");' iframe='0' >详情</a>"; 
			}
		}]] 
	});
	
	//alert(12);
	//加载历史单位净值曲线图
	$.ajax({
		type:"POST",
		url:"${pageContext.request.contextPath}/category/qryCategoryData",
		cache:false,
		asyn:true,
		success:function(result){
			console.log(result);
			getBiddingDataLine(result);
		},
		error:function(){
			alert("请求失败");
		}
	});
	
}
function searchNetBank(){
	$('#dt1').css('display', 'none');
	$('#dt2').css('display', 'block');
}

function openDialog2(bidCode){

	//alert(bidCode);
	//alert(bidCode,bidName);
	//addTab("标的历史收益", "${pageContext.request.contextPath}/pages/assets/assetDetail.jsp?bidCode='+bidCode", "icon-chart-organisation", false);
	//alert(bidCode);
	var rows = $('#catBigConList').datagrid('getSelected');
	if(rows){
		var bidName=rows.bidName;
		//var data={"bidCode":bidCode,"bidName":bidName},
		alert(bidName);
		//alert(bidCode,bidName);
		$('#biddetail').dialog({
			title: '标的详情',
			width: 800,
			height: 600,
			closed: false,
			cache: false,
			href: '${pageContext.request.contextPath}/pages/assets/bidInfo.jsp?bidCode='+bidCode+'&bidName='+encodeURI(bidName),
			//href: '${pageContext.request.contextPath}/pages/assets/bidInfo.jsp?data='+data,
			modal: true
		});
	}
}





//收益率曲线
function getBiddingDataLine(data){
	var xList=[];
	var yList=[];

	var yList1=[];
	var yList2=[];
	var yList3=[];
	var yList4=[];
	var yList5=[];
	var yList6=[];
	var yList7=[];
	var yList8=[];
	
	
	for(var i=0;i<data.length;i++){
  		
  		if(data[i].code=='1'){
  			xList.push(data[i].day.substring(0,10));
  		}
  		if(data[i].code=='1'){
  			yList1.push(data[i].data);
  		}
  		if(data[i].code=='2'){
  			yList2.push(data[i].data);
  		}
  		if(data[i].code=='3'){
  			yList3.push(data[i].data);
  		}
  		if(data[i].code=='4'){
  			yList4.push(data[i].data);
  		}
  		if(data[i].code=='5'){
  			yList5.push(data[i].data);
  		}
  		if(data[i].code=='6'){
  			yList6.push(data[i].data);
  		}
  		if(data[i].code=='7'){
  			yList7.push(data[i].data);
  		}
  		if(data[i].code=='8'){
  			yList8.push(data[i].data);
  		}
  		
		//yList.push(data[i].data);
	}
	var bidchart = echarts.init(document.getElementById('biddingLine'));


	var dateList = xList;
	var valueList = yList;
	option = {
				title: {
				        text: '历史单位净值',
				        x:'center'
				    },
				tooltip: {
				        trigger: 'axis'
				    },
				legend: {
			         orient: 'horizontal',
			         x: 'center',
			         y: 'bottom',
				     data: ['中证1000指数','中证财富1000指数','上证港股通指数','SPY指数','中证货币基金指数','中证普通债基金','中证纯债债券型基金指数','上海黄金交易所AU9995黄金']
				    },
				  grid: {
				        bottom: '10%',
				        containLabel: true
				 },
				 toolbox: {
					    show : true,
					   	x:'75%',
				        feature : {
				            mark : {show: true},
				            dataZoom : {show: true},
				            dataView : {show: true},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				 dataZoom : {
					 	bottom:"5%", 
				        show : true,
				        realtime : true,
				        height: 20,
				        start : 40,
				        end : 50
				    },
				 xAxis: {
				        type: 'category',
				        boundaryGap: false,
				        data:dateList
				    },
				 yAxis: {
				        type: 'value'
				    },
				 series: [
				        {
				            name:'中证1000指数',
				            type:'line',
				            symbol:'none',
				            data:yList1
				        },{
				            name:'中证财富1000指数',
				            type:'line',
				            symbol:'none',
				            data:yList2
				        },{
				            name:'上证港股通指数',
				            type:'line',
				            symbol:'none',
				            data:yList3
				        },{
				            name:'SPY指数',
				            type:'line',
				            symbol:'none',
				            data:yList4
				        },{
				            name:'中证货币基金指数',
				            type:'line',
				            symbol:'none',
				            data:yList5
				        },{
				            name:'中证普通债基金',
				            type:'line',
				            symbol:'none',
				            data:yList6
				        },{
				            name:'中证纯债债券型基金指数',
				            type:'line',
				            symbol:'none',
				            data:yList7
				        },{
				            name:'上海黄金交易所AU9995黄金',
				            type:'line',
				            symbol:'none',
				            data:yList8
				        }
				    ]
				};
		bidchart.setOption(option);
}

</script>