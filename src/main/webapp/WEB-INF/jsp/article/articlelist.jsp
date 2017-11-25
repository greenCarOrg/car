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
        function editItem() {
            var dataItem = getSelectedGridItem("grid");
            if (dataItem) {
                window.location.href = "toEditArticle.do?id=" + dataItem.id;
            }
        }
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">文章管理</h3>
<div style="padding :0 30px 10px 0;text-align: right">
    <a class="md-btn md-btn-primary" href="toAddArticle.do?explain=articledo">新增</a>
    <a class="md-btn md-btn-warning" href="javascript:editItem()">编辑</a>
</div>
<div>
    <select id="state" style="width:200px;height: 40px;">
        <option value="">--选择状态--</option>
        <option value="0">下架</option>
        <option value="1">上架</option>
    </select>
    <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
</div>
<kendo:grid name="grid" pageable="true" sortable="true" filterable="true" selectable="true" height="780" style="border-width:0px;">
    <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/><kendo:grid-columns>
        <kendo:grid-column title="编号" field="id" sortable="false" filterable="false"  width="90px" hidden="true"/>
        <kendo:grid-column title="标题" field="title" sortable="false" filterable="false"  width="90px"/>
        <kendo:grid-column title="内容" field="remark" sortable="false" filterable="false"  width="90px"/>
        <kendo:grid-column title="关键词" field="keywords" sortable="false" filterable="false"  width="90px"/>
        <kendo:grid-column title="链接" field="link" sortable="false" filterable="false" width="90px"/>
        <kendo:grid-column title="创建人" field="creator" width="80px" filterable="false"/>
        <kendo:grid-column title="点击量" field="click" sortable="false" filterable="false" width="90px"/>
        <kendo:grid-column title="作者" field="author" width="80px" filterable="false"/>
        <kendo:grid-column title="创建时间" field="createTime" width="90px" filterable="false"/>
        <kendo:grid-column title="发布时间" field="publishTime" sortable="false" filterable="false" width="90px"/>
        <kendo:grid-column title="状态" field="state" width="80px" filterable="false"/>
        <kendo:grid-column title="操作" width="110px">

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
                <kendo:grid-column-commandItem name="deleteArtic" text="删除" className="grid-button">
                    <kendo:grid-column-commandItem-click>
                        <script>
                            function deleteArtic(e) {
                                var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                                var state = dataItem.state;
                                UIkit.modal.confirm("确认删除吗?", function(){
                                    $.get('deleteArtic.do', {
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
    <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
        <kendo:dataSource-schema data="content" total="totalElements"></kendo:dataSource-schema>
        <kendo:dataSource-transport>
            <kendo:dataSource-transport-read url="articlelist.do" type="POST" contentType="application/json"/>
            <kendo:dataSource-transport-parameterMap>
                <script>
                    function parameterMap(options, type) {
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
</script>
</body>
</html>