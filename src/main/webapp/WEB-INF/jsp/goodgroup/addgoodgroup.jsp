<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="../../assets/js/jquery-1.11.1.min.js"></script>
<html>
<head>
    <title></title>
    <script type="application/javascript">
        jQuery(document).ready(function () {
            validataFrom();

            $("input[name='isparent']").eq(0).prop("checked",true);

            $("#parentidDiv").hide();

            $("input[name='isparent']").click(function(){
                var a = $(this).val();
                if(a == 1){
                    $("#parentidDiv").hide();
                }else{
                    $("#parentidDiv").show();
                }
            });
        });
        function validataFrom() {
            jQuery("#validateSubmitForm").validate({
                rules: {
                    name: {
                        required: true,
                        minlength: 2,
                        maxlength: 20
                    },
                    orderNum: {
                        number:true,
                        required: true
                    }
                }
            });
        }


        function checkFunc(){
            var isparent = $("input[name='isparent'][value = '1']").prop("checked");
            if(!isparent){
                var parentid = $("#parentid").val();
                if(parentid == 0){
                    $("#msg_parentid").html("请选择父节点");
                    return false;
                }
            }else{
                $("#validateSubmitForm").submit();
            }
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
        #uploadImg{ font-size:12px; overflow:hidden; position:absolute}
        #file{ position:absolute; z-index:100; margin-left:-180px; font-size:60px;opacity:0;filter:alpha(opacity=0); margin-top:-5px;}
    </style>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">新增商品分组</h3>

<div class="md-card">
    <div class="md-card-content large-padding" style="height: 700px;">
        <form:form id="validateSubmitForm" action="addGoodGroup.do" cssClass="uk-form-stacked" commandName="goodGroup" enctype="multipart/form-data">
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">分组名称<span style="color:#ff0000;">*</span></label>
                        <form:input path="name" cssClass="md-input" id="name" />
                    </div>
                </div>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">排序号：<span style="color:#ff0000;">*</span></label>
                        <form:input path="orderNum" cssClass="md-input" maxlength="2" name="orderNum"/>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">是否一级分组</label>
                        <input type="radio" name="isparent" checked="checked" style="width: 20px;height: 20px;" value="1"/>是<label data-text="" style="padding-left: 10%;"/>
                        <input type="radio" name="isparent" value="0" style="width: 20px;height: 20px;"/>否
                    </div>
                </div>
                <div class="uk-width-medium-1-2" id="parentidDiv">
                        <label class="uk-form-label">选择父节点<span style="color:#ff0000;">*</span></label>
                        <select id="parentid" name="parentid" style="height: 40px;width: 50%;font-size: 15px;">
                            <option value="0">请选择</option>
                            <c:forEach var="ls" items="${list}">
                                <option value="${ls.id}">${ls.name}</option>
                            </c:forEach>
                        </select>
                        <div id="msg_parentid" style="color: #ff0000; font-size: 14px; padding-top: 3px;"></div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">状态</label>
                        <span class="icheck-inline">
                            <input type="radio" name="state"  value="1" data-md-icheck checked/>
                            <label  class="inline-label">可用</label>
                        </span>
                        <span class="icheck-inline" style="padding-left: 4%;">
                            <input type="radio" name="state" value="0" data-md-icheck/>
                            <label class="inline-label">停用</label>
                        </span>
                    </div>
                </div>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">是否为热门推荐</label>
                        <span class="icheck-inline">
                            <input type="radio" name="hot"  value="1" data-md-icheck checked/>
                            <label  class="inline-label">推荐</label>
                        </span>
                        <span class="icheck-inline" style="padding-left: 4%;">
                            <input type="radio" name="hot" value="0" data-md-icheck/>
                            <label class="inline-label">普通</label>
                        </span>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">备注 (最多255个字符)</label>
                        <textarea class="md-input" name="remark" cols="10" rows="3" data-parsley-trigger="keyup" data-parsley-minlength="20"
                              data-parsley-maxlength="255" data-parsley-validation-threshold="10"></textarea>
                    </div>
                </div>
                <div class="uk-width-medium-1-2" style="display: none">
                    <span id="uploadImg">
                        <input type="file" name="file" id="file" size="1" accept="image/*" onchange="PreviewImage(this)">
                        <a href="#">上传LOGO</a>
                    </span>
                    <span style="margin-left: 60px"></span>
                    <div class="parsley-row">
                        <div id="imgPreview" style="width:100px; height:100px;">
                            <img id="imageFile" src="../images/course/course_default.png" style="width:100px;height:100px;">
                        </div>
                    </div>
                </div>
            </div>
            <div class="uk-grid" style="padding-top: 6%;">
                <div class="uk-width-1-2">
                    <input type="submit" class="md-btn md-btn-primary" onclick="return checkFunc();" value="提交保存"/>
                    <a class="md-btn" href="goodGroup.do">返回</a>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
