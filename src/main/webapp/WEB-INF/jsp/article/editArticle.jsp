<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="../../assets/js/jquery-1.11.1.min.js"></script>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
    <script type="application/javascript">
        jQuery(document).ready(function () {
            validataFrom();
        });

        function validataFrom() {
            jQuery("#validateSubmitForm").validate({
                rules: {
                    title: {
                        required: true,
                        minlength: 2,
                        maxlength: 32
                    },
                    content: {
                        required: true,
                        minlength:2,
                        maxlength: 1000
                    },
                    publishTime:{
                        required:true
                    },
                    keywords:{
                        required:true,
                        minlength:1,
                        maxlength:10,
                    }
                }
            });
        }
    </script>
</head>

<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">编辑</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <form:form id="validateSubmitForm"  cssClass="uk-form-stacked" commandName="article" action="updateArticle.do">
            <form:hidden path="id" id="${article.id}"/>
            <div class="md-card">
                <div class="md-card-content large-padding">
                    <div class="md-card-content large-padding" >
                        <div class="uk-grid" data-uk-grid-margin>
                            <div class="uk-width-medium-1-2">
                                <div class="parsley-row">
                                    <label>标题<span class="req">*</span></label>
                                    <form:input path="title" cssClass="md-input" value="${article.title}"  name="title"/>
                                </div>
                            </div>
                            <div class="uk-width-medium-1-2">
                                <div class="parsley-row">
                                    <label>连接</label>
                                    <form:input path="link" value="${article.link}" cssClass="md-input" />
                                </div>
                            </div>
                        </div>
                        <div class="uk-grid" data-uk-grid-margin>
                            <div class="uk-width-medium-1-2">
                                <div class="parsley-row">
                                    <label>作者</label>
                                    <form:input path="author" value="${article.author}" cssClass="md-input" id="author" name="author"/>
                                </div>
                            </div>
                            <div class="uk-width-medium-1-2">
                                <div class="parsley-row">
                                    <label>作者邮箱</label>
                                    <form:input path="authorEmail" value="${article.authorEmail}" cssClass="md-input" id="authorEmail" name="authorEmail"/>
                                </div>
                            </div>
                        </div>
                        <div class="uk-grid" data-uk-grid-margin>
                            <div class="uk-width-medium-1-2">
                                <div class="parsley-row">
                                    <label>关键词<span class="req">*</span></label>
                                    <form:input path="keywords" cssClass="md-input" value="${article.keywords}" id="keywords" name="keywords"/>
                                </div>
                            </div>
                            <div class="uk-width-medium-1-2">
                                <div class="parsley-row">
                                    <label>发布时间<span class="req">*</span></label>
                                    <input type="text" name="publishTime" cssClass="md-input" value="${article.publishTime}" readonly="readonly" style="width:325px;height: 30px;" id="publishTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d',startDate:'%y-%M-01'})" class="Wdate" size="50" />
                                </div>
                            </div>
                        </div>

                        <div class="uk-grid" data-uk-grid-margin>
                            <div class="uk-width-medium-1-2">
                                <div class="uk-form-row parsley-row">
                                    <label class="uk-form-label">是否上架</label>
                                    <span class="icheck-inline">
                                    <input type="radio" name="state" <c:if test="${article.state == 1}">checked</c:if> id="val_radio_enabled" value="1" data-md-icheck checked/>
                                    <label for="val_radio_enabled" class="inline-label">上架</label>
                                </span>
                                    <span class="icheck-inline">
                                    <input type="radio" name="state" <c:if test="${article.state == 0}">checked</c:if>  id="val_radio_disabled" value="0" data-md-icheck/>
                                    <label for="val_radio_disabled" class="inline-label">下架</label>
                                </span>
                                </div>
                            </div>
                        </div>
                        <div class="uk-grid" data-uk-grid-margin>
                            <div class="uk-width-medium-1-2">
                                <div class="parsley-row">
                                    <label class="uk-form-label">内容 (最多255个字符)</label>
                                    <textarea class="md-input" name="remark" cols="10" rows="3" data-parsley-trigger="keyup" data-parsley-minlength="20"
                                              data-parsley-maxlength="2000" data-parsley-validation-threshold="10">${article.remark}</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="uk-grid">
                            <div class="uk-width-1-1" style="padding-left: 6%;">
                                <button id="submitBtn" type="submit" class="md-btn md-btn-primary">提交</button>
                                <a class="md-btn" href="article.do">返回</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
