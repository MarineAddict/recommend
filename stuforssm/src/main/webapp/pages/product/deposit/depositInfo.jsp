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
		<form id="dep_searchForm">
			<table>
				<tr>
					<th>产品名称：</th>
					<td><input name="productName" /></td>
					<th>产品代码：</th>
					<td><input name="productCode" /></td>
					<!-- <th>发行时间</th>
					<td><input class="easyui-datetimebox" editable="false"
						name="releaseDate" /></td> -->
					<!--由于datebox框架上面的数据必须是时间格式的，所以我们用editable="false"来禁止用户手动输入，以免报错-->
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="depositSeachForm();">查找</a></td>
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="clearDepositInfo();">清空</a></td>
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
function depositSeachForm() {
    $("#depositList").datagrid("load", sy.serializeObject($("#dep_searchForm").form()));//将dep_searchForm表单内的元素序列为对象传递到后台
}

//点击清空按钮出发事件
function clearDepositInfo() {
    $("#depositList").datagrid("load", {});//重新加载数据，无填写数据，向后台传递值则为空
    $("#dep_searchForm").find("input").val("");//找到form表单下的所有input标签并清空
}
	$('#depositList').datagrid({
		width : '100%',
		url : "${pageContext.request.contextPath}/deposit/getDepositList",
		loadMsg : '数据加载中,请稍后……',
		pagination : true,
		singleSelect : true,
		rownumbers : true,
		nowrap : true,
		height : 'auto',
		/* fit : true, */
		fitColumns : true,
		striped : true,
		idField : 'bondId',
		pageSize : 10,
		pageNumber:1,
		pageList : [ 10, 30, 50 ],
		columns : [ [{
			field : 'code',
			title : '产品代码',
			width : 200,
			align : 'center'
		}, 
		{
			field : 'name',
			title : '产品名称',
			width : 400,
			align : 'center'
		}, 
		 {
			field : 'categorySmallName',
			title : '资产类别',
			width : 200,
			align : 'center'
		},
		{
			field : 'subStartDay',
			title : '起购日',
			width : 200,
			align : 'center',
			formatter:function(value,row,index){
                      return new Date(value).format("yyyy-MM-dd");
				}
		}, {
			field : 'subEndDay',
			title : '截止日',
			width : 200,
			align : 'center',
			formatter:function(value,row,index){
				  return new Date(value).format("yyyy-MM-dd");
			}
		}, {
			field : 'intStartDay',
			title : '起息日',
			width : 200,
			align : 'center',
			formatter:function(value,row,index){
				  return new Date(value).format("yyyy-MM-dd");
			}
		}, {
			field : 'intEndDay',
			title : '到息日',
			width : 200,
			align : 'center',
			formatter:function(value,row,index){
				  return new Date(value).format("yyyy-MM-dd");
			}
		}, 
		{
			field : 'startMoney',
			title : '最低投资额',
			width : 200,
			align : 'center',
			
		},
		{
			field : 'interest',
			title : '利率',
			width : 200,
			align : 'center'
		} , {
			field : 'theTerm',
			title : '期限(月)',
			width : 200,
			align : 'center'
		},
		{
			field: '详情',
			title : '存单详情',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
					return "<a onclick='depositDetail("+index+");' iframe='0'>详情</a>";
			}
		} ] ]
	});


function depositDetail(index){
	var prdCode=$('#depositList').datagrid('getData').rows[index].code;
	var name=$('#depositList').datagrid('getData').rows[index].name;
	var subEndDay=new Date($('#depositList').datagrid('getData').rows[index].subEndDay).format("yyyy-MM-dd") ;
	var subStartDay=new Date($('#depositList').datagrid('getData').rows[index].subStartDay).format("yyyy-MM-dd");
	var intStartDay=new Date( $('#depositList').datagrid('getData').rows[index].intStartDay).format("yyyy-MM-dd");
	var intEndDay=new Date($('#depositList').datagrid('getData').rows[index].intEndDay).format("yyyy-MM-dd");
	var interest=$('#depositList').datagrid('getData').rows[index].interest;
	var assetType=$('#depositList').datagrid('getData').rows[index].categorySmallName;
	var traceName=$('#depositList').datagrid('getData').rows[index].traceName;
	var term=$('#depositList').datagrid('getData').rows[index].theTerm;
	
	console.log($('#depositList').datagrid('getData').rows[index]);
	addTab("存单信息", "${pageContext.request.contextPath}/deposit/toDepositDetail?prdCode="+prdCode+"&name="+name+"&subEndDay="+subEndDay+
			"&subStartDay="+subStartDay+"&intStartDay="+intStartDay+"&assetType="+assetType+"&traceName="+traceName+
			"&intEndDay="+intEndDay+"&interest="+interest+"&term="+term, "icon-chart-organisation", false);
}
	
</script>
</body>
</html>