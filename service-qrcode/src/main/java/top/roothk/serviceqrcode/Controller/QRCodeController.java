package top.roothk.serviceqrcode.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.roothk.serviceqrcode.Domain.ImgBean;
import top.roothk.serviceqrcode.Service.ImageService;
import top.roothk.serviceqrcode.Service.LogoService;
import top.roothk.serviceqrcode.Service.QRCodeService;
import top.roothk.serviceqrcode.Utils.DataConversionUtils;
import top.roothk.serviceqrcode.Utils.Imagebase64Utils;
import top.roothk.serviceqrcode.Utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class QRCodeController {

    @Autowired
    DataConversionUtils dataConversionUtils;

    @Autowired
    JSONUtils jsonUtils;

    @Autowired
    ImageService imageService;

    @Autowired
    QRCodeService qrCodeService;

    @Autowired
    LogoService logoService;

    /**
     * 网页端输出二维码 GET方法
     *
     * @param response
     * @return
     */
    @GetMapping(value = "web/qrCode/{uri}")
    public String getQRCodeGet(@PathVariable("uri") String uri,
                               @RequestParam(value = "f", defaultValue = "PNG") String f,
                               @RequestParam(value = "w", defaultValue = "300") Integer w,
                               @RequestParam(value = "h", defaultValue = "300") Integer h,
                               HttpServletResponse response) {
        log.info("------- INFO 访问 /web/QRCode/{uri} 有参构造");
        try {
            ImgBean imgBean = new ImgBean(uri,w,h,0,0,f);
            BufferedImage img = imageService.QRCode(imgBean);
            //图片转化为ByteArr
            byte[] imgByte = dataConversionUtils.BufferedImageToByte(img, "PNG");
            //设置返回的数据类型
            response.setContentType("image/" + "PNG");
            //新建流进行发送
            OutputStream os = response.getOutputStream();
            os.write(imgByte);
            os.flush();
            os.close();
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 网页端输出二维码 GET方法
     *
     * @param response 输出图片
     * @return
     */
    @GetMapping(value = "web/qrCode")
    public String getQRCode(HttpServletResponse response) {
        log.info("------- INFO 访问 /web/QRCode 无参构造");
        try {
            JSONArray imgData = new JSONArray();//初始图像数据
            imgData.add(new JSONObject());
            BufferedImage img = imageService.getQRCodeImageByJSONObj(imgData, 0);
            //图片转化为ByteArr
            byte[] imgByte = dataConversionUtils.BufferedImageToByte(img, "PNG");
            //设置返回的数据类型
            response.setContentType("image/" + "PNG");
            //新建流进行发送
            OutputStream os = response.getOutputStream();
            os.write(imgByte);
            os.flush();
            os.close();
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 网页端输出二维码 POST方法
     *
     * @param jsonObject 二维码各个参数
     * @return
     */
    @PostMapping(value = "web/qrCode")
    public String getQRCode(@RequestBody JSONObject jsonObject, HttpServletResponse response) {
        log.info("------- INFO 访问 /web/qrCode 有参构造");

        JSONArray imgData;//初始图像数据
        BufferedImage img = null; //初始画布
        Integer z = null;//初始二维码啊Z轴

        //1.判断有没有该key
        if (jsonObject.containsKey("data")) {
            imgData = jsonObject.getJSONArray("data");
        } else {
            log.error("------- ERROR 未接收到图像数据");
            return "------- ERROR 未接收到图像数据";
        }

        //2.判断有没有该key
        if (jsonObject.containsKey("qrCodeZ")) {
            //获取二维码所在Z轴
            z = jsonObject.getInteger("qrCodeZ");
            //TODO 这里还有一个错误处理,就是"qrCodeZ"获得的数不是正数的判断.暂时不知道要怎么写
            //判断Z轴是否大于总图片数
            if (z > imgData.size()) {
                log.error("------- ERROR 二维码Z值大于图片层数");
                return "";
            }
        }

        //3.判断图像数据是否为空
        if (imgData != null) {
            img = imageService.getQRCodeImageByJSONObj(imgData, z);
        }
        //4.查看是否生成了图像
        if (img != null) {
            try {
                //图片转化为ByteArr
                byte[] imgByte = dataConversionUtils.BufferedImageToByte(img, "PNG");
                //设置返回的数据类型
                response.setContentType("image/" + "PNG");
                //新建流进行发送
                OutputStream os = response.getOutputStream();
                os.write(imgByte);
                os.flush();
                os.close();
                return "success";
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return "------- ERROR 输出图片出错,输出图像为 null";
        }
        return "";
    }

    /**
     * 服务间调用
     * @param uri 生成二维码的地址
     * @param f 二维码图片格式
     * @param w 二维码宽度
     * @param h 二维码高度
     * @return 返回输入地址生产的二维码
     */
    @GetMapping(value = "service/qrCode/{uri}")
    public String getQRCodeGet(@PathVariable("uri") String uri,
                               @RequestParam(value = "f", defaultValue = "PNG") String f,
                               @RequestParam(value = "w", defaultValue = "300") Integer w,
                               @RequestParam(value = "h", defaultValue = "300") Integer h) {
            log.info("------- INFO 访问 /web/QRCode/{uri} 有参构造");

            ImgBean imgBean = new ImgBean(uri,w,h,0,0,f);
            BufferedImage img = imageService.QRCode(imgBean);
            String base64 = Imagebase64Utils.getImageBinary(img);
            return base64;
    }

    /**
     * 服务间调用方法
     *
     * @return 返回默认的二维码
     */
    @GetMapping(value = "service/qrCode")
    public String getQRCodeByte() {
        log.info("------- INFO 访问 /service/qrCode 无参构造");

        JSONArray imgData = new JSONArray();//初始图像数据
        imgData.add(new JSONObject());
        BufferedImage img = imageService.getQRCodeImageByJSONObj(imgData, 0);
        //转为Base64
        String base64 = Imagebase64Utils.getImageBinary(img);
        return base64;
    }

    /**
     * 服务间调用方法,传入JSONObjcet可自定义
     *
     * @param
     * @return
     */
    @PostMapping(value = "service/qrCode")
    public String getQRCodeByte(@RequestBody JSONObject jsonObject, HttpServletResponse response) {
        log.info("------- INFO 访问 /service/qrCode 有参构造");

        JSONArray imgData;//初始图像数据
        BufferedImage img = null; //初始画布
        Integer z = null;//初始二维码啊Z轴

        //1.判断有没有该key
        if (jsonObject.containsKey("data")) {
            imgData = jsonObject.getJSONArray("data");
        } else {
            log.error("------- ERROR 未接收到图像数据");
            return "------- ERROR 未接收到图像数据";
        }

        //2.判断有没有该key
        if (jsonObject.containsKey("qrCodeZ")) {
            //获取二维码所在Z轴
            z = jsonObject.getInteger("qrCodeZ");
            //TODO 这里还有一个错误处理,就是"qrCodeZ"获得的数不是正数的判断.暂时不知道要怎么写
            //判断Z轴是否大于总图片数
            if (z > imgData.size()) {
                log.error("------- ERROR 二维码Z值大于图片层数");
                return "";
            }
        }

        //3.判断图像数据是否为空
        if (imgData != null) {
            img = imageService.getQRCodeImageByJSONObj(imgData, z);
        }

        //4.查看是否生成了图像
        if (img != null) {
            //转为Base64
            String base64 = Imagebase64Utils.getImageBinary(img);
            return base64;
        } else {
            return "------- ERROR 输出图片出错,输出图像为 null";
        }


//        try {
//            //图片转化为ByteArr
//            byte[] imgByte = dataConversionUtils.BufferedImageToByte(img, "PNG");
//            //设置返回的数据类型
//            response.setContentType("image/" + "PNG");
//            //新建流进行发送
//            OutputStream os = response.getOutputStream();
//            os.write(imgByte);
//            os.flush();
//            os.close();
//            return "success";
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
    }
}