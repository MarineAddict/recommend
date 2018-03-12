<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 <style>
.tdd2 tr{line-height: 30px;}
.tdd2 tr:nth-child(odd){border-bottom:1px solid #F4F4F4;}
.tdd2 tr:nth-child(even){background:#F4F4F4;}
.tdd2 tr td:nth-child(1){padding-left:13px;font-weight:bold}
.tdd2 tr td:nth-child(2){text-align:right;padding-right:13px}
.tdd2{width:100%; border:1px solid #000000; border-collapse: collapse;}
</style>
<script src="<c:url value="/js/echars/echarts.js"/>"></script>
</head>
<div class="demo-info">
    <div class="demo-tip icon-tip"></div>
    <div data-options="region:'north',title:'查询'"style="height: 40px; background: #F4F4F4;">
        <form id="basicSearchForm">
            <table>
                <tr>
                    <!-- <th>组合名称：</th>
                    <td><input name="groupName" /></td> -->
                    <th>组合代码：</th>
                    <td><input id="backTestGroupCode" name="groupCode" /></td>
                    <th>开始时间</th>
                    <td><input class="easyui-datebox" editable="false" id="backTestStartTime" name="startTime" /></td>
                    <th>结束时间</th>
                    <td><input class="easyui-datebox" editable="false" id="backTestEndTime" name="endTime" /></td>
                    <th>初始金额</th>
                    <td><input editable="false" class="easyui-numberbox"  ata-options="min:0,precision:2" id="backTestInitmoney" name="initmoney" /></td>
                    <th>再平衡</th>
                    <td>
                        <select id="backTestRebalance" name="rebalance">
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
                        <select id="backTestbenchmark" name="rebalance">
                            <option value="" selected>请选择</option>
                            <option value="000001.SH">上证</option>
                            <option value="000300.SH">沪深300</option>
                            <option value="512500.SH">中证500</option>
                        </select>
                    </td>
                    <th>组合来源</th>
                    <td>
                        <select id="backTestGroupType" name="groupType">
                            <option value="" selected>请选择</option>
                            <option value="asset">资产小类</option>
                            <option value="financial">金融产品</option>
                            <option value="mvo">MVO模型</option>
                        </select>
                    </td>
					<th>时间类型</th>
					<td>
						<select id="backTestDateType" name="dateType">
							<option value="" selected>请选择</option>
							<option value="day">天</option>
							<option value="month">月</option>
						</select>
					</td>
                    <td><a class="easyui-linkbutton" href="javascript:void(0);"onclick="searchBackTestForm();">分析</a></td>
                    <td><a class="easyui-linkbutton" href="javascript:void(0);"onclick="clearSearch();">清空</a></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div style="margin:10px 0;"></div>
<div class="easyui-layout" style="width:100%;height:100%;">
        <div class="easyui-tabs" data-options="fit:true,border:false,plain:true">
            <div title="<a onclick='groupBasicAnalyse();'>组合基本数据</a>" data-options="href:''" style="padding:10px;">
                <div style="width:50%;float:left;height:300px;">
   					 <table id="groupList"></table>
				</div>
				<div id="productPercent" style="width:50%;float:right;height:300px;"></div>
				<div id="portfolioGrowth" style="width: auto;height:300px;visibility:hidden;"></div>
				<div id="yieldRatioLine" style="width: auto;height:300px;visibility:hidden;margin-top:300px;"></div>
                <div id="increaseLine" style="width: auto;height:300px;visibility:hidden;"></div>
            </div>
           <div title="<a onclick='groupMetrics();'>组合指标分析</a>" data-options="href:''" style="padding:10px">
				<div style="margin:30px 30px 30px 30px">
					<table class="tdd2" style="visibility:hidden;" id="dataTable">
					<tr>
						<td>平均回报(每月)</td>
						<td id="meanReturnMonthly"></td>
					</tr>
					 <tr>
						<td>平均回报(年化)</td>
					     <td id="meanReturnAnnualized"></td>
					</tr>
					<tr>
						<td>复合收益(每月)</td>
						<td id="compoundReturnMonthly"></td>
					</tr>
					<tr>
						<td>复合回报(年化)</td>
						<td id="compoundReturnAnnualized"></td>
					</tr>
					<tr>
						<td>波动(每月)</td>
						<td id="volatilityMonthly"></td>
					</tr>
					<tr>
						<td>波动性(年化)</td>
						<td id="volatilityAnnualized"></td>
					</tr>
					<tr>
						<td>下行偏差(月度)</td>
						<td id="downsideDeviationMonthly"></td>
					</tr>
					<tr>
						<td>最大亏损</td>
						<td id="worstYear"></td>
					</tr>
					<tr>
						<td>市场相关</td>
						<td id="marketCorrelation"></td>
					</tr>
					<tr>
						<td>Beta(*)</td>
						<td id="beta"></td>
					</tr>
					<tr>
						<td>阿尔法(年化)</td>
						<td id="alphaAnnualized"></td>
					</tr>
					<tr>
						<td>R^2</td>
						<td id="R2"></td>
					</tr>
					<tr>
						<td>夏普比率</td>
						<td id="sharpeRatio"></td>
					</tr>
					<tr>
						<td>Sortino比率</td>
						<td id="sortinoRatio"></td>
					</tr>
					<tr>
						<td>特雷诺比(％)</td>
						<td id="treynorRatio"></td>
					</tr>
					<tr>
						<td>多样化比例</td>
						<td id="diversificationRatio"></td>
					</tr>
					<tr>
						<td>偏态</td>
						<td id="skewness"></td>
					</tr>
					<tr>
						<td>超额峰值</td>
						<td id="excessKurtosis"></td>
					</tr>
					<tr>
						<td>历史风险价值(5％)</td>
						<td id="historicalValue"></td>
					</tr>
					<tr>
						<td>分析风险价值(5％)</td>
						<td id="deltaNormalValue"></td>
					</tr>
					<tr>
						<td>有条件的风险价值(5％)</td>
						<td id="conditionalValue"></td>
					</tr>
					<tr>
						<td>正期间</td>
						<td id="positivePeriods"></td>
					</tr>
					<tr>
						<td>收益/损失率</td>
						<td id="gainLossRatio">1.0</td>
					<tr>
					</table>
				</div>
            </div>
           <div title="<a onclick='searchGroupAssets();'>组合资产分析</a>" data-options="href:''" style="padding:10px">
	           	 <label id="table1" style="display:none;font-size:14px;font-weight:600;margin-top:10px;">投资组合</label>
					<div style="margin-top:10px;">
						<table id="table-1" style="width:90%;border-bottom-width: 1px;border-bottom-style: solid;border-bottom-color: rgb(211, 202, 221);border:none;  border-collapse:collapse;border-spacing:0;"></table>
					</div>
            </div>
             <div title="<a onclick='groupIndexAnalyse();'>组合回撤分析</a>" data-options="href:''" style="padding:10px">
	           	<div id="ga" style="width: 1200px;height:600px;"></div>
            </div>
        </div>
</div>
<script type="text/javascript">

var resultData;
var compareProductName;

//公用数据
var initGroupCode=<%=request.getParameter("groupCode")%>;
var initStartTime=<%=request.getParameter("startTime")%>;
var initEndTime=<%=request.getParameter("endTime")%>;
var initRebalance=<%=request.getParameter("rebalance")%>;
var initInitmoney=<%=request.getParameter("initmoney")%>;
var initGroupType=<%=request.getParameter("groupType")%>;
$(function(){
    resultData="";
    compareProductName="";
	if(initGroupCode!=null&&initStartTime==null&&initEndTime==null&&initRebalance==null&&initInitmoney==null){
			$('#backTestGroupCode').val(initGroupCode);
			$('#backTestGroupType').val(initGroupType);
			document.getElementById("backTestGroupType").disabled="disabled";
			createGroupListAndSheet(initGroupCode,null,null,null,null,initGroupType);
			getPieChartData(initGroupCode,null,null,null,null,initGroupType);
	}else if(initGroupCode!=null&&initStartTime!=null&&initEndTime!=null&&initRebalance!=null&&initInitmoney!=null){
		$('#backTestGroupCode').val(initGroupCode);
		$('#backTestStartTime').datebox('setValue', initStartTime);
		$('#backTestEndTime').datebox('setValue', initEndTime);
		$('#backTestRebalance').val(initRebalance);
		$('#backTestInitmoney').val(initInitmoney);
		searchBackTestForm();
	}
});

var sy = $.extend({}, sy);/*定义一个全局变量*/

sy.serializeObject = function (form) { /*将form表单内的元素序列化为对象，扩展Jquery的一个方法*/
    var o = {};
    $.each(form.serializeArray(), function (index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
};

var groupCode;
var startTime;
var endTime;
var rebalance;
var initmoney; 
var groupType;
var benchmark;
var backTestDateType;

//点击查找按钮出发事件
function searchBackTestForm() {
	groupCode=$('#backTestGroupCode').val();
    startTime=$('#backTestStartTime').datebox('getValue');
    endTime=$('#backTestEndTime').datebox('getValue');
   	rebalance=$('#backTestRebalance').val();
    initmoney=$('#backTestInitmoney').val();
    backTestDateType=$('#backTestDateType').val();
    groupType=initGroupType;
   /*  alert(groupCode+","+startTime+","+endTime+","+rebalance+","+initmoney+","+groupType); */
    if(rebalance==""||groupCode==""||startTime==""||endTime==""||initmoney==""||backTestDateType==""){
    	alert("所有选项为必填项");
    	return ;
    } 
	
   
	document.getElementById("portfolioGrowth").style.visibility = "visible";
	document.getElementById("yieldRatioLine").style.visibility = "visible";
    document.getElementById("increaseLine").style.visibility = "visible";
	
    /*获取组合基本信息*/
    /*获取组合收益等曲线图*/
    createGroupListAndSheet(groupCode,startTime,endTime,rebalance,initmoney,groupType);
    getGraphs(groupCode,startTime,endTime,rebalance,initmoney,groupType,backTestDateType);
}

function groupBasicAnalyse(){
    document.getElementById("portfolioGrowth").style.visibility = "visible";
    document.getElementById("yieldRatioLine").style.visibility = "visible";
    document.getElementById("increaseLine").style.visibility = "visible";
}

//点击清空按钮出发事件
function clearSearch() {
    $("#groupList").datagrid("reload", {});//重新加载数据，无填写数据，向后台传递值则为空
    $("#basicSearchForm").find("input").val("");//找到form表单下的所有input标签并清空
    $("#backTestRebalance option:first").prop("selected", 'selected');
}


function createGroupListAndSheet(groupCode,startTime,endTime,rebalance,initmoney,groupType){
	 $('#groupList').datagrid({
	        width : '100%',
	        url : "${pageContext.request.contextPath}/backtest/getBacktestBasicData",
	        loadMsg : '数据加载中,请稍后……',
	        pagination : true,
	        singleSelect : true,
	        rownumbers : true,
	        nowrap : true,
	        height : 'auto',
	        fit : true,
	        queryParams:{
	        	groupCode:groupCode,
	        	startTime:startTime,
	        	endTime:endTime,
	        	rebalance:rebalance,
	        	initmoney:initmoney,
	        	groupType:groupType
	        },
	        fitColumns : true,
	        striped : true,
	        idField : 'bondId',
	        pageSize : 10,
	        pageList : [ 10, 30, 50 ],
	        columns : [ [ {
	            field : 'productCode',
	            title : '代码',
	            width : 200,
	            align : 'center',
	        },{
	            field : 'productName',
	            title : '名称',
	            width : 200,
	            align : 'center',
	        }, {
	            field : 'weights',
	            title : '权重',
	            width : 200,
	            align : 'center'
	        } ] ]
	    });
}

function getPieChartData(groupCode,startTime,endTime,rebalance,initmoney,groupType){
	$.ajax({
	        type:"POST",
	        url:"${pageContext.request.contextPath}/backtest/getBacktestBasicData",
	        data:{groupCode:groupCode,startTime:startTime,endTime:endTime,rebalance:rebalance,initmoney:initmoney,groupType:groupType},
	        timeout:180000,
	        cache:false,
	        beforeSend: function () {
	        	load();
	        },
	        complete: function () {
	            disLoad();
	        },
	        success:function(data){
	            //提示信息
	            Piechart(data);
	        },
	        error:function(){
	            alert("请求失败");
	        }
	    }); 
}

function Piechart(result){
	var array = [];
	var arrayTitle=[];
	for(var i=0;i<result.length;i++){
		var map={};
		map.name=result[i].productName;
		map.value=result[i].weights;
		array[i]=map;
		arrayTitle.push(result[i].productName);
	}
	var myChart = echarts.init(document.getElementById('productPercent'));
	option = {
		    title : {
		        text: '',
		        subtext: '',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		   legend: {
		        orient : 'vertical',
		        x : 'left',
		        data:arrayTitle
		    },  
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel'],
		                option: {
		                    funnel: {
		                        x: '25%',
		                        width: '50%',
		                        funnelAlign: 'left',
		                        max: 1548
		                    }
		                }
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'产品占比',
		            type:'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:array,
		            itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: 'outer',
                                formatter: '{b} : ({d}%)'
                            }/* ,
                            labelLine: {
                                show: false
                            } */
                        }
                    }
		        }
		    ],
		    
		};
	myChart.setOption(option);    
}


function getGraphs(groupCode,startTime,endTime,rebalance,initmoney,groupType,dateType) {
	
	benchmark=$('#backTestbenchmark').val();
	if(benchmark=="000001.SH"){
		compareProductName="上证";
	}else if(benchmark=="000300.SH"){
		compareProductName="沪深300";
	}else if(benchmark=="512500.SH"){
		compareProductName="中证500";
	}

	
    //请求后台数据 收益率曲线
    $.ajax({
        type:"POST",
        url:"${pageContext.request.contextPath}/backtest/getBacktestMetricsData",
        data:{groupCode:groupCode,startTime:startTime,endTime:endTime,rebalance:rebalance,initmoney:initmoney,benchmark:benchmark,groupType:groupType,dateType:dateType},
        timeout:180000,
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
            resultData=data;
        	yieldRatioLine(data[0]);
            portfolioGrowth(data[0]);
            increaseLine(data[0]);
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
    var y2List=[];
    var xValue=data.monthEndMoneyDate;
    var yValue=data.monthRetValueArr;
    var y2Value=data.monthRetBenchmarkRetValueArr;
    for(var i=0;i<xValue.length;i++){
        xList.push(xValue[i]);
        yList.push(yValue[i]);
        y2List.push(y2Value[i]);
    }
    for(var i=0;i<y2Value.length;i++){
        y2List.push(y2Value[i]);
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
            text: '组合收益率曲线',
            subtext:'组合：'+minValue(yList)+"~"+maxValue(yList)+"    "+compareProductName+"："+minValue(y2List)+"~"+maxValue(y2List),
            subtextStyle: {
                color: '#383838'          // 副标题文字颜色
            },
        },
        legend: {
            data:['组合',compareProductName],
            x: 'left'
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
        dataZoom: [{
            type: 'inside',
            start: 0,
            end: 100
        }, {
            start: 0,
            end: 10,
            handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
            handleSize: '80%',
            handleStyle: {
                color: '#fff',
                shadowBlur: 3,
                shadowColor: 'rgba(0, 0, 0, 0.6)',
                shadowOffsetX: 2,
                shadowOffsetY: 2
            }
        }],
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
                name:'组合',
                type:'line',
                data:valueList,
            }, {
                name:compareProductName,
                type:'line',
                data:y2List
            }
        ],
        calculable:false
    };
    myChart.setOption(option);
}

//单位净值曲线
function portfolioGrowth(data){
    var xList=[];
    var yList=[];
    var y2List=[];
    var xValue=data.monthEndMoneyDate;
    var yValue=data.monthEndMoneyValueArr;
    var y2Value=data.monthEndBenchmarkMoneyValueArr;
    for(var i=0;i<xValue.length;i++){
        xList.push(xValue[i]);
        yList.push(yValue[i]);
    }
    for(var i=0;i<y2Value.length;i++){
    	y2List.push(y2Value[i]);
    }
    var myChart = echarts.init(document.getElementById('portfolioGrowth'));
    var dateList = xList;
    var valueList = yList;
    option = {
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['组合',compareProductName],
            x: 'left'
        },
        title: {
            left: 'center',
            text: '组合资产曲线',
            subtext:'组合：'+minValue(yList)+"~"+maxValue(yList)+"    "+compareProductName+"："+minValue(y2List)+"~"+maxValue(y2List),
            subtextStyle: {
                color: '#383838'          // 副标题文字颜色
            },
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
        dataZoom: [{
            type: 'inside',
            start: 0,
            end: 100
        }, {
            start: 0,
            end: 10,
            handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
            handleSize: '80%',
            handleStyle: {
                color: '#fff',
                shadowBlur: 3,
                shadowColor: 'rgba(0, 0, 0, 0.6)',
                shadowOffsetX: 2,
                shadowOffsetY: 2
            }
        }],
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : dateList,
            }
        ],
        yAxis : [
            {
                type : 'value',
               	min:Math.floor(minArrayValue(minValue(y2List),minValue(yList)))
            }
        ],
        series : [
            {
                name:'组合',
                type:'line',
                data:valueList,
            }, {
                name:compareProductName,
                type:'line',
                data:y2List
            }
        ],
        calculable:false
    };
    myChart.setOption(option);
}

//涨幅曲线
//产品业绩曲线
function increaseLine(data){

    var xValue=data.monthEndMoneyDate;
    var yValue=data.monthMoneyGrowth;
    var y2Value=data.benchmarkMoneyGrowth;

    var myChart = echarts.init(document.getElementById('increaseLine'));
    option = {
        title: {
            text: '组合涨幅走势',
            left:'center',
            subtext:'组合：'+minValue(yValue)+"~"+maxValue(yValue)+"    "+compareProductName+"："+minValue(y2Value)+"~"+maxValue(y2Value),
            subtextStyle: {
                color: '#383838'          // 副标题文字颜色
            },
        },
        tooltip : {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        legend: {
            data:["组合",compareProductName],
            x: 'left'
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
        dataZoom: [{
            type: 'inside',
            start: 0,
            end: 100
        }, {
            start: 0,
            end: 10,
            handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
            handleSize: '80%',
            handleStyle: {
                color: '#fff',
                shadowBlur: 3,
                shadowColor: 'rgba(0, 0, 0, 0.6)',
                shadowOffsetX: 2,
                shadowOffsetY: 2
            }
        }],
        grid: {

            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : xValue
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:"组合",
                type:'line',
                stack: '总量',
                areaStyle: {normal: {}},
                data:yValue
            },
            {
                name:compareProductName,
                type:'line',
                stack: '总量',
                areaStyle: {normal: {}},
                data:y2Value
            }
        ]
    };
    myChart.setOption(option);
}




//-----------------------------------------------------组合指标分析-----------------------------------//
 function groupMetrics() {
    	document.getElementById("dataTable").style.visibility = "visible";
    	dataInput(resultData[0]);
    }
//填充数据
 function dataInput(data){
 	console.log(data);
 	$('#meanReturnMonthly').text((Number(data.meanReturnMonthly)*100).toFixed(2)+"%");
 	$('#meanReturnAnnualized').text((Number(data.meanReturnAnnualized)*100).toFixed(2)+"%");
 	$('#compoundReturnMonthly').text((Number(data.compoundReturnMonthly)*100).toFixed(2)+"%");
 	$('#compoundReturnAnnualized').text((Number(data.compoundReturnAnnualized)*100).toFixed(2)+"%");
 	$('#volatilityMonthly').text((Number(data.volatilityMonthly)*100).toFixed(2)+"%");
 	$('#volatilityAnnualized').text((Number(data.volatilityYearly)*100).toFixed(2)+"%");
 	$('#downsideDeviationMonthly').text((Number(data.downsideDeviationMonthly)*100).toFixed(2)+"%");
 	$('#worstYear').text((Number(data.worstYear)*100).toFixed(2)+"%");
 	$('#marketCorrelation').text(Number(data.marketCorrelation).toFixed(2));
 	$('#beta').text(Number(data.beta).toFixed(2));
 	$('#alphaAnnualized').text((Number(data.alphaAnnualized)*100).toFixed(2)+"%");
 	$('#R2').text((Number(data.r2)*100).toFixed(2)+"%");
 	$('#sharpeRatio').text(Number(data.sharpeRatio).toFixed(2));
 	$('#sortinoRatio').text(Number(data.sortinoRatio).toFixed(2));
 	$('#treynorRatio').text(Number(data.treynorRatio).toFixed(2));
 	$('#diversificationRatio').text(Number(data.diversificationRatio).toFixed(2));
 	$('#skewness').text(Number(data.skewness).toFixed(2));
 	$('#excessKurtosis').text(Number(data.excessKurtosis).toFixed(2));
 	$('#historicalValue').text((Number(data.historicalValue)*100).toFixed(2)+"%");
 	$('#deltaNormalValue').text((Number(data.deltaNormalValue)*100).toFixed(2)+"%");
 	$('#conditionalValue').text((Number(data.conditionalValue)*100).toFixed(2)+"%");
 	$('#positivePeriods').text((Number(data.positivePeriods)*100).toFixed(2)+"%");
 	$('#gainLossRatio').text(Number(data.gainLossRatio).toFixed(2));
 }	
 
//-----------------------------------------------------组合资产分析-----------------------------------//

//点击查找按钮出发事件
function searchGroupAssets() {
	 $("#table-1").empty();
	 createTable(resultData[0].assetIndexTitleArr,resultData[0].assetIndexValuesList);
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


function groupIndexAnalyse(){
	 getItermAnalyse(resultData[0].monthEndMoneyDate,resultData[0].drawdownsArr,resultData[0].monthBenchmarkDrawdownArr);
}


function getItermAnalyse(xdata,ydata,y2data){
	 var groupchart1 = echarts.init(document.getElementById('ga'));
	             option = {
	            	title : {
	     			        text: '组合指标分析图',
	     			        x: 'center',
	     			        align: 'right',
                            subtext:'组合：'+minValue(ydata)+"~"+maxValue(ydata)+"    "+compareProductName+"："+minValue(y2data)+"~"+maxValue(y2data),
                            subtextStyle: {
                                color: '#383838'          // 副标题文字颜色
                            },
	     			 },
	                 tooltip: {
	                     trigger: 'axis',
	                     axisPointer: {
	                         animation: false
	                     }
	                 },
	                 legend: {
	                     data:['组合',compareProductName],
	                     x: 'left'
	                 },
	                 toolbox: {
	                     feature: {
	                         dataZoom: {
	                             yAxisIndex: 'none'
	                         },
	                         restore: {},
	                         saveAsImage: {}
	                     }
	                 },
	                 dataZoom: [{
	                     type: 'inside',
	                     start: 0,
	                     end: 100
	                 }, {
	                     start: 0,
	                     end: 10,
	                     handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
	                     handleSize: '80%',
	                     handleStyle: {
	                         color: '#fff',
	                         shadowBlur: 3,
	                         shadowColor: 'rgba(0, 0, 0, 0.6)',
	                         shadowOffsetX: 2,
	                         shadowOffsetY: 2
	                     }
	                 }],
	                 grid: {
	                     left: 50,
	                     right: 50
	                     /* height: '65%' */
	                 },
	                 xAxis : [
	                     {
	                         type : 'category',
	                         boundaryGap : false,
	                         axisLine: {onZero: true},
	                         data: xdata,
	                         position: 'bottom'
	                     }
	                 ],
	                 yAxis : [
	                    {
	                         type : 'value',
	                         name : '指标',
	                         nameLocation:'end',
	                         max : 0,
	                       /*   min:-1, */
	                         inverse: false
	                     }
	                 ],
	                 series : [
	                     {
	                         name:'组合',
	                         type:'line',
	                         symbol:'none',
	                         symbolSize: 8,
	                         hoverAnimation: false,
	                         itemStyle: {
	                             normal: {
	                                 color: 'rgb(255, 70, 131)'
	                             }
	                         },
	                         areaStyle: {
	                             normal: {
	                                 color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
	                                     offset: 0,
	                                     color: 'rgb(255, 158, 68)'
	                                 }, {
	                                     offset: 1,
	                                     color: 'rgb(255, 70, 131)'
	                                 }])
	                             }
	                         },
	                         data: ydata
	                     },
	                     {
	                         name:compareProductName,
	                         type:'line',
	                         symbol:'none',
	                         symbolSize: 8,
	                         hoverAnimation: false,
	                         itemStyle: {
	                             normal: {
	                                 color: 'rgb(65, 97, 99)'
	                             }
	                         },
	                         areaStyle: {
	                             normal: {
	                                 color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
	                                     offset: 0,
	                                     color: 'rgb(109, 163, 137)'
	                                 }, {
	                                     offset: 1,
	                                     color: 'rgb(65, 97, 99)'
	                                 }])
	                             }
	                         },
	                         data: y2data
	                     }
	                 ]
	             };
	 
	 
	 groupchart1.setOption(option);
}

</script>
