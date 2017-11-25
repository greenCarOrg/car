<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<html>
<head>
    <title></title>
    <script src="${ctx}/js/common/FormUtil.js"></script>
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
                    realName: {
                        required: true,
                        minlength: 0,
                        maxlength: 32
                    },
                    email: {
                        email: true,
                        minlength: 0,
                        maxlength: 50
                    },
                    attendanceNoStr: {
                        required: true,
                        minlength: 0,
                        maxlength: 50
                    },
                    contact: {
                        phone: true,
                        minlength: 11,
                        maxlength: 11
                    },
                    remark: {
                        maxlength: 255
                    }
                }
            });
        }
        //电话号码验证
        function validatePhone(v) {
            if(!FormUtil.isPhone(v)){
                UIkit.notify({
                    message: '请输入有效的电话号码！',
                    status: 'danger',
                    timeout: 3000,
                    pos: 'top-center'
                });
                $("#contact").val("");
                return;
            }
        }
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">修改账号信息</h3>
<div class="md-card">
    <div class="md-card-content large-padding">
        <form:form id="validateSubmitForm" action="updateUser.do" cssClass="uk-form-stacked" commandName="user">
            <form:hidden path="userId"/>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">账号</label>
                        <label cssClass="md-input">${user.userName}</label>
                        <form:hidden path="userName"  cssClass="md-input"/>
                        <label class="uk-form-label"></label>
                        <form:errors path="userName" cssStyle="color:red"  id="tishi" />
                    </div>
                </div>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">姓名<span style="color: red;">*</span></label>
                        <form:input path="realName" cssClass="md-input"/>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="uk-form-row parsley-row">
                        <label class="uk-form-label">联系方式<span style="color: red;">*</span></label>
                        <form:input path="contact" cssClass="md-input" maxlength="11" onchange="validatePhone(this.value)"/>
                    </div>
                </div>
                <div class="uk-width-medium-1-2">
                    <div class="uk-form-row parsley-row">
                        <label class="uk-form-label">E_mail</label>
                        <form:input path="email" cssClass="md-input"/>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="uk-form-row parsley-row">
                        <label class="uk-form-label">性别</label>
                                    <span class="icheck-inline">
                                        <input type="radio" name="sex" id="val_radio_enabled" value="true" <c:if test="${user.sex}">checked</c:if>
                                               data-md-icheck checked/>
                                        <label for="val_radio_enabled" class="inline-label">男</label>
                                    </span>
                                    <span class="icheck-inline">
                                        <input type="radio" name="sex" id="val_radio_disabled" value="false" <c:if test="${user.sex eq false}">checked</c:if>
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
                        <textarea class="md-input" name="remark" cols="10" rows="4" data-parsley-trigger="keyup" data-parsley-minlength="20" data-parsley-maxlength="100"
                                  data-parsley-validation-threshold="10" data-parsley-minlength-message="Come on! You need to enter at least a 20 caracters long comment..">${user.remark}</textarea>
                    </div>
                </div>
            </div>
            <div class="uk-grid">
                <div class="uk-width-1-1">
                    <button type="submit" class="md-btn md-btn-primary">提交保存</button>
                    <a class="md-btn" href="users.do">取消</a>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
