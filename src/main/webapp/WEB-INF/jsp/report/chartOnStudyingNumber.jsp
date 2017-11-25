<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>妈妈有约管理系统</title>
    <script type="text/javascript" src="${ctx}/kendo/js/kendo.all.min.js"></script>
</head>
<body style="margin: 0px;padding: 0px;height: auto;border: none; background-color: #ffffff; font-size: 12px;" class="sidebar_main_open sidebar_main_swipe">
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-toolbar">
                <h3 class="md-card-toolbar-heading-text" id="showIncome">
                    ${corporationType}:<font style="font-weight: bold">${param.schoolName}</font>
                </h3>
            </div>
            <div class="md-card-content" style="height: auto !important;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"><i class="uk-input-group-icon uk-icon-calendar"></i></span>
                            <label for="startDate">开始日期</label>
                            <input class="md-input" type="text" id="startDate" data-uk-datepicker="{format:'YYYY-MM-DD'}" value="${startDate}">
                        </div>
                    </div>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"><i class="uk-input-group-icon uk-icon-calendar"></i></span>
                            <label for="endDate">结束日期</label>
                            <input class="md-input" type="text" id="endDate" data-uk-datepicker="{format:'YYYY-MM-DD'}" value="${endDate}">
                        </div>
                    </div>
                    <div class="uk-width-large-1-5">
                        <a class="md-btn md-btn-primary" href="javascript:createChart();">查询</a>
                    </div>
                </div>
                <div id="example">
                    <div class="demo-section k-content wide">
                        <div id="chart"></div>
                    </div>
                    <div class="demo-section k-content wide">
                        <div id="oneToOneNumberChart"></div>
                    </div>
                    <div class="demo-section k-content wide">
                        <div id="teamNumberChart"></div>
                    </div>
                    <div class="demo-section k-content wide">
                        <div id="normalNumberChart"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function createChart() {
        $.getJSON('chartOnStudyingNumber.do', {
            schoolId:"${param.schoolId}",
            startDate:$("#startDate").val(),
            endDate:$("#endDate").val()
        }, function (json) {
            $("#chart").kendoChart({
                title: {
                    text: "在读人数"
                },
                dataSource: {
                    data: json.onStudyingNumber
                },
                series: [{
                    type: "line",
                    aggregate: "avg",
                    field: "value",
                    categoryField: "date"
                }],
                categoryAxis: {
                    baseUnit: "days"
                }
            });
            $("#oneToOneNumberChart").kendoChart({
                title: {
                    text: "一对一在读人数"
                },
                dataSource: {
                    data: json.oneToOneNumber
                },
                series: [{
                    type: "line",
                    aggregate: "avg",
                    field: "value",
                    categoryField: "date"
                }],
                categoryAxis: {
                    baseUnit: "days"
                }
            });
            $("#teamNumberChart").kendoChart({
                title: {
                    text: "小组课在读人数"
                },
                dataSource: {
                    data: json.teamNumber
                },
                series: [{
                    type: "line",
                    aggregate: "avg",
                    field: "value",
                    categoryField: "date"
                }],
                categoryAxis: {
                    baseUnit: "days"
                }
            });
            $("#normalNumberChart").kendoChart({
                title: {
                    text: "多科强化班在读人数"
                },
                dataSource: {
                    data: json.normalNumber
                },
                series: [{
                    type: "line",
                    aggregate: "avg",
                    field: "value",
                    categoryField: "date"
                }],
                categoryAxis: {
                    baseUnit: "days"
                }
            });
            $("#startDate").val(json.startDate)
            $("#endDate").val(json.endDate)
        });
    }
    $(document).ready(createChart);
    $("#example").bind("kendo:skinChange", createChart);
</script>

</body>
</html>