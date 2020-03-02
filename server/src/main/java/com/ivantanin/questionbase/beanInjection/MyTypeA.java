package com.ivantanin.questionbase.beanInjection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("A")
public class MyTypeA implements TypeInterface {

    @Override
    public String getText() {
        return "A";
    }
}
