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
		产品代码:<input id="productCode" type="text" />
		开始时间:<input id="startTime" type="date" />
		结束时间:<input id="endTime" type="date" />
		<button onclick="search();">查询</button>
	</div>
	<div>
	涨幅：<input id="growth" type="text" />
	最大回撤：<input id="maxdrawdown" type="text" />
	</div>
	<div id="yieldRatioLine" style="width: 1200px;height:800px;"></div>
	<div id="NAVADJLine" style="width: 1200px;height:800px;"></div>
</div>
<script type="text/javascript">
var url;
var data;

function search(){
	
	var productCode=$('#productCode').val();
	var startTime=$('#startTime').val();
	var endTime=$('#endTime').val();
	
	//根据不同的产品类型去调用不同的url
	/* var selected=$("#productType").find("option:selected").val();
	if(selected=='finance'){
		url="${pageContext.request.contextPath}/finance/getFinanceHistoryIncomeLine";
	}else if(selected=='fund'){
		url="${pageContext.request.contextPath}/fund/yieldRatioLine";
	}else if(selected=='deposit'){
		url="${pageContext.request.contextPath}/deposit/yieldRatioLine";
	}else if(selected=='pm'){
		url="${pageContext.request.contextPath}/pm/yieldRatioLine";
	} */
	
	//获取涨幅和最大回撤
	$.ajax({
		type:"POST",
		url:"${pageContext.request.contextPath}/fund/cycle/day/getFundBasicInfo",
		data:{productCode:productCode,startTime:startTime,endTime:endTime},
		timeout:20000,
		cache:false,
		success:function(data){
			$('#growth').val(data[0].growth);
			$('#maxdrawdown').val(data[0].maxdrawdown);
		},
		error:function(){
			alert("请求失败");
		}
	});
	
	//请求后台数据 收益率曲线
	$.ajax({
		type:"POST",
		url:"${pageContext.request.contextPath}/fund/yieldRatioLine",
		data:{productCode:productCode,startTime:startTime,endTime:endTime},
		timeout:20000,
		cache:false,
		success:function(data){
			yieldRatioLine(data);
			NAVADJLine(data);
		},
		error:function(){
			alert("请求失败");
		}
	});
	
}

//收益率曲线
function yieldRatioLine(data){
	var xList=[];
	var yList=[];
	for(var i=0;i<data.length;i++){
		xList.push(data[i].navDate.substring(0,10));
		yList.push(data[i].yieldRatio);
	}
	var myChart = echarts.init(document.getElementById('yieldRatioLine'));


	var dateList = xList;
	var valueList = yList;
		option = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    title: {
			        left: 'center',
			        text: '收益率曲线',
			    },
			    toolbox: {
			        show : true,
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
			        show : true,
			        realtime : true,
			        //orient: 'vertical',   // 'horizontal'
			        //x: 0,
			        y: 36,
			        //width: 400,
			        height: 20,
			        //backgroundColor: 'rgba(221,160,221,0.5)',
			        //dataBackgroundColor: 'rgba(138,43,226,0.5)',
			        //fillerColor: 'rgba(38,143,26,0.6)',
			        //handleColor: 'rgba(128,43,16,0.8)',
			        //xAxisIndex:[],
			        //yAxisIndex:[],
			        start : 40,
			        end : 60
			    },
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : false,
			            data : dateList,
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'收益率',
			            type:'line',
			            data:valueList,
			        }
			    ],
			    calculable:false
			};
	
	
	

	myChart.setOption(option);
}

//单位净值曲线
function NAVADJLine(data){
	var xList=[];
	var yList=[];
	for(var i=0;i<data.length;i++){
		xList.push(data[i].navDate.substring(0,10));
		yList.push(data[i].navaDj);
	}
	var myChart = echarts.init(document.getElementById('NAVADJLine'));


	var dateList = xList;
	var valueList = yList;
		option = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    title: {
			        left: 'center',
			        text: '单位净值曲线',
			    },
			    toolbox: {
			        show : true,
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
			        show : true,
			        realtime : true,
			        //orient: 'vertical',   // 'horizontal'
			        //x: 0,
			        y: 36,
			        //width: 400,
			        height: 20,
			        //backgroundColor: 'rgba(221,160,221,0.5)',
			        //dataBackgroundColor: 'rgba(138,43,226,0.5)',
			        //fillerColor: 'rgba(38,143,26,0.6)',
			        //handleColor: 'rgba(128,43,16,0.8)',
			        //xAxisIndex:[],
			        //yAxisIndex:[],
			        start : 40,
			        end : 60
			    },
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : false,
			            data : dateList,
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'单位净值',
			            type:'line',
			            data:valueList,
			        }
			    ],
			    calculable:false
			};
	
	
	
	
	myChart.setOption(option);
}

</script>
