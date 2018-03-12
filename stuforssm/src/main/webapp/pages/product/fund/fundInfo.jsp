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
	<div data-options="region:'north',title:'查询'" style="height: 70px; background: #F4F4F4;">
		<form id="fundInfo_searchForm">
			<table style="padding-top:10px;">
				<tr>
					<th>基金简称/代码</th>
					<td><input name="productCode" style="width:196px;"/></td>
					<th>&nbsp;&nbsp;资产类别</th>
					<td><select class="easyui-combobox" name="scode" style="width:200px;" id="scode">
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
					<th>&nbsp;募集开始时间</th>
					<td><input class="easyui-datetimebox" editable="false" name="startTime" style="width:200px;"/></td>
					<th>&nbsp;募集结束时间</th>
					<td><input class="easyui-datetimebox" editable="false" name="endTime" style="width:200px;"/></td>
				</tr>
				<tr>
					<th>&nbsp;基金类型</th>
					<td><select class="easyui-combobox" name="fundTyp" style="width:200px;" id="fundTyp">
						    <option value="" selected>请选择</option>
						    <option value="1">混合型基金 </option>
							<option value="2">债券型基金 </option>
							<option value="3">股票型基金 </option>
							<option value="4">货币市场型基金</option>
							<option value="5">其他基金 </option>
						</select>
					</td>
					<th>&nbsp;晨星基金类型</th>
					<td><select class="easyui-combobox" name="cxfunTyp" style="width:200px;" id="cxfunTyp">
						    <option value="" selected>请选择</option>
						    <option value="1">灵活配置型基金            </option>
							<option value="2">纯债型基金                </option>
							<option value="3">股票型基金                </option>
							<option value="4">激进配置型基金            </option>
							<option value="5">货币市场基金              </option>
							<option value="6">激进债券型基金            </option>
							<option value="7">普通债券型基金            </option>
							<option value="8">保守混合型基金            </option>
							<option value="9 ">其它基金                  </option>
							<option value="10">QDII基金                  </option>
							<option value="11">保本基金                  </option>
							<option value="12">短债基金                  </option>
							<option value="13">标准混合型基金            </option>
							<option value="14">沪港深混合型基金          </option>
							<option value="15">行业股票-医药             </option>
							<option value="16">行业股票-科技、传媒及通讯 </option>
							<option value="17">可转债基金                </option>
							<option value="18">沪港深股票型基金          </option>
							<option value="19">市场中性策略              </option>
							<option value="20">商品型基金                </option>
							<option value="21">大中华区股债混合(QDII)    </option>
							<option value="22">其它(QDII)                </option>
							<option value="23">亚洲股债混合(QDII)        </option>
							<option value="24">全球新兴市场股债混合(QDII)</option>
							<option value="25">保守配置型基金            </option>
						</select>
					</td>
					<th>&nbsp;申购状态</th>
					<td><select class="easyui-combobox" name="funStatus" style="width:200px;" id="fundStatus">
						    <option value="" selected>请选择</option>
						    <option value="0">封闭期</option>
						    <option value="1">暂停申购</option>
						    <option value="2">暂停大额申购</option>
						    <option value="3">开放申购</option>
						</select>
					</td>
				
					<td></td>
					<td><a class="easyui-linkbutton" href="javascript:void(0);" onclick="searchFundInfo();">查找</a>&nbsp;&nbsp;<a class="easyui-linkbutton" href="javascript:void(0);" onclick="clearFundInfo();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div>
		<table id="fundList"></table>
	</div>
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
function searchFundInfo() {
    $("#fundList").datagrid("load", sy.serializeObject($("#fundInfo_searchForm").form()));//将fundInfo_searchForm表单内的元素序列为对象传递到后台
}

//点击清空按钮出发事件
function clearFundInfo() {
    $("#fundList").datagrid("load", {});//重新加载数据，无填写数据，向后台传递值则为空
    $("#fundInfo_searchForm").find("input").val("");//找到form表单下的所有input标签并清空
    $('#fundStatus').combobox('setValue', "");
    $('#fundTyp').combobox('setValue', "");
    $('#cxfunTyp').combobox('setValue', "");
    $('#scode').combobox('setValue', "");
}
	$('#fundList').datagrid({
		width : '100%',
		url : "${pageContext.request.contextPath}/fund/cycle/day/qryFundBasicInfo",
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
			 /*  cycle: "day"       */      
            },    
		columns : [ [ {
			field : 'name',
			title : '基金简称',
			width : 200,
			align : 'center'
		}, {
			field : 'code',
			title : '基金代码',
			width : 200,
			align : 'center'
		/* }, {
			field : 'type',
			title : '产品类型',
			width : 200,
			align : 'center' */
		}, {
			field : 'sname',
			title : '资产类别',
			width : 200,
			align : 'center'
	/* 	}, {
			field : 'scode',
			title : '资产类别代码',
			width : 200,
			align : 'center' */
		}, {
			field : 'invtypone',
			title : '基金类型',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value =='1') {
					return '混合型基金';
				}
				if (value =='2') {
					return '债券型基金';
				}
				if (value =='3') {
					return '股票型基金';
				}
				if (value =='4') {
					return '货币市场型基金';
				}
				if (value =='5') {
					return '其他基金';
				}
			}
		}, {
			field : 'cxfundtyp',
			title : '晨星基金类型',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value == '1') {
					return '灵活配置型基金';
				}
				if (value == '2') {
					return '纯债型基金';
				}
				if (value == '3') {
					return '股票型基金';
				}
				if (value == '4') {
					return '激进配置型基金';
				}
				if (value == '5') {
					return '货币市场基金';
				}
				if (value == '6') {
					return '激进债券型基金';
				}
				if (value == '7') {
					return '普通债券型基金';
				}
				if (value == '8') {
					return '保守混合型基金';
				}
				if (value == '9') {
					return '其它基金';
				}
				if (value == '10') {
					return 'QDII基金';
				}
				if (value == '11') {
					return '保本基金';
				}
				if (value == '12') {
					return '短债基金';
				}
				if (value == '13') {
					return '标准混合型基金';
				}
				if (value == '14') {
					return '沪港深混合型基金';
				}
				if (value == '15') {
					return '行业股票-医药';
				}
				if (value == '16') {
					return '行业股票-科技、传媒及通讯';
				}
				if (value == '17') {
					return '可转债基金';
				}
				if (value == '18') {
					return '沪港深股票型基金';
				}
				if (value == '19') {
					return '市场中性策略';
				}
				if (value == '20') {
					return '商品型基金';
				}
				if (value == '21') {
					return '大中华区股债混合(QDII)';
				}
				if (value == '22') {
					return '其它(QDII)';
				}
				if (value == '23') {
					return '亚洲股债混合(QDII)';
				}
				if (value == '24') {
					return '全球新兴市场股债混合(QDII)';
				}
				if (value == '25') {
					return '保守配置型基金';
				}
				
			}
		}, {
			field : 'risklevel',
			title : '风险类型',
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
			field : 'bidcode',
			title : '跟踪指数',
			width : 200,
			align : 'center'
		}, {
			field : 'status',
			title : '状态',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
				if (value == '0') {
					return '封闭期';
				}
				if (value == '1') {
					return '暂停申购';
				}
				if (value == '2') {
					return '暂停大额申购';
				}
				if (value == '3') {
					return '开放申购';
				}
			}
		}, {
			field : '详情',
			title : '基金详情',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
					return "<a onclick='fundDetail("+index+");' iframe='0'>详情</a>";
			}
		} ] ]
	});
	function  fundDetail(index){
		var fundCode = $('#fundList').datagrid('getData').rows[index].code;
		var bidCode = $('#fundList').datagrid('getData').rows[index].bidcode;
		var sCode = $('#fundList').datagrid('getData').rows[index].sCode;
		console.log(fundCode,bidCode);
		addTab("基金信息", "${pageContext.request.contextPath}/fund/toFundDetail?code="+fundCode+"&bidCode="+bidCode+"&sCode="+sCode, "icon-chart-organisation", false);
	}
</script>
</body>
</html>