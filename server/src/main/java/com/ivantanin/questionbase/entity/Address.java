package com.ivantanin.questionbase.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {
    private String country;
    private String city;
    private String street;
    private String house;

    public Address() {
        this.country = "no info";
        this.city = "no info";
        this.street = "no info";
        this.house = "no info";
    }
}
