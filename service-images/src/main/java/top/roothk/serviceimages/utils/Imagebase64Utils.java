package top.roothk.serviceimages.utils;

import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

@Component
public class Imagebase64Utils {
    /**
     * imgToBase64
     * @param bi
     * @return String
     */
    public String getImageBinary(BufferedImage bi) {
        try {
            BASE64Encoder encoder = new BASE64Encoder();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", baos);
            byte[] bytes = baos.toByteArray();
            return encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Base64toImg
     * @param string
     * @return BufferedImage
     */
    public BufferedImage base64StringToImage(String string) {
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(string);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            return read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}