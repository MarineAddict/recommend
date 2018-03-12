<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<c:url value="/js/echars/echarts.js"/>"></script>
</head>
	<input type="hidden" value="${finance.CODE}" id="fin_del_code"/>
	<input type="hidden" value="${finance.FINANCE_NAME}" id="fin_del_name"/>
	<input type="hidden" value="${finance.SNAME}" id="fin_del_sname"/>
	<input type="hidden" value="${finance.SCODE}" id="fin_del_scode"/>
	<input type="hidden" value="${finance.BIDNAME}" id="fin_del_bidname"/>
	<input type="hidden" value="${finance.BIDCODE}" id="fin_del_bidcode"/>
	
	<div class="easyui-layout" style="width:100%;height:100%;">
		<div class="easyui-tabs" data-options="fit:true,border:false,plain:true">
				<div title="基本信息" data-options="href:''" style="padding: 10px; width: 100%; height: 802px;padding:10px">
					<table style="margin:30px 20px 20px 20px;width:90%;line-height:30px;border:1px solid #ffffff; border-collapse: collapse;">
						<tr style="background:#f4f4f4;">
							<td style="width:50%;text-align: left;">理财产品名称</td>
							<td style="width:50%;text-align: left;">${finance.FINANCE_NAME}</td>
						</tr>
						<tr>
							<td style="width:50%;text-align: left;">理财产品代码</td>
							<td style="width:50%;text-align: left;">${finance.CODE}</td>
						</tr>
						<tr style="background:#f4f4f4;">
							<td style="width:50%;text-align: left;">理财产品类型</td>
							<td style="width:50%;text-align: left;">${finance.PRODUCT_TYPE}</td>
						</tr>
						<tr>
							<td style="width:50%;text-align: left;">理财产品状态</td>
							<td style="width:50%;text-align: left;">
								<c:if test="${finance.FINANCE_STATUS=='0'}">可售</c:if>
								<c:if test="${finance.FINANCE_STATUS=='1'}">不可售</c:if>
							</td>
						</tr>
						<tr style="background:#f4f4f4;">
							<td style="width:50%;text-align: left;">最大预期收益率</td>
							<td style="width:50%;text-align: left;">${finance.EXP_YIELD_MAX}</td>
						</tr>
						<tr>
							<td style="width:50%;text-align: left;">实际收益率</td>
							<td style="width:50%;text-align: left;">${finance.REAL_YIELD}</td>
						</tr>
						<tr style="background:#f4f4f4;">
							<td style="width:50%;text-align: left;">起息日</td>
							<td style="width:50%;text-align: left;">${finance.VALUE_DATE}</td>
						</tr>
						<tr>
							<td style="width:50%;text-align: left;">结息日</td>
							<td style="width:50%;text-align: left;">${finance.EXPIRY_DATE}</td>
						</tr>
						<tr style="background:#f4f4f4;">
							<td style="width:50%;text-align: left;">最小预期收益率</td>
							<td style="width:50%;text-align: left;">${finance.EXP_YIELD_MIN}</td>
						</tr>
						<tr>
							<td style="width:50%;text-align: left;">风险等级</td>
							<td style="width:50%;text-align: left;">${finance.RISK_LEVEL}</td>
						</tr>
						<tr style="background:#f4f4f4;">
							<td style="width:50%;text-align: left;">起售金额</td>
							<td style="width:50%;text-align: left;">${finance.START_MONEY}</td>
						</tr>
					</table>
				</div>
				<div title="业绩" data-options="onOpen:function(){financeachie();}" style="width: 100%; padding:10px">
					<div  id="finachievement" style="width:1000px;height:500px;padding-top:30px;"></div>
				</div>
				<div title="风险" data-options="onOpen:function(){financeRisk();}" style="padding:10px">
					<div id="financeYieldRatioLine" style="width:1000px;height:500px;padding-top:30px;"></div>
				</div>
		</div>
	</div>
	
<script type="text/javascript">
	function financeachie(){
		 var productCode=$('#fin_del_code').val();
		 var productName=$('#fin_del_name').val();
		 var bidName=$('#fin_del_bidname').val();
		 var bidCode=$('#fin_del_bidcode').val();
		
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/finance/toFinanceAchie",
			data:{code:productCode,bidcode:bidCode},
			timeout:20000,
			cache:false,
			success:function(data){
				console.log("=================");
				console.log(data);
				financeachieLine(productName,bidName,data.dates,data.yields);
			},
			error:function(){
				alert("请求失败");
			}
		});
	}
	
	function financeRisk(){
		var productCode=$('#fin_del_code').val();
		var productName=$('#fin_del_name').val();
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/finance/toFinanceYields",
			data:{code:productCode},
			timeout:20000,
			cache:false,
			success:function(data){
				financeYieldRatioLine(productName,data.yieldLst,data.dates);
			},
			error:function(){
				alert("请求失败");
			}
		});
	}
	
	
	
	
	
	 /*=============================理财涨幅|所属小类指数涨幅=============================================-  */
	 //基金、指数涨幅曲线
	function financeachieLine(productName,bidName,xLst,yLst){
		var myChart = echarts.init(document.getElementById('finachievement'));
			option = {
				    tooltip: {
				        trigger: 'axis'
				        /*formatter: '{a1}:{c1}'+'%'+'<br/>'+'{a1}:{c1}'+'%' */
				       /*  position: function (pt) {
				            return [pt[0], '10%'];
				        } */
				    },
				    title: {
				        x: 'center',
				        text: '产品涨幅走势',
				    },
				    legend: {
				    	orient: 'horizontal',
				         x: 'center',
				         y: 'bottom',
				        data:[productName,bidName]
				    },
				   /*  toolbox: {
					    show : true,
					   	x:'72%',
				        feature : {
				            mark : {show: true},
				            dataZoom : {show: true},
				            dataView : {show: true},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    }, */
				 dataZoom : {
					 	bottom:"5%", 
				        show : true,
				        realtime : true,
				        height: 20,
				        start : 0,
				        end : 100
				    },
				    grid: {
				        bottom: '10%',
				        containLabel: true
				 },
				    xAxis: {
				        type: 'category',
				        boundaryGap: false,
				        data: xLst
				    },
				    yAxis: {
				        type: 'value',
				        boundaryGap: [0, '100%']
				    },
				    series: [
				        {
				            name:productName,
				            type:'line',
				            smooth:true,
				            symbol: 'none',
				            sampling: 'average',
				            itemStyle: {
				                normal: {
				                	 color: '#d4726f'
				                }
				            },
				            areaStyle: {
				                normal: {
				                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
				                        offset: 0,
				                        color: '#d4726f'
				                    }, {
				                        offset: 1,
				                        color: '#d4726f'
				                    }])
				                }
				            },
				            data: yLst
				        },
				         {
				            name:bidName,
				            type:'line',
				            smooth:true,
				            symbol: 'none',
				            sampling: 'average',
				            itemStyle: {
				                normal: {
				                	 color: '#6e7d88'
				                }
				            },
				            areaStyle: {
				                normal: {
				                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
				                        offset: 0,
				                        color: '#6e7d88'
				                    }, {
				                        offset: 1,
				                        color: '#6e7d88'
				                    }])
				                }
				            },
				            data:yLst
				        }
				    ],
				    calculable:false
				};
		myChart.setOption(option);
	} 
	 
	 
	//收益率曲线
	function financeYieldRatioLine(productName,yList,xList){
		var myChart = echarts.init(document.getElementById('financeYieldRatioLine'));
			option = {
					tooltip: {
				        trigger: 'axis'
				    },
				    title: {
				        x: 'center',
				        text: '收益率曲线',
				    },
				  legend: {
				    	orient: 'horizontal',
				         x: 'center',
				         y: 'bottom',
				        data:[productName]
				    },
				   /*  toolbox: {
					    show : true,
					   	x:'72%',
				        feature : {
				            mark : {show: true},
				            dataZoom : {show: true},
				            dataView : {show: true},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    }, */
				 	dataZoom : {
					 	bottom:"5%", 
				        show : true,
				        realtime : true,
				        height: 20,
				        start : 0,
				        end : 100
				    },
				    grid: {
				        bottom: '10%',
				        containLabel: true
				 	},
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : false,
				            data : xList
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value'
				        }
				    ],
				    series : [
				        {
				            name:productName,
				            type:'line',
				            symbol:'none',
				            data:yList
				        }
				    ],
				    calculable:false
				};
		myChart.setOption(option);
	}
</script>
