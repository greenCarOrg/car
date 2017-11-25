<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script src="../../assets/js/jquery-1.11.1.min.js"></script>
    <script type="application/javascript">
        jQuery(document).ready(function () {
            validataFrom();
        });
        function validataFrom() {
            jQuery("#validateSubmitForm").validate({
                rules: {
                    discountName: {
                        required: true,
                        minlength: 2,
                        maxlength: 32
                    },
                    mainCourseId: {
                        required: true,
                        min: 1,
                    },
//                    classTime: {
//                        required: true,
//                    },
                    secondCourseId: {
                        required: true,
                        min: 1,
                    },
                    giveCount: {
                        required: true,
                        digits: true,
                        min: 0,
                    },
                    discountPrice: {
                        required: true,
                        number: true,
                        min: 0,
                    },
                    beginDate: {
                        required: true,
                        date:true,
                    },
                    endDate: {
                        required: true,
                        date:true,
                    }
                }
            });
        }
        function getMainCourseByTeachMethod(){
            var teachMethod=$("#teachMethod").val();
            $.ajax({
                type: "get",
                url: "getMainCourseByTeachMethod.do",
                data: {teachMethod:teachMethod},
                async: false,
                dataType: "json",
                success: function (data) {
                    $("#mainCourseId").html("");
                    $("#mainCourseId").append("<option value=0>-请选择-</option>");
                    var obj= $.parseJSON(data);
                    $.each(obj, function(i, item) {
                        $("#mainCourseId").append("<option value="+item.courseId+">"+item.courseName+"</option>")
                    });

                }
            });
        }
        function getSecondCourseByTeachMethod(){
            var teachMethod=$("#secondTeachMethod").val();
            $.ajax({
                type: "get",
                url: "getMainCourseByTeachMethod.do",
                data: {teachMethod:teachMethod},
                async: false,
                dataType: "json",
                success: function (data) {
                    $("#secondCourseId").html("");
                    $("#secondCourseId").append("<option value=0>-请选择-</option>");
                    var obj= $.parseJSON(data);
                    $.each(obj, function(i, item) {
                        $("#secondCourseId").append("<option value="+item.courseId+">"+item.courseName+"</option>")
                    });

                }
            });
        }

        function getCoursePrice(){
            var courseId=$("#secondCourseId").val();
            $.ajax({
                type: "get",
                url: "getCourseById.do",
                data: {courseId:courseId},
                async: false,
                dataType: "json",
                success: function (data) {
                    $("#price").html(data);
                }
            });
        }

        function getMainCourseId(){
            var courseId=$("#mainCourseId").val();
//            $("#firstCourseId").val(courseId);
        }

        function getOldPrice(){
            var giveCount=$("#giveCount").val();
            var price=$("#price").html();
            var discountPrice=$("#discountPrice").val();
            $("#oldAllPrice").html(giveCount*price);
            $("#discountAllPrice").html(giveCount*discountPrice);
        }

        function getDiscountPrice(){
            var giveCount=$("#giveCount").val();
            var price=$("#discountPrice").val();
            $("#discountAllPrice").html(giveCount*price);
        }
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">编辑套餐</h3>
<div class="md-card">
    <div class="md-card-content large-padding">
        <form:form id="validateSubmitForm"  action="editDiscount.do" cssClass="uk-form-stacked" commandName="discount">
            <form:hidden path="discountId"/>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label>套餐名称</label>
                        <form:input path="discountName" cssClass="md-input"/>
                    </div>
                </div>
            </div>
            <h5 style="color:#000000">主课程</h5>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label>课程类型</label>
                        <select name="teachMethod" id="teachMethod" onchange="getMainCourseByTeachMethod()" required data-md-selectize  >
                            <option value="0" <c:if test="${mainCourse.teachMethod.ordinal()==0}">selected</c:if> >一对一</option>
                            <option value="1" <c:if test="${mainCourse.teachMethod.ordinal()==1}">selected</c:if> >小组课</option>
                            <option value="2" <c:if test="${mainCourse.teachMethod.ordinal()==2}">selected</c:if> >综合班</option>
                        </select>
                    </div>
                </div>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label>课程名称</label>
                        <select name="mainCourseId" id="mainCourseId" onchange="getMainCourseId()"  >
                            <c:forEach items='${mainCourseList}' var="course">
                                <option <c:if test="${course.courseId==mainCourse.courseId}">selected</c:if> value="${course.courseId}">${course.courseName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="uk-form-row parsley-row">
                        <label>上课时间</label>
                        <p>
                            <input type="checkbox" name="classTime"   value="1" id="checkbox_demo_1"
                                    <c:forEach items='${classTimeList}' var="classTime">
                                        <c:if test="${classTime==1}">checked</c:if>
                                    </c:forEach>
                                   data-md-icheck />
                            <label for="checkbox_demo_1" class="inline-label">星期一</label>
                            <input type="checkbox" name="classTime"  value="2"  id="checkbox_demo_2"
                                    <c:forEach items='${classTimeList}' var="classTime">
                                        <c:if test="${classTime==2}">checked</c:if>
                                    </c:forEach>
                                   data-md-icheck />
                            <label for="checkbox_demo_2" value="2" class="inline-label">星期二</label>
                            <input type="checkbox" name="classTime" value="3" id="checkbox_demo_3"
                                    <c:forEach items='${classTimeList}' var="classTime">
                                        <c:if test="${classTime==3}">checked</c:if>
                                    </c:forEach>
                                   data-md-icheck  />
                            <label for="checkbox_demo_3" class="inline-label">星期三</label>
                            <input type="checkbox" name="classTime" value="4" id="checkbox_demo_4"
                                    <c:forEach items='${classTimeList}' var="classTime">
                                        <c:if test="${classTime==4}">checked</c:if>
                                    </c:forEach>
                                   data-md-icheck  />
                            <label for="checkbox_demo_4" class="inline-label">星期四</label>
                            <input type="checkbox" name="classTime" value="5" id="checkbox_demo_5"
                                    <c:forEach items='${classTimeList}' var="classTime">
                                        <c:if test="${classTime==5}">checked</c:if>
                                    </c:forEach>
                                   data-md-icheck  />
                            <label for="checkbox_demo_4" class="inline-label">星期五</label>
                        </p>
                    </div>
                </div>
            </div>
            <h5 style="color:#000000">辅课程</h5>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">课程类型</label>
                        <select name="secondTeachMethod" id="secondTeachMethod" onchange="getSecondCourseByTeachMethod()" required data-md-selectize  >
                            <option value="0"
                                    <c:if test="${secondCourse.teachMethod.ordinal()==0}">selected</c:if> >一对一</option>
                            <option value="1" <c:if test="${secondCourse.teachMethod.ordinal()==1}">selected</c:if> >小组课</option>
                        </select>
                    </div>
                </div>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">课程名称</label>
                        <select name="secondCourseId" id="secondCourseId" onchange="getCoursePrice()">
                            <c:forEach items='${secondCourseList}' var="cou">
                                <option <c:if test="${cou.courseId==secondCourse.courseId}">selected</c:if> value="${cou.courseId}">${cou.courseName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin="" style="margin-top:0 ;">
                <div class="uk-width-medium-1-2">
                        <%--<p style="display: inline-block;width: 30%;">标准课次&nbsp;&nbsp;<span>10次</span></p>--%>
                    <p style="display: inline-block;width: 32%;">课时原价：&nbsp;&nbsp;<span  id="price">${secondCourse.unitPrice}</span></p>
                        <%--<p style="display: inline-block;width: 30%;">总费用：&nbsp;&nbsp;<span>1000元</span</p>--%>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">赠送次数</label>
                        <form:input path="giveCount" cssClass="md-input" onchange="getOldPrice()"/>
                    </div>
                </div>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">优惠单价&nbsp;(单位：元/课程）</label>
                        <form:input path="discountPrice" cssClass="md-input" onchange="getDiscountPrice()"/>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label>原价:</label><label id="oldAllPrice">${discount.unitPrice*discount.giveCount}</label>
                    </div>
                </div>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        优惠价:<label id="discountAllPrice">${discount.secondCourse.unitPrice*discount.giveCount}</label>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label>有效日期</label>
                        <form:input class="md-input" path="beginDate" data-uk-datepicker="{format:'YYYY-MM-DD'}" value=""/>
                    </div>
                </div>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label>截止日期</label>
                        <form:input class="md-input" path="endDate" data-uk-datepicker="{format:'YYYY-MM-DD'}" value=""/>
                    </div>
                </div>
            </div>
            <div class="uk-grid">
                <div class="uk-width-1-1">
                    <button type="submit" class="md-btn md-btn-primary">提交保存</button>
                    <a class="md-btn" href="discounts.do">返回</a>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
