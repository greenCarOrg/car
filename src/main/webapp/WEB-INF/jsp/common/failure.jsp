<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>酷客后台管理</title>
    <script type="application/javascript">

    </script>
</head>
<body>
<div class="breadcrumbwidget">
    <ul class="skins">
        <li><a href="default" class="skin-color default"></a></li>
        <li><a href="orange" class="skin-color orange"></a></li>
        <li><a href="dark" class="skin-color dark"></a></li>
        <li>&nbsp;</li>
        <li class="fixed"><a href="" class="skin-layout fixed"></a></li>
        <li class="wide"><a href="" class="skin-layout wide"></a></li>
    </ul>
    <!--skins-->
    <ul class="breadcrumb">
        <li><a href="../console/dashboard.do">Home</a> <span class="divider">/</span></li>
        <li class="active">Dashboard</li>
    </ul>
</div>
<!--breadcrumbwidget-->
<div class="pagetitle">
    <h1>操作失败</h1> <span>This is a sample description for dashboard page...</span>
    <a href="toAddRole.do" class="btn btn-primary" style="float:right;margin-top:3px">新增角色</a>
</div>
<!--pagetitle-->
<div class="maincontent">
    <div class="contentinner wrapper404">
        <p>The page you are looking for is not found. This could be for several reasons...</p>
        <ul>
            <li><span class="icon-check"></span> It never existed.</li>
            <li><span class="icon-check"></span> It got deleted for some reason.</li>
            <li><span class="icon-check"></span> You were looking for something that is not here.</li>
            <li><span class="icon-check"></span> You like this page.</li>
        </ul>
        <br/>
        <button class="btn btn-primary" onClick="history.back()">Go Back to Previous Page</button>
        &nbsp;
        <button onClick="location.href='dashboard.html'" class="btn">Go Back to Dashboard</button>
    </div>
</div>
</body>
</html>
