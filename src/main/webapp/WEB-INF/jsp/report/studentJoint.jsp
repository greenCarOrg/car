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
        var url = "../chart/chartPage.do?rad="+ Math.random()+"&chartName={1}&schoolId={2}&tdNumber={3}";
        jQuery(document).ready(function () {
            showIncome();
            createSchoolChart();
            createCourseChart();
            createGradeChart();
        });

        function reloadData(){
            $("#grid").data("kendoGrid").dataSource.read();
            showIncome();
            createSchoolChart();
            createCourseChart();
            createGradeChart();
        }
        //在读人数
        function showIncome(){
            var options = {};
            options.schoolId=$("#schoolId").val();
            $.ajax({
                type: "post",
                url: "onStudyingNumber.do",
                data:options,
                dateType:"json",
                success: function (data) {
                    $("#showIncome").html("在读人数:<font color='blue' style='font-weight: bold;'>"+data+"</font>");
                }
            });

        }
        function createSchoolChart(){
            $.getJSON('getTuitionFeeGroupBySchool.do', {
                schoolId:$("#schoolId").val(),
                rad: Math.random()
            }, function (json) {
                var datas = [];
                $.each(json, function (date, item) {
                    datas.push([item.schoolName, item.fee]);
                });

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

        function createCourseChart(){
            var src = url.replace("{1}","courseJoint");
            src = src.replace("{2}",$("#schoolId").val());
            src = src.replace("{3}",2);
            $("#courseIframe").attr("src",src);
        }

        function createGradeChart(){
            var src = url.replace("{1}","gradeJointBS");
            src = src.replace("{2}",$("#schoolId").val());
            src = src.replace("{3}",3);
            $("#gradeIframe").attr("src",src);
        }

    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">

<h3 class="heading_b uk-margin-bottom">在读人数统计</h3>

<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-toolbar">
                <h3 class="md-card-toolbar-heading-text" id="showIncome">
                    在读人数：0
                </h3>
            </div>
            <div class="md-card-content">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"></span>
                            <label>${corporationType}名称</label>
                            <select name="schoolId" id="schoolId" data-md-selectize>
                                <c:forEach items="${schoolList}" var="s">
                                    <option value="${s.schoolId}">${s.schoolName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="uk-width-large-1-5" style="line-height: 56px;padding-top: 4px;">
                        <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                    </div>
                </div>
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-medium-3-5">
                        <kendo:grid name="grid" pageable="true" sortable="true" filterable="true" selectable="true" height="480" resizable="true" >
                            <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/>
                            <kendo:grid-filterable extra="true">
                                <kendo:grid-filterable-messages filter="查询" clear="清除" info="提示" and="并且" or="或者"/>
                                <kendo:grid-filterable-operators>
                                    <kendo:grid-filterable-operators-string contains="包含" eq="等于"/>
                                    <kendo:grid-filterable-operators-number eq="=" lte="<=" gte=">="/>
                                    <kendo:grid-filterable-operators-date eq="=" lte="早于" gte="晚于"/>
                                    <kendo:grid-filterable-operators-enums eq="等于" />
                                </kendo:grid-filterable-operators>
                            </kendo:grid-filterable>
                            <kendo:grid-columns>
                                <kendo:grid-column title="${corporationType}名称" field="schoolName" width="150px" />
                                <kendo:grid-column title="地址" field="address" width="200px" filterable="false"/>
                                <kendo:grid-column title="在读人数" field="studentjoint" template="#= studentJoint>0 ? studentJoint : '--' #" width="100px" filterable="false"/>
                                <kendo:grid-column title="操作" width="100px">
                                    <kendo:grid-column-command>
                                        <kendo:grid-column-commandItem  name="schoolId" text="查看详情" className="grid-button">
                                            <kendo:grid-column-commandItem-click>
                                                <script>
                                                    function viewInfo(e) {
                                                        var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                                                        var querParams = {"schoolId":dataItem.schoolId};
                                                        $('#studentData').datagrid({
                                                            method:'get',
                                                            queryParams:querParams,
                                                            url:'../report/getStudentJointBySchoolId.do',
                                                            fitColumns:true,
                                                            rownumbers:true,
                                                            collapsible:false,
                                                            minimizable:false,
                                                            maximizable:false,
                                                            border:false,
                                                            columns:[[
                                                                {field:'studentName',title:'姓名',align:'center',width:100},
                                                                {field:'studentCode',title:'学号',align:'center',width:120},
                                                                {field:'gradeName',title:'年级',align:'center',width:80},
                                                                {field:'guard1',title:'监护人',align:'center',width:100},
                                                                {field:'guardcontact1',title:'手机号码',align:'center',width:100}
                                                            ]]
                                                        });
                                                        $('#studentInfo').window({
                                                            title:"在读学员详情",
                                                            closable:true,
                                                            closed:true,
                                                            width:600,
                                                            height:400,
                                                            modal:true
                                                        });
                                                        $("#studentInfo").window("open");
                                                    }
                                                </script>
                                            </kendo:grid-column-commandItem-click>
                                        </kendo:grid-column-commandItem>
                                        <kendo:grid-column-commandItem name="OnStudyingNumber" text="查看趋势图" className="grid-button">
                                            <kendo:grid-column-commandItem-click>
                                                <script>
                                                    function viewOnStudyingNumberInfo(e) {
                                                        var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                                                        window.location.href="../report/toChartOnStudyingNumberPage.do?schoolId="+dataItem.schoolId+"&schoolName="+dataItem.schoolName;
                                                    }
                                                </script>
                                            </kendo:grid-column-commandItem-click>
                                        </kendo:grid-column-commandItem>
                                    </kendo:grid-column-command>
                                </kendo:grid-column>
                            </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements">
                                    <kendo:dataSource-schema-model>
                                        <kendo:dataSource-schema-model-fields>
                                            <kendo:dataSource-schema-model-field name="fee" type="number" />
                                        </kendo:dataSource-schema-model-fields>
                                    </kendo:dataSource-schema-model>
                                </kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="../report/pageStudentJoints.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                options.schoolId=$("#schoolId").val();
                                                return JSON.stringify(options);
                                            }
                                        </script>
                                    </kendo:dataSource-transport-parameterMap>
                                </kendo:dataSource-transport>
                            </kendo:dataSource>
                        </kendo:grid>
                    </div>
                    <div class="uk-width-medium-2-5">
                        <div id="schoolChat" class="mGraph" style="min-width: 400px; height: 420px; margin: 0 auto"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-medium-1-2">
        <iframe id="courseIframe" src="" class="md-card" style="border: none;overflow-y:auto;overflow-x:hidden;width: 100%;height: 635px;"></iframe>
    </div>
    <div class="uk-width-medium-1-2" >
        <iframe id="gradeIframe" src="" class="md-card" style="border: none;overflow-y:auto;overflow-x:hidden;width: 100%;height: 635px;"></iframe>
    </div>
</div>
<div id="studentInfo">
    <div id="studentData" ></div>
</div>
</body>
</html>