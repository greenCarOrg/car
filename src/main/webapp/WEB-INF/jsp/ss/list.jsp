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
    <title>妈妈有约管理系统</title>
    <link rel="stylesheet" href="${ctx}/css/common/list.css" />
</head>
<body class="sidebar_main_open sidebar_main_swipe" id="bodyId">
<h3 class="heading_b uk-margin-bottom">专家服务管理</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-content" style="height: auto !important;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label >服务名字</label>
                            <input class="md-input" type="text" id="name" value="${name}"/>
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
                    <a style="float: right;margin-left: 5px;" class="md-btn md-btn-primary" href="toEdit.do">添加专家服务</a>
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
                                <kendo:grid-column title="专家服务名称" field="name" width="8%" filterable="false"/>
                                <kendo:grid-column title="服务专家" field="s_name" width="6%" filterable="false"/>
                                <kendo:grid-column title="服务价格" field="price" width="6%" filterable="false"/>
                                <kendo:grid-column title="服务封面" filterable="false" width="6%">
                                    <kendo:grid-column-template>
                                        <script>
                                            function template(options) {
                                                var html="<img style='height: 130px;width:130px;'  onclick='showImg(this)' src="+getQNUrl()+options.img+">";
                                                return html;
                                            }
                                        </script>
                                    </kendo:grid-column-template>
                                </kendo:grid-column>
                                <kendo:grid-column title="创建人" field="real_name" width="6%" filterable="false"/>
                                <kendo:grid-column title="创建时间" field="create_time" width="6%" filterable="false"/>
                                <kendo:grid-column title="上下架状态" field="shelf" width="5%" filterable="false" template="#= shelf==0 ? '下架' : '上架' #"/>
                                <kendo:grid-column title="操作" width="8%" filterable="false" >
                                    <kendo:grid-column-template>
                                        <script>
                                            function template(options) {
                                                var shelf=options.shelf==0?'上架':'下架';
                                                var html="<div style='text-align:center;margin:0 auto'>" +
                                                        "<a class='k-button k-button-icontext grid-button k-grid-user_id' href='toEdit.do?id="+options.id+"'>编辑<a/>"+
                                                        "<a class='k-button k-button-icontext grid-button k-grid-user_id' href=javascript:removeItem("+options.id+");>删除<a/>"+
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
                                    <kendo:dataSource-transport-read url="list.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                options.name=$("#name").val();
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
<div class="motai" id="mo">
    <span class="close" id="close" onclick="hideImg()">×</span>
    <img class="motaiimg" id="moimg">
    <div id="caption"></div>
</div>
<script type="application/javascript">
    //grid重新获取数据
    function reloadData(){
        $("#grid").data("kendoGrid").dataSource.read();
    }
    function removeItem(id){
        UIkit.modal.confirm("确认删除这个专家服务吗?", function(){
            $.get("../common/softDelete.do",
                {
                    id: id,
                    tn:"specialist_service",
                    state:0
                },
                function (data) {
                if (data != 0) {
                    UIkit.notify({message: "删除成功", status: 'success', timeout: 1000, pos: 'top-center'});
                    reloadData();
                } else {
                    UIkit.notify({message: "该数据正在使用，不能删除", status: 'danger', timeout: 1000, pos: 'top-center'});
                }
            });
        },{labels:{Ok:"确认",Cancel:"取消"}});
    }
    function showImg(e){
        var item = $("#grid").data("kendoGrid").dataItem($($(e).parent()).closest("tr"));
        var motai=document.getElementById('mo');
        var moimg=document.getElementById("moimg");
        motai.style.display="block"
        moimg.src=getQNUrl()+item.img;
    }
    function hideImg(){
        var motai=document.getElementById('mo');
        motai.style.display="none";
    }
    /**
     * 全选或全不选
     * @param e
     */
    function checkAll(e) {
        var checkAll=$(e).get(0).checked;
        $("input[name='check']").prop("checked",checkAll);
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
                        //当前专家状态和需要操作状态一致，则提示
                        if (obj.shelf==shelf) {
                            UIkit.notify({message:"专家【"+obj.name+"】已经是"+(obj.shelf==0?"下":"上")+"架状态!", status: 'warning', timeout: 2000, pos: 'top-center'});
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
        UIkit.modal.confirm("确定"+shelfText+"架所选专家服务？", function () {
            $.post('../common/batchShelf.do', {
                tn:"specialist_service",
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