<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <title>Insert title here</title>
</head>
<body>

<div data-options="region:'north',title:'查询'" style="height: 70px; background: #F4F4F4;">
		<form id="asset_searchForm">
			<table style="padding-top:10px;">
				<tr>
				   <th>体系</th>
					<td><select class="easyui-combobox" name="system" style="width:200px;">
					        <option value="" selected>请选择</option>
						    <option value="1">权益类 </option>
							<option value="2">固定收益类 </option>
							<option value="3">现金类 </option>
							<option value="4">其它资产</option>
						</select>
					</td>
				
				
					<th>资产大类</th>
					<td><select class="easyui-combobox" name="categorybig" style="width:200px;">
					       <option value="" selected>请选择</option>
						    <option value="1">权益类 </option>
							<option value="2">固定收益类 </option>
							<option value="3">现金类 </option>
							<option value="4">其它资产</option>
						</select>
					</td>
					<th>资产小类</th>
					<td><select class="easyui-combobox" name="categorysmall" style="width:200px;">
						    <option value="" selected>请选择</option>
						    <option value="1">国内小盘股 </option>
							<option value="2">国内大盘股 </option>
							<option value="3">港股 </option>
							<option value="4">美股</option>
							<option value="5">货币 </option>
							<option value="6">普通债 </option>
							<option value="7">纯债 </option>
							<option value="8">黄金 </option>
						</select>
					</td>
					<th>资产类型</th>
					<td><select class="easyui-combobox" name="assettype" style="width:200px;">
						    <option value="" selected>请选择</option>
						    <option value="1">理财 </option>
							<option value="2">基金</option>
							<option value="3">存单 </option>
							<option value="4">贵金属</option>
						</select>
					</td>
				
					<td></td>
					<td><a class="easyui-linkbutton" href="javascript:void(0);" onclick="searchAssetInfo();">查找</a>&nbsp;&nbsp;<a class="easyui-linkbutton" href="javascript:void(0);" onclick="clearAssetInfo();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>


<div id="AssetList">
</div>



<!-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- js部分 -- -- -- -- -- -- -- -- -- -- -- -- -- -- -->
<script type="text/javascript">
serializeObject = function (form) { /*将form表单内的元素序列化为对象，扩展Jquery的一个方法*/
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

 function searchAssetInfo(){
	 $("#AssetList").datagrid("load", serializeObject($("#asset_searchForm").form()));
	 }

 $('#AssetList').datagrid({
		width : '100%',
		url : "${pageContext.request.contextPath}/category/getProductAssetInfo",
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
		pageList : [ 10, 30, 50 ], 
		queryParams: { 			 
           }, 
		columns : [ [ {
			field : 'categoryBigName',
			title : '资产大类',
			width : 200,
			align : 'center'
		}, {
			field : 'categorySmallName',
			title : '资产小类',
			width : 200,
			align : 'center'
		}, {
			field : 'code',
			title : '产品代码',
			width : 200,
			align : 'center'
		}, {
			field : 'name',
			title : '产品名称',
			width : 200,
			align : 'center'
		},
		{
			field : '详情',
			title : '详情',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
					return "<a onclick='AssetInfoDetail("+index+");' iframe='0'>详情</a>";
			}
		} ] ]
	});

 
function  AssetInfoDetail(index){
	var fundCode = $('#AssetList').datagrid('getData').rows[index].code;
	var bidCode = $('#AssetList').datagrid('getData').rows[index].bidCode;
	console.log( fundCode+","+bidCode);
 addTab("资产信息详情", "${pageContext.request.contextPath}/fund/toFundDetail?code="+fundCode+"&bidCode="+bidCode, "icon-chart-organisation", false);
}

 function clearAssetInfo(){
  $("select").each(function(i){
     $(this).combobox('setValue', "");
	  })
	 }

</script>

</body>

</html>