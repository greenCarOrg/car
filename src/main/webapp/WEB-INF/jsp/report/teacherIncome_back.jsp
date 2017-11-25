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
        function reloadData() {
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            var teacherName = $("#teacherName").val();
            var schoolId = $("#schoolId").val();
            if (startDate == null || startDate == '') {
                alert('开始时间不能为空');
                return;
            }
            if (endDate == null || endDate == '') {
                alert('结束时间不能为空');
                return;
            }
            window.location.href = "pageTeacherInComes.do?schoolId=" + schoolId + "&teacherName=" + teacherName + "&startDate=" + startDate + "&endDate=" + endDate;
        }
    </script>
</head>

<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">老师课酬</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-content">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-3">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"><i
                                    class="uk-input-group-icon uk-icon-calendar"></i></span>
                            <label for="teacherName">老师姓名</label>
                            <input class="md-input" type="text" id="teacherName" name="teacherName"
                                   value="${teacherName}">
                        </div>
                    </div>
                    <div class="uk-width-large-1-3">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"><i
                                    class="uk-input-group-icon uk-icon-calendar"></i></span>
                            <label for="schoolId">${corporationType}</label>
                            <select id="schoolId" name="school.schoolId" data-md-selectize>
                                <c:forEach items="${schoolList}" var="school">
                                    <option
                                            <c:if test="${school.schoolId==schoolId}">selected</c:if>
                                            value=${school.schoolId}>
                                            ${school.schoolName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="uk-width-large-1-3">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"><i
                                    class="uk-input-group-icon uk-icon-calendar"></i></span>
                            <label for="startDate">开始日期</label>
                            <input class="md-input" type="text" id="startDate"
                                   data-uk-datepicker="{format:'YYYY-MM-DD'}" value="${startDate}">
                        </div>
                    </div>
                    <div class="uk-width-large-1-3">
                        <div class="uk-input-group">
                            <span class="uk-input-group-addon"><i
                                    class="uk-input-group-icon uk-icon-calendar"></i></span>
                            <label for="endDate">结束日期</label>
                            <input class="md-input" type="text" id="endDate" data-uk-datepicker="{format:'YYYY-MM-DD'}"
                                   value="${endDate}">
                        </div>
                    </div>
                    <div class="uk-width-large-1-3">
                        <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                    </div>
                </div>

                <div class="uk-overflow-container">
                    <table class="uk-table uk-table-nowrap">
                        <thead>
                        <tr>
                            <th class="uk-width-1-10">老师姓名</th>
                            <th class="uk-width-1-10 uk-text-center">${corporationType}</th>
                            <th class="uk-width-1-10 uk-text-center">手机号码</th>
                            <th class="uk-width-1-10 uk-text-center">应出勤学员人数</th>
                            <th class="uk-width-1-10 uk-text-center">实出勤人数</th>
                            <th class="uk-width-1-10 uk-text-center">课酬总额</th>
                            <th class="uk-width-1-10 uk-text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="bean" items="${teacherInComeVoList}">
                            <tr>
                                <td class="uk-text-center">${bean.teacherName}</td>
                                <td class="uk-text-center">${bean.schoolName}</td>
                                <td class="uk-text-center">${bean.teacherPhone}</td>
                                <td class="uk-text-center">${bean.attendNum}</td>
                                <td class="uk-text-center">${bean.noAttendNum}</td>
                                <td class="uk-text-center">${bean.inCome}</td>
                                <td class="uk-text-center">
                                    <a href="getStudentKaoq.do?schoolId=${bean.schoolId}&teacherId=${bean.teacherId}&startDate=${startDate}&endDate=${endDate}"
                                       target="_blank">查看学员出勤详情</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
