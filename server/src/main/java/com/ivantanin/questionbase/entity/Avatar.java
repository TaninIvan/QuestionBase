package com.ivantanin.questionbase.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.*;
@Entity
@Data
@ToString(exclude = {"user","image"})
@Table(name = "avatar")
public class Avatar implements Serializable {

    @OneToOne(mappedBy = "avatar", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avatar_id;

    @Column(name = "image")
    private byte[] image;


}
