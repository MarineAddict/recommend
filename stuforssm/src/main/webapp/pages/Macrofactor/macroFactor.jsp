<%--
  Created by IntelliJ IDEA.
  User: lxs
  Date: 2018/3/7
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Title</title>
    <script src="<c:url value="/js/echars/echarts.js"/>"></script>
</head>
<div data-options="region:'north',title:'查询'"
     style="height: 40px; background: #F4F4F4;">
    <form>
        <table>
            <tr>
                <th>宏观因子</th>
                <td><select class="easyui-combobox" style="width:200px;" id="macroFactorcombox" editable="false">
                    <option value="" selected>请选择</option>
                    <option value="MACRO_CPI">居民消费价格指数(CPI)</option>
                    <option value="MACRO_GDP">国内生产总值(GDP)</option>
                    <option value="MACRO_MONEY_SUPPLY">货币供应量</option>
                    <option value="MACRO_PMI">采购经理人指数(PMI)</option>
                    <option value="MACRO_PPI">工业品出厂价格指数(PPI)</option>
                </select>
                </td>
                <th>开始时间</th>
                <td><input class="easyui-datebox" editable="false" id="macroFactorStartTime" /></td>
                <th>结束时间</th>
                <td><input class="easyui-datebox" editable="false" id="macroFactorEndTime"/></td>
                <!--由于datebox框架上面的数据必须是时间格式的，所以我们用editable="false"来禁止用户手动输入，以免报错-->
                <td><a class="easyui-linkbutton" href="javascript:void(0);"
                       onclick="searchMacroFactor();">查找</a></td>
                <td><a class="easyui-linkbutton" href="javascript:void(0);"
                       onclick="clearMacroFactor();">清空</a></td>
            </tr>
        </table>
    </form>
</div>
<div id="MACRO_CPI_Line" style="width:1400px;height: 300px;display: none;"></div>
<div id="MARCO_GDP_Line" style="width:1400px;height: 300px;display: none;"></div>
<div id="MARCO_MONEY_SUPPLY_Line" style="width:1400px;height: 300px;display: none;"></div>
<div id="MARCO_PMI_Line" style="width:1400px;height: 300px;display: none;"></div>
<div id="MARCO_PPI_Line" style="width:1400px;height: 300px;display: none;"></div>
<table id="MACRO_CPI_Table" style=""></table>
<table id="MARCO_GDP_Table" style="display: none;"></table>
<table id="MARCO_MONEY_SUPPLY_Table" style="display: none;"></table>
<table id="MARCO_PMI_Table" style="display: none;"></table>
<table id="MARCO_PPI_Table" style="display: none;"></table>
<script type="text/javascript">

    $(document).ready(function () {

        $("#macroFactorcombox").combobox({

            onChange: function (n,o) {
                $('#macroFactorStartTime').datebox('setValue', '');
                $('#macroFactorEndTime').datebox('setValue', '');
            }

        });

    });



    //搜索按钮
    function searchMacroFactor(){
        var macroFactorValue=$('#macroFactorcombox').combobox('getValue');
        var startTime=$('#macroFactorStartTime').datebox('getValue');
        var endTime=$('#macroFactorEndTime').datebox('getValue');
        if(macroFactorValue==null||macroFactorValue==""){
            alert("宏观因子不能为空")
        }else{
            $.ajax({
                type:"POST",
                url:"${pageContext.request.contextPath}/macrofactor/entity/"+macroFactorValue+"/getAllInfo",
                data:{startTime:startTime,endTime:endTime},
                timeout:60000,
                cache:false,
                beforeSend: function () {
                    load();
                },
                complete: function () {
                    disLoad();
                },
                success:function(data){
                    console.log(data);
                    if(startTime==""||startTime==null){
                        if(macroFactorValue=="MACRO_CPI"){
                            $('#macroFactorStartTime').datebox('setValue', data[0].cpi_date.substring(0,10));
                        }else if(macroFactorValue=="MACRO_GDP"){
                            $('#macroFactorStartTime').datebox('setValue', data[0].gdp_date.substring(0,10));
                        }else if(macroFactorValue=="MACRO_MONEY_SUPPLY"){
                            $('#macroFactorStartTime').datebox('setValue', data[0].ms_date.substring(0,10));
                        }else if(macroFactorValue=="MACRO_PMI"){
                            $('#macroFactorStartTime').datebox('setValue', data[0].pmi_date.substring(0,10));
                        }else if(macroFactorValue=="MACRO_PPI"){
                            $('#macroFactorStartTime').datebox('setValue', data[0].ppi_date.substring(0,10));
                        }

                    }
                    if(endTime==""||endTime==null){
                        if(macroFactorValue=="MACRO_CPI"){
                            $('#macroFactorEndTime').datebox('setValue', data[data.length-1].cpi_date.substring(0,10));
                        }else if(macroFactorValue=="MACRO_GDP"){
                            $('#macroFactorEndTime').datebox('setValue', data[data.length-1].gdp_date.substring(0,10));
                        }else if(macroFactorValue=="MACRO_MONEY_SUPPLY"){
                            $('#macroFactorEndTime').datebox('setValue', data[data.length-1].ms_date.substring(0,10));
                        }else if(macroFactorValue=="MACRO_PMI"){
                            $('#macroFactorEndTime').datebox('setValue', data[data.length-1].pmi_date.substring(0,10));
                        }else if(macroFactorValue=="MACRO_PPI"){
                            $('#macroFactorEndTime').datebox('setValue', data[data.length-1].ppi_date.substring(0,10));
                        }

                    }

                    if(macroFactorValue=="MACRO_CPI"){
                        MACRO_CPI_Drid(data);
                        document.getElementById("MACRO_CPI_Line").style.display="block";
                        document.getElementById("MARCO_GDP_Line").style.display="none";
                        document.getElementById("MARCO_MONEY_SUPPLY_Line").style.display="none";
                        document.getElementById("MARCO_PMI_Line").style.display="none";
                        document.getElementById("MARCO_PPI_Line").style.display="none";
                    }else if (macroFactorValue=="MACRO_GDP"){
                        MARCO_GDP_Drid(data);
                        document.getElementById("MACRO_CPI_Line").style.display="none";
                        document.getElementById("MARCO_GDP_Line").style.display="block";
                        document.getElementById("MARCO_MONEY_SUPPLY_Line").style.display="none";
                        document.getElementById("MARCO_PMI_Line").style.display="none";
                        document.getElementById("MARCO_PPI_Line").style.display="none";
                    }else if (macroFactorValue=="MACRO_MONEY_SUPPLY"){
                        MARCO_MONEY_SUPPLY_Drid(data);
                        document.getElementById("MACRO_CPI_Line").style.display="none";
                        document.getElementById("MARCO_GDP_Line").style.display="none";
                        document.getElementById("MARCO_MONEY_SUPPLY_Line").style.display="block";
                        document.getElementById("MARCO_PMI_Line").style.display="none";
                        document.getElementById("MARCO_PPI_Line").style.display="none";
                    }else if (macroFactorValue=="MACRO_PMI"){
                        MARCO_PMI_Drid(data);
                        document.getElementById("MACRO_CPI_Line").style.display="none";
                        document.getElementById("MARCO_GDP_Line").style.display="none";
                        document.getElementById("MARCO_MONEY_SUPPLY_Line").style.display="none";
                        document.getElementById("MARCO_PMI_Line").style.display="block";
                        document.getElementById("MARCO_PPI_Line").style.display="none";
                    }else if (macroFactorValue=="MACRO_PPI"){
                        MARCO_PPI_Drid(data);
                        document.getElementById("MACRO_CPI_Line").style.display="none";
                        document.getElementById("MARCO_GDP_Line").style.display="none";
                        document.getElementById("MARCO_MONEY_SUPPLY_Line").style.display="none";
                        document.getElementById("MARCO_PMI_Line").style.display="none";
                        document.getElementById("MARCO_PPI_Line").style.display="block";
                    }
                },
                error:function(){
                    alert("请求失败");
                }
            });
        }

    }
    //清空输入框
    function clearMacroFactor() {
        $('#macroFactorcombox').combobox('setValue', "");
        $('#macroFactorStartTime').datebox('setValue', "");
        $('#macroFactorEndTime').datebox('setValue', "");
    }

    function MACRO_CPI_Drid(data) {
            var xValue=[];
            var y1Value=[];
            var y2Value=[];
            var y3Value=[];
            for(var i=0;i<data.length;i++){
                xValue.push(data[i].cpi_date.substring(0,10));
                y1Value.push(data[i].cpi_pural_month);
                y2Value.push(data[i].cpi_country_month);
                y3Value.push(data[i].cpi_city_month);
            }
            console.log(y3Value);
            var myChart = echarts.init(document.getElementById('MACRO_CPI_Line'));
            option = {
                tooltip : {
                    trigger: 'axis'
                },
                title: {
                    left: 'center',
                    text: '中国 居民消费价格指数（CPI）'
                },
                legend: {
                    data:['农村','全国','城市'],
                    x: 'left'
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
                dataZoom: [{
                    type: 'inside',
                    start: 0,
                    end: 100
                }, {
                    start: 0,
                    end: 10,
                    handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                    handleSize: '80%',
                    handleStyle: {
                        color: '#fff',
                        shadowBlur: 3,
                        shadowColor: 'rgba(0, 0, 0, 0.6)',
                        shadowOffsetX: 2,
                        shadowOffsetY: 2
                    }
                }],
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : false,
                        data : xValue,
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        min:Math.floor(minArrayValue(minValue(y1Value),minValue(y2Value),minValue(y3Value)))
                    }
                ],
                series : [
                    {
                        name:'农村',
                        type:'line',
                        data:y1Value,
                    }, {
                        name:'全国',
                        type:'line',
                        data:y2Value
                    }, {
                        name:'城市',
                        type:'line',
                        data:y3Value
                    }
                ],
                calculable:false
            };
            myChart.setOption(option);
    }

    function MARCO_GDP_Drid(data){
        var xValue=[];
        var y1Value=[];
        var y2Value=[];
        var y3Value=[];
        var y4Value=[];
        for(var i=0;i<data.length;i++){
            xValue.push(data[i].gdp_date.substring(0,10));
            y1Value.push(data[i].gdp_first_yoy);
            y2Value.push(data[i].gdp_second_yoy);
            y3Value.push(data[i].gdp_third_yoy);
            y4Value.push(data[i].gdp_total_yoy);
        }
        var myChart = echarts.init(document.getElementById('MARCO_GDP_Line'));
        option = {
            tooltip : {
                trigger: 'axis'
            },
            title: {
                left: 'center',
                text: '中国 国内生产总值（GDP）'
            },
            legend: {
                data:['第一产业（%）','第二产业（%）','第三产业（%）','国内生产总值（%）'],
                x: 'left'
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
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 100
            }, {
                start: 0,
                end: 10,
                handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                handleSize: '80%',
                handleStyle: {
                    color: '#fff',
                    shadowBlur: 3,
                    shadowColor: 'rgba(0, 0, 0, 0.6)',
                    shadowOffsetX: 2,
                    shadowOffsetY: 2
                }
            }],
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : xValue,
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    min:Math.floor(minArrayValue(minValue(y1Value),minValue(y2Value),minValue(y3Value),minValue(y4Value)))
                }
            ],
            series : [
                {
                    name:'第一产业（%）',
                    type:'line',
                    data:y1Value,
                }, {
                    name:'第二产业（%）',
                    type:'line',
                    data:y2Value
                }, {
                    name:'第三产业（%）',
                    type:'line',
                    data:y3Value
                }, {
                    name:'国内生产总值（%）',
                    type:'line',
                    data:y4Value
                }
            ],
            calculable:false
        };
        myChart.setOption(option);
    }

    function MARCO_MONEY_SUPPLY_Drid(data) {
        var xValue=[];
        var y1Value=[];
        var y2Value=[];
        var y3Value=[];
        for(var i=0;i<data.length;i++){
            xValue.push(data[i].ms_date.substring(0,10));
            y1Value.push(data[i].m0_mom);
            y2Value.push(data[i].m1_mom);
            y3Value.push(data[i].m2_mom);
        }
        var myChart = echarts.init(document.getElementById('MARCO_MONEY_SUPPLY_Line'));
        option = {
            tooltip : {
                trigger: 'axis'
            },
            title: {
                left: 'center',
                text: '中国 货币供应量'
            },
            legend: {
                data:['M0增长（%）','M1增长（%）','M2增长（%）'],
                x: 'left'
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
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 100
            }, {
                start: 0,
                end: 10,
                handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                handleSize: '80%',
                handleStyle: {
                    color: '#fff',
                    shadowBlur: 3,
                    shadowColor: 'rgba(0, 0, 0, 0.6)',
                    shadowOffsetX: 2,
                    shadowOffsetY: 2
                }
            }],
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : xValue,
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    min:Math.floor(minArrayValue(minValue(y1Value),minValue(y2Value),minValue(y3Value)))
                }
            ],
            series : [
                {
                    name:'M0增长（%）',
                    type:'line',
                    data:y1Value,
                }, {
                    name:'M1增长（%）',
                    type:'line',
                    data:y2Value
                }, {
                    name:'M2增长（%）',
                    type:'line',
                    data:y3Value
                }
            ],
            calculable:false
        };
        myChart.setOption(option);
    }

    function MARCO_PMI_Drid(data) {
        var xValue=[];
        var y1Value=[];
        var y2Value=[];
        for(var i=0;i<data.length;i++){
            xValue.push(data[i].pmi_date.substring(0,10));
            y1Value.push(data[i].pmi_manu);
            y2Value.push(data[i].pmi_non_manu);
        }
        var myChart = echarts.init(document.getElementById('MARCO_PMI_Line'));
        option = {
            tooltip : {
                trigger: 'axis'
            },
            title: {
                left: 'center',
                text: '中国 采购经理人指数（PMI）'
            },
            legend: {
                data:['制造业','非制造业'],
                x: 'left'
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
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 100
            }, {
                start: 0,
                end: 10,
                handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                handleSize: '80%',
                handleStyle: {
                    color: '#fff',
                    shadowBlur: 3,
                    shadowColor: 'rgba(0, 0, 0, 0.6)',
                    shadowOffsetX: 2,
                    shadowOffsetY: 2
                }
            }],
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : xValue,
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    min:Math.floor(minArrayValue(minValue(y1Value),minValue(y2Value)))
                }
            ],
            series : [
                {
                    name:'制造业',
                    type:'line',
                    data:y1Value,
                }, {
                    name:'非制造业',
                    type:'line',
                    data:y2Value
                }
            ],
            calculable:false
        };
        myChart.setOption(option);
    }

    function MARCO_PPI_Drid(data) {
        var xValue=[];
        var y1Value=[];
        var y2Value=[];
        for(var i=0;i<data.length;i++){
            xValue.push(data[i].ppi_date.substring(0,10));
            y1Value.push(data[i].ppi_month);
            y2Value.push(data[i].ppi_total);
        }
        var myChart = echarts.init(document.getElementById('MARCO_PPI_Line'));
        option = {
            tooltip : {
                trigger: 'axis'
            },
            title: {
                left: 'center',
                text: '中国 工业品出厂价格指数（PPI）'
            },
            legend: {
                data:['当月','累计'],
                x: 'left'
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
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 100
            }, {
                start: 0,
                end: 10,
                handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                handleSize: '80%',
                handleStyle: {
                    color: '#fff',
                    shadowBlur: 3,
                    shadowColor: 'rgba(0, 0, 0, 0.6)',
                    shadowOffsetX: 2,
                    shadowOffsetY: 2
                }
            }],
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : xValue,
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    min:Math.floor(minArrayValue(minValue(y1Value),minValue(y2Value)))
                }
            ],
            series : [
                {
                    name:'当月',
                    type:'line',
                    data:y1Value,
                }, {
                    name:'累计',
                    type:'line',
                    data:y2Value
                }
            ],
            calculable:false
        };
        myChart.setOption(option);
    }

</script>
