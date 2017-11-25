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
            var datas = [];
            $.each(json.prants, function (date, item) {
                datas.push([item.prantName+ "：￥" + item.ratioNum, item.ratio]);
            });

           $('#gradeChat').highcharts({
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
            if(null != json.nodes){
                var tdNumber = $("#tdNumber").val()*1;
                var html = '<table><tr height="35">';
                for(var i=0;i<json.nodes.length;i++){
                    var num = i+1;
                    if(num >= tdNumber &&  num % tdNumber == 1){
                        html +=  '</tr><tr height="35">';
                    }
                    html += '<td align="right" width="150">'+json.nodes[i].nodeName+'：</td>';
                    html+='<td align="left"  width="50">'+json.nodes[i].ratioStr+',</td>'
                    html+='<td align="left"  width="150">'+json.nodes[i].ratio+'%</td>'
                }
                html +='</tr></table>'
                $("#sectionChat").html(html);
            }
        }

    </script>
</head>
<body class="md-card" style="margin: 0px;padding: 0px;color: #444;">
<input type="hidden" id="chartName" value="${params.chartName}" data="${chartData}"/>
<input type="hidden" id="schoolId" value="${params.schoolId}"/>
<input type="hidden" id="startDate" value="${params.startDate}"/>
<input type="hidden" id="endDate" value="${params.endDate}"/>
<input type="hidden" id="tdNumber" value="${params.tdNumber}"/>
<div class="md-card-content" id="content">
    <div id="gradeChat" class="mGraph" style="min-width: 400px; height: 350px; margin: 0 auto"></div>
    <div style="height: 230px;width: 100%;overflow-y: auto; overflow-x:hidden;border:1px solid #ccc;">
        <div id="sectionChat" style="padding-left:40px;font-size: 16px;"></div>
    </div>
</div>
</body>
</html>