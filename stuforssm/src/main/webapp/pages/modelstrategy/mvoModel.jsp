<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
table{
width:1000px;
}
</style>
</head>
<body>
	<div data-options="region:'north',title:'设置'"
		style="height: 40px; background: #F4F4F4;">
		<form id="searchForm">
			<table>
				<tr>
					<th>开始时间</th>
					<td>
						<input class="easyui-datebox" editable="false" id = "mvoStartTime" name="" />
					</td>
					<th>结束时间</th>
					<td>
						<input class="easyui-datebox" editable="false" id = "mvoEndTime" name="" /></td>
					<!-- <th>取值频率</th>
					<td><select>
					  <option value ="volvo">天</option>
					  <option value ="saab">周</option>
					  <option value="opel">月</option>
					  <option value="audi">年</option>
					</select></td>
					<th>平均收益率类型</th>
					<td><select>
					  <option value ="volvo">几何平均</option>
					  <option value ="saab">算数平均</option>
					</select></td>
					<th>方差类型</th>
					<td><select>
					  <option value ="volvo">协方差</option>
					  <option value ="saab">半方差</option>
					</select></td>-->
					<th>权重上限</th>
					<td><input id= "up" /></td>
					<th>权重下限</th>
					<td><input id="down"/></td>
						
					<!--由于datebox框架上面的数据必须是时间格式的，所以我们用editable="false"来禁止用户手动输入，以免报错-->
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="searchProdGroup();">计算</a></td>
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="clearSearch();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div style="width: auto;height:200px;">
    	<table id="mvoTable" ></table>
	</div>
	<div id="mvoDetail">Dialog Content.</div>


<script type="text/javascript">

    $(function(){
        selectInfo();
    });

    //加载数据
    function selectInfo(){
        $('#mvoTable').datagrid({
            width : '100%',
            url : "${pageContext.request.contextPath}/generateGroup/selectGroupInfo",
            loadMsg : '数据加载中,请稍后……',
            pagination : true,
            singleSelect : true,
            rownumbers : true,
            nowrap : true,
            height : 'auto',
            /* fit : true, */
            fitColumns : true,
            striped : true,
            idField : 'groupCode',
            pageSize : 10,
            pageList : [10, 30, 50 ],
            columns : [ [ {
                field : 'groupCode',
                title : '组合代码',
                width : 200,
                align : 'center',
            },{
                field : 'groupName',
                title : '组合名称',
                width : 200,
                align : 'center',
            }, {
                field : 'riskLevel',
                title : '风险等级',
                width : 200,
                align : 'center'
            } , {
                field : 'yieldRatio',
                title : '收益率',
                width : 200,
                align : 'center'
            }, {
                field : 'riskRatio',
                title : '风险率',
                width : 200,
                align : 'center'
            }, {
                field : 'createDate',
                title : '创建时间',
                width : 200,
                align : 'center'
            }, {
                field : 'doIt',
                title : '操作',
                width : 200,
                align : 'center',
                formatter : function(value, row, index) {
                    return "<a onclick='openNewTab2(\""+row.groupCode+"\");' >回归测试</a>"+"&nbsp;&nbsp;&nbsp;<a onclick='mvoDetail(\""+row.groupCode+"\");' >详情</a>";
                }
            }] ]
        })
	};

var sy = $.extend({}, sy);/*定义一个全局变量*/
var bidCodeStr = '${bid}';
var bidCodeArr = bidCodeStr.split(",");
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
function searchProdGroup() {
	var startTime = $("#mvoStartTime").datebox("getValue");  
	var endTime = $("#mvoEndTime").datebox("getValue");  
	var up = $("#up").val();  
	var down = $("#down").val();  
	
	/* alert(startTime+","+endTime); */
	
	if(startTime==""||endTime==""){
		alert("开始和结束时间不能为空！");
		return ;
	}
	if (startTime < endTime){
		createTableLsit(startTime,endTime,up,down);
	}else {
		alert("开始时间不能大于结束时间");
	}
	
}

function createTableLsit(startTime,endTime,up,down){
	$('#mvoTable').datagrid({
	    width : '100%',
	    url : "${pageContext.request.contextPath}/generateGroup/generateGroupByMVO",
	    loadMsg : '数据加载中,请稍后……',

        pagination : true,
        singleSelect : true,
        rownumbers : true,
        nowrap : true,
        height : 'auto',
        /* fit : true, */
        fitColumns : true,
        striped : true,
        idField : 'groupCode',
        pageSize : 10,
        pageList : [10, 30, 50 ],

	    queryParams:{
	    	startTime:startTime,
	    	endTime:endTime,
	    	up:up,
	    	down:down
	    },
	    pageSize : 10,
	    pageList : [ 10, 30, 50 ],
	    columns : [ [ {
	        field : 'groupCode',
	        title : '组合代码',
	        width : 200,
	        align : 'center',
	    },{
	        field : 'groupName',
	        title : '组合名称',
	        width : 200,
	        align : 'center',
	    }, {
	        field : 'riskLevel',
	        title : '风险等级',
	        width : 200,
	        align : 'center'
	    } , {
	        field : 'riskRatio',
	        title : '风险率',
	        width : 200,
	        align : 'center'
	    }, {
	        field : 'yieldRatio',
	        title : '收益率',
	        width : 200,
	        align : 'center'
	    }, {
	        field : 'createDate',
	        title : '创建时间',
	        width : 200,
	        align : 'center'
	    }, {
	        field : 'doIt',
	        title : '操作',
	        width : 200,
	        align : 'center',
	        formatter : function(value, row, index) {
				return "<a onclick='openNewTab2(\""+row.groupCode+"\");' >回归测试</a>"+"&nbsp;&nbsp;&nbsp;<a onclick='mvoDetail(\""+row.groupCode+"\");' >详情</a>";
            }
	    }] ]
	});
}



function openNewTab(groupCode){
	 $('#wu-tabs').tabs('close','组合基本数据');
	 addTab("组合基本数据", "pages/backtest/test/groupBasic.jsp?groupCode='"+groupCode+"'&groupType='mvo'", "icon-chart-organisation", false);
}

function openNewTab2(groupCode){
    $('#wu-tabs').tabs('close','组合回测分析');
    addTab("组合回测分析", "pages/backtest/backTestInfo.jsp?groupCode='"+groupCode+"'&groupType='mvo'", "icon-chart-organisation", false);
}


function mvoDetail(groupCode){
    $('#mvoDetail').dialog({
        title: '组合详情',
        width: 600,
        height: 400,
        closed: false,
        cache: false,
        href: '${pageContext.request.contextPath}/pages/modelstrategy/mvoDetail.jsp?groupCode='+groupCode,
        modal: true
    });
}

//点击清空按钮出发事件
function clearSearch() {
	$("#startTime").datebox("setValue", "");
	$("#endTime").datebox("setValue", "");
	$("#up").val("");  
	$("#down").val("");  
}
</script>
</body>
</html>