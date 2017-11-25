<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="lte-ie9" lang="en">
<html lang="en">
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="msapplication-tap-highlight" content="no"/>

    <link rel="stylesheet" href="${ctx}/kendo/styles/kendo.common.min.css"/>
    <link rel="stylesheet" href="${ctx}/kendo/styles/kendo.silver.min.css"/>
    <link rel="stylesheet" href="${ctx}/bower_components/uikit/css/uikit.css"/>
    <link rel="stylesheet" href="${ctx}/bower_components/uikit/css/uikit.min.css"/>
    <link rel="stylesheet" href="${ctx}/assets/css/main.min.css"/>

    <script src="${ctx}/kendo/js/jquery.min.js"></script>
    <script src="${ctx}/kendo/js/kendo.web.min.js"></script>
    <script src="${ctx}/bower_components/uikit/js/uikit.min.js"></script>
    <script src="${ctx}/bower_components/uikit/js/components/notify.min.js"></script>
    <script type="text/javascript" src="${ctx}/kendo/js/cultures/kendo.culture.zh-CN.min.js"></script>

    <title>妈妈有约管理系统</title>
    <script type="application/javascript">
        jQuery(document).ready(function () {
            kendo.culture("zh-CN");
            $("#columnButton").click(function(){
  /*              $("#column:visible .overDiv").css("overflow","auto");
                $("#column:hidden .overDiv").css("overflow","hidden");*/
                $("#column").slideToggle();
                $("#row:visible").slideToggle();
            });
            $("#rowButton").click(function(){
                $("#row").slideToggle();
                $("#column:visible").slideToggle();
            });
            $(".colseColumn").click(function(){
                $('#columnButton').trigger('click');
            });
            $(".colseRow").click(function(){
                $('#rowButton').trigger('click');
            });

            $(".datepicker").kendoDatePicker({
                format: "yyyy-MM-dd"
            });

            $("#myRowId :checkbox").click(function(){
                var checkName = $(this).attr("name");
                if($("#myRowId input[name = "+ checkName +"]:checked").length > 3){
                    UIkit.notify({message: "最多选择三个行标题！", status: 'warning', timeout: 2000, pos: 'top-center'});
                    return false;
                }
            });

            loadGrid();
            $("#teachMethodRowBut").trigger('click');
            setDatabyRows('teachMethod',false);
            setDatabyColumns('grade','section',false);
        });


        function loadGrid(data){
            $("#grid").kendoGrid({
                dataSource:data,
                height: 778,
                filterable: false,
                sortable: true,
                selectable:true,
                columns: [{field:"columnName",title:" ",template:"#if (columnType == 'section' || columnType == 'subject' || columnType == 'teacher') {# <strong>#: columnName #</strong> #} else { # #:columnName# #} #"},
                            {title: '-', headerAttributes: {"class": "table-header-cell",style: "text-align: right","id":"rowTitle0"},
                                columns: [
                                    {field:"tuitionFee1",title:"营收" ,template:"<div style='text-align:right'>#= tuitionFee1 #</div>"},
                                    {field:"curcFee1",title:"课收",template:"<div style='text-align:right'>#= curcFee1 #</div>"},
                                    {field:"teacherFee1",title:"成本",template:"<div style='text-align:right'>#= teacherFee1 #</div>"},
                                    {field:"teacherDivClass1",title:"成本/课收",format:"{0:#.##}%" ,template:"<div style='text-align:right'>#= teacherDivClass1 #</div>"},
                                    {field:"classDivTuition1",title:"课收/营收",format:"{0:#.##}%" ,template:"<div style='text-align:right'>#= classDivTuition1 #</div>"}
                                ]
                            },
                            {title: '-', headerAttributes: {"class": "table-header-cell",style: "text-align: right","id":"rowTitle1"},
                                columns: [
                                    {field:"tuitionFee2",title:"营收" ,template:"<div style='text-align:right'>#= tuitionFee2 #</div>"},
                                    {field:"curcFee2",title:"课收",template:"<div style='text-align:right'>#= curcFee2 #</div>"},
                                    {field:"teacherFee2",title:"成本",template:"<div style='text-align:right'>#= teacherFee2 #</div>"},
                                    {field:"teacherDivClass2",title:"成本/课收",format:"{0:#.##}%",template:"<div style='text-align:right'>#= teacherDivClass2 #</div>"},
                                    {field:"classDivTuition2",title:"课收/营收",format:"{0:#.##}%",template:"<div style='text-align:right'>#= classDivTuition2 #</div>"}
                                ]},
                            {title: '-', headerAttributes: {"class": "table-header-cell",style: "text-align: right","id":"rowTitle2"},
                                columns: [
                                    {field:"tuitionFee3",title:"营收",template:"<div style='text-align:right'>#= tuitionFee3 #</div>"},
                                    {field:"curcFee3",title:"课收",template:"<div style='text-align:right'>#= curcFee3 #</div>"},
                                    {field:"teacherFee3",title:"成本",template:"<div style='text-align:right'>#= teacherFee3 #</div>"},
                                    {field:"teacherDivClass3",title:"成本/课收",format:"{0:#.##}%",template:"<div style='text-align:right'>#= teacherDivClass3 #</div>"},
                                    {field:"classDivTuition3",title:"课收/营收",format:"{0:#.##}%",template:"<div style='text-align:right'>#= classDivTuition3 #</div>"}
                                ]},
                ],
                noRecords:{template: "表中无内容" }
            });
        }

        var data = {};
        function setDatabyRows(name,i){
            $("#rowTitle0").html("-");
            $("#rowTitle1").html("-");
            $("#rowTitle2").html("-");
            var checkedRows = $("#myRowId input[name = "+ name +"]:checked");
            data.rowType = name;
            data.rowList = [];
            if(checkedRows.length != 0){
                $.each( checkedRows, function(i, n){
                    if(i < 3){
                        $("#rowTitle"+i).html($(n).attr("text"));
                        data.rowList.push($(n).attr("value"));
                    }
                });
            }
            if(i == null){
                $('#rowButton').trigger('click');
            }

            if(data.columnType2 == data.rowType){
                UIkit.notify({message: "列标题和行标题不能是相同类型！", status: 'warning', timeout: 2000, pos: 'top-center'});
                $("#myRowId input[name = "+ name +"]").prop("checked",false);
                return;
            }

            readGridData(1);
        }

        function setDatabyColumns(name,name2,i){
            var checkedColumns = $("#myColumnId input[name = "+ name +"]:checked");
            data.columnType = name;
            data.columnType2 = name2;
            data.columnList = [];
            columnList = [];
            if(checkedColumns.length != 0){
                $.each( checkedColumns, function(i, n){
                    var map = {};
                    map.columnType2Id = $(n).attr("data-"+name);
                    map.columnTypeList =[];
                    map.columnTypeList.push($(n).attr("value"));
                    columnList.push(map);
                });
                $.each(columnList, function(i, n){
                    var flag = true;
                    $.each(data.columnList, function(i1, n1){
                        if(n.columnType2Id == n1.columnType2Id){
                            n1.columnTypeList.push(n.columnTypeList[0]);
                            flag = false;
                        }
                    });
                    if(flag){
                        data.columnList.push(n);
                    }
                });
            }
            if(i == null){
                $('#columnButton').trigger('click');
            }

            if(data.columnType2 == data.rowType){
                UIkit.notify({message: "列标题和行标题不能是相同类型！", status: 'warning', timeout: 2000, pos: 'top-center'});
                $("#myColumnId input[name = "+ name +"]").prop("checked",false);
                $("#myColumnId input[name = "+ name +"All]").prop("checked",false);
                return;
            }

            readGridData(1);
        }

        function readGridData(i){
            data.startDate = $("#startDate").val();
            data.endDate = $("#endDate").val();
            data.schoolId = ${schoolId};
            data.rec = new Date();

            if(data.columnList &&　data.columnList.length > 0 && data.rowList　&&　data.rowList.length > 0){
                $.ajax({
                    type: "POST",
                    url: "../chart/classSurveyDateList.do",
                    headers:{'Access-Control-Allow-Origin':'*'},
                    contentType:"application/json",
                    data:JSON.stringify(data),
                    dateType:"json",
                    success: function (data) {
                        gridData = data;
                        loadGrid(data);
                    }
                });
            }else{
                if(i!=1){
                    if(data.columnList.length = 0){
                        UIkit.notify({message: "请选择列标题！", status: 'warning', timeout: 2000, pos: 'top-center'});
                    }
                    if(data.rowList.length = 0){
                        UIkit.notify({message: "请选择行标题！", status: 'warning', timeout: 2000, pos: 'top-center'});
                    }
                }else{
                    loadGrid(null);
                }
            }
        }

        function checkedAll(i,name,id) {
            if(name == "teachMethod"){
                $("#myColumnId input[name = "+ name +"]").prop("checked",i.checked);
            }else{
                $("#myColumnId input[name = "+ name +"][data-"+name+" = "+id+" ]").prop("checked",i.checked);
            }
        }

        function exportExcel(){
            var rowStrList = "";
            $.each( data.rowList, function(i, n){
                rowStrList = rowStrList + $("#rowTitle"+i).html() + ",";
            });
           app.exportExcel($("#startDate").val(),$("#endDate").val(),JSON.stringify(data),rowStrList);
        }

        function reloadJsp(){
            location.reload();
        }

    </script>
    <style>
        tr{text-align: right;}
        td{border-bottom:solid #add9c0; border-width:0px 0px 1px 0px;text-align: right;}


        .button {
            min-height: 24px;
            display: inline-block;
            outline: none;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            font: 14px/100% Arial, Helvetica, sans-serif;
            padding: .5em 2em .30em;
            text-shadow: 0 1px 1px rgba(0,0,0,.1);
            -webkit-border-radius: .5em;
            -moz-border-radius: .5em;
            border-radius: .5em;
            -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);
            -moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);
            box-shadow: 0 1px 2px rgba(0,0,0,.2);

            color: #323232;
            border: solid 1px #B5B5B5;
            background: #E8E8E8;
            background: -webkit-gradient(linear, left top, left bottom, from(#FEFEFE), to(#E2E2E2));
            background: -moz-linear-gradient(top,  #FEFEFE,  #E2E2E2);
            filter:  progid:DXImageTransform.Microsoft.gradient(startColorstr='#FEFEFE', endColorstr='#E2E2E2');
        }
        .uk-button-primary{
            min-height: 24px;
            display: inline-block;
            outline: none;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            font: 14px/100% Arial, Helvetica, sans-serif;
            padding: .5em 2em .30em;
            text-shadow: 0 1px 1px rgba(0,0,0,.1);
            -webkit-border-radius: .5em;
            -moz-border-radius: .5em;
            border-radius: .5em;
            -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);
            -moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);
            box-shadow: 0 1px 2px rgba(0,0,0,.2);
        }

        .button:hover {
            text-decoration: none;
        }
        .button:active {
            position: relative;
            top: 1px;
        }

        .filterGrid{
            border: 1px solid #3CB371;
            border-radius: 4px;
            box-shadow: 1px 1px 2px 2px #F5FFFA;
        }
    </style>
</head>
<body  style="margin: 0px;padding: 0px; border: none; background-color: #ffffff; font-size: 12px;" >
    <div class="md-card" id="content" style="height: 830px;" >
        <div class="md-card-toolbar" style="padding-top: 10px;background-color:#E8E8E8 ;height: 40px">
            <div class="uk-grid" >
                <div class="uk-width-6-10">
                    <div class="uk-button-dropdown">
                        <button class="uk-button button" id="columnButton" >列标题  <i class="uk-icon-caret-down"></i></button>
                    </div>
                    <div class="uk-button-dropdown" data-uk-dropdown>
                        <button class="uk-button button" id="rowButton">行标题  <i class="uk-icon-caret-down"></i></button>
                    </div>
                </div>
                <div class="uk-width-4-10" style="text-align: right">
                    <div class="uk-button-dropdown" data-uk-dropdown>
                        <div class="uk-form-icon">
                            <i class="uk-icon-calendar"></i>
                            <input class="datepicker" type="text" id="startDate"  value="${startDate}">
                        </div>
                        至
                        <div class="uk-form-icon">
                            <i class="uk-icon-calendar"></i>
                            <input class="datepicker" type="text" id="endDate"  value="${endDate}">
                        </div>
                        <button class="uk-button button" onclick="readGridData()">统计  <i class="uk-icon-area-chart"></i></button>
                        <button class="uk-button button " onclick="exportExcel()" >导出  <i class="uk-icon-file-excel-o"></i></button>
                        <button class="uk-button button " onclick="reloadJsp()" ><i class="uk-icon-refresh"></i></button>
                    </div>
                </div>
            </div>
            <div class="uk-grid filterGrid" id = "column" style="display: none;background-color:#eaeaea;margin-top: 2px;margin-left:2px;width: 1020px;" >
                <div class="uk-width-medium-1-1" >
                    <div class="uk-panel"  data-uk-switcher="{connect:'#myColumnId',animation:'fade'}" style="padding-top: 5px">
                        <button class="uk-button uk-button-primary" id = "sectionColBut" name="colBut">学段年级</button>
                        <button class="uk-button uk-button-primary" id = "subjectColBut" name="colBut">学科课程</button>
                        <button class="uk-button uk-button-primary" id = "teachMethodColBut" name="colBut">班级类型</button>
                        <button class="uk-button uk-button-primary" id = "teacherColBut" name="colBut">老师班级</button>
                    </div>
                    <div id="myColumnId" class="uk-switcher uk-margin">
                        <div class="uk-panel" > <!--学段年级-->
                            <div style="position:relative ; max-height:600px; overflow:auto">
                                <c:forEach var="item" items="${sgsSet}" varStatus="status">
                                    <div class="uk-form-row" data-uk-margin>
                                     <label><input type="checkbox" name="gradeAll" onchange="checkedAll(this,'grade',${item.sectionId})" <c:if test="${status.index==0}">checked</c:if> />${item.sectionName}:</label>
                                     <c:forEach var="sg" items="${item.sgList}" varStatus="status1">
                                         <label><input type="checkbox" value="${sg.gradeId}"  name="grade" text="${sg.gradeName}" data-grade="${item.sectionId}" <c:if test="${status.index==0}">checked</c:if>>${sg.gradeName} </label>
                                     </c:forEach>
                                    </div>
                                </c:forEach>
                            </div>
                            <div style="text-align: right;padding-bottom: 5px;padding-right: 5px;">
                                <button class="uk-button  colseColumn button">取消</button>
                                <button class="uk-button button" onclick="setDatabyColumns('grade','section')">确定</button>
                            </div>
                        </div>
                        <div class="uk-panel" > <!--学科课程-->
                            <div style="position:relative ; max-height:600px; overflow:auto" class="overDiv">
                                <c:forEach var="item" items="${csSet}" varStatus="status">
                                    <div class="uk-form-row" data-uk-margin>
                                        <table style="border-bottom-color: #0000ee">
                                            <tr>
                                                <td width="100px"><label><input type="checkbox" name="courseAll" onchange="checkedAll(this,'course',${item.subjectId})" />${item.subjectName}:</label></td>
                                                <td>
                                                    <c:forEach var="cs" items="${item.csList}" varStatus="status">
                                                        <label><input type="checkbox" value="${cs.courseId}" name="course" text="${cs.courseName}" data-course="${item.subjectId}">${cs.courseName} </label>
                                                        <c:if test="${status.count%8==0&&!status.last}">
                                                            <br/>
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </c:forEach>
                            </div>
                            <div style="text-align: right;padding-bottom: 5px;padding-right: 5px;">
                                <button class="uk-button colseColumn button">取消</button>
                                <button class="uk-button button" onclick="setDatabyColumns('course','subject')">确定</button>
                            </div>
                        </div>
                        <div class="uk-panel"> <!--班级类型-->
                            <div class="uk-form-row" data-uk-margin>
                                <label><input type="checkbox" name="teachMethodAll" onchange="checkedAll(this,'teachMethod')" />班级类型:</label>
                                <label><input type="checkbox" value="0" name="teachMethod" text="一对一" data-teachMethod="0">一对一 </label>
                                <label><input type="checkbox" value="1" name="teachMethod" text="小组课" data-teachMethod="1">小组课 </label>
                                <label><input type="checkbox" value="2" name="teachMethod" text="多科强化班" data-teachMethod="2">多科强化班 </label>
                                <label><input type="checkbox" value="3" name="teachMethod" text="托管" data-teachMethod="3">托管 </label>
                            </div>
                            <div style="text-align: right;padding-bottom: 5px;padding-right: 5px;">
                                <button class="uk-button colseColumn button">取消</button>
                                <button class="uk-button button" onclick="setDatabyColumns('teachMethod','teachMethod')">确定</button>
                            </div>
                        </div>
                        <div class="uk-panel" > <!--老师班级-->
                            <div style="position:relative ; max-height:600px; overflow:auto" class="overDiv">
                                <c:forEach var="item" items="${tcSet}" varStatus="status">
                                    <div class="uk-form-row" data-uk-margin >
                                        <table>
                                            <tr>
                                                <td width="100px"><label><input type="checkbox" name="classAll" onchange="checkedAll(this,'class',${item.teacherId})" /> ${item.teacherName}:</label></td>
                                                <td>
                                                    <c:forEach var="tc" items="${item.tcList}" varStatus="status">
                                                        <label><input type="checkbox" value="${tc.classId}"  name="class" text="${tc.className}" data-class="${item.teacherId}">${tc.className} </label>
                                                        <c:if test="${status.count%8==0&&!status.last}">
                                                            <br/>
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </c:forEach>
                            </div>
                            <div style="text-align: right;padding-bottom: 5px;padding-right: 5px;">
                                <button class="uk-button colseColumn button">取消</button>
                                <button class="uk-button button" onclick="setDatabyColumns('class','teacher')">确定</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="uk-grid filterGrid" id = "row" style="display: none;background-color:#eaeaea;margin-top: 2px;margin-left:112px;width: 1020px;z-index:1000" >
                <div class="uk-width-medium-1-1" >
                    <div class="uk-panel"  data-uk-switcher="{connect:'#myRowId',animation:'fade'}" style="padding-top: 5px">
                        <button class="uk-button uk-button-primary" id = "sectionRowBut" name="rowBut">学段</button>
                        <button class="uk-button uk-button-primary" id = "subjectRowBut" name="rowBut">学科</button>
                        <button class="uk-button uk-button-primary" id = "teachMethodRowBut" name="rowBut">班级类型</button>
                        <button class="uk-button uk-button-primary" id = "teacherRowBut" name="rowBut">老师</button>
                    </div>
                    <div id="myRowId" class="uk-switcher uk-margin">
                        <div class="uk-panel" > <!--学段年级-->
                            <div class="uk-form-row" data-uk-margin>
                                <label>学段:</label>
                                <c:forEach var="item" items="${sgsSet}" varStatus="status">
                                    <label><input type="checkbox" value="${item.sectionId}" name="section" text="${item.sectionName}">${item.sectionName}</label>
                                </c:forEach>
                            </div>
                            <div style="text-align: right;padding-bottom: 5px;padding-right: 5px;">
                                <label>最多选择三个行标题</label>
                                <button class="uk-button colseRow button">取消</button>
                                <button class="uk-button button" onclick="setDatabyRows('section')">确定</button>
                            </div>
                        </div>
                        <div class="uk-panel" > <!--学科课程-->
                            <div class="uk-form-row" data-uk-margin>
                                <label>学科:</label>
                                <c:forEach var="item" items="${csSet}" varStatus="status">
                                    <label><input type="checkbox" value="${item.subjectId}" name="subject" text="${item.subjectName}">${item.subjectName} </label>
                                    <c:if test="${status.count%8==0&&!status.last}">
                                        <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div style="text-align: right;padding-bottom: 5px;padding-right: 5px;">
                                <label>最多选择三个行标题</label>
                                <button class="uk-button colseRow button">取消</button>
                                <button class="uk-button button" onclick="setDatabyRows('subject')">确定</button>
                            </div>
                        </div>
                        <div class="uk-panel"> <!--班级类型-->
                            <div class="uk-form-row" data-uk-margin>
                                <label>班级类型:</label>
                                <label><input type="checkbox" value="0" name="teachMethod" text="一对一" checked>一对一 </label>
                                <label><input type="checkbox" value="1" name="teachMethod" text="小组课" checked>小组课 </label>
                                <label><input type="checkbox" value="2" name="teachMethod" text="多科强化班" checked>多科强化班 </label>
                                <label><input type="checkbox" value="3" name="teachMethod" text="托管" checked>托管 </label>
                            </div>
                            <div style="text-align: right;padding-bottom: 5px;padding-right: 5px;">
                                <button class="uk-button colseRow button">取消</button>
                                <button class="uk-button button" onclick="setDatabyRows('teachMethod')">确定</button>
                            </div>
                        </div>
                        <div class="uk-panel"> <!--老师班级-->
                            <div class="uk-form-row" data-uk-margin>
                                <label>老师:</label>
                                <c:forEach var="item" items="${tcSet}" varStatus="status">
                                    <label><input type="checkbox" value="${item.teacherId}" name="teacher" text="${item.teacherName}">${item.teacherName} </label>
                                    <c:if test="${status.count%8==0&&!status.last}">
                                         <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div style="text-align: right;padding-bottom: 5px;padding-right: 5px;   ">
                                <label  >最多选择三个行标题</label>
                                <button class="uk-button colseRow button">取消</button>
                                <button class="uk-button button" onclick="setDatabyRows('teacher')">确定</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <div class="uk-width-medium-1-1">
                <div id="grid"></div>
            </div>
        </div>
    </div>
</body>
</html>