<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html class="lte-ie9" lang="en">
<html lang="en">
<head>
	<title>Document</title>

	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/report/chargeRecordBill.css">
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="msapplication-tap-highlight" content="no"/>

	<link rel="stylesheet" href="${ctx}/kendo/styles/kendo.common.min.css"/>
	<link rel="stylesheet" href="${ctx}/kendo/styles/kendo.silver.min.css"/>
	<link rel="stylesheet" href="${ctx}/bower_components/uikit/css/uikit.css"/>
	<link rel="stylesheet" href="${ctx}/bower_components/uikit/css/uikit.min.css"/>
	<link rel="stylesheet" href="${ctx}/assets/css/main.min.css"/>

	<script src="${ctx}/kendo/js/jquery.min.js"></script>
	<script src="${ctx}/kendo/js/kendo.web.min.js"></script>
	<script src="${ctx}/bower_components/uikit/js/uikit.min.js"></script>
	<script src="${ctx}/bower_components/uikit/js/components/notify.min.js"></script>
	<script type="text/javascript" src="${ctx}/kendo/js/cultures/kendo.culture.zh-CN.min.js"></script>
</head>
<body style="width: 600px;">
<div class="wrap">
	<div class="left-wrap">
		<div class="head">
			<div class="head-left"><img src="${ctx}/images/cs/code.png"></div>
			<div class="head-center">
				<div style="height: 70px;">
					<img src="${ctx}/images/cs/logo.jpg">
					<h1>${obj.corpName}</h1>
				</div>

				<h2>班级报读单</h2>
			</div>
			<div class="head-right">NO：${obj.billNo}</div>
		</div>
		<div class="content">
			<div class="desc">
				<p class="desc-left">校区：${obj.schoolName}</p>
				<p class="desc-right">${obj.createTime}</p>
			</div>
			<table cellpadding="0" cellspacing="0"  border="1">
				<tr>
					<th>学生姓名</th>
					<td>${obj.studentName}</td>
					<th>学号</th>
					<td>${obj.studentCode}</td>
					<th>家长电话</th>
					<td>${obj.guardContact1}</td>
				</tr>
				<tr>
					<th>学校</th>
					<td colspan="2">${obj.attendSchool}</td>
					<th>年级</th>
					<td colspan="2">${obj.grade}</td>
				</tr>
				<tr>
					<th>支付方式</th>
					<td colspan="2">${obj.payType}</td>
					<th>充值日期</th>
					<td colspan="2">${obj.chargeTime}</td>
				</tr>
				<tr>
					<th>充值金额(￥)</th>
					<td colspan="2">${obj.money}</td>
					<th>优惠说明</th>
					<td colspan="2">${obj.discountNote}</td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0"  border="1">
				<tr>
					<th>教材费(￥)</th>
					<td></td>
					<th>生活费(￥)</th>
					<td></td>
					<th>教具费(￥)</th>
					<td></td>
					<th>其他(￥)</th>
					<td></td>
				</tr>
				<tr height="100">
					<th>备注：</th>
					<td colspan="7">${obj.remark}</td>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0"  border="1">
				<tr>
					<th>班级类型</th>
					<th>小组课</th>
					<th>一对一</th>
					<th>${obj.aclassType}</th>
					<th>${obj.bclassType}</th>
				</tr>
				<tr>
					<th>班级名称</th>
					<th>${obj.xiaoZuKeName}</th>
					<th>${obj.yiDuiYiName}</th>
					<th>${obj.aclassName}</th>
					<th>${obj.bclassName}</th>
				</tr>
				<tr>
					<th>课时数</th>
					<th>${obj.xiaoZuKeNum}</th>
					<th>${obj.yiDuiYiNum}</th>
					<th>${obj.aclassNum}</th>
					<th>${obj.bclassNum}</th>
				</tr>
				<tr>
					<th>课时单价(￥)</th>
					<th>${obj.xiaoZuKePrice}</th>
					<th>${obj.yiDuiYiPrice}</th>
					<th>${obj.aclassPrice}</th>
					<th>${obj.bclassPrice}</th>
				</tr>
				<tr>
					<th>小计(￥)</th>
					<th>${obj.xiaoZuKeSubtotal}</th>
					<th>${obj.yiDuiYiSubtotal}</th>
					<th>${obj.aclassSubtotal}</th>
					<th>${obj.bclassSubtotal}</th>
				</tr>
				<tr>
					<th>合计(￥)</th>
					<th colspan="4">${obj.total}</th>
				</tr>
			</table>
			<table cellpadding="0" cellspacing="0"  border="1" style="border-bottom:1px solid #666;">
				<tr>
					<th>经办人姓名</th>
					<td>${obj.createBy}</td>
					<th>经办人联系方式</th>
					<td>${obj.operatorPhone}</td>
				</tr>
				<tr height="100">
					<th colspan="4"  style="text-align: left;">说明:</th>
				</tr>
				<tr>
					<td colspan="4" height="100"> 请认真阅读以上内容，并签字确认</td>
				</tr>
				<tr height="50">
					<th>家长<br>签字</th>
					<td></td>
					<th>经办人<br>签字</th>
					<td></td>
				</tr>
			</table>
			<p class="foot">
				注：以上所有信息解释权归深圳市超课科技有限公司所有。
			</p>
		</div>
	</div>
</div>
</body>
</html>  
