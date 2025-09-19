package org.example.strategy_pattern.service.impl;

import org.example.strategy_pattern.annotations.SupportUserType;
import org.example.strategy_pattern.enums.UserTypeEnum;
import org.example.strategy_pattern.service.ICustomerService;
import org.springframework.stereotype.Component;

@Component
@SupportUserType(value = UserTypeEnum.NORMAL)
public class NormalCustomerService implements ICustomerService {
    @Override
    public String findCustomer() {
        System.out.println("普通玩家客服Service被调用");
        return "普通玩家客服";
    }
}
