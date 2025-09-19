package org.example.strategy_pattern.enums;

import java.util.function.IntPredicate;

public enum UserTypeEnum {
    NORMAL(recharge -> recharge > 0 && recharge <= 100),
    SMALL(recharge -> recharge > 100 && recharge <= 1000),
    BIG(recharge -> recharge > 1000 && recharge <= 10000),
    SUPER(recharge -> recharge > 10000 && recharge <= 100000),
    PERSONAL(recharge -> recharge > 100000);

    private final IntPredicate support;
    UserTypeEnum(IntPredicate support) {
        this.support = support;
    }

    public static UserTypeEnum typeOf(int recharge) {
        for (UserTypeEnum userType : values()) {
            if(userType.support.test(recharge)) {
                return userType;
            }
        }
        return null;
    }
}
