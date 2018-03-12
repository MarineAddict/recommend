<%--
  Created by IntelliJ IDEA.
  User: yongquan.xiong
  Date: 2018/2/2 0002
  Time: 下午 3:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="copyright" content="All Rights Reserved, Copyright (C) 2013, Wuyeguo, Ltd." />
    <title>回归测试</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/1.3.4/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/wu.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/icon.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/1.3.4/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
    <div id="itemList" style="height:30px;width:auto;">
        <div>
            <span id="basicData" style="cursor:pointer" onclick="basicData()">
                组合基本数据
            </span >
            <span id="metricsData" style="cursor:pointer" onclick="metricsData()">
                组合分析指标
            </span >
            <span id="rerurnData" style="cursor:pointer" onclick="rerurnData()">
                组合收益分析
            </span>
            <span id="drawdownData" style="cursor:pointer" onclick="drawdownData()">
                组合回撤分析
            </span>
        </div>
    </div>

    <div id="dataList" style="height:600px;width:auto;">

    </div>


    <script type="text/javascript">

        function basicData() {
            console.log("basicData");
          /*  alert("进入 basicData");*/
            $.ajax({
                type:"POST",
                url:"${pageContext.request.contextPath}/backtest/getBacktestBasicData",
                dataType:"text",
                data: {},
                success: function(msg){
                    //列表数据页面渲染
                    $('#dataList').html(msg);
                },
                error:function(){
                    alert("请求失败");
                }
            });
        }

    </script>

</body>
</html>


