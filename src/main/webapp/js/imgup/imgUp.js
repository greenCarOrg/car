/*点击图片的文本框*/
function imgUpChange(obj) {
    var idFile = $(obj).attr("id");
    var file = document.getElementById(idFile);
    var fileList = file.files; //获取的图片文件
    // var imgContainer = $(obj).parents(".z_photo"); //存放图片的父亲元素

    var input = $(obj).parent();//文本框的父亲元素
    var imgArr = [];
    //遍历得到的图片文件
	fileList = validateUp(fileList);
	for(var i = 0;i<fileList.length;i++){
		var imgUrl = window.URL.createObjectURL(fileList[i]);
		imgArr.push(imgUrl);

		var $section = $(obj).parent(".z_file");
        $section.removeClass("z_file");
        $section.addClass("up-section");
        $section.children(".add-img").remove();
        $(obj).remove();
		// imgContainer.append($section);
		var $span = $("<span class='up-span'>");
		$span.appendTo($section);

		var $img0 = $("<img class='close-upimg'>").on("click",function(event){
            //删除当前父标签下所有元素，再添加原始元素
            $section.removeClass("up-section");
            $section.addClass("z_file");
            $section.empty();
            $section.append("<img src='../images/upload/a11.png' class='add-img'>");
            console.log(idFile);
            $section.append("<input type='file' name='file' id='"+idFile+"' onchange='imgUpChange(this)' class='file' value='' accept='image/jpg,image/jpeg,image/png,image/bmp' />");
		});
		$img0.attr("src","../images/upload/a7.png").appendTo($section);
		var $img = $("<img class='up-img up-opcity' id='i"+idFile+"'>");
		$img.attr("src",imgArr[i]);
		$img.appendTo($section);


		var $p = $("<p class='img-name-p'>");
		$p.html(fileList[i].name).appendTo($section);
		var $input = $("<input id='taglocation' name='taglocation' value='' type='hidden'>");
		$input.appendTo($section);
		var $input2 = $("<input id='tags' name='tags' value='' type='hidden'/>");
		$input2.appendTo($section);
	}
    setTimeout(function(){
        $(".up-section").removeClass("loading");
        $(".up-img").removeClass("up-opcity");
    },450);
    //input内容清空
    $(this).val("");
}
function validateUp(files){
    var arrFiles = [];//替换的文件数组
    var defaults = {
        fileType         : ["jpg","png","bmp","jpeg"],   // 上传文件的类型
        fileSize         : 1024 * 1024 * 10                  // 上传文件的大小 10M
    };
    for(var i = 0, file; file = files[i]; i++){
        //获取文件上传的后缀名
        var newStr = file.name.split("").reverse().join("");
        if(newStr.split(".")[0] != null){
            var type = newStr.split(".")[0].split("").reverse().join("");
            if(jQuery.inArray(type, defaults.fileType) > -1){
                // 类型符合，可以上传
                if (file.size >= defaults.fileSize) {
                    alert(file.size);
                    alert('您这个"'+ file.name +'"文件大小过大');
                } else {
                    // 在这里需要判断当前所有文件中
                    arrFiles.push(file);
                }
            }else{
                alert('您这个"'+ file.name +'"上传类型不符合');
            }
        }else{
            alert('您这个"'+ file.name +'"没有类型, 无法识别');
        }
    }
    return arrFiles;
}