package com.ivantanin.questionbase.beanInjection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("B")
public class MyTypeB implements TypeInterface {

    @Override
    public String getText() {
        return "B";
    }
}
