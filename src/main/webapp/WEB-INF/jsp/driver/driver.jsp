<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<!doctype html>
<html class="lte-ie9" lang="en">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="msapplication-tap-highlight" content="no"/>
    <title>${initParam['system-name']}管理系统</title>
    <script type="application/javascript">
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe" id="bodyId">
<h3 class="heading_b uk-margin-bottom">司机管理</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-content" style="height: auto !important;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label >司机名字</label>
                            <input class="md-input" type="text" id="name" value="${name}"/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label >司机电话</label>
                            <input class="md-input" type="text" id="phone" value="${phone}"/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label style="display: inherit;">上架状态</label>
                            <select name="shelf" id="shelf">
                                <option value="">全部</option>
                                <option value="0" <c:if test="${shelf==0}">selected</c:if>>下架</option>
                                <option value="1" <c:if test="${shelf==1}">selected</c:if>>上架</option>
                            </select>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6" style="line-height: 56px;padding-top: 4px;">
                        <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                    </div>
                </div>
                <div style="height: 40px;">
                    <span class="icheck-inline" style="margin:10px 0px 0px 7px;">
                            <input type="checkbox" id="checkbox_all"  onclick="checkAll(this)"/>
                            <label for="checkbox_all"  class="inline-label inline-label-top">全选</label>
                    </span>
                    <a style="float: right;margin-left: 5px;" class="md-btn md-btn-primary" href="toEdit.do">添加月嫂</a>
                    <a style="float: right;margin-left: 5px;" class="md-btn md-btn-primary" href="javascript:batchShelf(0)">批量下架</a>
                    <a style="float: right;margin-left: 5px;" class="md-btn md-btn-primary" href="javascript:batchShelf(1)">批量上架</a>
                </div>
                <div style="clear: both;"></div>
                <div class="uk-grid">
                    <div class="uk-width-medium-1-1">
                        <kendo:grid name="grid" pageable="true" sortable="true" filterable="true" selectable="true" height="750" resizable="true" >
                            <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/>
                            <kendo:grid-filterable extra="true">
                                <kendo:grid-filterable-messages filter="查询" clear="清除" info="提示" and="并且" or="或者"/>
                                <kendo:grid-filterable-operators>
                                    <kendo:grid-filterable-operators-string contains="包含" eq="等于"/>
                                    <kendo:grid-filterable-operators-number eq="=" lte="<=" gte=">="/>
                                    <kendo:grid-filterable-operators-date eq="=" lte="早于" gte="晚于"/>
                                    <kendo:grid-filterable-operators-enums eq="等于" />
                                </kendo:grid-filterable-operators>
                            </kendo:grid-filterable>
                            <kendo:grid-columns>
                                <kendo:grid-column title="选择" width="3%" template="<input type='checkbox' id=#= id # name='check' value=#= id # data-md-icheck />"/>
                                <kendo:grid-column title="头像" field="head" width="6%" filterable="false">
                                    <kendo:grid-column-template>
                                        <script>
                                            function template(options) {
                                                var html="<img style='height: 100px;' src="+options.image_url+">";
                                                return html;
                                            }
                                        </script>
                                    </kendo:grid-column-template>
                                </kendo:grid-column>
                                <kendo:grid-column title="司机名字" field="name" width="7%" filterable="false"/>
                                <kendo:grid-column title="电话" field="phone" width="8%" filterable="false"/>
                                <kendo:grid-column title="类型" field="type" width="5%" filterable="false" template="#= type==0? '月嫂' : '育婴师' #"/>
                                <kendo:grid-column title="口碑" field="star_num" width="5%" filterable="false"/>
                                <kendo:grid-column title="服务价格" field="price" width="5%" filterable="false"/>
                                <kendo:grid-column title="年龄" field="age" width="5%" filterable="false"/>
                                <kendo:grid-column title="创建人" field="real_name" width="7%" filterable="false"/>
                                <kendo:grid-column title="创建时间" field="create_time" width="10%" filterable="false"/>
                                <kendo:grid-column title="推荐人" field="refer" width="7%" filterable="false"/>
                                <kendo:grid-column title="状态" field="state" width="5%" filterable="false" template="#= state==0 ? '未在职' : '已签约' #"/>
                                <kendo:grid-column title="上下架状态" field="shelf" width="5%" filterable="false" template="#= shelf==0 ? '下架' : '上架' #"/>
                                <kendo:grid-column title="操作" width="5%" filterable="false" >
                                    <kendo:grid-column-template>
                                        <script>
                                            function template(options) {
                                                var shelf=options.shelf==0?'上架':'下架';
                                                var html="<div style='text-align:center;margin:0 auto'>" +
                                                        "<a class='k-button k-button-icontext grid-button k-grid-user_id' href='toEdit.do?id="+options.id+"'>编辑</a>"+
                                                        "<a class='k-button k-button-icontext grid-button k-grid-user_id' href='javascript:setShelf([" + options.id + "],"+(options.shelf==0?1:0)+")'>"+shelf+"</a>"+
                                                        "</div>";
                                                return html;
                                            }
                                        </script>
                                    </kendo:grid-column-template>
                                </kendo:grid-column>
                            </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements">
                                </kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="page.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                options.name=$("#name").val();
                                                options.phone=$("#phone").val();
                                                options.type=$("#type").val();
                                                options.state=$("#state").val();
                                                options.shelf=$("#shelf").val();
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
<div class="configuration k-widget k-header" style="top:50%;bottom:50%;display: none;">
    <kendo:window name="popWindow" title="添加服务" style="overflow: hidden;" width="900" height="322" actions="<%=new String[] { \"Close\" } %>" close="closeWindow">
        <kendo:window-content>
            <form class="uk-form" id="saveForm" name="saveForm">
                <input id="mmId" type="hidden" name="mmId">
                <div data-uk-grid-margin>
                    <div class="uk-width-medium-1-1">
                        <div class="parsley-row" style="min-height:200px;">
                            <label class="uk-form-label">服务</label>
                            <div style="border: double 1px #dadad0;width: 98%; height: 100%;padding: 10px;min-height:180px;">
                                <ul id="serviceUl" style="list-style: none; width: 100%;height: auto;padding: 0px;min-height:180px;">
                                    <li style="width: 20%;min-width:140px ;float: left;">
                                        <input type="checkbox" name="subjectIds" value="1" id="checkbox_demo_1" data-md-icheck />
                                        <label for="checkbox_demo_1" class="inline-label">服务</label>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <div id="btnDiv" data-uk-grid-margin align="center" style="border: 0px solid green;margin: 30px auto 10px;">
                <button type="button" class="md-btn md-btn-primary" onclick="save()">保存</button>
                <button type="button" onclick="closeOperationWindow()" class="md-btn md-btn-primary">取消</button>
            </div>
        </kendo:window-content>
    </kendo:window>
</div>
<script type="application/javascript">
    //grid重新获取数据
    function reloadData(){
        $("#grid").data("kendoGrid").dataSource.read();
    }
    /**
     * 全选或全不选
     * @param e
     */
    function checkAll(e) {
        var checkAll=$(e).get(0).checked;
        $("input[name='check']").prop("checked",checkAll);
    }
    function removeItem(id){
        UIkit.modal.confirm("确认删除这个月嫂吗?", function(){
            $.get("delete.do", {
                id: id
            }, function (data) {
                if (data != 0) {
                    UIkit.notify({message: "删除成功", status: 'success', timeout: 1000, pos: 'top-center'});
                    reloadData();
                } else {
                    UIkit.notify({message: "该数据正在使用，不能删除", status: 'danger', timeout: 1000, pos: 'top-center'});
                }
            });
        },{labels:{Ok:"确认",Cancel:"取消"}});
    }
    function addService() {
        var dataItem = getSelectedGridItem("grid");
        if (dataItem!=null) {
            $("#mmId").val(dataItem.id);
            var role=dataItem.type==0?"月嫂":"育婴师";
            $.post("../driver/getMaternityService.do",{id:dataItem.id,type:dataItem.type},
                function(msg){
                    var resultCode=msg.resultCode;
                    if(resultCode == 'SUCCESS'){
                        var result=msg.result;
                        var html="";
                        for(var i = 0 ;i< result.length ; i++){
                            html += "<li style='width: 20%;float: left;'>" +
                                    "<input type='checkbox' name='serviceIds'  value='" + result[i].id + "' id='checkbox_demo_" + i + "'  "+(result[i].mm_id!=0?'checked':'')+" data-md-icheck />" +
                                    "<label for='checkbox_demo_" + i + "' id='serviceLabel_"+i+"' class='inline-label'>" + result[i].name + "</label>" +
                                    "</li>";
                        }
                        var ul=$("#serviceUl");
                        ul.empty();
                        ul.append(html);
                        $("#popWindow").data("kendoWindow").title(role+dataItem.name+"添加服务");
                        $("#popWindow").data("kendoWindow").open();
                        $("#popWindow").data("kendoWindow").center();
                    }else {
                        UIkit.notify({message: '请添加'+role+'服务后，在对用户分配服务!', status: 'danger', timeout: 1000, pos: 'top-center'});
                    }
                });
        }
    }
    function closeWindow() {
        $("#popWindow").show();
    }
    function closeOperationWindow() {
        $("#popWindow").data("kendoWindow").close();
    }
    function save() {
        var serviceIds=[];
        $("input[name='serviceIds']:checked").each(function(){
            serviceIds.push($(this).val());
        });
        if (serviceIds == null||serviceIds.length==0) {
            UIkit.notify({message: '请勾选服务!', status: 'warning', timeout: 1000, pos: 'top-center'});
        }else{
            $.post("../driver/addService.do",{mmId:$("#mmId").val(),serviceIds:serviceIds},
                function(msg){
                    var resultCode=msg.resultCode;
                    if(resultCode == 'SUCCESS'){
                        UIkit.notify({message: '分配服务成功!', status: 'success', timeout: 1000, pos: 'top-center'});
                        closeOperationWindow();
                    }else {
                        UIkit.notify({message: '请勾选服务!', status: 'danger', timeout: 1000, pos: 'top-center'});
                    }
                });
        }
    }
    /**
     * 批量上架或下架
     * @param shelf：1-上架；0-下架
     */
    function batchShelf(shelf) {
        var checks=$("input[name='check']:checked");
        var grid = $("#grid").data("kendoGrid").dataSource.view();
        if(StrUtil.isNotEmpty(checks)&&checks.length>0){
            var ids=new Array();
            for(var j=0;j<checks.length;j++){
                for(var i=0;i<grid.length;i++){
                    var obj=grid[i];
                    if ($(checks[j]).val()== obj.id) {
                        //当前月嫂状态和需要操作状态一致，则提示
                        if (obj.shelf==shelf) {
                            UIkit.notify({message: (obj.type==0?"月嫂":"育婴师")+"【"+obj.name+"】已经是"+(obj.shelf==0?"下":"上")+"架状态!", status: 'warning', timeout: 2000, pos: 'top-center'});
                            return;
                        }
                    }
                }
                ids.push($(checks[j]).val());
            }
            setShelf(ids,shelf);
        }
    }

    /**
     * @param ids：id
     * @param shelf：1-上架；0-下架
     */
    function setShelf(ids,shelf) {
        var shelfText=(shelf==0?"下":"上");
        UIkit.modal.confirm("确定"+shelfText+"架所选月嫂或育婴师？", function () {
            $.post('batchShelf.do', {
                ids: ids,
                shelf: shelf
            }, function (data) {
                var resultCode=data.resultCode;
                if (resultCode == 'SUCCESS') {
                    reloadData();
                    $("#checkbox_all").prop("checked",false);
                    UIkit.notify({
                        message: shelfText+'架成功',
                        status: 'success',
                        timeout: 3000,
                        pos: 'top-center'
                    });
                }else if(resultCode == 'FAIL') {
                    UIkit.notify({
                        message: data.message,
                        status: 'warning',
                        timeout: 3000,
                        pos: 'top-center'
                    });
                }else{
                    UIkit.notify({
                        message: data.message,
                        status: 'danger',
                        timeout: 3000,
                        pos: 'top-center'
                    });
                }
            });
        }, {labels: {Ok: "确认", Cancel: "取消"}});
    }
</script>
</body>
</html>