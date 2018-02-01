<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="utf-8">
    <title>ECHARS</title>
    <script src="<c:url value="/js/jquery/jquery-3.2.1.js"/>"></script>
    <script src="<c:url value="/js/echars/echarts.js"/>"></script>
</head>
<body>
<div id="main" style="width: 1200px;height:800px;"></div>
<script>
    // 绘制图表。
    /*echarts.init(document.getElementById('main')).setOption({
    series: {
            type: 'pie',
            data: [
                {name: 'A', value: 1212},
                {name: 'B', value: 2323},
                {name: 'C', value: 1919}
            ]
        }
    });*/

    alert("1.准备初始化echarts实例");
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var symbolSize = 20;
    var data = [
        [0.12879539,0.042739145],
        [0.1297947,0.04836813],
        [0.13274753,0.053997114],
        [0.13752809,0.0596261],
        [0.14395443,0.06525508],
        [0.15181766,0.070884064],
        [0.16091435,0.07651305],
        [0.17148136,0.08214203],
        [0.18443097,0.08777101],
        [0.2147091,0.0934]];

    option = {
        title: {
            text: 'Try Dragging these Points'
        },
        tooltip: {
            triggerOn: 'none',
            formatter: function (params) {
                return 'X: ' + params.data[0].toFixed(2) + '<br>Y: ' + params.data[1].toFixed(2);
            }
        },
        grid: {
        },
        xAxis: {
            min: 0.128,
            max: 0.215,
            type: 'value',
            axisLine: {onZero: false}
        },
        yAxis: {
            min: 0.04,
            max: 0.1,
            type: 'value',
            axisLine: {onZero: false}
        },
        dataZoom: [

        ],
        series: [
            {
                id: 'a',
                type: 'line',
                smooth: true,
                symbolSize: symbolSize,
                data: data
            }
        ]
    };

    function updatePosition() {
        myChart.setOption({
            graphic: echarts.util.map(data, function (item, dataIndex) {
                return {
                    position: myChart.convertToPixel('grid', item)
                };
            })
        });
    }



    alert("2.准备指定配置项");
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    // 相当于 document.ready，{代码}
    $(function(){
        alert("3.页面加载完毕");
    })


</script>
</body>
</html>
