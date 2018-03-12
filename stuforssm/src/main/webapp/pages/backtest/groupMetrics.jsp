<%--
  Created by IntelliJ IDEA.
  User: yongquan.xiong
  Date: 2018/2/2 0002
  Time: 下午 3:43
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>回归测试</title>
    <style>
.tdd2 tr{line-height: 30px;}
.tdd2 tr:nth-child(odd){border-bottom:1px solid #F4F4F4;}
.tdd2 tr:nth-child(even){background:#F4F4F4;}
.tdd2 tr td:nth-child(1){padding-left:13px;font-weight:bold}
.tdd2 tr td:nth-child(2){text-align:right;padding-right:13px}
.tdd2{width:100%; border:1px solid #000000; border-collapse: collapse;}


</style>
</head>
<div data-options="region:'north',title:'查询'"style="height: 40px; background: #F4F4F4;">
    <form id="metricsSearchForm">
        <table>
            <tr>
                <th>组合代码：</th>
                <td><input name="groupCode" id="metricsGroupCode"/></td>
                <th>开始时间</th>
                <td><input class="easyui-datebox" editable="false" name="startTime" id="metricsStartTime"/></td>
                <th>结束时间</th>
                <td><input class="easyui-datebox" editable="false" name="endTime" id="metricsEndTime"/></td>
                <th>初始金额</th>
                <td><input editable="false" name="initmoney" class="easyui-numberbox" ata-options="min:0,precision:2" id="metricsInitmoney"/></td>
                <th>再平衡</th>
                <td>
                	<select id="metricsRebalance" name="rebalance">
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
                <td><a class="easyui-linkbutton" href="javascript:void(0);" onclick="searchFunc();">分析</a></td>
                <td><a class="easyui-linkbutton" href="javascript:void(0);" onclick="clearSearch();">清空</a></td>
                <td><a class="easyui-linkbutton" href="javascript:void(0);"onclick="searchMetricsOtherInfo();">查看其他指标信息</a></td>
            </tr>
        </table>
    </form>
</div>

  <!-- <table id="metricsGroupList"></table> -->


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
		$('#metricsGroupCode').val(initGroupCode);
		$('#metricsStartTime').datebox('setValue', initStartTime);
		$('#metricsEndTime').datebox('setValue', initEndTime);
		$('#metricsRebalance').val(initRebalance);
		$('#metricsInitmoney').val(initInitmoney);
		searchFunc();
	}
});

//查看其他信息页面
function searchMetricsOtherInfo(){
	var groupCode=$('#metricsGroupCode').val();
    var startTime=$('#metricsStartTime').datebox('getValue');
    var endTime=$('#metricsEndTime').datebox('getValue');
   	var rebalance=$('#metricsRebalance').val();
    var initmoney=$('#metricsInitmoney').val();
    if(rebalance==""||groupCode==""||startTime==""||endTime==""||initmoney==""){
    	alert("所有选项为必填项");
    	return ;
    } 
    
	 $('#wu-tabs').tabs('close','组合资产分析');
	 $('#wu-tabs').tabs('close','组合回撤分析');
	 $('#wu-tabs').tabs('close','组合基本数据');
	 addTab("组合资产分析", "pages/backtest/groupAssets.jsp?groupCode='"+groupCode+"'&startTime='"+startTime+"'&endTime='"+endTime+"'&rebalance='"+rebalance+"'&initmoney='"+initmoney+"'", "icon-chart-organisation", false);
	 addTab("组合回撤分析", "pages/backtest/groupIndexAnalyse.jsp?groupCode='"+groupCode+"'&startTime='"+startTime+"'&endTime='"+endTime+"'&rebalance='"+rebalance+"'&initmoney='"+initmoney+"'", "icon-chart-organisation", false);
	 addTab("组合基本数据", "pages/backtest/test/groupBasic.jsp?groupCode='"+groupCode+"'&startTime='"+startTime+"'&endTime='"+endTime+"'&rebalance='"+rebalance+"'&initmoney='"+initmoney+"'", "icon-chart-organisation", false);
}


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

    //点击查找按钮出发事件
    function searchFunc() {
    	var groupCode=$('#metricsGroupCode').val();
    	var startTime=$("#metricsStartTime").datebox("getValue"); 
    	var endTime=$("#metricsEndTime").datebox("getValue"); 
    	var rebalance=$('#metricsRebalance').val();
        var initmoney=$('#metricsInitmoney').val();
       /*  alert(groupCode+","+startTime+","+endTime+","+rebalance+","+initmoney); */
    	 if(rebalance==""||groupCode==""||startTime==""||endTime==""||initmoney==""){
         	alert("所有选项为必填项");
         	return ;
         } 
    	
    	document.getElementById("dataTable").style.visibility = "visible";
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/backtest/getBacktestMetricsData",
            data: {groupCode:groupCode,startTime:startTime,endTime:endTime,rebalance:rebalance,initmoney:initmoney},
            timeout:60000,
            beforeSend: function () {
            	load();
            },
            complete: function () {
                disLoad();
            },
            success: function(result){
                //提示信息
                if("Failed to call python"==result[0].errorInfo){
                    disLoad();
                    alert("Failed to call python!");
                }

                dataInput(result[0]);
            },
            error:function(){
                alert("请求失败");
            }
        });
        
        
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

    //点击清空按钮出发事件
    function clearSearch() {
        $("#metricsGroupList").datagrid("load", {});//重新加载数据，无填写数据，向后台传递值则为空
        $("#metricsSearchForm").find("input").val("");//找到form表单下的所有input标签并清空
        $("#metricsSearchForm option:first").prop("selected", 'selected');
    }
    
    
</script>
