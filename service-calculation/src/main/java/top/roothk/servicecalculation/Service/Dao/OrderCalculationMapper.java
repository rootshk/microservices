package top.roothk.servicecalculation.Service.Dao;

import top.roothk.servicecalculation.Service.Model.OrderCalculation;

import java.util.List;

public interface OrderCalculationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderCalculation record);

    int insertSelective(OrderCalculation record);

    OrderCalculation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderCalculation record);

    int updateByPrimaryKey(OrderCalculation record);

    List<OrderCalculation> getAllUsers();
}