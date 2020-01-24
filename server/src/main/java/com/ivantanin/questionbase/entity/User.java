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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<Answer> answer;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "score")
    private Integer score;

    @Type(type = "jsonb")
    @Column(name = "address", columnDefinition = "jsonb")
    private Address address;

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Collection<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(Collection<Answer> answer) {
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

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

        @Override
        public String toString(){
            return "{" + this.country + "," + this.town + "," + this.street + "," + this.house + "}";

        }
    }

    @Override
    public String toString(){
        return "{" + this.id.toString() + ";" + this.username + ";" + this.score + "}";

    }

}


