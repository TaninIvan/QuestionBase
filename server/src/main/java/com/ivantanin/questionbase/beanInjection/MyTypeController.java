package com.ivantanin.questionbase.beanInjection;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/myType")
@RestController
@RequiredArgsConstructor
public class MyTypeController {

    @Autowired @Qualifier("A") private final TypeInterface myTypeListA;
    @Autowired @Qualifier("B") private final TypeInterface myTypeListB;
    private final TypeInterface myType;
    private final List<TypeInterface> myTypeList;

    @GetMapping()
    public List<TypeInterface> myTypeList() {
        return myTypeList;
    }

    @GetMapping("A")
    public TypeInterface myTypeListA() {
        return myTypeListA;
    }

    @GetMapping("B")
    public TypeInterface myTypeListB() {
        return myTypeListB;
    }

    @GetMapping("primary")
    public TypeInterface myTypeListPrimary() {
        return myType;
    }
}
