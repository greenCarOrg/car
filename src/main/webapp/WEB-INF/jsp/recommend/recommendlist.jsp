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
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">推荐人管理</h3>
<div class="uk-grid" data-uk-grid-margin>
    <div class="uk-width-1-1">
        <div class="md-card">
            <div class="md-card-content" style="height: auto !important;">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label>姓名</label>
                            <input class="md-input" type="text" id="name" value=""/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6">
                        <div class="uk-input-group">
                            <label>联系电话</label>
                            <input class="md-input" type="text" id="phone" value=""/>
                        </div>
                    </div>
                    <div class="uk-width-large-1-6" style="line-height: 56px;padding-top: 4px;">
                        <a class="md-btn md-btn-primary" href="javascript:reloadData();">查询</a>
                    </div>
                </div>
                <div style="padding :0 30px 10px 0;text-align: right">
                    <a class="md-btn md-btn-primary" href="toAddRecommend.do">新增</a>
                    <a class="md-btn md-btn-warning" href="javascript:editItem()">编辑</a>
                </div>
                <div class="uk-grid">
                    <div class="uk-width-medium-1-1">
                         <kendo:grid name="grid" pageable="true" sortable="true" filterable="true" selectable="true" height="680" style="border-width:0px;">
                             <kendo:grid-pageable refresh="true" pageSizes="true" buttonCount="5"/><kendo:grid-columns>
                             <kendo:grid-column title="编号" field="id" sortable="false" filterable="false"  width="90px"/>
                             <kendo:grid-column title="名称" field="name" sortable="false" filterable="false"  width="90px"/>
                             <kendo:grid-column title="联系电话" field="phone" sortable="false" filterable="false"  width="90px"/>
                             <kendo:grid-column title="身份证号码" field="id_card" sortable="false" filterable="false"  width="90px"/>
                             <kendo:grid-column title="性别" field="sex" width="80px" template="#= sex ? '男' : '女' #" filterable="false"/>
                            &lt;%&ndash; <kendo:grid-column title="头像" filterable="false" width="90px">
                                 <kendo:grid-column-template>
                                     <script>
                                         function template(options) {
                                             var html="<img style='height: 190px;' onclick='showImg(this)' src="+'http://7xjew0.com2.z0.glb.qiniucdn.com/'+options.head+">";
                                             return html;
                                         }
                                     </script>
                                 </kendo:grid-column-template>
                             </kendo:grid-column>&ndash;%&gt;
                             <kendo:grid-column title="管理员" field="real_name" sortable="false" filterable="false" width="90px"/>
                             <kendo:grid-column title="登记时间" field="create_time" sortable="false" filterable="false"  width="90px"/>
                             <kendo:grid-column title="状态" field="state" width="80px" template="#= state ? '启用' : '停用' #" filterable="false"/>
                             &lt;%&ndash;<kendo:grid-column title="操作" width="110px">
                                 <kendo:grid-column-command>
                                     <kendo:grid-column-commandItem name="enabledState" text="启用/停用" className="grid-button">
                                         <kendo:grid-column-commandItem-click>
                                             <script>
                                                 function enabledState(e) {
                                                     var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                                                     var state = dataItem.state;
                                                     UIkit.modal.confirm("确认"+ (state ? "启用" : "停用") +"吗?", function(){
                                                         $.get('enabledState.do', {
                                                             id: dataItem.id,
                                                             state: !state
                                                         }, function (data) {
                                                             if (data > 0) {
                                                                 $("#grid").data("kendoGrid").dataSource.read();
                                                                 UIkit.notify({message: '操作成功', status: 'success', timeout: 3000, pos: 'top-center'});
                                                             } else {
                                                                 UIkit.notify({message: '操作失败', status: 'danger', timeout: 3000, pos: 'top-center'});
                                                             }
                                                         });
                                                     },{labels:{Ok:"确认",Cancel:"取消"}});
                                                 }
                                             </script>
                                         </kendo:grid-column-commandItem-click>
                                     </kendo:grid-column-commandItem>
                                 </kendo:grid-column-command>
                             </kendo:grid-column>&ndash;%&gt;
                         </kendo:grid-columns>
                             <kendo:dataSource pageSize="5" serverPaging="true" serverFiltering="true" serverSorting="true">
                                 <kendo:dataSource-schema data="content" total="totalElements"></kendo:dataSource-schema>
                                 <kendo:dataSource-transport>
                                     <kendo:dataSource-transport-read url="recommendList.do" type="POST" contentType="application/json"/>
                                     <kendo:dataSource-transport-parameterMap>
                                         <script>
                                             function parameterMap(options, type) {
                                                 options.name=$("#name").val();
                                                 options.phone=$("#phone").val();
                                                 return JSON.stringify(options);
                                             }
                                         </script>
                                     </kendo:dataSource-transport-parameterMap>
                                 </kendo:dataSource-transport>
                             </kendo:dataSource>
                         </kendo:grid>--%>
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
    function showImg(e){
        var item = $("#grid").data("kendoGrid").dataItem($($(e).parent()).closest("tr"));
        var motai=document.getElementById('mo');
        var moimg=document.getElementById("moimg");
        motai.style.display="block";
        moimg.src='http://7xjew0.com2.z0.glb.qiniucdn.com/'+item.head;
    }

    function hideImg(){
        var motai=document.getElementById('mo');
        motai.style.display="none";
    }

    //grid重新获取数据
    function reloadData(){
        $("#grid").data("kendoGrid").dataSource.read();
    }

    function editItem() {
        var dataItem = getSelectedGridItem("grid");
        if (dataItem) {
            window.location.href = "toEditRecommend.do?id=" + dataItem.id;
        }
    }
</script>
</body>
</html>