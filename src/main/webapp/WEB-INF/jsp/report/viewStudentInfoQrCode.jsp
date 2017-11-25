<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>社区课堂学生考勤信息</title>
    <script src="${ctx}/js/jquery.min.js"></script>
    <script src="${ctx}/js/qrcode.js"></script>
    <style>
        .contain {
            width: 600px;
            height: 600px;
        }
        .code {
            float: left;
            width: 50%;
        }
        .mb-40 {
            margin-bottom: 40px;
        }
    </style>
</head>
<body style="width:600px;height:600px;">
<div class="contain">
    <input type="hidden" id="codeUrl" value="${requestURL}" />
    <input type="hidden" id="returnCode" value="${returnCode}" />
    <input type="hidden" id="studentName" value="${studentName}" />
    <input type="hidden" id="schoolName" value="${schoolName}" />
    <input type="hidden" id="returnMsg" value="${returnMsg}" />
    <div class="show" style="display: block" ondragstart="return false">
        <div class="code mb-40">
            <div align="center" class="qrcode" id="qrcode1" style="margin-top: 20px;" ></div>
            <div align="center" style="margin-top: 20px;"> <label>学生姓名:</label><span  class="student"></span></div>
            <div align="center" style="margin-top: 20px;"> <label>报读校区:</label><span  class="school"></span></div>
        </div>
        <div class="code mb-40">
            <div align="center" class="qrcode" id="qrcode2" style="margin-top: 20px;" ></div>
            <div align="center" style="margin-top: 20px;"> <label>学生姓名:</label><span  class="student"></span></div>
            <div align="center" style="margin-top: 20px;"> <label>报读校区:</label><span  class="school"></span></div>
        </div>
        <div class="code">
            <div align="center" class="qrcode" id="qrcode3" style="margin-top: 20px;" ></div>
            <div align="center" style="margin-top: 20px;"> <label>学生姓名:</label><span  class="student"></span></div>
            <div align="center" style="margin-top: 20px;"> <label>报读校区:</label><span  class="school"></span></div>
        </div>
        <div class="code">
            <div align="center" class="qrcode"  id="qrcode4" style="margin-top: 20px;" ></div>
            <div align="center" style="margin-top: 20px;"> <label>学生姓名:</label><span  class="student"></span></div>
            <div align="center" style="margin-top: 20px;"> <label>报读校区:</label><span  class="school"></span></div>
        </div>
    </div>
    <div class="hite" style="display: none;">
        <div align="center" style="margin-top: 20px;"> <label>学生姓名:</label><span  class="student1"></span></div>
        <div align="center" style="margin-top: 20px;"> <label>报读校区:</label><span  class="school1"></span></div>
        <div class="falt" align="center" style="margin-top: 20px;"><span  id="msg"></span></div>
    </div>
</div>

<script>
    $(document).ready(function() {
        loadData();
    });
    function loadData(){
        var qrcode1 = new QRCode(document.getElementById("qrcode1"), {
            width : 200,//设置宽高
            height : 200
        });
        var qrcode2 = new QRCode(document.getElementById("qrcode2"), {
            width : 200,//设置宽高
            height : 200
        });
        var qrcode3 = new QRCode(document.getElementById("qrcode3"), {
            width : 200,//设置宽高
            height : 200
        });
        var qrcode4 = new QRCode(document.getElementById("qrcode4"), {
            width : 200,//设置宽高
            height : 200
        });
        qrcode1.makeCode($("#codeUrl").val());
        $("#qrcode1").attr("title","");
        qrcode2.makeCode($("#codeUrl").val());
        $("#qrcode2").attr("title","");
        qrcode3.makeCode($("#codeUrl").val());
        $("#qrcode3").attr("title","");
        qrcode4.makeCode($("#codeUrl").val());
        $("#qrcode4").attr("title","");
        if($("#returnCode").val() == "FAIL" ){
            $(".show").hide();
            $(".hite").show();
            $("#msg").html($("#returnMsg").val());
            $(".student1").html($("#studentName").val());
            $(".school1").html($("#schoolName").val());
        }else{
            $(".show").show();
            $(".hite").hide();
            $(".student").html($("#studentName").val());
            $(".school").html($("#schoolName").val());
        }
    }
</script>
</body>
</html>
