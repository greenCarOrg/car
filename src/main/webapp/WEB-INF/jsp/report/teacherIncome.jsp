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
        var operateType=0;
        window.onload=function () {
            showIncome();
            formulaSchoolChart();

            $("#grid").data("kendoGrid").showColumn(0);
            $("#grid").data("kendoGrid").hideColumn(1);
            $("#grid").data("kendoGrid").hideColumn(2);
            $("#grid").data("kendoGrid").hideColumn(3);
            $("#grid").data("kendoGrid").hideColumn(4);
            $("#grid").data("kendoGrid").hideColumn(5);
            $("#grid").data("kendoGrid").showColumn(7);
            $("#grid").data("kendoGrid").showColumn(8);

            $("#grid").data("kendoGrid").hideColumn(9);
            $("#grid").data("kendoGrid").hideColumn(10);

            $("#grid").data("kendoGrid").showColumn(11);
            $("#grid").data("kendoGrid").hideColumn(12);
            $("#grid").data("kendoGrid").hideColumn(13);
            $("#grid").data("kendoGrid").hideColumn(14);
        }

        function reloadData(){
            $("#grid").data("kendoGrid").dataSource.read();
            formulaSchoolChart();
            showIncome();
        }

        function reset(){
            $("#year").data('kendoComboBox').select(0)
            $("#month").data("kendoComboBox").select(0);
            $("#schoolIds").data("kendoMultiSelect").value('');
            $("#teacherIds").data("kendoMultiSelect").value('');
            $("#subjectIds").data("kendoMultiSelect").value('');
            $("#courseIds").data("kendoMultiSelect").value('');
            $("#sectionIds").data("kendoMultiSelect").value('');
            $("#gradeIds").data("kendoMultiSelect").value('');
            onChangeSchool();
            onChangeSubject();
            onChangeSection();
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
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").showColumn(7);
                $("#grid").data("kendoGrid").showColumn(8);

                $("#grid").data("kendoGrid").hideColumn(9);
                $("#grid").data("kendoGrid").hideColumn(10);

                $("#grid").data("kendoGrid").showColumn(11);
                $("#grid").data("kendoGrid").hideColumn(12);
                $("#grid").data("kendoGrid").hideColumn(13);
                $("#grid").data("kendoGrid").hideColumn(14);

            }else if(type==1){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").showColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(7);
                $("#grid").data("kendoGrid").hideColumn(8);

                $("#grid").data("kendoGrid").showColumn(9);
                $("#grid").data("kendoGrid").showColumn(10);

                $("#grid").data("kendoGrid").showColumn(11);
                $("#grid").data("kendoGrid").hideColumn(12);
                $("#grid").data("kendoGrid").showColumn(13);
                $("#grid").data("kendoGrid").hideColumn(14);
            }else if(type==2){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").showColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(7);
                $("#grid").data("kendoGrid").hideColumn(8);

                $("#grid").data("kendoGrid").hideColumn(9);
                $("#grid").data("kendoGrid").hideColumn(10);

                $("#grid").data("kendoGrid").hideColumn(11);
                $("#grid").data("kendoGrid").hideColumn(12);
                $("#grid").data("kendoGrid").hideColumn(13);
                $("#grid").data("kendoGrid").showColumn(14);
            }else if(type==3){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").showColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(7);
                $("#grid").data("kendoGrid").hideColumn(8);

                $("#grid").data("kendoGrid").hideColumn(9);
                $("#grid").data("kendoGrid").hideColumn(10);

                $("#grid").data("kendoGrid").hideColumn(11);
                $("#grid").data("kendoGrid").hideColumn(12);
                $("#grid").data("kendoGrid").hideColumn(13);
                $("#grid").data("kendoGrid").showColumn(14);
            }else if(type==4){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").showColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(7);
                $("#grid").data("kendoGrid").hideColumn(8);

                $("#grid").data("kendoGrid").hideColumn(9);
                $("#grid").data("kendoGrid").hideColumn(10);

                $("#grid").data("kendoGrid").hideColumn(11);
                $("#grid").data("kendoGrid").hideColumn(12);
                $("#grid").data("kendoGrid").hideColumn(13);
                $("#grid").data("kendoGrid").showColumn(14);
            }else if(type==5){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").showColumn(5);
                $("#grid").data("kendoGrid").hideColumn(7);
                $("#grid").data("kendoGrid").hideColumn(8);

                $("#grid").data("kendoGrid").hideColumn(9);
                $("#grid").data("kendoGrid").hideColumn(10);

                $("#grid").data("kendoGrid").hideColumn(11);
                $("#grid").data("kendoGrid").hideColumn(12);
                $("#grid").data("kendoGrid").hideColumn(13);
                $("#grid").data("kendoGrid").showColumn(14);
            }
            var obj = $("#data"+type).siblings();
            $("#data"+type).css("background","rebeccapurple");
            $.each(obj,function(i,v){
                $(v).css("background","");
            });
            //重新检索数据
            reloadData();
        }

        function showIncome(){
            var options = {};
            options.year=$("#year").val();
            options.month=$("#month").val();
            // 教学点
            var schoolIds = $("#schoolIds").data("kendoMultiSelect").value();
            if(schoolIds!=null&&schoolIds.length>0){
                options.schoolIds = schoolIds;
            }
            // 老师
            var teacherIds = $("#teacherIds").data("kendoMultiSelect").value();
            if(teacherIds.length>0){
                options.teacherIds = teacherIds;
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
            options.operateType = operateType;
            $.ajax({
                type: "post",
                traditional: true,
                url: "../report/getFormulaAllCount.do",
                data:options,
                dateType:"json",
                success: function (data) {
                    var html = "当前结果：总计"+data.totalCount+"条记录";
                    if(data.rewardMoney!=undefined && data.rewardMoney!=null){
                        html = html+",标准计算课酬："+data.rewardMoney+"元 ";
                    }
                    if(data.monthRewardMoney1!=undefined && data.monthRewardMoney1!=null){
                        html = html+"| 按月课时计算课酬："+data.monthRewardMoney1+"元 ";
                    }
                    if(data.monthRewardMoney2!=undefined && data.monthRewardMoney2!=null){
                        html = html+"| 按月课收计算课酬："+data.monthRewardMoney2+"元 ";
                    }
                    if(data.rewardMoneyTotal!=undefined && data.rewardMoneyTotal!=null){
                        html = html+"| 课酬总额："+data.rewardMoneyTotal+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                    }
                    $("#showIncome").html(html);
                }
            });
        }

        //教学点改变,重新加载老师下拉框
        function onChangeSchool() {
            var options = {};
            options.type="teacher";
            // 教学点
            var schoolIds = $("#schoolIds").data("kendoMultiSelect").value();
            if(schoolIds!=null&&schoolIds.length>0){
                options.schoolIds = schoolIds;
            }
            $.ajax({
                type: "post",
                traditional: true,
                url: "../combobox/getComboxData.do",
                data:options,
                dateType:"json",
                success: function (data) {
                    if (data!= null) {
                        var teacherIds = $("#teacherIds").getKendoMultiSelect();
                        teacherIds.dataSource.data(data);
                    }
                }
            });
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

        //报表-课酬按老师
        function formulaSchoolChart() {
            var params={};
            // 教学点
            var schoolIds = $("#schoolIds").data("kendoMultiSelect").value();
            if(schoolIds.length>0){
                params.schoolIds = schoolIds;
            }
            // 老师
            var teacherIds = $("#teacherIds").data("kendoMultiSelect").value();
            if(teacherIds.length>0){
                params.teacherIds = teacherIds;
            }
            // 学科
            var subjectIds = $("#subjectIds").data("kendoMultiSelect").value();
            if(subjectIds.length>0){
                params.subjectIds = subjectIds;
            }
            // 课程
            var courseIds = $("#courseIds").data("kendoMultiSelect").value();
            if(courseIds.length>0){
                params.courseIds = courseIds;
            }
            // 学段
            var sectionIds = $("#sectionIds").data("kendoMultiSelect").value();
            if(sectionIds.length>0){
                params.sectionIds = sectionIds;
            }
            // 年级
            var gradeIds = $("#gradeIds").data("kendoMultiSelect").value();
            if(gradeIds.length>0){
                params.gradeIds = gradeIds;
            }
            params.year=$("#year").data("kendoComboBox").value();
            params.month=$("#month").data("kendoComboBox").value();
            params.operateType = operateType;


            $.ajax({
                type: "post",
                traditional: true,
                url: "../report/formulaIncomeSchoolChart.do",
                data:params,
                dateType:"json",
                success: function (data) {
                    if (data!= null) {
                        var teachers = new Array();
                        var reMoney = new Array();
                        var reMoney1 = new Array();
                        var reMoney2 = new Array();
                        for(var i=0;i<data.formulaIncomeSchoolChart.length;i++){
                            teachers[i] = data.formulaIncomeSchoolChart[i].teacherName;
                            reMoney[i] = data.formulaIncomeSchoolChart[i].rewardMoney;
                            reMoney1[i] = data.formulaIncomeSchoolChart[i].monthRewardMoney1;
                            reMoney2[i] = data.formulaIncomeSchoolChart[i].monthRewardMoney2;
                        }

                        //教学点柱状图
                        var series = [{
                            data: reMoney,
                            color:"#66ccff"
                        }, {
                            data: reMoney1,
                            color:"#66FF33"
                        }, {
                            data: reMoney2,
                            color:"#FFCC00"
                        }];


                        //教学点柱状图
                        $("#formulaSchoolChart").kendoChart({
                            renderAs: "canvas",
                            title: {
                                text: data.startDate+"至"+data.endDate
                            },
                            legend: {
                                position: "bottom"
                            },
                            seriesDefaults: {
                                type: "column",
                                stack: true
                            },
                            series: series,
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
                                line: {
                                    visible: false
                                }
                            },
                            categoryAxis: {
                                min: 0,
                                max: 10,
                                labels: {
                                    rotation: "auto"
                                },
                                categories: teachers,
                                majorGridLines: {
                                    visible: false
                                }
                            },
                            tooltip: {
                                visible: true,
                                format: "{0}"
                            }
                        });
                    }
                }
            });
        }

        /**
         * 报表生成图片并下载
         * @param fileName:下载后保存的文件
         */
        function downloadCharts(fileName) {
            var chart = $("#formulaSchoolChart").data("kendoChart");
            var imageDataURL = chart.imageDataURL();
            var timestamp= (new Date()).Format("yyyyMMddhhmmss");
            FileUtil.saveImageFile(imageDataURL,fileName+"_"+timestamp);
        }

        /**
         * 导出营业收入
         */
        function exportTeacherIncome() {

            var params="";
            // 教学点
            var schoolIds = $("#schoolIds").data("kendoMultiSelect").value();
            if(schoolIds.length>0){
                params+="&schoolIds="+schoolIds;
            }
            // 老师
            var teacherIds = $("#teacherIds").data("kendoMultiSelect").value();
            if(teacherIds.length>0){
                params+="&teacherIds="+teacherIds;
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
            params+="&year="+$("#year").data("kendoComboBox").value();
            params+="&month="+$("#month").data("kendoComboBox").value();
            params+="&operateType="+operateType;
            document.location.href="exportTeacherIncome.do?"+params;
        }

        /**
         * 查看课酬明细
         * @param e
         * @param type
         */
        function showRewardInfo(e,type) {
            if(operateType==0){
                document.getElementById("rewardInfo").style.display = "";

                var dataItem = $("#grid").data("kendoGrid").dataItem($($(e).parent()).closest("tr"));
                var querParams = {"teacherId": dataItem.teacherId,"year":$("#year").val(),"month":$("#month").val(),"rewardType":type,"operateType":operateType};
                var title = "按月课";
                var thereTitle = "月课时数";

                if(type==0){
                    title+= "时计算课酬明细（"+dataItem.teacherName+"）";
                }else {
                    title+= "收计算课酬明细（"+dataItem.teacherName+"）";
                    thereTitle = "月课收数";
                }

                $('#rewardData').datagrid({
                    method: 'get',
                    queryParams: querParams,
                    url: '../report/getRewardDataInfo.do',
                    fitColumns: true,
                    rownumbers: true,
                    collapsible: false,
                    minimizable: false,
                    maximizable: false,
                    border: false,
                    singleSelect: true,
                    columns: [[
                        {field: 'calculation_date', title: '计算日期', align: 'center', width: 200},
                        {field: 'year_month', title: '月份', align: 'center', width: 100},
                        {field: 'month_hour', title: thereTitle, align: 'center', width: 100},
                        {field: 'reward_money', title: '课酬', align: 'center', width: 100},
                        {field: 'remark', title: '备注', align: 'center', width: 300}
                    ]]
                });
                $('#rewardInfo').window({
                    title: title,
                    closable: true,
                    closed: true,
                    width: 800,
                    height: 600,
                    modal: true
                });
                $("#rewardInfo").window("open");
            }
        }

    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">老师课酬
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
                <a class="md-btn md-btn-primary" id="data0" href="javascript:changePage(0)" style="border-top-right-radius: 0px;border-bottom-right-radius: 0px; background: rebeccapurple;">按老师</a>
                <a class="md-btn md-btn-primary" id="data1" href="javascript:changePage(1)" style="margin:-4px;border-radius: 0;">按校区</a>
                <a class="md-btn md-btn-primary" id="data2" href="javascript:changePage(2)" style="margin:-4px;border-radius: 0;">按学科</a>
                <a class="md-btn md-btn-primary" id="data3" href="javascript:changePage(3)" style="margin:-4px;border-radius: 0;">按课程</a>
            <%--><a class="md-btn md-btn-primary" id="data4" href="javascript:changePage(4)" style="margin:-4px;border-radius: 0;">按学段</a>
                <a class="md-btn md-btn-primary" id="data5" href="javascript:changePage(5)" style="margin:0px;border-top-left-radius: 0px;border-bottom-left-radius: 0px;">按年级</a>
                <--%>
            </div>

            <div class="md-card-content large-padding" style="height: 100%;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-4">
                        <span class="uk-input-group" style="width: 50%;float: left">
                            <label>年份:</label>
                            <kendo:comboBox name="year" filter="contains" placeholder="选择年份..." index="0" suggest="true" dataTextField="year" dataValueField="year" style="width: 65%;">
                                <kendo:dataSource data="${yearList}"></kendo:dataSource>
                            </kendo:comboBox>
                        </span>
                        <span class="uk-input-group" style="width: 50%;">
                            <label>月份:</label>
                            <kendo:comboBox name="month" filter="contains" placeholder="选择月份..." index="0" suggest="true" dataTextField="month" dataValueField="key" style="width: 75%;">
                                <kendo:dataSource data="${monthList}"></kendo:dataSource>
                            </kendo:comboBox>
                        </span>
                    </div>

                    <div class="uk-width-large-1-3">
                        <div class="uk-input-group" style="width: 123%;line-height: 26px;float: left">
                            <label style="float: left;">${corporationType}:</label>
                            <kendo:multiSelect name="schoolIds" change="onChangeSchool" dataTextField="value"  dataValueField="key" placeholder="所有${corporationType}" style="width:80%;float:left;">
                                <kendo:dataSource data="${schoolList}">
                                </kendo:dataSource>
                            </kendo:multiSelect>
                        </div>
                    </div>

                    <div class="uk-width-large-1-3">
                        <div class="uk-input-group" style="width: 120%;line-height: 26px;margin-left:21%">
                            <label style="float: left;">教师:</label>
                            <kendo:multiSelect name="teacherIds" dataTextField="value"  dataValueField="key" placeholder="所有老师" style="width:80%;float:left;">
                                <kendo:dataSource data="${teacherList}">
                                </kendo:dataSource>
                            </kendo:multiSelect>
                        </div>
                    </div>
                    <div class="uk-width-large-1-5">
                    </div>
                    <div class="uk-width-large-1-5">
                    </div>
                </div>

                <div class="uk-grid" data-uk-grid-margin id="selectOtherList">
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group" style="width: 100%;line-height: 26px;">
                            <label style="float: left;">学科:</label>
                            <kendo:multiSelect name="subjectIds" change="onChangeSubject" dataTextField="value"  dataValueField="key" placeholder="所有学科" style="width:80%;float:left;">
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
                        <div class="uk-input-group" style="width: 80%;line-height: 26px;">
                            <label style="float: left;">学段:</label>
                            <kendo:multiSelect name="sectionIds" change="onChangeSection" dataTextField="value"  dataValueField="key" placeholder="所有学段" style="width:80%;float:left;">
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
                                <span style="width: 40px;height: 18px;background-color: #66ccff; display: block;float: left;"></span><span>&nbsp;标准计算课酬</span>
                            </div>
                            <div id="hour" style="float: left;margin-left: 10px;width: 200px">
                                <span style="width: 40px;height: 18px;background-color: #66FF33; display: block;float: left;"></span><span>&nbsp;按月课时计算课酬</span>
                            </div>
                            <div id="income" style="float: left;margin-left: 10px;width: 200px">
                                <span style="width: 40px;height: 18px;background-color: #FFCC00; display: block;float: left;"></span><span>&nbsp;按月课收计算课酬</span>
                            </div>

                            <div  style="float: none">
                            </div>
                            <a onclick="downloadCharts('老师课酬');" style="float: right;color: blue;" class="k-button">下载</a>
                            <div style="clear: both;"></div>
                            <div id="formulaSchoolChart"></div>
                        </div>
                    </div>
                </div>

                <div class="md-card-toolbar" style="padding: 0px;">
                    <span id="showIncome" style="background-color: #cccccc;margin-bottom: 5px;font-weight: 600"></span>
                    <a onclick='exportTeacherIncome();' style='float: right;color: blue;margin-top: 10px;' class='k-button'>导出</a>
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
                                <kendo:grid-column title="老师" field="teacherName" width="15%" filterable="false" hidden="false"/>
                                <kendo:grid-column title="${corporationType}" field="teacherName" width="15%" filterable="false" hidden="true"/>
                                <kendo:grid-column title="学科" field="teacherName" width="15%" filterable="false" hidden="true"/>
                                <kendo:grid-column title="课程" field="teacherName" width="15%" filterable="false" hidden="true"/>
                                <kendo:grid-column title="学段" field="teacherName" width="15%" filterable="false" hidden="true"/>
                                <kendo:grid-column title="年级" field="teacherName" width="15%" filterable="false" hidden="true"/>
                                <kendo:grid-column title="标准计算课酬（元）" field="rewardMoney" width="15%" filterable="false" template="<div style='text-align:right'>#=rewardMoney#</div>"/>
                                <kendo:grid-column title="按月课时计算课酬（元）" field="monthRewardMoney1" filterable="false"  width="15%" template="<div style='text-align:right'>#=monthRewardMoney1>0 ? '<a onclick=\"showRewardInfo(this,0)\">'+monthRewardMoney1+'</a>' : '---' #</div>"/>
                                <kendo:grid-column title="按月课收计算课酬（元）" field="monthRewardMoney2" width="15%" filterable="false" template="<div style='text-align:right'>#=monthRewardMoney2>0 ? '<a onclick=\"showRewardInfo(this,1)\">'+monthRewardMoney2+'</a>' : '---' #</div>"/>
                                <kendo:grid-column title="按月课时计算课酬（元）" field="monthRewardMoney1" filterable="false"  width="15%" template="<div style='text-align:right'>#=monthRewardMoney1 #</div>"/>
                                <kendo:grid-column title="按月课收计算课酬（元）" field="monthRewardMoney2" width="15%" filterable="false" template="<div style='text-align:right'>#=monthRewardMoney2 #</div>"/>
                                <kendo:grid-column title="课酬总额（元）" field="rewardMoneyTotal" width="15%" filterable="false" template="<div style='text-align:right'>#=rewardMoneyTotal#</div>"/>
                                <kendo:grid-column title="课酬占课收比例" field="remark" width="15%" filterable="false" template="<div style='text-align:right'>#=remark#</div>" hidden="false"/>
                                <kendo:grid-column title="课酬总额占比" field="remark" width="15%" filterable="false" template="<div style='text-align:right'>#=remark#</div>" hidden="true"/>
                                <kendo:grid-column title="占比" field="remark" width="15%" filterable="false" template="<div style='text-align:right'>#=remark#</div>" hidden="true"/>
                            </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements">
                                </kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="pageTeacherRewardByMyBatis.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                options["year"]=$("#year").val();
                                                options["month"]=$("#month").val();
                                                options["operateType"]= operateType;
                                                // 教学点
                                                var schoolIds = $("#schoolIds").data("kendoMultiSelect").value();
                                                if(schoolIds.length>0){
                                                    options["schoolIds"]=schoolIds;
                                                }
                                                // 老师
                                                var teacherIds = $("#teacherIds").data("kendoMultiSelect").value();
                                                if(teacherIds.length>0){
                                                    options["teacherIds"] = teacherIds;
                                                }
                                                // 学科
                                                var subjectIds = $("#subjectIds").data("kendoMultiSelect").value();
                                                if(subjectIds.length>0){
                                                    options["subjectIds"] = subjectIds;
                                                }
                                                // 课程
                                                var courseIds = $("#courseIds").data("kendoMultiSelect").value();
                                                if(courseIds.length>0){
                                                    options["courseIds"] = courseIds;
                                                }
                                                // 学段
                                                var sectionIds = $("#sectionIds").data("kendoMultiSelect").value();
                                                if(sectionIds.length>0){
                                                    options["sectionIds"] = sectionIds;
                                                }
                                                // 年级
                                                var gradeIds = $("#gradeIds").data("kendoMultiSelect").value();
                                                if(gradeIds.length>0){
                                                    options["gradeIds"] = gradeIds;
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