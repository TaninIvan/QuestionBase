package com.ivantanin.questionbase.entity;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Data
@Table(name = "avatar")
@TypeDef(name = "Blob", typeClass = Blob.class)
public class Avatar {

    @OneToOne(optional = false, mappedBy = "avatar")
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avatar_id;

    @Type(type = "Blob")
    @Column(name = "image")
    private Blob blob;
}
