<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="lte-ie9" lang="en">
<html lang="en">
<head>
    <title>妈妈有约管理系统</title>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">学员考勤信息详情</h3>

<div class="uk-grid" data-uk-grid-margin="">
    <div class="uk-width-medium-1-2" style="width: 30%;float: left;border:3px solid #eee;">
        <div class="md-card">
            <div class="md-card-content">
                <div class="uk-overflow-container">
                    <table class="uk-table uk-table-nowrap">
                        <thead>
                        <tr>
                            <th class="uk-width-1-10">姓名</th>
                            <th class="uk-width-2-10 uk-text-center">小组课班级</th>
                            <th class="uk-width-2-10 uk-text-center">上课日期</th>
                            <th class="uk-width-2-10 uk-text-center">出勤情况</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="bean" items="${list2}">
                            <tr>
                                <td class="uk-text-center">${bean.studentName}</td>
                                <td class="uk-text-center">${bean.className}</td>
                                <td class="uk-text-center">${bean.curriculumDate}</td>
                                <td class="uk-text-center">
                                    <c:if test="${bean.attendence == 1 }">
                                        出勤
                                    </c:if>
                                    <c:if test="${bean.attendence != 1 }">
                                        缺勤
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="uk-width-medium-1-2" style="width: 30%;float: left;border:3px solid #eee;">
        <div class="md-card">
            <div class="md-card-content">
                <div class="uk-overflow-container">
                    <table class="uk-table uk-table-nowrap">
                        <thead>
                        <tr>
                            <th class="uk-width-1-10">姓名</th>
                            <th class="uk-width-2-10 uk-text-center">一对一班级</th>
                            <th class="uk-width-2-10 uk-text-center">上课日期</th>
                            <th class="uk-width-2-10 uk-text-center">出勤情况</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="bean" items="${list3}">
                            <tr>
                                <td class="uk-text-center">${bean.studentName}</td>
                                <td class="uk-text-center">${bean.className}</td>
                                <td class="uk-text-center">${bean.curriculumDate}</td>
                                <td class="uk-text-center">
                                    <c:if test="${bean.attendence == 1}">
                                        出勤
                                    </c:if>
                                    <c:if test="${bean.attendence != 1}">
                                        缺勤
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="uk-width-medium-1-2" style="width: 30%;float: left;border:3px solid #eee;">
        <div class="md-card">
            <div class="md-card-content">
                <div class="uk-overflow-container">
                    <table class="uk-table uk-table-nowrap">
                        <thead>
                        <tr>
                            <th class="uk-width-1-10">姓名</th>
                            <th class="uk-width-2-10 uk-text-center">多科强化班</th>
                            <th class="uk-width-2-10 uk-text-center">上课日期</th>
                            <th class="uk-width-2-10 uk-text-center">出勤情况</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="bean" items="${list1}">
                            <tr>
                                <td class="uk-text-center">${bean.studentName}</td>
                                <td class="uk-text-center">${bean.className}</td>
                                <td class="uk-text-center">${bean.curriculumDate}</td>
                                <td class="uk-text-center">
                                    <c:if test="${bean.attendence == 1}">
                                        出勤
                                    </c:if>
                                    <c:if test="${bean.attendence != 1}">
                                        缺勤
                                    </c:if>
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