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
        var url = "../chart/chartPage2.do?rad="+ Math.random()+"&chartName={1}&startDate={2}&endDate={3}&tdNumber={4}&studentName={5}&schoolId=${schoolId}";
        jQuery(document).ready(function () {
            createPayTypeChart();
            createSectionChat();
            createGradeChat();
            showIncome();
            loadGrid();//初始化grid
        });

        function reloadData(){
            $("#grid").data("kendoGrid").dataSource.page(1);
            $("#grid").data("kendoGrid").dataSource.read();
            createPayTypeChart();
            createSectionChat();
            createGradeChat();
            showIncome();
        }

        function showIncome(){
            var options = {};
            options.startDate=$("#startDate").val();
            options.endDate=$("#endDate").val();
            options.studentName=$("#studentName").val();
            options.schoolId=${schoolId};
            $.ajax({
                type: "get",
                url: "../chart/getBusinessIncomeList.do",
                data:options,
                dateType:"json",
                success: function (data) {
                    var chargeTotal = 0;
                    var refoundTotal = 0;
                    $.each(data, function (date, item) {
                        chargeTotal = chargeTotal + item.chargeSum;
                        refoundTotal = refoundTotal + item.refoundSum;
                    });
                    var netIncome = chargeTotal + refoundTotal;
                    $("#showIncome").html("${corporationType}：  " + "  ${schoolName}  " +"  ，营业收入总额(￥)："+netIncome.toFixed(2)+"，账户现金累计充值(￥)："+chargeTotal.toFixed(2)+"，退费总额(￥)："+(-refoundTotal.toFixed(2)));
                }
            });

        }
        function loadGrid(){
            var dataSource = new kendo.data.DataSource({
                transport: {
                    read: {
                        url:"pageBusinessIncomeByDate2.do",
                        contentType:"application/json",
                        type:"POST"
                    },
                    parameterMap: function(options, type) {
                        options.schoolId=${schoolId};
                        options.startDate=$("#startDate").val();
                        options.endDate=$("#endDate").val();
                        options.studentName=$("#studentName").val();
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
                serverSorting: true
            });

            $("#grid").kendoGrid({
                dataSource:dataSource,
                height: 500,
                filterable: false,
                sortable: true,
                pageable: true,
                selectable:true,
                columns: [
                    {field:"createDateStr",title: "日期",width: "200px"},
                    {field:"student.studentName",title: "学生名称",},
                    {field:"student.gradeName",title: "学生年级"},
                    {field:"payTypeStr",title: "出纳方式"},
                    {field:"money",title: "充值金额(元)",template:"#= type==1 ? money : '--' #"},
                    {field:"money",title: "退费金额(元)",template:"#= type==2 ? -money : '--' #"}

                ]
            });
        }

        function createPayTypeChart(){
            var src = url.replace("{1}","payTypeCharge");
            src = src.replace("{2}",$("#startDate").val());
            src = src.replace("{3}",$("#endDate").val());
            src = src.replace("{4}",2);
            src = src.replace("{5}",$("#studentName").val());
            $.getJSON(src, {}, function (json) {
                var datas = [];
                $.each(json.prants, function (date, item) {
                    datas.push([item.prantName + "," + item.ratioNum, item.ratio]);
                });
                createChart(datas,"#payTypeChat","充值方式占比")
            });
        }

        function createSectionChat(){
            var src = url.replace("{1}","gradeCharge");
            src = src.replace("{2}",$("#startDate").val());
            src = src.replace("{3}",$("#endDate").val());
            src = src.replace("{4}",3);
            src = src.replace("{5}",$("#studentName").val());
            $.getJSON(src, {}, function (json) {
                var datas = [];
                $.each(json.prants, function (date, item) {
                    datas.push([item.prantName + "," + item.ratioNum, item.ratio]);
                });
                createChart(datas,"#sectionChat","各学段营业额占比")
            });
        }

        function createGradeChat(){
            var src = url.replace("{1}","gradeCharge");
            src = src.replace("{2}",$("#startDate").val());
            src = src.replace("{3}",$("#endDate").val());
            src = src.replace("{4}",3);
            src = src.replace("{5}",$("#studentName").val());
            $.getJSON(src, {}, function (json) {
                var datas = [];
                $.each(json.nodes, function (date, item) {
                    datas.push([item.nodeName + "," + item.ratioNum, item.ratio]);
                });
                createChart(datas,"#gradeChat","各年级营业额占比")
            });
        }

        function createChart(datas,id,chartText){
            $(id).highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text:chartText
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
        }

    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe" id="bodyId">

<h3 class="heading_b uk-margin-bottom">营业明细</h3>

<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-toolbar">
                <h3 class="md-card-toolbar-heading-text" id="showIncome">
                    营业收入总额(￥):
                </h3>
            </div>
            <div class="md-card-content">
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
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"></span>
                            <label>学生姓名</label>
                            <input class="md-input" type="text" id="studentName" >
                        </div>
                    </div>
                    <div class="uk-width-large-1-5">
                        <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                        <a class="md-btn md-btn-primary" href="../report/businessIncome.do?explain=businessIncomedo">返回</a>
                    </div>
                </div>
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-medium-3-5">
                        <div id="grid"></div>
                    </div>
                    <div class="uk-width-medium-2-5">
                        <div id="payTypeChat" class="mGraph" style="min-width: 400px; height: 420px; margin: 0 auto"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-medium-1-2">
        <div id="sectionChat" class="mGraph" style="border: none;overflow-y:auto;overflow-x:hidden;width: 100%;height: 550px; margin: 0 auto"></div>
    </div>
    <div class="uk-width-medium-1-2" >
        <div id="gradeChat" class="mGraph" style="border: none;overflow-y:auto;overflow-x:hidden;width: 100%;height: 550px; margin: 0 auto"></div>
    </div>
</div>

</body>
</html>