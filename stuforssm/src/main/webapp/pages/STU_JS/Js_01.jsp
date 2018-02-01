<%--
  Created by IntelliJ IDEA.
  User: WhoAmI
  Date: 2017/12/11
  Time: 11:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JS加载顺序测试文件</title>

    <script defer>
        alert("1");
    </script>

    <script>
        /*页面加载完后执行*/
        window.onload=function () {
            alert("onlode");
        };


        /*document.addEventListener(event, function, useCapture)
         *event:         必需。描述事件名称的字符串。
         *function:      必需。描述了事件触发后执行的函数。
         * useCapture:   可选。布尔值，指定事件是否 在捕获或冒泡阶段执行。(true - 事件句柄在捕获阶段执行 false- 默认。事件句柄在冒泡阶段执行)
         */

        /*为addEventListener绑定监听事件*/
        /*文档基本结构加载完成后执行*/
        document.addEventListener("DOMContentLoaded",function(){
            alert("DOMContentLoaded");
        },false);
    </script>

    <script type="text/javascript" src="js/test.js" defer></script>

    <script>
        alert("2");
    </script>

</head>
<body>

</body>
</html>
