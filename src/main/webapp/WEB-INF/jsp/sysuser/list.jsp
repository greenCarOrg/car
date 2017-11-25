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

<h3 class="heading_b uk-margin-bottom">用户列表</h3>
<div style="padding :0 30px 10px 0;text-align: right">
    <a class="md-btn md-btn-primary" href="toAddSysUser.do?explain=sysuserdo">新增用户</a>
    <a class="md-btn md-btn-warning" href="javascript:editUserI()">编辑用户</a>
    <script language="javascript">
        function editUserI(){
            var dataItem = getSelectedGridItem("grid");
            if (dataItem) {
                var userName = dataItem.userName;
                window.location.href = "toEditSysUserPage.do?userName=" + userName;
            }
        };
    </script>
</div>
<kendo:grid name="grid" pageable="true" sortable="true" selectable="true" height="600" resizable="true" >
    <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/>
    <kendo:grid-columns>
        <kendo:grid-column title="用户名" field="userName" width="100px" filterable="false"/>
        <kendo:grid-column title="真实姓名" width="100px" field="realName" />
        <kendo:grid-column title="手机号码" width="100px" field="phone" />
        <kendo:grid-column title="角色" field="roleName" width="150px" />
        <kendo:grid-column title="状态" field="status" width="80px" template="#= status ? '启用' : '停用' #" filterable="false"/>
        <kendo:grid-column title="创建时间" field="createTime" width="150px" />
        <kendo:grid-column title="最后登录IP" field="lastLoginIp" width="150px"/>
        <kendo:grid-column title="最后登录时间" field="lastLoginTime" width="150px"/>
        <kendo:grid-column title="最后操作者" field="lastOperator" width="150px"/>
        <kendo:grid-column title="操作" width="90px">
            <kendo:grid-column-command>
                <kendo:grid-column-commandItem name="enabledSysUser" text="启用/停用" className="grid-button">
                    <kendo:grid-column-commandItem-click>
                        <script>
                            function enabledSysUser(e) {
                                var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                                var enabled = dataItem.status;
                                UIkit.modal.confirm("确认"+ (enabled ? "停用" : "启用") +"吗?", function(){
                                    $.get('enabledSysUser.do', {
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
            <kendo:dataSource-transport-read url="sysUserlist.do" type="POST" contentType="application/json"/>
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