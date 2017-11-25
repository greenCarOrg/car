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
        jQuery(document).ready(function () {
            createChart();
        });

        function createChart(){
            createSchoolChart();
            createCourseChart();
        }

        function createSchoolChart(){
            $.getJSON('getCurriculumCourseByMonthGroupByCourse.do', {
                year:$("#year").val(),
                month:$("#month").val(),
                rad: Math.random()
            }, function (json) {
                var series=[];
                $.each(json, function (date, item) {
                    var courseSeries=null;
                    $.each(series, function (data, serie) {
                        if(item.courseName==serie.name){
                            courseSeries=serie;
                        }
                    });
                    if(courseSeries==null){
                        courseSeries={name:item.courseName,data:[]};
                        series.push(courseSeries);
                    }
                    courseSeries.data.push([item.calendar, item.courseHour]);
                });

                var categories = [];
                for(var i=0;i<=31;i++){
                    categories.push((i  ));
                }

                var options = {
                    chart: {
                        type: 'spline',
                        margin: [50, 50, 80, 80]
                    },
                    title: {
                        text: "课程消耗月统计图"//
                    },
                    subtitle: {
                        text: ""
                    },
                    xAxis: {
                        type: 'integer',
                        categories:categories
                    },
                    yAxis: {
                        min: 0,
                        type: 'integer',
                        title: {
                            text: "课时(数量)"
                        }
                    },
                    legend: {
                        enabled: true
                    },
                    tooltip: {
                        headerFormat: '<span style="font-size:10px">{point.key}日</span><table>',
                        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.0f} 个课时</b></td></tr>',
                        footerFormat: '</table>',
                        shared: true,
                        useHTML: true
                    },
                    series: series
                };

                $('#schoolChat').highcharts(options );
            });
        }

        function createCourseChart(){
            $.getJSON('getCurriculumCourseByYearGroupByCourse.do', {
                year:$("#year").val(),
                rad: Math.random()
            }, function (json) {
                var series=[];
                $.each(json, function (date, item) {
                    var courseSeries=null;
                    $.each(series, function (data, serie) {
                        if(item.courseName==serie.name){
                            courseSeries=serie;
                        }
                    });
                    if(courseSeries==null){
                        courseSeries={name:item.courseName,data:[]};
                        series.push(courseSeries);
                    }
                    courseSeries.data.push([item.calendar, item.courseHour]);
                });

//                if(series.length>0){
//                    var firstSeries= series[0].data;
//                    for(var i=1;i<=12;i++){
//                        var exist=false;
//                        for(var j=0;j<firstSeries.length;j++){
//                            if(firstSeries[j][0]==i){
//                                exist=true;
//                                break;
//                            }
//                        }
//                        if(!exist){
//                            firstSeries.push([i, 0]);
//                        }
//                    }
//                }

                var categories = [];
                for(var i=0;i<=12;i++){
                    categories.push((i  ));
                }

                var options = {
                    chart: {
                        type: 'line',
                        margin: [50, 50, 80, 80]
                    },
                    title: {
                        text: "课程消耗年统计图"//
                    },
                    subtitle: {
                        text: ""
                    },
                    xAxis: {
                        type: 'integer',
                        categories:categories
                    },
                    yAxis: {
                        min: 0,
                        type: 'integer',
                        title: {
                            text: "课时(数量)"
                        }
                    },
                    legend: {
                        enabled: true
                    },
                    tooltip: {
                        headerFormat: '<span style="font-size:10px">{point.key}月</span><table>',
                        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.0f} 个课时</b></td></tr>',
                        footerFormat: '</table>',
                        shared: true,
                        useHTML: true
                    },
                    series: series
                };

                $('#courseChat').highcharts(options);
            });
        }


    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">课程课时消耗</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-toolbar">
                <h3 class="md-card-toolbar-heading-text">
                    统计条件
                </h3>
            </div>
            <div class="md-card-content">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"><i class="uk-input-group-icon uk-icon-calendar"></i></span>
                            <label for="year">年份</label>
                            <select id="year" name="year" required data-md-selectize  >
                                <c:forEach  var="item" begin="2014" end="2030">
                                    <option <c:if test="${item==year}">selected</c:if> value="${item}">${item}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"><i class="uk-input-group-icon uk-icon-calendar"></i></span>
                            <label for="month">月份</label>
                            <select id="month" name="month" required data-md-selectize  >
                                <c:forEach  var="item" begin="0" end="11">
                                    <option <c:if test="${item==month}">selected</c:if> value="${item}">${item+1}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="uk-width-large-1-5" style="line-height: 56px;padding-top: 4px;">
                        <a class="md-btn md-btn-primary" href="javascript:createChart();">查询</a>
                    </div>
                </div>
                <div id="schoolChat" class="mGraph" style="min-width: 400px; height: 420px; margin: 0 auto"></div>
            </div>
        </div>
    </div>
</div>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-medium-1-1">
        <div class="md-card">
            <div class="md-card-content">
                <div id="courseChat" class="mGraph" style="min-width: 400px; height: 420px; margin: 0 auto"></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>