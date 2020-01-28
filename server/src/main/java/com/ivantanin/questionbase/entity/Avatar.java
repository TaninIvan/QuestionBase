package com.ivantanin.questionbase.entity;

import javax.persistence.*;
import java.io.*;
@Entity
@Table(name = "avatar")
public class Avatar implements Serializable {

    @OneToOne(mappedBy = "avatar", cascade = CascadeType.MERGE)
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avatar_id;

    @Column(name = "image")
    private byte[] image;

    public User getUser() {
        return user;
    }

}
