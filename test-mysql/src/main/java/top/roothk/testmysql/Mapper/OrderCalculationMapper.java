package top.roothk.testmysql.Mapper;

import org.apache.ibatis.annotations.*;
import top.roothk.servicecalculation.Service.Dao.OrderCalculation;

import java.util.List;

public interface OrderCalculationMapper {
    @Select("SELECT * FROM order_calculation")
    @Results({
//            @Result(property = "userSex", column = "user_sex", javaType = UserSexEnum.class),

            @Result(property = "id", column = "ID"),
            @Result(property = "commodityName", column = "COMMODITY_NAME"),
            @Result(property = "commodityModel", column = "COMMODITY_MODEL"),
            @Result(property = "commodityNum", column = "COMMODITY_NUM"),
            @Result(property = "commodityPrice", column = "COMMODITY_PRICE"),
            @Result(property = "couponTypeOne", column = "COUPON_TYPE_ONE"),
            @Result(property = "couponTypeTwo", column = "COUPON_TYPE_TWO"),
            @Result(property = "couponTypeThr", column = "COUPON_TYPE_THR"),
            @Result(property = "couponNum", column = "COUPON_NUM"),
            @Result(property = "couponCalculation", column = "COUPON_CALCULATION"),
            @Result(property = "calculationTime", column = "CALCULATION_TIME")
    })
    List<OrderCalculation> getAll();

    @Select("SELECT * FROM order_calculation WHERE ID = #{id}")
    @Results({
            @Result(property = "id", column = "ID"),
            @Result(property = "commodityName", column = "COMMODITY_NAME"),
            @Result(property = "commodityModel", column = "COMMODITY_MODEL"),
            @Result(property = "commodityNum", column = "COMMODITY_NUM"),
            @Result(property = "commodityPrice", column = "COMMODITY_PRICE"),
            @Result(property = "couponTypeOne", column = "COUPON_TYPE_ONE"),
            @Result(property = "couponTypeTwo", column = "COUPON_TYPE_TWO"),
            @Result(property = "couponTypeThr", column = "COUPON_TYPE_THR"),
            @Result(property = "couponNum", column = "COUPON_NUM"),
            @Result(property = "couponCalculation", column = "COUPON_CALCULATION"),
            @Result(property = "calculationTime", column = "CALCULATION_TIME")
    })
    OrderCalculation getId(Long id);

//    下面的方法没有写好的，不能用

    @Insert("INSERT INTO order_calculation(userName,passWord,user_sex) VALUES(#{userName}, #{passWord}, #{userSex})")
    void insert(OrderCalculation orderCalculation);

    @Update("UPDATE order_calculation SET userName=#{userName},nick_name=#{nickName} WHERE ID =#{id}")
    void update(OrderCalculation orderCalculation);

    @Delete("DELETE FROM order_calculation WHERE ID =#{id}")
    void delete(Long id);

}
