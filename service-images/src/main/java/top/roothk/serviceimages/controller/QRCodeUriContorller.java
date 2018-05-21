package top.roothk.serviceimages.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import top.roothk.serviceimages.service.ServiceClient.OSSServiceFeign;
import top.roothk.serviceimages.service.ServiceClient.QRCodeServiceFeign;
import top.roothk.serviceimages.utils.DataConversionUtils;
import top.roothk.serviceimages.utils.Imagebase64Utils;
import top.roothk.serviceimages.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 用于获得二维码图片
 */
@Slf4j
@RestController
@RequestMapping(value = "/qrCode")
public class QRCodeUriContorller {

    @Autowired
    QRCodeServiceFeign qrCodeServiceFeign;

    @Autowired
    OSSServiceFeign ossServiceFeign;

    @Autowired
    Imagebase64Utils imagebase64Utils;

    @Autowired
    JSONUtils jsonUtils;

    @Autowired
    DataConversionUtils dataConversionUtils;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Value("${roothk.config.redisDemo}")
    Boolean redisDemo;

    /**
     * 获得二维码图片的OSS地址 通过传入地址
     * @return
     */
    @GetMapping(value = "/uri/{uri}")
    public JSONObject getQRCodeImgUri(@PathVariable("uri") String uri) {
        String ord = uri;

        Boolean is = false;
        //开启redis缓存
        if(redisDemo){
            //判断对应的UrKEY是否存在
            is = stringRedisTemplate.opsForHash().hasKey("otherUri",ord);
        }
        if(is){
            uri = (String) stringRedisTemplate.opsForHash().get("otherUri",ord);
        } else {
            //查询阿里云有没有这个文件
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uri",uri+".png");

            JSONObject oss = ossServiceFeign.getImgOther(jsonObject);

            if(oss.getInteger("error_code") == 0){//阿里云存在这个地址
                //拿到阿里云的地址
                uri = oss.getString("data");
            } else {//阿里云不存在这个地址
                //生成一张二维码
                String base64 = qrCodeServiceFeign.getQRCodeGet(uri,"PNG",300,300);
                jsonObject.put("base64",base64);
                //保存到OSS
                oss = ossServiceFeign.uploadImgOther(jsonObject);
                if(oss.getInteger("error_code") == 0){//成功
                    //获得oss地址
                    uri = oss.getString("data");
                    if(redisDemo){stringRedisTemplate.opsForHash().put("other",ord,uri);}
                } else {
                    return jsonUtils.getRoot(1,"错误，保存到OSS失败",jsonObject);
                }
            }
        }
        return jsonUtils.getRoot(0,"success",uri);
    }

    /**
     * 获得二维码图片的OSS地址 通过传入JS
     *
     * 模型
     *{
     *     "qrCodeZ": "0",     //设置的二维码所在层数
     *     "data": [
     *     {
     *          "imgUri": "http://www.roothk.top",   //图像地址，如果为二维码，则输入二维码地址
     *          "imgWidth": "300",                   //图像宽度
     *          "imgHeight": "300",                  //图像高度
     *          "imgX": "0",                         //图像X轴位置，建议第一层为0
     *          "imgY": "0",                         //图像y轴位置，建议第一层为0
     *          "imgFormat": "png"                  //图像的保存PNG
     *     },
     *     {
     *          "imgUri": "http://www.roothk.top/a.png",
     *          "imgWidth": "200",
     *          "imgHeight": "200",
     *          "imgX": "50",
     *          "imgY": "50",
     *          "imgFormat": "png"
     *     }
     *     ]
     *}
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/uri")
    public JSONObject getQRcodeImgUri(@RequestBody JSONObject jsonObject){
        String md5 = DigestUtils.md5DigestAsHex(jsonObject.toString().getBytes());

        log.info("---MD5--- >> " + md5 + " << ---MD5---");

        String uri = "";
        Boolean is = false;
        //开启redis缓存
        if(redisDemo){
             is = stringRedisTemplate.opsForHash().hasKey("imgJSON",md5);
        }
        //是否已生成过该二维码
        if(is){//存在
            log.info("---------- >> Redis存在该图片 << ----------");
            //获得二维码地址
            uri = (String) stringRedisTemplate.opsForHash().get("imgJSON",md5);
        } else {
            log.info("---------- >> Reids不存在该图片 << ----------");
            //生成二维码
            //查询阿里云有没有这个文件
            JSONObject ossIn = new JSONObject();
            //------->> 1.插入地址
            ossIn.put("uri","md5Img/" + md5 + ".png");
            //查询是否有该图片
            JSONObject ossOut = ossServiceFeign.getImgOther(ossIn);
            if(ossOut.getInteger("error_code") == 0){//阿里云存在这个地址
                //拿到阿里云的地址
                uri = ossOut.getString("data");
            } else {//阿里云不存在这个地址
                //生成一张二维码
                String base64 = qrCodeServiceFeign.getQRCode1(jsonObject);
                //-------->> 2.插入图像数据
                ossIn.put("base64",base64);
                //保存到OSS
                ossOut = ossServiceFeign.uploadImgOther(ossIn);
                if(ossOut.getInteger("error_code") == 0){//成功
                    //获得oss地址
                    uri = ossOut.getString("data");
                    if(redisDemo){ stringRedisTemplate.opsForHash().put("imgJSON",md5,uri);}
                } else {
                    return jsonUtils.getRoot(1,"错误，保存到OSS失败",jsonObject);
                }
            }
        }
        return jsonUtils.getRoot(0,"success",uri);
    }

    /**
     * 直接获得二维码图片 没有了中间商赚差价
     * @return
     */
    @GetMapping(value = "/img")
    public String getQRCodeImg(HttpServletResponse response) throws IOException {
        String base64 = qrCodeServiceFeign.getQRCode();
        BufferedImage image = imagebase64Utils.base64StringToImage(base64);
        //图片转化为ByteArr
        byte[] imgByte = dataConversionUtils.BufferedImageToByte(image, "PNG");
        //设置返回的数据类型
        response.setContentType("image/" + "PNG");
        //新建流进行发送
        OutputStream os = response.getOutputStream();
        os.write(imgByte);
        os.flush();
        os.close();
        return "success";
    }

    /**
     * 直接获得二维码图片 没有了中间商赚差价
     * @param response 返回图片
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/img")
    public String getQRCodeImg(@RequestBody JSONObject jsonObject, HttpServletResponse response) throws IOException {
        String base64 = qrCodeServiceFeign.getQRCode1(jsonObject);
        BufferedImage image = imagebase64Utils.base64StringToImage(base64);
        //图片转化为ByteArr
        byte[] imgByte = dataConversionUtils.BufferedImageToByte(image, "PNG");
        //设置返回的数据类型
        response.setContentType("image/" + "PNG");
        //新建流进行发送
        OutputStream os = response.getOutputStream();
        os.write(imgByte);
        os.flush();
        os.close();
        return "success";
    }
}
