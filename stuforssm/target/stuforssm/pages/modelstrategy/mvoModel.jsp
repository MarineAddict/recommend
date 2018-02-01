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
					<td><input class="easyui-datetimebox" editable="false"
						name="startTime" /></td>
					<th>结束时间</th>
					<td><input class="easyui-datetimebox" editable="false"
						name="endTime" /></td>
					<th>取值频率</th>
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
					</select></td>
					<th>权重上限</th>
					<td><input /></td>
					<th>权重下限</th>
					<td><input /></td>
						
					<!--由于datebox框架上面的数据必须是时间格式的，所以我们用editable="false"来禁止用户手动输入，以免报错-->
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="searchFunc();">设置</a></td>
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="clearSearch();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- <table id="fundList"></table> -->

<script type="text/javascript">

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
    $("#fundList").datagrid("load", sy.serializeObject($("#searchForm").form()));//将searchForm表单内的元素序列为对象传递到后台
}

//点击清空按钮出发事件
function clearSearch() {
    $("#fundList").datagrid("load", {});//重新加载数据，无填写数据，向后台传递值则为空
    $("#searchForm").find("input").val("");//找到form表单下的所有input标签并清空
}
	$('#fundList').datagrid({
		width : '100%',
		url : "${pageContext.request.contextPath}/fund/cycle/day/getFundBasicInfo",
		loadMsg : '数据加载中,请稍后……',
		pagination : true,
		singleSelect : true,
		rownumbers : true,
		nowrap : true,
		height : 'auto',
		fit : true,
		fitColumns : true,
		striped : true,
		idField : 'bondId',
		pageSize : 10,
		pageList : [ 10, 30, 50 ],
		  queryParams: {          
			 /*  cycle: "day"       */      
            },    
		columns : [ [ {
			field : 'name',
			title : '产品名称',
			width : 200,
			align : 'center'
		}, {
			field : 'code',
			title : '产品代码',
			width : 200,
			align : 'center'
		}, {
			field : 'ipo_START_DATE',
			title : '募集开始日期',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value != '' && value != null) {
					return value.substring(0,10);
				}
			}
		}, {
			field : 'ipo_END_DATE',
			title : '募集结束日期',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value != '' && value != null) {
					return value.substring(0,10);
				}
			}
		}, {
			field : 'estab_DATE',
			title : '产品成立日期',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value != '' && value != null) {
					return value.substring(0,10);
				}
			}
		}, {
			field : 'status',
			title : '状态',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value == '1') {
					return '可售';
				}
				if (value == '0') {
					return '不可售';
				}
			}
		} ] ]
	});
</script>
</body>
</html>