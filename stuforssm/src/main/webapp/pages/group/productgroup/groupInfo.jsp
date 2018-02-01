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
					<th>组合名称：</th>
					<td><input name="name" /></td>
					<th>产品代码：</th>
					<td><input name="productCode" /></td>
					<th>开始时间</th>
					<td><input class="easyui-datetimebox" editable="false"
						name="startTime" /></td>
					<th>结束时间</th>
					<td><input class="easyui-datetimebox" editable="false"
						name="endTime" /></td>
					<!--由于datebox框架上面的数据必须是时间格式的，所以我们用editable="false"来禁止用户手动输入，以免报错-->
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="searchFunc();">查找</a></td>
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="clearSearch();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<table id="groupList"></table>
	<div id="dd">Dialog Content.</div>

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
    $("#groupList").datagrid("load", sy.serializeObject($("#searchForm").form()));//将searchForm表单内的元素序列为对象传递到后台
}

//点击清空按钮出发事件
function clearSearch() {
    $("#groupList").datagrid("load", {});//重新加载数据，无填写数据，向后台传递值则为空
    $("#searchForm").find("input").val("");//找到form表单下的所有input标签并清空
}
	$('#groupList').datagrid({
		width : '100%',
		url : "${pageContext.request.contextPath}/productGroup/getProductGroupAllInfo",
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
			field : 'id',
			title : '组合代码',
			width : 200,
			align : 'center',
		},{
			field : 'product_group_name',
			title : '组合名称',
			width : 200,
			align : 'center',
		}, {
			field : 'sharpeRatio',
			title : 'sharp比率',
			width : 200,
			align : 'center'
		}, {
			field : 'create_source',
			title : '来源',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value == 1) {
					return '后台';
				}else if(value == 2){
					return '客户';
				}else{
					return '未知';
				}
			}
		}, {
			field : 'create_time',
			title : '创建时间',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value != '' && value != null) {
					return value.substring(0,19);
				}
			}
		},{
			field : 'status',
			title : '状态',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value == '1') {
					return '启用';
				}
				if (value == '0') {
					return '禁用';
				}
			}
		},{
			field : 'caozuo',
			title : '操作',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				return "<a onclick='openDialog("+row.id+");' iframe='0' >详情</a>"; 
			}
		} ] ]
	});
	
	function openDialog(id){
		$('#dd').dialog({
		    title: 'My Dialog',
		    width: 600,
		    height: 400,
		    closed: false,
		    cache: false,
		    href: '${pageContext.request.contextPath}/pages/group/productgroup/groupdetail.jsp?id='+id,
		    modal: true
		});
	}
</script>
</body>
</html>