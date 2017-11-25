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
<h3 class="heading_b uk-margin-bottom">短信日志管理</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-content" style="height: auto !important;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label>电话号码</label>
                            <input class="md-input" type="text" id="phone_number" value=""/>
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
                            <kendo:grid-column title="编号" field="id" hidden="hidden" sortable="false" filterable="false"  width="90px"/>
                            <kendo:grid-column title="手机号码" field="phone_number" sortable="false" filterable="false"  width="90px"/>
                            <kendo:grid-column title="发送时间" field="create_time" sortable="false" filterable="false"  width="90px"/>
                            <kendo:grid-column title="验证码" field="verify_code" sortable="false" filterable="false"  width="90px"/>
                            <kendo:grid-column title="返回结果" field="return_code" sortable="false" filterable="false"  width="90px"/>
                            <kendo:grid-column title="操作类型" field="name" sortable="false" filterable="false"  width="90px"/>
                        </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements"></kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="smsLogList.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                options.phone_number=$("#phone_number").val();
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