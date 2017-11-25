<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="../../assets/js/jquery-1.11.1.min.js"></script>
<html>
<head>
    <title></title>
    <script type="application/javascript">
        jQuery.validator.addMethod("isMobile", function(value, element, param) {
            var length = value.length;
            var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
            return this.optional(element) || (length == 11 && mobile.test(value));
        }, $.validator.format("请确保输入的手机号码是正确的"));
        jQuery(document).ready(function () {
            validataFrom();
        });
        function validataFrom() {
            jQuery("#validateSubmitForm").validate({
                rules: {
                    realName: {
                        required: true,
                    },
                    phone: {
                        required: true,
                        maxlength: 11,
                        minlength:11,
                        isMobile:true
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
<h3 class="heading_b uk-margin-bottom">修改用户信息</h3>

<div class="md-card">
    <div class="md-card-content large-padding">
        <form:form id="validateSubmitForm" action="editSysUser.do" cssClass="uk-form-stacked" commandName="sysUser">
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">用户名：</label>
                        <form:input path="userName" cssClass="md-input" id="userName" name="userName" readonly="true" />
                    </div>
                </div>
            </div>


            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">真实姓名：</label>
                        <form:input path="realName" cssClass="md-input" id="realName" name="realName"/>
                    </div>
                </div>
            </div>

            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">手机号码：</label>
                        <form:input path="phone" cssClass="md-input" id="phone" name="phone" maxlength="11"/>
                        <div id="msg_phone" style="color: #ff0000;margin-top: 2px;"></div>
                    </div>
                </div>
            </div>


            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">角色：</label>
                        <select id="roleId" name="roleId" style="height: 30px; font-size: 14px;width: 300px;">
                            <c:forEach var="role" items="${roleList}">
                                <c:if test="${sysUser.roleId==role.id}">
                                    <option value="${role.id}" style="width: 200px; height: 40px;" selected>${role.roleName}</option>
                                </c:if>
                                <c:if test="${sysUser.roleId!=role.id}">
                                    <option value="${role.id}" style="width: 200px; height: 40px; ">${role.roleName}</option>
                                </c:if>
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
