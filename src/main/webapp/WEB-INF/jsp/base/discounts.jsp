<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script>
        function removeItem() {
            var dataItem = getSelectedGridItem("grid");
            if (dataItem) {
                if (confirm("确定删除吗？")) {
                    var concentId = dataItem.concentId;
                    $.get("removeDiscount.do", {
                        discountId: dataItem.discountId,
                        rad: Math.random()
                    }, function (data) {
                        if (data != 0) {
                            UIkit.notify({message: "删除成功", status: 'success', timeout: 1000, pos: 'top-center'});
                            $("#grid").data("kendoGrid").dataSource.read();
                        } else {
                            UIkit.notify({message: "该数据正在使用，不能删除", status: 'danger', timeout: 1000, pos: 'top-center'});
                        }
                    });
                }
            }
        }

        function editItem() {
            var dataItem = getSelectedGridItem("grid");
            if (dataItem) {
                window.location.href = "toEditDiscount.do?discountId=" + dataItem.discountId;
            }
        }
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">套餐管理</h3>

<div style="padding :0 30px 10px 0;text-align: right">
    <a class="md-btn md-btn-primary" href="toAddDiscount.do">新增套餐</a>
    <a class="md-btn md-btn-warning" href="javascript:editItem()">编辑</a>
    <a class="md-btn md-btn-danger" href="javascript:removeItem();">删除</a>
</div>
<kendo:grid name="grid" pageable="true" sortable="true" filterable="true" selectable="true" height="680"
            style="border-width:0px;">
    <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="10"/>
    <kendo:grid-filterable extra="true">
        <kendo:grid-filterable-messages filter="查询" clear="清除" info="提示" and="并且" or="或者"/>
        <kendo:grid-filterable-operators>
            <kendo:grid-filterable-operators-string contains="包含" eq="等于"/>
            <kendo:grid-filterable-operators-number eq="=" lte="<=" gte=">="/>
            <kendo:grid-filterable-operators-date eq="=" lte="早于" gte="晚于"/>
            <kendo:grid-filterable-operators-enums eq="等于"/>
        </kendo:grid-filterable-operators>
    </kendo:grid-filterable>
    <kendo:grid-columns>
        <kendo:grid-column title="套餐名称" field="discountName" width="40%"/>
        <kendo:grid-column title="开始时间" field="beginDate" width="40%"/>
        <kendo:grid-column title="截止时间" field="endDate" width="40%"/>
        <kendo:grid-column title="主课程名称" field="mainCourseName" width="40%"/>
        <kendo:grid-column title="上课组合方式" field="classTime" width="40%"/>
        <kendo:grid-column title="副课程名称" field="secondCourseName" width="40%"/>
        <kendo:grid-column title="优惠课时" field="giveCount" width="40%"/>
        <kendo:grid-column title="课时原价" field="unitPrice"   width="40%"/>
        <kendo:grid-column title="课时优惠价" field="discountPrice"  width="40%"/>
    </kendo:grid-columns>
    <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
        <kendo:dataSource-schema data="content" total="totalElements">
            <kendo:dataSource-schema-model>
                <kendo:dataSource-schema-model-fields>
                    <kendo:dataSource-schema-model-field name="discountName" type="string"/>
                    <kendo:dataSource-schema-model-field name="unitPrice" type="number"/>
                    <kendo:dataSource-schema-model-field name="discountPrice" type="number"/>
                    <kendo:dataSource-schema-model-field name="discountPrice" type="giveCount"/>
                </kendo:dataSource-schema-model-fields>
            </kendo:dataSource-schema-model>
        </kendo:dataSource-schema>
        <kendo:dataSource-transport>
            <kendo:dataSource-transport-read url="pagedDiscounts.do" type="POST" contentType="application/json"/>
            <kendo:dataSource-transport-parameterMap>
                <script>
                    function parameterMap(options, type) {
                        return JSON.stringify(options);
                    }
                </script>
            </kendo:dataSource-transport-parameterMap>
        </kendo:dataSource-transport>
    </kendo:dataSource>
</kendo:grid>
</body>
</html>