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
    <script type="application/javascript">
        //操作类型：0-营收按校区；1-营收按时间；2-充值收入按充值方式；3-收入按类型；4-支出按类型
        var operateType=0;
        window.onload=function () {
            var startDate=new Date("${startDate}").Format("yyyy-MM-dd");
            var endDate=new Date("${endDate}").Format("yyyy-MM-dd");
            businessIncomeSchoolChart();
        }
        //切换页面
        function changePage(type){
            operateType=type;
            if(type==0){
                $("#dayMonth").hide();
//                $("#incomeTypeDiv").show();
//                $("#payTypeDiv").show();
                $("#searchDiv").css("margin-top","8px");
                $("#demoImg").show();
                $("#chartTitle").text("营业收入");
                $("#grid").data("kendoGrid").showColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").showColumn(2);
                $("#grid").data("kendoGrid").showColumn(3);
                $("#grid").data("kendoGrid").showColumn(4);
                $("#grid").data("kendoGrid").showColumn(5);
                $("#grid").data("kendoGrid").showColumn(6);
                $("#grid").data("kendoGrid").showColumn(7);

                $("#grid").data("kendoGrid").hideColumn(8);
                $("#grid").data("kendoGrid").hideColumn(9);
                $("#grid").data("kendoGrid").hideColumn(10);
                $("#grid").data("kendoGrid").hideColumn(11);
                $("#grid").data("kendoGrid").hideColumn(12);
            }else if(type==1){
                $("#dayMonth").show();
//                $("#incomeTypeDiv").show();
//                $("#payTypeDiv").show();
                $("#searchDiv").css("margin-top","8px");
                $("#demoImg").hide();
                $("#chartTitle").text("营业收入");
                $("#grid").data("kendoGrid").hideColumn(0);

                $("#grid").data("kendoGrid").showColumn(1);
                $("#grid").data("kendoGrid").showColumn(2);
                $("#grid").data("kendoGrid").showColumn(3);
                $("#grid").data("kendoGrid").showColumn(4);
                $("#grid").data("kendoGrid").showColumn(5);
                $("#grid").data("kendoGrid").showColumn(6);
                $("#grid").data("kendoGrid").showColumn(7);

                $("#grid").data("kendoGrid").hideColumn(8);
                $("#grid").data("kendoGrid").hideColumn(9);
                $("#grid").data("kendoGrid").hideColumn(10);
                $("#grid").data("kendoGrid").hideColumn(11);
                $("#grid").data("kendoGrid").hideColumn(12);
            }else if(type==2){
                $("#dayMonth").hide();
//                $("#incomeTypeDiv").hide();
//                $("#payTypeDiv").hide();
                $("#searchDiv").css("margin-top","8px");
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(6);
                $("#grid").data("kendoGrid").hideColumn(7);
                $("#grid").data("kendoGrid").hideColumn(9);
                $("#grid").data("kendoGrid").hideColumn(10);

                $("#grid").data("kendoGrid").showColumn(8);
                $("#grid").data("kendoGrid").showColumn(11);
                $("#grid").data("kendoGrid").showColumn(12);
                $("#demoImg").show();
                $("#chartTitle").text("充值收入金额");
            }else if(type==3){
                $("#demoImg").show();
//                $("#incomeTypeDiv").hide();
//                $("#payTypeDiv").hide();
                $("#searchDiv").css("margin-top","8px");
                $("#demoImg").show();
                $("#chartTitle").text("金额");
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(6);
                $("#grid").data("kendoGrid").hideColumn(7);
                $("#grid").data("kendoGrid").hideColumn(8);
                $("#grid").data("kendoGrid").hideColumn(10);

                $("#grid").data("kendoGrid").showColumn(9);
                $("#grid").data("kendoGrid").showColumn(11);
                $("#grid").data("kendoGrid").showColumn(12);
            }else{
                $("#dayMonth").hide();
                $("#demoImg").show();
//                $("#incomeTypeDiv").hide();
//                $("#payTypeDiv").hide();
                $("#searchDiv").css("margin-top","8px");
                $("#chartTitle").text("金额");
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(6);
                $("#grid").data("kendoGrid").hideColumn(7);
                $("#grid").data("kendoGrid").hideColumn(8);
                $("#grid").data("kendoGrid").hideColumn(9);
                $("#grid").data("kendoGrid").hideColumn(12);

                $("#grid").data("kendoGrid").showColumn(10);
                $("#grid").data("kendoGrid").showColumn(11);
                $("#grid").data("kendoGrid").showColumn(12);
            }
            var obj = $("#data"+type).siblings();
            $("#data"+type).css("background","rebeccapurple");
            $.each(obj,function(i,v){
                $(v).css("background","");
            });
            //重新检索数据
            reloadData();
        }
        /**
         *查询搜索
         */
        function reloadData(){
            $("#grid").data("kendoGrid").dataSource.read();
            businessIncomeSchoolChart();
        }
        /**
         *重置搜索条件
         */
        function reset(){
            $("#schoolId").data("kendoMultiSelect").value('');
//            $("#incomeType").data("kendoMultiSelect").value('');
//            $("#payType").data("kendoMultiSelect").value('');
        }
        /**
         * 表头：营业收入-统计汇总
         * @param params:查询参数
         */
        function showIncome(params){
            $.ajax({
                type: "post",
                url: "businessIncomeCount.do",
                data:params,
                dateType:"json",
                success: function (data) {
                    if (operateType == 0||operateType == 1) {
                        if (data != null&&data!=""&&data!="undefined") {
                            $("#showIncome").html("充值收入:<font color='blue' style='font-weight: bold;'>"+data.recharge_income+"</font>" +
                                "&nbsp;&nbsp;&nbsp;其他收入:<font color='blue' style='font-weight: bold;'>"+data.other_income+"</font>" +
                                "&nbsp;&nbsp;&nbsp;退费支出:<font color='blue' style='font-weight: bold;'>"+data.returns_pay+"</font>" +
                                "&nbsp;&nbsp;&nbsp;其他支出:<font color='blue' style='font-weight: bold;'>"+data.other_pay+"</font>" +
                                "&nbsp;&nbsp;&nbsp;营业收入:<font color='blue' style='font-weight: bold;'>"+data.business_income+"</font>");
                        }else{
                            $("#showIncome").html("充值收入:<font color='blue' style='font-weight: bold;'>"+0+"</font>" +
                                "&nbsp;&nbsp;&nbsp;其他收入:<font color='blue' style='font-weight: bold;'>"+0+"</font>" +
                                "&nbsp;&nbsp;&nbsp;退费支出:<font color='blue' style='font-weight: bold;'>"+0+"</font>" +
                                "&nbsp;&nbsp;&nbsp;其他支出:<font color='blue' style='font-weight: bold;'>"+0+"</font>" +
                                "&nbsp;&nbsp;&nbsp;营业收入:<font color='blue' style='font-weight: bold;'>"+0+"</font>");
                        }
                    }else if(operateType == 2){
                        if (data != null&&data!=""&&data!="undefined") {
                            $("#showIncome").html("充值金额:<font color='blue' style='font-weight: bold;'>"+data.totalMoney+"</font>");
                        }else{
                            $("#showIncome").html("充值金额:<font color='blue' style='font-weight: bold;'>"+0+"</font>");
                        }
                    }else{
                        if (data != null&&data!=""&&data!="undefined") {
                            $("#showIncome").html("金额:<font color='blue' style='font-weight: bold;'>"+data.totalMoney+"</font>");
                        }else{
                            $("#showIncome").html("金额:<font color='blue' style='font-weight: bold;'>"+0+"</font>");
                        }
                    }
                }
            });
        }
        //报表-营收按校区
        function businessIncomeSchoolChart() {
            var params={};
            params.operateType=operateType;//操作类型
            var startDate=$("#startDate").data("kendoDatePicker").value();
            var endDate=$("#endDate").data("kendoDatePicker").value();
            if(StrUtil.isNotEmpty(startDate)){
                startDate=startDate.Format("yyyy-MM-dd");
            }
            if(StrUtil.isNotEmpty(endDate)){
                endDate=new Date(endDate);
                endDate=new Date(endDate.setDate(endDate.getDate()+1)).Format("yyyy-MM-dd");
            }
            params.startDate=startDate;
            params.endDate=endDate;
            var schoolId=$("#schoolId").data("kendoMultiSelect").value();
//            var incomeType=$("#incomeType").data("kendoMultiSelect").value();
//            var payType=$("#payType").data("kendoMultiSelect").value();
            if (schoolId!= null&&schoolId.length!=0) {
                //将数组转换为字符串
                params.schoolId=schoolId.join(",");
            }
//            if (incomeType!= null&&incomeType.length!=0) {
//                incomeType.push(0);
//                params.incomeType=incomeType;
//            }
//            if (payType!= null&&payType.length!=0) {
//                payType.push(0);
//                params.payType=payType;
//            }
            if(operateType == 1){//营收按时间
                params.type=$("input[name='dayMonth']:checked").val();
                params.dayMonth=$("input[name='dayMonth']:checked").val()==1?"%Y-%m-%d":"%Y-%m";
                params.dayType = "%Y-%m-%d";
                if(params.dayMonth == "%Y-%m"){
                    params.startDate = startDate.substr(0,7)+"-01";
                }
            }
            $.post("../report/businessIncomeSchoolChart.do",params, function (data) {
                if (data != null) {
                    if (operateType == 1) {
                        $("#businessIncomeSchoolChart").kendoChart({
                            title: {
                                text: params.type==1?startDate+"至"+endDate:new Date(startDate).Format("yyyy")+"年"
                            },
                            dataSource: {
                                data: data.businessIncomeDateChart
                            },
                            series: [{
                                type: "line",
                                aggregate: "avg",
                                field: "business_income",
                                categoryField: "create_time"
                            }],
                            categoryAxis: {
                                baseUnit: params.type==1?"days":"months"
                            }
                        });
                    }else{
                        //教学点柱状图
                        $("#businessIncomeSchoolChart").kendoChart({
                            renderAs: "canvas",
                            title: {
                                text: startDate+"至"+endDate
                            },
                            dataSource: {
                                data: data.columnChart
                            },
                            categoryAxis: {
                                min: 0,
                                max: 10,
                                labels: {
                                    rotation: "auto"
                                }
                            },
                            seriesDefaults: {
                                type: "column",
                                labels: {
                                    visible: true,
                                    background: "transparent"
                                }
                            },
                            series: [{
                                type: "column",
                                field: "series",
                                categoryField: "axis"
                            }],
                            pannable: {
                                lock: "y"
                            },
                            zoomable: {
                                mousewheel: {
                                    lock: "y"
                                },
                                selection: {
                                    lock: "y"
                                }
                            },
                            valueAxis: {
                                labels: {
                                    skip: 2,
                                    step: 2
                                }
                            },
                            tooltip: {
                                visible: true,
                                template: "#= category #: #= value  #"
                            }
                        });
                    }
                }
            });
            showIncome(params);
        }
        /**
         * 报表生成图片并下载
         */
        function downloadCharts() {
            var fileName=operateType==0?"营收按校区":operateType==1?"营收按时间":operateType==2?"充值收入按充值方式":operateType==3?"收入按类型":"支出按类型";
            var chart = $("#businessIncomeSchoolChart").data("kendoChart");
            var imageDataURL = chart.imageDataURL();
            var timestamp= (new Date()).Format("yyyyMMddhhmmss");
            FileUtil.saveImageFile(imageDataURL,fileName+"_"+timestamp);
        }
        /**
         * 导出营业收入
         */
        function exportBusinessIncome() {
            var params="";
            params+="operateType="+operateType;//操作类型
            var startDate=$("#startDate").data("kendoDatePicker").value();
            var endDate=$("#endDate").data("kendoDatePicker").value();
            if(StrUtil.isNotEmpty(startDate)){
                startDate=startDate.Format("yyyy-MM-dd");
            }
            if(StrUtil.isNotEmpty(endDate)){
                endDate=new Date(endDate);
                endDate=new Date(endDate.setDate(endDate.getDate()+1)).Format("yyyy-MM-dd");
            }
            params.dayType = "%Y-%m-%d";
            if($("input[name='dayMonth']:checked").val() == 2){
                startDate = startDate.substr(0,7)+"-01";
            }
            params+="&startDate="+startDate;
            params+="&endDate="+endDate;
            var schoolId=$("#schoolId").data("kendoMultiSelect").value();
//            var incomeType=$("#incomeType").data("kendoMultiSelect").value();
//            var payType=$("#payType").data("kendoMultiSelect").value();
            if (schoolId!= null&&schoolId.length!=0) {
                //将数组转换为字符串
                params+="&schoolId="+schoolId.join(",");
            }
//            if (incomeType!= null&&incomeType.length!=0) {
//                incomeType.push(0);
//                params+="&incomeType="+incomeType;
//            }
//            if (payType!= null&&payType.length!=0) {
//                payType.push(0);
//                params+="&payType="+payType;
//            }
            if(operateType == 1){//营收按时间
                params+="&type="+$("input[name='dayMonth']:checked").val();
            }
            document.location.href="exportBusinessIncome.do?"+params;
        }
        //输入查询时间校验
        function checkDate(dateType) {
            var startDate = new Date($("#startDate").val()).valueOf();
            var endDate = new Date($("#endDate").val()).valueOf();
            if(dateType == 'start' && startDate > endDate){
                $("#startDate").val("");
                UIkit.notify({message: '开始时间必须小于结束时间！', status: 'danger', timeout: 1500, pos: 'top-center'});
            }else if(dateType == 'end' && startDate > endDate){
                $("#endDate").val("");
                UIkit.notify({message: '结束时间必须大于开始时间！', status: 'danger', timeout: 1500, pos: 'top-center'});
            }
        }
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe" id="bodyId">
<h3 class="heading_b uk-margin-bottom">营业收入
    <c:if test="${cropManageModel==0}">
        <img src="../images/icon/fullTime.png" alt="" style="float: right;">
    </c:if>
    <c:if test="${cropManageModel==1}">
        <img src="../images/icon/outside.png" alt="" style="float: right;">
    </c:if>
    <c:if test="${cropManageModel==2}">
        <img src="../images/icon/interest.png" alt="" style="float: right;">
    </c:if>
</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div style="padding :0px;text-align: left">
                <a class="md-btn md-btn-primary" id="data0" href="javascript:changePage(0)" style="border-top-right-radius: 0px;border-bottom-right-radius: 0px; background: rebeccapurple;">营收按校区</a>
                <a class="md-btn md-btn-primary" id="data1" href="javascript:changePage(1)" style="margin:-4px;border-radius: 0;">营收按时间</a>
                <a class="md-btn md-btn-primary" id="data2" href="javascript:changePage(2)" style="margin:-4px;border-radius: 0;">充值收入按充值方式</a>
                <a class="md-btn md-btn-primary" id="data3" href="javascript:changePage(3)" style="margin:-4px;border-radius: 0;">收入按类型</a>
                <a class="md-btn md-btn-primary" id="data4" href="javascript:changePage(4)" style="margin:0px;border-top-left-radius: 0px;border-bottom-left-radius: 0px;">支出按类型</a>
            </div>
            <div class="md-card-content large-padding" style="height: 100%;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-3">
                        <div class="uk-input-group" style="width: 100%">
                            <label style="display: inherit;">时间:</label>
                            <kendo:datePicker name="startDate" value="${startDate}" style="width:40%" title="开始日期" onchange="checkDate('start')"></kendo:datePicker>至
                            <kendo:datePicker name="endDate"   value="${endDate}" style="width:40%" title="结束日期" onchange="checkDate('end')"></kendo:datePicker>
                        </div>
                    </div>
                    <div class="uk-width-large-1-3">
                        <div class="uk-input-group" style="width: 100%;line-height: 26px;">
                            <label style="display: inherit;">校区:</label>
                            <kendo:multiSelect name="schoolId"  dataTextField="schoolName"  dataValueField="schoolId" placeholder="不限" style="width:80%;float:left;">
                                <kendo:dataSource data="${schoolList}">
                                </kendo:dataSource>
                            </kendo:multiSelect>
                        </div>
                    </div>
                    <%--<div class="uk-width-large-1-3" id="incomeTypeDiv">--%>
                        <%--<div class="uk-input-group" style="width: 100%;line-height: 26px;">--%>
                            <%--<label style="display: inherit;">收入类型:</label>--%>
                            <%--<kendo:multiSelect name="incomeType"  dataTextField="typeName"  dataValueField="typeId" placeholder="不限" style="width:80%;float:left;">--%>
                                <%--<kendo:dataSource data="${incomeTypeList}">--%>
                                <%--</kendo:dataSource>--%>
                            <%--</kendo:multiSelect>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="uk-width-large-1-3" id="payTypeDiv" style="margin-top: 20px;">--%>
                        <%--<div class="uk-input-group" style="width: 100%;line-height: 26px;">--%>
                            <%--<label style="display: inherit;">支出类型:</label>--%>
                            <%--<kendo:multiSelect name="payType"  dataTextField="payTypeName"  dataValueField="typeId" placeholder="不限" style="width:80%;float:left;">--%>
                                <%--<kendo:dataSource data="${payTypeList}">--%>
                                <%--</kendo:dataSource>--%>
                            <%--</kendo:multiSelect>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="uk-width-large-1-3" id="dayMonth" style="display: none;margin-top: 20px;">
                        <div class="uk-input-group" style="width: 100%;margin-top: 30px;">
                            <input type="radio" name="dayMonth" value="1" id="day" class="k-radio" checked="checked" onclick="reloadData()">
                            <label class="k-radio-label" for="day">按日</label>
                            <input type="radio" name="dayMonth" value="2" id="month" class="k-radio" onclick="reloadData()">
                            <label class="k-radio-label" for="month">按月</label>
                        </div>
                    </div>
                    <div class="uk-width-large-1-3" id="searchDiv" style="margin-top: 8px;">
                        <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                        <a class="md-btn md-btn-warning" style="margin-left: 20px;" href="javascript:reset();">重置</a>
                    </div>
                </div>
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-1">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"></span>
                            <label><label id="demoImg" style="width: 40px;height: 18px;background-color: #FF7617; display: block;float: left;"></label>&nbsp;<label id="chartTitle">营业收入：</label></label>
                            <a onclick="downloadCharts();" style="float: right;color: blue;" class="k-button">下载</a>
                            <div style="clear: both;"></div>
                            <div id="businessIncomeSchoolChart"></div>
                        </div>
                    </div>
                </div>
                <div class="md-card-toolbar" style="padding: 0px;">
                    <h3 class="md-card-toolbar-heading-text" id="showIncome">
                    </h3>
                    <a onclick='exportBusinessIncome();' style='float: right;color: blue;margin-top: 10px;' class='k-button'>导出</a>
                </div>
                <div class="uk-grid">
                    <div class="uk-width-medium-1-1">
                        <kendo:grid name="grid" pageable="true" sortable="true" filterable="false" selectable="false" height="500" style="border-width:0px;" >
                            <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/>
                            <kendo:grid-filterable extra="true">
                                <kendo:grid-filterable-messages filter="查询" clear="清除" info="提示" and="并且" or="或者"/>
                                <kendo:grid-filterable-operators>
                                    <kendo:grid-filterable-operators-string contains="包含" eq="等于"/>
                                    <kendo:grid-filterable-operators-number eq="=" lte="<=" gte=">="/>
                                    <kendo:grid-filterable-operators-date eq="=" lte="早于" gte="晚于"/>
                                    <kendo:grid-filterable-operators-enums eq="等于"/>
                                </kendo:grid-filterable-operators>
                            </kendo:grid-filterable>
                            <kendo:grid-columns>
                                <kendo:grid-column title="校区" field="school_name" width="15%" filterable="false"/>
                                <kendo:grid-column title="日期" field="create_time" width="15%" filterable="false" hidden="true"/>
                                <kendo:grid-column title="充值收入(元)" field="recharge_income" width="10%" filterable="false" template="<div style='text-align:right'>#=recharge_income#</div>"/>
                                <kendo:grid-column title="其他收入(元)" field="other_income" filterable="false" width="10%" template="<div style='text-align:right'>#=other_income#</div>"/>
                                <kendo:grid-column title="退费支出(元)" field="returns_pay" width="10%" filterable="false" template="<div style='text-align:right'>#=returns_pay#</div>"/>
                                <kendo:grid-column title="其他支出(元)" field="other_pay" filterable="false" width="10%" template="<div style='text-align:right'>#=other_pay#</div>"/>
                                <kendo:grid-column title="营业收入(元)" field="business_income" width="10%" filterable="false" template="<div style='text-align:right'>#=business_income#</div>"/>
                                <kendo:grid-column title="营业占比" field="business_scale" width="10%" filterable="false" />
                                <kendo:grid-column title="充值方式" field="pay_type" width="10%" filterable="false" hidden="true"/>
                                <kendo:grid-column title="收入类型" field="type_name" width="10%" filterable="false" hidden="true"/>
                                <kendo:grid-column title="支出类型" field="type_name" width="10%" filterable="false" hidden="true"/>
                                <kendo:grid-column title="金额(元)" field="money" width="10%" filterable="false" template="<div style='text-align:right'>#=money#</div>" hidden="true" />
                                <kendo:grid-column title="占比" field="money_scale" width="10%" filterable="false" hidden="true"/>
                            </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements">
                                </kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="pageBusinessIncome.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                var schoolId=$("#schoolId").data("kendoMultiSelect").value();
//                                                var incomeType=$("#incomeType").data("kendoMultiSelect").value();
//                                                var payType=$("#payType").data("kendoMultiSelect").value();
                                                if (schoolId!= null&&schoolId.length!=0) {
                                                    options.schoolId=schoolId.join(",");
                                                }
//                                                if (incomeType!= null&&incomeType.length!=0) {
//                                                    incomeType.push(0);
//                                                    options.incomeType=incomeType;
//                                                }
//                                                if (payType!= null&&payType.length!=0) {
//                                                    payType.push(0);
//                                                    options.payType=payType;
//                                                }
                                                var startDate=$("#startDate").data("kendoDatePicker").value();
                                                var endDate=$("#endDate").data("kendoDatePicker").value();
                                                if(StrUtil.isNotEmpty(startDate)){
                                                    startDate=startDate.Format("yyyy-MM-dd");
                                                }
                                                if(StrUtil.isNotEmpty(endDate)){
                                                    endDate=new Date(endDate);
                                                    endDate=new Date(endDate.setDate(endDate.getDate()+1)).Format("yyyy-MM-dd");
                                                }
                                                options.startDate=startDate;
                                                options.endDate=endDate;
                                                options.operateType=operateType;//操作类型
                                                options.dayType = "%Y-%m-%d";
                                                if(operateType == 1){//营收按时间
                                                    options.dayMonth=$("input[name='dayMonth']:checked").val()==1?"%Y-%m-%d":"%Y-%m";
                                                    if(options.dayMonth == "%Y-%m"){
                                                        options.startDate = startDate.substr(0,7)+"-01";
                                                    }
                                                }
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