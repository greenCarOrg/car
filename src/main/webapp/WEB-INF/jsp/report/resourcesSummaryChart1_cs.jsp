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
            resourcesSummaryChart1(json);
        }

        function resourcesSummaryChart1(json){
            var xcategories = json.teacherName;
            var teacherCount = json.teacherCount;
            var startDate = json.startDate;
            var endDate = json.endDate;
            $('#resourcesSummaryChart1').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    type: 'column'
                },
                title: {
                    text:startDate +" - " + endDate +'老师饱和情况',
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
                    enabled: false
                },
                xAxis: {
                    labels: {
                        rotation: 330,//角度
                        min: 0,
                        x:5,
                        y:40,
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
                        text: '课次'                  //指定y轴的标题
                    }
                },
                series: [{
                    name: '课次',
                    data: teacherCount
                 }
                ]
            });
        }

    </script>
</head>
<body class="" style="margin: 0px;padding: 0px;height: 375px;border: none; background-color: #ffffff; font-size: 12px;" >
<input type="hidden" id="tdNumber" value="${params.tdNumber}"/>
<div class="md-card-content" id="content" style="height: 400px;">
    <div id="resourcesSummaryChart1" class="mGraph" style="width: 100%; height: 360px; margin: 0 auto;
    float: right;border-top: 1px solid;border-left: 1px solid;border-color: #C1C1C1;"></div>
</div>
</body>
</html>