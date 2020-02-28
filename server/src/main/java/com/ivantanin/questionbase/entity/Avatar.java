package com.ivantanin.questionbase.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "avatar")
public class Avatar implements Serializable {

    @OneToOne(mappedBy = "avatar", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    transient private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avatar_id;

    @Column(name = "image")
    private byte[] image;
}
