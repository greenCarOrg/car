<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html class="lte-ie9" lang="en">
<html lang="en">
<head>
	<title>Document</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="msapplication-tap-highlight" content="no"/>

	<link rel="stylesheet" href="${ctx}/css/easyui/easyui.css" >
	<script src="${ctx}/kendo/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/easyui/jquery.easyui.min.js"></script>
</head>
<body style="width: 580px;">
<div id="chargeRecordBillHistoryList" ></div>
<script>
    var querParams = {"chargeId":${chargeId}};
    $('#chargeRecordBillHistoryList').datagrid({
        method:'get',
        queryParams:querParams,
        url:'../cs/findChargeRecordBillList.do',
        fitColumns:true,
        rownumbers:true,
        collapsible:false,
        minimizable:false,
        maximizable:false,
        border:false,
        columns:[[
            {field:'createTime',title:'时间',align:'center',width:120},
            {field:'createBy',title:'操作人',align:'center',width:80}
        ]]
    });
</script>
</body>
</html>  