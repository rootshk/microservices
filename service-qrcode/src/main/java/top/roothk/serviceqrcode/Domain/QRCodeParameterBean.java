package top.roothk.serviceqrcode.Domain;

import com.alibaba.fastjson.JSONObject;

import java.util.Objects;

public class QRCodeParameterBean {
    private String qrCodeUrl;   //二维码地址
    private Integer qrCodeWidth;    //二维码宽度
    private Integer qrCodeHeight;   //二维码高度
    private Integer qrCodeX;   //二维码高度
    private Integer qrCodeY;   //二维码高度
    private String qrCodeFormat;    //二维码格式
    private Integer qrCodeZ;          //二维码z轴

    public QRCodeParameterBean(JSONObject jsonObject){
        this.qrCodeUrl = jsonObject.getString("QRCodeUrl");
        this.qrCodeWidth = jsonObject.getInteger("QRCodeWidth");
        this.qrCodeHeight = jsonObject.getInteger("QRCodeHeight");
        this.qrCodeFormat = jsonObject.getString("QRCodeFormat");
        this.qrCodeFormat = jsonObject.getString("QRCodeZ");
        this.qrCodeX = jsonObject.getInteger("QRCodeX");
        this.qrCodeY = jsonObject.getInteger("QRCodeY");
    }

    public Integer getQrCodeX() {
        return qrCodeX;
    }

    public void setQrCodeX(Integer qrCodeX) {
        this.qrCodeX = qrCodeX;
    }

    public Integer getQrCodeY() {
        return qrCodeY;
    }

    public void setQrCodeY(Integer qrCodeY) {
        this.qrCodeY = qrCodeY;
    }

    public Integer getQrCodeZ() {
        return qrCodeZ;
    }

    public void setQrCodeZ(Integer qrCodeZ) {
        this.qrCodeZ = qrCodeZ;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public Integer getQrCodeWidth() {
        return qrCodeWidth;
    }

    public void setQrCodeWidth(Integer qrCodeWidth) {
        this.qrCodeWidth = qrCodeWidth;
    }

    public Integer getQrCodeHeight() {
        return qrCodeHeight;
    }

    public void setQrCodeHeight(Integer qrCodeHeight) {
        this.qrCodeHeight = qrCodeHeight;
    }

    public String getQrCodeFormat() {
        return qrCodeFormat;
    }

    public void setQrCodeFormat(String qrCodeFormat) {
        this.qrCodeFormat = qrCodeFormat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QRCodeParameterBean that = (QRCodeParameterBean) o;
        return Objects.equals(qrCodeUrl, that.qrCodeUrl) &&
                Objects.equals(qrCodeWidth, that.qrCodeWidth) &&
                Objects.equals(qrCodeHeight, that.qrCodeHeight) &&
                Objects.equals(qrCodeFormat, that.qrCodeFormat);
    }

    @Override
    public int hashCode() {

        return Objects.hash(qrCodeUrl, qrCodeWidth, qrCodeHeight, qrCodeFormat);
    }

    @Override
    public String toString() {
        return "QRCodeParameterBean{" +
                "qrCodeUrl='" + qrCodeUrl + '\'' +
                ", qrCodeWidth=" + qrCodeWidth +
                ", qrCodeHeight=" + qrCodeHeight +
                ", qrCodeFormat='" + qrCodeFormat + '\'' +
                '}';
    }

    public Boolean qrCodeUrlIsEmpty() {return qrCodeUrl == null || qrCodeUrl.isEmpty();}
    public Boolean qrCodeWidthIsEmpty() {return qrCodeWidth == null;}
    public Boolean qrCodeHeightIsEmpty() {return qrCodeHeight == null;}
    public Boolean qrCodeXIsEmpty() {return qrCodeX == null;}
    public Boolean qrCodeYIsEmpty() {return qrCodeY == null;}
    public Boolean qrCodeFormatIsEmpty() {return qrCodeFormat == null || qrCodeFormat.isEmpty();}
    public Boolean qrCodeZIsEmpty() {return qrCodeZ == null;}
}
