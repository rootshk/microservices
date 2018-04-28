package top.roothk.serviceqrcode.Utils;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 数据转化工具类
 */
@Component
public class DataConversionUtils {

    /**
     * 将为BufferedImage转byte[]
     * @param img    转换的图片
     * @param format 图片格式，如PNG
     * @return
     * @throws IOException
     */
    public byte[] BufferedImageToByte(BufferedImage img, String format) throws IOException {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(img, format, out);
            byte[] imageInByte = out.toByteArray();
            out.flush();
            out.close();
            return imageInByte;
        } catch (IOException e) {
            e.getMessage();
        } finally {

        }
        return null;
    }

    /**
     * 将为byte[]转BufferedImage
     * @param data
     * @return
     */
    public BufferedImage ByteToBufferedImage(byte[] data) {
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
            return img;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
