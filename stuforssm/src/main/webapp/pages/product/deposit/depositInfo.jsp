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
	<div data-options="region:'north',title:'查询'"
		style="height: 40px; background: #F4F4F4;">
		<form id="searchForm">
			<table>
				<tr>
					<th>产品名称：</th>
					<td><input name="name" /></td>
					<th>产品代码：</th>
					<td><input name="code" /></td>
					<!-- <th>发行时间</th>
					<td><input class="easyui-datetimebox" editable="false"
						name="releaseDate" /></td> -->
					<!--由于datebox框架上面的数据必须是时间格式的，所以我们用editable="false"来禁止用户手动输入，以免报错-->
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="searchFunc();">查找</a></td>
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="clearSearch();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<table id="depositList"></table>

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
    $("#depositList").datagrid("load", sy.serializeObject($("#searchForm").form()));//将searchForm表单内的元素序列为对象传递到后台
}

//点击清空按钮出发事件
function clearSearch() {
    $("#depositList").datagrid("load", {});//重新加载数据，无填写数据，向后台传递值则为空
    $("#searchForm").find("input").val("");//找到form表单下的所有input标签并清空
}
	$('#depositList').datagrid({
		width : '100%',
		url : "${pageContext.request.contextPath}/deposit/getDepositBasic",
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
			field : 'substartday',
			title : '起购日',
			width : 200,
			align : 'center'
		}, {
			field : 'subendday',
			title : '截止日',
			width : 200,
			align : 'center'
		}, {
			field : 'intestartday',
			title : '起息日',
			width : 200,
			align : 'center'
		}, {
			field : 'intendday',
			title : '到息日',
			width : 200,
			align : 'center'
		}, {
			field : 'interest',
			title : '利率',
			width : 200,
			align : 'center'
		} , {
			field : 'theterm',
			title : '期限(月)',
			width : 200,
			align : 'center'
		}] ]
	});
</script>
</body>
</html>