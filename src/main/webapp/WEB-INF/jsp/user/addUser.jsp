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
                        minlength: 0,
                        maxlength: 32
                    },
                    password: {
                        required: true,
                        minlength: 0,
                        maxlength: 32
                    },
                    confirmPsw: {
                        required: true,
                        minlength: 0,
                        maxlength: 32
                    },
                    realName: {
                        required: true,
                        minlength: 0,
                        maxlength: 32
                    },
                    contact: {
                        required: true,
                        minlength: 0,
                        maxlength: 11
                    },
                    email: {
                        email: true,
                        minlength: 0,
                        maxlength: 50
                    },
                    remark: {
                        maxlength: 255
                    }
                }
            });
        }


        function warnconfirmPsw() {
            var password = $("#password").val();
            var confirmPsw = $("#confirmPsw").val();
            if (password != confirmPsw) {
                var parent = document.getElementById("confirmPsw_parent");
                //添加 span
                var span = document.createElement("span");
                //设置 span 属性，如 id
                span.setAttribute("id", "confirmPsw_msg");
                span.innerHTML = "两次密码输入不一致！";
                span.setAttribute("style", "color:red");
                parent.appendChild(span);
                $("#confirmPsw").val("");
                return;
            }
        }
        function confirmPsw_msg(){
            var confirmPsw = $("#confirmPsw").val();
            var parent = document.getElementById("confirmPsw_parent");
            if(confirmPsw === ""){
                var span = document.getElementById("confirmPsw_msg");
                parent.removeChild(span);
            }
            return;
        }

        function userName_msg(){
            var userName = $("#userName").val();
            var parent = document.getElementById("userName_parent");
            var span = document.getElementById("userName_msg");
            if (/[a-zA-Z][a-zA-Z0-9]{3,13}/.test(userName) || userName === "") {
                parent.removeChild(span);
            }else if(userName === "" && span != null){
                parent.removeChild(span);
            }
            return;
        }

        function pwd_msg() {
            var password = $("#password").val();
            var parent = document.getElementById("password_parent");
            if(password === ""){
                var span = document.getElementById("pwd_msg");
                parent.removeChild(span);
            }
            return;
        }

        function contact_msg() {
            var contact = $("#contact").val();
            var parent = document.getElementById("contact_parent");
            var span = document.getElementById("contact_msg");
            if (/^1[0-9]{10}$/.test(contact)) {
                parent.removeChild(span);
            }else if(contact === "" && span != null){
                parent.removeChild(span);
            }
            return;
        }
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">新增账号信息</h3>

<div class="md-card">
    <div class="md-card-content large-padding">
        <form:form id="validateSubmitForm" action="addUser.do" onsubmit="return yingCang();" cssClass="uk-form-stacked" commandName="user">
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row" id="userName_parent">
                        <label class="uk-form-label">账号<span class="req">*</span></label>
                        <form:input path="userName" cssClass="md-input" id="userName" name="userName" maxlength="14"
                                    onchange="zhanghao()" onfocus="userName_msg()"/>
                    </div>
                </div>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">姓名</label>
                        <form:input path="realName" cssClass="md-input"/>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row" id="password_parent">
                        <label class="uk-form-label">密码</label>
                        <form:password path="password" cssClass="md-input" onfocus="pwd_msg()" onchange="check_pwd()"/>
                    </div>
                </div>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row" id="confirmPsw_parent">
                        <label class="uk-form-label">确认密码</label>
                        <input type="password" name="confirmPsw" id="confirmPsw" onfocus="confirmPsw_msg()" onchange="warnconfirmPsw()"
                               class="md-input"/>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="uk-form-row parsley-row">
                        <label class="uk-form-label">E_mail</label>
                        <form:input path="email" cssClass="md-input"/>
                    </div>
                </div>
                <div class="uk-width-medium-1-2">
                    <div class="uk-form-row parsley-row" id="contact_parent">
                        <label class="uk-form-label">手机号码</label>
                        <form:input path="contact" cssClass="md-input" maxlength="11" onfocus="contact_msg()" onchange="check_contact()"
                                    onkeyup='this.value=this.value.replace(/\D/gi,"")'/>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="uk-form-row parsley-row">
                        <label class="uk-form-label">性别</label>
                                    <span class="icheck-inline">
                                        <input type="radio" name="sex" id="val_radio_enabled" value="true"
                                               data-md-icheck checked/>
                                        <label for="val_radio_enabled" class="inline-label">男</label>
                                    </span>
                                    <span class="icheck-inline">
                                        <input type="radio" name="sex" id="val_radio_disabled" value="false"
                                               data-md-icheck/>
                                        <label for="val_radio_disabled" class="inline-label">女</label>
                                    </span>
                    </div>
                </div>
                <div class="uk-width-medium-1-2">
                    <div class="uk-form-row parsley-row">
                        <label class="uk-form-label">住址</label>
                        <form:input path="address" cssClass="md-input"/>
                    </div>
                </div>
            </div>
            <div class="uk-grid">
                <div class="uk-width-1-1">
                    <div class="parsley-row">
                        <label class="uk-form-label">备注 (最多255个字符)</label>
                        <textarea class="md-input" name="remark" cols="10" rows="4" data-parsley-trigger="keyup"
                                  data-parsley-minlength="20" data-parsley-maxlength="100"
                                  data-parsley-validation-threshold="10"
                                  data-parsley-minlength-message="Come on! You need to enter at least a 20 caracters long comment..">${user.remark}</textarea>
                    </div>
                </div>
            </div>
            <div class="uk-grid">
                <div class="uk-width-1-1">
                    <button type="submit" class="md-btn md-btn-primary" title="这是保存按钮">提交保存</button>
                    <a class="md-btn" href="users.do">取消</a>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
