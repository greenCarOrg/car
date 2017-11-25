<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="../../assets/js/jquery-1.11.1.min.js"></script>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="${ctx}/js/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${ctx}/js/ueditor/ueditor.all.js"></script>


    <script type="application/javascript">

        $(document).ready(function() {
            //初始化编辑器
            ue = UE.getEditor('editor',{
                toolbars: toolbars
            });
        });
        function subm(){
            var repaly=$("#replay").val();
            if(repaly.hasContent()){
                UIkit.notify({message: '请填写有效回复', status: 'danger', timeout: 3000, pos: 'top-center'});
                return false;
            }else {
                $("#validateSubmitForm").submit();
            }
        }
    </script>
</head>

<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">回复</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <form:form id="validateSubmitForm"  cssClass="uk-form-stacked" action="updateFeedbook.do" enctype="multipart/form-data">
            <input  name="id" type="hidden" value="${data.id}"/>
            <div class="md-card">
                <div class="md-card-content large-padding" style="height: 800px;">
                    <div class="uk-grid" data-uk-grid-margin>
                        <div class="uk-width-medium-1-2">
                            <div class="parsley-row">
                                <label class="uk-form-label">问题描述</label>
                                <textarea class="md-input"cols="10" rows="3" readonly="readonly" data-parsley-trigger="keyup" data-parsley-minlength="20"
                                          data-parsley-maxlength="1000" data-parsley-validation-threshold="10">${data.content}</textarea>
                            </div>
                        </div>
                        <div class="uk-width-medium-1-2">
                            <div class="parsley-row">
                                <c:if test="${!empty data.img}">
                                    <img style="width:180px;height: 150px;" src="${data.img}"/>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="uk-grid" data-uk-grid-margin>
                        <div class="uk-width-medium-1-1">
                            <div class="parsley-row uk-margin-top">
                                <label class="uk-form-label">正文<span class="req">*</span></label>
                                <div id="editor" name="replay" style="width: 100%;height: 300px;"></div>
                            </div>
                        </div>
                    </div>
                    <div class="uk-grid" data-uk-grid-margin>
                        <div class="uk-width-medium-1-2" style="padding-top: 6%;">
                            <button onclick="subm()" type="submit" class="md-btn md-btn-primary">提交</button>
                            <a class="md-btn" href="feedbook.do">返回</a>
                        </div>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
