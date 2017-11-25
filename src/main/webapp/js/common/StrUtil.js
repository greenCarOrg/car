/**
* 字符创工具类
*/
var StrUtil={};
/**
 * 判断字符串是否为空：
 * @param str:被判断字符串
 * @returns {boolean}空->true;不为空->false
 */
StrUtil.isEmpty=function(str){
	if(str==''||str==null||str==undefined||str=="undefined")
		return true;
	return false;
};
/**
 * 判断字符串是否为空：
 * @param str:被判断字符串
 * @returns {boolean}空->false;不为空->true
 */
StrUtil.isNotEmpty=function(str){
	if(str==''||str==null||str==undefined||str=="undefined")
		return false;
	return true;
};
