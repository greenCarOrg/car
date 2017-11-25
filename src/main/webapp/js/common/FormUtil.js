/**
* form表单工具类
*/
var FormUtil={};
/**
 * 匹配密码，必须含数字、字母、特殊符号，长度4-16位之间。
 */
FormUtil.isPwd=function(str) {
    var reg = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+`\-={}:";'<>?,.\/]).{6,20}$/;
    return reg.test(str);
}/**
 * 匹配手机号码
 */
FormUtil.isPhone=function(str) {
    var reg =/^1\d{10}$/;
    return reg.test(str);
}