package org.example.strategy_pattern.controller;

import jakarta.annotation.Resource;
import org.example.strategy_pattern.enums.UserTypeEnum;
import org.example.strategy_pattern.factory.CustomerStrategyFactory;
import org.example.strategy_pattern.service.ICustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CustomerController {

    @Resource
    CustomerStrategyFactory customerStrategyFactory;

    @GetMapping("/customer/{recharge}")
    public ResponseEntity<String> customer(@PathVariable(value = "recharge") int recharge) {
        UserTypeEnum userType = UserTypeEnum.typeOf(recharge);
        ICustomerService customerService = customerStrategyFactory.getStrategy(userType);
        return ResponseEntity.ok(customerService.findCustomer());
    }


}
