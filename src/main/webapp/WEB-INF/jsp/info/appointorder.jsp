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
<h3 class="heading_b uk-margin-bottom">预约订单管理</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-content" style="height: auto !important;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label>月嫂号码</label>
                            <input class="md-input" type="text" id="phone" value=""/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label>月嫂姓名</label>
                            <input class="md-input" type="text" id="name" value=""/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label style="display: inherit;">订单状态</label>
                            <select id="state">
                                <option value="">全部</option>
                                <option value="1">已下单</option>
                                <option value="2">用户已取消</option>
                                <option value="3">月嫂取消预约</option>
                                <option value="4">订单完成</option>
                            </select>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6" style="line-height: 56px;padding-top: 4px;">
                        <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                    </div>
                </div>
                <div class="uk-grid">
                    <div class="uk-width-medium-1-1">
                        <kendo:grid name="grid" pageable="true" sortable="true" filterable="true" selectable="true" height="680" style="border-width:0px;">
                            <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/><kendo:grid-columns>
                            <kendo:grid-column title="编号" field="id" hidden="hidden" sortable="false" filterable="false"  width="50px"/>
                            <kendo:grid-column title="用户昵称" field="user_name" sortable="false" filterable="false"  width="90px"/>
                            <kendo:grid-column title="用户号码" field="phone" sortable="false" filterable="false"  width="90px"/>
                            <kendo:grid-column title="月嫂姓名" field="mname" sortable="false" filterable="false"  width="90px"/>
                            <kendo:grid-column title="月嫂电话" field="mphone" sortable="false" filterable="false"  width="90px"/>
                            <kendo:grid-column title="下单时间" field="create_time" sortable="false" filterable="false"  width="90px"/>
                            <kendo:grid-column title="预约时间" field="appoint_time" sortable="false" filterable="false"  width="90px"/>
                            <kendo:grid-column title="时间段" field="type" sortable="false" filterable="false"  width="90px"/>
                            <kendo:grid-column title="预约地址" field="address" sortable="false" filterable="false"  width="90px"/>
                            <kendo:grid-column title="备注" field="remark" sortable="false" filterable="false"  width="90px"/>
                            <kendo:grid-column title="订单状态" field="state" sortable="false" filterable="false"  width="90px"/>
                            <kendo:grid-column title="缴纳押金" field="bond" sortable="false" filterable="false"  width="90px"/>
                        </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements"></kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="appointOrderList.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                options.phone=$("#phone").val();
                                                options.name=$("#name").val();
                                                options.state=$("#state").val();
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
    function reloadData() {
        $("#grid").data("kendoGrid").dataSource.read();
    }
</script>
</body>
</html>