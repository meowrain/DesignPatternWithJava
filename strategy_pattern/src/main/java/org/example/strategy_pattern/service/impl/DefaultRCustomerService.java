package org.example.strategy_pattern.service.impl;

import org.example.strategy_pattern.service.ICustomerService;
import org.springframework.stereotype.Component;
@Component
public class DefaultRCustomerService implements ICustomerService {
    @Override
    public String findCustomer() {
        System.out.println("默认客服Service被调用");
        return "找不到客服";
    }
}
