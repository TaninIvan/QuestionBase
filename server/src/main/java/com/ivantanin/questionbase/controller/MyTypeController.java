package com.ivantanin.questionbase.controller;

import com.ivantanin.questionbase.MyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/myType")
@RestController
public class MyTypeController {

    @Autowired
    private List<MyType> myTypeList;

    @Autowired
    @Qualifier("A")
    private List<MyType> myTypeListA;

    @Autowired
    @Qualifier("B")
    private List<MyType> myTypeListB;

    @Autowired
    private MyType myType;

    @GetMapping()
    public List<MyType> myTypeList() {
        return myTypeList;
    }

    @GetMapping("A")
    public List<MyType> myTypeListA() {
        return myTypeListA;
    }

    @GetMapping("B")
    public List<MyType> myTypeListB() {
        return myTypeListB;
    }

    @GetMapping("primary")
    public MyType myTypeListPrimary() {
        return myType;
    }
}
