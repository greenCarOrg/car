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
    <title>${initParam['system-name']}管理系统</title>
    <script type="text/javascript" src="${ctx}/js/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${ctx}/js/ueditor/ueditor.all.js"></script>
    <style type="text/css">

        #mo{
            display: none;/*隐藏*/
            width: 100%;
            height: 100%;
            position: fixed;
            overflow: auto;
            background-color: rgba(0,0,0,0.7);
            top: 0px;
            left: 0px;
            z-index: 1;
        }
        #moimg{
            display: block;
            margin:150px auto;
            width: 60%;
            max-width: 750px;
        }
        #caption{
            text-align: center;
            margin: 100px auto;
            width: 60%;
            max-height: 750px;
            font-size: 20px;
            color:#ccc;
        }
        #moimg,#caption{
            -webkit-animation: first 1s;
            -o-animation: first 1s;
            animation: first 1s;
        }
        @keyframes first{
            from{transform: scale(0.1);}
            to{transform: scale(1);}
        }
        .close{
            font-size: 40px;
            font-weight: bold;
            position: absolute;
            top: 100px;
            right: 14%;
            color:#f1f1f1;
        }
        .close:hover,
        .close:focus{
            color:#bbb;
            cursor:pointer;
        }
        @media only screen and(max-width:750px ) {
            #moimg{
                width: 100%;
            }
        }
    </style>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">用户反馈</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-content" style="height: auto !important;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label>电话号码</label>
                            <input class="md-input" type="text" id="phone" value=""/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6" style="line-height: 56px;padding-top: 4px;">
                        <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                    </div>
                </div>
                <div class="uk-grid">
                    <div class="uk-width-medium-1-1">
                        <kendo:grid name="grid" pageable="true" sortable="true" filterable="true" selectable="true" height="680" style="border-width:0px;">
                            <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/><kendo:grid-columns>
                            <kendo:grid-column title="编号" field="id"  filterable="false" hidden="true" />
                            <kendo:grid-column title="用户姓名" field="name"  filterable="false"/>
                            <kendo:grid-column title="电话号码" field="phone"  filterable="false"/>
                            <kendo:grid-column title="创建时间" field="create_time" filterable="false"/>
                            <kendo:grid-column title="显示图片" filterable="false">
                                <kendo:grid-column-template>
                                    <script>
                                        function template(options) {
                                            var html="";
                                            if(options.img !=null){
                                                 html= html+"<img style='width: 80px;height: 100px;' onclick='showImg(this)' src="+'http://7xjew0.com2.z0.glb.qiniucdn.com/'+options.img+">";
                                            }else{
                                                 html="尚未上传图片";
                                            }
                                            return html;
                                        }
                                    </script>
                                </kendo:grid-column-template>
                            </kendo:grid-column>
                            <kendo:grid-column title="问题" field="content"  filterable="false"/>
                            <kendo:grid-column title="反馈" field="replay" filterable="false" hidden="true"/>
                            <kendo:grid-column title="更新时间" field="update_time" filterable="false"/>
                            <kendo:grid-column title="创建人" field="real_name"  filterable="false"/>
                            <kendo:grid-column title="操作" >
                                <kendo:grid-column-template>
                                    <script>
                                        function template(options) {
                                            var html="<div style='text-align:center;margin:0 auto'>";
                                            if(options.state==0){
                                                html=html +"<a class='k-button k-button-icontext grid-button k-grid-user_id' href='toReplay.do?id="+options.id+"'>回复<a/>";
                                            }else{
                                                html=html+"<a class='k-button k-button-icontext grid-button k-grid-user_id' onclick='showReplay(this)'>查看<a/>";
                                            }
                                            return html;
                                        }
                                    </script>
                                    </script>
                                </kendo:grid-column-template>
                            </kendo:grid-column>
                        </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements"></kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="feedbookList.do" type="POST" contentType="application/json"/>
                                    <kendo:dataSource-transport-parameterMap>
                                        <script>
                                            function parameterMap(options, type) {
                                                options.phone=$("#phone").val();
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


<div class="configuration k-widget k-header" style="display: none;">
    <kendo:window name="updateValuie" title="回复" style="overflow: hidden;"  width="950" height="400" >
        <kendo:window-content>
            <div id="editor" name="editor"></div>
            <div id="btnDiv" data-uk-grid-margin align="center" style="border: 0px solid green;margin: 15px auto 10px;">
                <button type="button" class="md-btn md-btn-primary" onclick="closeWindow()">取消</button>
            </div>
        </kendo:window-content>
    </kendo:window>
</div>

<div class="motai" id="mo">
    <span class="close" id="close" onclick="hideImg()">×</span>
    <img class="motaiimg" id="moimg">
    <div id="caption"></div>
</div>
<script type="application/javascript">


    function showImg(e){
        var item = $("#grid").data("kendoGrid").dataItem($($(e).parent()).closest("tr"));
        var motai=document.getElementById('mo');
        var moimg=document.getElementById("moimg");
        motai.style.display="block"
        moimg.src='http://7xjew0.com2.z0.glb.qiniucdn.com/'+item.img;
    }

    function hideImg(){
        var motai=document.getElementById('mo');
        motai.style.display="none";
    }

    $(document).ready(function() {
        //初始化编辑器
//        ue = UE.getEditor('editor',{
//            toolbars: toolbars
//        });
    });

    function reloadData() {
        $("#grid").data("kendoGrid").dataSource.read();
    }

    function showReplay(e){
        var item = $("#grid").data("kendoGrid").dataItem($($(e).parent()).closest("tr"));
        ue.ready(function() {
            ue.setDisabled();
            ue.setContent(item.replay,false);
        });
        $("#updateValuie").data("kendoWindow").open();
        $("#updateValuie").data("kendoWindow").center();
    }

    function closeWindow() {
        $("#updateValuie").data("kendoWindow").close();
        $("#grid").data("kendoGrid").dataSource.read();
    }
</script>
</body>
</html>