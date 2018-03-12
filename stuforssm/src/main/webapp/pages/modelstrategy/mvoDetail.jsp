<%--
  Created by IntelliJ IDEA.
  User: lxs
  Date: 2018/3/6
  Time: 13:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Title</title>
    <script src="<c:url value="/js/echars/echarts.js"/>"></script>
</head>
<div id="mvoProductDetail" style="width: 550px;height:300px;"></div>
<script type="text/javascript">

    var initGroupCode=<%=request.getParameter("groupCode")%>;
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/backtest/getBacktestBasicData",
            data:{groupCode:initGroupCode,startTime:null,endTime:null,rebalance:null,initmoney:null,groupType:"mvo"},
            timeout:60000,
            cache:false,
            beforeSend: function () {
                load();
            },
            complete: function () {
                disLoad();
            },
            success:function(data){
                //提示信息
                PiechartDetail(data);
            },
            error:function(){
                alert("请求失败");
            }
        });

    function PiechartDetail(result){
        var array = [];
        var arrayTitle=[];
        for(var i=0;i<result.length;i++){
            var map={};
            map.name=result[i].productName;
            map.value=result[i].weights;
            array[i]=map;
            arrayTitle.push(result[i].productName);
        }
        var myChart = echarts.init(document.getElementById('mvoProductDetail'));
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
            legend: {
                orient : 'vertical',
                x : 'left',
                data:arrayTitle
            },
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
