package com.itee.bingsheng.utils;

import com.itee.bingsheng.support.ExecuteResult;
import com.itee.bingsheng.support.ResultCode;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;


public class QiNiuXiuXiuUtil {

    public static String ACCESS_KEY = "kEUFey_36xJqHaOhr44fNd8eNUo4a9TLtMJSrqhu";
    public static String SECRET_KEY = "FkNWRn5WDQB0TO9fLEy7fP7MrC3BpKNetM9Vc54V";
    public static String bucketName = "weidian";
    public static String QINIUURL = "http://7xjew0.com2.z0.glb.qiniucdn.com/";
    public static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    public static UploadManager uploadManager = new UploadManager();

    protected final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(QiNiuXiuXiuUtil.class);

    /**
     * 获取凭证
     * bucketName 空间名称
     * @return
     */
    public static String getUpToken() {
        return auth.uploadToken(bucketName);
    }

    @SuppressWarnings("restriction")
    public static String getCutUptoken(String key) {
        return "";
    }

    /**
     * 上传
     * @param key 上传到七牛上的文件的名称（同一个空间下，名称【key】是唯一的）
     * @param file
     */
    public static boolean upload(String key, File file) {
        boolean uploadOk = false;
        try {
            // 调用put方法上传
            Response res = uploadManager.put(file, key, getUpToken());
            // 打印返回的信息
            System.out.println(res.bodyString());
            logger.info("----------res.bodyString()-----------"+res.bodyString());
            uploadOk = true;
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            logger.info("----------r.toString()-----------"+r.toString());
            e.printStackTrace();
            try {
                // 响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException qe) {
                logger.info("----------r.bodyString()-----------"+r.bodyString());
                e.printStackTrace();
            }
        }finally {
            return uploadOk;
        }
    }

    /**
     * 上传
     * @param key 上传到七牛上的文件的名称（同一个空间下，名称【key】是唯一的）
     * @param file
     */
    public static boolean upload(String key, byte[] file) {
        try {
            // 调用put方法上传
            Response res = uploadManager.put(file, key, getUpToken());
            // 打印返回的信息
            System.out.println(res.bodyString());
            return true;
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                // 响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException qe) {
                // ignore
            }
        }finally {
            return false;
        }
    }

    /**
     * 删除七牛云存储上文件,（权限控制，不给删除）
     * @param key
     */
    @Deprecated
    public static void delete( String key) {
        BucketManager bucketManager = new BucketManager(auth);
        try {
            bucketManager.delete(bucketName,key);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载七牛云存储上文件
     * @param key
     * @return 返回下载路径
     */
    public static String downLoad(String key){
        String downloadRUL = auth.privateDownloadUrl(QINIUURL+key);
        return downloadRUL;
    }

    public static void main(String[] args) throws Exception{

        /*File file = new File("C:\\Users\\Administrator\\Desktop\\最新，最全HOST测试配置！1.txt");
        //File file = new File("http://7xjew0.com2.z0.glb.qiniucdn.com/06FACADB88EC4674A85EE040E23DE39E");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        while (reader.ready()){
            System.out.println(new String(reader.readLine().getBytes(),"gbk"));
        }
        String key = "F566E52AAF0B4322A4C747009905DA92";//CreateUUIDUtil.createUUID();;
        System.out.println("key:" + key);//06FACADB88EC4674A85EE040E23DE39E.txt
        //delete(key);
        upload(key, file);*/
        System.out.println(downLoad("4c45add01cf245ddac1c84c19d51b34d"));

    }


    /**
     * 上传一个文件返回外网地址
     * @param key
     * @param file
     * @return
     */
    public static String uploadReturnURL(String key, File file) {
        Response r=null;
        try {
            // 调用put方法上传
            Response res = uploadManager.put(file, key, getUpToken());
            logger.info("uploadReturnURL返回信息---------------------"+res.bodyString());
        } catch (QiniuException e) {
            r = e.response;
            logger.info("uploadReturnURL上传图片异常--------------------"+r.toString());
            e.printStackTrace();
        }finally {
            String downloadRUL=null;
            try {
                downloadRUL= auth.privateDownloadUrl(QINIUURL+key);
            } catch (Exception qe) {
                logger.info("uploadReturnURL获取图片地址出错---------------------"+ qe.toString());
                qe.printStackTrace();
            }finally {
                return downloadRUL;
            }
        }
    }
    /**
     * 上传一个文件返回key和文件名称
     * @param file
     * @return
     */
    public static Map<String,String> uploadReturnKey(MultipartFile file) {
        Map<String, String> map=new HashMap<String, String>();
        Response r=null;
        String key= CreateUUIDUtil.createUUID();
        String fileName="";
        try {
            fileName=file.getOriginalFilename();
            File upLoadFile = new File(file.getOriginalFilename());
            FileOutputStream outputStream = new FileOutputStream(upLoadFile);
            outputStream.write(file.getBytes());
            outputStream.flush();
            outputStream.close();
            // 调用put方法上传
            Response res = uploadManager.put(upLoadFile, key, getUpToken());
            logger.info("uploadReturnURL返回信息---------------------"+res.bodyString());
        } catch (QiniuException e) {
            r = e.response;
            logger.info("uploadReturnURL上传图片异常--------------------"+r.toString());
            e.printStackTrace();
            return null;
        }finally {
            map.put("fileName",fileName);
            map.put("fileKey",key);
            return map;
        }
    }

    /**
     * 更新图片,先拿到原有的key,删除原有的图片,再上传新的图片
     * @param oldKey
     * @param newkey
     * @param newfile
     * @return
     */
    public static boolean updataFile(String oldKey,String newkey, File newfile) {
        BucketManager bucketManager = new BucketManager(auth);
        try {
            bucketManager.delete(bucketName,oldKey);
        } catch (QiniuException e) {
            logger.info("updataFile更新图片删除原有图片出错--------------------"+e.toString());
        }finally {
            try {
                uploadManager.put(newfile, newkey, getUpToken());
            }catch (Exception e){
                logger.info("updataFile更新图片上传新图片返回异常---------------------"+e.toString());
                return false;
            }finally {
                return true;
            }

        }
    }

    /**上传文件公用方法
     * @param files：上传的文件
     * @param oldKeysStr：历史上传图片key(以逗号{,}隔开的字符串key)
     * @return
     * @throws Exception
     */
    public static ExecuteResult<Map<String,Object>> uploadFiles(MultipartFile[] files,String oldKeysStr)throws Exception{
        ExecuteResult<Map<String,Object>> result=null;
        File upLoadFile=null;
        String fileKeys = "";
//        String fileUrls = "";
        String url = "";
        String fileKey="";
        if (files!= null&&files.length>0) {
            result=new ExecuteResult<Map<String,Object>>(ResultCode.SUCCESS);
            //判断是否有之前上传的图片，有则删除
            if (org.apache.commons.lang.StringUtils.isNotEmpty(oldKeysStr)) {
                String [] oldKeys=oldKeysStr.split(",");
                if(oldKeys!=null&&oldKeys.length>0){
                    for(String oldKey:oldKeys){
                        delete(oldKey);
                    }
                }
            }
            for (MultipartFile file : files) {
                if (file != null&&file.getBytes().length>0) {
                    if (file.getBytes().length > 1024 * 1024 * 200) {
                        // return 0;  //200M 文件过大
                        result.setResultCode(ResultCode.FAIL);
                        result.setMessage("上传的文件过大！");
                        return result;
                    }
                    fileKey= CreateUUIDUtil.createUUID();
                    upLoadFile = new File(file.getOriginalFilename());
                    FileOutputStream outputStream = new FileOutputStream(upLoadFile);
                    outputStream.write(file.getBytes());
                    outputStream.flush();
                    outputStream.close();
                    url = QiNiuXiuXiuUtil.uploadReturnURL(fileKey, upLoadFile);
                    if (StringUtils.isEmpty(url)) {
                        //  return 1;  //文件上传失败
                        result.setResultCode(ResultCode.ERROR);
                        result.setMessage("上传文件失败！");
                        return result;
                    }
                    fileKeys += fileKey + ",";
//                    fileUrls += url + ",";
                }
            }
            if(org.apache.commons.lang.StringUtils.isNotEmpty(fileKeys)){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("fileKeys", fileKeys.substring(0,fileKeys.length()-1));
//            map.put("fileUrls", fileUrls);
                result.setResult(map);
            }
        }
        return result;
    }
}