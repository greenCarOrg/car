<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="viewport" content="width=device-width; initial-scale=1.4; minimum-scale=1.0; maximum-scale=2.0;"/>
    <title></title>
</head>
<body style="width:480px;height:420px;">
<div>
    <div style="padding: 50px  30px 50px 50px">
        <img src="${image}" style="width: 150px; height: 150px;" alt=""/>
        <br/>
        <label style="alignment: left">学生姓名:${studentName}</label>
        <br/>
        <label style="alignment: left">报读校区:${schoolName}</label>
        <br/>
        <label style="alignment: left">家长电话:${concat}</label>
        <br/>
        <label style="alignment: left">报读信息:${className}</label>
        <br/>
        <label style="alignment: left">考勤时间:${time}</label>
    </div>
</div>
</body>
</html>
