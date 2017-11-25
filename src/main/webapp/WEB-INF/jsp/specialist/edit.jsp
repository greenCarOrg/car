<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        .title{
            color:#000000;
            font-weight: 600;
        }
        .uploadImg{ font-size:12px; overflow:hidden; position:absolute}
        .myfiles{ position:absolute; z-index:100; width: 150px; margin-left: -50px; font-size:60px;opacity:0;filter:alpha(opacity=0); margin-top:-5px;}
    </style>
    <script type="text/javascript" src="${ctx}/js/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${ctx}/js/ueditor/ueditor.all.js"></script>
    <script src="${ctx}/js/plupload/qiniu.min.js"></script>
    <script src="${ctx}/js/plupload/plupload.full.min.js"></script>
    <script type="application/javascript" src="${ctx}/js/common/select.js"></script>
    <script type="application/javascript">
        jQuery(document).ready(function () {
            validataFrom();
        });
        function validataFrom() {
            jQuery("#validateSubmitForm").validate({
                rules: {
                    name: {
                        required: true,
                        minlength: 0,
                        maxlength: 32
                    },
                    phone: {
                        required: true,
                        mobile:true,
                        minlength: 0,
                        maxlength: 32
                    },
                    idCard: {
                        required: true,
                        chrnum:true,
                        minlength: 18,
                        maxlength: 18
                    },
                    age: {
                        required: true,
                        digits:true,
                        range:[1,120]
                    },
                    reputation: {
                        required: true,
                        range:[0,5],
                        decimal2:[1,1]
                    },
                    summary: {
                        maxlength:30,
                        minlength:1
                    }
                }
            });
        }
        var ue=null;
        function initData(){
            var contant = '${entity.remark}';
            ue.ready(function() {
                ue.setContent(contant,false);
            });
        }
        $(document).ready(function () {
            //初始化编辑器
            ue = UE.getEditor('remark',{
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
            //初始化数据
            initData();
            initRecommend();
        });
        function initRecommend() {
            var refer=StrUtil.isNotEmpty("${entity.refer}")?"${entity.refer}":0;
            $.post("${ctx}/combobox/boxRecommend.do", null, function (data) {
                if (data!= null) {
                    $("#refer").kendoComboBox({
                        placeholder: "请选择...",
                        dataTextField: "text",
                        dataValueField: "value",
                        dataSource: data,
                        filter: "contains",
                        suggest: true,
                        index: 0
                    });
                    if (refer!= 0) {
                        $("#refer").data("kendoComboBox").value(refer);
                    }
                }
            });
        }

        /**
         * 检查手机号码是否存在
         */
        function checkPhone(phone){
            var id=$("#id").val();
            if (!/^1[0-9]{10}$/.test(phone)) {
                UIkit.notify({message: '手机号码必须是11位数字!', status: 'danger', timeout: 1000, pos: 'top-center'});
                $("#phone").val("");
                return;
            }else{
                $.get("../specialist/checkPhone.do?phone="+phone,null,
                    function(msg){
                        var resultCode=msg.resultCode;
                        if(resultCode == 'FAIL'){
                            if(StrUtil.isNotEmpty(id)){
                                var result=msg.result;
                                if(result.id!=id){
                                    UIkit.notify({message: '该手机号码已被专家注册，请更换手机号码!', status: 'danger', timeout: 1000, pos: 'top-center'});
                                    $("#phone").val("");
                                }
                            }else {
                                UIkit.notify({message: '该手机号码已被专家注册，请更换手机号码!', status: 'danger', timeout: 1000, pos: 'top-center'});
                                $("#phone").val("");
                            }
                        }
                });
            }
        }
        /**
         * 上传预览图片
         * @param imgFile：文件
         * @param previewDiv：渲染div-img的id
         * @param num:上传文件的数量
         * @constructor
         */
        function PreviewImage(imgFile,previewDiv,num) {
            var pattern = /(\.*.jpg$)|(\.*.png$)|(\.*.jpeg$)|(\.*.gif$)|(\.*.bmp$)/;
            if(!pattern.test(imgFile.value)) {
                UIkit.notify({message: '系统仅支持jpg/jpeg/png/gif/bmp格式的图片！', status:'danger', timeout: 1000, pos: 'top-center'});
                imgFile.focus();
            } else {
                var files = imgFile.files;
                var imgs = "";
                if(files.length>num){
                    UIkit.notify({message: '最多上传'+num+'张图片!', status:'danger', timeout: 1000, pos: 'top-center'});
                    var idFile = $(this).attr("id");
                    $("#"+idFile).val("");
                    return;
                }
                for(var i=0;i<files.length;i++){
                    var path = URL.createObjectURL(imgFile.files[i]);
                    imgs +="<img src='"+path+"' style='width:180px;height:100px;float:left;' />";
                }
                document.getElementById(previewDiv).innerHTML = imgs;
                var checkbox=previewDiv.substring(0,previewDiv.length-10);
                if (StrUtil.isNotEmpty(checkbox)) {
                    $("#"+checkbox+"").prop("checked",true);
                }
            }
        }
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom"><c:if test="${empty entity.id}">添加</c:if><c:if test="${not empty entity.id}">编辑</c:if>专家</h3>
<div class="md-card">
    <div class="md-card-content large-padding" style="height: auto !important;">
        <form:form id="validateSubmitForm" cssClass="uk-form-stacked" action="edit.do" commandName="specialist" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${entity.id}"/>
            <h3 class="title">基本信息</h3>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-4">
                    <div class="parsley-row">
                        <label for="name" class="uk-form-label required" >专家姓名</label>
                        <input name="name" id="name" value="${entity.name}" style="width: 100%;"/>
                    </div>
                </div>
                <div class="uk-width-medium-1-4">
                    <div class="parsley-row">
                        <label class="uk-form-label">电话</label>
                        <input name="phone" id="phone" placeholder="手机号码" onchange="checkPhone(this.value)" value="${entity.phone}" onkeyup='this.value=this.value.replace(/\D/gi,"")' style="width: 100%;"/>
                    </div>
                </div>
                <div class="uk-width-medium-1-4">
                    <div class="parsley-row">
                        <label class="uk-form-label">身份证号码</label>
                        <input name="idCard" id="idCard" placeholder="手机号码" value="${entity.idCard}" maxlength="18" style="width: 100%;"/>
                    </div>
                </div>
                <div class="uk-width-medium-1-4">
                    <div class="parsley-row">
                        <label class="uk-form-label">录属医院</label>
                        <input name="affiliatedHospital" id="affiliatedHospital"  value="${entity.affiliatedHospital}" maxlength="18" style="width: 100%;"/>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-4">
                    <div style="width: 30%;float:left;">
                        <label class="uk-form-label">省<span class="req">*</span></label>
                        <select name="province" id="province" style="width: 100%;">
                            <option value="">--</option>
                            <c:forEach var="p" items="${provinceList}">
                                <option value="${p.id}" <c:if test="${entity.province==p.id}">selected</c:if>>${p.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div style="width: 30%;float:left;">
                        <label class="uk-form-label">市<span class="req">*</span></label>
                        <select name="city" id="city" style="width: 100%;">
                            <option value="">--</option>
                            <c:forEach var="c" items="${cityList}">
                                <option value="${c.id}" <c:if test="${entity.city==c.id}">selected</c:if>>${c.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div style="width: 30%;float:left;">
                        <label class="uk-form-label">区<span class="req">*</span></label>
                        <select name="district" id="district" style="width: 100%;">
                            <option value="">--</option>
                            <c:forEach var="d" items="${districtList}">
                                <option value="${d.id}" <c:if test="${entity.district==d.id}">selected</c:if>>${d.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="uk-width-medium-1-4">
                    <div class="parsley-row">
                        <label class="uk-form-label">推荐人</label>
                        <input id="refer" name="refer" placeholder="选择经纪人..." style="width: 50%;" />
                    </div>
                </div>
                <div class="uk-width-medium-1-4">
                    <div class="parsley-row">
                        <label class="uk-form-label">年龄</label>
                        <input name="age" id="age" value="${entity.age}" onkeyup='this.value=this.value.replace(/\D/gi,"")' style="width: 100%;"/>
                    </div>
                </div>
                <div class="uk-width-medium-1-4">
                    <div class="uk-form-row parsley-row">
                        <label class="uk-form-label">简介</label>
                        <textarea class="md-input" name="summary" cols="10" rows="1" data-parsley-trigger="keyup"
                                  data-parsley-minlength="20" data-parsley-maxlength="100"
                                  data-parsley-validation-threshold="10"
                                  data-parsley-minlength-message="Come on! You need to enter at least a 20 caracters long comment..">${entity.summary}</textarea>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-4">
                    <div class="parsley-row">
                        <label class="uk-form-label">口碑</label>
                        <input name="reputation" id="reputation" placeholder="输入0-5之间的数字" value="${entity.reputation}" style="width: 50%;"/>
                    </div>
                </div>
            </div>
            <hr>
            <h3 class="title">认证信息</h3>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-5">
                    <div class="uk-form-row parsley-row">
                        <label>身份证</label>
                        <input type="checkbox" name="isIdCard" id="isIdCard" value="1" <c:if test="${entity.isIdCard==1}">checked="checked"</c:if>/>
                        <span class="uploadImg">
                            <input type="file" name="isIdCardImg" id="isIdCardImg" class="myfiles" size="1" accept="image/*" multiple="multiple" onchange="PreviewImage(this,'isIdCardImgPreview',2)"/>
                            <a href="#">上传身份证图片</a>
                        </span>
                        <div id="isIdCardImgPreview" style="width:100%; height:100px;">
                            <c:if test="${not empty idCardImgs}">
                                <c:forEach var="img" items="${idCardImgs}">
                                    <img src="${initParam['QNURL']}${img}" style="width:180px;height:100px;float: left;"/>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="uk-width-medium-1-5">
                    <div class="uk-form-row parsley-row">
                        <label>健康证</label>
                        <input type="checkbox" name="isHealthCertificate" id="isHealthCertificate" value="1" <c:if test="${entity.isHealthCertificate==1}">checked="checked"</c:if>/>
                        <span class="uploadImg">
                            <input type="file" name="isHealthCertificateImg" id="isHealthCertificateImg" class="myfiles" size="1" accept="image/*" onchange="PreviewImage(this,'isHealthCertificateImgPreview',1)" />
                            <a href="#">健康证图片</a>
                        </span>
                        <div id="isHealthCertificateImgPreview" style="width:100%; height:100px;">
                            <c:if test="${not empty entity.healthCertificateKey}">
                                <img src="${initParam['QNURL']}${entity.healthCertificateKey}" style="width:180px;height:100px;"/>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="uk-width-medium-1-5">
                    <div class="uk-form-row parsley-row">
                        <label>母婴护理师证${entity.isMctCertificate}</label>
                        <input type="checkbox" name="isMctCertificate" id="isMctCertificate" value="1" <c:if test="${entity.isMctCertificate==1}">checked="checked"</c:if>/>
                        <span class="uploadImg">
                            <input type="file" name="isMctCertificateImg" id="isMctCertificateImg" class="myfiles" size="1" accept="image/*" onchange="PreviewImage(this,'isMctCertificateImgPreview',1)" />
                            <a href="#">母婴护理师证图片</a>
                        </span>
                        <div id="isMctCertificateImgPreview" style="width:100%; height:100px;">
                            <c:if test="${not empty entity.mctCertificateKey}">
                                <img src="${initParam['QNURL']}${entity.mctCertificateKey}" style="width:180px;height:100px;"/>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="uk-width-medium-1-5">
                    <div class="uk-form-row parsley-row">
                        <label>育婴师证</label>
                        <input type="checkbox" name="isNtCartificate" id="isNtCartificate" value="1" <c:if test="${entity.isNtCartificate==1}">checked="checked"</c:if>/>
                        <span class="uploadImg">
                            <input type="file" name="isNtCartificateImg" id="isNtCartificateImg" class="myfiles" size="1" accept="image/*" onchange="PreviewImage(this,'isNtCartificateImgPreview',1)" />
                            <a href="#">育婴师证图片</a>
                        </span>
                        <div id="isNtCartificateImgPreview" style="width:100%; height:100px;">
                            <c:if test="${not empty entity.ntCartificateKey}">
                                <img src="${initParam['QNURL']}${entity.ntCartificateKey}" style="width:180px;height:100px;"/>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="uk-width-medium-1-5">
                    <div class="uk-form-row parsley-row">
                        <label>等级认证证书</label>
                        <input type="checkbox" name="isGradeCartificate" id="isGradeCartificate" value="1" <c:if test="${entity.isGradeCartificate==1}">checked="checked"</c:if>/>
                        <span class="uploadImg">
                            <input type="file" name="isGradeCartificateImg" id="isGradeCartificateImg" class="myfiles" size="1" accept="image/*" onchange="PreviewImage(this,'isGradeCartificateImgPreview',1)" />
                            <a href="#">等级认证证书图片</a>
                        </span>
                        <div id="isGradeCartificateImgPreview" style="width:100%; height:100px;">
                            <c:if test="${not empty entity.gradeCartificateKey}">
                                <img src="${initParam['QNURL']}${entity.gradeCartificateKey}" style="width:180px;height:100px;"/>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="uk-form-row parsley-row">
                        <label class="uk-form-label">个人形象</label>
                        <span id="uploadImg">
                            <input type="file" name="images" id="images" class="myfiles" accept="image/*" onchange="PreviewImage(this,'imagesPreview',7)" multiple>
                            <a href="#">上传个人形象图片</a>
                        </span>
                        <div id="imagesPreview" style="width:100%; height:210px;">
                            <c:if test="${not empty images}">
                                <c:forEach var="image" items="${images}">
                                    <img src="${initParam['QNURL']}${image}" style="width:180px;height:100px;float: left;"/>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="uk-width-medium-1-2">
                    <div class="uk-form-row parsley-row">
                        <label class="uk-form-label">视屏秀</label>
                        <input type="file" name="videoFile" id="videoFile" accept="MPEG-4 Audio/video/*">
                    </div>
                </div>
            </div>
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-1">
                    <div class="uk-form-row parsley-row">
                        <label class="uk-form-label">从业背景</label>
                        <div id="remark" name="remark" style="width: 100%;height: 300px;"></div>
                    </div>
                </div>
            </div>
            <div class="uk-grid">
                <div class="uk-width-1-1">
                    <button type="submit" class="md-btn md-btn-primary" onclick="save()">提交保存</button>
                    <a class="md-btn" href="maternity.do">返回</a>
                </div>
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
