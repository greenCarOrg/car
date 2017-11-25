<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: gonglei
  Date: 15/9/3
  Time: 下午3:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h3 class="heading_b uk-margin-bottom">修改密码</h3>
<div class="md-card">
    <div class="md-card-content large-padding">
        <form:form action="savepassword.do" cssClass="uk-form-stacked" method="post"  modelAttribute="user">
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label>原密码<span class="req">*</span></label>
                        <form:password path="password" cssClass="md-input"/>
                    </div>
                </div>

            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label>新密码<span class="req">*</span></label>
                        <form:password path="password1" cssClass="md-input"/>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label>确认密码</label>
                        <form:password path="password2" cssClass="md-input"/>
                    </div>
                </div>
            </div>
            <div class="uk-grid">
                <div class="uk-width-1-1">
                    <button type="submit" class="md-btn md-btn-primary">提交保存</button>
                    <a class="md-btn" href="../console/dashboard.do">取消</a>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
