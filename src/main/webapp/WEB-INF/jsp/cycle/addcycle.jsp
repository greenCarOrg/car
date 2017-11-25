<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="../../assets/js/jquery-1.11.1.min.js"></script>
<!doctype html>
<html class="lte-ie9" lang="en">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge;charset=UTF-8">

    <meta name="msapplication-tap-highlight" content="no"/>
    <title>${initParam['system-name']}管理系统</title>
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
    <h3 class="heading_b uk-margin-bottom">新增周期</h3>
    <div class="uk-grid" data-uk-grid-margin>
        <div class="uk-width-1-1" >
            <form:form id="validateSubmitForm" commandName="cycle" cssClass="uk-form-stacked"  action="addCycle.do" enctype="multipart/form-data">
                <div class="md-card" >
                    <div class="md-card-content large-padding" style="height: 800px;">
                        <div class="uk-grid" data-uk-grid-margin>
                            <div class="uk-width-medium-1-2">
                                <div class="parsley-row">
                                    <div class="parsley-row">
                                        <label>周期数<span class="req">*</span></label>
                                        <form:input path="level" cssClass="md-input"/>
                                    </div>
                                </div>
                            </div>
                            <div class="uk-width-medium-1-2">
                                <div class="uk-form-row parsley-row">
                                    <label class="uk-form-label">上架状态</label>
                                    <span class="icheck-inline">
                                        <input type="radio" name="state" id="val_radio_enabled" value="1" data-md-icheck checked/>
                                        <label for="val_radio_enabled" class="inline-label">启用</label>
                                    </span>
                                    <span class="icheck-inline" style="padding-left: 10%;">
                                        <input type="radio" name="state" id="val_radio_disabled" value="0" data-md-icheck/>
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
                                              data-parsley-maxlength="1000" data-parsley-validation-threshold="10"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="uk-grid" data-uk-grid-margin>
                            <div class="uk-width-medium-1-2">
                                <span class="uploadImg">
                                    <input type="file" name="file" id="img" class="file" size="1" accept="image/*" onchange="PreviewImage(this)">
                                    <a href="#">上传展示图片</a>
                                </span>
                                <div class="parsley-row">
                                    <div id="imgPreview" style="width:380px; height:200px;">
                                        <img src="../images/course/course_default.png" style="width:380px;height:200px;">
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
