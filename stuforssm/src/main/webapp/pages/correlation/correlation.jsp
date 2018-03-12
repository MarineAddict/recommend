<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<c:url value="/js/echars/echarts.js"/>"></script>
</head>
<div>
	<div>
	<form id="searchCorForm">
	<table style="align:center;margin:20px auto">
		<tr>
			<td> 开始时间：</td>
			<td> <input id="corstartime" type="date"></td>
			<td> &nbsp;&nbsp;结束时间：</td>
			<td> <input id="corendtime" type="date"></td>
			<td> &nbsp;&nbsp; <input  type="button" onclick="caculateCor();" value="分析"></td>
		</tr>
	</table>
	</form>
</div>
<br>
	<div id="correlationLine" style="width: 700px;height:600px;margin:40px auto;"></div>
</div>

<script type="text/javascript">

var initStartTime;
var initEndTime;

function caculateCor(){
	var startTime=$("#corstartime").val();
	var endTime=$("#corendtime").val();
	getCorData(startTime,endTime)
	getCorName();
}


var xList=[];
//获取相关系数
function getCorData(startTime,endTime){
$.ajax({
	type:"POST",
	url:"${pageContext.request.contextPath}/correlation/getCorrelationCoefficent",
	data:{startTime:startTime,endTime:endTime},
	timeout:200000,
	cache:false,
	success:function(data){
		console.log(data);
		if(startTime==''||startTime==null){
			$('#corstartime').val(initStartTime.substring(0,10));
		}
		if(endTime==''||endTime==null){
			$('#corendtime').val(initEndTime.substring(0,10));
		}
		correlationLine(data);
	},
	error:function(){
		alert("请求失败");
	}
});
}

//或取小类名称
function getCorName(){
	$.ajax({
	type:"POST",
	url:"${pageContext.request.contextPath}/correlation/getDistinctName",
	/* data:{productCode:productCode,startTime:startTime,endTime:endTime}, */
	timeout:200000,
	cache:false,
	success:function(data){
		console.log(data);
		xList=data;
		
	},
	error:function(){
		alert("请求失败");
	}
	});
}
$.ajax({
	type:"POST",
	url:"${pageContext.request.contextPath}/correlation/getDistinctCode",
	/* data:{productCode:productCode,startTime:startTime,endTime:endTime}, */
	timeout:200000,
	cache:false,
	success:function(data){
		console.log(data);
		xList1=data;
		
	},
	error:function(){
		alert("请求失败");
	}
});
//获取初始化时间
$.ajax({
	type:"POST",
	url:"${pageContext.request.contextPath}/correlation/getInitTime",
	timeout:200000,
	cache:false,
	success:function(data){
		initStartTime=data[0];
		initEndTime=data[1];
	},
	error:function(){
		alert("请求失败");
	}
});



function correlationLine(data){
	var outerList=[];
	for(var i=0;i<data.length;i++){
		for(var j=0;j<data[i].length;j++){
		//for(var j =7;j>-1;j--){
			var innerList=[];
			innerList.push(i);
			innerList.push(j);
			innerList.push(Number(data[i][j]).toFixed(2));
			console.log("innerList="+innerList);
			outerList.push(innerList);
			console.log("outerList="+outerList);
		}
	} 
	var myChart = echarts.init(document.getElementById('correlationLine'));


	var hours = xList;
	var days = xList;
	     var data = outerList;
	     data = data.map(function (item) {
	         return [item[1], item[0], item[2] || '-'];
	     });

	     option = {
	    		 title: {
				        text: '产品小类相关性视图',
				        x:'center',
				        subtext: ' '
				    },
	         tooltip: {
	             position: 'top'
	         },
	         animation: false,
	         grid: {
	             height: '50%',
	             y: '10%'
	         },
	         xAxis: {
	             type: 'category',
	             data: hours,
	             axisLine: {onZero: true},
	             splitArea: {
	                 show: true
	             }, axisLabel:{  
                     interval:0,  
                     rotate:45,//倾斜度 -90 至 90 默认为0  
                     margin:2
                 }/* ,
                inverse: true */
	         },
	         yAxis: {
	             type: 'category',
	             data: days,
	             splitArea: {
	                 show: true
	             } ,
	             inverse: true
	         },
	         visualMap: {
	        	 color:['#1b9722','#a8a8a0','#f44343'],
	             min: -1,
	             max: 1,
	             calculable: true,
	             orient: 'horizontal',
	             left: 'center',
	             bottom: '15%'
	         },
	         series: [{
	             name: '相关性系数',
	             type: 'heatmap',
	             data:data,
	            label: {
	                 normal: {
	                     show: true
	                 }
	             },
	             emphasis: {
	                 shadowBlur: 10,
	                 shadowColor: 'rgba(0, 0, 0, 0.5)',
	              }
	         }]
	     };
	
	myChart.setOption(option);
}
</script>