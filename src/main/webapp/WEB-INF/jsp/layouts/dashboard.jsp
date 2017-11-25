<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="msapplication-tap-highlight" content="no"/>
    <title>${initParam['system-name']}管理系统</title>
</head>
<body id="body">
<h3 class="heading_b uk-margin-bottom">首页</h3>
<div class="uk-grid uk-grid-small" data-uk-grid-margin data-uk-grid-match="{target:'.md-card'}">
    <div class="uk-width-medium-2-3" style="padding-bottom: 5px;">
        <div class="md-card">
            <div class="md-card-toolbar">
                <h3 class="md-card-toolbar-heading-text">
                    新增订单
                </h3>
            </div>
            <div class="md-card-content" style="padding: 0px;">
                <kendo:grid name="grid" pageable="true" height="590px;" sortable="true"  filterable="true" selectable="true">
                    <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/><kendo:grid-columns>
                    <kendo:grid-column title="编号" field="id" sortable="false" filterable="false"  width="90px" hidden="true"/>
                    <kendo:grid-column title="标题" field="title" sortable="false" filterable="false"  width="90px"/>
                    <kendo:grid-column title="内容" field="remark" sortable="false" filterable="false"  width="120px"/>
                    <kendo:grid-column title="链接" field="link" sortable="false" filterable="false" width="90px"/>
                    <kendo:grid-column title="操作" width="90px">
                        <kendo:grid-column-command>
                            <kendo:grid-column-commandItem name="enabledState" text="上架/下架" className="grid-button">
                                <kendo:grid-column-commandItem-click>
                                    <script>
                                        function enabledState(e) {
                                            var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                                            var state = dataItem.state;
                                            UIkit.modal.confirm("确认"+ (state ? "上架" : "下架") +"吗?", function(){
                                                $.get('enabledState.do', {
                                                    id: dataItem.id,
                                                    state: !state
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
                                    </script>
                                </kendo:grid-column-commandItem-click>
                            </kendo:grid-column-commandItem>
                            <kendo:grid-column-commandItem name="delete" text="删除" className="grid-button">
                                <kendo:grid-column-commandItem-click>
                                    <script>
                                        function deleteAdvertisement(e) {
                                            var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                                            UIkit.modal.confirm("确认删除吗?", function(){
                                                $.get('deleteAdvertisement.do', {
                                                    id: dataItem.id
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
                                    </script>
                                </kendo:grid-column-commandItem-click>
                            </kendo:grid-column-commandItem>
                        </kendo:grid-column-command>
                    </kendo:grid-column>
                </kendo:grid-columns>
                    <kendo:dataSource pageSize="5" serverPaging="true" serverFiltering="true" serverSorting="true">
                        <kendo:dataSource-schema data="content" total="totalElements"></kendo:dataSource-schema>
                        <kendo:dataSource-transport>
                            <kendo:dataSource-transport-read url="adList.do" type="POST" contentType="application/json"/>
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
            </div>
        </div>
    </div>
    <div class="uk-width-medium-1-3">
        <div class="md-card">
            <div class="md-card-toolbar">
                <h3 class="md-card-toolbar-heading-text">
                    新增会员
                </h3>
            </div>
            <div class="md-card">
                <kendo:grid name="grid1" pageable="true" sortable="true" height="590px" filterable="true" selectable="true" >
                    <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/><kendo:grid-columns>
                    <kendo:grid-column title="编号" field="id" sortable="false" filterable="false"  width="90px" hidden="true"/>
                    <kendo:grid-column title="标题" field="title" sortable="false" filterable="false"  width="90px"/>
                    <kendo:grid-column title="链接" field="link" sortable="false" filterable="false" width="90px"/>
                    <kendo:grid-column title="状态" field="state" width="80px" template="#= state ? '上架' : '下架' #" filterable="false"/>
                </kendo:grid-columns>
                    <kendo:dataSource pageSize="5" serverPaging="true" serverFiltering="true" serverSorting="true">
                        <kendo:dataSource-schema data="content" total="totalElements"></kendo:dataSource-schema>
                        <kendo:dataSource-transport>
                            <kendo:dataSource-transport-read url="adList.do" type="POST" contentType="application/json"/>
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
            </div>
        </div>
    </div>
</div>
</body>
</html>
