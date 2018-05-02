package top.roothk.servicecalculation.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import top.roothk.servicecalculation.Service.Model.OrderCalculation;
import top.roothk.servicecalculation.Service.OrderCalculationService;

@RestController
@RefreshScope
public class TestController {

    @Autowired
    private OrderCalculationService orderCalculationService;

    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public int addUser(@RequestBody OrderCalculation user){
        return orderCalculationService.addUser(user);
    }

    @RequestMapping(value="/get",method=RequestMethod.GET)
    @ResponseBody
    public OrderCalculation getUserById(){
        return orderCalculationService.getUserById(1L);
    }
}
