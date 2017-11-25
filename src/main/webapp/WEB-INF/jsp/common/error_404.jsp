<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>404错误</title>
</head>
<body>
<div class="error_page_header">
    <div class="uk-width-8-10 uk-container-center">
        404!
    </div>
</div>
<div class="error_page_content">
    <div class="uk-width-8-10 uk-container-center">
        <p class="heading_b">页面找不到</p>

        <p class="uk-text-large">
            请求的网页路径 <span class="uk-text-muted">/some_url</span> 在服务器上找不到.
        </p>
        <a href="#" onclick="history.go(-1);return false;">返回上一页</a>
        <a href="../console/dashboard.do">返回主页</a>
    </div>
</div>

</body>
</html>
