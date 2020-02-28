package com.ivantanin.questionbase.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Data
@Table(name = "usr")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)

public class User {

   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "avatar_id")
   private Avatar avatar;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Answer> answers;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    @NotNull  private Integer score;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Address[] address;
}


