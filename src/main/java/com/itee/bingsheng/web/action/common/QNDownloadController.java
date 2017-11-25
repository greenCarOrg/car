
package com.itee.bingsheng.web.action.common;

import com.itee.bingsheng.utils.QiNiuXiuXiuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;



@Controller
@RequestMapping(value = "/qnDownload")
public class QNDownloadController {

    /**从七牛服务器下载文件
     * @param fileKey
     * @param fileName
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "qnDownloadFile", method = RequestMethod.GET)
    @ResponseBody
    public void downLoadContractFile(@RequestParam("fileKey") String fileKey,@RequestParam("fileName") String fileName,HttpServletResponse response,HttpServletRequest request) throws Exception{
        String destUrl = QiNiuXiuXiuUtil.QINIUURL+fileKey;
        // 建立链接
        URL url = new URL(destUrl);
        HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
        // 连接指定的资源
        httpUrl.connect();
        // 获取网络输入流
        BufferedInputStream bis = new BufferedInputStream(httpUrl.getInputStream());

        //1.判断ie
        boolean isFirefox = isFirefox(request);
        //2.文件名处理
        if (isFirefox){// FF
            fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
        } else {// 其他浏览器
            fileName = URLEncoder.encode(fileName, "utf-8");
            fileName = fileName.replace("+", "%20");// 处理空格变“+”的问题
        }
        //3.准备响应头
        //这里filename用“"”英文的双引号扣起来，可以防止firefox空格节点文件名的问题
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        response.setContentType("application/x-download");
        response.addHeader("Content-Length", httpUrl.getContentLengthLong() + "");
        OutputStream out = response.getOutputStream();
        byte[] buf = new byte[1024];
        BufferedInputStream br = bis;
        int len = 0;
        while ((len = br.read(buf)) > 0){
            out.write(buf, 0, len);
        }
        br.close();
        out.flush();
        out.close();
    }

    //判断是否是火狐浏览器
    public static boolean isFirefox(HttpServletRequest request){
        return  request.getHeader( "USER-AGENT" ).toLowerCase().indexOf( "firefox" ) >  0  ?  true  :  false ;
    }

    @RequestMapping(value = "getUpToken",method = RequestMethod.GET)
    @ResponseBody
    public String getUpToken(ModelMap modelMap) {
        return QiNiuXiuXiuUtil.getUpToken();
    }

}
