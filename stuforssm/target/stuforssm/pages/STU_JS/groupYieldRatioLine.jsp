<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%-- <script src="<c:url value="/js/jquery/jquery-3.2.1.js"/>"></script> --%>
<script src="<c:url value="/js/echars/echarts.js"/>"></script>
<script src="<c:url value="/js/Page.js"/>"></script>
<style> 

/* body {
    width: 600px;
    text-align:center;
    font-family: 'trebuchet MS', 'Lucida sans', Arial;
    font-size: 14px;
    color: #444;
} */

table {
	text-align:center;
    *border-collapse: collapse; /* IE7 and lower */
    border-spacing: 0;
    width: 100%;    
}

.bordered {
	text-align:center;
    border: solid #ccc 1px;
    -moz-border-radius: 6px;
    -webkit-border-radius: 6px;
    border-radius: 6px;
    -webkit-box-shadow: 0 1px 1px #ccc; 
    -moz-box-shadow: 0 1px 1px #ccc; 
    box-shadow: 0 1px 1px #ccc;         
}

.bordered tr:hover {
    background: #fbf8e9;
    -o-transition: all 0.1s ease-in-out;
    -webkit-transition: all 0.1s ease-in-out;
    -moz-transition: all 0.1s ease-in-out;
    -ms-transition: all 0.1s ease-in-out;
    transition: all 0.1s ease-in-out;     
}    
    
.bordered td, .bordered th {
    border-left: 1px solid #ccc;
    border-top: 1px solid #ccc;
    padding: 10px;
    text-align: left;    
}

.bordered th {
    background-color: #dce9f9;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc), to(#dce9f9));
    background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image:    -moz-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image:     -ms-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image:      -o-linear-gradient(top, #ebf3fc, #dce9f9);
    background-image:         linear-gradient(top, #ebf3fc, #dce9f9);
    -webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset; 
    -moz-box-shadow:0 1px 0 rgba(255,255,255,.8) inset;  
    box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;        
    border-top: none;
    text-shadow: 0 1px 0 rgba(255,255,255,.5); 
}

.bordered td:first-child, .bordered th:first-child {
    border-left: none;
}

.bordered th:first-child {
    -moz-border-radius: 6px 0 0 0;
    -webkit-border-radius: 6px 0 0 0;
    border-radius: 6px 0 0 0;
}

.bordered th:last-child {
    -moz-border-radius: 0 6px 0 0;
    -webkit-border-radius: 0 6px 0 0;
    border-radius: 0 6px 0 0;
}

.bordered th:only-child{
    -moz-border-radius: 6px 6px 0 0;
    -webkit-border-radius: 6px 6px 0 0;
    border-radius: 6px 6px 0 0;
}

.bordered tr:last-child td:first-child {
    -moz-border-radius: 0 0 0 6px;
    -webkit-border-radius: 0 0 0 6px;
    border-radius: 0 0 0 6px;
}

.bordered tr:last-child td:last-child {
    -moz-border-radius: 0 0 6px 0;
    -webkit-border-radius: 0 0 6px 0;
    border-radius: 0 0 6px 0;
}



/*----------------------*/

.zebra td, .zebra th {
    padding: 10px;
    border-bottom: 1px solid #f2f2f2;    
}

.zebra tbody tr:nth-child(even) {
    background: #f5f5f5;
    -webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset; 
    -moz-box-shadow:0 1px 0 rgba(255,255,255,.8) inset;  
    box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;        
}

.zebra th {
    text-align: left;
    text-shadow: 0 1px 0 rgba(255,255,255,.5); 
    border-bottom: 1px solid #ccc;
    background-color: #eee;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#f5f5f5), to(#eee));
    background-image: -webkit-linear-gradient(top, #f5f5f5, #eee);
    background-image:    -moz-linear-gradient(top, #f5f5f5, #eee);
    background-image:     -ms-linear-gradient(top, #f5f5f5, #eee);
    background-image:      -o-linear-gradient(top, #f5f5f5, #eee); 
    background-image:         linear-gradient(top, #f5f5f5, #eee);
}

.zebra th:first-child {
    -moz-border-radius: 6px 0 0 0;
    -webkit-border-radius: 6px 0 0 0;
    border-radius: 6px 0 0 0;  
}

.zebra th:last-child {
    -moz-border-radius: 0 6px 0 0;
    -webkit-border-radius: 0 6px 0 0;
    border-radius: 0 6px 0 0;
}

.zebra th:only-child{
    -moz-border-radius: 6px 6px 0 0;
    -webkit-border-radius: 6px 6px 0 0;
    border-radius: 6px 6px 0 0;
}

.zebra tfoot td {
    border-bottom: 0;
    border-top: 1px solid #fff;
    background-color: #f1f1f1;  
}

.zebra tfoot td:first-child {
    -moz-border-radius: 0 0 0 6px;
    -webkit-border-radius: 0 0 0 6px;
    border-radius: 0 0 0 6px;
}

.zebra tfoot td:last-child {
    -moz-border-radius: 0 0 6px 0;
    -webkit-border-radius: 0 0 6px 0;
    border-radius: 0 0 6px 0;
}

.zebra tfoot td:only-child{
    -moz-border-radius: 0 0 6px 6px;
    -webkit-border-radius: 0 0 6px 6px
    border-radius: 0 0 6px 6px
}
  
</style> 
</head>
<h2>当前组合产品列表:</h2>
<table class="bordered" id="productList">
    <thead>

    <tr>
        <th>组合名称</th>        
        <th>产品代码</th>        
        <th>产品占比</th>
    </tr>
    </thead>
    <tbody id="tbody">
    
</tbody>
</table>
<!-- <table>
<tr>
	<td><a href="#" onclick="page.prePage();">上一页</a></td>
	<td><a href="#" onclick="page.nextPage();">下一页</a></td>
	<td><a href="#" onclick="">生成产品组合收益曲线</a></td>
	<td><span id="pageindex"></span></td>
</tr>
</table> -->
<div style="width: 1200px;margin-top:40px;">

	<!-- <select id="productType">
	  <option value="finance">理财</option>
	  <option value="fund">基金</option>
	  <option value="deposit">存款</option>
	  <option value="pm">贵金属</option>
	</select> -->
	组合代码:<input id="productCode" type="text" />
	开始时间:<input id="startTime" type="date" />
	结束时间:<input id="endTime" type="date" />
	<button onclick="search();">查询</button>

</div>
<div id="yieldRatioLine" style="width: 1200px;height:800px;"></div>
<div id="NAVADJLine" style="width: 1200px;height:800px;"></div>
<script type="text/javascript">

//请求后台数据 
$.ajax({
	type:"POST",
	url:"${pageContext.request.contextPath}/productGroup/productGroupId/104/getProductGroupDetailsInfo",
	timeout:20000,
	cache:false,
	success:function(data){
		console.log(data);
		createTableList(data);
	},
	error:function(){
		alert("请求失败");
	}
});

//创建Table表格
function createTableList(data){
	var content="";
	for(var i=0;i<data.pgdList.length;i++){
		content+="<tr>";
		content+="<td>"+data.product_group_name+"</td>";
		content+="<td>"+data.pgdList[i].product_code+"</td>";
		content+="<td>"+data.pgdList[i].proportion*100+"%</td>";
		content+="</tr>";
	}
	document.getElementById("tbody").innerHTML=content;
	page = new Page(4,'productList','tbody'); 
}

//根据产品类型标识来区分不同种类产品
function productTypeName(key){
	if(key==1){
		return "理财";
	}else if(key==2){
		return "基金";
	}else if(key==3){
		return "存单";
	}else if(key==4){
		return "贵金属";
	}else{
		return "其他";
	}
}


/* window.onload = function(){
	page = new Page(4,'productList','tbody'); 
}; */




var url;
var data;

function search(){
	var productCode=$('#productCode').val();
	var startTime=$('#startTime').val();
	var endTime=$('#endTime').val();

	//请求后台数据 
	$.ajax({
		type:"POST",
		url:"${pageContext.request.contextPath}/productGroup/yieldRatioLine",
		data:{productCode:productCode,startTime:startTime,endTime:endTime},
		timeout:20000,
		cache:false,
		success:function(data){
			yieldRatioLine(data);
			NAVADJLine(data);
		},
		error:function(){
			alert("请求失败");
		}
	});
}

//收益率曲线
function yieldRatioLine(data){
	var xList=[];
	var yList=[];
	for(var i=0;i<data.length;i++){
		xList.push(data[i].navDate.substring(0,10));
		yList.push(data[i].yieldRatio);
	}
	var myChart = echarts.init(document.getElementById('yieldRatioLine'));


	var dateList = xList;
	var valueList = yList;
		option = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    title: {
			        left: 'center',
			        text: '组合收益率曲线',
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataZoom : {show: true},
			            dataView : {show: true},
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    dataZoom : {
			        show : true,
			        realtime : true,
			        //orient: 'vertical',   // 'horizontal'
			        //x: 0,
			        y: 36,
			        //width: 400,
			        height: 20,
			        //backgroundColor: 'rgba(221,160,221,0.5)',
			        //dataBackgroundColor: 'rgba(138,43,226,0.5)',
			        //fillerColor: 'rgba(38,143,26,0.6)',
			        //handleColor: 'rgba(128,43,16,0.8)',
			        //xAxisIndex:[],
			        //yAxisIndex:[],
			        start : 40,
			        end : 60
			    },
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : false,
			            data : dateList,
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'收益率',
			            type:'line',
			            data:valueList,
			        }
			    ],
			    calculable:false
			};
	
	
	

	myChart.setOption(option);
}

//单位净值曲线
function NAVADJLine(data){
	var xList=[];
	var yList=[];
	for(var i=0;i<data.length;i++){
		xList.push(data[i].navDate.substring(0,10));
		yList.push(data[i].navaDj);
	}
	
	var myChart = echarts.init(document.getElementById('NAVADJLine'));


	var dateList = xList;
	var valueList = yList;
		option = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    title: {
			        left: 'center',
			        text: '组合单位净值曲线',
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataZoom : {show: true},
			            dataView : {show: true},
			            magicType : {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    dataZoom : {
			        show : true,
			        realtime : true,
			        //orient: 'vertical',   // 'horizontal'
			        //x: 0,
			        y: 36,
			        //width: 400,
			        height: 20,
			        //backgroundColor: 'rgba(221,160,221,0.5)',
			        //dataBackgroundColor: 'rgba(138,43,226,0.5)',
			        //fillerColor: 'rgba(38,143,26,0.6)',
			        //handleColor: 'rgba(128,43,16,0.8)',
			        //xAxisIndex:[],
			        //yAxisIndex:[],
			        start : 40,
			        end : 60
			    },
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : false,
			            data : dateList,
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'单位净值',
			            type:'line',
			            data:valueList,
			        }
			    ],
			    calculable:false
			};
	
	
	
	myChart.setOption(option);
}

</script>
