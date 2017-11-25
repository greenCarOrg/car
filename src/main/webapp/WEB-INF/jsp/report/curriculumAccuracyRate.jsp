<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script type="text/javascript" src="../kendo/js/messages/kendo.messages.zh-user.min.js"></script>
    <script>
        window.onload=function () {
            curriculumAccuracyRateChart();
            //默认“Week”按钮选中
            $("#date1").css("background","rebeccapurple");
        }
        //重新加载老师下拉框
        function onChangeSchool() {
            var schoolId=$("#schoolId").data("kendoComboBox").value();
            $.post("../combobox/boxTeachersBySchoolId.do", {schoolId:schoolId}, function (data) {
                if (data!= null) {
                    $("#teacherId").kendoComboBox({
                        dataTextField: "teacherName",
                        dataValueField: "teacherId",
                        dataSource: data,
                        filter: "contains",
                        suggest: true,
                        change:onChangeTeachMethod,
                        index: 0
                    });
                }
            });
            //加载课程
            onChangeTeachMethod();
            //加载班级
            onChangeCourse();
        }
        //重新加载课程和班级下拉框
        function onChangeTeachMethod() {
            var teachMethod=$("#teachMethod").data("kendoComboBox").value();
            var schoolId=$("#schoolId").data("kendoComboBox").value();
            var teacherId=$("#teacherId").data("kendoComboBox").value();
            $.post("../combobox/boxCourseByTeachMethod.do",
                {
                    schoolId:schoolId,
                    teacherId:teacherId,
                    teachMethod:teachMethod
                },
                function (data) {
                if (data!= null) {
                    $("#courseId").kendoComboBox({
                        dataTextField: "courseName",
                        dataValueField: "courseId",
                        dataSource: data,
                        filter: "contains",
                        suggest: true,
                        change:onChangeCourse,
                        index: 0
                    });
                }
            });
            //加载班级
            onChangeCourse();
        }
        //重新班级下拉框
        function onChangeCourse() {
            var teachMethod=$("#teachMethod").data("kendoComboBox").value();
            var courseId=$("#courseId").data("kendoComboBox").value();
            var schoolId=$("#schoolId").data("kendoComboBox").value();
            var teacherId=$("#teacherId").data("kendoComboBox").value();
            $.post("../combobox/boxTeachClassByCourse.do",
                {
                    teachMethod:teachMethod,
                    courseId:courseId,
                    schoolId:schoolId,
                    teacherId:teacherId
                },
                function (data) {
                if (data!= null) {
                    $("#classId").kendoComboBox({
                        dataTextField: "className",
                        dataValueField: "classId",
                        dataSource: data,
                        filter: "contains",
                        suggest: true,
                        index: 0
                    });
                }
            });
        }
        //教学点、老师、课程课次准确率
        function curriculumAccuracyRateChart() {
            var params={};
            params.schoolId=$("#schoolId").data("kendoComboBox").value();
            params.teachMethod=$("#teachMethod").data("kendoComboBox").value();
            params.teacherId=$("#teacherId").data("kendoComboBox").value();
            params.courseId=$("#courseId").data("kendoComboBox").value();
            params.classId=$("#classId").data("kendoComboBox").value();
            var startDate=$("#startDate").data("kendoDatePicker").value();
            var endDate=$("#endDate").data("kendoDatePicker").value();
            if(StrUtil.isNotEmpty(startDate)){
                startDate=startDate.Format("yyyy-MM-dd");
            }
            if(StrUtil.isNotEmpty(endDate)){
                endDate=endDate.Format("yyyy-MM-dd");
            }
            params.startDate=startDate;
            params.endDate=endDate;
            //计算当前排课准确率
            count(params);
            $.post("../report/curriculumAccuracyRateChart.do",params, function (data) {
                if (data!= null) {
                    //教学点柱状图
                    $("#schoolCurriculumAccuracyRate").kendoChart({
                        renderAs: "canvas",
                        dataSource: {
                            data: data.schoolChart
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
                                format: "{0}%",
                                background: "transparent"
                            }
                        },
                        series: [{
                            type: "column",
                            field: "accuracy_rate",
                            categoryField: "school_short_name"
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
                                format: "{0}%",
                                skip: 2,
                                step: 2
                            }
                        }
                    });
                    //老师柱状图
                    $("#teacherCurriculumAccuracyRate").kendoChart({
                        renderAs: "canvas",
                        dataSource: {
                            data: data.teacherChart
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
                                format: "{0}%",
                                background: "transparent"
                            }
                        },
                        series: [{
                            type: "column",
                            field: "accuracy_rate",
                            categoryField: "teacher_name"
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
                                format: "{0}%",
                                skip: 2,
                                step: 2
                            }
                        }
                    });
                    //课程柱状图
                    $("#courseCurriculumAccuracyRate").kendoChart({
                        renderAs: "canvas",
                        dataSource: {
                            data: data.courseChart
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
                                format: "{0}%",
                                background: "transparent"
                            }
                        },
                        series: [{
                            type: "column",
                            field: "accuracy_rate",
                            categoryField: "course_name"
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
                                format: "{0}%",
                                skip: 2,
                                step: 2
                            }
                        }
                    });
                }
            });
        }
        //grid重新获取数据
        function reloadData(){
            var startDate=$("#startDate").data("kendoDatePicker").value();
            var endDate=$("#endDate").data("kendoDatePicker").value();
            if(startDate!=""&&endDate!=""&&startDate >endDate) {
                UIkit.notify({message: '开始时间不能大于结束时间.', status: 'danger', timeout: 1000, pos: 'top-center'});
                return;
            }else{
                $("#grid").data("kendoGrid").dataSource.read();
                curriculumAccuracyRateChart();
            }
        }
        /**
         * 计算当前排课准确率
         * @param params：查询参数
         */
        function count(params){
            $.ajax({
                type: "post",
                url: "currentCurriculumAccuracyRate.do",
                data:params,
                dateType:"json",
                success: function (data) {
                    if (data != null&&data!=""&&data!="undefined"&&data.currentCurriculumAccuracyRate!="undefined") {
                        $("#count").html("当前排课准确率:<font color='blue' style='font-weight: bold;'>"+data.currentCurriculumAccuracyRate+"</font>");
                    }else{
                        $("#count").html("当前排课准确率:<font color='blue' style='font-weight: bold;'>"+0+"%</font>");
                    }
                }
            });
        }
        //全局类型：0-日;1-周；2-月
        var t=1;
        var day=new Date();
        //获取上个星期
        var lastWeek=new Date(new Date(day.setDate(day.getDate()-7)).Format("yyyy-MM-dd"));
        var weekDate=DateUtil.getCurrentWeek(lastWeek);
        var startDate=weekDate[0];
        var endDate=weekDate[1];
        /**
         * 选择相应的日期，计算当前排课准确率
         * @param type：0-日;1-周；2-月
         */
        function selectDate(type){
            t=type;
            var btn1=$("#btn1");
            if (type == 0) {
                startDate=new Date();
                endDate=new Date();
                startDate.setDate(startDate.getDate()-1);
                endDate.setDate(endDate.getDate()-1);
                $("#endDate").data("kendoDatePicker").value(endDate);
                $("#startDate").data("kendoDatePicker").value(startDate);
                btn1.text("日");
            }else if(type == 1){
                var day=new Date();
                //获取上个星期
                var lastWeek=new Date(new Date(day.setDate(day.getDate()-7)).Format("yyyy-MM-dd"));
                var weekDate=DateUtil.getCurrentWeek(lastWeek);
                startDate=weekDate[0];
                endDate=weekDate[1];
                $("#endDate").data("kendoDatePicker").value(endDate);
                $("#startDate").data("kendoDatePicker").value(startDate);
                btn1.text("周");
            }else{
                var day=new Date();
                //获取上个月
                var lastMonth=new Date(new Date(day.setMonth(day.getMonth()-1)).Format("yyyy-MM-dd"));
                var monthDate=DateUtil.getCurrentMonth(lastMonth);
                startDate=monthDate[0];
                endDate=monthDate[1];
                $("#endDate").data("kendoDatePicker").value(endDate);
                $("#startDate").data("kendoDatePicker").value(startDate);
                btn1.text("月");
            }
            //改变按钮颜色
            var obj = $("#date"+t).siblings();
            $("#date"+t).css("background","rebeccapurple");
            $.each(obj,function(i,v){
                $(v).css("background","");
            });
            //将grid重置第一页
            $("#grid").data("kendoGrid").dataSource.page(1);
            reloadData();
        }
        /**
         * 选择相应的日期，计算当前排课准确率
         * @param v：-1->减操作;1-加操作
         */
        function jiaJianDate(v){
            if (v==-1) {
                setDate(-1,-7,-1);
            }else{
                setDate(1,7,1);
            }
            //将grid重置第一页
            $("#grid").data("kendoGrid").dataSource.page(1);
            reloadData();
        }
        /**
         * 设置开始和结束日期
         * @param d：天
         * @param w：星期
         * @param m：月
         */
        function setDate(d,w,m) {
            if (t == 0) {
                startDate.setDate(startDate.getDate()+d);
                endDate.setDate(endDate.getDate()+d);
                $("#startDate").data("kendoDatePicker").value(startDate);
                $("#endDate").data("kendoDatePicker").value(endDate);
            }else if(t == 1){
                startDate.setDate(startDate.getDate()+w);
                endDate.setDate(endDate.getDate()+w);
                $("#startDate").data("kendoDatePicker").value(startDate);
                $("#endDate").data("kendoDatePicker").value(endDate);
            }else{
                //跳到当前日期在做相应的加减月份操作
                var currentDate=new Date(startDate).Format("yyyy-MM-dd");
                currentDate=new Date(currentDate);
                currentDate.setMonth(currentDate.getMonth()+m);
                var monthDate=DateUtil.getCurrentMonth(currentDate);
                startDate=monthDate[0];
                endDate=monthDate[1];
                $("#startDate").data("kendoDatePicker").value(startDate);
                $("#endDate").data("kendoDatePicker").value(endDate);
            }
        }
        /**
         * 悬浮显示课次学生信息
         * @param curcId：班级课次id
         * @param studentCount：学生数量
         */
        function showStudentCurriculum(dataItem) {

            if(StrUtil.isNotEmpty(dataItem.studentCount)&&dataItem.studentCount!= 0){
                $("#window").data("kendoWindow").center();
                $("#window").data("kendoWindow").open();
                $("#studentInfo").html("上课时间:<font color='black'>"+dataItem.curriculum_date+"</font>&nbsp;&nbsp;"+
                                        "学生总数:<font color='black'>"+dataItem.studentCount+"</font>"
                                      );
                $.ajax({
                    type: "post",
                    url: "studentCurriculumAccuracyRate.do",
                    data:{curcId:dataItem.curc_id},
                    dateType:"json",
                    success: function (data) {
                        if (data != null&&data!="") {
                            $("#studentCurriculumAccuracyRate").kendoGrid({
                                dataSource:data,
                                height: 340,
                                filterable: false,
                                sortable: true,
                                selectable:false,
                                columns: [
                                    {field:"student_name",title: "学生姓名",width: "180px"},
                                    {field:"student_code",title: "学号" ,width: "180px"},
                                    {field:"accuracy_rate",title: "出勤准确率",width: "180px"}
                                ]
                            });
                        }
                    }
                });
            }
        }
        //关闭窗口
        function closeStudentCurriculum() {
            $("#window").data("kendoWindow").close();
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
<h3 class="heading_b uk-margin-bottom">排课准确率统计</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-content" style="height: auto !important;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-4">
                        <div class="uk-input-group" style="width: 100%">
                            <label>${corporationType}:</label>
                            <kendo:comboBox name="schoolId" change="onChangeSchool" filter="contains" placeholder="选择校区..." index="0" suggest="true" dataTextField="schoolName" dataValueField="schoolId" style="width: 80%;">
                                <kendo:dataSource data="${schoolList}"></kendo:dataSource>
                            </kendo:comboBox>
                        </div>
                    </div>
                    <div class="uk-width-large-1-4">
                        <div class="uk-input-group" style="width: 100%">
                            <label >老师姓名:</label>
                            <kendo:comboBox name="teacherId" filter="contains" placeholder="选择老师..." index="0" suggest="true" dataTextField="teacherName" dataValueField="teacherId" style="width: 70%;">
                                <kendo:dataSource data="${teachers}"></kendo:dataSource>
                            </kendo:comboBox>
                        </div>
                    </div>
                    <div class="uk-width-large-1-4">
                        <div class="uk-input-group" style="width: 100%">
                            <label>课程类型:</label>
                            <kendo:comboBox name="teachMethod" change="onChangeTeachMethod" filter="contains" placeholder="选择课程类型..." index="0" suggest="true" dataTextField="text" dataValueField="value" style="width: 70%;">
                                <kendo:dataSource data="${teachMethods}"></kendo:dataSource>
                            </kendo:comboBox>
                        </div>
                    </div>
                    <div class="uk-width-large-1-4">
                        <div class="uk-input-group" style="width: 100%">
                            <label>课程名称:</label>
                            <kendo:comboBox name="courseId" change="onChangeCourse" filter="contains" placeholder="选择课程..." index="0" suggest="true" dataTextField="courseName" dataValueField="courseId" style="width: 70%;">
                                <kendo:dataSource data="${courses}"></kendo:dataSource>
                            </kendo:comboBox>
                        </div>
                    </div>
                </div>
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-4">
                        <div class="uk-input-group" style="width: 100%">
                            <label>班级名称:</label>
                            <kendo:comboBox name="classId" filter="contains" placeholder="选择班级..." index="0" suggest="true" dataTextField="className" dataValueField="classId" style="width: 80%;">
                                <kendo:dataSource data="${teachClasss}"></kendo:dataSource>
                            </kendo:comboBox>
                        </div>
                    </div>
                    <div class="uk-width-large-1-3">
                        <div class="uk-input-group" style="width: 100%">
                            <label>时间:</label>
                            <kendo:datePicker name="startDate" value="${startDate}" style="width:40%" title="开始日期" onchange="checkDate('start')"></kendo:datePicker>至
                            <kendo:datePicker name="endDate"  value="${endDate}" style="width:40%" title="结束日期" onchange="checkDate('end')"></kendo:datePicker>
                        </div>
                    </div>
                    <div class="uk-width-large-1-4">
                        <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                    </div>
                </div>
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-1">
                        <div class="uk-input-group" style="width: 100%">
                            <div style="padding :0px;text-align: center">
                                <a class="md-btn md-btn-primary" id="date0" href="javascript:selectDate(0)" style="border-top-right-radius: 0px;border-bottom-right-radius: 0px;">日</a>
                                <a class="md-btn md-btn-primary" id="date1" href="javascript:selectDate(1)" style="margin:-4px;border-radius: 0;">周</a>
                                <a class="md-btn md-btn-primary" id="date2" href="javascript:selectDate(2)" style="margin:0px;border-top-left-radius: 0px;border-bottom-left-radius: 0px;">月</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="md-card-toolbar" style="padding: 0px;">
                    <h3 class="md-card-toolbar-heading-text" id="count">
                    </h3>
                    <div style="padding :0px;text-align: right">
                        <a class="md-btn md-btn-primary" id="btn0" href="javascript:jiaJianDate(-1)" style="border-top-right-radius: 0px;border-bottom-right-radius: 0px;">&lt;</a>
                        <a class="md-btn md-btn-primary" id="btn1" href="javascript:void(0)" style="margin:-4px;border-radius: 0;">周</a>
                        <a class="md-btn md-btn-primary" id="btn2" href="javascript:jiaJianDate(1)" style="margin:0px;border-top-left-radius: 0px;border-bottom-left-radius: 0px;">&gt;</a>
                    </div>
                </div>
                <div class="uk-grid">
                    <div class="uk-width-medium-1-1">
                        <kendo:grid name="grid" pageable="true" sortable="true" filterable="false" selectable="false" height="400" style="border-width:0px;">
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
                                <kendo:grid-column title="${corporationType}" field="school_name" width="120px" filterable="false"/>
                                <kendo:grid-column title="上课时间" field="curriculum_date" width="100px" filterable="false"/>
                                <kendo:grid-column title="老师姓名" field="teacher_name" filterable="false" width="90px" />
                                <kendo:grid-column title="课程类型" field="teachMethod" width="90px" filterable="false"/>
                                <kendo:grid-column title="课程名称" field="course_name" filterable="false" width="90px" />
                                <kendo:grid-column title="班级名称" field="class_name" width="90px" filterable="false"/>
                                <kendo:grid-column title="教室" field="room_name" width="180px" filterable="false"/>
                                <kendo:grid-column title="学生数" field="studentCount" width="100px" filterable="false">
                                    <kendo:grid-column-template>
                                        <script>
                                            function template(options) {
                                                return "<div style='text-align:left;margin:0 auto;' onmouseover='showStudentCurriculum("+JSON.stringify(options)+")'><a class='grid_a' href='javascript:void(0);'>"+options.studentCount+"</a></div>";
                                            }
                                        </script>
                                    </kendo:grid-column-template>
                                </kendo:grid-column>
                                <kendo:grid-column title="排课准确率" field="accuracy_rate" width="110px" filterable="false" template="#=accuracy_rate#%" />
                            </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements">
                                </kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="pageCurriculumAccuracyRate.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                options.schoolId=$("#schoolId").data("kendoComboBox").value();
                                                options.teachMethod=$("#teachMethod").data("kendoComboBox").value();
                                                options.teacherId=$("#teacherId").data("kendoComboBox").value();
                                                options.courseId=$("#courseId").data("kendoComboBox").value();
                                                options.classId=$("#classId").data("kendoComboBox").value();
                                                var startDate=$("#startDate").data("kendoDatePicker").value();
                                                var endDate=$("#endDate").data("kendoDatePicker").value();
                                                if(StrUtil.isNotEmpty(startDate)){
                                                    startDate=startDate.Format("yyyy-MM-dd");
                                                }
                                                if(StrUtil.isNotEmpty(endDate)){
                                                    endDate=endDate.Format("yyyy-MM-dd");
                                                }
                                                options.startDate=startDate;
                                                options.endDate=endDate;
                                                return JSON.stringify(options);
                                            }
                                        </script>
                                    </kendo:dataSource-transport-parameterMap>
                                </kendo:dataSource-transport>
                            </kendo:dataSource>
                        </kendo:grid>
                    </div>
                </div>
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-1">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"></span>
                            <label>该时间段各${corporationType}排课准确率：</label>
                            <div id="schoolCurriculumAccuracyRate"></div>
                        </div>
                    </div>
                    <div class="uk-width-large-1-1">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"></span>
                            <label>该时间段各老师排课准确率：</label>
                            <div id="teacherCurriculumAccuracyRate"></div>
                        </div>
                    </div>
                    <div class="uk-width-large-1-1">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"></span>
                            <label>该时间段各课程排课准确率：</label>
                            <div id="courseCurriculumAccuracyRate"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="configuration k-widget k-header" style="top:50%;bottom:50%;display: none;">
    <kendo:window name="window" title="在读学生出勤准确率"  width="600" height="400" draggable="true" resizable="true">
        <kendo:window-content>
            <h5 class="md-card-toolbar-heading-text" id="studentInfo"></h5>
            <div id="studentCurriculumAccuracyRate"></div>
        </kendo:window-content>
    </kendo:window>
</div>
</body>
</html>