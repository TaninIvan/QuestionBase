package com.ivantanin.questionbase.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Data
@Table(name = "avatar")
public class Avatar implements Serializable {

    @OneToOne(mappedBy = "avatar", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnoreProperties("avatar")
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avatar_id;

    @Column(name = "image")
    private byte[] image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Avatar)) return false;
        Avatar avatar = (Avatar) o;
        return avatar_id.equals(avatar.avatar_id) &&
                Arrays.equals(image, avatar.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(avatar_id);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "avatar_id=" + avatar_id +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
