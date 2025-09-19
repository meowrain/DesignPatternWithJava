package org.example.strategy_pattern.service.impl;

import org.example.strategy_pattern.annotations.SupportUserType;
import org.example.strategy_pattern.enums.UserTypeEnum;
import org.example.strategy_pattern.service.ICustomerService;
import org.springframework.stereotype.Component;
@Component
@SupportUserType(UserTypeEnum.PERSONAL)
public class PersonalRCustomerService implements ICustomerService {
    @Override
    public String findCustomer() {
        System.out.println("专属客服Service被调用");
        return "专属客服";
    }
}
