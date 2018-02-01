<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
<table id="custgroupList"></table>
<script type="text/javascript">
//请求后台数据 
$.ajax({
	type:"POST",
	url:"${pageContext.request.contextPath}/custom/getCustomeGroup",
	data:{planId:'20181',cusId:'201801'},
	timeout:20000,
	cache:false,
	success:function(data){
		console.log(data);
		/* createTableList(data); */
	},
	error:function(){
		alert("请求失败");
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
function searchFunc() {
    $("#custgroupList").datagrid("load", sy.serializeObject($("#searchForm").form()));//将searchForm表单内的元素序列为对象传递到后台
}

//点击清空按钮出发事件
function clearSearch() {
    $("#custgroupList").datagrid("load", {});//重新加载数据，无填写数据，向后台传递值则为空
    $("#searchForm").find("input").val("");//找到form表单下的所有input标签并清空
}
	$('#custgroupList').datagrid({
		width : '100%',
		url : "${pageContext.request.contextPath}/custom/getCustomeGroup",
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
		rownumbers : false,//行号
		pageSize : 10,
		pageList : [ 10, 30, 50 ],
		queryParams: {          
			planId:'20181',
			cusId:'201801'      
        },
        onLoadSuccess: function(data) {
            //所有列进行合并操作
            //$(this).datagrid("autoMergeCells");
            //指定列进行合并操作
            $(this).datagrid("autoMergeCells", ['productGroupName']);
        },
		columns : [ [ {
			field : 'productGroupName',
			title : '组合名称',
			width : 200,
			align : 'center'
		}, {
			field : 'productCode',
			title : '产品代码',
			width : 200,
			align : 'center'
		}, {
			field : 'proportion',
			title : '产品占比',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value != '' && value != null) {
					return value*100+"%";
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
		}] ]
	});
</script>
</body>
</html>