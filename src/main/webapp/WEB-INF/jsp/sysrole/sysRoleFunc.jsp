<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="../../assets/js/jquery-1.11.1.min.js"></script>
<html>
<head>
    <title></title>
    <script type="application/javascript">
        jQuery(document).ready(function () {
            $("input[id^='parent_func_id']").bind("click", function(){
                var value = $(this).prop("value");
                var checked = $(this).prop("checked");
                var id = $(this).prop("id");

                var sonId = "son_func_id_" + value;
                if(checked){
                    $("input[id='"+sonId+"']").each(function(){
                        $(this).prop("checked",true);
                    });
                }else{
                    $("input[id='"+sonId+"']").each(function(){
                        $(this).prop("checked",false);
                    });
                }
            });

            $("input[id^='son_func_id']").bind("click", function(){
                var id = $(this).prop("id");
                var idAry = id.split("_");
                var value = idAry[idAry.length -1];

                var checked = $(this).prop("checked");
                if(checked){
                    var parentId = "parent_func_id_" + value;
                    var isChecked = $("input[id='"+parentId+"']").prop("checked");
                    if(!isChecked){
                        $("input[id='"+parentId+"']").prop("checked",true);
                    }
                }else{
                    var count = 0;
                    var sonId = "son_func_id_" + value;
                    $("input[id='"+sonId+"']").each(function(){
                        var isChecked = $(this).prop("checked");
                        if(isChecked){
                            count = count + 1;
                        }
                    });

                    if(count == 0){
                        var parentId = "parent_func_id_" + value;
                        $("input[id='"+parentId+"']").prop("checked",false);
                    }
                }
            });
        });

        function checkRoleFunc(){
            var roleId = $("#roleId").val();
            if(roleId == 0){
                $("#msg_roleId").html("请选择您要分配的角色名");
                return false;
            }else{
                $("#msg_roleId").html("");
            }

            var count = 0;
            $("input[name='funcId']").each(function(){
                var isChecked = $(this).prop("checked");
                if(isChecked){
                    count = count + 1;
                }
            });

            if(count == 0){
                $("#msg_funcId").html("请选择您要分配的权限");
                return false;
            }else{
                $("#msg_funcId").html("");
            }
            $("#validateSubmitForm").submit();
        }
    </script>
</head>
<body class="sidebar_main_open sidebar_main_swipe">
<h3 class="heading_b uk-margin-bottom">分配权限</h3>
<div class="md-card">
    <div class="md-card-content large-padding">
        <form:form id="validateSubmitForm" action="updateSysRoleFunc.do" cssClass="uk-form-stacked" commandName="sysRole">
            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-1-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">角色名：<br><font style="color: #ff0000;font-size: 14px;">${sysRole.roleName}</font></label>
                        <input type = "hidden" name="functionId" id = "functionId" value="${func}">
                        <input type = "hidden" name="roleId" id = "roleId" value="${sysRole.id}">
                    </div>
                </div>
            </div>

            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-medium-2-2">
                    <div class="parsley-row">
                        <label class="uk-form-label">分配权限<span style="color:#ff0000;">*</span></label>
                        <c:forEach var="funcMap" items="${respMap}">
                            <input type="checkbox" name="funcId" value="${funcMap.key.id}" id="parent_func_id_${funcMap.key.id}">${funcMap.key.funcName}<br/>
                            <c:forEach items="${funcMap.value}" var="sonFunc">
                                &nbsp;&nbsp;<input type="checkbox" name="funcId" value="${sonFunc.id}" id='son_func_id_${funcMap.key.id}'/>${sonFunc.funcName}
                            </c:forEach>
                            <br/><br/>
                        </c:forEach>
                        <div id="msg_funcId" style="color: #ff0000;margin-top: 2px;"></div>
                    </div>
                </div>
            </div>



            <div class="uk-grid">
                <div class="uk-width-1-1">
                    <input type="button" class="md-btn md-btn-primary" onclick="return checkRoleFunc();" value="提交保存" />
                    <a class="md-btn" href="sysrole.do?explain=sysroledo">取消</a>
                </div>
            </div>
        </form:form>
    </div>
</div>
<script>
    var func = $("#functionId").val();
    if(func != null && func.length > 0){
        var funcIdAry = func.split(";");
        for(var id = 0 ; id  < funcIdAry.length; id ++){
            var funcId = funcIdAry[id];
            if(funcId > 0){
                $("input[name='funcId'][value='"+funcId+"']").prop("checked",true);
            }
        }
    }

</script>

</body>
</html>
