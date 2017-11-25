/**清除表单方法
 * @param form
 */
function clearForm(form)
{
    //清除所有的JQueryEasyUI-combogrid
    var grid = $(".searchBar .easyui-combogrid");
    for(var i=0;i<grid.length;i++) {
        $(grid[i]).combogrid("clear");
    }
    var formObj = document.getElementsByName(form)[0];
    if(formObj == undefined)
    {
        return;
    }
    for(var i=0; i<formObj.elements.length; i++)
    {
        if(formObj.elements[i].type == "text")
        {
            formObj.elements[i].value = "";
        }
        if(formObj.elements[i].type == "hidden")
        {
            formObj.elements[i].value = "";
        }
        else if(formObj.elements[i].type == "password")
        {
            formObj.elements[i].value = "";
        }
        else if(formObj.elements[i].type == "radio")
        {
            formObj.elements[i].checked = false;
        }
        else if(formObj.elements[i].type == "checkbox")
        {
            formObj.elements[i].checked = false;
        }
        else if(formObj.elements[i].type == "select-one")
        {
            if (formObj.elements[i].options[0]!= null) {
                formObj.elements[i].options[0].selected = true;
            }
        }
        else if(formObj.elements[i].type == "select-multiple")
        {
            for(var j = 0; j < formObj.elements[i].options.length; j++)
            {
                formObj.elements[i].options[j].selected = false;
            }
        }
        else if(formObj.elements[i].type == "file")
        {
            //formObj.elements[i].select();
            //document.selection.clear();
            // for IE, Opera, Safari, Chrome
            var file = formObj.elements[i];
            if (file.outerHTML) {
                file.outerHTML = file.outerHTML;
            } else {
                file.value = "";  // FF(包括3.5)
            }
        }
        else if(formObj.elements[i].type == "textarea")
        {
            formObj.elements[i].value = "";
        }
    }
}