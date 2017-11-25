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
        var url = "../chart/chartPage.do?rad="+ Math.random()+"&chartName={1}&startDate={2}&endDate={3}&tdNumber={4}";
        jQuery(document).ready(function () {
            loadGrid();
            createSchoolChart();
            setTotalMoney();
        });

        function reloadData(){
            $("#grid").data("kendoGrid").dataSource.page(1);
            $("#grid").data("kendoGrid").dataSource.read();
            createSchoolChart();
            setTotalMoney();
        }

        function loadGrid(){
            var dataSource = new kendo.data.DataSource({
                transport: {
                    read: {
                        url:"../rest/pagedRefundStatisticsByBs.do",
                        contentType:"application/json",
                        type:"POST"
                    },
                    parameterMap: function(options, type) {
                        options.startDate=$("#startDate").val();
                        options.endDate=$("#endDate").val();
                        options.schoolName=$("#schoolName").val();
                        options.studentName=$("#studentName").val();
                        options.gradeName=$("#gradeName").val();
                        return JSON.stringify(options);
                    }
                },
                schema: {
                    data: function (d) {
                        return d.content;
                    },
                    total: function (d) {
                        return d.totalElements;
                    }
                },
                pageSize: 20,
                serverPaging: true,
                serverFiltering: true,
                serverSorting: false

            });

            $("#grid").kendoGrid({
                dataSource:dataSource,
                height: 500,
                filterable: false,
                sortable: true,
                pageable: true,
                selectable:true,
                columns: [{field:"createDate",title:"日期"},
                           {field:"schoolName",title: "${corporationType}"},
                           {field:"studentName",title: "学生姓名",},
                           {field:"gradeName",title: "年级"},
                           {field:"money",title: "退费金额(元)",template:"<div style='text-align:right'>#= -money#</div>"},

                ]
            });
        }

        function createSchoolChart(){
            $.getJSON('../chart/chartRefundPageByCS.do', {
                startDate:$("#startDate").val(),
                endDate:$("#endDate").val(),
                schoolName:$("#schoolName").val(),
                studentName:$("#studentName").val(),
                gradeName:$("#gradeName").val(),
                type:"school",
                rad: Math.random()
            }, function (json) {

                createGradeChart();

                var datas = [];
                $.each(json, function (date, item) {
                    datas.push([item.schoolName + "：" + item.money, item.ratioNum]);
                });

                $('#schoolChat').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text: '各校区占比'
                    },
                    tooltip: {
                        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                color: '#000000',
                                connectorColor: '#000000',
                                formatter: function () {
                                    return '<b>' + this.point.name + '</b>: ' + this. percentage.toFixed(2) + ' %';
                                }
                            }
                        }
                    },
                    series: [{
                        type: 'pie',
                        name: '统计',
                        data: datas
                    }]
                });
            });
        }

        function createGradeChart(){
            $.getJSON('../chart/chartRefundPageByCS.do', {
                startDate:$("#startDate").val(),
                endDate:$("#endDate").val(),
                schoolName:$("#schoolName").val(),
                studentName:$("#studentName").val(),
                gradeName:$("#gradeName").val(),
                type:"section",
                rad: Math.random()
            }, function (json) {
                var datas = [];
                $.each(json, function (date, item) {
                    datas.push([item.sectionName + "：" + item.money, item.ratioNum]);
                });

                $('#gradeIChat').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text: '各学段占比'
                    },
                    tooltip: {
                        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                color: '#000000',
                                connectorColor: '#000000',
                                formatter: function () {
                                    return '<b>' + this.point.name + '</b>: ' + this.percentage.toFixed(2) + ' %';
                                }
                            }
                        }
                    },
                    series: [{
                        type: 'pie',
                        name: '统计',
                        data: datas
                    }]
                });
            });
        }

        function setTotalMoney(){
            var options = {};
            options.startDate=$("#startDate").val();
            options.endDate=$("#endDate").val();
            options.schoolName=$("#schoolName").val();
            options.studentName=$("#studentName").val();
            options.gradeName=$("#gradeName").val();

            $.ajax({
                type: 'POST',
                url: '../rest/pagedRefundStatisticsBySum.do',
                contentType: "application/json",
                data: JSON.stringify(options),
                success: function (json) {
                    console.log(json);
                    $("#totalMoney").html(json);
                }
            });
        }

    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe" id="bodyId">

<h3 class="heading_b uk-margin-bottom">退费统计</h3>

<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-toolbar">
                <h3 class="md-card-toolbar-heading-text">
                    退费总额(￥): <label id="totalMoney" for="totalMoney"></label>
                </h3>
            </div>
            <div class="md-card-content">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"><i class="uk-input-group-icon uk-icon-calendar"></i></span>
                            <label for="startDate">开始日期</label>
                            <input class="md-input" type="text" id="startDate" data-uk-datepicker="{format:'YYYY-MM-DD'}" value="${startDate}"/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"><i class="uk-input-group-icon uk-icon-calendar"></i></span>
                            <label for="endDate">结束日期</label>
                            <input class="md-input" type="text" id="endDate" data-uk-datepicker="{format:'YYYY-MM-DD'}" value="${endDate}"/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label for="endDate">${corporationType}</label>
                            <input class="md-input" type="text" id="schoolName" />
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label for="endDate">学生姓名</label>
                            <input class="md-input" type="text" id="studentName"/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label for="endDate">年级名称</label>
                            <input class="md-input" type="text" id="gradeName" />
                        </div>
                    </div>
                    <div class="uk-width-large-1-6" style="line-height: 56px;padding-top: 4px;">
                        <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                    </div>
                </div>
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-medium-1-1">
                        <div id="grid"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-medium-1-2">
        <div id="schoolChat" class="mGraph" style="border: none;overflow-y:auto;overflow-x:hidden;width: 100%;height: 550px; margin: 0 auto"></div>
    </div>
    <div class="uk-width-medium-1-2" >
        <div id="gradeIChat" class="mGraph" style="border: none;overflow-y:auto;overflow-x:hidden;width: 100%;height: 550px; margin: 0 auto"></div>
    </div>
</div>

</body>
</html>