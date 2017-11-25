<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${ctx}/assets/js/jquery-1.11.1.min.js"></script>
<html>
<head>
    <title>${initParam['system-name']}管理系统</title>
    <style>
        .req{color: red;}
    </style>
    <script type="text/javascript" src="${ctx}/js/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${ctx}/js/ueditor/ueditor.all.js"></script>
    <script src="${ctx}/js/plupload/qiniu.min.js"></script>
    <script src="${ctx}/js/plupload/plupload.full.min.js"></script>
    <script type="application/javascript">
        $(document).ready(function() {

            validataFrom();

            //初始化编辑器
            ue = UE.getEditor('goodsContent',{
                toolbars: biVToolbars
            });

            var uptoken = "";
            $.ajax({
                url: '../qnDownload/getUpToken.do',
                type: 'GET',
                async: false,//这里应设置为同步的方式
                success: function(data) {
                    uptoken = data;
                },
                cache: false
            });
            uploader = Qiniu.uploader({
                runtimes: 'html5,flash,html4',
                browse_button: 'pickfiles',//上传按钮的ID
                container: 'btn-uploader',//上传按钮的上级元素ID
                drop_element: 'btn-uploader',
                max_file_size: '200mb',//最大文件限制
                flash_swf_url: '../js/plupload-2.3.1/Moxie.swf',
                dragdrop: false,
                chunk_size: '4mb',//分块大小
                //uptoken_url: '../qnDownload/getUpToken.do',//设置请求qiniu-token的url
                //uptoken_func:getUpToken,
                //Ajax请求upToken的Url，**强烈建议设置**（服务端提供）
                uptoken:uptoken,
                //若未指定uptoken_url,则必须指定 uptoken ,uptoken由其他程序生成
                // unique_names: true,
                // 默认 false，key为文件名。若开启该选项，SDK会为每个文件自动生成key（文件名）
                save_key: true,
                // 默认 false。若在服务端生成uptoken的上传策略中指定了 `sava_key`，则开启，SDK在前端将不对key进行任何处理
                domain: 'http://7xjew0.com2.z0.glb.qiniucdn.com/ ',//自己的七牛云存储空间域名
                multi_selection: false,//是否允许同时选择多文件
                //文件类型过滤，这里限制为图片类型
                //filters: {
                //  mime_types : [
                //    {title : "Image files", extensions: "jpg,jpeg,gif,png"}
                //  ]
                //},
                auto_start: false,
                init: {
                    'FilesAdded': function(up, files) {
                        PreviewImage(files);
                        //删除选错的文件
                        if(up.files.length>1){
                            up.splice(0,up.files.length-1);
                        }
                    },
                    'BeforeUpload': function(up, file) {
                    },
                    'UploadProgress': function(up, file) {

                    },
                    'UploadComplete': function(up, file) {
                        //整个上传完成后执行
                        //如果没有上传失败的文件,则保存表单
                        if(up.total.failed==0){
                            $("#submitBtn").click();
                            //submintForm();
                        }
                    },
                    'FileUploaded': function(up, file, info) {
                        //每个文件上传成功后,处理相关的事情
                        var res = eval('(' + info + ')');
                        $("#titleImg").val(res.key);
                        //回填文件上传完成后返回的key
                    },
                    'Error': function(up, err, errTip) {

                    }
                }
            });
        });

        function validataFrom() {
            jQuery("#validateSubmitForm").validate({
                rules: {
                    goodsSn: {required: true,minlength: 6,maxlength: 40},
                    goodsName: {required: true,minlength: 2,maxlength: 60},
                    keywords: {maxlength: 40},
                    giveIntegral:{required: true,digits: true,min: 0,max: 1000000},
                    shopPrice:{required: true,number: true,min: 0,max: 10000000},
                    price:{required: true,number: true,min: 0,max: 10000000},
                    storeCount: {required: true,digits: true,min: 0,max: 10000000},
                    weight: {required: true,number: true,min: 0,max: 1000000},
                    sort:{required: true,digits: true,min: 0,max: 100000},
                    goodsRemark: {maxlength: 255},
                    commission: {required: true,number: true,min: 0,max: 1000000}
                }
            });
        }

        // 提交前校验数据
        function checkData() {
            var divs = $("div.parsley-errors-list");
            if(divs.length>0){
                return false;
            }

            if($("#catId").val()==''||$("#suppliersId").val()==''||$("#promType").val()==''){
                UIkit.notify({message: '有带*号必填项未填写！', status: 'danger', timeout: 1000, pos: 'top-center'});
                return false;
            }
            return true;
        }

        function PreviewImage(imgFile) {
            var pattern = /(\.*.jpg$)|(\.*.png$)|(\.*.jpeg$)|(\.*.gif$)|(\.*.bmp$)/;
            if(!pattern.test(imgFile.value)) {
                UIkit.notify({message: '系统仅支持jpg/jpeg/png/gif/bmp格式的图片！', status:'danger', timeout: 1000, pos: 'top-center'});
                imgFile.focus();
            } else {
                var files = imgFile.files;
                var imgs = "";
                if(files.length>10){
                    UIkit.notify({message: '最多上传10张图片!', status:'danger', timeout: 1000, pos: 'top-center'});
                    $("#myfiles").val("");
                }
                for(var i=0;i<files.length;i++){
                    var path = URL.createObjectURL(imgFile.files[i]);
                    imgs +="<img src='"+path+"' style='width:180px;height:100px;float:left;' />";
                }
                document.getElementById("imgPreview").innerHTML = imgs;
            }
        }
        //重新加载扩展分类
        function onChangeValue() {
            var catId=$("#catId").data("kendoComboBox").value();

            $.post("../combobox/getComboxData.do", {
                type:"goodGroup",
                parentId:catId
            }, function (data) {
                if (data!= null) {
                    var combobox = $("#extendCatId").data("kendoComboBox");
                    combobox.dataSource.data(data);
                }
            });
        }
    </script>
    <style>
        #uploadImg{ font-size:12px; overflow:hidden; position:absolute}
        #myfiles{ position:absolute; z-index:100; margin-left:-180px; font-size:60px;opacity:0;filter:alpha(opacity=0); margin-top:-5px;}
    </style>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
    <h3 class="heading_b uk-margin-bottom">新增商品</h3>
    <div class="md-card">
        <div class="md-card-content large-padding" style="height:auto !important;">
            <form:form id="validateSubmitForm" commandName="goods" onsubmit="return checkData()" cssClass="uk-form-stacked"  action="saveGood.do" enctype="multipart/form-data">
                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-medium-1-3">
                        <div class="parsley-row">
                            <label>商品编号<span class="req">*</span></label>
                            <form:input path="goodsSn" cssClass="md-input" value="${goodsSn}" readonly="true"/>
                        </div>
                    </div>
                    <div class="uk-width-medium-1-3">
                        <div class="parsley-row">
                            <label>商品名称<span class="req">*</span></label>
                            <form:input path="goodsName" cssClass="md-input"/>
                        </div>
                    </div>
                    <div class="uk-width-medium-1-3">
                        <div class="parsley-row">
                            <label>商品关键词</label>
                            <form:input path="keywords" cssClass="md-input"/>
                        </div>
                    </div>
                </div>

                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-medium-1-3">
                        <div class="uk-input-group">
                            <label>商品分类<span class="req">*</span></label>
                            <kendo:comboBox name="catId" filter="contains" placeholder="全部..." change="onChangeValue" suggest="true" dataTextField="value" dataValueField="key" style="width: 100%;">
                                <kendo:dataSource data="${goodGroupList}"></kendo:dataSource>
                            </kendo:comboBox>
                        </div>
                    </div>
                    <div class="uk-width-medium-1-3">
                        <div class="uk-input-group">
                            <label>商品扩展分类</label>
                            <kendo:comboBox name="extendCatId" filter="contains" placeholder="全部..." suggest="true" dataTextField="value" dataValueField="key" style="width: 100%;">
                                <kendo:dataSource data="${extendGroupList}"></kendo:dataSource>
                            </kendo:comboBox>
                        </div>
                    </div>
                    <div class="uk-width-medium-1-3">
                        <div class="uk-input-group">
                            <label>厂家<span class="req">*</span></label>
                            <kendo:comboBox name="suppliersId" filter="contains" placeholder="全部..." suggest="true" dataTextField="value" dataValueField="key" style="width: 100%;">
                                <kendo:dataSource data="${suppliers}"></kendo:dataSource>
                            </kendo:comboBox>
                        </div>
                    </div>
                </div>

                <div class="uk-grid" data-uk-grid-margin>
                   <div class="uk-width-medium-1-3">
                        <div class="parsley-row">
                            <label>进货价</label>
                            <form:input path="dealerprice" cssClass="md-input"/>
                        </div>
                    </div>
                    <div class="uk-width-medium-1-3">
                        <div class="parsley-row">
                            <label>售价<span class="req">*</span></label>
                            <form:input path="price" cssClass="md-input"/>
                        </div>
                    </div>
                </div>

                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-medium-1-3">
                        <div class="parsley-row">
                            <label>库存数量<span class="req">*</span></label>
                            <form:input path="storeCount" cssClass="md-input"/>
                        </div>
                    </div>
                    <div class="uk-width-medium-1-3">
                        <div class="parsley-row">
                            <label>商品重量</label>
                            <form:input path="weight" cssClass="md-input"/>
                        </div>
                    </div>
                    <div class="uk-width-medium-1-3">
                        <div class="parsley-row">
                            <label>商品排序<span class="req">*</span></label>
                            <form:input path="sort" cssClass="md-input"/>
                        </div>
                    </div>
                </div>

                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-medium-1-3">
                        <div class="uk-form-row parsley-row">
                            <label class="uk-form-label">是否为实物</label>
                            <span class="icheck-inline">
                                <input type="radio" name="isReal" id="is_real_enabled" value="1" data-md-icheck checked/>
                                <label for="is_real_enabled" class="inline-label">是</label>
                            </span>
                            <span class="icheck-inline" style="padding-left: 10%;">
                                <input type="radio" name="isReal" id="is_real_disabled" value="0" data-md-icheck/>
                                <label for="is_real_disabled" class="inline-label">否</label>
                            </span>
                        </div>
                    </div>
                    <div class="uk-width-medium-1-3">
                        <div class="uk-form-row parsley-row">
                            <label class="uk-form-label">是否上架</label>
                            <span class="icheck-inline">
                                <input type="radio" name="isOnSale" id="on_sale_enabled" value="1" data-md-icheck checked/>
                                <label for="on_sale_enabled" class="inline-label">上架</label>
                            </span>
                            <span class="icheck-inline" style="padding-left: 10%;">
                                <input type="radio" name="isOnSale" id="on_sale_disabled" value="0" data-md-icheck/>
                                <label for="on_sale_disabled" class="inline-label">下架</label>
                            </span>
                        </div>
                    </div>
                    <div class="uk-width-medium-1-3">
                        <div class="uk-form-row parsley-row">
                            <label class="uk-form-label">是否热卖</label>
                            <span class="icheck-inline">
                                <input type="radio" name="isHot" id="is_hot_enabled" value="1" data-md-icheck checked/>
                                <label for="is_hot_enabled" class="inline-label">是</label>
                            </span>
                            <span class="icheck-inline" style="padding-left: 10%;">
                                <input type="radio" name="isHot" id="is_hot_disabled" value="0" data-md-icheck/>
                                <label for="is_hot_disabled" class="inline-label">否</label>
                            </span>
                        </div>
                    </div>
                </div>

                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-medium-1-3">
                        <div class="uk-form-row parsley-row">
                            <label class="uk-form-label">是否推荐</label>
                            <span class="icheck-inline">
                                <input type="radio" name="isRecommend" id="recommend_enabled" value="1" data-md-icheck checked/>
                                <label for="recommend_enabled" class="inline-label">是</label>
                            </span>
                            <span class="icheck-inline" style="padding-left: 10%;">
                                <input type="radio" name="isRecommend" id="recommend_disabled" value="0" data-md-icheck/>
                                <label for="recommend_disabled" class="inline-label">否</label>
                            </span>
                        </div>
                    </div>
                    <div class="uk-width-medium-1-3">
                        <div class="uk-form-row parsley-row">
                            <label class="uk-form-label">是否新品</label>
                            <span class="icheck-inline">
                                <input type="radio" name="isNew" id="is_new_enabled" value="1" data-md-icheck checked/>
                                <label for="is_new_enabled" class="inline-label">是</label>
                            </span>
                            <span class="icheck-inline" style="padding-left: 10%;">
                                <input type="radio" name="isNew" id="is_new_disabled" value="0" data-md-icheck/>
                                <label for="is_new_disabled" class="inline-label">否</label>
                            </span>
                        </div>
                    </div>
                </div>

               <%-- <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-medium-1-3">
                        <div class="parsley-row">
                            <label>商品描述</label>
                            <form:input path="goodsRemark" cssClass="md-input"/>
                        </div>
                    </div>
                    <div class="uk-width-medium-1-3">
                        <div class="parsley-row">
                            <label>分销佣金<span class="req">*</span></label>
                            <form:input path="commission" cssClass="md-input"/>
                        </div>
                    </div>
                    <div class="uk-width-medium-1-3"></div>
                </div>--%>

                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-medium-1-2">
                        <label class="uk-form-label">商品详细描述</label>
                        <div id="goodsContent" name="goodsContent" style="width: 100%;height: 300px;"></div>
                    </div>
                    <div class="uk-width-medium-1-2">
                        <span id="uploadImg">
                            <input type="file" name="myfiles" id="myfiles" size="1" accept="image/*" onchange="PreviewImage(this)" multiple>
                            <a href="#">上传商品默认图片</a>
                        </span>
                        <div class="parsley-row">
                            <div id="imgPreview" style="width:800px; height:650px;">
                                <img id="imageFile" src="../images/course/course_default.png" style="width:380px;height:280px;">
                            </div>
                        </div>
                    </div>
                </div>

                <div class="uk-grid" data-uk-grid-margin>
                    <div class="uk-width-medium-1-1"  align="center">
                        <button id="submitBtn" type="submit" class="md-btn md-btn-primary">提交</button>
                        <a class="md-btn" href="good.do">返回</a>
                    </div>
                </div>

            </form:form>
        </div>
    </div>
</body>
</html>
