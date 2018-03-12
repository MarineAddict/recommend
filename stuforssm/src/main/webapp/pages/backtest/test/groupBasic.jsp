
<%--<html>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>回归测试</title>
    <script src="<c:url value="/js/echars/echarts.js"/>"></script>
</head>
<%--<body>--%>
<div data-options="region:'north',title:'查询'"style="height: 40px; background: #F4F4F4;">
    <form id="basicSearchForm">
        <table>
            <tr>
                <!-- <th>组合名称：</th>
                <td><input name="groupName" /></td> -->
                <th>组合代码：</th>
                <td><input id="basicGroupCode" name="groupCode" /></td>
                <th>开始时间</th>
                <td><input class="easyui-datebox" editable="false" id="basicStartTime" name="startTime" /></td>
                <th>结束时间</th>
                <td><input class="easyui-datebox" editable="false" id="basicEndTime" name="endTime" /></td>
                <th>初始金额</th>
                <td><input editable="false" class="easyui-numberbox"  ata-options="min:0,precision:2" id="basicInitmoney" name="initmoney" /></td>
                <th>再平衡</th>
                <td>
                	<select id="basicRebalance" name="rebalance">
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
                
                <td><a class="easyui-linkbutton" href="javascript:void(0);"onclick="searchBasicForm();">分析</a></td>
                <td><a class="easyui-linkbutton" href="javascript:void(0);"onclick="clearSearch();">清空</a></td>
                <td><a class="easyui-linkbutton" href="javascript:void(0);"onclick="searchBasicOtherInfo();">查看其他指标信息</a></td>
            </tr>
        </table>
    </form>
</div>
<div style="width: auto;height:200px;">
    <table id="groupList" ></table>
</div>

<div id="portfolioGrowth" style="width: auto;height:300px;visibility:hidden;"></div>
<div id="yieldRatioLine" style="width: auto;height:300px;visibility:hidden;"></div>

<script type="text/javascript">

	var initGroupCode=<%=request.getParameter("groupCode")%>;
	var initStartTime=<%=request.getParameter("startTime")%>;
	var initEndTime=<%=request.getParameter("endTime")%>;
	var initRebalance=<%=request.getParameter("rebalance")%>;
	var initInitmoney=<%=request.getParameter("initmoney")%>;
	var initGroupType=<%=request.getParameter("groupType")%>;
	$(function(){
		if(initGroupCode!=null&&initStartTime==null&&initEndTime==null&&initRebalance==null&&initInitmoney==null){
				$('#basicGroupCode').val(initGroupCode);
				$('#basicGroupType').val(initGroupType);
				createGroupList(initGroupCode,null,null,null,null,initGroupType);
		}else if(initGroupCode!=null&&initStartTime!=null&&initEndTime!=null&&initRebalance!=null&&initInitmoney!=null){
			$('#basicGroupCode').val(initGroupCode);
			$('#basicStartTime').datebox('setValue', initStartTime);
			$('#basicEndTime').datebox('setValue', initEndTime);
			$('#basicRebalance').val(initRebalance);
			$('#basicInitmoney').val(initInitmoney);
			searchBasicForm();
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
	
   
    
    //点击查找按钮出发事件
    function searchBasicForm() {
    	var groupCode=$('#basicGroupCode').val();
        var startTime=$('#basicStartTime').datebox('getValue');
        var endTime=$('#basicEndTime').datebox('getValue');
       	var rebalance=$('#basicRebalance').val();
        var initmoney=$('#basicInitmoney').val();
        var groupType=$('#basicGroupType').val();
       /*  alert(groupCode+","+startTime+","+endTime+","+rebalance+","+initmoney); */
        if(rebalance==""||groupCode==""||startTime==""||endTime==""||initmoney==""){
        	alert("所有选项为必填项");
        	return ;
        } 
    	
    	document.getElementById("portfolioGrowth").style.visibility = "visible";
    	document.getElementById("yieldRatioLine").style.visibility = "visible";
    	
        /*获取组合基本信息*/
        /*获取组合收益等曲线图*/
        createGroupList(groupCode,startTime,endTime,rebalance,initmoney,groupType);
        getGraphs(groupCode,startTime,endTime,rebalance,initmoney,groupType);
    }

    //点击清空按钮出发事件
    function clearSearch() {
        $("#groupList").datagrid("reload", {});//重新加载数据，无填写数据，向后台传递值则为空
        $("#basicSearchForm").find("input").val("");//找到form表单下的所有input标签并清空
        $("#basicRebalance option:first").prop("selected", 'selected');
    }
    
    //查看其他信息页面
    function searchBasicOtherInfo(){
    	var groupCode=$('#basicGroupCode').val();
        var startTime=$('#basicStartTime').datebox('getValue');
        var endTime=$('#basicEndTime').datebox('getValue');
       	var rebalance=$('#basicRebalance').val();
        var initmoney=$('#basicInitmoney').val();
        if(rebalance==""||groupCode==""||startTime==""||endTime==""||initmoney==""){
        	alert("所有选项为必填项");
        	return ;
        } 
        
    	 $('#wu-tabs').tabs('close','组合指标分析');
    	 $('#wu-tabs').tabs('close','组合资产分析');
    	 $('#wu-tabs').tabs('close','组合回撤分析');
    	 addTab("组合指标分析", "pages/backtest/groupMetrics.jsp?groupCode='"+groupCode+"'&startTime='"+startTime+"'&endTime='"+endTime+"'&rebalance='"+rebalance+"'&initmoney='"+initmoney+"'", "icon-chart-organisation", false);
    	 addTab("组合资产分析", "pages/backtest/groupAssets.jsp?groupCode='"+groupCode+"'&startTime='"+startTime+"'&endTime='"+endTime+"'&rebalance='"+rebalance+"'&initmoney='"+initmoney+"'", "icon-chart-organisation", false);
    	 addTab("组合回撤分析", "pages/backtest/groupIndexAnalyse.jsp?groupCode='"+groupCode+"'&startTime='"+startTime+"'&endTime='"+endTime+"'&rebalance='"+rebalance+"'&initmoney='"+initmoney+"'", "icon-chart-organisation", false);
    }
    
    function createGroupList(groupCode,startTime,endTime,rebalance,initmoney,groupType){
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
    	            title : '产品代码',
    	            width : 200,
    	            align : 'center',
    	        },{
    	            field : 'productName',
    	            title : '产品名称',
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
    
   
    
    /* $(document).ready(function () {  
    	getGraphs();  
    });    */
    function getGraphs(groupCode,startTime,endTime,rebalance,initmoney,groupType) {
    	
    	var benchmark=$('#benchmark').val();
    	
        //请求后台数据 收益率曲线
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/backtest/getBacktestMetricsData",
            data:{groupCode:groupCode,startTime:startTime,endTime:endTime,rebalance:rebalance,initmoney:initmoney,benchmark:benchmark,groupType:groupType},
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
            	yieldRatioLine(data[0]);
                portfolioGrowth(data[0]);
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
                start : 0,
                end : 100
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
                    name:'组合',
                    type:'line',
                    data:valueList,
                }, {
                    name:'基准',
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
            title: {
                left: 'center',
                text: '组合资产曲线',
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
                start : 0,
                end : 100
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
                    name:'基准',
                    type:'line',
                    data:y2List
                }
            ],
            calculable:false
        };
        myChart.setOption(option);
    }

</script>
<%--</body>--%>
<%--</html>--%>


