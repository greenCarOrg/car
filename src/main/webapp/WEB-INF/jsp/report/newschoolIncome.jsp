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
    <link rel="stylesheet" href="/assets/css/main.min.css" media="all">
    <script type="text/javascript" src="../kendo/js/messages/kendo.messages.zh-user.min.js"></script>
    <script type="application/javascript">
        //定义操作类型
        var operateType=0;
        /**
         *初始化渲染界面
         */
        window.onload=function () {

            $("#showIncome").html("当前结果: 应消耗课次数:<font color='blue' style='font-weight: bold;'>"+0+"</font>" +
                    "&nbsp;&nbsp;&nbsp;实消耗课次数:<font color='blue' style='font-weight: bold;'>"+0+"</font>" +
                    "&nbsp;&nbsp;&nbsp;课耗率:<font color='blue' style='font-weight: bold;'>"+0+"</font>" +
                    "&nbsp;&nbsp;&nbsp;应课收:<font color='blue' style='font-weight: bold;'>"+0+"</font>" +
                    "&nbsp;&nbsp;&nbsp;实课收（现金）:<font color='blue' style='font-weight: bold;'>"+0+"</font>"+
                    "&nbsp;&nbsp;&nbsp;实课收（代金券）:<font color='blue' style='font-weight: bold;'>"+0+"</font>"+
                    "&nbsp;&nbsp;&nbsp;实课收总计:<font color='blue' style='font-weight: bold;'>"+0+"</font>"
            );

            $("#grid").data("kendoGrid").showColumn(0);
            $("#grid").data("kendoGrid").hideColumn(1);
            $("#grid").data("kendoGrid").hideColumn(2);
            $("#grid").data("kendoGrid").hideColumn(3);
            $("#grid").data("kendoGrid").hideColumn(4);
            $("#grid").data("kendoGrid").hideColumn(5);
            $("#grid").data("kendoGrid").hideColumn(6);
            $("#grid").data("kendoGrid").hideColumn(7);
            schoolIncomeChart1();
            showIncome();
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
            // 加载老师
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
                        options.type="teachClass";
                        // 加载班级
                        $.ajax({
                            type: "post",
                            traditional: true,
                            url: "../combobox/getComboxData.do",
                            data:options,
                            dateType:"json",
                            success: function (data1) {
                                if (data1!= null) {
                                    var classIds = $("#classIds").getKendoMultiSelect();
                                    classIds.dataSource.data(data1);

                                    options.type="student";
                                    // 加载学生
                                    $.ajax({
                                        type: "post",
                                        traditional: true,
                                        url: "../combobox/getComboxData.do",
                                        data:options,
                                        dateType:"json",
                                        success: function (data2) {
                                            if (data2!= null) {
                                                var studentIds = $("#studentIds").getKendoMultiSelect();
                                                studentIds.dataSource.data(data2);
                                            }
                                        }
                                    });
                                }
                            }
                        });
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

        //课程改变,重新加载班级下拉框
        function onChangeCourse() {
            var options = {};
            options.type="teachClass";
            // 学科
            var courseIds = $("#courseIds").data("kendoMultiSelect").value();
            if(courseIds!=null && courseIds.length>0){
                options.courseIds = courseIds;
            }
            $.ajax({
                type: "post",
                traditional: true,
                url: "../combobox/getComboxData.do",
                data:options,
                dateType:"json",
                success: function (data) {
                    if (data!= null) {
                        var classIds = $("#classIds").getKendoMultiSelect();
                        classIds.dataSource.data(data);
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

        /**
         * 报表生成图片并下载  课酬统计课收
         * @param fileName:下载后保存的文件
         */
        function downloadCharts(fileName) {
            var chart = $("#schoolIncomeChart").data("kendoChart");
            var imageDataURL = chart.imageDataURL();
            var timestamp= (new Date()).Format("yyyyMMddhhmmss");
            FileUtil.saveImageFile(imageDataURL,fileName+"_"+timestamp);
        }

        /**
         * 报表生成图片并下载  课酬统计消耗金额
         * @param fileName:下载后保存的文件
         */
        function downloadCharts2(fileName) {
            var chart = $("#schoolIncomeChart2").data("kendoChart");
            var imageDataURL = chart.imageDataURL();
            var timestamp= (new Date()).Format("yyyyMMddhhmmss");
            FileUtil.saveImageFile(imageDataURL,fileName+"_"+timestamp);
        }

        /**
         * 表头：营业收入-统计汇总
         * @param params:查询参数
         */
        function showIncome(){
            var options = {};
            var sectionIds=$("#sectionIds").data("kendoMultiSelect").value();
            if(sectionIds!=null && sectionIds.length>0){
                options.sectionIds=sectionIds;
            }
            var courseIds=$("#courseIds").data("kendoMultiSelect").value();
            if(courseIds!=null && courseIds.length>0){
                options.courseIds=courseIds;
            }
            var gradeIds=$("#gradeIds").data("kendoMultiSelect").value();
            if(gradeIds!=null && gradeIds.length>0){
                options.gradeIds=gradeIds;
            }
            var classIds=$("#classIds").data("kendoMultiSelect").value();
            if(classIds!=null && classIds.length>0){
                options.classIds=classIds;
            }
            var schoolIds=$("#schoolIds").data("kendoMultiSelect").value();
            if(schoolIds!=null && schoolIds.length>0){
                options.schoolIds=schoolIds;
            }
            var subjectIds=$("#subjectIds").data("kendoMultiSelect").value();
            if(subjectIds!=null && subjectIds.length>0){
                options.subjectIds=subjectIds;
            }
            var teacherIds=$("#teacherIds").data("kendoMultiSelect").value();
            if(teacherIds!=null && teacherIds.length>0){
                options.teacherIds=teacherIds;
            }
            var studentIds = $("#studentIds").data("kendoMultiSelect").value();
            if(studentIds!=null && studentIds.length>0){
                options.studentIds=studentIds;
            }
            options.operateType=operateType;
            options.startDate=$("#startDate").val()+" 00:00:00";
            options.endDate=$("#endDate").val()+" 23:59:59";

            $.ajax({
                type: "post",
                traditional: true,
                url: "../report/getSchoolIncomeAll.do",
                data:options,
                dateType:"json",
                success: function (data) {
                    if (data!= null) {

                        $("#showIncome").html("当前结果: 应消耗课次数:<font color='blue' style='font-weight: bold;'>"+data.all_consume+"</font>" +
                                "&nbsp;&nbsp;&nbsp;实消耗课次数:<font color='blue' style='font-weight: bold;'>"+data.use_consume+"</font>" +
                                "&nbsp;&nbsp;&nbsp;课耗率:<font color='blue' style='font-weight: bold;'>"+data.hour_ratio+"</font>" +
                                "&nbsp;&nbsp;&nbsp;应课收:<font color='blue' style='font-weight: bold;'>"+data.should_income+"</font>" +
                                "&nbsp;&nbsp;&nbsp;实课收（现金）:<font color='blue' style='font-weight: bold;'>"+data.money_consume+"</font>"+
                                "&nbsp;&nbsp;&nbsp;实课收（代金券）:<font color='blue' style='font-weight: bold;'>"+data.voucher_consume+"</font>"+
                                "&nbsp;&nbsp;&nbsp;实课收总计:<font color='blue' style='font-weight: bold;'>"+data.all_income+"</font>"
                        );
                    }else{
                        $("#showIncome").html("当前结果: 应消耗课次数:<font color='blue' style='font-weight: bold;'>"+0+"</font>" +
                                "&nbsp;&nbsp;&nbsp;实消耗课次数:<font color='blue' style='font-weight: bold;'>"+0+"</font>" +
                                "&nbsp;&nbsp;&nbsp;课耗率:<font color='blue' style='font-weight: bold;'>"+0+"</font>" +
                                "&nbsp;&nbsp;&nbsp;应课收:<font color='blue' style='font-weight: bold;'>"+0+"</font>" +
                                "&nbsp;&nbsp;&nbsp;实课收（现金）:<font color='blue' style='font-weight: bold;'>"+0+"</font>"+
                                "&nbsp;&nbsp;&nbsp;实课收（代金券）:<font color='blue' style='font-weight: bold;'>"+0+"</font>"+
                                "&nbsp;&nbsp;&nbsp;实课收总计:<font color='blue' style='font-weight: bold;'>"+0+"</font>"
                        );
                    }
                }
            });
        }

        /**
         * 报表  课收
         */
        function schoolIncomeChart1() {
            var params={};
            debugger;
            var sectionIds=$("#sectionIds").data("kendoMultiSelect").value();
            var courseIds=$("#courseIds").data("kendoMultiSelect").value();
            var gradeIds=$("#gradeIds").data("kendoMultiSelect").value();
            var classIds=$("#classIds").data("kendoMultiSelect").value();
            var schoolIds=$("#schoolIds").data("kendoMultiSelect").value();
            var teacherIds=$("#teacherIds").data("kendoMultiSelect").value();
            var subjectIds=$("#subjectIds").data("kendoMultiSelect").value();
            var studentIds = $("#studentIds").data("kendoMultiSelect").value();
            if (schoolIds!= null &&schoolIds!= undefined && schoolIds.length!=0) {
                params.schoolIds=schoolIds;
            }
            if (sectionIds!= null && sectionIds.length!=0) {
                params.sectionIds=sectionIds;
            }
            if (courseIds!= null && courseIds.length!=0) {
                params.courseIds=courseIds;
            }
            if (gradeIds!= null && gradeIds.length!=0) {
                params.gradeIds=gradeIds;
            }
            if (classIds!= null && classIds.length!=0) {
                params.classIds=classIds;
            }
            if(subjectIds!=null  && subjectIds.length>0){
                params.subjectIds=subjectIds;
            }
            if(teacherIds!=null && teacherIds.length>0){
                params.teacherIds=teacherIds;
            }
            if(studentIds!=null  && studentIds.length>0){
                params.studentIds=studentIds;
            }
            params.startDate=$("#startDate").val();
            params.endDate=$("#endDate").val();
            params.operateType=operateType;

            $.ajax({
                type: "post",
                traditional: true,
                url: "../report/schoolIncomeChart.do",
                data:params,
                dateType:"json",
                success: function (data) {
                    if (data!= null) {
                        var itemName = new Array();
                        var shouldIncome = new Array();
                        var realMoney = new Array();
                        var realVoucher = new Array();
                        var allConsume = new Array();
                        var noConsume = new Array();
                        var useConsume = new Array();
                        for(var i=0;i<data.schoolIncomeChart.length;i++){
                            itemName[i] = data.schoolIncomeChart[i].item_name;
                            shouldIncome[i] = data.schoolIncomeChart[i].should_income;
                            realMoney[i] = data.schoolIncomeChart[i].money_consume;
                            realVoucher[i] = data.schoolIncomeChart[i].voucher_consume;
                            allConsume[i]=data.schoolIncomeChart[i].all_consume;
                            noConsume[i]=data.schoolIncomeChart[i].no_consume;
                            useConsume[i]=data.schoolIncomeChart[i].use_consume;
                        }
                        //教学点柱状图--课收数
                        var series1 = [{
                            name:"应课收",
                            stack: {
                                group: "should"
                            },
                            data: shouldIncome,
                            color:"#66ccff"
                        }, {
                            name:"实课收(现金)",
                            stack: {
                                group: "real"
                            },
                            data: realMoney,
                            color:"#66FF33"
                        }, {
                            name:"实课收(代金劵)",
                            stack: {
                                group: "real"
                            },
                            data: realVoucher,
                            color:"#FFCC00"
                        }];

                        //教学点柱状图--课次消耗数
                        var series2 = [ {
                            name:"应消耗",
                            stack: {
                                group: "all"
                            },
                            data: allConsume,
                            color:"#66FF33"
                        }, {
                            name:"实消耗",
                            stack: {
                                group: "use"
                            },
                            data: useConsume,
                            color:"#FFCC00"
                        }];

                        $("#schoolIncomeChart").kendoChart({
                            renderAs: "canvas",
                            title: {text: params.startDate+"至"+params.endDate},
                            legend: {position: "bottom"},
                            seriesDefaults: {
                                type: "column",
                                stack: true
                            },
                            series: series1,
                            pannable: {lock: "y"},
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
                                categories: itemName,
                                majorGridLines: {
                                    visible: false
                                }
                            },
                            tooltip: {
                                visible: true,
                                template: "#= series.name #: #= value #"
                            }
                        });

                        $("#schoolIncomeChart2").kendoChart({
                            renderAs: "canvas",
                            title: {text:  params.startDate+"至"+params.endDate},
                            legend: {position: "bottom"},
                            seriesDefaults: {
                                type: "column",
                                stack: true
                            },
                            series: series2,
                            pannable: {lock: "y"},
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
                                categories: itemName,
                                majorGridLines: {
                                    visible: false
                                }
                            },
                            tooltip: {
                                visible: true,
                                template: "#= series.name #: #= value #"
                            }
                        });
                    }
                }
            });
        }

        /**
         * 初始化查询
         */
        function reloadData(){

            var startDate=$("#startDate").val();
            var endDate=$("#endDate").val();
            var oDate1 = new Date(startDate);
            var oDate2 = new Date(endDate);
            if(oDate1.getTime() > oDate2.getTime()){
                UIkit.notify({message: "开始时间不得晚于结束时间", status: 'danger', timeout: 1000, pos: 'top-center'});
            } else {
                $("#showIncome").html("当前结果: 应消耗课次数:<font color='blue' style='font-weight: bold;'>"+0+"</font>" +
                        "&nbsp;&nbsp;&nbsp;实消耗课次数:<font color='blue' style='font-weight: bold;'>"+0+"</font>" +
                        "&nbsp;&nbsp;&nbsp;课耗率:<font color='blue' style='font-weight: bold;'>"+0+"</font>" +
                        "&nbsp;&nbsp;&nbsp;应课收:<font color='blue' style='font-weight: bold;'>"+0+"</font>" +
                        "&nbsp;&nbsp;&nbsp;实课收（现金）:<font color='blue' style='font-weight: bold;'>"+0+"</font>"+
                        "&nbsp;&nbsp;&nbsp;实课收（代金券）:<font color='blue' style='font-weight: bold;'>"+0+"</font>"+
                        "&nbsp;&nbsp;&nbsp;实课收总计:<font color='blue' style='font-weight: bold;'>"+0+"</font>"
                );
                $("#grid").data("kendoGrid").dataSource.read();
                schoolIncomeChart1();
                showIncome();
            }
        }

        /**
         * 导出报表
         */
        function downLoadIncome(){
            var sectionIds=$("#sectionIds").data("kendoMultiSelect").value();
            var courseIds=$("#courseIds").data("kendoMultiSelect").value();
            var gradeIds=$("#gradeIds").data("kendoMultiSelect").value();
            var classIds=$("#classIds").data("kendoMultiSelect").value();
            var schoolIds=$("#schoolIds").data("kendoMultiSelect").value();
            var subjectIds=$("#subjectIds").data("kendoMultiSelect").value();
            var teacherIds=$("#teacherIds").data("kendoMultiSelect").value();
            var startDate=$("#startDate").val();
            var endDate=$("#endDate").val();
            var studentIds=$("#studentIds").data("kendoMultiSelect").value();
            document.location.href="exportSchoolIncome.do?sectionIds="+sectionIds+"&studentIds="+studentIds+"&startDate="+startDate+"&endDate="+endDate+"&courseIds="+courseIds+"&gradeIds="+gradeIds+"&classIds="+classIds+"&schoolIds="+schoolIds+"&subjectIds="+subjectIds+"&teacherIds="+teacherIds+"&operateType="+operateType;
        }

        /**
         * 重置查询条件
         */
        function resetQuery(){
            $("#schoolIds").data("kendoMultiSelect").value('');
            $("#teacherIds").data("kendoMultiSelect").value('');
            $("#subjectIds").data("kendoMultiSelect").value('');
            $("#courseIds").data("kendoMultiSelect").value('');
            $("#classIds").data("kendoMultiSelect").value('');
            $("#sectionIds").data("kendoMultiSelect").value('');
            $("#gradeIds").data("kendoMultiSelect").value('');
            $("#studentIds").data("kendoMultiSelect").value('');
            var d = new Date();
            var year = d.getFullYear();
            var month = d.getMonth() + 1; // 记得当前月是要+1的
            month = month < 10 ? ("0" + month) : month;
            var dt = d.getDate();
            dt = dt < 10 ? ("0" + dt) : dt;
            var today = year + "-" + month + "-" + dt;
            $("#startDate").val(today);

            var y=year,m=d.getMonth() + 4;
            if(m>12){
                m=(m-12);
                y=y+1;
            }
            if(m<10){
                m=("0"+m);
            }
            var bef=y+"-"+m+"-"+dt;
            $("#endDate").val(bef);
        }

        /**
         * 切换菜单更新当前菜单颜色
         * @type {number}
         */
        function changeColor(type){
            var obj = $("#data"+type).siblings();
            $("#data"+type).css("background","rebeccapurple");
            $.each(obj,function(i,v){
                $(v).css("background","");
            });

            operateType=type;
            // 0.按校区 1.按老师 2.按学生 3.按学科 4.按课程 5.按班级 6.按学段 7.按年级
            if(type==0){
                $("#grid").data("kendoGrid").showColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(6);
                $("#grid").data("kendoGrid").hideColumn(7);
                $("#grid").data("kendoGrid").showColumn(16);
                $("#grid").data("kendoGrid").showColumn(17);
            }else if(type==1){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").showColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(6);
                $("#grid").data("kendoGrid").hideColumn(7);
                $("#grid").data("kendoGrid").hideColumn(16);
                $("#grid").data("kendoGrid").hideColumn(17);
            }else if(type==2){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").showColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(6);
                $("#grid").data("kendoGrid").hideColumn(7);
                $("#grid").data("kendoGrid").showColumn(16);
                $("#grid").data("kendoGrid").showColumn(17);
            }else if(type==3){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").showColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(6);
                $("#grid").data("kendoGrid").hideColumn(7);
                $("#grid").data("kendoGrid").hideColumn(16);
                $("#grid").data("kendoGrid").hideColumn(17);
            }else if(type==4){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").showColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(6);
                $("#grid").data("kendoGrid").hideColumn(7);
                $("#grid").data("kendoGrid").hideColumn(16);
                $("#grid").data("kendoGrid").hideColumn(17);
            }else if(type==5){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").showColumn(5);
                $("#grid").data("kendoGrid").hideColumn(6);
                $("#grid").data("kendoGrid").hideColumn(7);
                $("#grid").data("kendoGrid").hideColumn(16);
                $("#grid").data("kendoGrid").hideColumn(17);
            }else if(type==6){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").showColumn(6);
                $("#grid").data("kendoGrid").hideColumn(7);
                $("#grid").data("kendoGrid").hideColumn(16);
                $("#grid").data("kendoGrid").hideColumn(17);
            }else if(type==7){
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(6);
                $("#grid").data("kendoGrid").showColumn(7);
                $("#grid").data("kendoGrid").hideColumn(16);
                $("#grid").data("kendoGrid").hideColumn(17);
            }
            $("#grid").data("kendoGrid").dataSource.read();
            schoolIncomeChart1();
            showIncome();
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
<h3 class="heading_b uk-margin-bottom">课耗收入
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
            <div style="padding :0px;text-align: left"><%--0.按校区 1.按老师 2.按学生 3.按学科 4.按课程 5.按班级 6.按学段 7.按年级--%>
                <a class="md-btn md-btn-primary" id="data0" href="javascript:changeColor(0)" style="border-top-right-radius: 0px;border-bottom-right-radius: 0px; background: rebeccapurple;">按校区</a>
                <a class="md-btn md-btn-primary" id="data1" href="javascript:changeColor(1)" style="margin:-4px;border-radius: 0;">按老师</a>
                <a class="md-btn md-btn-primary" id="data2" href="javascript:changeColor(2)" style="margin:-4px;border-radius: 0;">按学生</a>
                <a class="md-btn md-btn-primary" id="data3" href="javascript:changeColor(3)" style="margin:-4px;border-radius: 0;">按学科</a>
                <a class="md-btn md-btn-primary" id="data4" href="javascript:changeColor(4)" style="margin:-4px;border-radius: 0;">按课程</a>
                <a class="md-btn md-btn-primary" id="data5" href="javascript:changeColor(5)" style="margin:-4px;border-radius: 0;">按班级</a>
                <a class="md-btn md-btn-primary" id="data6" href="javascript:changeColor(6)" style="margin:-4px;border-radius: 0;">按学段</a>
                <%--<a class="md-btn md-btn-primary" id="data7" href="javascript:changeColor(7)" style="margin:0px;border-top-left-radius: 0px;border-bottom-left-radius: 0px;">按年级</a>
                <--%>
</div>
<div class="md-card-content large-padding" style="height: 100%;">

<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-large-1-4">
        <div class="uk-input-group" style="width: 100%">
            时间:<kendo:datePicker name="startDate" value="${startDate}" style="width:40%" title="开始日期" format="yyyy-MM-dd" onchange="checkDate('start')"></kendo:datePicker>至
            <kendo:datePicker name="endDate"   value="${endDate}" style="width:40%" title="结束日期" format="yyyy-MM-dd" onchange="checkDate('end')"></kendo:datePicker>
        </div>
    </div>

    <div class="uk-width-large-1-4">
        <div class="uk-input-group" style="width: 110%;line-height: 26px;float: left">
            <label style="float: left;">${corporationType}:</label>
            <kendo:multiSelect name="schoolIds" change="onChangeSchool" dataTextField="value"  dataValueField="key"
                               placeholder="全部..." style="width:80%;float:left;">
                <kendo:dataSource data="${schoolList}"></kendo:dataSource>
            </kendo:multiSelect>
        </div>
    </div>

    <div class="uk-width-large-1-4">
        <div class="uk-input-group" style="width: 110%;line-height: 26px;float: left">
            <label style="float: left;">教师</label>
            <kendo:multiSelect name="teacherIds"  dataTextField="value" dataValueField="key"
                               placeholder="全部..." style="width:80%;float:left;">
                <kendo:dataSource data="${teacherList}"></kendo:dataSource>
            </kendo:multiSelect>
        </div>
    </div>

    <div class="uk-width-large-1-4">
        <div class="uk-input-group" style="width: 110%;line-height: 26px;float: left">
            <label style="float: left;">学生</label>
            <kendo:multiSelect name="studentIds"  dataTextField="value" dataValueField="key"
                               placeholder="全部..." style="width:80%;float:left;">
                <kendo:dataSource data="${studentList}"></kendo:dataSource>
            </kendo:multiSelect>
        </div>
    </div>
</div>

<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-large-1-5">
        <div class="uk-input-group" style="width: 100%;line-height: 26px;float: left">
            <label style="float: left;">学科</label>
            <kendo:multiSelect name="subjectIds" change="onChangeSubject" dataTextField="value" dataValueField="key"
                               placeholder="全部..." style="width:80%;float:left;">
                <kendo:dataSource data="${subjectMapList}"></kendo:dataSource>
            </kendo:multiSelect>
        </div>
    </div>
    <div class="uk-width-large-1-5">
        <div class="uk-input-group" style="width: 100%;line-height: 26px;float: left">
            <label style="float: left;">课程</label>
            <kendo:multiSelect name="courseIds" change="onChangeCourse" dataTextField="value" dataValueField="key"
                               placeholder="全部..." style="width:80%;float:left;">
                <kendo:dataSource data="${courseList}"></kendo:dataSource>
            </kendo:multiSelect>
        </div>
    </div>
    <div class="uk-width-large-1-5">
        <div class="uk-input-group" style="width: 100%;line-height: 26px;float: left">
            <label style="float: left;">班级</label>
            <kendo:multiSelect name="classIds"  dataTextField="value" dataValueField="key"
                               placeholder="全部..." style="width:80%;float:left;">
                <kendo:dataSource data="${classList}"></kendo:dataSource>
            </kendo:multiSelect>
        </div>
    </div>
    <div class="uk-width-large-1-5">
        <div class="uk-input-group" style="width: 100%;line-height: 26px;float: left">
            <label style="float: left;">学段</label>
            <kendo:multiSelect name="sectionIds" change="onChangeSection" dataTextField="value" dataValueField="key"
                               placeholder="全部..." style="width:80%;float:left;">
                <kendo:dataSource data="${sectionList}"></kendo:dataSource>
            </kendo:multiSelect>
        </div>
    </div>

    <div class="uk-width-large-1-5">
        <div class="uk-input-group" style="width: 100%;line-height: 26px;float: left">
            <label style="float: left;">年级</label>
            <kendo:multiSelect name="gradeIds"  dataTextField="value" dataValueField="key"
                               placeholder="全部..." style="width:80%;float:left;">
                <kendo:dataSource data="${gradeList}"></kendo:dataSource>
            </kendo:multiSelect>
        </div>
    </div>
</div>

<div class="uk-grid" data-uk-grid-margin>

    <div class="uk-width-large-1-4"></div>
    <div class="uk-width-large-1-4"></div>
    <div class="uk-width-large-1-4"></div>
    <div class="uk-width-large-1-4" >
        <div class="uk-input-group" style="float: right">
            <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
            <a class="md-btn md-btn-warning" href="javascript:resetQuery();">重置</a>
        </div>
    </div>
</div>

<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-large-1-1">
        <div class="uk-input-group">
            <span class="uk-input-group-addon"></span>
                <a onclick="downloadCharts('课程统计消耗');" style="float: right;color: blue;" class="k-button">下载</a>
            <div style="clear: both;"></div>
            <div id="schoolIncomeChart"></div>
        </div>
    </div>
</div>

<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-large-1-1">
        <div class="uk-input-group">
            <span class="uk-input-group-addon"></span>
                <a onclick="downloadCharts2('课程统计消耗');" style="float: right;color: blue;" class="k-button">下载</a>
            <div style="clear: both;"></div>
            <div id="schoolIncomeChart2"></div>
        </div>
    </div>
</div>

<div class="md-card-toolbar" style="padding: 0px;">
    <h3 class="md-card-toolbar-heading-text" id="showIncome"></h3>
    <a onclick="downLoadIncome();" style="float: right;color: blue;" class="k-button">导出</a>
</div>
<div class="uk-grid">
    <div class="uk-width-medium-1-1">
        <kendo:grid name="grid" pageable="true" sortable="true" filterable="false" selectable="true" resizable="true" >
            <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/>
            <kendo:grid-columns>
                <kendo:grid-column title="校区" field="item_name" width="10%" />
                <kendo:grid-column title="老师" field="item_name" width="10%" />
                <kendo:grid-column title="学生" field="item_name" width="10%" />
                <kendo:grid-column title="学科" field="item_name" width="10%" />
                <kendo:grid-column title="课程" field="item_name" width="10%" />
                <kendo:grid-column title="班级" field="item_name" width="10%" />
                <kendo:grid-column title="学段" field="item_name" width="10%" />
                <kendo:grid-column title="年级" field="item_name" width="10%" />
                <kendo:grid-column title="应消耗课次数" field="all_consume" width="10%"/>
                <kendo:grid-column title="实消耗课次数" field="use_consume" width="10%"/>
                <kendo:grid-column title="课耗率" field="hour_ratio" width="10%"/>
                <kendo:grid-column title="应课收(元)" field="should_income" width="10%"/>
                <kendo:grid-column title="实课收(现金)(元)" field="money_consume" width="10%"/>
                <kendo:grid-column title="现金占比" field="money_ratio" width="10%"/>
                <kendo:grid-column title="实课收(代金券)(元)" field="voucher_consume" width="10%"/>
                <kendo:grid-column title="代金券占比" field="voucher_ratio" width="10%"/>
                <kendo:grid-column title="补充课收" field="over_money" width="10%"/>
                <kendo:grid-column title="其他课收" field="free_money" width="10%"/>
                <kendo:grid-column title="实课收总计(元)" field="all_income"  width="10%"/>
                <kendo:grid-column title="实课收总占比" field="income_ratio" width="10%" />
            </kendo:grid-columns>
            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="false" serverSorting="true">
                <kendo:dataSource-schema data="content" total="totalElements"></kendo:dataSource-schema>
                <kendo:dataSource-transport>
                    <kendo:dataSource-transport-read url="../report/pagedStudentCurrFee.do" type="POST" contentType="application/json"/>
                    <kendo:dataSource-transport-parameterMap>
                        <script>
                            function parameterMap(options, type) {
                                var sectionIds=$("#sectionIds").data("kendoMultiSelect").value();
                                if(sectionIds.length>0){
                                    options.sectionIds = sectionIds;
                                }
                                var gradeIds=$("#gradeIds").data("kendoMultiSelect").value();
                                if(gradeIds.length>0){
                                    options.gradeIds = gradeIds;
                                }
                                var classIds=$("#classIds").data("kendoMultiSelect").value();
                                if(classIds.length>0){
                                    options.classIds = classIds;
                                }
                                var courseIds=$("#courseIds").data("kendoMultiSelect").value();
                                if(courseIds.length>0){
                                    options.courseIds = courseIds;
                                }
                                var subjectIds=$("#subjectIds").data("kendoMultiSelect").value();
                                if(subjectIds.length>0){
                                    options.subjectIds = subjectIds
                                }
                                var teacherIds=$("#teacherIds").data("kendoMultiSelect").value();
                                if(teacherIds.length>0){
                                    options.teacherIds = teacherIds;
                                }
                                var schoolIds=$("#schoolIds").data("kendoMultiSelect").value();
                                if(schoolIds.length>0){
                                    options.schoolIds = schoolIds;
                                }
                                var studentIds=$("#studentIds").data("kendoMultiSelect").value();
                                if(studentIds.length>0){
                                    options.studentIds = studentIds;
                                }
                                options.operateType=operateType;
                                options.startDate=$("#startDate").val()+" 00:00:00";
                                options.endDate=$("#endDate").val()+" 23:59:59";
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
<style>
.mask {
position: absolute; top: 0px; filter: alpha(opacity=60); background-color: #777;
z-index: 1002; left: 0px;
opacity:0.5; -moz-opacity:0.5;
}
</style>
</body>
</html>