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
		<form id="fin_searchForm">
			<table>
				<tr>
					<th>产品名称：</th>
					<td><input name="financeName" /></td>
					<th>产品代码：</th>
					<td><input name="code" /></td>
					<th>状态</th>
					<td><select class="easyui-combobox" name="status" style="width:200px;" id="finStatus">
						    <option value="" selected>请选择</option>
						    <option value="0">可售</option>
						    <option value="1">不可售</option>
						</select>
					</td>
					<!--由于datebox框架上面的数据必须是时间格式的，所以我们用editable="false"来禁止用户手动输入，以免报错-->
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="searchFunc();">查找</a></td>
					<td><a class="easyui-linkbutton" href="javascript:void(0);"
						onclick="clearSearch();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<table id="financeList"></table>
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
    $("#financeList").datagrid("load", sy.serializeObject($("#fin_searchForm").form()));//将fin_searchForm表单内的元素序列为对象传递到后台
}

//点击清空按钮出发事件
function clearSearch() {
    $("#financeList").datagrid("load", {});//重新加载数据，无填写数据，向后台传递值则为空
    $("#fin_searchForm").find("input").val("");//找到form表单下的所有input标签并清空
    $('#finStatus').combobox('setValue', "");
}
	$('#financeList').datagrid({
		width : '100%',
		url : "${pageContext.request.contextPath}/finance/getFinanceList",
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
		columns : [ [ {
			field : 'financeName',
			title : '产品名称',
			width : 250,
			align : 'center'
		}, {
			field : 'financeCode',
			title : '产品代码',
			width : 200,
			align : 'center'
		}, {
			field : 'prdType',
			title : '产品类型',
			width : 200,
			align : 'center'
		}, {
			field : 'scode',
			title : '资产类别',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value =='1') {
					return '国内小盘股';
				}
				if (value =='2') {
					return '国内大盘股';
				}
				if (value =='3') {
					return '港股';
				}
				if (value =='4') {
					return '美股';
				}
				if (value =='5') {
					return '货币';
				}
				if (value =='6') {
					return '普通债';
				}
				if (value =='7') {
					return '纯债';
				}
				if (value =='8') {
					return '黄金';
				}
			}
		}, {
			field : 'valueDate',
			title : '起息日',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value != '' && value != null) {
					return value.substring(0,10);
				}
			}
		}, {
			field : 'expiryDate',
			title : '到息日',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value != '' && value != null) {
					return value.substring(0,10);
				}
			}
		/* }, {
			field : 'expYieldMin',
			title : '预期最小收益率',
			width : 200,
			align : 'center' */
		}, {
			field : 'startMoney',
			title : '起购金额(元)',
			width : 200,
			align : 'center'
		}, {
			field : 'expYieldMax',
			title : '预期最大收益率(%)',
			width : 200,
			align : 'center'
		/* }, {
			field : 'realYield',
			title : '实际收益率',
			width : 200,
			align : 'center' */
		}, {
			field : 'period',
			title : '周期(天)',
			width : 200,
			align : 'center'
		},{
			field : 'financeStatus',
			title : '状态',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value == '1') {
					return '不可售';
				}
				if (value == '0') {
					return '可售';
				}
			}
		}, {
			field : 'risklevel',
			title : '风险等级',
			width : 200,
			align : 'center'
		}, {
			field : '详情',
			title : '理财详情',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				return "<a onclick='financeDetail("+index+");' iframe='0'>详情</a>";
			}
		} ] ]
	});
	
	
	
	function  financeDetail(index){
		var financeCode = $('#financeList').datagrid('getData').rows[index].financeCode;
		//var bidCode = $('#financeList').datagrid('getData').rows[index].bidcode;
		//alert(financeCode);
		//console.log(fundCode,bidCode);
		addTab("理财信息", "${pageContext.request.contextPath}/finance/toFinanceDetail?code="+financeCode, "icon-chart-organisation", false);
	}
	
	
	
	
</script>
</body>
</html>