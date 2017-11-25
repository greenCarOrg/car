<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<!doctype html>
<html class="lte-ie9" lang="en">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="msapplication-tap-highlight" content="no"/>
    <title>妈妈有约管理系统</title>
    <script type="application/javascript">
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe" id="bodyId">
<h3 class="heading_b uk-margin-bottom">活动管理</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-content" style="height: auto !important;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label >活动名称</label>
                            <input class="md-input" type="text" id="name" value="${name}"/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label style="display: inherit;">活动类型</label>
                            <select name="type" id="type">
                                <option value="">全部</option>
                                <option value="1" <c:if test="${entity.type==1}">selected</c:if>>直接打折</option>
                                <option value="2" <c:if test="${entity.type==2}">selected</c:if>>满减送优惠券</option>
                            </select>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label style="display: inherit;">活动开始时间</label>
                            <kendo:dateTimePicker name="start_time" value="${entity.start_time}" dateInput="true" title="datetimepicker" change="checkDate"></kendo:dateTimePicker>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label style="display: inherit;">活动结束时间</label>
                            <kendo:dateTimePicker name="end_time" value="${entity.end_time}" dateInput="true" title="datetimepicker" change="checkDate"></kendo:dateTimePicker>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6" style="line-height: 56px;padding-top: 4px;">
                        <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                    </div>
                </div>
                <div style="padding :0 30px 10px 0;text-align: right">
                    <a class="md-btn md-btn-primary" href="toEdit.do">新增</a>
                </div>
                <div class="uk-grid">
                    <div class="uk-width-medium-1-1">
                        <kendo:grid name="grid" pageable="true" sortable="true" filterable="true" selectable="true" height="750" resizable="true" >
                            <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/>
                            <kendo:grid-filterable extra="true">
                                <kendo:grid-filterable-messages filter="查询" clear="清除" info="提示" and="并且" or="或者"/>
                                <kendo:grid-filterable-operators>
                                    <kendo:grid-filterable-operators-string contains="包含" eq="等于"/>
                                    <kendo:grid-filterable-operators-number eq="=" lte="<=" gte=">="/>
                                    <kendo:grid-filterable-operators-date eq="=" lte="早于" gte="晚于"/>
                                    <kendo:grid-filterable-operators-enums eq="等于" />
                                </kendo:grid-filterable-operators>
                            </kendo:grid-filterable>
                            <kendo:grid-columns>
                                <kendo:grid-column title="活动名称" field="name" width="10%" filterable="false"/>
                                <kendo:grid-column title="活动类型" field="type" width="10%" filterable="false"/>
                                <kendo:grid-column title="活动开始时间" field="start_time" width="8%" filterable="false"/>
                                <kendo:grid-column title="活动结束时间" field="end_time" width="8%" filterable="false"/>
                                <kendo:grid-column title="活动人数" field="count" width="8%" filterable="false"/>
                                <kendo:grid-column title="活动描述" field="remark" width="8%" filterable="false"/>
                                <kendo:grid-column title="操作" width="23%" filterable="false" >
                                    <kendo:grid-column-template>
                                        <script>
                                            function template(options) {
                                                var html="<div style='text-align:center;margin:0 auto'>" +
                                                        "<a class='k-button k-button-icontext grid-button k-grid-user_id' href='toEdit.do?id="+options.id+"'>编辑<a/>"+
                                                        "<a class='k-button k-button-icontext grid-button k-grid-user_id' href=javascript:removeItem("+options.id+");>删除<a/>"+
                                                        "</div>";
                                                return html;
                                            }
                                        </script>
                                    </kendo:grid-column-template>
                                </kendo:grid-column>
                            </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements">
                                </kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="page.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                var start_time=$("#start_time").data("kendoDateTimePicker").value();
                                                var end_time=$("#end_time").data("kendoDateTimePicker").value();
                                                if(StrUtil.isNotEmpty(start_time)){
                                                    start_time=start_time.Format("yyyy-MM-dd hh:mm:ss");
                                                }
                                                if(StrUtil.isNotEmpty(end_time)){
                                                    end_time=end_time.Format("yyyy-MM-dd hh:mm:ss");
                                                }
                                                options.name=$("#name").val();
                                                options.type=$("#type").val();
                                                options.start_time=start_time;
                                                options.end_time=end_time;
                                                return JSON.stringify(options);
                                            }
                                        </script>
                                    </kendo:dataSource-transport-parameterMap>
                                </kendo:dataSource-transport>
                            </kendo:dataSource>
                        </kendo:grid>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="application/javascript">
    //输入时间校验
    function checkDate() {
        var start_time = new Date($("#start_time").val()).valueOf();
        var end_time = new Date($("#end_time").val()).valueOf();
        if(start_time > end_time){
            UIkit.notify({message: '开始时间必须小于结束时间！', status: 'danger', timeout: 1500, pos: 'top-center'});
            this.value("");
        }
    }
    //grid重新获取数据
    function reloadData(){
        $("#grid").data("kendoGrid").dataSource.read();
    }
    function removeItem(id){
        UIkit.modal.confirm("确认删除这个供应商吗?", function(){
            $.get("delete.do", {
                id: id
            }, function (data) {
                if (data != 0) {
                    UIkit.notify({message: "删除成功", status: 'success', timeout: 1000, pos: 'top-center'});
                    reloadData();
                } else {
                    UIkit.notify({message: "该数据正在使用，不能删除", status: 'danger', timeout: 1000, pos: 'top-center'});
                }
            });
        },{labels:{Ok:"确认",Cancel:"取消"}});
    }
</script>
</body>
</html>