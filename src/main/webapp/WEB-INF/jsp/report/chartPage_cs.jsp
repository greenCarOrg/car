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
    <script type="text/javascript" src="${ctx}/js/ajax.js"></script>
    <script>
        $(document).ready(function() {
            createChart();

        });
        function createChart(){
            var json =JSON.parse('${chartData}');
            var datas = [];
            if (json.hasOwnProperty("show") && json.show == 'nodes') {
                if(json.hasOwnProperty("nodes")){
                    $.each(json.nodes, function (date, item) {
                        datas.push([item.nodeName + ", " + Math.abs(item.ratioNum) , item.ratio]);
                    });
                }
            }else{
                if(json.hasOwnProperty("prants")){
                    $.each(json.prants, function (date, item) {
                        datas.push([item.prantName + ", " + Math.abs(item.ratioNum) , item.ratio]);
                    });
                }
            }
           $('#gradeChat').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text:json.chartText,
                    magin:0,
                    stytle:{ "color": "#333333", "fontSize": "12px" }
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
                },
               navigation: {
                   buttonOptions: {
                       enabled: false
                   }
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
                                return '<b>' + this.point.name + '</b>,  ' + Highcharts.numberFormat(this.percentage,2) + ' %';
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
//            if (null != json.nodes) {
//                var tdNumber = $("#tdNumber").val() * 1;
//                var html = '<table style="border:none;font-size:13px;">';
//                for (var i = 0; i < json.nodes.length; i++) {
//                    html = html +
//                        '<tr height="30">' +
//                        '<td align="right">' + json.nodes[i].nodeName + '：</td>' +
//                        '<td align="left">' + json.moneyIco + json.nodes[i].ratioStr + '，占比 ' + json.nodes[i].ratio + '%&nbsp;&nbsp;&nbsp;&nbsp;</td>';
//                    if ((i < json.nodes.length - 1)) {
//                        i++;
//                        html = html +
//                            '<td align="right">&nbsp;' + json.nodes[i].nodeName + '：</td>' +
//                            '<td align="left">' + json.moneyIco + json.nodes[i].ratioStr + '，占比 ' + json.nodes[i].ratio + '%</td>' +
//                            '</tr>';
//                    }
//                }
//                html += '</table>';
//                $("#sectionChat").html(html);
//            }
        }

    </script>
</head>
<body class="" style="margin: 0px;padding: 0px;height: 375px;border: none; background-color: #ffffff; font-size: 12px;" >
<input type="hidden" id="tdNumber" value="${params.tdNumber}"/>
<div class="md-card-content" id="content" style="height: 375px;">
    <div id="gradeChat" class="mGraph" style="min-width: 300px; height: 375px; margin: 0 auto"></div>
    <%--<div style="height: 140px;width: 100%;overflow-y: auto; overflow-x:hidden;border-top:1px solid #ccc;">--%>
        <%--<div id="sectionChat" style="padding-left:10px;font-size: 12px;">--%>
        <%--</div>--%>
    <%--</div>--%>
</div>
</body>
</html>