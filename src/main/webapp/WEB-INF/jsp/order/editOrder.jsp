<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        .title{
            color:#000000;
            font-weight: 600;
        }
    </style>
    <script type="application/javascript" src="${ctx}/js/common/select.js"></script>
    <script type="application/javascript">
        jQuery(document).ready(function () {
        });
        //保存课程信息
        function save(){
            var params = {};
            var saveForm=$("#validateSubmitForm").serializeArray();
            var formData = new FormData($("#validateSubmitForm")[0]);
            $.each(saveForm, function() {
                params[this.name] = this.value;
            });
            var span = document.getElementById("phone_msg");
            if(span != null){
                UIkit.notify({message: "该用户不存在，请重新输入账号!", status: 'warning', timeout: 1000, pos: 'top-center'});
                return;
            }
            if(StrUtil.isEmpty(params.consignee)){
                UIkit.notify({message: "收货人不能为空!", status: 'warning', timeout: 1000, pos: 'top-center'});
                return;
            }
            if(StrUtil.isEmpty(params.mobile)){
                UIkit.notify({message: "收货人手机号码不能为空!", status: 'warning', timeout: 1000, pos: 'top-center'});
                return;
            }
            if(StrUtil.isEmpty(params.province)){
                UIkit.notify({message: "请选择省份!", status: 'warning', timeout: 1000, pos: 'top-center'});
                return;
            }
            if(StrUtil.isEmpty(params.city)){
                UIkit.notify({message: "请选择城市!", status: 'warning', timeout: 1000, pos: 'top-center'});
                return;
            }
            if(StrUtil.isEmpty(params.district)){
                UIkit.notify({message: "请选择区域!", status: 'warning', timeout: 1000, pos: 'top-center'});
                return;
            }
            var invoice=$("#isNeedInvoice").get(0).checked;
            if(invoice){
                var invoice_type=$("input[name='invoice_type']:checked").val();
                if(invoice_type=='1'){
                    if(StrUtil.isEmpty(params.invoice_title)){
                        UIkit.notify({message: "请填写发票抬头!", status: 'warning', timeout: 1000, pos: 'top-center'});
                        return;
                    }
                    if(StrUtil.isEmpty(params.invoice_taxcodes)){
                        UIkit.notify({message: "请填写企业税码!", status: 'warning', timeout: 1000, pos: 'top-center'});
                        return;
                    }
                }
            }
            formData.append("")
            $.ajax({
                url: 'edit.do' ,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (result) {
                    result = eval("("+result+")");
                    if(result.code=="0"){
                        UIkit.notify({message: "保存成功!", status: 'success', timeout: 1000, pos: 'top-center'});
                        // 跳转到列表页面
                        setTimeout('window.location.href="${ctx}/order/order.do";',1000);
                    }else{
                        UIkit.notify({message: result.msg, status: 'danger', timeout: 1000, pos: 'top-center',});
                    }
                },
                error: function (result) {
                    UIkit.notify({message: '保存失败!', status: 'danger', timeout: 1000, pos: 'top-center'});
                }
            });
        }

        /**
         * 检查手机号码是否有效
         */
        function checkUser(phone){
            var parent = document.getElementById("phone_parent");
            var span = document.getElementById("phone_msg");
            if(span == null){
                span = document.createElement("span");
            }
            //设置 span 属性，如 id
            span.setAttribute("id", "phone_msg");
            span.setAttribute("style", "color:red");
            parent.appendChild(span);

            if (!/^1[0-9]{10}$/.test(phone)) {
                if(span == null){
                    span = document.createElement("span");
                }
                span.innerHTML = "必须是11位数字！";
                return;
            }else{
                $.get("../user/checkUser.do?phone="+phone,null,
                    function(msg){
                        var resultCode=msg.resultCode;
                        parent.removeChild(span);
                        if(resultCode == 'FAIL'){
                            span.innerHTML = "该用户不存在，请重新输入账号!";
                            parent.appendChild(span);
                        }else{
                            //填充用户信息
                            var result=msg.result;
                            $("#consignee").val(result.consignee);
                            $("#address").val(result.address);
                            $("#mobile").val(result.mobile);
                            $("#province").val(result.province);
                            $("#city").val(result.city);
                            $("#district").val(result.district);
                            $("#user_id").val(result.user_id);
                        }
                });
            }
        }
        /**
         * 显示和影藏发票信息
         */
        function showInvoice() {
            var invoice=$("#isNeedInvoice").get(0).checked;
            if(invoice){
                $("#invoice").show();

            }else{
                $("#invoice").hide();
            }
        }
        /**
         * 选择开什么发票
         */
        function chooseInvoiceTitle() {
            var invoice_type=$("input[name='invoice_type']:checked").val();
            if(invoice_type=='0'){
                $("#invoiceInfo").hide();
            }else{
                $("#invoiceInfo").show();
            }
        }
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom"><c:if test="${empty entity.act_id}">添加</c:if><c:if test="${not empty entity.order_id}">编辑</c:if>订单</h3>
<div class="md-card">
    <div class="md-card-content large-padding" style="height: auto !important;">
        <form:form id="validateSubmitForm" cssClass="uk-form-stacked">
            <input type="hidden" name="order_id" value="${entity.order_id}"/>
            <input type="hidden" name="user_id" value="0"/>
            <input type="hidden" name="pay_type" value="1"/>
            <h3 class="title">基本信息</h3>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-4">
                    <div class="parsley-row">
                        <label class="uk-form-label">订单号码</label>
                        <input name="order_sn" id="order_sn" value="${entity.order_sn}" style="width: 50%;" readonly="readonly"/>
                    </div>
                </div>
                <div class="uk-width-medium-1-4">
                    <div class="parsley-row" id="phone_parent">
                        <label class="uk-form-label">用户名</label>
                        <input name="phone" id="phone" placeholder="用户手机号码或邮箱搜索" onchange="checkUser(this.value)" value="${entity.phone}" onkeyup='this.value=this.value.replace(/\D/gi,"")' style="width: 50%;"/>
                    </div>
                </div>
                <div class="uk-width-medium-1-4">
                    <div class="parsley-row">
                        <label class="uk-form-label">快递方式<span class="req">*</span></label>
                        <select name="shipping_code" id="shipping_code" style="width: 100%;">
                            <c:forEach var="shipping" items="${shippingList}">
                                <option value="${shipping.shipping_code}" <c:if test="${entity.shipping_code==province.shipping_code}">selected</c:if>>${shipping.shipping_name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="uk-width-medium-1-4">
                    <div class="parsley-row">
                        <label class="uk-form-label">支付方式<span class="req">*</span></label>
                        <input name="pay_type_name" id="pay_type_name" value="微信" style="width: 50%;" readonly="readonly"/>
                        <%--<select name="pay_type" id="pay_type" style="width: 100%;">--%>
                            <%--<option value="1">微信</option>--%>
                            <%--<option value="2">支付宝</option>--%>
                            <%--<option value="3">银联</option>--%>
                            <%--<option value="4">信用卡</option>--%>
                            <%--<option value="5">货到付款</option>--%>
                        <%--</select>--%>
                    </div>
                </div>
            </div>
            <hr>
            <h3 class="title">收货信息</h3>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-4">
                    <div class="parsley-row">
                        <label class="uk-form-label">收货人<span class="req">*</span></label>
                        <input name="consignee" id="consignee" value="${entity.consignee}" style="width: 50%;"/>
                    </div>
                </div>
                <div class="uk-width-medium-1-4">
                    <div class="parsley-row">
                        <label class="uk-form-label">收货人手机号码<span class="req">*</span></label>
                        <input name="mobile" id="mobile" value="${entity.mobile}" style="width: 50%;"/>
                    </div>
                </div>
                <div class="uk-width-medium-1-4">
                    <div style="width: 30%;float:left;">
                        <label class="uk-form-label">省<span class="req">*</span></label>
                        <select name="province" id="province" style="width: 100%;">
                            <option value="">--</option>
                            <c:forEach var="province" items="${provinceList}">
                                <option value="${province.id}" <c:if test="${entity.province==province.id}">selected</c:if>>${province.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div style="width: 30%;float:left;">
                        <label class="uk-form-label">市<span class="req">*</span></label>
                        <select name="city" id="city" style="width: 100%;">
                            <option value="">--</option>
                            <c:forEach var="city" items="${cityList}">
                                <option value="${city.id}" <c:if test="${entity.city==city.id}">selected</c:if>>${city.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div style="width: 30%;float:left;">
                        <label class="uk-form-label">区<span class="req">*</span></label>
                        <select name="district" id="district" style="width: 100%;">
                            <option value="">--</option>
                            <c:forEach var="district" items="${districtList}">
                                <option value="${district.id}" <c:if test="${entity.district==district.id}">selected</c:if>>${district.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="uk-width-medium-1-4">
                    <div class="parsley-row">
                        <label class="uk-form-label">详细地址<span class="req">*</span></label>
                        <input name="address" id="address"  value="${entity.address}" style="width: 50%;"/>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-1">
                    <div class="parsley-row">
                        是否需要开发票<input type="checkbox" id="isNeedInvoice" onclick="showInvoice()"/>
                        <div id="invoice" hidden="hidden">
                            <input type="radio" name="invoice_type" value="0" onclick="chooseInvoiceTitle()"/>个人发票
                            <input type="radio" name="invoice_type" value="1" onclick="chooseInvoiceTitle()"/>企业发票
                            <div class="uk-grid" id="invoiceInfo" hidden="hidden" data-uk-grid-margin>
                                <div class="uk-width-medium-1-4">
                                    <div class="parsley-row">
                                        <label class="uk-form-label">发票抬头<span class="req">*</span></label>
                                        <input name="invoice_title" id="invoice_title" class="md-input" value="${entity.invoice_title}" style="width: 50%;"/>
                                    </div>
                                </div>
                                <div class="uk-width-medium-1-4">
                                    <div class="parsley-row">
                                        <label class="uk-form-label">发票税码<span class="req">*</span></label>
                                        <input name="invoice_taxcodes" id="invoice_taxcodes" class="md-input" value="${entity.invoice_taxcodes}" style="width: 50%;"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <hr>
            <h3 class="title">商品信息</h3>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-1">
                    <div class="parsley-row">
                        <label class="uk-form-label">选择商品<span class="req">*</span></label>
                        <kendo:multiSelect name="goods_id" dataTextField="goods_name" dataValueField="goods_id" filter="startswith">
                            <kendo:dataSource serverFiltering="true">
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="searchGoods.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options) {
                                                return JSON.stringify(options);
                                            }
                                        </script>
                                    </kendo:dataSource-transport-parameterMap>
                                </kendo:dataSource-transport>
                                <kendo:dataSource-schema data="data" total="total">
                                </kendo:dataSource-schema>
                            </kendo:dataSource>
                        </kendo:multiSelect>
                    </div>
                </div>
            </div>
            <hr>
            <h3 class="title">发货备注</h3>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="uk-form-row parsley-row">
                        <label class="uk-form-label">用户备注</label>
                        <textarea class="md-input" name="user_note" cols="10" rows="2" data-parsley-trigger="keyup"
                                  data-parsley-minlength="20" data-parsley-maxlength="100"
                                  data-parsley-validation-threshold="10"
                                  data-parsley-minlength-message="Come on! You need to enter at least a 20 caracters long comment..">${entity.user_note}</textarea>
                    </div>
                </div>
                <div class="uk-width-medium-1-2">
                    <div class="uk-form-row parsley-row">
                        <label class="uk-form-label">管理员备注</label>
                        <textarea class="md-input" name="admin_note" cols="10" rows="2" data-parsley-trigger="keyup"
                                  data-parsley-minlength="20" data-parsley-maxlength="100"
                                  data-parsley-validation-threshold="10"
                                  data-parsley-minlength-message="Come on! You need to enter at least a 20 caracters long comment..">${entity.admin_note}</textarea>
                    </div>
                </div>
            </div>
            <div class="uk-grid">
                <div class="uk-width-1-1">
                    <button type="button" class="md-btn md-btn-primary" onclick="save()">提交保存</button>
                    <a class="md-btn" href="activity.do">返回</a>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
