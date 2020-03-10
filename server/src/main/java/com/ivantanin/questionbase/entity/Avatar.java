package com.ivantanin.questionbase.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "avatar")
public class Avatar implements Serializable {

    @OneToOne(mappedBy = "avatar", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    private User user;

    @Id
    private Long avatar_id;

    @Column(name = "image")
    @ToString.Exclude
    private byte[] image;
}
