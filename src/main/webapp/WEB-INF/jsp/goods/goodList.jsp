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
    <style>
        .btn1 {
            border: 0;
            display: inline-block;
            width:auto;
            height: 26px;
            line-height: 26px;
            text-align: center;
            cursor: pointer;
            background: #2196f3;
            color: #fff;
            -webkit-border-radius: 5px;
            padding-left: 10px;
            padding-right: 10px;
        }
        .btn2 {
            border: 0;
            display: inline-block;
            width: auto;
            height: 26px;
            line-height: 26px;
            text-align: center;
            cursor: pointer;
            background: #D7D9DF;
            color: #fff;
            -webkit-border-radius: 5px;
            padding-left: 5px;
            padding-right: 5px;
        }
        .btn2:hover{
            color: #fff;
            box-shadow: 0 10px 20px rgba(0,0,0,.19),0 6px 6px rgba(0,0,0,.23);
        }
        .btn1:hover{
            color: #fff;
            box-shadow: 0 10px 20px rgba(0,0,0,.19),0 6px 6px rgba(0,0,0,.23);
        }
    </style>
    <script>

        function editGood() {
            var dataItem = getSelectedGridItem("grid");
            if (dataItem) {
                window.location.href = "toEditGood.do?id=" + dataItem.goods_id;
            }
        }

        // 上架下架
        function confirmIntoAccount(e) {
            var dataItem = getSelectedGridItem("grid");
            if (dataItem) {
                var title = "请确认是否上架？";
                if (e == 0) {
                    title = "请确认是否下架？";
                }
                if(dataItem.is_on_sale==e){
                    UIkit.notify({
                        message: '操作成功',
                        status: 'success',
                        timeout: 3000,
                        pos: 'top-center'
                    });
                    return;
                }

                UIkit.modal.confirm(title, function () {
                    $.get('enabledState.do', {
                        id: dataItem.goods_id,
                        isOnSale:e
                    }, function (data) {
                        if (data > 0) {
                            reloadData();
                            UIkit.notify({
                                message: '操作成功',
                                status: 'success',
                                timeout: 3000,
                                pos: 'top-center'
                            });
                            reloadData();
                        } else {
                            UIkit.notify({
                                message: '操作失败',
                                status: 'danger',
                                timeout: 3000,
                                pos: 'top-center'});
                        }
                    });
                }, {labels: {Ok: "确认", Cancel: "取消"}});
            }
        }

        // 编辑商品属性
        function editGoodAttr(){
            var dataItem = getSelectedGridItem("grid");
            if (dataItem) {
                window.open("toEditGoodAttr.do?goodsId=" + dataItem.goods_id);
            }
        }

        // 查询
        function reloadData() {
            $("#grid").data("kendoGrid").dataSource.page(1);
            $("#grid").data("kendoGrid").dataSource.read();
        }
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">商品列表</h3>

<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-content" style="height: auto !important;">

                <div class="uk-grid" data-uk-grid-margin>

                    <div class="uk-width-large-1-6" style="display: none">
                        <div class="uk-input-group" style="width: 100%;line-height: 26px;">
                            <label>商品编号</label>
                            <input class="md-input" type="text" id="goods_sn"/>
                        </div>
                    </div>

                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group" style="width: 100%;line-height: 26px;">
                            <label>商品名称</label>
                            <input class="md-input" type="text" id="goods_name"/>
                        </div>
                    </div>

                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group" style="width: 100%;line-height: 26px;">
                            <label>商品关键字</label>
                            <input class="md-input" type="text" id="keywords"/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group" style="width: 120%;line-height: 26px;padding-top: 10px">
                            <label style="display: inherit;">是否上架:</label>
                            <kendo:comboBox name="is_on_sale" filter="contains" placeholder="全部..." value="1" suggest="true" dataTextField="value" dataValueField="key" style="width: 60%;">
                                <kendo:dataSource data="${validList}"></kendo:dataSource>
                            </kendo:comboBox>
                        </div>
                    </div>

                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group" style="width: 120%;line-height: 26px;padding-top: 10px">
                            <label style="display: inherit;">是否热卖</label>
                            <kendo:comboBox name="is_hot" filter="contains" placeholder="全部..." suggest="true" dataTextField="value" dataValueField="key" style="width: 70%;">
                                <kendo:dataSource data="${validList}"></kendo:dataSource>
                            </kendo:comboBox>
                        </div>
                    </div>
                </div>
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group" align="left" style="width: 100%;line-height: 26px;padding-top: 10px">
                            <a class="md-btn md-btn-primary" href="javascript:reloadData();" disabled="disabled">查询</a>
                        </div>
                    </div>
                </div>

                <div style="padding :10px 30px 10px 0;text-align: left">
                    <a class="btn1" href="toAddGood.do?explain=toAddGood&type=1">新增商品</a>
                    <a class="btn1" onclick="editGood()">编 辑</a>
                    <a class="btn1" onclick="confirmIntoAccount(1)">上 架</a>
                    <a class="btn1" onclick="confirmIntoAccount(0)">下 架</a>
                    <a class="btn1" onclick="editGoodAttr()">编辑属性</a>
                </div>

                <div class="uk-grid">
                    <div class="uk-width-medium-1-1">
                        <kendo:grid name="grid" pageable="true" sortable="true" filterable="true" selectable="true" height="680" style="border-width:0px;">
                            <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/><kendo:grid-columns>
                                <kendo:grid-column title="编号" field="goods_sn" sortable="false" filterable="false"  width="80px"/>
                                <kendo:grid-column title="商品名称" field="goods_name" sortable="false" filterable="false"  width="80px"/>
                                <kendo:grid-column title="点击数" field="click_count" sortable="false" filterable="false"  width="50px"/>
                                <kendo:grid-column title="销量" field="sales_sum" sortable="false" filterable="false"  width="50px"/>
                                <kendo:grid-column title="库存数量" field="store_count" sortable="false" filterable="false"  width="50px"/>
                                <kendo:grid-column title="是否上架" field="is_on_sale" sortable="false" filterable="false" template="#= is_on_sale==1 ? '已上架' : '已下架' #" width="50px"/>
                                <kendo:grid-column title="是否推荐" field="is_recommend" sortable="false" filterable="false" template="#= is_recommend==1 ? '推荐商品' : '普通商品' #" width="50px"/>
                                <kendo:grid-column title="进货价" field="dealerprice" sortable="false" filterable="false" width="50px" template="<div style='text-align:right'>#=dealerprice#</div>"/>
                                <kendo:grid-column title="售价" field="price" sortable="false" filterable="false" width="50px" template="<div style='text-align:right'>#=price#</div>" format=""/>
                                <kendo:grid-column title="供货商名称" field="serviceName" sortable="false" filterable="false" width="50px"/>
                            </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements"></kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="queryGoodList.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                options.goods_sn = $("#goods_sn").val();
                                                options.goods_name = $("#goods_name").val();
                                                options.keywords = $("#keywords").val();
                                                options.is_on_sale = $("#is_on_sale").val();
                                                options.is_hot = $("#is_hot").val();
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