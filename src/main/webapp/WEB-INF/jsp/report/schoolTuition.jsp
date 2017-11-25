<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>妈妈有约管理系统</title>
    <!-- altair admin -->
    <link rel="stylesheet" href="${ctx}/assets/css/main.min.css" media="all">
    <script>
        var operateType = 0;
        window.onload=function () {
            schoolTuitionChart();
            $("#grid").data("kendoGrid").showColumn(0);
            $("#grid").data("kendoGrid").hideColumn(1);
            $("#grid").data("kendoGrid").hideColumn(2);
            $("#grid").data("kendoGrid").hideColumn(3);
            $("#grid").data("kendoGrid").hideColumn(4);
            $("#grid").data("kendoGrid").showColumn(5);
            $("#grid").data("kendoGrid").showColumn(6);
            $("#grid").data("kendoGrid").showColumn(7);
        }

        //切换页面
        function changePage(type){
            operateType=type;
            if(type==0){
                $("#grid").data("kendoGrid").showColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").showColumn(5);
                $("#grid").data("kendoGrid").showColumn(6);
                $("#grid").data("kendoGrid").showColumn(7);

            }else if(type==1){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").showColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(6);
                $("#grid").data("kendoGrid").showColumn(7);
            }else if(type==2){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").showColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(6);
                $("#grid").data("kendoGrid").showColumn(7);
            }else if(type==3){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").showColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(6);
                $("#grid").data("kendoGrid").showColumn(7);
            }else if(type==5){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").showColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(6);
                $("#grid").data("kendoGrid").showColumn(7);
            }
            var obj = $("#data"+type).siblings();
            $("#data"+type).css("background","rebeccapurple");
            $.each(obj,function(i,v){
                $(v).css("background","");
            });
            //重新检索数据
            reloadData();
        }


        function reloadData(){
            $("#grid").data("kendoGrid").dataSource.read();
            schoolTuitionChart();
        }

        // 重置按钮
        function reset(){
            $("#schoolIds").data("kendoMultiSelect").value('');
            $("#subjectIds").data("kendoMultiSelect").value('');
            $("#courseIds").data("kendoMultiSelect").value('');
            $("#sectionIds").data("kendoMultiSelect").value('');
            $("#gradeIds").data("kendoMultiSelect").value('');
            onChangeSubject();
            onChangeSection();
        }

        //报表-班级营收按校区
        function schoolTuitionChart() {
            debugger;
            var options = {};

            var startDate = $("#startDate").data("kendoDatePicker").value();
            var endDate = $("#endDate").data("kendoDatePicker").value();
            if (StrUtil.isNotEmpty(startDate)) {
                startDate = startDate.Format("yyyy-MM-dd");
            }
            if (StrUtil.isNotEmpty(endDate)) {
                endDate = endDate.Format("yyyy-MM-dd");
            }

            options.startDate = startDate;
            options.endDate = endDate;
            // 教学点
            var schoolIds = $("#schoolIds").data("kendoMultiSelect").value();
            if (schoolIds.length > 0) {
                options.schoolIds = schoolIds;
            }
            // 学科
            var subjectIds = $("#subjectIds").data("kendoMultiSelect").value();
            if (subjectIds.length > 0) {
                options.subjectIds = subjectIds;
            }
            // 课程
            var courseIds = $("#courseIds").data("kendoMultiSelect").value();
            if (courseIds.length > 0) {
                options.courseIds = courseIds;
            }
            // 学段
            var sectionIds = $("#sectionIds").data("kendoMultiSelect").value();
            if (sectionIds.length > 0) {
                options.sectionIds = sectionIds;
            }
            // 年级
            var gradeIds = $("#gradeIds").data("kendoMultiSelect").value();
            if (gradeIds.length > 0) {
                options.gradeIds = gradeIds;
            }
            options.searchType = operateType;

            $.ajax({
                type: "post",
                traditional: true,
                url: "../report/schoolTuitionChart.do",
                data: options,
                dateType: "json",
                success: function (data) {
                    if (data != null) {
                        // 教学点柱状图
                        $("#schoolTuitionChart").kendoChart({
                            renderAs: "canvas",
                            title: {
                                text: data.startDate + "至" + data.endDate
                            },
                            dataSource: {
                                data: data.schoolTuitionChart
                            },
                            categoryAxis: {
                                min: 0,
                                max: 10,
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
                                field: "classRevenueMoney",
                                categoryField: "itemName",
                                color: "#66ccff"
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

                        // 总数量
                        var tuitionData = data.schoolTuitionData;
                        var html = "当前结果：总计" + tuitionData.total_num + "条记录,";
                        if (operateType == 1 && tuitionData.classRevenueInputMoney != undefined && tuitionData.classRevenueInputMoney != null) {
                            html = html + "充值：" + tuitionData.classRevenueInputMoney + "元 ";
                        }
                        if (operateType == 1 && tuitionData.classRevenueOutMoney != undefined && tuitionData.classRevenueOutMoney != null) {
                            html = html + "| 退费：" + tuitionData.classRevenueOutMoney + "元 ";
                        }
                        html = html + "| 班级营收：" + tuitionData.classRevenueMoney + "元 ";
                        $("#showTuition").html(html);
                    }
                }
            });
        }

        /**
         * 报表生成图片并下载
         * @param fileName:下载后保存的文件
         */
        function downloadCharts(fileName) {
            var chart = $("#schoolTuitionChart").data("kendoChart");
            var imageDataURL = chart.imageDataURL();
            var timestamp= (new Date()).Format("yyyyMMddhhmmss");
            FileUtil.saveImageFile(imageDataURL,fileName+"_"+timestamp);
        }

        /**
         * 导出班级营收
         */
        function exportSchoolTuition() {
            var params="";
            var startDate=$("#startDate").data("kendoDatePicker").value();
            var endDate=$("#endDate").data("kendoDatePicker").value();
            if(StrUtil.isNotEmpty(startDate)){
                startDate=startDate.Format("yyyy-MM-dd");
            }
            if(StrUtil.isNotEmpty(endDate)){
                endDate=endDate.Format("yyyy-MM-dd");
            }
            params+="&startDate="+startDate;
            params+="&endDate="+endDate;
            // 教学点
            var schoolIds = $("#schoolIds").data("kendoMultiSelect").value();
            if(schoolIds.length>0){
                params+="&schoolIds="+schoolIds;
            }
            // 学科
            var subjectIds = $("#subjectIds").data("kendoMultiSelect").value();
            if(subjectIds.length>0){
                params+="&subjectIds="+subjectIds;
            }
            // 课程
            var courseIds = $("#courseIds").data("kendoMultiSelect").value();
            if(courseIds.length>0){
                params+="&courseIds="+courseIds;
            }
            // 学段
            var sectionIds = $("#sectionIds").data("kendoMultiSelect").value();
            if(sectionIds.length>0){
                params+="&sectionIds="+sectionIds;
            }
            // 年级
            var gradeIds = $("#gradeIds").data("kendoMultiSelect").value();
            if(gradeIds.length>0){
                params+="&gradeIds="+gradeIds;
            }
            params+="&searchType="+operateType;
            document.location.href="exportSchoolTuition.do?"+params;
        }

        //学科改变,重新加载课程下拉框
        function onChangeSubject() {
            var options = {};
            options.type="course";
            // 学科
            var subjectIds = $("#subjectIds").data("kendoMultiSelect").value();
            if(subjectIds!=null && subjectIds.length>0){
                options.subjectIds = subjectIds;
            }
            $.ajax({
                type: "post",
                traditional: true,
                url: "../combobox/getComboxData.do",
                data:options,
                dateType:"json",
                success: function (data) {
                    if (data!= null) {
                        var courseIds = $("#courseIds").getKendoMultiSelect();
                        courseIds.dataSource.data(data);
                    }
                }
            });
        }

        //学段改变,重新加载年级下拉框
        function onChangeSection() {
            var options = {};
            options.type="grade";
            // 学段
            var sectionIds = $("#sectionIds").data("kendoMultiSelect").value();
            if(sectionIds!=null && sectionIds.length>0){
                options.sectionIds = sectionIds;
            }
            $.ajax({
                type: "post",
                traditional: true,
                url: "../combobox/getComboxData.do",
                data:options,
                dateType:"json",
                success: function (data) {
                    if (data!= null) {
                        var gradeIds = $("#gradeIds").getKendoMultiSelect();
                        gradeIds.dataSource.data(data);
                    }
                }
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
<h3 class="heading_b uk-margin-bottom">班级营收
    <c:if test="${cropManageModel==0}">
        <img src="../images/icon/fullTime.png" alt="" style="float: right;">
    </c:if>
    <c:if test="${cropManageModel==1}">
        <img src="../images/icon/outside.png" alt="" style="float: right;">
    </c:if>
    <c:if test="${cropManageModel==2}">
        <img src="../images/icon/interest.png" alt="" style="float: right;">
    </c:if>
</h3>

<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">

            <div style="padding :0px;text-align: left">
                <a class="md-btn md-btn-primary" id="data0" href="javascript:changePage(0)" style="border-top-right-radius: 0px;border-bottom-right-radius: 0px; background: rebeccapurple;">按校区</a>
                <a class="md-btn md-btn-primary" id="data1" href="javascript:changePage(1)" style="margin:-4px;border-radius: 0;">按学科</a>
                <a class="md-btn md-btn-primary" id="data2" href="javascript:changePage(2)" style="margin:-4px;border-radius: 0;">按学段</a>
                <a class="md-btn md-btn-primary" id="data3" href="javascript:changePage(3)" style="margin:-4px;border-radius: 0;">按年级</a>
                <a class="md-btn md-btn-primary" id="data5" href="javascript:changePage(5)" style="margin:0px;border-top-left-radius: 0px;border-bottom-left-radius: 0px;">按课程</a>
            </div>

            <div class="md-card-content large-padding" style="height: 100%;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-3">
                        <div class="uk-input-group" style="width: 100%">
                            <label>时间:</label>
                            <kendo:datePicker name="startDate" value="${startDate}" style="width:40%" title="开始日期" onchange="checkDate('start')"></kendo:datePicker>至
                            <kendo:datePicker name="endDate"   value="${endDate}" style="width:40%" title="结束日期" onchange="checkDate('end')"></kendo:datePicker>
                        </div>
                    </div>

                    <div class="uk-width-large-1-2">
                        <div class="uk-input-group" style="width: 123%;line-height: 26px;float: left">
                            <label style="float: left;">${corporationType}:</label>
                            <kendo:multiSelect name="schoolIds" dataTextField="schoolName" dataValueField="schoolId" placeholder="所有${corporationType}" style="width:80%;float:left;">
                                <kendo:dataSource data="${schoolList}">
                                </kendo:dataSource>
                            </kendo:multiSelect>
                        </div>
                    </div>
                </div>

                <div class="uk-grid" data-uk-grid-margin id="selectOtherList">
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group" style="width: 100%;line-height: 26px;">
                            <label style="float: left;">学科:</label>
                            <kendo:multiSelect name="subjectIds"  dataTextField="value" change="onChangeSubject" dataValueField="key" placeholder="所有学科" style="width:80%;float:left;">
                                <kendo:dataSource data="${subjectList}">
                                </kendo:dataSource>
                            </kendo:multiSelect>
                        </div>
                    </div>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group" style="width: 100%;line-height: 26px;">
                            <label style="float: left;">课&nbsp;&nbsp;&nbsp;程:</label>
                            <kendo:multiSelect name="courseIds"  dataTextField="value"  dataValueField="key" placeholder="所有课程" style="width:80%;float:left;">
                                <kendo:dataSource data="${courseList}">
                                </kendo:dataSource>
                            </kendo:multiSelect>
                        </div>
                    </div>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group" style="width: 100%;line-height: 26px;">
                            <label style="float: left;">学段:</label>
                            <kendo:multiSelect name="sectionIds"  dataTextField="value" change="onChangeSection"  dataValueField="key" placeholder="所有学段" style="width:80%;float:left;">
                                <kendo:dataSource data="${sectionList}">
                                </kendo:dataSource>
                            </kendo:multiSelect>
                        </div>
                    </div>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group" style="width: 100%;line-height: 26px;">
                            <label style="float: left;">年级:</label>
                            <kendo:multiSelect name="gradeIds"  dataTextField="value"  dataValueField="key" placeholder="所有年级" style="width:80%;float:left;">
                                <kendo:dataSource data="${gradeList}">
                                </kendo:dataSource>
                            </kendo:multiSelect>
                        </div>
                    </div>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group" style="float: right">
                            <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                            <a class="md-btn" style="margin-left: 20px;" href="javascript:reset();">重置</a>
                        </div>
                    </div>
                </div>

                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-1">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"></span>
                            <div id="base" style="float: left;width: 200px">
                                <span style="width: 40px;height: 18px;background-color: #66ccff; display: block;float: left;"></span><span>&nbsp;班级营收</span>
                            </div>
                            <div  style="float: none">
                            </div>
                            <a onclick="downloadCharts('班级营收');" style="float: right;color: blue;" class="k-button">下载</a>
                            <div style="clear: both;"></div>
                            <div id="schoolTuitionChart"></div>
                        </div>
                    </div>
                </div>

                <div class="md-card-toolbar" style="padding: 0px;">
                    <span id="showTuition" style="background-color: #cccccc;margin-bottom: 5px;font-weight: 600"></span>
                    <a onclick='exportSchoolTuition();' style='float: right;color: blue;margin-top: 10px;' class='k-button'>导出</a>
                </div>

                <div class="uk-grid">
                    <div class="uk-width-medium-1-1">
                        <kendo:grid name="grid" pageable="true" sortable="true" filterable="false" selectable="false" height="500" style="border-width:0px;">
                            <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/>
                            <kendo:grid-filterable extra="true">
                                <kendo:grid-filterable-messages filter="查询" clear="清除" info="提示" and="并且" or="或者"/>
                                <kendo:grid-filterable-operators>
                                    <kendo:grid-filterable-operators-string contains="包含" eq="等于"/>
                                    <kendo:grid-filterable-operators-number eq="=" lte="<=" gte=">="/>
                                    <kendo:grid-filterable-operators-date eq="=" lte="早于" gte="晚于"/>
                                    <kendo:grid-filterable-operators-enums eq="等于"/>
                                </kendo:grid-filterable-operators>
                            </kendo:grid-filterable>
                            <kendo:grid-columns>
                                <kendo:grid-column title="校区" field="itemName" width="20%" filterable="false" hidden="false"/>
                                <kendo:grid-column title="学科" field="itemName" width="20%" filterable="false" hidden="true"/>
                                <kendo:grid-column title="学段" field="itemName" width="20%" filterable="false" hidden="true"/>
                                <kendo:grid-column title="年级" field="itemName" width="20%" filterable="false" hidden="true"/>
                                <kendo:grid-column title="课程" field="itemName" width="20%" filterable="false" hidden="true"/>
                                <kendo:grid-column title="充值金额（元）" field="classRevenueInputMoney" width="20%" filterable="false" template="<div style='text-align:right'>#=classRevenueInputMoney#</div>" hidden="true"/>
                                <kendo:grid-column title="退班金额（元）" field="classRevenueOutMoney" width="20%" filterable="false" template="<div style='text-align:right'>#=classRevenueOutMoney#</div>" hidden="true"/>
                                <kendo:grid-column title="班级营收（元）" field="classRevenueMoney" width="20%" filterable="false" template="<div style='text-align:right'>#=classRevenueMoney#</div>" hidden="true"/>
                                <kendo:grid-column title="占比(元)" field="remark" width="20%" filterable="false"/>
                            </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements">
                                </kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="getClassRevenueInfo.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                var startDate=$("#startDate").data("kendoDatePicker").value();
                                                var endDate=$("#endDate").data("kendoDatePicker").value();
                                                if(StrUtil.isNotEmpty(startDate)){
                                                    startDate=startDate.Format("yyyy-MM-dd");
                                                }
                                                if(StrUtil.isNotEmpty(endDate)){
                                                    endDate=endDate.Format("yyyy-MM-dd");
                                                }
                                                options.startDate = startDate;
                                                options.endDate = endDate;
                                                options.searchType = operateType;

                                                // 教学点
                                                var schoolIds = $("#schoolIds").data("kendoMultiSelect").value();
                                                if(schoolIds.length>0){
                                                    options.schoolIds = schoolIds;
                                                }
                                                // 学科
                                                var subjectIds = $("#subjectIds").data("kendoMultiSelect").value();
                                                if(subjectIds.length>0){
                                                    options.subjectIds = subjectIds;
                                                }
                                                // 课程
                                                var courseIds = $("#courseIds").data("kendoMultiSelect").value();
                                                if(courseIds.length>0){
                                                    options.courseIds = courseIds;
                                                }
                                                // 学段
                                                var sectionIds = $("#sectionIds").data("kendoMultiSelect").value();
                                                if(sectionIds.length>0){
                                                    options.sectionIds = sectionIds;
                                                }
                                                // 年级
                                                var gradeIds = $("#gradeIds").data("kendoMultiSelect").value();
                                                if(gradeIds.length>0){
                                                    options.gradeIds = gradeIds;
                                                }

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
<div id="rewardInfo" style="display: none">
    <div id="rewardData"></div>
</div>
</body>
</html>