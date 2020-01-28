package com.ivantanin.questionbase.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "usr")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)

public class User {

   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "avatar_id")
   private Avatar avatar;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Answer> answers;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Integer score;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Address address;

    class Address implements Serializable {
        private String country;
        private String town;
        private String street;
        private String house;

        public Address(){
            this.country = "no info";
            this.town = "no info";
            this.street = "no info";
            this.house = "no info";
        }
    }

}


