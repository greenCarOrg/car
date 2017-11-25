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
    <script>
        function reloadData() {
            $("#grid").data("kendoGrid").dataSource.read();
        }

        function showImg(e){
            var item = $("#grid").data("kendoGrid").dataItem($($(e).parent()).closest("tr"));
            var motai=document.getElementById('mo');
            var moimg=document.getElementById("moimg");
            motai.style.display="block"
            moimg.src=item.img;
        }

        function hideImg(){
            var motai=document.getElementById('mo');
            motai.style.display="none";
        }
    </script>

</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">会员管理</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-content" style="height: auto !important;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label>电话号码</label>
                            <input class="md-input" type="text" id="phone" value="${phone}"/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6" style="line-height: 56px;padding-top: 4px;">
                        <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                    </div>
                </div>
                <div class="uk-grid">
                    <div class="uk-width-medium-1-1">
                    <kendo:grid name="grid" pageable="true" sortable="true" filterable="false" selectable="true" height="700" style="border-width:0px;">
                        <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/><kendo:grid-columns>
                        <kendo:grid-column title="编号" field="user_id" width="70px" />
                        <kendo:grid-column title="电话" field="phone" width="100px"/>
                        <kendo:grid-column title="姓名" field="user_name" width="100px"/>
                        <kendo:grid-column title="性别" field="sex" filterable="false" width="90px"/>
                        <kendo:grid-column title="账户余额" field="user_money" width="90px" hidden="true"/>
                        <kendo:grid-column title="显示图片" filterable="false" width="100px">
                            <kendo:grid-column-template>
                                <script>
                                    function template(options) {
                                        var html="";
                                        if(options.img !=null){
                                            html= html+"<img style='width: 80px;height: 100px;' onclick='showImg(this)' src="+'http://7xjew0.com2.z0.glb.qiniucdn.com/'+options.head+">";
                                        }else{
                                            html="尚未上传图片";
                                        }
                                        return html;
                                    }
                                </script>
                            </kendo:grid-column-template>
                        </kendo:grid-column>
                        <kendo:grid-column title="身份证号码" field="id_card" width="180px"/>
                        <kendo:grid-column title="注册时间" field="reg_time" width="100px"/>
                        <kendo:grid-column title="预产期" field="expect_date" width="110px" filterable="false"/>
                        <kendo:grid-column title="宝宝生日" field="child_birth" width="110px" filterable="false"/>
                        <kendo:grid-column title="状态" field="state" width="110px" filterable="false" template="#=(state==0)?'启用':'锁定'#"/>
                    </kendo:grid-columns>
                            <kendo:dataSource pageSize="20" serverPaging="true" serverFiltering="true" serverSorting="true">
                                <kendo:dataSource-schema data="content" total="totalElements"></kendo:dataSource-schema>
                                <kendo:dataSource-transport>
                                    <kendo:dataSource-transport-read url="pagedUsers.do" type="POST" contentType="application/json"/>
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
<div class="motai" id="mo">
    <span class="close" id="close" onclick="hideImg()">×</span>
    <img class="motaiimg" id="moimg">
    <div id="caption"></div>
</div>
</body>
</html>