<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        .uploadImg{ font-size:12px; overflow:hidden; position:absolute}
        .myfiles{ position:absolute; z-index:100; width: 150px; margin-left: -50px; font-size:60px;opacity:0;filter:alpha(opacity=0); margin-top:-5px;}
    </style>
    <script src="${ctx}/js/plupload/qiniu.min.js"></script>
    <script src="${ctx}/js/plupload/plupload.full.min.js"></script>
    <script type="application/javascript" src="${ctx}/js/common/select.js"></script>
    <script type="application/javascript">
        jQuery(document).ready(function () {
            validataFrom();
        });
        function validataFrom() {
            jQuery("#validateSubmitForm").validate({
                rules: {
                    name: {
                        required: true,
                        minlength: 0,
                        maxlength: 50
                    },
                    remark: {
                        required: true,
                        minlength: 0,
                        maxlength: 500
                    }
                }
            });
        }
        function PreviewImage(imgFile,previewDiv) {
            var pattern = /(\.*.jpg$)|(\.*.png$)|(\.*.jpeg$)|(\.*.gif$)|(\.*.bmp$)/;
            if(!pattern.test(imgFile.value)) {
                UIkit.notify({message: '系统仅支持jpg/jpeg/png/gif/bmp格式的图片！', status:'danger', timeout: 1000, pos: 'top-center'});
                imgFile.focus();
            } else {
                var files = imgFile.files;
                var imgs = "";
                if(files.length>10){
                    UIkit.notify({message: '最多上传10张图片!', status:'danger', timeout: 1000, pos: 'top-center'});
                    $("#myfiles").val("");
                }
                for(var i=0;i<files.length;i++){
                    var path = URL.createObjectURL(imgFile.files[i]);
                    imgs +="<img src='"+path+"' style='width:180px;height:100px;float:left;' />";
                }
                document.getElementById(previewDiv).innerHTML = imgs;
            }
        }
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom"><c:if test="${empty tService.id}">添加</c:if><c:if test="${not empty tService.id}">编辑</c:if>服务</h3>
<div class="md-card">
    <div class="md-card-content large-padding" style="height: auto !important;">
        <form:form id="validateSubmitForm" cssClass="uk-form-stacked" action="edit.do" commandName="tService" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${tService.id}"/>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label  class="uk-form-label">类型<span class="req">*</span></label>
                        <select name="type" id="type" style="width: 80%;">
                            <option value="0" <c:if test="${tService.type==0}">selected</c:if>>月嫂服务</option>
                            <option value="1" <c:if test="${tService.type==1}">selected</c:if>>专家服务</option>
                        </select>
                    </div>
                </div>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label for="name" class="uk-form-label" >服务名称</label>
                        <form:input path="name" cssClass="md-input" style="width: 100%;"/>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-1">
                    <div class="uk-form-row parsley-row">
                        <label class="uk-form-label">服务内容</label>
                        <textarea class="md-input" name="remark" cols="10" rows="2" data-parsley-trigger="keyup"
                                  data-parsley-minlength="20" data-parsley-maxlength="100"
                                  data-parsley-validation-threshold="10"
                                  data-parsley-minlength-message="Come on! You need to enter at least a 20 caracters long comment..">${tService.remark}</textarea>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-1">
                    <div class="uk-form-row parsley-row">
                        <label>封面图片</label>
                        <span class="uploadImg">
                            <input type="file" name="img" id="img" class="myfiles" size="1" accept="image/*" onchange="PreviewImage(this,'imgPreview')"/>
                            <a href="#">上传封面图片</a>
                        </span>
                        <div id="imgPreview" style="width:100%; height:100px;">
                            <c:if test="${not empty tService.imgKey}">
                                <img src="${tService.imgUrl}" style="width:180px;height:100px;"/>
                            </c:if>
                        </div>
                    </div>
                </div>
            <div class="uk-grid">
                <div class="uk-width-1-1">
                    <button type="submit" class="md-btn md-btn-primary">提交保存</button>
                    <a class="md-btn" href="maternity.do">返回</a>
                </div>
            </div>
        </form:form>
    </div>
</div>
</div>
</body>
</html>
