package com.ivantanin.questionbase.beanInjection;

import org.springframework.stereotype.Component;

@Component
public class MyType implements TypeInterface {

    @Override
    public String getText() {
        return "Default";
    }
}
