package com.ivantanin.questionbase;

public class MyType {
    private String text;

    public MyType() {
    }

    public MyType(String type) {
        this.text = type;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String newText) {
        this.text = newText;
    }

}
