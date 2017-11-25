<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>酷客后台管理</title>
    <meta http-equiv="refresh" content="3;url=../${param.reurl}">
    <script type="application/javascript">

    </script>
</head>
<body>
<div class="error_page_header">
    <div class="uk-width-8-10 uk-container-center">
        操作成功!
    </div>
</div>
<div class="error_page_content">
    <div class="uk-width-8-10 uk-container-center">
        <p class="heading_b">操作成功</p>

        <p class="uk-text-large">
            操作成功，请等待页面跳转...
        </p>
        <a href="../${param.reurl}">返回上一页</a>
        <a href="../console/dashboard.do">返回主页</a>
    </div>
</div>
</body>
</html>
