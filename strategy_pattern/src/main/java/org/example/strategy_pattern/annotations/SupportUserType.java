package org.example.strategy_pattern.annotations;


import org.example.strategy_pattern.enums.UserTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SupportUserType {
    UserTypeEnum value();
}
