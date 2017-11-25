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
    <link rel="stylesheet" href="${ctx}/assets/css/main.min.css" media="all">
    <script type="text/javascript" src="../kendo/js/messages/kendo.messages.zh-course.min.js"></script>
    <script type="application/javascript">
        //定义操作类型
        var operateType=0;
        function reloadData(){
            $("#grid").data("kendoGrid").dataSource.read();
            showIncome();
            studentJointChart();
        }

        //在读人数
        function showIncome(){
            var params={};
            params.type=operateType;
            var sections=$("#sections").data("kendoMultiSelect").value();
            var courses=$("#courses").data("kendoMultiSelect").value();
            var grades=$("#grades").data("kendoMultiSelect").value();
            var classs=$("#classs").data("kendoMultiSelect").value();
            var schoolIds=$("#schoolIds").data("kendoMultiSelect").value();
            if (schoolIds!= null&&schoolIds!= undefined &&schoolIds.length!=0) {
                //将数组转换为字符串
                params.schoolIds=schoolIds;
            }
            if (sections!= null &&sections!= undefined &&sections.length!=0 ) {
                //将数组转换为字符串
                params.sections=sections;
            }
            if (courses!= null &&courses!= undefined&&courses.length!=0) {
                //将数组转换为字符串
                params.courses=courses;
            }
            if (grades!= null &&grades!= undefined&&grades.length!=0) {
                //将数组转换为字符串
                params.grades=grades;
            }
            if (classs!= null &&classs!= undefined&&classs.length!=0) {
                //将数组转换为字符串
                params.classs=classs;
            }

            $.ajax({
                type: 'get',
                url: '../report/onStudyingNumber.do',
                dataType:'json',
                data:params,
                success: function (data) {
                    if(data!=null){
                        $("#showIncome").html("在读人数:<font color='blue' style='font-weight: bold;'>"+data+"</font>");
                    }
                }
            });
        }

        window.onload=function () {
            showIncome();
            studentJointChart();
            $("#grid").data("kendoGrid").showColumn(0);
            $("#grid").data("kendoGrid").hideColumn(1);
            $("#grid").data("kendoGrid").hideColumn(2);
            $("#grid").data("kendoGrid").hideColumn(3);
            $("#grid").data("kendoGrid").hideColumn(4);
            $("#grid").data("kendoGrid").hideColumn(5);
            $("#grid").data("kendoGrid").hideColumn(6);
            $("#grid").data("kendoGrid").showColumn(7);
        };

        function downLoad(){
            var sections=$("#sections").data("kendoMultiSelect").value();
            var courses=$("#courses").data("kendoMultiSelect").value();
            var grades=$("#grades").data("kendoMultiSelect").value();
            var classs=$("#classs").data("kendoMultiSelect").value();
            var schoolIds=$("#schoolIds").data("kendoMultiSelect").value();
            var type=operateType;
        document.location.href="exportStudentJoint.do?sections="+sections+"&courses="+courses+"&grades="+grades+"&classs="+classs+"&schoolIds="+schoolIds+"&type="+type;
        }

        /**
         * 重置查询条件
         */
        function resetQuery(){
            $("#schoolIds").data("kendoMultiSelect").value('');
            $("#grades").data("kendoMultiSelect").value('');
            $("#courses").data("kendoMultiSelect").value('');
            $("#sections").data("kendoMultiSelect").value('');
            $("#classs").data("kendoMultiSelect").value('');
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
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").hideColumn(4);
                $("#grid").data("kendoGrid").hideColumn(5);
                $("#grid").data("kendoGrid").hideColumn(6);
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
            }else {   //班级科次
                $("#grid").data("kendoGrid").hideColumn(0);
                $("#grid").data("kendoGrid").hideColumn(1);
                $("#grid").data("kendoGrid").hideColumn(2);
                $("#grid").data("kendoGrid").hideColumn(3);
                $("#grid").data("kendoGrid").showColumn(4);
                $("#grid").data("kendoGrid").showColumn(5);
                $("#grid").data("kendoGrid").showColumn(6);
                $("#grid").data("kendoGrid").hideColumn(7);
            }

            $("#grid").data("kendoGrid").dataSource.read();
            studentJointChart();
            showIncome();
        }


        /**
         * 校区  ---课程
         */
        function onChangeSchool() {
            var options={};
            var schoolIds=$("#schoolIds").data("kendoMultiSelect").value();
            if (schoolIds!= null && schoolIds!= undefined && schoolIds.length>0) {
                //将数组转换为字符串
                options.schoolIds=schoolIds;
            }
            $.ajax({
                type: 'get',
                url: 'getCourseBySchoolIds.do',
                dataType:'json',
                data:options,
                success: function (data) {
                    if(data!=null){
                        var allowUsers = $("#courses").getKendoMultiSelect();
                        allowUsers.dataSource.data(data);
                    }
                }
            });
        }


        /**
         * 学段   ---年级
         */
        function onChangeGrades() {
            var options={};
            var sections=$("#sections").data("kendoMultiSelect").value();
            if(sections!=null && sections!=undefined && sections.length>0){
                options.sections=sections;
            }
            $.ajax({
                type: 'get',
                url: 'getGradeBySection.do',
                dataType:'json',
                data:options,
                success: function (data) {
                    if(data!=null){
                        var allowUsers = $("#grades").getKendoMultiSelect();
                        allowUsers.dataSource.data(data);
                    }
                }
            });
        }


        /**
         * 报表生成图片并下载
         * @param fileName:下载后保存的文件
         */
        function downloadCharts(fileName) {
            var chart = $("#studentJointChart").data("kendoChart");
            var imageDataURL = chart.imageDataURL();
            var timestamp= (new Date()).Format("yyyyMMddhhmmss");
            FileUtil.saveImageFile(imageDataURL,fileName+"_"+timestamp);
        }

        //报表-营收按校区
        function studentJointChart() {
            var params={};
            var sections=$("#sections").data("kendoMultiSelect").value();
            var courses=$("#courses").data("kendoMultiSelect").value();
            var grades=$("#grades").data("kendoMultiSelect").value();
            var classs=$("#classs").data("kendoMultiSelect").value();
            var schoolIds=$("#schoolIds").data("kendoMultiSelect").value();
            if (schoolIds!= null&&schoolIds!= undefined &&schoolIds.length!=0) {
                //将数组转换为字符串
                params.schoolIds=schoolIds;
            }
            if (sections!= null &&sections!= undefined &&sections.length!=0 ) {
                //将数组转换为字符串
                params.sections=sections;
            }
            if (courses!= null &&courses!= undefined&&courses.length!=0) {
                //将数组转换为字符串
                params.courses=courses;
            }
            if (grades!= null &&grades!= undefined&&grades.length!=0) {
                //将数组转换为字符串
                params.grades=grades;
            }
            if (classs!= null &&classs!= undefined&&classs.length!=0) {
                //将数组转换为字符串
                params.classs=classs;
            }
            params.type=operateType;
            $.post("../report/studentJointChart.do",params, function (data) {
                if (data != null) {
                    if(operateType==0){          //校区
                        $("#studentJointChart").kendoChart({
                            renderAs: "canvas",
                            title: {text: ""},
                            dataSource: {data: data.studentJointChart},
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
                                field: "studentJoint",
                                categoryField: "schoolName"
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

                    }else if(operateType==1){    //学段
                        $("#studentJointChart").kendoChart({
                            renderAs: "canvas",
                            title: {
                                text: ""
                            },
                            dataSource: {
                                data: data.studentJointChart
                            },
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
                                field: "studentJoint",
                                categoryField: "sectionName"
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
                    }else if(operateType==2){    //年级
                        $("#studentJointChart").kendoChart({
                            renderAs: "canvas",
                            title: {
                                text: ""
                            },
                            dataSource: {
                                data: data.studentJointChart
                            },
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
                                field: "studentJoint",
                                categoryField: "gradeName"
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
                                    skip: 1,
                                    step: 1
                                }
                            },
                            tooltip: {
                                visible: true,
                                template: "#= category #: #= value  #"
                            }
                        });
                    }else if(operateType==3){    //课程
                        $("#studentJointChart").kendoChart({
                            renderAs: "canvas",
                            title: {
                                text: ""
                            },
                            dataSource: {
                                data: data.studentJointChart
                            },
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
                                field: "studentJoint",
                                categoryField: "courseName"
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
                    }else if(operateType==4){    //班级
                        $("#studentJointChart").kendoChart({
                            renderAs: "canvas",
                            title: {
                                text: ""
                            },
                            dataSource: {
                                data: data.studentJointChart
                            },
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
                                field: "studentJoint",
                                categoryField: "className"
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
                }
            });
        }
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">在读人数统计
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
                <a class="md-btn md-btn-primary" id="data0" href="javascript:changeColor(0)" style="border-top-right-radius: 0px;border-bottom-right-radius: 0px; background: rebeccapurple;">在读人数按校区</a>
                <a class="md-btn md-btn-primary" id="data1" href="javascript:changeColor(1)" style="margin:-4px;border-radius: 0;">在读人数按就读学段</a>
                <a class="md-btn md-btn-primary" id="data2" href="javascript:changeColor(2)" style="margin:-4px;border-radius: 0;">在读人数按就读年级</a>
                <a class="md-btn md-btn-primary" id="data3" href="javascript:changeColor(3)" style="margin:-4px;border-radius: 0;">在读人数按课程</a>
                <a class="md-btn md-btn-primary" id="data4" href="javascript:changeColor(4)" style="margin:0px;border-top-left-radius: 0px;border-bottom-left-radius: 0px;">科次按班级</a>
            </div>

            <div class="md-card-content large-padding" style="height: 100%;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group">
                            <label>校区</label>
                            <kendo:multiSelect name="schoolIds" dataTextField="schoolName" change="onChangeSchool" style="width: 200px;" filter="contains"  index="0" suggest="true" dataValueField="schoolId" placeholder="选择校区...">
                                <kendo:dataSource data="${schoolList}"></kendo:dataSource>
                            </kendo:multiSelect>
                        </div>
                    </div>


                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group">
                            <label>课程</label>
                            <kendo:multiSelect name="courses"  dataTextField="courseName" style="width: 200px;" filter="contains" index="0" suggest="true" dataValueField="courseId" placeholder="选择课程...">
                                <kendo:dataSource data="${courseList}"></kendo:dataSource>
                            </kendo:multiSelect>
                        </div>
                    </div>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group">
                            <label>班级</label>
                            <kendo:multiSelect name="classs"  dataTextField="className" style="width: 200px;" filter="contains" index="0" suggest="true" dataValueField="classId" placeholder="选择班级...">
                                <kendo:dataSource data="${classList}"></kendo:dataSource>
                            </kendo:multiSelect>
                        </div>
                    </div>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group">
                            <label>就读学段</label>
                            <kendo:multiSelect name="sections"  change="onChangeGrades" dataTextField="sectionName" style="width: 200px;" filter="contains" index="0" suggest="true" dataValueField="sectionId" placeholder="选择学段...">
                                <kendo:dataSource data="${sectionList}"></kendo:dataSource>
                            </kendo:multiSelect>
                        </div>
                    </div>
                    <div class="uk-width-large-1-5">
                        <div class="uk-input-group">
                            <label>就读年级</label>
                            <kendo:multiSelect name="grades"  dataTextField="gradeName" style="width: 200px;" filter="contains" index="0" suggest="true" dataValueField="gradeId" placeholder="选择年级...">
                                <kendo:dataSource data="${gradeList}"></kendo:dataSource>
                            </kendo:multiSelect>
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
                            <label><label style="width: 40px;height: 18px;background-color: #FF7617; display: block;float: left;"></label>&nbsp;在读人数</label>
                            <a onclick="downloadCharts('在读人数');" style="float: right;color: blue;" class="k-button">下载</a>
                            <div style="clear: both;"></div>
                            <div id="studentJointChart"></div>
                        </div>
                    </div>
                </div>

                <div class="md-card-toolbar">
                    <h3 class="md-card-toolbar-heading-text" id="showIncome">在读人数：0</h3>
                    <a onclick="downLoad();" style="float: right;color: blue;" class="k-button">导出</a>
                </div>

                <div class="uk-grid">
                    <div class="uk-width-medium-1-1">
                        <kendo:grid name="grid" pageable="true" height="500" sortable="true" filterable="true" selectable="true" resizable="true" >
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
                                <kendo:grid-column title="校区" field="schoolName" width="30%" />
                                <kendo:grid-column title="学段" field="sectionName" width="30%" />
                                <kendo:grid-column title="年级" field="gradeName" width="30%" />
                                <kendo:grid-column title="课程" field="courseName" width="30%" />
                                <kendo:grid-column title="班级" field="className" width="30%" />
                                <kendo:grid-column title="校区" field="schoolName" width="30%" />
                                <kendo:grid-column title="科次" field="studentJoint" template="#= studentJoint>0 ? studentJoint : '--' # " width="30%" />
                                <kendo:grid-column title="在读人数" field="studentJoint" template="#= studentJoint>0 ? studentJoint : '--' # " width="30%" filterable="false"/>
                                <kendo:grid-column title="占比" field="rate" width="30%" filterable="false"/>
                            </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements">
                                </kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="../report/pageStudentJoints.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                options.sections=$("#sections").data("kendoMultiSelect").value();
                                                options.courses=$("#courses").data("kendoMultiSelect").value();
                                                options.grades=$("#grades").data("kendoMultiSelect").value();
                                                options.classs=$("#classs").data("kendoMultiSelect").value();
                                                options.schoolIds=$("#schoolIds").data("kendoMultiSelect").value();
                                                options.type=operateType;
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