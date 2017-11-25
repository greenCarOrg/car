<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title></title>
    <script type="application/javascript">
        jQuery(document).ready(function () {
            validataFrom();
        });

        function validataFrom() {
            jQuery("#validateSubmitForm").validate({
                rules: {
                    name: {
                        required: true,
                        minlength: 2,
                        maxlength: 32
                    },
                    remark: {
                        required: true,
                        minlength: 2,
                        maxlength: 1000
                    },
                    phone: {
                        required: true,
                        minlength: 11,
                        maxlength: 11
                    },
                    address: {
                        required: true,
                        minlength: 5,
                        maxlength: 30
                    }
                }
            });
        }

        function PreviewImage(imgFile) {
            var pattern = /(\.*.jpg$)|(\.*.png$)|(\.*.jpeg$)|(\.*.gif$)|(\.*.bmp$)/;
            if(!pattern.test(imgFile.value)) {
                alert("系统仅支持jpg/jpeg/png/gif/bmp格式的照片！");
                imgFile.focus();
            } else {
                var path = URL.createObjectURL(imgFile.files[0]);
                document.getElementById("imgPreview").innerHTML = "<img src='"+path+"' style='width:380px;height:200px;' />";
            }
        }
    </script>
    <style>
        .uploadImg{ font-size:12px; overflow:hidden; position:absolute}
        .file{ position:absolute; z-index:100; margin-left:-180px; font-size:60px;opacity:0;filter:alpha(opacity=0); margin-top:-5px;}
    </style>
</head>

<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">修改推荐人</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1" >
        <form:form id="validateSubmitForm"  cssClass="uk-form-stacked" commandName="recommend"  action="updateRecommend.do" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${recommend.id}"/>
            <div class="md-card" >
                <div class="md-card-content large-padding" style="height: 800px;">
                    <div class="uk-grid" data-uk-grid-margin>
                        <div class="uk-width-medium-1-2">
                            <div class="parsley-row">
                                <label>姓名<span class="req">*</span></label>
                                <form:input path="name" cssClass="md-input"/>
                            </div>
                        </div>
                        <div class="uk-width-medium-1-2">
                            <div class="uk-form-row parsley-row">
                                <label class="uk-form-label">性别</label>
                                <span class="icheck-inline">
                                        <input type="radio" name="sex" id="val_radio_enabled" value="1" <c:if test="${recommend.sex==1}">checked</c:if> data-md-icheck checked/>
                                        <label for="val_radio_enabled" class="inline-label">男</label>
                                    </span>
                                <span class="icheck-inline" style="padding-left: 10%;">
                                        <input type="radio" name="sex" id="val_radio_disabled" value="0" <c:if test="${recommend.sex==0}">checked</c:if> data-md-icheck/>
                                        <label for="val_radio_disabled" class="inline-label">女</label>
                                    </span>
                            </div>
                        </div>
                    </div>
                    <div class="uk-grid" data-uk-grid-margin>
                        <div class="uk-width-medium-1-2">
                            <div class="parsley-row">
                                <label>身份证号码<span class="req">*</span></label>
                                <form:input path="idCard" cssClass="md-input" maxlength="11"/>
                            </div>
                        </div>
                        <div class="uk-width-medium-1-2">
                            <div class="uk-form-row parsley-row">
                                <label class="uk-form-label">状态</label>
                                <span class="icheck-inline">
                                        <input type="radio" name="state" id="val_radio_state" value="1" <c:if test="${recommend.state==1}">checked</c:if> data-md-icheck checked/>
                                        <label for="val_radio_state" class="inline-label">启用</label>
                                    </span>
                                <span class="icheck-inline" style="padding-left: 6%;">
                                        <input type="radio" name="state" id="val_radio_stated" value="0" <c:if test="${recommend.state==0}">checked</c:if> data-md-icheck/>
                                        <label for="val_radio_stated" class="inline-label">停用</label>
                                    </span>
                            </div>
                        </div>
                    </div>
                    <div class="uk-grid" data-uk-grid-margin>
                        <div class="uk-width-medium-1-2">
                            <div class="parsley-row">
                                <label>联系电话<span class="req">*</span></label>
                                <form:input path="phone" cssClass="md-input" maxlength="11"/>
                            </div>
                        </div>
                        <div class="uk-width-medium-1-2" >
                            <div class="parsley-row">
                                <label>地址<span class="req">*</span></label>
                                <form:input path="address" cssClass="md-input" />
                            </div>
                        </div>
                    </div>

                    <div class="uk-grid" data-uk-grid-margin>
                        <div class="uk-width-medium-1-2" style="padding-top: 10%;">
                            <div class="parsley-row">
                                <label class="uk-form-label">内容 (最多1000个字符)</label>
                                <textarea class="md-input" name="remark" cols="10" rows="3" data-parsley-trigger="keyup" data-parsley-minlength="20"
                                          data-parsley-maxlength="1000" data-parsley-validation-threshold="10">${recommend.remark}</textarea>
                            </div>
                        </div>
                        <div class="uk-width-medium-1-2">
                                <span class="uploadImg">
                                    <input type="file" name="file" id="file" class="file" size="1" accept="image/*" onchange="PreviewImage(this)">
                                    <a href="#">上传用户头像</a>
                                </span>
                            <div class="parsley-row">
                                <div id="imgPreview" style="width:380px; height:200px;">
                                    <img  src="http://7xjew0.com2.z0.glb.qiniucdn.com/${recommend.head}"  style="width:380px;height:200px;">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="uk-grid" data-uk-grid-margin>
                        <div class="uk-width-medium-1-2" style="padding-top: 6%;">
                            <button id="submitBtn" type="submit" class="md-btn md-btn-primary">提交</button>
                            <a class="md-btn" href="recommend.do">返回</a>
                        </div>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>

</body>
</html>
