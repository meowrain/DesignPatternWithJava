package org.example.strategy_pattern.factory;

import jakarta.annotation.Resource;
import org.example.strategy_pattern.annotations.SupportUserType;
import org.example.strategy_pattern.enums.UserTypeEnum;
import org.example.strategy_pattern.service.ICustomerService;
import org.example.strategy_pattern.service.impl.DefaultRCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CustomerStrategyFactory {
    @Resource
    private DefaultRCustomerService defaultRCustomerService;
    private Map<UserTypeEnum, ICustomerService> userTypeEnumICustomerServiceMap;


    public ICustomerService getStrategy(UserTypeEnum userTypeEnum) {
        return userTypeEnumICustomerServiceMap.getOrDefault(userTypeEnum, defaultRCustomerService);
    }

    @Autowired
    public void setUserTypeEnumICustomerServiceMap(List<ICustomerService> customerServiceList) {
        this.userTypeEnumICustomerServiceMap = customerServiceList.stream()
                .filter(customerService -> customerService.getClass().isAnnotationPresent(SupportUserType.class))
                .collect(Collectors.toMap(this::findUserTypeByAnnotation, Function.identity()));
        if(this.userTypeEnumICustomerServiceMap.size() != UserTypeEnum.values().length) {
            throw new IllegalArgumentException("有用户类型没有对应的策略实现类");
        }
    }

    private UserTypeEnum findUserTypeByAnnotation(ICustomerService customerService) {
        SupportUserType supportUserType = customerService.getClass().getAnnotation(SupportUserType.class);
        return supportUserType.value();
    }
}
