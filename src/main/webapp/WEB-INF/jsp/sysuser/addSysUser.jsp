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
        });
        function validataFrom() {
            jQuery("#validateSubmitForm").validate({
                rules: {
                    userName: {
                        required: true,
                        minlength: 6,
                        maxlength: 20
                    },
                    password: {
                        required: true,
                        minlength: 6,
                        maxlength: 20
                    },
                    realName: {
                        required: true,
                    },
                    phone: {
                        required: true,
                        maxlength: 11
                    }
                }
            });
        }

        function checkUser(){
            var roleId = $("#roleId").val();
            if(roleId == 0){
                $("#msg_roleId").html("请选择您要分配的角色名");
                return false;
            }else{
                $("#msg_roleId").html("");
            }

            $("#validateSubmitForm").submit();
        }

    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">新增用户信息</h3>

<div class="md-card">
    <div class="md-card-content large-padding">
        <form:form id="validateSubmitForm" action="addSysUser.do" cssClass="uk-form-stacked" commandName="sysUser">
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">用户名：<span style="color:#ff0000;">*</span></label>
                        <form:input path="userName" cssClass="md-input" id="userName" name="userName"/>
                    </div>
                </div>
            </div>

            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">密码：<span style="color:#ff0000;">*</span></label>
                        <form:password path="password" cssClass="md-input" id="password" name="password"/>
                    </div>
                </div>
            </div>

            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">真实姓名：<span style="color:#ff0000;">*</span></label>
                        <form:input path="realName" cssClass="md-input" id="realName" name="realName"/>
                    </div>
                </div>
            </div>

            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">手机号码：<span style="color:#ff0000;">*</span></label>
                        <form:input path="phone" cssClass="md-input" id="phone" name="phone" maxlength="11"/>
                    </div>
                </div>
            </div>

            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">状态：<span style="color:#ff0000;">*</span></label>
                        <input type="radio" name="status" value="1" checked="checked">可用
                        <input type="radio" name="status" value="0">停用
                    </div>
                </div>
            </div>

            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">角色：<span style="color:#ff0000;">*</span></label>
                        <select id="roleId" name="roleId" style="height: 30px; font-size: 14px;width: 300px;">
                            <option value="0">请选择</option>
                            <c:forEach var="role" items="${roleList}">
                                <option value="${role.id}">${role.roleName}</option>
                            </c:forEach>
                        </select>
                        <div id="msg_roleId" style="color: #ff0000;margin-top: 2px;"></div>
                    </div>
                </div>
            </div>

            <div class="uk-grid">
                <div class="uk-width-1-1">
                    <input type="submit" class="md-btn md-btn-primary" onclick="return checkUser();" value="提交保存" />
                    <a class="md-btn" href="sysuser.do?explain=sysuserdo">取消</a>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
