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
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">默认配置</h3>
<kendo:grid name="grid" pageable="true" sortable="true" filterable="true" selectable="true" height="680" style="border-width:0px;">
    <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/><kendo:grid-columns>
    <kendo:grid-column title="编号" field="id" width="100px" filterable="false"/>
    <kendo:grid-column title="配置项" field="code" width="100px" filterable="false"/>
    <kendo:grid-column title="列值" field="value" width="100px" filterable="false"/>
    <kendo:grid-column title="备注" field="remark" width="100px" filterable="false"/>
    <kendo:grid-column title="更新人" field="updator" width="100px" filterable="false"/>
    <kendo:grid-column title="更新时间" field="updateTime" width="100px" filterable="false"/>
    <kendo:grid-column title="操作" width="10%" filterable="false">
        <kendo:grid-column-template>
            <script>
                function template(row) {
                    var html  = "<div style='text-align:center;margin:0 auto'>" +
                            "<a  style='color: #6a0d6a'  class='k-button k-button-icontext grid-button k-grid-user_id' onclick='editCode(this)'>修改&nbsp;&nbsp;&nbsp;</a></div>";
                    return html;
                }
            </script>
        </kendo:grid-column-template>
    </kendo:grid-column>
</kendo:grid-columns>
    <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
        <kendo:dataSource-schema data="content" total="totalElements"></kendo:dataSource-schema>
        <kendo:dataSource-transport>
            <kendo:dataSource-transport-read url="codeList.do" type="POST" contentType="application/json"/>
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

<div class="configuration k-widget k-header" style="top:40%;bottom:50%;display: none;">
    <kendo:window name="updateValuie" title="修改默认配置参数" style="overflow: hidden;"  width="550" height="280" >
        <kendo:window-content>
            <form class="uk-form" id="saveForm">
                <input type="hidden" id="id" value="0">
                <div data-uk-grid-margin>
                    <div style="padding-top: 20px;">
                        <label >配置项目：</label>
                        <span class="icheck-inline" style="width: 250px;"><input type="text"  id="code" readonly="readonly" style="width: 255px;"/></span>
                    </div>
                    <div>
                        <label>设置数值：</label>
                        <span class="icheck-inline" style="width: 250px;"><input type="text" name="value" id="value" value="" style="width: 260px;" /></span>
                    </div>
                </div>
            </form>
            <div id="btnDiv" data-uk-grid-margin align="center" style="border: 0px solid green;margin: 30px auto 10px;">
                <button type="button" class="md-btn md-btn-primary" onclick="saveNew()">保存</button>
                <button type="button" class="md-btn md-btn-primary" onclick="closeWindow()">取消</button>
            </div>
        </kendo:window-content>
    </kendo:window>
</div>
<script type="application/javascript">
    function editCode(e){
        var item = $("#grid").data("kendoGrid").dataItem($($(e).parent()).closest("tr"));
        $("#code").val(item.remark);
        $("#code").css('border', '0px solid #04ae00');
        $("#value").val(item.value);
        $("#id").val(item.id);
        $("#updateValuie").data("kendoWindow").open();
        $("#updateValuie").data("kendoWindow").center();
    }
    function closeWindow() {
        $("#updateValuie").data("kendoWindow").close();
        $("#grid").data("kendoGrid").dataSource.read();
    }

    function saveNew(){
        var id=$("#id").val();
        var value=$("#value").val();
        if(value=='' || value==undefined || value=='' || isNaN(value)){
            UIkit.notify({message: '请输入有效数值', status: 'danger', timeout: 3000, pos: 'top-center'});
            return;
        }
        $.get('updateCode.do', {
            id: id,
            value: value
        }, function (data) {
            if (data > 0) {
                $("#grid").data("kendoGrid").dataSource.read();
                UIkit.notify({message: '操作成功', status: 'success', timeout: 3000, pos: 'top-center'});
            } else {
                UIkit.notify({message: '操作失败', status: 'danger', timeout: 3000, pos: 'top-center'});
            }
        });
    }
</script>
</body>
</html>