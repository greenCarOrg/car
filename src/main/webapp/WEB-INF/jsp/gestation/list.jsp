<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<!doctype html>
<html class="lte-ie9" lang="en">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="msapplication-tap-highlight" content="no"/>
    <title>${initParam['system-name']}管理系统</title>
    <link rel="stylesheet" href="${ctx}/css/common/list.css" />
</head>
<body class="sidebar_main_open sidebar_main_swipe" id="bodyId">
<h3 class="heading_b uk-margin-bottom">孕期管理</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-content" style="height: auto !important;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label >周期</label>
                            <input class="md-input" type="text" id="id" value="${id}"/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6" style="line-height: 56px;padding-top: 4px;">
                        <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                    </div>
                </div>
                <div style="padding :0 30px 10px 0;text-align: right">
                    <a class="md-btn md-btn-primary" href="toEdit.do">添加孕期</a>
                </div>
                <div class="uk-grid">
                    <div class="uk-width-medium-1-1">
                        <kendo:grid name="grid" pageable="true" sortable="true" filterable="true" selectable="true" height="750" resizable="true" >
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
                                <kendo:grid-column title="孕期图片" filterable="false" width="90px">
                                    <kendo:grid-column-template>
                                        <script>
                                            function template(options) {
                                                var html="<img style='height: 130px;width:130px;'  onclick='showImg(this)' src="+getQNUrl()+options.img+">";
                                                return html;
                                            }
                                        </script>
                                    </kendo:grid-column-template>
                                </kendo:grid-column>
                                <kendo:grid-column title="孕期" field="id" width="5%" filterable="false"/>
                                <kendo:grid-column title="孕期注意事项" field="content" width="60%" filterable="false">
                                    <kendo:grid-column-template>
                                        <script>
                                            function template(options) {
                                                var content=options.content;
                                                if (StrUtil.isNotEmpty(content)) {
                                                    var html="<div style='text-align:left;margin:0 auto'>";
                                                    var strs=content.split("/");
                                                    for (var i=0;i<strs.length ;i++ ){
                                                        html+="</br><label>★"+strs[i]+"</label>";
                                                    }
                                                    html+="</div>";
                                                    return html;
                                                }
                                                return "";
                                            }
                                        </script>
                                    </kendo:grid-column-template>
                                </kendo:grid-column>
                                <kendo:grid-column title="操作" width="5%" filterable="false" >
                                    <kendo:grid-column-template>
                                        <script>
                                            function template(options) {
                                                var html="<div style='text-align:center;margin:0 auto'>" +
                                                        "<a class='k-button k-button-icontext grid-button k-grid-user_id' href='toEdit.do?id="+options.id+"'>编辑<a/>"+
                                                        "</div>";
                                                return html;
                                            }
                                        </script>
                                    </kendo:grid-column-template>
                                </kendo:grid-column>
                            </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements">
                                </kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="list.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                options.id=$("#id").val();
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
<div class="motai" id="mo">
    <span class="close" id="close" onclick="hideImg()">×</span>
    <img class="motaiimg" id="moimg">
    <div id="caption"></div>
</div>
<script type="application/javascript">
    //grid重新获取数据
    function reloadData(){
        $("#grid").data("kendoGrid").dataSource.read();
    }
    function showImg(e){
        var item = $("#grid").data("kendoGrid").dataItem($($(e).parent()).closest("tr"));
        var motai=document.getElementById('mo');
        var moimg=document.getElementById("moimg");
        motai.style.display="block"
        moimg.src=getQNUrl()+item.img_key;
    }
    function hideImg(){
        var motai=document.getElementById('mo');
        motai.style.display="none";
    }
</script>
</body>
</html>