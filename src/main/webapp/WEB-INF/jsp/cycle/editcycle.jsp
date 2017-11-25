<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="../../assets/js/jquery-1.11.1.min.js"></script>
<script src="http://api.map.baidu.com/api?v=1.5&ak=1vQskKt1u9q4YTD3xMLcofl2" type="text/javascript"></script>
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
                    remark: {
                        required: true,
                        minlength:10,
                        maxlength: 1000
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
<h3 class="heading_b uk-margin-bottom">修改周期</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1" >
        <form:form id="validateSubmitForm" commandName="cycle" cssClass="uk-form-stacked"  action="updateCycle.do" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${cycle.id}"/>
            <div class="md-card" >
                <div class="md-card-content large-padding" style="height: 800px;">
                    <div class="uk-grid" data-uk-grid-margin>
                        <div class="uk-width-medium-1-2">
                            <div class="parsley-row">
                                <label>周期数<span class="req">*</span></label>
                                <form:input path="level" cssClass="md-input"/>
                            </div>
                        </div>
                        <div class="uk-width-medium-1-2">
                            <div class="uk-form-row parsley-row">
                                <label class="uk-form-label">上架状态</label>
                                <span class="icheck-inline">
                                    <input type="radio" name="state" <c:if test="${cycle.state == 1}">checked</c:if> id="val_radio_enabled" value="1" data-md-icheck checked/>
                                    <label for="val_radio_enabled" class="inline-label">启用</label>
                                </span>
                                <span class="icheck-inline" style="padding-left: 10%;">
                                    <input type="radio" name="state" <c:if test="${cycle.state == 0}">checked</c:if> id="val_radio_disabled" value="0" data-md-icheck/>
                                    <label for="val_radio_disabled" class="inline-label">停用</label>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="uk-grid" data-uk-grid-margin>
                        <div class="uk-width-medium-1-1">
                            <div class="parsley-row">
                                <label class="uk-form-label">内容 (多条以,分开)</label>
                                <textarea class="md-input" name="remark" cols="10" rows="3" data-parsley-trigger="keyup" data-parsley-minlength="20"
                                          data-parsley-maxlength="1000" data-parsley-validation-threshold="10">${cycle.remark}</textarea>
                            </div>
                        </div>
                    </div>

                    <div class="uk-grid" data-uk-grid-margin>
                        <div class="uk-width-medium-1-3">
                            <span class="uploadImg">
                                <input type="file" name="file" id="img" class="file" size="1" accept="image/*" onchange="PreviewImage(this)">
                                <a href="#">上传展示图片</a>
                            </span>
                            <span style="margin-left: 60px"><label></label></span>
                            <div class="parsley-row">
                                <div id="imgPreview" style="width:380px; height:200px;">
                                    <img src="http://7xjew0.com2.z0.glb.qiniucdn.com/${cycle.img}" style="width:380px;height:200px;">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="uk-grid" data-uk-grid-margin>
                        <div class="uk-width-medium-1-2" style="padding-top: 6%;">
                            <button id="submitBtn" type="submit" class="md-btn md-btn-primary">提交</button>
                            <a class="md-btn" href="cycle.do">返回</a>
                        </div>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>

</body>
</html>
