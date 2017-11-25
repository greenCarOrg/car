<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>妈妈有约管理系统</title>
    <!-- altair admin -->
    <link rel="stylesheet" href="${ctx}/assets/css/main.min.css" media="all">
    <script type="text/javascript" src="${ctx}/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/highcharts/highcharts.js"></script>
    <script type="text/javascript" src="${ctx}/highcharts/modules/exporting.js"></script>
    <script>
        $(document).ready(function() {
            createChart();

        });
        function createChart(){
            var json =JSON.parse('${chartData}');
            createTeacherRewardChart(json);
            gradeStudentCourseChart(json);
            incomeTeacherRewardByDateChart(json);
            incomeTeacherRewardByMohtChart(json);
        }

        function createTeacherRewardChart(json){
            var xcategories = [];
            var seriesDatas = [];
            $.each(json.teacherReward, function (date, item) {
                xcategories.push(item.teacherName);
                seriesDatas.push(item.money);
            });
            $('#teacherRewardChart').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'column'
                },
                title: {
                    text:json.chartText,
                    magin:0,
                    stytle:{ "color": "#333333", "fontSize": "12px" }
                },
                tooltip: {
                    pointFormat: '课收: <b>{point.y}</b>'
                },
                navigation: {
                    buttonOptions: {
                        enabled: false
                    }
                },
                legend: {
                    enabled: false
                },
                xAxis: {
                    labels: {
                        rotation: -45,//角度
                        min: 0,
                        y:25,
                        align: 'center',
                        style: {
                            fontSize: '13px',
                            fontFamily: 'Verdana, sans-serif'
                        }
                    },
                    categories: xcategories   //指定x轴分组
                },
                yAxis: {
                    title: {
                        text: '课收'                  //指定y轴的标题
                    }
                },
                series: [{
                    name:'老师',
                    dataLabels: {
                        enabled: true,
                        rotation: 0,//角度
                        color: '#FFFFFF',
                        align: 'center',
                        x: 0,
                        y: -10,
                        style: {
                            fontSize: '13px',
                            fontFamily: 'Verdana, sans-serif',
                            textShadow: '0 0 3px black'
                        }
                    },
                    data:seriesDatas
                }]
            });
        }

        function gradeStudentCourseChart(json){
            var xcategories = [];
            var gradeCourse = [];
            var gradeCharge = [];
            $.each(json.studyGrades, function (date, item) {
                xcategories.push(item);
                if(json.gradeCourseIncomes[item]!=null){
                    gradeCourse.push(json.gradeCourseIncomes[item]);
                }else{
                    gradeCourse.push(0);
                }

                if(json.gradeChargeIncomes[item]!=null){
                    gradeCharge.push(json.gradeChargeIncomes[item]);
                }else{
                    gradeCharge.push(0);
                }
            });
            $('#gradeStudentCourseChart').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'column'
                },
                title: {
                    text:'各年级学生的营收和课收',
                    magin:0,
                    stytle:{ "color": "#333333", "fontSize": "12px" }
                },
                tooltip: {
                    pointFormat: '{point.series.name}: <b>{point.y}</b>'
                },
                navigation: {
                    buttonOptions: {
                        enabled: false
                    }
                },
                legend: {
                    enabled: true,
                    floating:true,
                    y:-268
                },
                xAxis: {
                    labels: {
                        rotation: -45,//角度
                        min: 0,
                        y:25,
                        align: 'center',
                        style: {
                            fontSize: '13px',
                            fontFamily: 'Verdana, sans-serif'
                        }
                    },
                    categories: xcategories   //指定x轴分组
                },
                yAxis: {
                    title: {
                        text: '金额'                  //指定y轴的标题
                    }
                },
                series: [{
                    name:'营收',
                    dataLabels: {
                        enabled: true,
                        rotation: -90,//角度
                        color: '#FFFFFF',
                        align: 'center',
                        x: 0,
                        y: -10,
                        style: {
                            fontSize: '13px',
                            fontFamily: 'Verdana, sans-serif',
                            textShadow: '0 0 3px black'
                        }
                    },
                    data: gradeCharge
                },
                    {
                        name:'课收',
                        dataLabels: {
                            enabled: true,
                            rotation: -90,//角度
                            color: '#ffff00',
                            align: 'center',
                            x: 0,
                            y: -10,
                            style: {
                                fontSize: '13px',
                                fontFamily: 'Verdana, sans-serif',
                                textShadow: '0 0 3px black'
                            }
                        },
                        data:gradeCourse
                    }
                ]
            });
        }

        function incomeTeacherRewardByDateChart(json){
            var xcategories = json.dateList;
            var income = json.incomlistOfDate;
            var teacherReward = json.TeacherrewardlistOfDate;
            var month = ['一','二','三','四','五','六','七','八','九','十','十一','十二'];
            $('#incomeTeacherRewardByDateChart').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'line'
                },
                title: {
                    text:month[new Date().getMonth()]+'月份营收和课收的趋势图',
                    magin:0,
                    stytle:{ "color": "#333333", "fontSize": "12px" }
                },
                tooltip: {
                    pointFormat: '{point.series.name}: <b>{point.y}</b>'
                },
                navigation: {
                    buttonOptions: {
                        enabled: false
                    }
                },
                legend: {
                    enabled: true,
                    floating:true,
                    y:-268
                },
                xAxis: {
                    labels: {
                        rotation: -45,//角度
                        min: 0,
                        y:25,
                        align: 'center',
                        style: {
                            fontSize: '13px',
                            fontFamily: 'Verdana, sans-serif'
                        }
                    },
                    categories: xcategories   //指定x轴分组
                },
                yAxis: {
                    title: {
                        text: '金额'                  //指定y轴的标题
                    }
                },
                series: [{
                    name:'营收',
                    data: income
                },
                    {
                        name:'课收',
                        data:teacherReward
                    }
                ]
            });
        }

        function incomeTeacherRewardByMohtChart(json){
            var xcategories = ["01","02","03","04","05","06","07","08","09","10","11","12"];
            var income = json.incomlistOfMonth;
            var teacherReward = json.TeacherrewardlistOfMonth;
            $('#incomeTeacherRewardByMohtChart').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'line'
                },
                title: {
                    text:new Date().getFullYear()+'年营收和课收的月份趋势图',
                    magin:0,
                    stytle:{ "color": "#333333", "fontSize": "12px" }
                },
                tooltip: {
                    pointFormat: '{point.series.name}: <b>{point.y}</b>'
                },
                navigation: {
                    buttonOptions: {
                        enabled: false
                    }
                },
                legend: {
                    enabled: true,
                    floating:true,
                    y:-268
                },
                xAxis: {
                    labels: {
                        rotation: 0,//角度
                        min: 0,
                        y:25,
                        align: 'center',
                        style: {
                            fontSize: '13px',
                            fontFamily: 'Verdana, sans-serif'
                        }
                    },
                    categories: xcategories   //指定x轴分组
                },
                yAxis: {
                    title: {
                        text: '金额'                  //指定y轴的标题
                    }
                },
                series: [{
                    name:'营收',
                    data: income
                },
                    {
                        name:'课收',
                        data:teacherReward
                    }
                ]
            });
        }

    </script>
</head>
<body class="" style="margin: 0px;padding: 0px;height: 375px;border: none; background-color: #ffffff; font-size: 12px;" >
<input type="hidden" id="tdNumber" value="${params.tdNumber}"/>
<div class="md-card-content" id="content" style="height: 400px;">
    <div id="teacherRewardChart" class="mGraph" style="width: 49.8%; height: 360px; margin: 0 auto;
    float: left; border-bottom: 1px solid;border-right: 1px solid;border-color: #C1C1C1;"></div>

    <div id="gradeStudentCourseChart" class="mGraph" style="width: 50%; height: 360px; margin: 0 auto;
    float: right;border-left: 1px solid;border-bottom: 1px solid;border-color: #C1C1C1;">
    </div>
    <br>
    <div id="incomeTeacherRewardByDateChart" class="mGraph" style="width: 49.8%; height: 360px; margin: 0 auto;
    float: left; border-top: 1px solid;border-right: 1px solid;border-color: #C1C1C1;"></div>

    <div id="incomeTeacherRewardByMohtChart" class="mGraph" style="width: 50%; height: 360px; margin: 0 auto;
    float: right;border-top: 1px solid;border-left: 1px solid;border-color: #C1C1C1;"></div>
</div>
</body>
</html>