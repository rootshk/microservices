package top.roothk.serviceqrcode.Service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import top.roothk.serviceqrcode.Domain.ImgBean;
import top.roothk.serviceqrcode.Domain.QRCodeParameterBean;

@Service
public class QRCodeService {



    /**
     * 进行二维码数据的空处理
     * @param jsonObject 传入的二维码数据
     * @return
     */
    public QRCodeParameterBean qrCodeEmptyDataProcessing(JSONObject jsonObject){
        QRCodeParameterBean qrCodeParameterBean = new QRCodeParameterBean(jsonObject);

        if(qrCodeParameterBean.qrCodeUrlIsEmpty()){ qrCodeParameterBean.setQrCodeUrl("https://m.fulucang.com"); }
        if(qrCodeParameterBean.qrCodeWidthIsEmpty()){ qrCodeParameterBean.setQrCodeWidth(300); }
        if(qrCodeParameterBean.qrCodeHeightIsEmpty()){ qrCodeParameterBean.setQrCodeHeight(300); }
        if(qrCodeParameterBean.qrCodeFormatIsEmpty()){ qrCodeParameterBean.setQrCodeFormat("PNG"); }
        if(qrCodeParameterBean.qrCodeZIsEmpty()){ qrCodeParameterBean.setQrCodeZ(0); }

        return qrCodeParameterBean;
    }

    public ImgBean imgEmptyDataProcessing(JSONObject jsonObject){
        ImgBean imgBean = new ImgBean(jsonObject);

        if(imgBean.imgUrlIsEmpty()){ imgBean.setImgUri("http://oss.roothk.top/a.png"); }
        if(imgBean.imgWidthIsEmpty()){ imgBean.setImgWidth(300); }
        if(imgBean.imgHeightIsEmpty()){ imgBean.setImgHeight(300); }
        if(imgBean.imgXIsEmpty()){ imgBean.setImgX(0); }
        if(imgBean.imgYIsEmpty()){ imgBean.setImgY(0); }
        if(imgBean.imgFormatIsEmpty()){ imgBean.setImgFormat("PNG"); }

        return imgBean;
    }
}
