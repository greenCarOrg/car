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
            $("#iconDiv").show();

            $("input[name='isparent']").click(function(){
                var a = $(this).val();
                if(a == 1){
                    $("#parentidDiv").hide();
                    $("#iconDiv").show();
                }else{
                    $("#parentidDiv").show();
                    $("#iconDiv").hide();
                    $("#icon").val("");
                }
            });
        });
        function validataFrom() {
            jQuery("#validateSubmitForm").validate({
                rules: {
                    funcName: {
                        required: true,
                        minlength: 0,
                        maxlength: 32
                    },
                    funcPath: {
                        required: true,
                        minlength: 0,
                        maxlength: 200
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
                var icon = $("#icon").val();
                if(icon == null || icon == "" || icon.length == 0){
                    $("#msg_icon").html("请输入图标编码");
                    return false;
                }

            }
            $("#validateSubmitForm").submit();
        }

    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">新增功能信息</h3>

<div class="md-card">
    <div class="md-card-content large-padding">
        <form:form id="validateSubmitForm" action="addSysFunc.do" cssClass="uk-form-stacked" commandName="sysFunction">
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">功能名称<span style="color:#ff0000;">*</span></label>
                        <form:input path="funcName" cssClass="md-input" id="funcName" name="funcName"/>
                    </div>
                </div>
            </div>

            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">是否父节点</label>
                        <input type="radio" name="isparent" checked="checked" value="1"/>是
                        <input type="radio" name="isparent" value="0"/>否
                    </div>
                </div>
            </div>

            <div class="uk-grid" data-uk-grid-margin id="iconDiv">
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">图标：</label>
                        <form:input path="icon" cssClass="md-input" id="icon" name="icon"/>
                        <div id="msg_icon" style="color: #ff0000; font-size: 14px; padding-top: 3px;"></div>
                    </div>
                </div>
            </div>

            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">排序号：<span style="color:#ff0000;">*</span></label>
                        <form:input path="orderNum" cssClass="md-input" id="orderNum" name="orderNum"/>
                    </div>
                </div>
            </div>

            <div class="uk-grid" data-uk-grid-margin id="parentidDiv">
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">选择父节点<span style="color:#ff0000;">*</span></label>
                        <select id="parentid" name="parentid">
                            <option value="0">请选择</option>
                            <c:forEach var="ls" items="${list}">
                                <option value="${ls.id}">${ls.funcName}</option>
                            </c:forEach>
                        </select>
                        <div id="msg_parentid" style="color: #ff0000; font-size: 14px; padding-top: 3px;"></div>
                    </div>
                </div>
            </div>

            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">功能路径<span style="color:#ff0000;">*</span></label>
                        <form:input path="funcPath" cssClass="md-input" id="funcPath" name="funcPath"/>
                    </div>
                </div>
            </div>

            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-medium-2-2">
                        <div class="parsley-row">
                            <label class="uk-form-label">状态</label>
                            <input type="radio" name="status" value="1" checked="checked">可用
                            <input type="radio" name="status" value="0">停用
                        </div>
                    </div>
                </div>
            </div>

            <div class="uk-grid">
                <div class="uk-width-1-1">
                    <input type="submit" class="md-btn md-btn-primary" onclick="return checkFunc();" value="提交保存"></input>
                    <a class="md-btn" href="../system/sysfunc.do?explain=sysfuncdo">返回</a>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
