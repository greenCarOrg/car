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
    <%--<link rel="stylesheet" href="${ctx}/assets/css/main.min.css" media="all">--%>
    <script type="text/javascript" src="../kendo/js/messages/kendo.messages.zh-course.min.js"></script>
    <script type="application/javascript">
        //定义操作类型
        var operateType=0;

        function reloadData(){
            $("#grid").data("kendoGrid").dataSource.read();
            showIncome();
            studentAnaysisChart();
        }
        //在读人数
        function showIncome(){
            var params={};
            var schoolIds=$("#schoolIds").data("kendoMultiSelect").value();
            if (schoolIds!= null&&schoolIds!= undefined &&schoolIds.length!=0) {
                //将数组转换为字符串
                params.schoolIds=schoolIds;
            }
            var obj = document.getElementsByName("stat");
            var stats = [];
            for(k in obj){
                if(obj[k].checked){
                    stats.push(obj[k].value);
                }
            }
            params.stats=stats;
            params.type=operateType;
            $.ajax({
                type: 'get',
                url: '../report/queryStudentCountBySchool.do',
                dataType:'json',
                data:params,
                success: function (data) {
                    if(data!=null){
                        $("#showIncome").html("学员人数:<font color='blue' style='font-weight: bold;'>"+data+"</font>");
                    }
                }
            });
        }

        window.onload=function () {
            $("#grid").data("kendoGrid").showColumn(0);
            $("#grid").data("kendoGrid").hideColumn(1);
            $("#grid").data("kendoGrid").hideColumn(2);
            $("#tpe").hide();
            $("#sur").hide();
            studentAnaysisChart();
            showIncome();
        };

        function downLoad(){
            var schoolIds=$("#schoolIds").data("kendoMultiSelect").value();
            var type=operateType;
            var obj = document.getElementsByName("stat");
            var stats = [];
            for(k in obj){
                if(obj[k].checked){
                    stats.push(obj[k].value);
                }
            }
            document.location.href="exportStudentAnaysis.do?schoolIds="+schoolIds+"&type="+type+'&stats='+stats;
        }

        /**
         * 重置查询条件
         */
        function resetQuery(){
            $("#schoolIds").data("kendoMultiSelect").value('');
            $("input[name='stat']").attr("checked","true");
        }


        function changeColor(type){
            var obj = $("#data"+type).siblings();
            $("#data"+type).css("background","rebeccapurple");
            $.each(obj,function(i,v){
                $(v).css("background","");
            });
            operateType=type;

            if(type==0){
                $("#grid").data("kendoGrid").showColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#tpe").hide();
                $("#sur").hide();
                $("#lbl").html("意向状态");
            }else if(type==1){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").showColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#sur").show();
                $("#tpe").hide();
                $("#lbl").html("来&nbsp;&nbsp;&nbsp;&nbsp; 源");
            }else if(type==2){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").showColumn(2);
                $("#tpe").show();
                $("#sur").hide();
                $("#lbl").html("签单类型");
            }

            $("#grid").data("kendoGrid").dataSource.read();
            studentAnaysisChart();
            showIncome();
        }


        /**
         * 报表生成图片并下载
         * @param fileName:下载后保存的文件
         */
        function downloadCharts(fileName) {
            var chart = $("#studentAnaysisChart").data("kendoChart");
            var imageDataURL = chart.imageDataURL();
            var timestamp= (new Date()).Format("yyyyMMddhhmmss");
            FileUtil.saveImageFile(imageDataURL,fileName+"_"+timestamp);
        }

        //报表-营收按校区
        function studentAnaysisChart() {
            var params={};
            var schoolIds=$("#schoolIds").data("kendoMultiSelect").value();
            if (schoolIds!= null&&schoolIds!= undefined &&schoolIds.length!=0) {
                //将数组转换为字符串
                params.schoolIds=schoolIds;
            }
            var obj = document.getElementsByName("stat");
            var stats = [];
            for(k in obj){
                if(obj[k].checked){
                    stats.push(obj[k].value);
                }
            }
            params.stats=stats;
            params.type=operateType;
            $.post("../report/studentAnaysisChart.do",params, function (data) {
                if (data != null) {
                    $("#studentAnaysisChart").kendoChart({
                        renderAs: "canvas",
                        title: {text: "学员分析"},
                        dataSource: {data: data.studentAnaysisChart},
                        categoryAxis: {
                            min: 0,
                            max: 15,
                            labels: {
                                rotation: "auto"
                            }
                        },
                        seriesDefaults: {
                            type: "column",
                            labels: {
                                visible: true,
                                background: "transparent"
                            }
                        },
                        series: [{
                            type: "column",
                            field: "cout",
                            categoryField: "name"
                        }],
                        pannable: {
                            lock: "y"
                        },
                        zoomable: {
                            mousewheel: {
                                lock: "y"
                            },
                            selection: {
                                lock: "y"
                            }
                        },
                        valueAxis: {
                            labels: {
                                skip: 2,
                                step: 2
                            }
                        },
                        tooltip: {
                            visible: true,
                            template: "#= category #: #= value  #"
                        }
                    });
                }
            });
        }
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">学员分析</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div style="padding :0px;text-align: left">
                <a class="md-btn md-btn-primary" id="data0" href="javascript:changeColor(0)" style="border-top-right-radius: 0px;border-bottom-right-radius: 0px; background: rebeccapurple;">按意向状态</a>
                <a class="md-btn md-btn-primary" id="data1" href="javascript:changeColor(1)" style="margin:-4px;border-radius: 0;">按来源</a>
                <%--<a class="md-btn md-btn-primary" id="data2" href="javascript:changeColor(2)" style="margin:-4px;border-radius: 0;">按签单类型</a>--%>
            </div>

            <div class="md-card-content large-padding" style="height: 100%;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group">
                            <label>校区</label>
                            <kendo:multiSelect name="schoolIds" dataTextField="schoolName" style="width: 200px;" filter="contains"  index="0" suggest="true" dataValueField="schoolId" placeholder="选择校区...">
                                <kendo:dataSource data="${schoolList}"></kendo:dataSource>
                            </kendo:multiSelect>
                        </div>
                    </div>

                    <div class="uk-width-large-1-5" style="padding-top:1%;" id="sur" hidden="hidden">
                        <div class="uk-input-group">
                            <input type="checkbox" style="padding-right: 20px;" checked="checked" name="stat" value="0">意向学员&nbsp;&nbsp;
                            <input type="checkbox" style="padding-right: 20px;" checked="checked" name="stat" value="1">在读学员&nbsp;&nbsp;
                            <input type="checkbox" style="padding-right: 20px;" checked="checked" name="stat" value="2">结课学员
                        </div>
                    </div>
                    <div class="uk-width-large-1-5" id="tpe" hidden="hidden">
                        <div class="uk-input-group">
                            <input type="checkbox" name="sign" value="0">在读学员&nbsp;&nbsp;
                            <input type="checkbox" name="sign" value="1">结课学员&nbsp;&nbsp;
                        </div>
                    </div>
                </div>

                <div class="uk-grid" data-uk-grid-margin style="margin-top: 20px;">
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group">
                            <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                            <a class="md-btn md-btn-warning" href="javascript:resetQuery()">重置</a>
                        </div>
                    </div>
                </div>

                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-1">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"></span>
                            <label style="width: 60px;height: 20px;background-color: #FF7617; display: block;text-align: center;" id="lbl">意向状态</label>
                            <a onclick="downloadCharts('学员分析');" style="float: right;color: blue;" class="k-button">下载</a>
                            <div style="clear: both;"></div>
                            <div id="studentAnaysisChart"></div>
                        </div>
                    </div>
                </div>

                <div class="md-card-toolbar">
                    <h3 class="md-card-toolbar-heading-text" id="showIncome">学员人数：0</h3>
                    <a onclick="downLoad();" style="float: right;color: blue;" class="k-button">导出</a>
                </div>

                <div class="uk-grid">
                    <div class="uk-width-medium-1-1">
                        <kendo:grid name="grid"  pageable="false" height="420" sortable="true" filterable="true" selectable="true" resizable="true" >
                            <kendo:grid-pageable refresh="true" pageSizes="false" buttonCount="5"/>
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
                                <kendo:grid-column title="意向状态" field="name" width="30%"  filterable="false"/>
                                <kendo:grid-column title="来源" field="name" width="30%" filterable="false" />
                                <kendo:grid-column title="签单类型" field="name" width="30%"  filterable="false"/>
                                <kendo:grid-column title="学员人数" field="cout" width="30%"  filterable="false"/>
                                <kendo:grid-column title="占比" field="rate" width="30%" filterable="false" />
                            </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements">
                                </kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="../report/pageStudentAnaysis.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                options.schoolIds=$("#schoolIds").data("kendoMultiSelect").value();
                                                options.type=operateType;
                                                var obj = document.getElementsByName("stat");
                                                var stats = [];
                                                for(k in obj){
                                                    if(obj[k].checked){
                                                        stats.push(obj[k].value);
                                                    }
                                                }
                                                options.stats=stats;
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
</body>
</html>