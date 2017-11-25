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
        var url = "../rest/pagedStudentCurrFeeSum.do?rad="+ Math.random()+"&chartId={1}&chartName={2}&startDate={3}&endDate={4}&schoolId={5}";
        jQuery(document).ready(function () {
            createSchoolChart();
            createCourseChart();
            createGradeChart();
        });

        function reloadData(){
            $("#grid").data("kendoGrid").dataSource.read();
            createSchoolChart();
            createCourseChart();
            createGradeChart();
        }

        function createSchoolChart(){
            $.getJSON('../rest/pagedStudentCurrFeeSum.do', {
                startDate:$("#startDate").val(),
                endDate:$("#endDate").val(),
                schoolId:$("#schoolId").val() == -1 ? '':$("#schoolId").val(),
                chartId:"schoolId",
                chartName:"schoolShortName",
                rad: Math.random()
            }, function (json) {
                var datas = [];
                var income = 0;
                $.each(json, function (date, item) {
                    datas.push([item.schoolShortName + " , " + item.income, item.income]);
                    income = income +　item.income
                });
                $("#showIncome").html("课次收入总额(￥) :" +　income);

                $('#schoolChat').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text: '${corporationType}统计'
                    },
                    tooltip: {
                        pointFormat: '{series.name}: <b>{point.fee:.1f}</b>,<b>{point.percentage:.1f}%</b>'
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

        function createCourseChart(){
            var src = url.replace("{1}","teachMethod");
            src = src.replace("{2}","teachMethod");
            src = src.replace("{3}",$("#startDate").val());
            src = src.replace("{4}",$("#endDate").val());
            src = src.replace("{5}",$("#schoolId").val() == -1 ? '':$("#schoolId").val());
            createChart(src,"#courseIframe","teachMethod");
        }

        function createGradeChart(){
            var src = url.replace("{1}","sectionId");
            src = src.replace("{2}","sectionName");
            src = src.replace("{3}",$("#startDate").val());
            src = src.replace("{4}",$("#endDate").val());
            src = src.replace("{5}",$("#schoolId").val() == -1 ? '':$("#schoolId").val());
            createChart(src,"#gradeIframe","sectionName");
        }

        function createChart(src,id,c){
            $.getJSON(src, {}, function (json) {
                var datas = [];
                $.each(json, function (date, item) {
                    if(c == "teachMethod"){
                        if(item[c] == 0){
                            item[c] = "一对一";
                        }else if(item[c] == 1){
                            item[c] = "小组课";
                        }else if(item[c] == 2){
                            item[c] = "多科强化班";
                        }else{
                            item[c] = "未知";
                        }
                    }else{
                        if(item[c] == null || item[c] == ""){
                            item[c] = "未知";
                        }
                    }
                    datas.push([item[c]+" : "+ item.income, item.income]);
                });

                $(id).highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text:json.chartText
                    },
                    tooltip: {
                        pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
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
                                    return '<b>' + this.point.name + '</b>: ' + Highcharts.numberFormat(this.percentage,2) + ' %';
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
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">课耗收入</h3>

<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-toolbar">
                <h3 class="md-card-toolbar-heading-text" id="showIncome">
                    课次收入总额(￥)
                </h3>
            </div>
            <div class="md-card-content">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"><i class="uk-input-group-icon uk-icon-calendar"></i></span>
                            <label for="startDate">开始日期</label>
                            <input class="md-input" type="text" id="startDate" data-uk-datepicker="{format:'YYYY-MM-DD'}" value="${startDate}" onchange="checkDate('start')">
                        </div>
                    </div>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"><i class="uk-input-group-icon uk-icon-calendar"></i></span>
                            <label for="endDate">结束日期</label>
                            <input class="md-input" type="text" id="endDate" data-uk-datepicker="{format:'YYYY-MM-DD'}" value="${endDate}" onchange="checkDate('end')">
                        </div>
                    </div>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"></span>
                            <label>${corporationType}名称</label>
                            <select id="schoolId" name="school.schoolId" data-md-selectize>
                                <c:forEach items="${schoolList}" var="school">
                                    <option  <c:if test="${school.schoolId==schoolId}">selected</c:if> value=${school.schoolId}>
                                            ${school.schoolName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="uk-width-large-1-5" style="line-height: 56px;padding-top: 4px;">
                        <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                    </div>
                </div>
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-medium-1-1">
                        <kendo:grid name="grid" pageable="true" sortable="true" filterable="false" selectable="true" height="480" resizable="true" >
                            <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/>
                            <kendo:grid-columns>
                                <kendo:grid-column title="${corporationType}" field="schoolName" width="70px" />
                                <kendo:grid-column title="学生姓名" field="studentName" width="70px"/>
                                <kendo:grid-column title="日期" field="curriculumDate" width="85px"/>
                                <kendo:grid-column title="星期" field="week" width="65px"/>
                                <kendo:grid-column title="开始时间" field="startTime" width="85px"/>
                                <kendo:grid-column title="结束日期" field="endTime" width="85px"/>
                                <kendo:grid-column title="班级名称" field="className" width="100px"/>
                                <kendo:grid-column title="课程名称" field="courseName" width="100px"/>
                                <kendo:grid-column title="课次金额(元)" field="income"  width="85px" template="<div style='text-align:right'>#=income#</div>" />
                                <kendo:grid-column title="课次状态" field="attendence" template="#= attendence==1 ? '出勤' : '旷课' #" width="85px" />
                            </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="false" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements">
                                    <kendo:dataSource-schema-model>
                                        <kendo:dataSource-schema-model-fields>
                                            <kendo:dataSource-schema-model-field name="fee" type="number" />
                                        </kendo:dataSource-schema-model-fields>
                                    </kendo:dataSource-schema-model>
                                </kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="../rest/pagedStudentCurrFee.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                options.schoolId=$("#schoolId").val() == -1 ? '':$("#schoolId").val();
                                                options.startDate=$("#startDate").val();
                                                options.endDate=$("#endDate").val();
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
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-medium-1-2">
        <div id="schoolChat" class="mGraph" style="min-width: 400px; height: 550px; margin: 0 auto"></div>
    </div>
    <div class="uk-width-medium-1-2">
        <div id="courseIframe" class="mGraph" style="border: none;overflow-y:auto;overflow-x:hidden;width: 100%;height: 550px; margin: 0 auto"></div>
    </div>
</div>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-medium-1-2">
        <div id="gradeIframe" class="mGraph" style="min-width: 400px; height: 420px; margin: 0 auto"></div>
    </div>
 </div>
</body>
</html>