package com.ivantanin.questionbase.beanInjection;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MyTypePrimary implements TypeInterface {

    @Override
    public String getText() {
        return "Primary";
    }
}
