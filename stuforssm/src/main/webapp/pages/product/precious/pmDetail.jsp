<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<c:url value="/js/echars/echarts.js"/>"></script>
</head>
<div class="easyui-layout" style="width:100%;height:100%;">
    <div class="easyui-tabs" data-options="fit:true,border:false,plain:true">
        <div title="贵金属信息" data-options="href:''" style="padding: 10px; width: 100%; height: 802px;padding:10px">
            <table class="tdmd" id="fddmeta" style="margin:30px 20px 20px 20px;width:90%;line-height:30px;border:1px solid #ffffff; border-collapse: collapse;">
                <tr style="background:#f4f4f4;">
                    <td style="width:50%;text-align: left;">贵金属名称</td>
                    <td id="pmDetailName" style="width:50%;text-align: left;">${pm.NAME}</td>
                </tr>
                <tr>
                    <td style="width:50%;text-align: left;">贵金属代码</td>
                    <td id="pmDetailCode" style="width:50%;text-align: left;">${pm.CODE}</td>
                </tr>
                <tr style="background:#f4f4f4;">
                    <td style="width:50%;text-align: left;">资产类别</td>
                    <td id="pmDetailAssetType" style="width:50%;text-align: left;">${pm.CATEGORYNAME}</td>
                </tr>
                <tr>
                    <td style="width:50%;text-align: left;">贵金属状态</td>
                    <td id="pmDetailStatus" style="width:50%;text-align: left;">
                        <c:if test="${pm.STATUS =='0' }">可售</c:if>
                        <c:if test="${pm.STATUS =='1' }">不可售</c:if>
                    </td>
                </tr>
                <tr style="background:#f4f4f4;">
                    <td style="width:50%;text-align: left;">发行时间</td>
                    <td id="pmDetailReleaseDate" style="width:50%;text-align: left;">${pm.RELEASE_DATE}</td>
                </tr>
                <tr>
                    <td style="width:50%;text-align: left;">材质</td>
                    <td id="pmDetailMaterial" style="width:50%;text-align: left;">
                        <c:if test="${pm.MATERIAL =='0' }">黄金</c:if>
                        <c:if test="${pm.material =='1' }">白银</c:if>
                    </td>
                </tr>
               <%-- <tr style="background:#f4f4f4;">
                    <td style="width:50%;text-align: left;">克重</td>
                    <td id="pmDetailWeight" style="width:50%;text-align: left;">${pm.WEIGHT}${PM.UNIT} </td>
                </tr>

                <tr>
                    <td>风险类型</td>
                    <td id="pmDetailRiskLevel">${pm.RISK_LEVEL}</td>
                </tr>--%>

                <tr style="background:#f4f4f4;">
                    <td>跟踪标的</td>
                    <td id="pmDetailBidName">${pm.BIDNAME}</td>
                </tr>

                <tr>
                    <td>跟踪误差</td>
                    <td id="pmDetailTrackError">${pm.trackError}</td>
                </tr>
            </table>
        </div>

        <div title="贵金属业绩" data-options="onOpen:function(){pmachie();}" style="padding: 10px; width: 100%; padding:10px">
            <div id="pmDetailTimeSearch" style="margin-left: 25%;">
                <form name="form1">
                    选择时间
                    <input type="radio" id="pm_1month" name="radiobutton" value="1month"> <label for="pm_1month">1月</label>
                    <input type="radio" id="pm_3month" name="radiobutton" value="3month"> <label for="pm_3month">3月</label>
                    <input type="radio" id="pm_6month" name="radiobutton" value="6month"><label for="pm_6month">6月</label>
                    <input type="radio" id="pm_1year" name="radiobutton" value="1year"><label for="pm_1year">1年</label>
                    <input type="radio" id="pm_3year" name="radiobutton" value="3year"><label for="pm_3year">3年</label>
                    <input type="radio" id="pm_5year" name="radiobutton" value="5year"><label for="pm_5year">5年</label>
                    <%--<input type="radio" id="thisyear" name="radiobutton" value="thisyear"><label for="thisyear">近年来</label>--%>
                    <input type="radio" id="pm_maxtime" name="radiobutton" value="maxtime"  checked><label for="pm_maxtime">最大</label>
                    <input type="radio" id="pm_customized" name="radiobutton" value="customized"><label for="pm_customized">自定义查询</label>
                </form>
            </div>
            <div id="pm_customizediv" style="margin-left:-20%;visibility:hidden;">
                <table style="align:center;margin:20px auto">
                    <tr>
                        <td> 开始时间：</td>
                        <td> <input id="pmDetailStartTime" class="easyui-datebox" editable="false"></td>
                        <td> &nbsp;&nbsp;结束时间：</td>
                        <td> <input id="pmDetailEndTime" class="easyui-datebox" editable="false"></td>
                        <td> &nbsp;&nbsp; <a href="#" class="easyui-linkbutton"  onclick="pmDetailSearch();" >查询</a></td>
                    </tr>
                </table>
            </div>
            <div  id="pmachievement" style="width:1000px;height:500px;padding-top:30px;"></div>
            <br>
            <div  id="pmvalued" style="width:1000px;height:500px;padding-top:30px;"></div>
            <br>
           <%-- <div  id="fddmonemillion" style="width:1000px;height:500px;padding-top:30px;"></div>
            <br>
            <div  id="fddmonesereven" style="width:1000px;height:500px;padding-top:30px;"></div>
            <br>--%>
            <div  style="width:1000px;height:100px;padding-top:30px;">&nbsp;</div>
        </div>
        <div title="贵金属风险" data-options="href:''" style="padding:10px">
            <div style="margin:20px auto;">
                <input id="pmCode" name="pmCode" type="hidden" value="${pm.CODE}"/>
                <form id="pmRatioForm">
                    <table stylt="height:80px;line-height:30px;">
                        <tr>
                            <th style="width:140px;">&nbsp;</th>
                            <th style="width:80px;">开始时间</th>
                            <td><input id="pmStarTime" class="easyui-datebox" editable="false" name="pmStarTime" style="width:120px;"/></td>
                            <th style="width:80px;"> 结束时间</th>
                            <td><input id="pmEndTime" class="easyui-datebox" editable="false" name="pmEndTime" style="width:120px;"/></td>
                            <th style="width:40px;">类型</th>
                            <td><select id="pmCalTyp" class="easyui-combobox" data-options="editable:false" style="width:100px;" name="pmCalTyp">
                                <option value ="day">天</option>
                                <option value ="week">周</option>
                                <option value="month">月</option>
                                <option value="year">年</option>
                            </select>
                            </td>
                            <th style="width:100px;">风险计算算法</th>
                            <td><select id="pmItem" class="easyui-combobox" data-options="editable:false" style="width:100px;">
                                <option value ="day">标准差</option>
                                <option value ="week">半方差</option>
                                <option value="month">左偏动差</option>
                            </select>
                            </td>
                            <td style="width:80px;align:center;"><a class="easyui-linkbutton" href="javascript:void(0);" onclick="pmcaculate();">计算</a></td>
                            <td style="width:80px;"><a class="easyui-linkbutton" href="javascript:void(0);" onclick="pmclear();">清空</a></td>
                        </tr>
                    </table>
                </form>
                <br>
                <table style="margin:10px 20px 30px 70px;width:1200px;line-height:30px;border:1px solid #ffffff; border-collapse: collapse;">
                    <tr style="background:#f4f4f4;">
                        <th>涨幅</th>
                        <td id="pmIncresment"></td>
                        <th>最大回撤</th>
                        <td id="pmMaxRetreat"></td>
                    </tr>
                    <tr>
                        <th>预期收益率</th>
                        <td id="pmExpAnnualRadio"></td>
                        <th>预期风险率</th>
                        <td id="pmExpRiskRadio"></td>
                    </tr>
                    <tr style="background:#f4f4f4;">
                        <th>标准差</th>
                        <td id="pmStandardDeviation"></td>
                        <th>夏普比率</th>
                        <td id="pmSharpRadio"></td>
                    </tr>
                </table>
            </div>
            <div id="pmYieldRatioLine" style="width:1000px;height:500px;padding-top:30px;"></div>
            <br>
            <div id="fddMaxdrawnLine" style="width:1000px;height:500px;padding-top:30px;"></div>
            <div style="width:1000px;height:100px;padding-top:30px;">&nbsp;</div>
        </div>
    </div>
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
                $("#pmDetailStartTime").datebox("setValue", "");
                $("#pmDetailEndTime").datebox("setValue", "");
                document.getElementById("pm_customizediv").style.visibility = "visible";
                pmachie(null,null,null,1);
            }else{
                if(document.getElementById("pm_customizediv").style.visibility=='visible') {
                    document.getElementById("pm_customizediv").style.visibility = "hidden";
                }
                if(this.value=="1month"){
                    pmachie(1*30,null,null,0);
                }else if(this.value=="3month"){
                    pmachie(3*30,null,null,0);
                }else if(this.value=="6month"){
                    pmachie(6*30,null,null,0);
                }else if(this.value=="1year"){
                    pmachie(1*365,null,null,0);
                }else if(this.value=="3year"){
                    pmachie(3*365,null,null,0);
                }else if(this.value=="5year"){
                    pmachie(5*365,null,null,0);
                }else if(this.value=="thisyear"){
                    pmachie(getDaysOfThisYear(),null,null,0);
                }else if(this.value=="maxtime"){
                    pmachie(null,null,null,0);
                }
            }
        });
    });

    //自定义日期查询
    function pmDetailSearch(){
        pmachie(null,$("#pmDetailStartTime").datebox("getValue"),$("#pmDetailEndTime").datebox("getValue"),1);
    }




    //加载业绩信息
    function pmachie(days,startTime,endTime,flag){
        if(days==undefined){
            days=null;
        }
        if(flag==undefined || flag==null){
            flag=0;
        }
        var productCode="${pm.CODE}";
        var productName="${pm.NAME}";
        var productTyp=$('#fund_del_invtyp').val();
        var bName="${pm.BIDNAME}";

        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/pm/toPmAchie",
            data:{code:productCode,days:days,startTime:startTime,endTime:endTime,flag:flag},
            timeout:120000,
            cache:false,
            beforeSend: function () {
                load();
            },
            complete: function () {
                disLoad();
            },
            success:function(data){
                fddachieLine(productName,bName,data.fdyieldLst,data.zsyieldLst,data.dtLst);
                if(document.getElementById("pm_customizediv").style.visibility=='visible'){
                    if(startTime=="" || startTime==null){
                        $("#pmDetailStartTime").datebox("setValue", data.dtLst[0]);
                    }
                    if(endTime=="" || endTime==null){
                        $("#pmDetailEndTime").datebox("setValue", data.dtLst[data.dtLst.length-1]);
                    }

                }
            },
            error:function(){
                alert("请求失败");
            }
        });
            //单位净值
            $.ajax({
                type:"POST",
                url:"${pageContext.request.contextPath}/pm/pmValue",
                data:{code:productCode,days:days,startTime:startTime,endTime:endTime,flag:flag},
                timeout:120000,
                cache:false,
                beforeSend: function () {
                    load();
                },
                complete: function () {
                    disLoad();
                },
                success:function(data){
                    pmNavadjLine(productName,data);
                },
                error:function(){
                    alert("请求失败");
                }
            });

    }


    //基金、指数丈夫曲线
    function fddachieLine(productName,bName,fdyieldLst,zsyieldLst,dtLst){
        var myChart = echarts.init(document.getElementById('pmachievement'));
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


    //计算基金风险
    function pmcaculate() {

        var productCode="${pm.CODE}";
        var productName="${pm.NAME}";
        var bName="${pm.BIDNAME}";
        var startTime=$("#pmStarTime").datebox("getValue");
        var endTime=$("#pmEndTime").datebox("getValue");
        var url="";
        if(productCode==''||productCode==null){
            alert("产品代码不能为空!");
            return ;
        }
        var selectedType=$("#pmCalTyp").combobox("getValue");

        //获取涨幅和最大回撤
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/pm/getCaculateValue",
            data:{productCode:productCode,startTime:startTime,endTime:endTime,cycle:selectedType},
            timeout:120000,
            cache:false,
            beforeSend: function () {
                load();
            },
            complete: function () {
                disLoad();
            },
            success:function(data){
                $('#pmIncresment').html(Number(data.growth).toFixed(5));
                $('#pmMaxRetreat').html(Number(data.Maxdrawdown).toFixed(5));
                $('#pmExpAnnualRadio').html(Number(data.expected_annualized_return).toFixed(4));
                $('#pmExpRiskRadio').html(Number(data.expected_risk_ratio).toFixed(4));
            },
            error:function(){
                alert("请求失败");
            }
        });


        //计算sharp
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/pm/pmSharp",
            data:{code:productCode,startTime:startTime,endTime:endTime},
            timeout:120000,
            cache:false,
            success:function(data){
                $('#pmSharpRadio').html(Number(data.sharp).toFixed(4));
                $('#pmStandardDeviation').html(Number(data.standard).toFixed(4));
            },
            error:function(){
                alert("请求失败");
            }
        });

        //请求后台数据 收益率曲线
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/pm/yieldRatioLine",
            data:{productCode:productCode,startTime:startTime,endTime:endTime},
            timeout:120000,
            cache:false,
            success:function(data){
                if(startTime==''||startTime==null){
                    $('#pmStarTime').val(data[0].navDate.substring(0,10));
                }
                if(endTime==''||endTime==null){
                    $('#pmEndTime').val(data[data.length-1].navDate.substring(0,10));
                }
                pmYieldRatioLine(productName,data);
            },
            error:function(){
                alert("请求失败");
            }
        });

        //最大回撤曲线
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/pm/toPmDrawdowns",
            data:{productCode:productCode,startDate:startTime,endDate:endTime},
            cache:false,
            beforeSend: function () {
                load();
            },
            complete: function () {
                disLoad();
            },
            success:function(data){
                pmMaxDraws(productName,bName,data.pmDrawdowns,data.benchmarkDrawdowns,data.dates);
            },
            error:function(){
                alert("请求失败");
            }
        });





    }
    //点击清空按钮出发事件
    function pmclear() {
        $("#pmRatioForm").find("input").val("");//找到form表单下的所有input标签并清空
    }

    //最大回撤曲线
    function pmMaxDraws(productName,bName,pmDrawdowns,benchmarkDrawdowns,dates){
        var list=[];
        for(var i=0;i<dates.length;i++){
            list.push(dates[i].substring(0,10));
        }
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
                data: list
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
                    data: pmDrawdowns
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
    function pmYieldRatioLine(productName,data){
        var xList=[];
        var yList=[];
        for(var i=0;i<data.length;i++){
            xList.push(data[i].navDate.substring(0,10));
            yList.push(data[i].yieldRatio);
        }
        var myChart = echarts.init(document.getElementById('pmYieldRatioLine'));
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

    //单位净值曲线
    function pmNavadjLine(productName,data){
        var xList=[];
        var yList=[];

        for(var i=0;i<data.length;i++){
            xList.push(data[i].navlatestdate.substring(0,10));
            yList.push(data[i].navadj);
        }
        var myChart = echarts.init(document.getElementById('pmvalued'));
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
</script>
