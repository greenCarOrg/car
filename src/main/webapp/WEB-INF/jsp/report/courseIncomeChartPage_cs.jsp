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
            var json = JSON.parse('${data}');
            var datas = [json.subjectData,json.gradeData,json.teacherData];
            var texts = ['按学科统计课收分布','按年级统计课收分布','按老师统计课收分布'];
            var divIds = ['subjectGroupChat','gradeGroupChat','teacherGroupChat'];
            var value = {};
            for(var i=0;i<divIds.length;i++){
                value[divIds[i]]=i;
                $('#'+divIds[i]).highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text: texts[i]
                    },
                    exporting: {
                        buttons: {
                            contextButtons: {
                                enabled: false,
                                menuItems: null
                            }
                        },
                        enabled: false
                    },
                    tooltip: {
                        //pointFormat: '课酬:'+json.valueAry[Number('{point.x}')]+'\<br\>占比:{point.percentage:.1f}%'
                        formatter: function () {
                            return '<b>' + this.point.name +'\<br\>课收:'+datas[value[this.series.name]].valueAry[this.point.x]+'\<br\>占比:'+Highcharts.numberFormat(this.percentage,2) + ' %'
                            //return '<b>' + this.point.name +'\<br\>课收:'+datas[value[this.series.name]].valueAry[this.point.x]+'\<br\>占比:'+this.point.y+'%';
                        }
                    },
                    legend:{
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'middle',
                        borderWidth: 0
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
                                    return '<b>' + this.point.name + '，'+datas[value[this.series.name]].valueAry[this.point.x]+'，'+ this.percentage.toFixed(2) + ' %';
                                    //return '<b>' + this.point.name + '，'+datas[value[this.series.name]].valueAry[this.point.x]+'，'+ this.y + ' %';
                                }
                            }
                        }
                    },
                    series: [{
                        type: 'pie',
                        name: divIds[i],
                        data: datas[i].data
                    }]
                });
            }

        }


    </script>
</head>
<body class="" style="margin: 0px;padding: 0px;height: 375px;border: none; background-color: #ffffff; font-size: 12px;" >
<div class="md-card-content" id="content" style="height: 900px;">
    <div id="subjectGroupChat" class="mGraph" style="min-width: 260px; height: 300px; margin: 0 auto;border-bottom: 1px solid #c9bab7;">按学科统计课酬分布</div>
    <div id="gradeGroupChat" class="mGraph" style="min-width: 260px; height: 300px; margin: 0 auto;border-bottom: 1px solid #c9bab7;">按年级统计课酬分布</div>
    <div id="teacherGroupChat" class="mGraph" style="min-width: 260px; height: 300px; margin: 0 auto;border-bottom: 1px solid #c9bab7;">按老师统计课酬分布</div>
</div>
</body>
</html>