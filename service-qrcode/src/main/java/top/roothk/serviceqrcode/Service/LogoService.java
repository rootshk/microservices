package top.roothk.serviceqrcode.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.roothk.serviceqrcode.Domain.ImgBean;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 二维码 添加 logo图标 处理的方法,
 */
@Component
public class LogoService {

    @Autowired
    ImageService imageService;

    /**
     * 图片和ImgBean拼接
     * @param matrixImage 源图片
     * @return 返回拼接图片
     * @throws IOException
     */
    public BufferedImage ImgEdit(BufferedImage matrixImage, ImgBean imgBean) throws IOException{
        Graphics2D g2 = matrixImage.createGraphics();//读取二维码图片，并构建绘图对象
        BufferedImage logo = imageService.getImageByUrl(imgBean.getImgUri());//读取二维码logo图片
        //开始绘制图片
        g2.drawImage(logo,
                imgBean.getImgX(),
                imgBean.getImgY(),
                imgBean.getImgWidth(),
                imgBean.getImgHeight(), null);//绘制
        g2.dispose();
        matrixImage.flush() ;
        return matrixImage ;
    }

    /**
     * 二维码和图片拼接
     * @param
     * @return 返回拼接图片
     * @throws IOException
     */
    public BufferedImage ImgAndQRCodeEdit(BufferedImage img1, BufferedImage qrcodeimg,ImgBean imgBean ) throws IOException{
        Graphics2D g2 = img1.createGraphics();//读取二维码图片，并构建绘图对象
        //开始绘制图片
        g2.drawImage(qrcodeimg,
                imgBean.getImgX(),
                imgBean.getImgY(),
                imgBean.getImgWidth(),
                imgBean.getImgHeight(), null);//绘制
        g2.dispose();
        img1.flush() ;
        return img1 ;
    }

}
