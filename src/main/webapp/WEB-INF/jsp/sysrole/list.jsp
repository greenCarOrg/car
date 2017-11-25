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
    <title>妈妈有约管理系统</title>
</head>

<body class="sidebar_main_open sidebar_main_swipe" id="bodyId">
<h3 class="heading_b uk-margin-bottom">角色列表</h3>
<div style="padding :0 30px 10px 0;text-align: right">
    <a class="md-btn md-btn-primary" href="toAddSysRole.do?explain=sysroledo">新增角色</a>
    <a class="md-btn md-btn-warning" href="javascript:editRoleI()">编辑角色</a>
    <script type="application/javascript">
        function editRoleI(){
            var dataItem = getSelectedGridItem("grid");
            if(dataItem.status==1){
                window.location.href = "userRoleFunc.do?explain=sysroledo&id=" + dataItem.id;
            }else{
                UIkit.notify({message: '该角色已经停用，不能操作', status: 'danger', timeout: 3000, pos: 'top-center'});
            }
        };
    </script>
</div>
<kendo:grid name="grid" pageable="true" sortable="true" selectable="true" height="600" resizable="true" >
<kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/>
<kendo:grid-columns>
<kendo:grid-column title="角色名" field="roleName" width="100px" filterable="false"/>
<kendo:grid-column title="状态" width="100px" field="strStatus" />
<kendo:grid-column title="创建时间" field="createTimeStr" width="200px" />
<kendo:grid-column title="更新时间" field="updateTimeStr" width="180px"/>
<kendo:grid-column title="最后操作者" field="lastOperator" width="180px"/>
<kendo:grid-column title="操作" width="90px">
<kendo:grid-column-command>
    <kendo:grid-column-commandItem name="enabledSysRole" text="启用/停用" className="grid-button">
        <kendo:grid-column-commandItem-click>
            <script>
                function enabledSysRole(e) {
                    var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                    var enabled = dataItem.status;
                    UIkit.modal.confirm("确认"+ (enabled ? "停用" : "启用") +"吗?", function(){
                        $.get('enabledSysRole.do', {
                            id: dataItem.id,
                            enabled: !enabled
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
                                <kendo:dataSource-schema data="content" total="totalElements">
                                    <kendo:dataSource-schema-model>
                                        <kendo:dataSource-schema-model-fields>

                                        </kendo:dataSource-schema-model-fields>
                                    </kendo:dataSource-schema-model>
                                </kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="sysRolelist.do" type="POST" contentType="application/json"/>
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