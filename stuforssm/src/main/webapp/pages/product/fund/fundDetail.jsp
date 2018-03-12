<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<c:url value="/js/echars/echarts.js"/>"></script>
</head>
<input type="hidden" value="${fundCode}" id="fund_del_code"/>
<input type="hidden" value="${fund.NAME}" id="fund_del_name"/>
<input type="hidden" value="${fund.INVTYPONE}" id="fund_del_invtyp"/>
<input type="hidden" value="${fund.BNAME}" id="fund_del_bname"/>
	<div class="easyui-layout" style="width:100%;height:100%;">
			<div class="easyui-tabs" data-options="fit:true,border:false,plain:true">
				<%-- <div title="<a onclick='fundDet2();'>基本信息</a>" data-options="href:''" style="padding: 10px; width: 100%x; height: 802px;padding:10px"> --%>
				<div title="基本信息" data-options="href:''" style="padding: 10px; width: 100%; height: 802px;padding:10px">
					<table class="tdmd" id="fddmeta" style="margin:30px 20px 20px 20px;width:90%;line-height:30px;border:1px solid #ffffff; border-collapse: collapse;">
						<tr style="background:#f4f4f4;">
							<td style="width:50%;text-align: left;">基金名称</td>
							<td id="fdddname" style="width:50%;text-align: left;">${fund.NAME}</td>
						</tr>
						<tr>
							<td style="width:50%;text-align: left;">基金代码</td>
							<td id="fdddcode" style="width:50%;text-align: left;">${fund.CODE}</td>
						</tr>
						<tr style="background:#f4f4f4;">
							<td style="width:50%;text-align: left;">资产类别</td>
							<td id="fddscode" style="width:50%;text-align: left;">${fund.SNAME}</td>
						</tr>
						<tr>
							<td style="width:50%;text-align: left;">基金状态</td>
							<td id="fddstatus" style="width:50%;text-align: left;">
								<c:if test="${fund.STATUS =='1' }">封闭期</c:if>
								<c:if test="${fund.STATUS =='2' }">暂停申购</c:if>
								<c:if test="${fund.STATUS =='3' }">暂停大额申购</c:if>
								<c:if test="${fund.STATUS =='4' }">开放申购</c:if>
							</td>
						</tr>
						<tr style="background:#f4f4f4;">
							<td style="width:50%;text-align: left;">管理费率</td>
							<td id="managfeeratio" style="width:50%;text-align: left;">${fund.MANAGFEERATIO}</td>
						</tr>
						 <tr>
							<td>投资类型一</td>
						    <td id="invtypone" >
						    	<c:if test="${fund.INVTYPONE =='1' }">混合型基金</c:if>
								<c:if test="${fund.INVTYPONE =='2' }">债券型基金</c:if>
								<c:if test="${fund.INVTYPONE =='3' }">股票型基金</c:if>
								<c:if test="${fund.INVTYPONE =='4' }">货币市场型基金</c:if>
								<c:if test="${fund.INVTYPONE =='5' }">其他基金</c:if>
						    </td>
						</tr>
						<tr style="background:#f4f4f4;">
							<td>投资类型二</td>
							<td id="invtyptwo">${fund.INVTYPTWO}</td>
						</tr>
						 <tr>
							<td>销售服务费</td>
							<td id="servicefee">${fund.SERVICEFEE}</td>
						</tr>
						<tr  style="background:#f4f4f4;">
							<td>风险类型</td>
							<td id="fundrisktyp">${fund.RISKLEVEL}</td>
						</tr>
						 <tr>
							<td>主题类型</td>
							<td id="choicetyp">${fund.CHOICETYP}</td>
						</tr>
						<tr  style="background:#f4f4f4;">
							<td>晨星基金类型</td>
							<td id="cxfundtyp">${fund.CXTYP}</td>
						</tr>
						 <tr>
							<td>申购费率前端</td>
							<td id="purchfeeratio_front">${fund.PURCHFEERATIO_FRONT}</td>
						</tr>
						<tr  style="background:#f4f4f4;">
							<td>申购费率后端</td>
							<td id="purchfeeratio_back_end">${fund.PURCHFEERATIO_BACK_END}</td>
						</tr>
						 <tr>
							<td>赎回费率前端</td>
							<td id="redemfeeratio_front">${fund.REDEMFEERATIO_FRONT}</td>
						</tr>
						<tr  style="background:#f4f4f4;">
							<td>赎回费率后端</td>
							<td id="redemfeeratio_back_end">${fund.REDEMFEERATIO_BACK_END}</td>
						</tr>
						<tr>
							<td>跟踪标的</td>
							<td id="COMPAREBASE">${fund.COMPAREBASE}</td>
						</tr>
						<tr  style="background:#f4f4f4;">
							<td>标的代码</td>
							<td id="bidcode">${fund.BIDCODE}</td>
						</tr>
						<tr>
							<td>跟踪误差</td>
							<td id="gend">${fund.trackError}</td>
						</tr>
					</table>
				</div>
				
				<!-- <div title="贝塔评分" data-options="href:''" style="padding:10px">
					<p>测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试
						测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试</p>
				</div> -->
				<div title="业绩" data-options="onOpen:function(){fddachie();}" style="padding: 10px; width: 100%; padding:10px">
					<div id="timeSearch" style="margin-left: 25%;">
						<form name="form1">
							选择时间
							<input type="radio" id="1month" name="radiobutton" value="1month"> <label for="1month">1月</label>
							<input type="radio" id="3month" name="radiobutton" value="3month"> <label for="3month">3月</label>
							<input type="radio" id="6month" name="radiobutton" value="6month"><label for="6month">6月</label>
							<input type="radio" id="1year" name="radiobutton" value="1year"><label for="1year">1年</label>
							<input type="radio" id="3year" name="radiobutton" value="3year"><label for="3year">3年</label>
							<input type="radio" id="5year" name="radiobutton" value="5year"><label for="5year">5年</label>
							<%--<input type="radio" id="thisyear" name="radiobutton" value="thisyear"><label for="thisyear">近年来</label>--%>
							<input type="radio" id="maxtime" name="radiobutton" value="maxtime"  checked><label for="maxtime">最大</label>
							<input type="radio" id="customized" name="radiobutton" value="customized"><label for="customized">自定义查询</label>
						</form>
					</div>
					<%--<a href="#" style="margin-left: 60%;" class="easyui-linkbutton" onclick="customizedSearch();" >切换查询</a>--%>
					<div id="customizediv" style="margin-left:-20%;visibility:hidden;">
						<table style="align:center;margin:20px auto">
							<tr>
								<td> 开始时间：</td>
								<td> <input id="fundDetailStartTime" class="easyui-datebox" editable="false"></td>
								<td> &nbsp;&nbsp;结束时间：</td>
								<td> <input id="fundDetailEndTime" class="easyui-datebox" editable="false"></td>
								<td> &nbsp;&nbsp; <a href="#" class="easyui-linkbutton"  onclick="fundDetailSearch();" >查询</a></td>
							</tr>
						</table>
					</div>
					<div  id="fddachievement" style="width:1000px;height:500px;padding-top:30px;"></div>
					<br>
					<div  id="fddvalued" style="width:1000px;height:500px;padding-top:30px;"></div>
					<br>
					<div  id="fddmonemillion" style="width:1000px;height:500px;padding-top:30px;"></div>
					<br>
					<div  id="fddmonesereven" style="width:1000px;height:500px;padding-top:30px;"></div>
					<br>
					<div  style="width:1000px;height:100px;padding-top:30px;">&nbsp;</div>
				</div>
				<div title="风险" data-options="href:''" style="padding:10px">
				<div style="margin:20px auto;">
					<input id="fddcode" name="fddcode" type="hidden" value="${fundCode}"/>
					<form id="fddRatioForm">
						<table stylt="height:80px;line-height:30px;">
							<tr>
								<!-- <th>产品代码：</th> -->
								<%-- <td><input id="fddcode" name="fddcode" type="hidden" value="${fundCode}"/></td> --%>
								<th style="width:140px;">&nbsp;</th>
								<th style="width:80px;">开始时间</th>
								<td><input id="fddStarTime" class="easyui-datebox" editable="false" name="fddStarTime" style="width:120px;"/></td>
								<th style="width:80px;"> 结束时间</th>
								<td><input id="fddEndTime" class="easyui-datebox" editable="false" name="fddEndTime" style="width:120px;"/></td>
								<th style="width:40px;">类型</th>
								<td><select id="fddCalTyp" class="easyui-combobox" data-options="editable:false" style="width:100px;" name="fddCalTyp">
									  <option value ="day">天</option>
									  <option value ="week">周</option>
									  <option value="month">月</option>
									  <option value="year">年</option>
									</select>
								</td>
								<th style="width:100px;">风险计算算法</th>
								<td><select id="item" class="easyui-combobox" data-options="editable:false" style="width:100px;">
									  <option value ="day">标准差</option>
									  <option value ="week">半方差</option>
									  <option value="month">左偏动差</option>
									</select>
								</td>
								<td style="width:80px;align:center;"><a class="easyui-linkbutton" href="javascript:void(0);" onclick="fddcaculate();">计算</a></td>
								<td style="width:80px;"><a class="easyui-linkbutton" href="javascript:void(0);" onclick="fddclear();">清空</a></td>
							</tr>
						</table>
					</form>
					<br>
					<table style="margin:10px 20px 30px 70px;width:1200px;line-height:30px;border:1px solid #ffffff; border-collapse: collapse;">
						<tr style="background:#f4f4f4;">
							<th>涨幅</th>
							<td id="fddIncresment"></td>
							<th>最大回撤</th>
							<td id="fddMaxRetreat"></td>
						</tr>
						<tr>
							<th>预期收益率</th>
							<td id="fddExpAnnualRadio"></td>
							<th>预期风险率</th>
							<td id="fddExpRiskRadio"></td>
						</tr>
						<tr style="background:#f4f4f4;">
							<th>标准差</th>
							<td id="fddStandardDeviation"></td>
							<th>夏普比率</th>
							<td id="fddSharpRadio"></td>
						</tr>
						</table>
					</div>
					<div id="fddYieldRatioLine" style="width:1000px;height:500px;padding-top:30px;"></div>
					<br>
					<div id="fddMaxdrawnLine" style="width:1000px;height:500px;padding-top:30px;"></div>
					<div style="width:1000px;height:100px;padding-top:30px;">&nbsp;</div>
				</div>
				<!-- <div title="经理" data-options="href:''" style="padding:10px"></div>
				<div title="公司" data-options="href:''" style="padding:10px"></div>
				<div title="主题" data-options="href:''" style="padding:10px"></div> -->
				<!-- <div title="测试" data-options="href:'pages/product/fund/yieldriskRatio.jsp'" style="padding:10px"></div> -->
			</div>
	<!-- 	</div> -->
	</div>
	
<script type="text/javascript">
//判断今天是该年的第几天
	function getDaysOfThisYear(){
        var dateArr = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
        var date = new Date();
        var day = date.getDate();
        var month = date.getMonth(); //getMonth()是从0开始
        var year = date.getFullYear();
        var result = 0;
        for ( var i = 0; i < month; i++) {
            result += dateArr[i];
        }
        result += day;
		//判断是否闰年
        if (month > 1 && (year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            result += 1;
        }
        return result;
    }


    $(document).ready(function() {

        $('input[type=radio][name=radiobutton]').change(function() {

            if(this.value=="customized"){
                $("#fundDetailStartTime").datebox("setValue", "");
                $("#fundDetailEndTime").datebox("setValue", "");
                document.getElementById("customizediv").style.visibility = "visible";
                fddachie(null,null,null,1);
			}else{
                if(document.getElementById("customizediv").style.visibility=='visible') {
                    document.getElementById("customizediv").style.visibility = "hidden";
                }
                if(this.value=="1month"){
                    fddachie(1*30,null,null,0);
                }else if(this.value=="3month"){
                    fddachie(3*30,null,null,0);
                }else if(this.value=="6month"){
                    fddachie(6*30,null,null,0);
                }else if(this.value=="1year"){
                    fddachie(1*365,null,null,0);
                }else if(this.value=="3year"){
                    fddachie(3*365,null,null,0);
                }else if(this.value=="5year"){
                    fddachie(5*365,null,null,0);
                }else if(this.value=="thisyear"){
                    fddachie(getDaysOfThisYear(),null,null,0);
                }else if(this.value=="maxtime"){
                    fddachie(null,null,null,0);
                }
			}
        });
    });

	//自定义日期查询
	function fundDetailSearch(){
        fddachie(null,$("#fundDetailStartTime").datebox("getValue"),$("#fundDetailEndTime").datebox("getValue"),1);
	}
	//切换按钮
	/*function customizedSearch(){
	    if(document.getElementById("customizediv").style.visibility=='hidden'){
            $("#fundDetailStartTime").datebox("setValue", "");
            $("#fundDetailEndTime").datebox("setValue", "");
            document.getElementById("customizediv").style.visibility = "visible";
            fddachie(null,null,null,1);
		}else if(document.getElementById("customizediv").style.visibility=='visible'){
            document.getElementById("customizediv").style.visibility = "hidden";
            fddachie(null,null,null,0);
		}

	}*/

	 //加载业绩信息
	 function fddachie(days,startTime,endTime,flag){
	     if(days==undefined){
	         days=null;
		 }
		 if(flag==undefined || flag==null){
	         flag=0;
		 }
		 var productCode=$('#fund_del_code').val();
		 var productName=$('#fund_del_name').val();
		 var productTyp=$('#fund_del_invtyp').val();
		 var bName=$('#fund_del_bname').val();
		 
		 console.log(productTyp);
		 //alert(productName);
		 //alert(productCode);
		 $.ajax({
				type:"POST",
				url:"${pageContext.request.contextPath}/fund/toFundAchie",
				data:{code:productCode,name:'',bidCode:'000300.SH',days:days,startTime:startTime,endTime:endTime,flag:flag},
				timeout:20000,
				cache:false,
				success:function(data){
					fddachieLine(productName,bName,data.fdyieldLst,data.zsyieldLst,data.dtLst);
                    if(document.getElementById("customizediv").style.visibility=='visible'){
                        if(startTime=="" || startTime==null){
                            $("#fundDetailStartTime").datebox("setValue", data.dtLst[0]);
                        }
						if(endTime=="" || endTime==null){
                            $("#fundDetailEndTime").datebox("setValue", data.dtLst[data.dtLst.length-1]);
						}

                    }
				},
				error:function(){
					alert("请求失败");
				}
			});
		 
		 if(productTyp!=4){
		 //单位净值
		 $.ajax({
				type:"POST",
				url:"${pageContext.request.contextPath}/fund/fundValue",
				data:{code:productCode,days:days,startTime:startTime,endTime:endTime,flag:flag},
				timeout:20000,
				cache:false,
				success:function(data){
					fddNavadjLine(productName,data);
				},
				error:function(){
					alert("请求失败");
				}
			}); 
		 }
		//万份收益、七日年化
		 if(productTyp==4){
			 $.ajax({
					type:"POST",
					url:"${pageContext.request.contextPath}/fund/fundMonetaryYield",
					/* data:{code:productCode,}, */
					data:{code:productCode,days:days,startTime:startTime,endTime:endTime,flag:flag},
					timeout:20000,
					cache:false,
					success:function(data){
					console.log(data);
					var xList=[];
					var yList=[];
					var yList2=[];
					for(var i=0;i<data.length;i++){
						xList.push(data[i].pdate.substring(0,10));
						yList.push(data[i].millionyield);
						yList2.push(data[i].serevenyield);
					}
					fddMoneMillionLine(productName,yList,xList);
					fddMoneSerevenLine(productName,yList2,xList);
					},
					error:function(){
						alert("请求失败");
					}
				}); 
		 }
	 }
	 
	
	//计算基金风险
	function fddcaculate() {
		var productCode=$('#fddcode').val();
		var productName=$('#fund_del_name').val();
		 var bName=$('#fund_del_bname').val();
		var startTime=$("#fddStarTime").datebox("getValue");
		var endTime=$("#fddEndTime").datebox("getValue");
		var url="";
		if(productCode==''||productCode==null){
			alert("产品代码不能为空!");
			return ;
		}
		var selected=$("#fddCalTyp").combobox("getValue");
		if(selected=='day'){
			url="${pageContext.request.contextPath}/fund/cycle/day/getFundBasicInfo";
		}else if(selected=='week'){
			url="${pageContext.request.contextPath}/fund/cycle/week/getFundBasicInfo";
		}else if(selected=='month'){
			url="${pageContext.request.contextPath}/fund/cycle/month/getFundBasicInfo";
		}else if(selected=='year'){
			url="${pageContext.request.contextPath}/fund/cycle/year/getFundBasicInfo";
		}
		
		//获取涨幅和最大回撤系数
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/fund/cycle/day/getFundBasicInfo",
			data:{productCode:productCode,startTime:startTime,endTime:endTime},
			timeout:20000,
			cache:false,
			beforeSend: function () {
				 load();
			},
			complete: function () {
				 disLoad();
			},
			success:function(data){
				$('#fddIncresment').html(data[0].growth);
				$('#fddMaxRetreat').html(data[0].maxdrawdown);
			},
			error:function(){
				alert("请求失败");
			}
		});
		
		//获取预期收益率和预期风险率
		$.ajax({
			type:"POST",
			url:url,
			data:{productCode:productCode,startTime:startTime,endTime:endTime},
			timeout:20000,
			cache:false,
			beforeSend: function () {
				 load();
			},
			complete: function () {
				 disLoad();
			},
			success:function(data){
				if(startTime==''||startTime==null){
					$("#fddStarTime").datebox("setValue", data[0].ipo_START_DATE.substring(0,10));   
				}
				if(endTime==''||endTime==null){
					$("#fddEndTime").datebox("setValue", data[0].ipo_END_DATE.substring(0,10));  
				}
				$('#fddExpAnnualRadio').html(Number(data[0].expected_annualized_return).toFixed(4));
				$('#fddExpRiskRadio').html(Number(data[0].expected_risk_ratio).toFixed(4));
			},
			error:function(){
				alert("请求失败");
			}
		});
		//计算sharp
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/fund/fundSharp",
			data:{code:productCode,startTime:startTime,endTime:endTime},
			timeout:20000,
			cache:false,
			success:function(data){
				$('#fddSharpRadio').html(Number(data.sharp).toFixed(4));
				$('#fddStandardDeviation').html(Number(data.standard).toFixed(4));
			},
			error:function(){
				alert("请求失败");
			}
		});
		//请求后台数据 收益率曲线
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/fund/yieldRatioLine",
			data:{productCode:productCode,startTime:startTime,endTime:endTime},
			timeout:20000,
			cache:false,
			success:function(data){
				if(startTime==''||startTime==null){
					$('#fddStarTime').val(data[0].navDate.substring(0,10));
				}
				if(endTime==''||endTime==null){
					$('#fddEndTime').val(data[data.length-1].navDate.substring(0,10));
				}
				fddYieldRatioLine(productName,data);
				//fddNavadjLine(data);
			},
			error:function(){
				alert("请求失败");
			}
		});
		
		//最大回撤曲线
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath}/fund/toFundDrawdowns",
			//data:{productCode:productCode,startTime:startTime,endTime:endTime},
			data:{productCode:productCode,benchmarkCode:'000300.SH',startDate:startTime,endDate:endTime},
			cache:false,
			beforeSend: function () {
				 load();
			},
			complete: function () {
				 disLoad();
			},
			success:function(data){
				fddMaxDraws(productName,bName,data.fundDrawdowns,data.benchmarkDrawdowns,data.dates);
			},
			error:function(){
				alert("请求失败");
			}
		});
		
		
		
		
		
	}
	//点击清空按钮出发事件
	function fddclear() {
	    $("#fddRatioForm").find("input").val("");//找到form表单下的所有input标签并清空
	}
	
	 /*=============================产品涨幅|所属小类指数涨幅 、单位净值走势曲线=============================================-  */
	 //基金、指数涨幅曲线
	function fddachieLine(productName,bName,fdyieldLst,zsyieldLst,dtLst){
		var myChart = echarts.init(document.getElementById('fddachievement'));
			option = {
				    tooltip: {
				        trigger: 'axis'
				        /*formatter: '{a1}:{c1}'+'%'+'<br/>'+'{a1}:{c1}'+'%' */
				       /*  position: function (pt) {
				            return [pt[0], '10%'];
				        } */
				    },
				    title: {
				        x: 'center',
				        text: '产品涨幅走势',
				    },
				    legend: {
				    	orient: 'horizontal',
				         x: 'center',
				         y: 'bottom',
				        data:[productName,bName]
				    },
				   /*  toolbox: {
					    show : true,
					   	x:'72%',
				        feature : {
				            mark : {show: true},
				            dataZoom : {show: true},
				            dataView : {show: true},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    }, */
				 dataZoom : {
					 	bottom:"5%", 
				        show : true,
				        realtime : true,
				        height: 20,
				        start : 0,
				        end : 100
				    },
				    grid: {
				        bottom: '10%',
				        containLabel: true
				 },
				    xAxis: {
				        type: 'category',
				        boundaryGap: false,
				        data: dtLst
				    },
				    yAxis: {
				        type: 'value',
				        boundaryGap: [0, '100%']
				    },
				    series: [
				        {
				            name:productName,
				            type:'line',
				            smooth:true,
				            symbol: 'none',
				            sampling: 'average',
				            itemStyle: {
				                normal: {
				                	 color: '#d4726f'
				                }
				            },
				            areaStyle: {
				                normal: {
				                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
				                        offset: 0,
				                        color: '#d4726f'
				                    }, {
				                        offset: 1,
				                        color: '#d4726f'
				                    }])
				                }
				            },
				            data: fdyieldLst
				        },
				         {
				            name:bName,
				            type:'line',
				            smooth:true,
				            symbol: 'none',
				            sampling: 'average',
				            itemStyle: {
				                normal: {
				                	 color: '#6e7d88'
				                }
				            },
				            areaStyle: {
				                normal: {
				                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
				                        offset: 0,
				                        color: '#6e7d88'
				                    }, {
				                        offset: 1,
				                        color: '#6e7d88'
				                    }])
				                }
				            },
				            data:zsyieldLst
				        }
				    ],
				    calculable:false
				};

		myChart.setOption(option);
	} 
	 
	//单位净值曲线
	function fddNavadjLine(productName,data){
		var xList=[];
		var yList=[];
		for(var i=0;i<data.length;i++){
			xList.push(data[i].navlatestdate.substring(0,10));
			yList.push(data[i].navadj);
		}
		var myChart = echarts.init(document.getElementById('fddvalued'));
		var dateList = xList;
		var valueList = yList;
			option = {
					tooltip: {
				        trigger: 'axis'
				    },
				    title: {
				        x: 'center',
				        text: '产品单位净值走势',
				    },
				  legend: {
				    	orient: 'horizontal',
				         x: 'center',
				         y: 'bottom',
				        data:[productName]
				    },
				   /*  toolbox: {
					    show : true,
					   	x:'72%',
				        feature : {
				            mark : {show: true},
				            dataZoom : {show: true},
				            dataView : {show: true},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    }, */
				 	dataZoom : {
					 	bottom:"5%", 
				        show : true,
				        realtime : true,
				        height: 20,
				        start : 0,
				        end : 100
				    },
				    grid: {
				        bottom: '10%',
				        containLabel: true
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
				            type : 'value',
				            min:minValue(valueList)
				        }
				    ],
				    series : [
				        {
				            name:productName,
				            type:'line',
				            symbol:'none',
				            data:valueList,
				        }
				    ],
				    calculable:false
				};
		myChart.setOption(option);
	}
	
	
	/* ===============================万份收益、7日年化收益曲线======================================= */
	
	//万份收益
	function fddMoneMillionLine(productName,yList,xList){
		var myChart = echarts.init(document.getElementById('fddmonemillion'));
		var dateList = xList;
		var valueList = yList;
			option = {
					tooltip: {
				        trigger: 'axis'
				    },
				    title: {
				        x: 'center',
				        text: '每万份收益走势',
				    },
				  legend: {
				    	orient: 'horizontal',
				         x: 'center',
				         y: 'bottom',
				        data:[productName]
				    },
				   /*  toolbox: {
					    show : true,
					   	x:'72%',
				        feature : {
				            mark : {show: true},
				            dataZoom : {show: true},
				            dataView : {show: true},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    }, */
				 	dataZoom : {
					 	bottom:"5%", 
				        show : true,
				        realtime : true,
				        height: 20,
				        start : 0,
				        end : 100
				    },
				    grid: {
				        bottom: '10%',
				        containLabel: true
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
				            type : 'value',
				            min:minValue(valueList)
				        }
				    ],
				    series : [
				        {
				            name:productName,
				            type:'line',
				            symbol:'none',
				            data:valueList,
				        }
				    ],
				    calculable:false
				};
		myChart.setOption(option);
	}
	
	//七日年化
	function fddMoneSerevenLine(productName,yList2,xList){
		var myChart = echarts.init(document.getElementById('fddmonesereven'));
		var dateList = xList;
		var valueList = yList2;
			option = {
					tooltip: {
				        trigger: 'axis'
				    },
				    title: {
				        x: 'center',
				        text: '7日年化收益率走势',
				    },
				  legend: {
				    	orient: 'horizontal',
				         x: 'center',
				         y: 'bottom',
				        data:[productName]
				    },
				    /* toolbox: {
					    show : true,
					   	x:'72%',
				        feature : {
				            mark : {show: true},
				            dataZoom : {show: true},
				            dataView : {show: true},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    }, */
				 	dataZoom : {
					 	bottom:"5%", 
				        show : true,
				        realtime : true,
				        height: 20,
				        start : 0,
				        end : 100
				    },
				    grid: {
				        bottom: '10%',
				        containLabel: true
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
				            type : 'value',
				            min:minValue(valueList)
				        }
				    ],
				    series : [
				        {
				            name:productName,
				            type:'line',
				            symbol:'none',
				            data:valueList,
				        }
				    ],
				    calculable:false
				};
		myChart.setOption(option);
	}
	
	
	
	/*================================产品收益率、最大回撤=====================================  */
	 //最大回撤曲线
	 function fddMaxDraws(productName,bName,fundDrawdowns,benchmarkDrawdowns,dates){
		var myChart = echarts.init(document.getElementById('fddMaxdrawnLine'));
			option = {
				    tooltip: {
				        trigger: 'axis'
				        /*formatter: '{a1}:{c1}'+'%'+'<br/>'+'{a1}:{c1}'+'%' */
				       /*  position: function (pt) {
				            return [pt[0], '10%'];
				        } */
				       
				    },
				    title: {
				        x: 'center',
				        text: '产品最大回撤',
				    },
				    legend: {
				    	orient: 'horizontal',
				         x: 'center',
				         y: 'bottom',
				        data:[productName,bName]
				    },
				   /*  toolbox: {
					    show : true,
					   	x:'72%',
				        feature : {
				            mark : {show: true},
				            dataZoom : {show: true},
				            dataView : {show: true},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    }, */
				 dataZoom : {
					 	bottom:"5%", 
				        show : true,
				        realtime : true,
				        height: 20,
				        start : 0,
				        end : 100
				    },
				    grid: {
				        bottom: '10%',
				        containLabel: true
				 },
				    xAxis: {
				        type: 'category',
				        boundaryGap: false,
				        data: dates
				    },
				    yAxis: {
				        type: 'value',
				        boundaryGap: [0, '100%']
				    },
				    series: [
				        {
				            name:productName,
				            type:'line',
				            smooth:true,
				            symbol: 'none',
				            sampling: 'average',
				            itemStyle: {
				                normal: {
				                	 color: '#d4726f'
				                }
				            },
				            areaStyle: {
				                normal: {
				                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
				                        offset: 0,
				                        color: '#d4726f'
				                    }, {
				                        offset: 1,
				                        color: '#d4726f'
				                    }])
				                }
				            },
				            data: fundDrawdowns
				        },
				         {
				            name:bName,
				            type:'line',
				            smooth:true,
				            symbol: 'none',
				            sampling: 'average',
				            itemStyle: {
				                normal: {
				                	 color: '#6e7d88'
				                }
				            },
				            areaStyle: {
				                normal: {
				                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
				                        offset: 0,
				                        color: '#6e7d88'
				                    }, {
				                        offset: 1,
				                        color: '#6e7d88'
				                    }])
				                }
				            },
				            data:benchmarkDrawdowns
				        }
				    ],
				    calculable:false
				};
		myChart.setOption(option);
	} 
	 
	
	//收益率曲线
	function fddYieldRatioLine(productName,data){
		//alert(data);
		var xList=[];
		var yList=[];
		for(var i=0;i<data.length;i++){
			xList.push(data[i].navDate.substring(0,10));
			yList.push(data[i].yieldRatio);
		}
		var myChart = echarts.init(document.getElementById('fddYieldRatioLine'));
		var dateList = xList;
		var valueList = yList;
			option = {
					tooltip: {
				        trigger: 'axis'
				    },
				    title: {
				        x: 'center',
				        text: '收益率曲线',
				    },
				  legend: {
				    	orient: 'horizontal',
				         x: 'center',
				         y: 'bottom',
				        data:[productName]
				    },
				   /*  toolbox: {
					    show : true,
					   	x:'72%',
				        feature : {
				            mark : {show: true},
				            dataZoom : {show: true},
				            dataView : {show: true},
				            magicType : {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {show: true}
				        }
				    }, */
				 	dataZoom : {
					 	bottom:"5%", 
				        show : true,
				        realtime : true,
				        height: 20,
				        start : 0,
				        end : 100
				    },
				    grid: {
				        bottom: '10%',
				        containLabel: true
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
				            name:productName,
				            type:'line',
				            symbol:'none',
				            data:valueList,
				        }
				    ],
				    calculable:false
				};
		myChart.setOption(option);
	}

</script>
