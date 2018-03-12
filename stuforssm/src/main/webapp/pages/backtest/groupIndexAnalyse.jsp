<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<c:url value="/js/echars/echarts.js"/>"></script>
<div data-options="region:'north',title:'查询'"
		style="height: 40px; background: #F4F4F4;">
		<form id="analyseSearchForm">
			<table>
				<tr>
				<th>组合代码：</th>
                <td><input name="groupCode" id="analyseGroupCode"/></td>
                <th>开始时间</th>
                <td><input class="easyui-datebox" editable="false" name="startTime" id="analyseStartTime"/></td>
                <th>结束时间</th>
                <td><input class="easyui-datebox" editable="false"name="endTime" id="analyseEndTime"/></td>
                <th>初始金额</th>
                <td><input editable="false"name="initmoney" class="easyui-numberbox" ata-options="min:0,precision:2" id="analyseInitmoney"/></td>
                <th>再平衡</th>
                <td>
                	<select id="analyseRebalance" name="rebalance">
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
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="searchIndexAnalyse();">分析</a></td>
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="clearIndexAnalyse();">清空</a></td>
				<td><a class="easyui-linkbutton" href="javascript:void(0);"onclick="searchAnalyseOtherInfo();">查看其他指标信息</a></td>
				</tr>
			</table>
		</form>
	</div>

<div id="ga" style="width: 1200px;height:600px;">


</div>
<script>

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
		$('#analyseGroupCode').val(initGroupCode);
		$('#analyseStartTime').datebox('setValue', initStartTime);
		$('#analyseEndTime').datebox('setValue', initEndTime);
		$('#analyseRebalance').val(initRebalance);
		$('#analyseInitmoney').val(initInitmoney);
		searchIndexAnalyse();
	}
});


function searchIndexAnalyse(){
	var groupCode=$('#analyseGroupCode').val();
	var startTime=$("#analyseStartTime").datebox("getValue"); 
	var endTime=$("#analyseEndTime").datebox("getValue"); 
	var rebalance=$('#analyseRebalance').val();
    var initmoney=$('#analyseInitmoney').val();
   /*  alert(groupCode+","+startTime+","+endTime+","+rebalance+","+initmoney); */
	 if(rebalance==""||groupCode==""||startTime==""||endTime==""||initmoney==""){
     	alert("所有选项为必填项");
     	return ;
     } 
	 indexAnalyseCharts(groupCode,startTime,endTime,rebalance,initmoney);
	/* document.getElementById("ga").style.visibility = "visible"; */
}

//查看其他信息页面
function searchAnalyseOtherInfo(){
	var groupCode=$('#analyseGroupCode').val();
    var startTime=$('#analyseStartTime').datebox('getValue');
    var endTime=$('#analyseEndTime').datebox('getValue');
   	var rebalance=$('#analyseRebalance').val();
    var initmoney=$('#analyseInitmoney').val();
    if(rebalance==""||groupCode==""||startTime==""||endTime==""||initmoney==""){
    	alert("所有选项为必填项");
    	return ;
    } 
    
	 $('#wu-tabs').tabs('close','组合指标分析');
	 $('#wu-tabs').tabs('close','组合资产分析');
	 $('#wu-tabs').tabs('close','组合基本数据');
	 addTab("组合指标分析", "pages/backtest/groupMetrics.jsp?groupCode='"+groupCode+"'&startTime='"+startTime+"'&endTime='"+endTime+"'&rebalance='"+rebalance+"'&initmoney='"+initmoney+"'", "icon-chart-organisation", false);
	 addTab("组合资产分析", "pages/backtest/groupAssets.jsp?groupCode='"+groupCode+"'&startTime='"+startTime+"'&endTime='"+endTime+"'&rebalance='"+rebalance+"'&initmoney='"+initmoney+"'", "icon-chart-organisation", false);
	 addTab("组合基本数据", "pages/backtest/test/groupBasic.jsp?groupCode='"+groupCode+"'&startTime='"+startTime+"'&endTime='"+endTime+"'&rebalance='"+rebalance+"'&initmoney='"+initmoney+"'", "icon-chart-organisation", false);
}

function indexAnalyseCharts(groupCode,startTime,endTime,rebalance,initmoney){
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

			getItermAnalyse(data[0].monthEndMoneyDate,data[0].drawdownsArr);
		},
		error:function(){
			alert("请求失败");
		}
	});
}

function clearIndexAnalyse(){
	 $("#analyseSearchForm").find("input").val("");//找到form表单下的所有input标签并清空
	 $("#analyseSearchForm option:first").prop("selected", 'selected');
}


/* $(document).ready(function(){

	getItermAnalyse();
});
 */
 function getItermAnalyse(xdata,ydata){
	 var groupchart1 = echarts.init(document.getElementById('ga'));
	             option = {
	            	title : {
	     			        text: '组合指标分析图',
	     			        x: 'center',
	     			        align: 'right'
	     			 },
	                 tooltip: {
	                     trigger: 'axis',
	                     axisPointer: {
	                         animation: false
	                     }
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
	                         name:'指标',
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
	                     }
	                 ]
	             };
	 
	 
	 groupchart1.setOption(option);
 }
 
 
/*  function getItermAnalyse(xdata,ydata){
		var groupchart1 = echarts.init(document.getElementById('ga'));
		option = {
			    title : {
			        text: '组合指标分析图',
			        x: 'center',
			        align: 'right'
			    },
			    grid: {
			        bottom: 80
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
			    tooltip : {
			        trigger: 'axis',
			        axisPointer: {
			            type: 'cross',
			            animation: false,
			            label: {
			                backgroundColor: '#505765'
			            }
			        }
			    },
			    dataZoom: [
			        {
			            show: true,
			            realtime: true,
			            start: 65,
			            end: 85
			        },
			        {
			            type: 'inside',
			            realtime: true,
			            start: 65,
			            end: 85
			        }
			    ],
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : false,
			            axisLine: {onZero: false},
			            data : xdata.map(function (str) {
			                return str.replace(' ', '\n');
			            })
			        }
			    ],
			    yAxis: [
			        {
			            type: 'value',
			            max: 0,
			            min:-0.1,
			            inverse: false
			        },
			        {
			            name: '指标',
			            nameLocation: 'end',
			            max:0,
			            type: 'value',
			            inverse: false
			        }
			    ],
			    series: [
			        {
			            name:'指标',
			            type:'line',
			            symbol:'none',
			            yAxisIndex:1,
			            animation: false,
			            areaStyle: {
			                normal: {}
			            },
			            lineStyle: {
			                normal: {
			                    width: 1
			                }
			            },
			            markArea: {
			                silent: true,
			                data: [[{
			                    xAxis: '2009/9/10\n7:00'
			                }, {
			                    xAxis: '2009/9/20\n7:00'
			                }]]
			            },
			            data: ydata
			        }
			    ]
			};

		groupchart1.setOption(option);
		
	} */

</script>

