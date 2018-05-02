package top.roothk.servicecalculation.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.roothk.servicecalculation.Service.Dao.OrderCalculationMapper;
import top.roothk.servicecalculation.Service.Model.OrderCalculation;

import java.util.List;

@Service
public class OrderCalculationService {

    @Autowired
    private OrderCalculationMapper orderCalculationMapper;

    public int addUser(OrderCalculation user){
        return orderCalculationMapper.insert(user);
    }

    public OrderCalculation getUserById(Long id){
        return orderCalculationMapper.selectByPrimaryKey(id);
    }

    public List<OrderCalculation> getAllUsers(){
        return orderCalculationMapper.getAllUsers();
    }
}
