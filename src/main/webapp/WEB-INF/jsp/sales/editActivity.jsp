<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script type="application/javascript">
        jQuery(document).ready(function () {
        });
        //保存课程信息
        function save(){
            var params = {};
            var saveForm=$("#validateSubmitForm").serializeArray();
            var formData = new FormData($("#validateSubmitForm")[0]);
            $.each(saveForm, function() {
                params[this.name] = this.value;
            });
            if(StrUtil.isEmpty(params.act_name)){
                UIkit.notify({message: "活动名称不能为空!", status: 'warning', timeout: 1000, pos: 'top-center'});
                return;
            }
            if(StrUtil.isEmpty(params.act_type)){
                UIkit.notify({message: "活动类型不能为空!", status: 'warning', timeout: 1000, pos: 'top-center'});
                return;
            }
            if(StrUtil.isEmpty(params.act_count)){
                UIkit.notify({message: "参加活动人数不能为空!", status: 'warning', timeout: 1000, pos: 'top-center'});
                return;
            }
            if(StrUtil.isEmpty(params.start_time)){
                UIkit.notify({message: "活动开始时间不能为空!", status: 'warning', timeout: 1000, pos: 'top-center'});
                return;
            }
            if(StrUtil.isEmpty(params.end_time)){
                UIkit.notify({message: "活动结束时间不能为空!", status: 'warning', timeout: 1000, pos: 'top-center'});
                return;
            }
            if(StrUtil.isEmpty(params.act_desc)){
                UIkit.notify({message: "活动描述不能为空!", status: 'warning', timeout: 1000, pos: 'top-center'});
                return;
            }
            $.ajax({
                url: 'edit.do' ,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (result) {
                    result = eval("("+result+")");
                    if(result.code=="0"){
                        UIkit.notify({message: "保存成功!", status: 'success', timeout: 1000, pos: 'top-center'});
                        // 跳转到列表页面
                        setTimeout('window.location.href="${ctx}/activity/activity.do";',1000);
                    }else{
                        UIkit.notify({message: result.msg, status: 'danger', timeout: 1000, pos: 'top-center',});
                    }
                },
                error: function (result) {
                    UIkit.notify({message: '保存失败!', status: 'danger', timeout: 1000, pos: 'top-center'});
                }
            });
        }
        //输入时间校验
        function checkDate() {
            var start_time = new Date($("#start_time").val()).valueOf();
            var end_time = new Date($("#end_time").val()).valueOf();
            if(start_time > end_time){
                UIkit.notify({message: '开始时间必须小于结束时间！', status: 'danger', timeout: 1500, pos: 'top-center'});
                this.value("");
            }
        }
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom"><c:if test="${empty entity.act_id}">新增</c:if><c:if test="${not empty entity.act_id}">编辑</c:if>活动</h3>
<div class="md-card">
    <div class="md-card-content large-padding" style="height: 780px;">
        <form:form id="validateSubmitForm" cssClass="uk-form-stacked">
            <input type="hidden" name="act_id" value="${entity.act_id}"/>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-1">
                    <div class="parsley-row">
                        <label class="uk-form-label">活动名称<span class="req">*</span></label>
                        <input name="act_name" id="act_name" value="${entity.act_name}" style="width: 20%;height: 35px;"/>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-1">
                    <div class="parsley-row">
                        <label class="uk-form-label">活动类型</label>
                        <select name="act_type" id="act_type" style="width: 20%;height: 35px;">
                            <option value="1" <c:if test="${entity.act_type==1}">selected</c:if>>直接打折</option>
                            <option value="2" <c:if test="${entity.act_type==2}">selected</c:if>>满减送优惠券</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-1">
                    <div class="parsley-row">
                        <label class="uk-form-label">活动开始时间</label>
                        <kendo:dateTimePicker name="start_time" value="${entity.start_time}" dateInput="true" style="width: 20%;height: 35px;" title="datetimepicker" change="checkDate"></kendo:dateTimePicker>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-1">
                    <div class="parsley-row">
                        <label class="uk-form-label">活动结束时间</label>
                        <kendo:dateTimePicker name="end_time" value="${entity.end_time}" dateInput="true" style="width: 20%;height: 35px;" title="datetimepicker" change="checkDate"></kendo:dateTimePicker>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-1">
                    <div class="parsley-row" id="phone_parent">
                        <label class="uk-form-label">活动人数</label>
                        <input name="act_count" id="act_count" value="${entity.act_count}" onkeyup='this.value=this.value.replace(/\D/gi,"")' style="width: 20%;height: 35px;"/>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-1">
                    <div class="uk-form-row parsley-row">
                        <label class="uk-form-label">活动描述</label>
                        <textarea class="md-input" name="act_desc" cols="10" rows="2" data-parsley-trigger="keyup"
                                  data-parsley-minlength="20" data-parsley-maxlength="100"
                                  data-parsley-validation-threshold="10"
                                  data-parsley-minlength-message="Come on! You need to enter at least a 20 caracters long comment..">${entity.act_desc}</textarea>
                    </div>
                </div>
            </div>
            <div class="uk-grid">
                <div class="uk-width-1-1">
                    <button type="button" class="md-btn md-btn-primary" onclick="save()">提交保存</button>
                    <a class="md-btn" href="activity.do">返回</a>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
