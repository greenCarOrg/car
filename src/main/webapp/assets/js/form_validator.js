$.validator.setDefaults(
    {
        submitHandler: function (form) {
            UIkit.modal.confirm("确认提交吗？", function () {
                form.submit();
            }, {labels: {Ok: "确认", Cancel: "取消"}});
        },
        showErrors: function (map, list) {

            //   this.currentElements.parents('.md-input-wrapper:first').parents('parsley-row:first').find('.parsley-errors-list').remove();
            //        this.currentElements.parents('.md-input-wrapper:first').removeClass('md-input-wrapper-danger');
            var labp = this.currentElements.parents('parsley-row:first').length ? this.currentElements.parents('parsley-row:first') : this.currentElements.parents('.parsley-row:first');
            labp.find('.parsley-errors-list').remove();
            $.each(list, function (index, error) {
                var ee = $(error.element);
                var eep = ee.parents('parsley-row:first').length ? ee.parents('parsley-row:first') : ee.parents('.parsley-row:first');

                ee.parents('.md-input-wrapper:first').addClass('md-input-wrapper-danger');
                eep.find('.parsley-errors-list').remove();
                eep.append('<div class="parsley-errors-list filled"><span class="parsley-required"> ' + error.message + '</span></div>');
            });
            //refreshScrollers();
        }
    });

$(document).ready(function () {
    jQuery.extend(jQuery.validator.messages, {
        required: "必填字段",
        remote: "请修正该字段",
        email: "请输入正确格式的电子邮件",
        url: "请输入合法的网址",
        date: "请输入合法的日期",
        dateISO: "请输入合法的日期 (ISO).",
        number: "请输入合法的数字",
        digits: "只能输入正整数",
        creditcard: "请输入合法的信用卡号",
        equalTo: "请再次输入相同的值",
        accept: "请输入拥有合法后缀名的字符串",
        maxlength: jQuery.validator.format("请输入一个 长度最多是 {0} 的字符串"),
        minlength: jQuery.validator.format("请输入一个 长度最少是 {0} 的字符串"),
        rangelength: jQuery.validator.format("请输入 一个长度介于 {0} 和 {1} 之间的字符串"),
        range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
        max: jQuery.validator.format("请输入一个最大为{0} 的值"),
        min: jQuery.validator.format("请输入一个最小为{0} 的值")
    });
    // 手机号码验证
    jQuery.validator.addMethod("mobile", function(value, element) {
        var length = value.length;
        var mobile =  /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "手机号码格式错误");

    // 电话号码验证
    jQuery.validator.addMethod("phone", function(value, element) {
        var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
        return this.optional(element) || (tel.test(value));
    }, "电话号码格式错误");

    // 邮政编码验证
    jQuery.validator.addMethod("zipCode", function(value, element) {
        var tel = /^[0-9]{6}$/;
        return this.optional(element) || (tel.test(value));
    }, "邮政编码格式错误");

    // QQ号码验证
    jQuery.validator.addMethod("qq", function(value, element) {
        var tel = /^[1-9]\d{4,9}$/;
        return this.optional(element) || (tel.test(value));
    }, "qq号码格式错误");

    // IP地址验证
    jQuery.validator.addMethod("ip", function(value, element) {
        var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
        return this.optional(element) || (ip.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));
    }, "Ip地址格式错误");

    // 字母和数字的验证
    jQuery.validator.addMethod("chrnum", function(value, element) {
        var chrnum = /^([a-zA-Z0-9]+)$/;
        return this.optional(element) || (chrnum.test(value));
    }, "只能输入数字和字母(字符A-Z, a-z, 0-9)");

    // 中文的验证
    jQuery.validator.addMethod("chinese", function(value, element) {
        var chinese = /^[\u4e00-\u9fa5]+$/;
        return this.optional(element) || (chinese.test(value));
    }, "只能输入中文");

    // 下拉框验证
    $.validator.addMethod("selectNone", function(value, element) {
        return value == "请选择";
    }, "必须选择一项");

    // 字节长度验证
    //使用：
    //byteRangeLength[3,4]
    jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {
        var length = value.length;
        for (var i = 0; i < value.length; i++) {
            if (value.charCodeAt(i) > 127) {
                length++;
            }
        }
        return this.optional(element) || (length >= param[0] && length <= param[1]);
    }, $.validator.format("请确保输入的值在{0}-{1}个字节之间(一个中文字算2个字节)"));

    //整数位，小数位验证
    //使用：decimal: {
    //     		integer: 12, //整数最大位数
    //     		fraction: 3  //小数点后最多位数
    // 	   }
    jQuery.validator.addMethod("decimal", function(value, element, param) {
            return this.optional(element) || new RegExp("^-?\\d{1," + (param.integer != null ? param.integer : "") + "}" + (param.fraction != null ? (param.fraction > 0 ? "(\\.\\d{1," + param.fraction + "})?$" : "$") : "(\\.\\d+)?$")).test(value);},
        "numeric value out of bounds");

    //整数位，小数位验证
    //demo:decimal2[3,4]
    jQuery.validator.addMethod("decimal2", function(value, element, param) {
        return this.optional(element) || new RegExp("^-?\\d{1," + (param[0] != null ? param[0] : "") + "}" + (param[1] != null ? (param[1] > 0 ? "(\\.\\d{1," + param[1] + "})?$" : "$") : "(\\.\\d+)?$")).test(value);
    }, $.validator.format("内容输入错误或者格式错误：整数位最多{0}位，小数位最多{1}位"));

    //不能包含sql特殊字符
    jQuery.validator.addMethod("filterDBcom", function(value, element) {
        var command = /select|update|delete|insert|declare|dbcc|alter|drop|creat|backup|add|set|open|close|exec|count|’|"|=|;|>|<|%/i;
        return this.optional(element) || !(command.test(value));
    }, "不能包含sql特殊字符");

    //不用包含html字符
    jQuery.validator.addMethod("filterHTML", function(value, element) {
        var chrnum = /<[^>]+>/;
        return this.optional(element) || !(chrnum.test(value));
    }, "不用包含html字符");

    //不能包含空格
    jQuery.validator.addMethod("haveBlank", function(value, element) {
        var is = value.indexOf(" ") >= 0?false:true;
        return this.optional(element) || is;
    }, "不能包含空格");

    //错误提示信息创建什么标签<label>xxxx</label>
    $.validator.setDefaults({
        errorElement : "label"
    });
});

