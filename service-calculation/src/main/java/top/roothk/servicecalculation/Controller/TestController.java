package top.roothk.servicecalculation.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import top.roothk.servicecalculation.Service.Dao.OrderCalculation;
import top.roothk.servicecalculation.Service.Mapper.OrderCalculationMapper;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class TestController {

    @Autowired
    OrderCalculationMapper orderCalculationMapper;

    @GetMapping(value="getId")
    public OrderCalculation getById(){
        return orderCalculationMapper.getId(1L);
    }

    @GetMapping(value="getAll")
    public List<OrderCalculation> getAll(){
        return orderCalculationMapper.getAll();
    }

    @Value("${app.hello}")
    private String hello;

    @GetMapping(value = "/hello")
    public String test(){
        return hello;
    }
}
