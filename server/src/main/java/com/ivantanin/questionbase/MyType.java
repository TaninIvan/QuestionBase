package com.ivantanin.questionbase;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class MyType {
    private String text;

    public MyType() {
    }

    public MyType(String initText) {
        this.text = initText;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String newText) {
        this.text = newText;
    }

    @Bean
    @Qualifier("A")
    public MyType typeA() {
        return new MyType("A");
    }

    @Bean
    @Qualifier("B")
    public MyType typeB() {
        return new MyType("B");
    }

    @Bean
    @Primary
    public MyType primary() {
        return new MyType("primary");
    }


}
