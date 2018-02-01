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
					<td><input name="productCode" /></td>
					<th>发行时间</th>
					<td><input class="easyui-datetimebox" editable="false"
						name="releaseDate" /></td>
					<!--由于datebox框架上面的数据必须是时间格式的，所以我们用editable="false"来禁止用户手动输入，以免报错-->
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="searchFunc();">查找</a></td>
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="clearSearch();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<table id="preciousList"></table>

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
    $("#preciousList").datagrid("load", sy.serializeObject($("#searchForm").form()));//将searchForm表单内的元素序列为对象传递到后台
}

//点击清空按钮出发事件
function clearSearch() {
    $("#preciousList").datagrid("load", {});//重新加载数据，无填写数据，向后台传递值则为空
    $("#searchForm").find("input").val("");//找到form表单下的所有input标签并清空
}
	$('#preciousList').datagrid({
		width : '100%',
		url : "${pageContext.request.contextPath}/pm/pmList",
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
			field : 'productCode',
			title : '产品代码',
			width : 200,
			align : 'center'
		}, {
			field : 'releaseDate',
			title : '发行时间',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value != '' && value != null) {
					return value.substring(0,10);
				}
			}
		}, {
			field : 'material',
			title : '材质',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value == '0') {
					return '黄金';
				}
				if (value == '1') {
					return '白银';
				}
			}
		}, {
			field : 'riskLevel',
			title : '风险等级',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value == 1) {
					return '低风险';
				}
				if (value == 2) {
					return '中低风险';
				}
				if (value == 3) {
					return '中风险';
				}
				if (value == 4) {
					return '中高风险';
				}
				if (value == 5) {
					return '高风险';
				}
			}
		}, {
			field : 'status',
			title : '状态',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value == '0') {
					return '可售';
				}
				if (value == '1') {
					return '不可售';
				}
			}
		}, {
			field : 'weight',
			title : '克重',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				return value + row.unit;
			}
		} ] ]
	});
</script>
</body>
</html>