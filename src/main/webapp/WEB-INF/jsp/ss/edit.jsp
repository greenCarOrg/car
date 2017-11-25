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
            initSpecialist();
        });
        function validataFrom() {
            jQuery("#validateSubmitForm").validate({
                rules: {
                    name: {
                        required: true,
                        minlength: 0,
                        maxlength: 50
                    },
                    sId: {
                        required: true
                    },
                    detail: {
                        required: true,
                        minlength: 0,
                        maxlength: 500
                    },
                    price: {
                        required: true,
                        decimal2: [10,2]
                    }
                }
            });
        }
        function initSpecialist() {
            var sId=StrUtil.isNotEmpty("${entity.sId}")?"${entity.sId}":0;
            $.post("${ctx}/combobox/getComboxData.do", {type:'specialist'}, function (data) {
                if (data!= null) {
                    $("#sId").kendoComboBox({
                        placeholder: "请选择...",
                        dataTextField: "value",
                        dataValueField: "key",
                        dataSource: data,
                        filter: "contains",
                        suggest: true,
                        index: 0
                    });
                    if (sId!= 0) {
                        $("#sId").data("kendoComboBox").value(s_id);
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
<h3 class="heading_b uk-margin-bottom"><c:if test="${empty entity.id}">添加</c:if><c:if test="${not empty entity.id}">编辑</c:if>专家服务</h3>
<div class="md-card">
    <div class="md-card-content large-padding" style="height: auto !important;">
        <form:form id="validateSubmitForm" cssClass="uk-form-stacked" action="edit.do" commandName="specialistService" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${entity.id}"/>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-3">
                    <div class="parsley-row">
                        <label for="name" class="uk-form-label" >专家服务名称</label>
                        <form:input path="name" cssClass="md-input" style="width: 100%;"/>
                    </div>
                </div>
                <div class="uk-width-medium-1-3">
                    <div class="parsley-row">
                        <label for="sId" class="uk-form-label" >专家</label>
                        <input id="sId" name="sId" placeholder="选择专家..." style="width: 50%;" />
                    </div>
                </div>
                <div class="uk-width-medium-1-3">
                    <div class="parsley-row">
                        <label for="price" class="uk-form-label" >价格</label>
                        <form:input path="price" cssClass="md-input" style="width: 100%;"/>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-1">
                    <div class="uk-form-row parsley-row">
                        <label class="uk-form-label">服务内容</label>
                        <textarea class="md-input" name="detail" cols="10" rows="2" data-parsley-trigger="keyup"
                                  data-parsley-minlength="20" data-parsley-maxlength="100"
                                  data-parsley-validation-threshold="10"
                                  data-parsley-minlength-message="Come on! You need to enter at least a 20 caracters long comment..">${entity.detail}</textarea>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-1">
                    <div class="uk-form-row parsley-row">
                        <label>封面图片</label>
                        <span class="uploadImg">
                            <input type="file" name="imgFile" id="imgFile" class="myfiles" size="1" accept="image/*" onchange="PreviewImage(this,'imgPreview')"/>
                            <a href="#">上传封面图片</a>
                        </span>
                        <div id="imgPreview" style="width:100%; height:100px;">
                            <c:if test="${not empty entity.img}">
                                <img src="${initParam['QNURL']}${entity.img}" style="width:180px;height:100px;"/>
                            </c:if>
                        </div>
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
