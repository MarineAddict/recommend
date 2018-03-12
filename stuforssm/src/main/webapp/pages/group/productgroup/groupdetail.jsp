<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<c:url value="/js/echars/echarts.js"/>"></script>
</head>
产品组合代码：<input id="id" type="text">
产品组合名称：<input id="product_group_name" type="text"></br>
sharp比率：<input id="sharpeRatio" type="text">
创建时间：<input id="create_time" type="text">
<table id="productgroup"></table>
<div id="productPropotion" style="width: 400px;height:200px;"></div>
<script type="text/javascript">
//获取数据
var id=<%=request.getParameter("id")%>;
var result;
var array = [];
$.ajax({
	type:"POST",
	url:"${pageContext.request.contextPath}/productGroup/productGroupId/"+id+"/getProductGroupDetailsInfo",
	timeout:20000,
	cache:false,
	success:function(data){
		console.log(data);
		pieChart(data);
		/* result=data; */
		$('#id').val(data.id);
		$('#product_group_name').val(data.product_group_name);
		$('#sharpeRatio').val(data.sharpeRatio);
		$('#create_time').val(data.create_time);
		
		var content="";
		for(var i=0;i<data.pgdList.length;i++){
			content+="<tr>";
			content+="<th>产品代码</th>";
			content+="<td>"+data.pgdList[i].product_code+"</td>";
			content+="<th>产品占比</th>";
			content+="<td>"+data.pgdList[i].proportion*100+"%</td>";
			content+="<th>最后修改时间</th>";
			content+="<td>"+data.pgdList[i].last_mod_time.substring(0,19)+"</td>";
			content+="</tr>";
		}
		document.getElementById("productgroup").innerHTML=content;
	},
	error:function(){
		alert("请求失败");
	}
});

//画饼图
function pieChart(result){
	for(var i=0;i<result.pgdList.length;i++){
		var map={};
		map.name=result.pgdList[i].product_code;
		map.value=result.pgdList[i].proportion;
		array[i]=map;
	}

	var myChart = echarts.init(document.getElementById('productPropotion'));
	option = {
		    title : {
		        text: '',
		        subtext: '',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		   /*  legend: {
		        orient : 'vertical',
		        x : 'left',
		        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
		    },  */
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel'],
		                option: {
		                    funnel: {
		                        x: '25%',
		                        width: '50%',
		                        funnelAlign: 'left',
		                        max: 1548
		                    }
		                }
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'产品占比',
		            type:'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:array,
		            itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                position: 'outer',
                                formatter: '{b} : ({d}%)'
                            }/* ,
                            labelLine: {
                                show: false
                            } */
                        }
                    }
		        }
		    ],
		    
		};
	myChart.setOption(option);    
}
              
</script>

