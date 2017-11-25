<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>社区课堂微信扫码支付订单详情</title>
    <style type="text/css">
        .payType{color: blue;font-weight: bold;}
        .amount{color: red;font-weight: bold;}
        .hiden{display: none;}
    </style>
    <script src="${ctx}/js/jquery.min.js"></script>
    <script src="${ctx}/js/qrcode1.js"></script>
    <script>
        $(document).ready(function() {
            loadData();
        });
        function loadData(){
            $("#payType_"+$("#payType").val()).removeClass("hiden");
            $(".studentName").html("&nbsp;"+$("#studentName").val()+"&nbsp;");
            $(".schoolName").html("&nbsp;"+$("#schoolName").val()+"&nbsp;");
            $(".payTypeText").html("&nbsp;"+$("#payTypeText").val()+"&nbsp;");
            $(".amount").html("&nbsp;"+$("#amount").val()+"&nbsp;");
            loadCode();
        }
        function loadCode(){
            $("#qrcode").html("");
            var qrcode = new QRCode(document.getElementById("qrcode"), {
                width : 200,//设置宽高
                height : 200
            });
            //qrcode.makeCode($("#codeUrl").val());
			 qrcode.makeCode($("#divCodeUrl").html());
            $("#qrcode").attr("title","");
            if($("#returnCode").val() == "FAIL"){
                $("#fail").css("display","block");
                $("#success").css("display","none");
            }
        }
    </script>

</head>
<body style="width:750px;height:350px;">
<div id="divCodeUrl" style="display: none">${returnBean.codeUrl}</div>
<input type="hidden" id="orderKey" value="${returnMap.orderInfo.id}"/>
<input type="hidden" id="orderId" value="${returnMap.orderInfo.orderId}"/>
<input type="hidden" id="returnCode" value="${returnMap.returnBean.returnCode}" />
<input type="hidden" id="codeUrl" value="${returnMap.returnBean.codeUrl}" />
<input type="hidden" id="payTypeText" value="${payTypeText}" />
<input type="hidden" id="studentName" value="${studentName}" />
<input type="hidden" id="schoolName" value="${schoolName}" />
<input type="hidden" id="amount" value="${returnMap.orderInfo.amount}" />
<input type="hidden" id="payType" value="${payType}" />
<div id="success">
    <div >
        <p>尊敬的用户：</p>
        <p>
            &nbsp; &nbsp; &nbsp;&nbsp;&nbsp; 您已同意通过
            <span class="payType payTypeText"></span>支付方式往
            <span class="payType studentName"></span> 学员账户里充值：
            <span class="amount"></span> 元，充值金额作为给该学员报读社区课堂的相关课程使用；
            <br/>
            &nbsp; &nbsp; &nbsp;&nbsp;&nbsp; 以下为该订单的支付二维码，请开启您的手机
            <span class="payType payTypeText"></span> 点击
            <span id="payType_2" class="payType hiden">“扫一扫”, </span>
            <span id="payType_3" class="payType hiden">点击付款，再点击右下角“扫码付”， </span> 对准该二维码,在打开的窗口，支付即可。
        </p>
    </div>
    <div align="center" id="qrcode" style="margin-top: 20px;"></div>
    <div align="center" style="margin-top:20px; ">
        (手机<span class="payType payTypeText"></span>扫一扫)
    </div>
    <div align="right" style="margin-top:30px;" >
        社区课堂<span class="payType schoolName"></span>${corporationType}
    </div>
</div>
<div id="fail" align="center" style="display: none;">
    <div>
        <h2 style="color: red;">获取订单二维码异常，请联系管理员解决！</h2>
    </div>
    <div>
        <p> 错误信息：${returnMap.returnBean.returnMsg}</p>
    </div>
</div>
</body>
</html>
