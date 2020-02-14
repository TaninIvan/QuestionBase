package com.ivantanin.questionbase.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "usr")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)

public class User {

   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "avatar_id")
   @JsonIgnoreProperties("user")
   private Avatar avatar;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private Set<Answer> answers;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Integer score;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Address[] address;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", score=" + score +
                ", address=" + address +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(score, user.score) &&
                Arrays.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, password, score);
        result = 31 * result + Arrays.hashCode(address);
        return result;
    }
}


