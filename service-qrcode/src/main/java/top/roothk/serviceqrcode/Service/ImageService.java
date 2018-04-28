package top.roothk.serviceqrcode.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.roothk.serviceqrcode.Domain.ImgBean;
import top.roothk.serviceqrcode.Utils.MatrixToImageWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ImageService {

    @Autowired
    LogoService logoService;

    @Autowired
    QRCodeService qrCodeService;

    /**
     * 通过配置信息生成二维码
     *
     * @param imgBean
     * @return
     */
    public BufferedImage QRCode(ImgBean imgBean) {
        try {
            //1.生成了二维码
            QRCodeWriter writer = new QRCodeWriter();
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            BitMatrix m = writer.encode(imgBean.getImgUri(), BarcodeFormat.QR_CODE, imgBean.getImgWidth(), imgBean.getImgHeight(), hints);
            //转化为BufferedImage
            BufferedImage QRCode = MatrixToImageWriter.toBufferedImage(m);
            return QRCode;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    /**
     * 通过地址获取图片资源
     *
     * @param url
     * @return
     */
    public BufferedImage getImageByUrl(String url) {
        try {
            BufferedImage image = null;

            Properties props = System.getProperties(); //获得系统属性集
            String osName = props.getProperty("os.name"); //操作系统名称
            String regexOSNameLinux = ".*Linux.*";//操作系统名称
            String regexOSNameWindwos = ".*Windows.*";//操作系统名称
            boolean isMatchL = Pattern.matches(regexOSNameLinux, osName);
            boolean isMatchW = Pattern.matches(regexOSNameWindwos, osName);

            String regexUrl = "[a-zA-z]+://[^\\s]*";//网络文件正则
            String regexLinLocal = "/[^\\s]*/.*";//本地文件(linux的绝对路径)正则

            Matcher m = Pattern.compile(regexUrl).matcher(url);
            Matcher l = Pattern.compile(regexLinLocal).matcher(url);
            log.info("------------------------- >> 当前加载地址为 : '" + url + "' 的文件");
            if (m.matches()) {
                //存在协议头
                URL imgUrlU = new URL(url);
                image = ImageIO.read(imgUrlU);
            } else {//不存在协议头,当本地储存路径(Linux)
                log.info("------------------------- >> 提示 : 当前系统为 : '" + osName + "', 图片地址为 : '" + url + "' ,如无图片输出请注意路径是否正确");
                File imgUrlF = new File(url);
                image = ImageIO.read(imgUrlF);
            }//还有流的方式,这个还不考虑
            return image;
        } catch (IOException e) {
            if (url.isEmpty()) {
                log.info("------------------------- >> 错误!前景图片地址为空");
            }
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过JSONObjcet获得图片
     * @param imgData 所有图片参数
     * @param z 二维码层数
     * @return
     */
    public BufferedImage getQRCodeImageByJSONObj(JSONArray imgData, Integer z) {
        BufferedImage img = null; //初始画布
        try {
            for (Integer i = 0; i < imgData.size(); i++) {
                //获得某一个图片的详细参数信息
                JSONObject j = imgData.getJSONObject(i);
                //传入参数的空处理
                ImgBean imgBean = qrCodeService.imgEmptyDataProcessing(j);
                //使用第一张图片的W/H大小为图像初始值
                //TODO 这里需要处理的是以后有需要拼接图像的时候,画布不能是第一张图的大小,而是输出之后的总大小
                if (i == 0) {
                    Integer w = imgBean.getImgWidth();
                    Integer h = imgBean.getImgHeight();
                    img = new BufferedImage(w, h, 2);
                }

                if(z == null){//普通图片拼接时,没有z值
                    //根据图片配置信息合成两张图片
                    img = logoService.ImgEdit(img, imgBean);
                }else {
                    if (z.equals(i)) {//检测到当前图层为二维码
                        //生成二维码图片
                        BufferedImage qrcodeimg = QRCode(imgBean);
                        //根据二维码配置信息合成两张图片
                        img = logoService.ImgAndQRCodeEdit(img, qrcodeimg, imgBean);
                    } else {
                        //根据图片配置信息合成两张图片
                        img = logoService.ImgEdit(img, imgBean);
                    }
                }
            }
        }catch (Exception e){

        }
        return img;
    }
}
