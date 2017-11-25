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
    <script type="application/javascript">
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe" id="bodyId">
<h3 class="heading_b uk-margin-bottom">订单管理</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-content" style="height: auto !important;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label >订单编号</label>
                            <input class="md-input" type="text" id="order_sn" value="${order_sn}"/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label >下单人手机号码</label>
                            <input class="md-input" type="text" id="phone" value="${phone}"/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label >下单人</label>
                            <input class="md-input" type="text" id="nickname" value="${nickname}"/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label >收货人手机号码</label>
                            <input class="md-input" type="text" id="mobile" value="${mobile}"/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label >收货人</label>
                            <input class="md-input" type="text" id="consignee" value="${consignee}"/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label style="display: inherit;">支付状态</label>
                            <select name="order_status" id="order_status">
                                <option value="">全部</option>
                                <option value="0" <c:if test="${entity.order_status==0}">selected</c:if>>已下单</option>
                                <option value="1" <c:if test="${entity.order_status==1}">selected</c:if>>已完成</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label style="display: inherit;">订单下单开始时间</label>
                            <kendo:dateTimePicker name="start_time" value="${entity.start_time}" dateInput="true" title="datetimepicker" change="checkDate"></kendo:dateTimePicker>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label style="display: inherit;">订单下单结束时间</label>
                            <kendo:dateTimePicker name="end_time" value="${entity.end_time}" dateInput="true" title="datetimepicker" change="checkDate"></kendo:dateTimePicker>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6" style="line-height: 56px;padding-top: 4px;">
                        <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                    </div>
                </div>
                <div style="padding :0 30px 10px 0;text-align: right">
                    <a class="md-btn md-btn-primary" href="toEdit.do">添加订单</a>
                </div>
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
                                <kendo:grid-column title="订单编号" field="order_sn" width="10%" filterable="false"/>
                                <kendo:grid-column title="下单人手机号码" field="phone" width="10%" filterable="false"/>
                                <kendo:grid-column title="下单人昵称" field="userkname" width="10%" filterable="false"/>
                                <kendo:grid-column title="收货人" field="consignee" width="10%" filterable="false"/>
                                <kendo:grid-column title="收货人手机" field="mobile" width="10%" filterable="false"/>
                                <kendo:grid-column title="优惠券抵扣金额" field="coupon_price" width="10%" filterable="false"/>
                                <kendo:grid-column title="邮费" field="shipping_price" width="10%" filterable="false"/>
                                <kendo:grid-column title="应付款金额" field="order_amount" width="10%" filterable="false"/>
                                <kendo:grid-column title="订单总价" field="total_amount" width="10%" filterable="false"/>
                                <kendo:grid-column title="订单状态" field="order_status" width="10%" filterable="false"/>
                                <kendo:grid-column title="支付状态" field="pay_status" width="10%" filterable="false"/>
                                <kendo:grid-column title="发货状态" field="shipping_status" width="10%" filterable="false"/>
                                <kendo:grid-column title="下单时间" field="add_time" width="8%" filterable="false"/>
                                <kendo:grid-column title="支付时间" field="pay_time" width="8%" filterable="false"/>
                                <kendo:grid-column title="最后新发货时间" field="shipping_time" width="8%" filterable="false"/>
                                <kendo:grid-column title="收货确认时间" field="confirm_time" width="8%" filterable="false"/>
                                <kendo:grid-column title="用户备注" field="user_note" width="8%" filterable="false"/>
                                <kendo:grid-column title="操作" width="23%" filterable="false" >
                                    <kendo:grid-column-template>
                                        <script>
                                            function template(options) {
                                                var html="<div style='text-align:center;margin:0 auto'>" +
                                                        "<a class='k-button k-button-icontext grid-button k-grid-user_id' href='toEdit.do?id="+options.order_id+"'>编辑<a/>"+
                                                        "<a class='k-button k-button-icontext grid-button k-grid-user_id' href=javascript:removeItem("+options.order_id+");>删除<a/>"+
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
                                                var start_time=$("#start_time").data("kendoDateTimePicker").value();
                                                var end_time=$("#end_time").data("kendoDateTimePicker").value();
                                                if(StrUtil.isNotEmpty(start_time)){
                                                    start_time=start_time.Format("yyyy-MM-dd hh:mm:ss");
                                                }
                                                if(StrUtil.isNotEmpty(end_time)){
                                                    end_time=end_time.Format("yyyy-MM-dd hh:mm:ss");
                                                }
                                                options.order_sn=$("#order_sn").val();
                                                options.phone=$("#phone").val();
                                                options.nickname=$("#nickname").val();
                                                options.mobile=$("#mobile").val();
                                                options.consignee=$("#consignee").val();
                                                options.address=$("#address").val();
                                                options.order_status=$("#order_status").val();
                                                options.start_time=start_time;
                                                options.end_time=end_time;
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
<script type="application/javascript">

    //grid重新获取数据
    function reloadData(){
        var start_time = new Date($("#start_time").val()).valueOf();
        var end_time = new Date($("#end_time").val()).valueOf();
        if(start_time > end_time){
            UIkit.notify({message: '开始时间必须小于结束时间！', status: 'danger', timeout: 1500, pos: 'top-center'});
            this.value("");
        }
        $("#grid").data("kendoGrid").dataSource.read();
    }
    //输入时间校验
    function checkDate() {
        var start_time = new Date($("#start_time").val()).valueOf();
        var end_time = new Date($("#end_time").val()).valueOf();
        if(start_time > end_time){
            UIkit.notify({message: '开始时间必须小于结束时间！', status: 'danger', timeout: 1500, pos: 'top-center'});
            this.value("");
        }
    }
    function removeItem(id){
        UIkit.modal.confirm("确认删除这个团购项目吗?", function(){
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
</script>
</body>
</html>