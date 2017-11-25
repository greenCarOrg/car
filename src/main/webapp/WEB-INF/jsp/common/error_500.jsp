<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>500错误</title>
    <script type="application/javascript">

    </script>
</head>
<body>
<div class="error_page">
    <div class="error_page_header">
        <div class="uk-width-8-10 uk-container-center">
            500!
        </div>
    </div>
    <div class="error_page_content">
        <div class="uk-width-8-10 uk-container-center">
            <p class="heading_b">Oops! Something went wrong...</p>
            <p class="uk-text-large">
                There was an error. Please try again later.
            </p>
            <a href="#" onclick="history.go(-1);return false;">返回上一页</a>
            <a href="../console/dashboard.do">返回主页</a>
        </div>
    </div>
</div>


</body>
</html>
