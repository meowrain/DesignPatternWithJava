package org.example.strategy_pattern.service.impl;

import org.example.strategy_pattern.annotations.SupportUserType;
import org.example.strategy_pattern.enums.UserTypeEnum;
import org.example.strategy_pattern.service.ICustomerService;
import org.springframework.stereotype.Component;

@Component
@SupportUserType(value = UserTypeEnum.SUPER)
public class SuperRCustomerService implements ICustomerService {
    @Override
    public String findCustomer() {
        System.out.println("超级大R玩家客服Service被调用");
        return "超级大R玩家客服";
    }
}
