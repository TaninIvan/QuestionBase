package com.ivantanin.questionbase.entity;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(String url) throws IOException, SQLException {
        BufferedImage image = ImageIO.read(new File(url));
        OutputStream outputStream = blob.setBinaryStream(1);
        ImageIO.write(image, "jpg", outputStream);
    }
}
