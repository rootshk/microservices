package top.roothk.serviceqrcode.Domain;

import com.alibaba.fastjson.JSONObject;

import java.util.Objects;

/**
 * 用于存储图像信息
 */
public class ImgBean {
    private String imgUri;      //图像资源地址
    private Integer imgWidth;   //图像输出宽度
    private Integer imgHeight;  //图像输出高度
    private Integer imgX;       //当前图像距离上一个图像的左上角X轴距离
    private Integer imgY;       //当前图像距离上一个图像的左上角Y轴距离
    private String imgFormat;   //图像格式

    public ImgBean(String imgUri, Integer imgWidth, Integer imgHeight, Integer imgX, Integer imgY, String imgFormat){
        this.imgUri = imgUri;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.imgX = imgX;
        this.imgY = imgY;
        this.imgFormat = imgFormat;
    }

    public ImgBean(JSONObject jsonObject){
        this.imgUri = jsonObject.getString("imgUri");
        this.imgWidth = jsonObject.getInteger("imgWidth");
        this.imgHeight = jsonObject.getInteger("imgHeight");
        this.imgX = jsonObject.getInteger("imgX");
        this.imgY = jsonObject.getInteger("imgY");
        this.imgFormat = jsonObject.getString("imgFormat");
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public Integer getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(Integer imgWidth) {
        this.imgWidth = imgWidth;
    }

    public Integer getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(Integer imgHeight) {
        this.imgHeight = imgHeight;
    }

    public Integer getImgX() {
        return imgX;
    }

    public void setImgX(Integer imgX) {
        this.imgX = imgX;
    }

    public Integer getImgY() {
        return imgY;
    }

    public void setImgY(Integer imgY) {
        this.imgY = imgY;
    }

    public String getImgFormat() {
        return imgFormat;
    }

    public void setImgFormat(String imgFormat) {
        this.imgFormat = imgFormat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImgBean imgBean = (ImgBean) o;
        return Objects.equals(imgUri, imgBean.imgUri) &&
                Objects.equals(imgWidth, imgBean.imgWidth) &&
                Objects.equals(imgHeight, imgBean.imgHeight) &&
                Objects.equals(imgX, imgBean.imgX) &&
                Objects.equals(imgY, imgBean.imgY) &&
                Objects.equals(imgFormat, imgBean.imgFormat);
    }

    @Override
    public int hashCode() {

        return Objects.hash(imgUri, imgWidth, imgHeight, imgX, imgY, imgFormat);
    }

    @Override
    public String toString() {
        return "ImgBean{" +
                "imgUri='" + imgUri + '\'' +
                ", imgWidth=" + imgWidth +
                ", imgHeight=" + imgHeight +
                ", imgX=" + imgX +
                ", imgY=" + imgY +
                ", imgFormat='" + imgFormat + '\'' +
                '}';
    }

    public Boolean imgUrlIsEmpty() {return imgUri == null || imgUri.isEmpty();}
    public Boolean imgWidthIsEmpty() {return imgWidth == null;}
    public Boolean imgHeightIsEmpty() {return imgHeight == null;}
    public Boolean imgXIsEmpty() {return imgX == null;}
    public Boolean imgYIsEmpty() {return imgY == null;}
    public Boolean imgFormatIsEmpty() {return imgFormat == null || imgFormat.isEmpty();}
}
