<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<c:url value="/js/echars/echarts.js"/>"></script>
</head>
<div style="margin:20px 20px 20px 20px">
	<form id="searchBidForm">
	<table>
		<tr>
			<td> 标的名称：</td>
			<td> <input id="" type="text" value="${bid.NAME}"></td>
			<td> 开始时间：</td>
			<td> <input id="bidstartime" type="date" value="${bid.dmin}"></td>
			<td> 结束时间：</td>
			<td> <input id="bidendtime" type="date"  value="${bid.dmax}"></td>
		</tr>
		<tr>
			<td> 预期收益：</td>
			<td> <input id="bidyieldratio" type="text"></td>
			<td>风险率&nbsp;：</td>
			<td> <input id="bidriskratio" type="text"></td>
			<td>计算规则：</td>
			<td> 
				<select id="bidcalrule"   style="width:60%;">
					  <option value ="standard">标准差</option>
					  <option value ="semiVariance">半方差</option>
				</select>
				<input  type="button" onclick="caculateBid();" value="计算">
			</td>
		</tr>
	</table>
	</form>
</div>
<br>
<div id="biddinfo"style="width: 700px;height:400px;margin:30px auto"></div>
<script type="text/javascript">
	var bidCode=<%=request.getParameter("bidCode")%>;
	$.ajax({
		type:"POST",
		url:"${pageContext.request.contextPath}/category/qryBidData",
		data:{code:bidCode},
		timeout:20000,
		cache:false,
		success:function(data){
			console.log(data);
			getBidInfo(data);
		},
		error:function(){
			alert("请求失败");
		}
	});

	function getBidInfo(data){
			var xList=[];
			var yList=[];
			for(var i=0;i<data.length;i++){
				xList.push(data[i].day.substring(0,10));
				yList.push(data[i].data);
			}
			var bidchart = echarts.init(document.getElementById('biddinfo'));

			var dateList = xList;
			var valueList = yList;
				option = {
					    tooltip : {
					        trigger: 'axis'
					    },
					    title: {
					        left: 'center',
					        text: '标的单位净值',
					    },
						  grid: {
						        bottom: '10%',
						        containLabel: true
						 },
						 toolbox: {
							    show : true,
							   	x:'65%',
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
							 	bottom:"2%", 
						        show : true,
						        realtime : true,
						        height: 20,
						        start : 40,
						        end : 50
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
					            name:'历史单位净值',
					            type:'line',
					            symbol:'none',
					            data:valueList,
					        }
					    ],
					    calculable:false
					};
			bidchart.setOption(option);
	}
	
	function caculateBid(){
		var starTime=$('#bidstartime').val();
		var endTime=$('#bidendtime').val();
		var calculateType=$('#bidcalrule').val();
		
		//alert(calculateType);
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/category/qryBidDetail",
			data:{"code":bidCode,"starTime":starTime,"endTime":endTime,"calculateType":calculateType},
			timeout:20000,
			cache:false,
			success:function(data){
				console.log(data);
				$("#bidyieldratio").val(data.expectYieldRatio);
				$("#bidriskratio").val(data.expectRiskRatio);
						
			},
			error:function(){
				alert("请求失败");
			}
		});
	}


</script>

