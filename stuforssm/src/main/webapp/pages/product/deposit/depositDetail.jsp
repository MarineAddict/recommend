<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<c:url value="/js/echars/echarts.js"/>"></script>
</head>
	<div class="easyui-layout" style="width:100%;height:100%;">
			<div class="easyui-tabs" data-options="fit:true,border:false,plain:true">
				<div title="基本信息" data-options="href:''" style="padding: 10px; width: 100%; height: 802px;padding:10px">
					<table class="tdmd" id="fddmeta" style="margin:30px 20px 20px 20px;width:90%;line-height:30px;border:1px solid #ffffff; border-collapse: collapse;">
						<tr style="background:#f4f4f4;">
							<td style="width:50%;text-align: left;">存单名称</td>
							<td id="deposit_name" style="width:50%;text-align: left;">${deposit.name}</td>
						</tr>
						<tr>
							<td style="width:50%;text-align: left;">存单代码</td>
							<td id="deposit_code" style="width:50%;text-align: left;">${deposit.prdCode}</td>
						</tr>
						<tr style="background:#f4f4f4;">
							<td style="width:50%;text-align: left;">资产类别</td>
							 <td id="asset_type" style="width:50%;text-align: left;">${deposit.assetType}</td> 
						</tr>
						<tr>
							<td style="width:50%;text-align: left;">起售日期</td>
							
							<td id="sale_start_day" style="width:50%;text-align: left;"> ${deposit.subStartDay}</td>
						</tr>
						<tr style="background:#f4f4f4;">
							<td style="width:50%;text-align: left;">停售日期</td>
							<td id="sale_halt_day" style="width:50%;text-align: left;">${deposit.subEndDay}</td>
						</tr>
						<tr>
							<td style="width:50%;text-align: left;">起息日</td>
							<td id="interest_start_date" style="width:50%;text-align: left;">${deposit.intStartDay}</td>
						</tr>
						<tr style="background:#f4f4f4;">
							<td style="width:50%;text-align: left;">到息日</td>
							<td id="interest_end_date" style="width:50%;text-align: left;">${deposit.intEndDay}</td>
						</tr>
							<tr>
							<td style="width:50%;text-align: left;">期限</td>
							<td id="term" style="width:50%;text-align: left;">${deposit.term}个月</td>
						</tr>
						<tr>
							<td style="width:50%;text-align: left;">年利率</td>
							<td id="interest" style="width:50%;text-align: left;">${deposit.interest}</td>
						</tr>
						
						
						<%-- <tr>
							<td style="width:50%;text-align: left;">基金状态</td>
							<td id="fddstatus" style="width:50%;text-align: left;">
								<c:if test="${fund.STATUS =='1' }">封闭期</c:if>
								<c:if test="${fund.STATUS =='2' }">暂停申购</c:if>
								<c:if test="${fund.STATUS =='3' }">暂停大额申购</c:if>
								<c:if test="${fund.STATUS =='4' }">开放申购</c:if>
							</td>
						</tr>
						<tr style="background:#f4f4f4;">
							<td style="width:50%;text-align: left;">管理费率</td>
							<td id="managfeeratio" style="width:50%;text-align: left;">${fund.MANAGFEERATIO}</td>
						</tr>
						 <tr>
							<td>投资类型一</td>
						    <td id="invtypone" >
						    	<c:if test="${fund.INVTYPONE =='1' }">混合型基金</c:if>
								<c:if test="${fund.INVTYPONE =='2' }">债券型基金</c:if>
								<c:if test="${fund.INVTYPONE =='3' }">股票型基金</c:if>
								<c:if test="${fund.INVTYPONE =='4' }">货币市场型基金</c:if>
								<c:if test="${fund.INVTYPONE =='5' }">其他基金</c:if>
						    </td>
						</tr>
						<tr style="background:#f4f4f4;">
							<td>投资类型二</td>
							<td id="invtyptwo">${fund.INVTYPTWO}</td>
						</tr>
						 <tr>
							<td>销售服务费</td>
							<td id="servicefee">${fund.SERVICEFEE}</td>
						</tr>
						<tr  style="background:#f4f4f4;">
							<td>风险类型</td>
							<td id="fundrisktyp">${fund.RISKLEVEL}</td>
						</tr>
						 <tr>
							<td>主题类型</td>
							<td id="choicetyp">${fund.CHOICETYP}</td>
						</tr>
						<tr  style="background:#f4f4f4;">
							<td>晨星基金类型</td>
							<td id="cxfundtyp">${fund.CXTYP}</td>
						</tr>
						 <tr>
							<td>申购费率前端</td>
							<td id="purchfeeratio_front">${fund.PURCHFEERATIO_FRONT}</td>
						</tr>
						<tr  style="background:#f4f4f4;">
							<td>申购费率后端</td>
							<td id="purchfeeratio_back_end">${fund.PURCHFEERATIO_BACK_END}</td>
						</tr>
						 <tr>
							<td>赎回费率前端</td>
							<td id="redemfeeratio_front">${fund.REDEMFEERATIO_FRONT}</td>
						</tr>
						<tr  style="background:#f4f4f4;">
							<td>赎回费率后端</td>
							<td id="redemfeeratio_back_end">${fund.REDEMFEERATIO_BACK_END}</td>
						</tr> --%>
						<tr style="background:#f4f4f4;">
							<td>跟踪标的</td>
							 <td id="deposite_traceName">${deposit.traceName}</td> 
						</tr>
						<tr >
							<td>标的代码</td>
							<%-- <td id="bidcode">${fund.BIDCODE}</td> --%>
						</tr>
						 <tr style="background:#f4f4f4;">
							<td>跟踪误差</td>
							<%-- <td id="gend">${fund.trackError}</td> --%>
						</tr>
					</table>
				</div>
				
				<!-- <div title="贝塔评分" data-options="href:''" style="padding:10px">
					<p>测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试
						测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试</p>
				</div> -->
				<div title="业绩" data-options="onOpen:function(){fddachie();}" style="padding: 10px; width: 100%; padding:10px">
					<%-- <div id="timeSearch" style="margin-left: 25%;">
						<form name="form1">
							选择时间
							<input type="radio" id="1month" name="radiobutton" value="1month"> <label for="1month">1月</label>
							<input type="radio" id="3month" name="radiobutton" value="3month"> <label for="3month">3月</label>
							<input type="radio" id="6month" name="radiobutton" value="6month"><label for="6month">6月</label>
							<input type="radio" id="1year" name="radiobutton" value="1year"><label for="1year">1年</label>
							<input type="radio" id="3year" name="radiobutton" value="3year"><label for="3year">3年</label>
							<input type="radio" id="5year" name="radiobutton" value="5year"><label for="5year">5年</label>
							<input type="radio" id="thisyear" name="radiobutton" value="thisyear"><label for="thisyear">近年来</label>
							<input type="radio" id="maxtime" name="radiobutton" value="maxtime"  checked><label for="maxtime">最大</label>
							<input type="radio" id="customized" name="radiobutton" value="customized"><label for="customized">自定义查询</label>
						</form>
					</div> --%>
					<%--<a href="#" style="margin-left: 60%;" class="easyui-linkbutton" onclick="customizedSearch();" >切换查询</a>--%>
					<!-- <div id="customizediv" style="margin-left:-20%;visibility:hidden;">
						<table style="align:center;margin:20px auto">
							<tr>
								<td> 开始时间：</td>
								<td> <input id="fundDetailStartTime" class="easyui-datebox" editable="false"></td>
								<td> &nbsp;&nbsp;结束时间：</td>
								<td> <input id="fundDetailEndTime" class="easyui-datebox" editable="false"></td>
								<td> &nbsp;&nbsp; <a href="#" class="easyui-linkbutton"  onclick="fundDetailSearch();" >查询</a></td>
							</tr>
						</table>
					</div> -->
					<div  id="deposit_increase_performance" style="width:1000px;height:500px;padding-top:30px;"></div>
					<br>
				</div>

	</div>
	
<script type="text/javascript">

//页面加载涨幅曲线
   $().ready(function(){
	   var deposit_code=$("#deposit_code").text();
	   $.ajax({
		   url: "${pageContext.request.contextPath}/deposit/depositIncreasePerformance",
		   data: {depositCode:deposit_code},
		   success: function(data){
                console.log(data);
                increasePerformanceLine(data.bidIncreaseData,data.depositIncreaseData,data.timeData,data.prdName,data.bidName);
			   },
		 });
	   })
	
	 /*=============================产品涨幅|所属小类指数涨幅 、单位净值走势曲线=============================================-  */
	 //涨幅标的曲线
	function increasePerformanceLine(bidIncreaseData,depositIncreaseData,timeData,prdName,bidName){
		var myChart = echarts.init(document.getElementById('deposit_increase_performance'));
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
				        text: '理财涨幅走势',
				    },
				    legend: {
				    	orient: 'horizontal',
				         x: 'center',
				         y: 'bottom',
				        data:[prdName,bidName]
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
				        data: timeData
				    },
				    yAxis: {
				        type: 'value',
				        boundaryGap: [0, '100%'],
				        max: function(value) {
				            return Math.ceil(value.max);
				        }
				    },
				    series: [
				        {
				            name:prdName,
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
				            data: depositIncreaseData
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
				            data:bidIncreaseData
				        }
				    ],
				    calculable:false
				};

		myChart.setOption(option);
	} 
	 

</script>
