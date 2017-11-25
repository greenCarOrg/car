
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${initParam['system-name']}管理系统</title>
    <script>
        function editItem() {
            var dataItem = getSelectedGridItem("grid");
            if (dataItem) {
                window.location.href = "toEditGoodGroup.do?id=" + dataItem.id;
            }
        }

        function reloadData() {
            $("#grid").data("kendoGrid").dataSource.page(1);
            $("#grid").data("kendoGrid").dataSource.read();
        }
    </script>
</head>

<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">商品分组</h3>
<div id="mask" class="mask11"></div>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <input type="hidden" value="${pageContext.request.contextPath}" >
            <div style="padding :0 30px 10px 0;text-align: right">
                <a class="md-btn md-btn-primary" href="toAddGoodGroup.do">新增</a>
                <a class="md-btn md-btn-warning" href="javascript:editItem()">编辑</a>
            </div>

            <div class="md-card-content large-padding" style="height: 100%;">
                <div>
                    <select id="state" style="width:200px;height: 40px;">
                        <option value="">--选择状态--</option>
                        <option value="0">停用</option>
                        <option value="1">启用</option>
                    </select>
                    <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                </div>

                <div class="uk-grid">
                    <div class="uk-width-medium-1-1">
                        <kendo:grid name="grid" pageable="true" sortable="true" filterable="true" selectable="true" height="680" style="border-width:0px;">
                            <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/>
                            <kendo:grid-columns>
                                <kendo:grid-column title="编号" field="id" width="100px" filterable="false" hidden="true"/>
                                <kendo:grid-column title="分类名称" field="name" width="100px" filterable="false"/>
                                <kendo:grid-column title="上级分类" field="parent_name" width="100px" filterable="false"/>
                                <kendo:grid-column title="备注" field="remark" width="100px" filterable="false"/>
                                <kendo:grid-column title="排序" field="order_num" width="100px" filterable="false"/>
                                <kendo:grid-column title="状态" field="state" width="100px" template="#= state ? '启用' : '停用' #" filterable="false"/>
                                <kendo:grid-column title="是否为热门推荐" field="hot" width="100px" template="#= hot ? '是' : '否' #" filterable="false"/>
                                <kendo:grid-column title="操作" width="100px">
                                    <kendo:grid-column-command>
                                        <kendo:grid-column-commandItem name="enabledState" text="启用/停用" className="grid-button">
                                            <kendo:grid-column-commandItem-click>
                                                <script>
                                                    function enabledState(e) {
                                                        var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                                                        var state = dataItem.state;
                                                        UIkit.modal.confirm("确认"+ (state ? "停用" : "启用") +"吗?", function(){
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
                                    </kendo:grid-column-command>
                                </kendo:grid-column>
                            </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements"></kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="goodGroupList.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                options.isparent=0;
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
</body>
</html>