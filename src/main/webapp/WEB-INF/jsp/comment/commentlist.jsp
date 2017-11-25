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
        /**
         * 全选
         */
        function checkAll(e) {
            var checkAll=$(e).get(0).checked;
            $("input[name='check']").prop("checked",checkAll);
        }

        /**
         * 批量审核
         * @param type
         */
        function batchExtensionApply(type) {
            var checks=$("input[name='check']:checked");
            var grid = $("#grid").data("kendoGrid").dataSource.view();
            if(StrUtil.isNotEmpty(checks)&&checks.length>0){
                var ids=new Array();
                for(var j=0;j<checks.length;j++){
                    ids.push($(checks[j]).val());
                }
                if(ids!=null && ids.length>0){
                    UIkit.modal.confirm("确定"+(type==1?"审核通过":"屏蔽")+"所选评论信息吗？", function () {
                        $.post('extensionApply.do', {
                            ids: ids,
                            state: type
                        }, function (data) {
                            if (data==1) {
                                $("#grid").data("kendoGrid").dataSource.read();
                                UIkit.notify({message: '操作成功', status: 'success', timeout: 3000, pos: 'top-center'});
                            } else {
                                UIkit.notify({message: '系统繁忙,请稍后重试', status: 'warning', timeout: 3000, pos: 'top-center'});
                            }
                        });
                    }, {labels: {Ok: "确认", Cancel: "取消"}});
                }else{
                    UIkit.notify({message: '请选中后在操作!', status: 'warning', timeout: 3000, pos: 'top-center'});
                }
            }else{
                UIkit.notify({message: '请选中后在操作!', status: 'warning', timeout: 3000, pos: 'top-center'});

            }
        }

        /**
         * 重置查询
         */
        function reloadData() {
            $("#grid").data("kendoGrid").dataSource.read();
        }

        //审核屏蔽
        function checkOFF(e,t){
            var item = $("#grid").data("kendoGrid").dataItem($($(e).parent()).closest("tr"));
            UIkit.modal.confirm("确认"+(t==1?'通过审核':'屏蔽审核')+"吗?", function(){
                $.get('enabledState.do', {
                    id: item.id,
                    state: t
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
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">月嫂评论管理</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-content" style="height: auto !important;">
                <div class="uk-grid" data-uk-grid-margin>
                        <div class="uk-width-large-1-6">
                            <div class="uk-input-group">
                                <label>月嫂姓名</label>
                                <input class="md-input" type="text" id="name" value=""/>
                            </div>
                        </div>
                        <div class="uk-width-large-1-6">
                            <div class="uk-input-group">
                                <label>月嫂电话</label>
                                <input class="md-input" type="text" id="phone" value=""/>
                            </div>
                        </div>
                        <div class="uk-width-large-1-6">
                            <div class="uk-input-group">
                                <select id="level" style="width:200px;height: 40px;">
                                    <option value="0">--评价星际--</option>
                                    <option value="5">5星</option>
                                    <option value="4">4星</option>
                                    <option value="3">3星</option>
                                    <option value="2">2星</option>
                                    <option value="1">1星</option>
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
                                <kendo:grid-column title="选择" width="60px" template="<input type='checkbox' id=#= id # name='check' value=#= id # data-md-icheck />"/>
                                <kendo:grid-column title="月嫂姓名" field="name" sortable="false" filterable="false"  width="90px"/>
                                <kendo:grid-column title="月嫂电话" field="phone" sortable="false" filterable="false"  width="90px"/>
                                <kendo:grid-column title="用户姓名" field="user_name" sortable="false" filterable="false"  width="90px"/>
                                <kendo:grid-column title="评论时间" field="create_time" sortable="false" filterable="false" width="90px"/>
                                <kendo:grid-column title="评价星级" field="level" sortable="false" filterable="false"  width="60px"/>
                                <kendo:grid-column title="内容" field="content" sortable="false" filterable="false"  width="120px"/>
                                <kendo:grid-column title="是否匿名" field="annoymous" width="80px" template="#= annoymous ? '匿名评论' : '实名评论' #" filterable="false"/>
                                <kendo:grid-column title="审核状态" field="operastate" width="80px"  filterable="false"/>
                                <kendo:grid-column title="审核人员" field="operator" width="80px"  filterable="false"/>
                                <kendo:grid-column title="操作" width="100px" filterable="false">
                                    <kendo:grid-column-template>
                                        <script>
                                            function template(options) {
                                                var html="";
                                                if(options.state==2){
                                                    html=html +"<div style='text-align:center;margin:0 auto'>" +
                                                             "<a style='color: #6a0d6a'  class='k-button k-button-icontext grid-button k-grid-user_id' onclick='checkOFF(this,1)'>通过</a>" +
                                                             "<a class='k-button k-button-icontext grid-button k-grid-user_id' onclick='checkOFF(this,0)'>屏蔽</a></div>";
                                                }
                                                return html;
                                            }
                                        </script>
                                    </kendo:grid-column-template>
                                </kendo:grid-column>
                            </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements"></kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="commentlist.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                options.level=$("#level").val();
                                                options.phone=$("#phone").val();
                                                options.name=$("#name").val();
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
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-large-1-1">
                    <span class="icheck-inline" style="padding-bottom: 20px;">
                        <input type="checkbox" id="checkbox_all"  onclick="checkAll(this)"/>
                        <label for="checkbox_all"  class="inline-label inline-label-top">全选</label>
                    </span>
                    <a class="md-btn md-btn-primary" href="javascript:batchExtensionApply(1);">批量审核通过</a>
                    <a class="md-btn md-btn-primary" href="javascript:batchExtensionApply(0);">屏蔽显示</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>