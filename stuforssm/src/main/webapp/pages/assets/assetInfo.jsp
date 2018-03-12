<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<style type="text/css">




</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<c:url value="/js/echars/echarts.js"/>"></script>
<!-- <style type="text/css">
	table{
		width:1000px;
		}
</style> -->
</head>
	
<div data-options="region:'north',title:'查询'"style="height: 40px; background: #F4F4F4;">
		<table> 
			<tr style="margin:20px auto">
				<td>请选择体系：</td>
				<td><a class="easyui-linkbutton" onclick="searchConbank();">建行</a></td>
				<td><a class="easyui-linkbutton" onclick="searchNetBank();">互联网</a></td>
			</tr>
		</table>
	</div>
	
	<div id="dt1" style="display:none;">
		<table id="catBigConList"></table>
		<button style="float:right;" onclick="ratioCombination()">组合配比</button>
		<div style="height: 30px;clear:both;margin-bottom:20px">
		<div style="display: inline-block; width: 30%;height: 100%"></div>
		   <div style="display: inline-block; width: 5%;height: 100%">选择时间</div>
		   
		   <div style="display: inline-block; width: 5%;height: 100%">
		    <input type="radio" name="cycle" value="1">
		    <label    >近1个月</label>
		   </div>
		   <div style="display: inline-block; width: 5%;height: 100%">
		    <input type="radio" name="cycle" value="3">
		    <label    >近3个月</label>
		   </div>
		   <div style="display: inline-block;width: 5%;height: 100%">
		   <input type="radio" name="cycle" value="6">
		    <label    >近6个月</label>
		   </div>
		   <div style="display: inline-block;width: 5%;height: 100%">
		   <input type="radio" name="cycle" value="9">
		    <label    >近9个月</label>
		   </div>
		   <div style="display: inline-block;width: 5%;height: 100%">
		  <input type="radio" name="cycle" value="12">
		    <label    >近1年</label>
		   </div>
		   <div style="display: inline-block;width: 5%;height: 100%">
		   <input type="radio" name="cycle" value="36">
		    <label    >近3年</label>
		   </div>
		   <div style="display: inline-block;width: 5%;height: 100%">
		   <input type="radio" name="cycle" value="60">
		    <label  >近5年</label>
		   </div>
		   <div style="display: inline-block;width: 5%;height: 100%">
		    <input id="customize" type="radio" name="cycle" value="0">
		    <label>自定义</label>
		   </div>
		</div>
		
	<div style="width: 90%; height: 30px;">
		<div id="customize_block" style="display: none;height: 100%">
		 <div style="width: 35%;display: inline-block;"></div>
		  <label>开始时间:</label>
		  <input type="text" class="easyui-datebox" id="startTime">
		  <div style="width: 2%;display: inline-block;"></div>
		  <label>结束时间:</label>
		  <input type="text" class="easyui-datebox" id="endTime">
		  <a id="checkButton" href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
		</div>
	</div>
	
		<div id="biddingLine" style="width:90%;height:800px;margin:50px 50px 100px 50px;"></div>
	</div>
	
	
	
	<div  id="dt2" style="display:none;">
		<table id="catBigNetList" style="border :black 1px solid"> 
		<tr style="margin:20px auto">
			<td>测试1</td>
			<td>测试2</td>
			<td>测试3</td>
		</tr>
	</table>
	</div>

	<div id="biddetail">Dialog Content.</div>

	
	
	
	
<script type="text/javascript">
//给所有按钮绑定单机事件
$("button[class='hoverButton']").click(function(){
var cycleType=$(this).val();
goGetData(cycleType);
})

//自定义radio绑定div跳出事件
$("#customize").click(function(){
    $("#customize_block").css('display','block');
})
//查询按钮
$("#checkButton").click(function(){
	var cycleType;
	var startTime=$("#startTime").datebox('getValue');
	var endTime=$("#endTime").datebox('getValue');
	console.log(startTime+","+endTime);
	goGetData(cycleType,startTime,endTime);
})
	
	
$("input[name='cycle']").not("#customize").click(function(){
	$("#customize_block").css('display','none');
	//同时发送请求
	var cycleType=$(this).val();
	goGetData(cycleType);
})

window.onload=function (){}
function searchConbank(){
	$('#dt2').css('display', 'none');
	$('#dt1').css('display', 'block');
	$('#catBigConList').datagrid({
		width : '100%',
		url : "${pageContext.request.contextPath}/category/qryCategory",
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
			field : 'bgName',
			title : '大类名称',
			width : 200,
			align : 'center'
		/* }, {
			field : 'smCode',
			title : '小类代码',
			width : 200,
			align : 'center' */
		}, {
			field : 'smName',
			title : '小类名称',
			width : 200,
			align : 'center'
		}, {
			field :'bidName' ,
			title : '标的',
			width : 200,
			align : 'center'
		}, {
			field : 'caozuo' ,
			title : '标的详情',
			width : 200,
			align : 'center',
			formatter : function(value, row, index) {
//				return "<a onclick='openDialog2("+row.bidCode+");' iframe='0' >详情</a>"; 
				return "<a name='indexRow' value='"+index+"' onclick='openDialog2("+index+");' iframe='0' >详情</a>"; 
			}
		}]] 
	});
	
	//alert(12);
	//加载历史单位净值曲线图
	$.ajax({
		type:"POST",
		url:"${pageContext.request.contextPath}/category/qryCategoryData",
		cache:false,
		asyn:true,
		 beforeSend: function () {
         	load();
         },
         complete: function () {
             disLoad();
         },
		success:function(result){
			console.log(result);
			getBiddingDataLine(result);
		},
		error:function(){
			alert("请求失败");
		}
	});
	
}
//根据循环周期查找数据
function goGetData(cycleType,startTime,EndTime){
	if (cycleType==null||cycleType==""){
		
		}
	//加载历史单位净值曲线图
	$.ajax({
		type:"POST",
		url:"${pageContext.request.contextPath}/category/qryCategoryData",
		data:{
            cycleType:cycleType,
            startTime:startTime,
            EndTime:EndTime
			},
		cache:false,
		asyn:true,
		 beforeSend: function () {
         	load();
         },
         complete: function () {
             disLoad();
         },
		success:function(result){
			console.log(result);
			getBiddingDataLine(result);
		},
		error:function(){
			alert("请求失败");
		}
	});
}


function searchNetBank(){
	$('#dt1').css('display', 'none');
	$('#dt2').css('display', 'block');
}

function ratioCombination(){
	var indexRow =document.getElementsByName("indexRow");
	var bidArr = new Array();
	for (var i = 0, j = indexRow.length; i < j; i++){
		bidArr[i] = $('#catBigConList').datagrid('getData').rows[i].bidCode;
	}
	addTab("MVO模型","generateGroup/tomvoModel?bidCode="+bidArr,"",false);
}

function openDialog2(index){
	//addTab("标的历史收益", "${pageContext.request.contextPath}/pages/assets/assetDetail.jsp?bidCode='+bidCode", "icon-chart-organisation", false);
	var bidCode = $('#catBigConList').datagrid('getData').rows[index].bidCode;
	var bidName = $('#catBigConList').datagrid('getData').rows[index].bidName;
	if(bidCode||bidName){
		$('#biddetail').dialog({
			title: '标的详情',
			width: 800,
			height: 600,
			closed: false,
			cache: false,
			href: '${pageContext.request.contextPath}/category/toQryBidData?bidCode='+bidCode,
			//href: '${pageContext.request.contextPath}/pages/assets/bidInfo.jsp?bidCode='+bidCode+'&bidName='+bidName,
			modal: true
		});
	}
}





//收益率曲线
function getBiddingDataLine(data){

	
	var xList=[];
	var yList=[];

	var yList1=[];
	var xList1=[];
	var yList2=[];
	var xList2=[];
	var yList3=[];
	var xList3=[];
	var yList4=[];
	var xList4=[];
	var yList5=[];
	var xList5=[];
	var yList6=[];
	var xList6=[];
	var yList7=[];
	var xList7=[];
	var yList8=[];
	var xList8=[];
	
    var flag=0;//这个参数是为了找到x轴的第一个日期值得，因为后台order by按照时间分好，转到前台如果选择1号指标的横坐标为基准坐标，
    //那么第一次找到的时间一定是基准时间，之后所有其他数据需要根据这个值进行填充数据
    var startTime;//X轴基准日期值
    
	
	for(var i=0;i<data.length;i++){
  		var dataTime=new Date(data[i].day);//该条记录的时间
  		
  		if(data[i].code=='1'){
  	  		if(flag==0){
  	  		startTime=new Date(dataTime);
             flag=1;
  	  	  		}
  			xList.push(data[i].day.substring(0,10));
  		}

  		//对code进行遍历
  		if(data[i].code=='1'){
  	  		xList1.push(data[i].day);
  			yList1.push(data[i].data);
  		}
  		else if(data[i].code=='2'){
  			xList2.push(data[i].day);
  			yList2.push(data[i].data);
  		}
  		else if(data[i].code=='3'){
  			xList3.push(data[i].day);
  			yList3.push(data[i].data);
  		}
  		else if(data[i].code=='4'){
  			xList4.push(data[i].day);
  			yList4.push(data[i].data);
  		}
  		else if(data[i].code=='5'){
  			xList5.push(data[i].day);
  			yList5.push(data[i].data);
  		}
  		else if(data[i].code=='6'){
  			xList6.push(data[i].day);
  			yList6.push(data[i].data);
  		}
  		else if(data[i].code=='7'){
  			xList7.push(data[i].day);
  			yList7.push(data[i].data);
  		}
  		else if(data[i].code=='8'){
  			xList8.push(data[i].day);
  			yList8.push(data[i].data);
  		} 	 
		//yList.push(data[i].data);
	}

var xListAll=[xList1,xList2,xList3,xList4,xList5,xList6,xList7,xList8];
var yListAll=[yList1,yList2,yList3,yList4,yList5,yList6,yList7,yList8];

var dataLength=xList.length; //总数据的条数

for (var i=0;i<xListAll.length;i++){
  //以Ｘｌｉｓｔ为基准,发现少数据
	if (xListAll[i].length<dataLength){
        // 遍历xList统计需要插入多少个数据
        var count=parseInt((new Date(xListAll[i][0])-new Date(xList[0]))/1000/60/60/24); //计算为多少天
                console.log(count);
                   //日期一致，将count值个null插入yListAll[I]之前
                   for(var x=0;x<count;x++){
                     yListAll[i].splice(x,0,null);
                   }

            }

		}

  
  


	

	var bidchart = echarts.init(document.getElementById('biddingLine'));
    

	var dateList = xList;
	var valueList = yList;
	option = {
				title: {
				        text: '历史单位净值',
				        x:'center'
				    },
				tooltip: {
				        trigger: 'axis'
				    },
				legend: {
			         orient: 'horizontal',
			         x: 'center',
			         y: 'bottom',
				     data: ['中证1000指数','中证财富1000指数','上证港股通指数','SPY指数','中证货币基金指数','中证普通债基金','中证纯债债券型基金指数','上海黄金交易所AU9995黄金']
				    },
				  grid: {
				        bottom: '10%',
				        containLabel: true
				 },
				 toolbox: {
					    show : true,
					   	x:'75%',
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
					 	bottom:"5%", 
				        show : true,
				        realtime : true,
				        height: 20,
				        start : 0,
				        end : 100
				    },
				 xAxis: {
				        type: 'category',
				        boundaryGap: false,
				        data:dateList
				    },
				 yAxis: {
				        type: 'value',
				        minInterval: 5,
				        max:function(max){
                            return parseInt(max.max+20);
					        },
					        min:function(min){
	                            return parseInt(min.min-20);
						        }
			        
				    },
				 series: [
				        {
				            name:'中证1000指数',
				            type:'line',
				            symbol:'none',
				            data:yList1
				        },{
				            name:'中证财富1000指数',
				            type:'line',
				            symbol:'none',
				            data:yList2
				        },{
				            name:'上证港股通指数',
				            type:'line',
				            symbol:'none',
				            data:yList3
				        },{
				            name:'SPY指数',
				            type:'line',
				            symbol:'none',
				            data:yList4
				        },{
				            name:'中证货币基金指数',
				            type:'line',
				            symbol:'none',
				            data:yList5
				        },{
				            name:'中证普通债基金',
				            type:'line',
				            symbol:'none',
				            data:yList6
				        },{
				            name:'中证纯债债券型基金指数',
				            type:'line',
				            symbol:'none',
				            data:yList7
				        },{
				            name:'上海黄金交易所AU9995黄金',
				            type:'line',
				            symbol:'none',
				            data:yList8
				        }
				    ]
				};
		bidchart.setOption(option);
}



</script>