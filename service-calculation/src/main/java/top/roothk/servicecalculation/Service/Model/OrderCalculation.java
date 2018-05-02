package top.roothk.servicecalculation.Service.Model;

import java.util.Date;

public class OrderCalculation {
    private Long id;

    private String commodityName;

    private String commodityModel;

    private String commodityNum;

    private Double commodityPrice;

    private String couponTypeOne;

    private String couponTypeTwo;

    private String couponTypeThr;

    private String couponNum;

    private String couponCalculation;

    private Date calculationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName == null ? null : commodityName.trim();
    }

    public String getCommodityModel() {
        return commodityModel;
    }

    public void setCommodityModel(String commodityModel) {
        this.commodityModel = commodityModel == null ? null : commodityModel.trim();
    }

    public String getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(String commodityNum) {
        this.commodityNum = commodityNum == null ? null : commodityNum.trim();
    }

    public Double getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(Double commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getCouponTypeOne() {
        return couponTypeOne;
    }

    public void setCouponTypeOne(String couponTypeOne) {
        this.couponTypeOne = couponTypeOne == null ? null : couponTypeOne.trim();
    }

    public String getCouponTypeTwo() {
        return couponTypeTwo;
    }

    public void setCouponTypeTwo(String couponTypeTwo) {
        this.couponTypeTwo = couponTypeTwo == null ? null : couponTypeTwo.trim();
    }

    public String getCouponTypeThr() {
        return couponTypeThr;
    }

    public void setCouponTypeThr(String couponTypeThr) {
        this.couponTypeThr = couponTypeThr == null ? null : couponTypeThr.trim();
    }

    public String getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(String couponNum) {
        this.couponNum = couponNum == null ? null : couponNum.trim();
    }

    public String getCouponCalculation() {
        return couponCalculation;
    }

    public void setCouponCalculation(String couponCalculation) {
        this.couponCalculation = couponCalculation == null ? null : couponCalculation.trim();
    }

    public Date getCalculationTime() {
        return calculationTime;
    }

    public void setCalculationTime(Date calculationTime) {
        this.calculationTime = calculationTime;
    }
}