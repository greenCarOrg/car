<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="lte-ie9" lang="en">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="msapplication-tap-highlight" content="no"/>
    <title>${initParam['system-name']}管理系统</title>
</head>

<body class="sidebar_main_open sidebar_main_swipe" id="bodyId">
<h3 class="heading_b uk-margin-bottom">提现申请</h3>

<div>
    <select id="state" style="width:200px;height: 40px;">
        <option value="">--选择状态--</option>
        <option value="1">申请中</option>
        <option value="2">提现成功</option>
        <option value="3">提现失败</option>
    </select>
    <select id="type" style="width:200px;height: 40px;">
        <option value="">--选择提现方式--</option>
        <option value="1">支付宝</option>
        <option value="2">微信</option>
        <option value="3">银行</option>
    </select>
    <a class="md-btn md-btn-primary" href="javascript:reloadData();" >查询</a>
</div>
<kendo:grid name="grid" pageable="true" sortable="true" filterable="true" selectable="true" height="680" style="border-width:0px;">
    <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/>
    <kendo:grid-columns>
        <kendo:grid-column title="编号" field="id" width="10%" filterable="false" hidden="hidden"/>
        <kendo:grid-column title="会员昵称" width="10%" field="nickname" filterable="false"/>
        <kendo:grid-column title="提现金额" width="10%" field="money" filterable="false"/>
        <kendo:grid-column title="账户余额" width="10%" field="user_money" filterable="false"/>
        <kendo:grid-column title="冻结金额" width="10%" field="frozen_money" filterable="false"/>
        <kendo:grid-column title="提现方式" field="type" width="10%" filterable="false"/>
        <kendo:grid-column title="提现账户" field="account_name" width="10%" filterable="false"/>
        <kendo:grid-column title="申请时间" field="update_time" width="10%" filterable="false"/>
        <kendo:grid-column title="更新时间" field="update_time" width="10%" filterable="false"/>
        <kendo:grid-column title="提现状态" field="state" width="10%" filterable="false"/>
        <kendo:grid-column title="经办人员" field="real_name" width="10%" filterable="false"/>
        <kendo:grid-column title="操作" width="10%">
            <kendo:grid-column-template>
                <script>
                    function template(row) {
                        var html  = "<div style='text-align:center;margin:0 auto'>" +
                                "<a  style='color: #6a0d6a'  class='k-button k-button-icontext grid-button k-grid-user_id' onclick='editCode(this)'>审核</a></div>";
                        return html;
                    }
                </script>
            </kendo:grid-column-template>
        </kendo:grid-column>
    </kendo:grid-columns>
    <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
        <kendo:dataSource-schema data="content" total="totalElements"></kendo:dataSource-schema>
        <kendo:dataSource-transport>
            <kendo:dataSource-transport-read url="withdrawList.do" type="POST" contentType="application/json"/>
            <kendo:dataSource-transport-parameterMap>
                <script>
                    function parameterMap(options, type) {
                        options.type=$("#type").val();
                        options.state=$("#state").val();
                        return JSON.stringify(options);
                    }
                </script>
            </kendo:dataSource-transport-parameterMap>
        </kendo:dataSource-transport>
    </kendo:dataSource>
</kendo:grid>

<script type="application/javascript">
    function reloadData() {
        $("#grid").data("kendoGrid").dataSource.read();
    }


    function editCode(e){
        var item = $("#grid").data("kendoGrid").dataItem($($(e).parent()).closest("tr"));
        var state=item.state;
        if(state=='申请中'){
            UIkit.modal.confirm("确认审核提现成功吗?", function(){
                $.get('enabledState.do', {
                    id: item.id,
                    state: 2
                }, function (data) {
                    if (data > 0) {
                        $("#grid").data("kendoGrid").dataSource.read();
                        UIkit.notify({message: '操作成功', status: 'success', timeout: 3000, pos: 'top-center'});
                    } else {
                        UIkit.notify({message: '操作失败', status: 'danger', timeout: 3000, pos: 'top-center'});
                    }
                });
            },{labels:{Ok:"确认",Cancel:"取消"}});
        }else if(state=="提现成功"){
            $("#grid").data("kendoGrid").dataSource.read();
            UIkit.notify({message: '你已经提现成功了哦', status: 'success', timeout: 3000, pos: 'top-center'});
        }else if(state=='提现失败'){
            UIkit.modal.confirm("确认审核提现成功吗?", function(){
                $.get('enabledState.do', {
                    id: item.id,
                    state: 2
                }, function (data) {
                    if (data > 0) {
                        $("#grid").data("kendoGrid").dataSource.read();
                        UIkit.notify({message: '操作成功', status: 'success', timeout: 3000, pos: 'top-center'});
                    } else {
                        UIkit.notify({message: '操作失败', status: 'danger', timeout: 3000, pos: 'top-center'});
                    }
                });
            },{labels:{Ok:"确认",Cancel:"取消"}});
        }
    }
</script>
</body>
</html>