<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 配置文件 -->
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="msapplication-tap-highlight" content="no"/>
    <title>配置商品属性</title>
    <style type="text/css">
        #mo{
            display: none;/*隐藏*/
            width: 100%;
            height: 100%;
            position: fixed;
            overflow: auto;
            background-color: rgba(0,0,0,0.7);
            top: 0px;
            left: 0px;
            z-index: 1;
        }
        #moimg{
            display: block;
            margin:150px auto;
            width: 60%;
            max-width: 750px;
        }
        #caption{
            text-align: center;
            margin: 100px auto;
            width: 60%;
            max-height: 750px;
            font-size: 20px;
            color:#ccc;
        }
        #moimg,#caption{
            -webkit-animation: first 1s;
            -o-animation: first 1s;
            animation: first 1s;
        }
        @keyframes first{
            from{transform: scale(0.1);}
            to{transform: scale(1);}
        }
        .close{
            font-size: 40px;
            font-weight: bold;
            position: absolute;
            top: 100px;
            right: 14%;
            color:#f1f1f1;
        }
        .close:hover,
        .close:focus{
            color:#bbb;
            cursor:pointer;
        }
        @media only screen and(max-width:750px ) {
            #moimg{
                width: 100%;
            }
        }
    </style>
    <script language="JavaScript">

        function removeItem(detailId,state) {
            UIkit.modal.confirm("确定修改吗？", function(){
                $.get("updateGoodAttr.do", {
                    attrId: detailId,
                    state: state
                }, function (data) {
                    if (data == 1) {
                        UIkit.notify({message: "操作成功", status: 'success', timeout: 1000, pos: 'top-center'});
                        setTimeout('window.location.reload()',1000);
                    }else {
                        UIkit.notify({message: "操作失败", status: 'danger', timeout: 1000, pos: 'top-center'});
                    }
                });
            },{labels:{Ok:"确认",Cancel:"取消"}});
        }

        function fileSelected() {
            var file = document.getElementById('file').files[0];
            var pattern = /(\.*.jpg$)|(\.*.png$)|(\.*.jpeg$)|(\.*.gif$)|(\.*.bmp$)/;
            if(!pattern.test(file.name)) {
                UIkit.notify({message: '系统仅支持jpg/jpeg/png/gif/bmp格式的照片!', status:'danger', timeout: 1000, pos: 'top-center'});
                $("#file").val("");
            }
            if (file) {
                var fileSize = 0;
                if (file.size > 1024 * 1024){
                    fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
                } else{
                    fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
                }
                if(file.size > 1024 * 1024 * 20){
                    UIkit.notify({message: '上传文件过大!', status:'danger', timeout: 1000, pos: 'top-center'});
                    $("#file").val("");
                    return;
                }
            }
        }
        
        function addGoodAttr() {
            var attr = $("#attr").val();
            if(attr=="" || attr==null){
                UIkit.notify({message: "请输入属性", status: 'danger', timeout: 1000, pos: 'top-center'});
                return;
            }

            if($("#price").val()==""){
                UIkit.notify({message: "请填写价格", status: 'danger', timeout: 1000, pos: 'top-center'});
            }else {
                var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
                if (!reg.test($("#price").val())) {
                    UIkit.notify({message: "价格格式错误", status: 'danger', timeout: 1000, pos: 'top-center'});
                    $("#price").val("");
                    return;
                }
            }
            if($("#num").val()==""){
                UIkit.notify({message: "请填写库存数量", status: 'danger', timeout: 1000, pos: 'top-center'});
            }else {
                var re = /^[0-9]+$/ ;
                if (!re.test($("#num").val())) {
                    UIkit.notify({message: "库存数量请填写正确", status: 'danger', timeout: 1000, pos: 'top-center'});
                    $("#num").val("");
                    return;
                }
            }

            var formData = new FormData($("#saveForm1")[0]);
            var xhr = new XMLHttpRequest();
            xhr.upload.addEventListener("progress", uploadProgress, false);
            xhr.addEventListener("load", uploadComplete, false);
            xhr.addEventListener("error", uploadFailed, false);
            xhr.addEventListener("abort", uploadCanceled, false);
            xhr.open("POST", "addGoodAttr.do");
            xhr.send(formData);
        }

        function showImg(e){
            var motai=document.getElementById('mo');
            var moimg=document.getElementById("moimg");
            motai.style.display="block";
            moimg.src='http://7xjew0.com2.z0.glb.qiniucdn.com/'+e;
        }

        function hideImg(){
            var motai=document.getElementById('mo');
            motai.style.display="none";
        }

        function uploadProgress(evt) {
        }

        function uploadComplete(evt) {
            /* 当服务器响应后返回结果后，这个事件就会被触发 */
            var result = eval("("+evt.target.responseText+")");
            if(result.code=="success"){
                UIkit.notify({message: '保存成功!', status: 'success', timeout: 1000, pos: 'top-center'});
                setTimeout('window.location.reload()',1500);
            }else{
                UIkit.notify({message: result.msg, status: 'warning', timeout: 1000, pos: 'top-center'});
            }
            $("#saveBut").attr('disabled',false);
            $("#cancelBut").attr('disabled',false);
        }

        function uploadFailed(evt) {
            UIkit.notify({message: '上传文件发生了错误，请重试!', status:'danger', timeout: 1000, pos: 'top-center'});
        }

        function uploadCanceled(evt) {
            UIkit.notify({message: '上传被用户取消或者浏览器断开连接!', status:'danger', timeout: 1000, pos: 'top-center'});
        }

    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<div style="clear: both;"></div>
<div class="md-card" style="height: 200px;">
    <div class="md-card-content large-padding">
        <form class="uk-form" id="saveForm1" enctype="multipart/form-data">
            <input type="hidden" name="goodId" id="goodId" value="${goodsId}">
            <table class="uk-table uk-table-hover" style="border: 0px;">
                <thead>
                    <tr>
                        <td width="150px;">属性</td>
                        <td width="150px;">价格</td>
                        <td width="150px;">库存</td>
                        <td width="150px;">图片</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input name="attr" id="attr" value="" style="width: 80%;" maxlength="30" /></td>
                        <td><input name="price" id="price" value="0" maxlength="10" /></td>
                        <td><input name="num" id="num" value="0" maxlength="10"/></td>
                        <td><input type="file" name="file" id="file" size="1" accept="image/*" onchange="fileSelected(this)"></td>
                    </tr>
                </tbody>
            </table>
            <div class="uk-grid">
                <div class="uk-width-1-1">
                    <button id ="saveBut" type="button" class="md-btn md-btn-primary" onclick="addGoodAttr()">保存</button>
                    <a class="md-btn" href="#" onclick="javascript:window.close()">退出</a>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="md-card uk-margin-medium-bottom">
    <div class="md-card-content">
        <div class="uk-overflow-container">
            <table class="uk-table uk-table-hover">
                <thead>
                <tr>
                    <td width="150px;">属性</td>
                    <td width="150px;">价格</td>
                    <td width="150px;">库存</td>
                    <td width="150px;">图片</td>
                    <td width="150px;">状态</td>
                    <td class="uk-width-2-10 uk-text-center">操作</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="ta" items="${attrList}">
                    <tr>
                        <td>${ta.attr}</td>
                        <td>${ta.price}</td>
                        <td>${ta.num}</td>
                        <td><a href="#" onclick="showImg('${ta.img}')">查看图片</a></td>
                        <td>${ta.state==1?'有效':'无效'}</td>
                        <td class="uk-text-center">
                            <a href="javascript:removeItem('${ta.attr_id}','${ta.state}')" class="k-button k-button-icontext k-grid-delete">
                                <span class="k-icon k-delete"></span>修改
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="motai" id="mo">
    <span class="close" id="close" onclick="hideImg()">×</span>
    <img class="motaiimg" id="moimg">
    <div id="caption"></div>
</div>
</body>
</html>
