<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form id="addgroup" action="">
<div style="margin-top:10px;" id="productTitle">
        <td style="text-align:right;">产品类型：</td>
        <td style="text-align:left">
            <span class="radioSpan">
                <input type="radio" name="assetsType" value="asset">资产小类</input>
                <input type="radio" name="assetsType" value="financial">金融产品</input>
            </span>
        </td>
</div>
</br>
<div id="financialProducts" style="display:none;">
	组合名称：<input name="Groupname"></br>
	<div style="margin-top:10px;">
			产品类型1：<select name="product_type"   style="width:200px;">
				<!-- <option value="1">理财</option>
				<option value="2">基金</option>
				<option value="3">贵金属</option>
				<option value="4">存单</option> -->
			</select>
			产品代码1：<input name="product_code">
			所占权重1：<input name="proportion">
	</div>
			<!-- 产品类型1：<input name="product_type"> -->
			<br>
	<div style="margin-top:10px;">
			产品类型2：<select name="product_type"  style="width:200px;">
				<!-- <option value="1">理财</option>
				<option value="2">基金</option>
				<option value="3">贵金属</option>
				<option value="4">存单</option> -->
			</select>
			产品代码2：<input name="product_code">
			所占权重2：<input name="proportion" type="text">
			<!-- 产品类型2：<input name="product_type"> -->
	</div>
	</br>
</div> 

</form>
<input type="button" value="新增产品" onclick="addgroupproduct();"> 
<input type="button" value="回归测试" onclick="groupBackTest();">

<script type="text/javascript">
var i=3;
var checked;
var selectData;
var availableTags = [];
//点击新增产品按钮触发事件
function addgroupproduct(){
	var content="";
	if(checked=='asset'){
		content+='<div style="margin-top:10px;">'+'产品类型'+i+'：<select name="product_type" style="width:200px;">'+createSelectContext(checked)+'</select>&nbsp;产品代码'+i+'：<input name="product_code" value="'+selectData[0].smCode+'">&nbsp;所占权重'+i+'：<input name="proportion"></div></br>';
	}else{
		content+='<div style="margin-top:10px;">'+'产品类型'+i+'：<select name="product_type" style="width:200px;">'+createSelectContext(checked)+'</select>&nbsp;产品代码'+i+'：<input name="product_code" >&nbsp;所占权重'+i+'：<input name="proportion"></div></br>';
	} 
	console.log(content);
	$("#financialProducts").append(content);
	i++;
		$('#financialProducts select[name="product_type"]').change(function(){
			if(checked=='asset'){
				$(this).next().val($(this).val());
				/* $("#div1 :text").each(function () {
				    var this_id = $(this).attr("id");
				    alert(this_id);
				} */
			}else if(checked=='financial'){
			/* 	getResourceCode("fund");
				$('#financialProducts input[name="product_code"]').autocomplete({
					max: 10,    //列表里的条目数 
		    	    source: availableTags
		    	}); */
			}
		}); 
	
}

//构建下拉框内容
function createSelectContext(type){
	var selectContext="";
	if(type=='asset'){
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/category/qryCategory",
			cache: false,  
			async:false,
			success:function(data){
				selectData=data;
				for(var i=0;i<data.length;i++){
					selectContext+='<option value="'+data[i].smCode+'">'+data[i].smName+'</option>';
				}
			},
			error:function(){
				alert("请求失败");
			}
		});
	}else if(type=='financial'){
		selectContext+='<option value="fund">基金</option><option value="finance">理财</option><option value="pm">贵金属</option><option value="deposit">存单</option>';
	}
	return selectContext;
}

//修改产品类型单选框触发的事件
document.getElementById("productTitle").onchange = function() {
   /*  alert("隐藏标签的当期值为：" + $('#productTitle input[name="assetsType"]:checked ').val()); */
    checked=$('#productTitle input[name="assetsType"]:checked ').val();
    document.getElementById("financialProducts").style.display = "block";
    $('#financialProducts select[name="product_type"]').empty();
    $('#financialProducts input[name="product_code"]').val("");
    $('#financialProducts input[name="proportion"]').val("");
    $('#financialProducts select[name="product_type"]').append(createSelectContext(checked));
    if(checked=='asset'){
    	$('#financialProducts input[name="product_code"]').each(function(i){
    		$(this).val(selectData[0].smCode);
    		/* $(this).attr("readonly","readonly"); */
    	});
    }else if(checked=='financial'){
    	/* $(this).removeAttr("readonly"); */
    	/* getResourceCode("fund");
    	$('#financialProducts input[name="product_code"]').autocomplete({
    		max: 10,    //列表里的条目数 
    	    source: availableTags
    	}); */
    }
};

//实现资产小类产品代码自动填写
$('#financialProducts select[name="product_type"]').change(function(){
	if(checked=='asset'){
		$(this).next().val($(this).val());
	}else if(checked=='financial'){
	/* 	getResourceCode($(this).val());
		$('#financialProducts input[name="product_code"]').autocomplete({
			max: 10,    //列表里的条目数 
    	    source: availableTags
    	}); */
		
	}
	
});   

//金融产品根据类型获取该类型下的所有产品代码
function getResourceCode(type){
	$.ajax({
		type : "POST",//方法类型
		url : "${pageContext.request.contextPath}/backtest/findProductCode",//url
		data : {productType:type},
		cache: false,  
		async:false,
		success : function(result) {
	    	console.log(result);
	    	availableTags=result;
		},
		error : function() { 
			alert("数据请求失败！");
		}
	});
}



//表单提交
function groupBackTest(){
	//验证产品占比是否为1
	var sum=0;
	obj = document.getElementsByName("proportion");
	for(var i=0;i<obj.length;i++){
	  sum+=Number(obj[i].value);
	}
	if(sum==1){
		$.ajax({
			type : "POST",//方法类型
			url : "${pageContext.request.contextPath}/backtest/insertProductGroup/backTest",//url
			data : $('#addgroup').serialize(),
			cache: false,  
			success : function(result) {
		    	 $('#wu-tabs').tabs('close','组合回测分析');
		    	 addTab("组合回测分析", "pages/backtest/backTestInfo.jsp?groupCode='"+result+"'&groupType='"+checked+"'", "icon-chart-organisation", false);
			},
			error : function() { 
				alert("数据提交失败！");
			}
		});
	}else{
		alert("产品占比之和要等于1");
	}
}

</script>
</body>
</html>