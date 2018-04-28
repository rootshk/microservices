package top.roothk.serviceoss.Contorller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import top.roothk.serviceoss.Utils.ALiYunOSSClientUtils;
import top.roothk.serviceoss.Utils.DataConversionUtils;
import top.roothk.serviceoss.Utils.Imagebase64Utils;
import top.roothk.serviceoss.Utils.JSONUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
@RestController
public class OSSImgController {

    @Autowired
    Imagebase64Utils imagebase64Utils;

    @Autowired
    DataConversionUtils dataConversionUtils;

    @Autowired
    ALiYunOSSClientUtils aLiYunOSSClientUtils;

    @Autowired
    JSONUtils jsonUtils;

    //地址
    @Value("${oss.endpoint}")
    private String endpoint;

    //命名空间
    @Value("${oss.bucketName}")
    private String bucketName;

    //协议头
    @Value("${oss.protocol}")
    private String protocol;

    /**
     * 上传文件,如文件已存在则返回错误信息
     *
     * @param jsonObject 图片的信息
     * @param eid    业务员的ID
     * @param sid    商品的ID
     * @param format 保存图片的格式
     * @return 成功信息
     * @throws IOException 上传错误信息
     */
    @PostMapping(value = "/img/{eid}/{sid}")
    public JSONObject upLoadImg(@RequestBody JSONObject jsonObject,
                                @PathVariable(value = "eid") String eid,
                                @PathVariable(value = "sid") String sid,
                                @RequestParam(value = "format", defaultValue = "png") String format) throws IOException {
        if (jsonObject == null) {
            return jsonUtils.getRoot(1, "error: 错误，因为传入的JSONObjcet为空", null);
        }
        String base64 = null;
        try {
            base64 = jsonObject.getString("base64");
        } catch (Exception e) {
        }
        //新建客户端
        OSSClient ossClient = aLiYunOSSClientUtils.getOSSClient();
        //拼接字key地址
        String uri = "img/" + eid + "/" + sid + "." + format;
        //判断是否存在这个图片
        boolean found = ossClient.doesObjectExist(bucketName, uri);
        //不存在就上传
        if (!found) {
            BufferedImage bufferedImage = Imagebase64Utils.base64StringToImage(base64);
            byte[] imgByte = dataConversionUtils.BufferedImageToByte(bufferedImage, format);
            //上传操作
            ossClient.putObject(bucketName, uri, new ByteArrayInputStream(imgByte));
            //关闭oss-client
            ossClient.shutdown();
            return jsonUtils.getRoot(0, "success", null);
        } else {
            ossClient.shutdown();
            return jsonUtils.getRoot(1, "error: 因为该文件已存在,如想覆盖旧文件请用updateImg方法", null);
        }
    }

    /**
     * 查看对应图片是否存在,存在则返回链接地址
     *
     * @param eid    业务员的ID
     * @param sid    商品的ID
     * @param format 查询图片的格式
     * @return 文件信息
     */
    @GetMapping(value = "/img/{eid}/{sid}")
    public JSONObject getImg(@PathVariable(value = "eid") String eid,
                             @PathVariable(value = "sid") String sid,
                             @RequestParam(value = "format", defaultValue = "png") String format) {
        //新建客户端
        OSSClient ossClient = aLiYunOSSClientUtils.getOSSClient();
        //解析KEY
        String uri = "img/" + eid + "/" + sid + "." + format;
        // Object是否存在
        boolean found = ossClient.doesObjectExist(bucketName, uri);
        // 关闭client
        ossClient.shutdown();
        //存在则组合对应地址
        if (found) {
            String url = protocol + "://" + bucketName + "." + endpoint + "/" + uri;
            return jsonUtils.getRoot(0, "success", url);
        }
        return jsonUtils.getRoot(0, "error: 该资源不存在", null);
    }

    /**
     * 覆盖更改图片,如果没有对应图片则新建上传
     *
     * @param base64 图片的base64
     * @param eid    业务员的ID
     * @param sid    商品的ID
     * @param format 查询图片的格式
     * @return 更改信息
     */
    @PutMapping(value = "/img/{eid}/{sid}")
    public JSONObject updateImg(@RequestBody String base64,
                                @PathVariable(value = "eid") String eid,
                                @PathVariable(value = "sid") String sid,
                                @RequestParam(value = "format", defaultValue = "png") String format) throws IOException {
        String uri = "img/" + eid + "/" + sid + "." + format;
        BufferedImage bufferedImage = Imagebase64Utils.base64StringToImage(base64);
        byte[] imgByte = dataConversionUtils.BufferedImageToByte(bufferedImage, format);
        //新建客户端
        OSSClient ossClient = aLiYunOSSClientUtils.getOSSClient();
        //上传操作
        ossClient.putObject(bucketName, uri, new ByteArrayInputStream(imgByte));
        //关闭oss-client
        ossClient.shutdown();
        return jsonUtils.getRoot(0, "success", null);
    }

    /**
     * 删除指定的图片文件，如果文件不存在则返回错误信息
     *
     * @param eid    业务员的ID
     * @param sid    商品的ID
     * @param format 查询图片的格式
     * @return 删除信息
     */
    @DeleteMapping(value = "/img/{eid}/{sid}")
    public JSONObject deleteImg(@PathVariable(value = "eid") String eid,
                                @PathVariable(value = "sid") String sid,
                                @RequestParam(value = "format", defaultValue = "png") String format) {
        //新建客户端
        OSSClient ossClient = aLiYunOSSClientUtils.getOSSClient();
        //解析KEY
        String uri = "img/" + eid + "/" + sid + "." + format;
        // Object是否存在
        boolean found = ossClient.doesObjectExist(bucketName, uri);
        //存在则组合对应地址
        if (found) {
            ossClient.deleteObject(bucketName, uri);
            ossClient.shutdown();
            return jsonUtils.getRoot(0, "success", null);
        } else {
            ossClient.shutdown();
            return jsonUtils.getRoot(1, "error: 错误，因为该文件不存在", uri);
        }

    }

    /**
     * 查询指定地址是否存在文件
     *
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/getImg/other")
    public JSONObject getImgOther(@RequestBody JSONObject jsonObject) {
        if (jsonObject == null) {
            return jsonUtils.getRoot(1, "error: 错误，因为传入的JSONObjcet为空", null);
        }

        //新建客户端
        OSSClient ossClient = aLiYunOSSClientUtils.getOSSClient();
        //获得网址
        String uri = jsonObject.getString("uri");
        uri = "img/other/" + uri;
        // Object是否存在
        boolean found = ossClient.doesObjectExist(bucketName, uri);
        // 关闭client
        ossClient.shutdown();
        //存在则组合对应地址
        if (found) {
            String url = protocol + "://" + bucketName + "." + endpoint + "/" + uri;
            return jsonUtils.getRoot(0, "success", url);
        }
        return jsonUtils.getRoot(1, "error: 该资源不存在", null);
    }

    /**
     * 上传图片文件,如文件已存在则返回错误信息
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/uploadImg/other")
    public JSONObject uploadImgOther(@RequestBody JSONObject jsonObject) {
        if (jsonObject == null) {
            return jsonUtils.getRoot(1, "error: 错误，因为传入的JSONObjcet为空", null);
        }
        String base64 = null;
        String uri = null;
        try {
            base64 = jsonObject.getString("base64");
            uri = jsonObject.getString("uri");
        } catch (Exception e) {
        }

        //新建客户端
        OSSClient ossClient = aLiYunOSSClientUtils.getOSSClient();
        //获得网址
        uri = "img/other/" + uri;
        //判断是否存在这个图片
        boolean found = ossClient.doesObjectExist(bucketName, uri);
        //不存在就上传
        if (!found) {
            try {
                BufferedImage bufferedImage = Imagebase64Utils.base64StringToImage(base64);
                byte[] imgByte = dataConversionUtils.BufferedImageToByte(bufferedImage, "png");
                //上传操作
                ossClient.putObject(bucketName, uri, new ByteArrayInputStream(imgByte));
                //关闭oss-client
                ossClient.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
                return jsonUtils.getRoot(1, "error: 转换错误/上传错误", null);
            }
            uri = protocol + "://" + bucketName + "." + endpoint + "/" + uri;
            return jsonUtils.getRoot(0, "success", uri);
        } else {
            ossClient.shutdown();
            return jsonUtils.getRoot(1, "error: 因为该文件已存在,如想覆盖旧文件请用updateImg方法", null);
        }
    }

    /**
     * 上传图片文件，如存在则覆盖上传
     *
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/updateImg/other")
    public JSONObject updateImgOther(@RequestBody JSONObject jsonObject) {
        if (jsonObject == null) {
            return jsonUtils.getRoot(1, "error: 错误，因为传入的JSONObjcet为空", null);
        }
        String base64 = null;
        String uri = null;
        try {
            base64 = jsonObject.getString("base64");
            uri = jsonObject.getString("uri");
        } catch (Exception e) {
        }
        //新建客户端
        OSSClient ossClient = aLiYunOSSClientUtils.getOSSClient();
        //获得网址
        uri = "img/other/" + uri;
        try {
            BufferedImage bufferedImage = Imagebase64Utils.base64StringToImage(base64);
            byte[] imgByte = dataConversionUtils.BufferedImageToByte(bufferedImage, "png");
            //上传操作
            ossClient.putObject(bucketName, uri, new ByteArrayInputStream(imgByte));
            //关闭oss-client
            ossClient.shutdown();
            uri = protocol + "://" + bucketName + "." + endpoint + "/" + uri;
            return jsonUtils.getRoot(0, "success", uri);
        } catch (IOException e) {
            e.printStackTrace();
            uri = protocol + "://" + bucketName + "." + endpoint + "/" + uri;
            return jsonUtils.getRoot(1, "error: 转换错误/上传错误", uri);
        }
    }

    /**
     * 删除图片文件，如存在则覆盖上传
     *
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/deleteImg/other")
    public JSONObject deleteImgOther(@RequestBody JSONObject jsonObject) {
        if (jsonObject == null) {
            return jsonUtils.getRoot(1, "error: 错误，因为传入的JSONObjcet为空", null);
        }

        String uri = null;
        try {
            uri = jsonObject.getString("uri");
            uri = "img/other/" + uri;
        } catch (Exception e) {
        }
        //新建客户端
        OSSClient ossClient = aLiYunOSSClientUtils.getOSSClient();
        // Object是否存在
        boolean found = ossClient.doesObjectExist(bucketName, uri);
        //存在则组合对应地址
        if (found) {
            ossClient.deleteObject(bucketName, uri);
            ossClient.shutdown();
            uri = protocol + "://" + bucketName + "." + endpoint + "/" + uri;
            return jsonUtils.getRoot(0, "success", uri);
        } else {
            ossClient.shutdown();
            uri = protocol + "://" + bucketName + "." + endpoint + "/" + uri;
            return jsonUtils.getRoot(1, "error: 错误，因为该文件不存在", uri);
        }
    }
}