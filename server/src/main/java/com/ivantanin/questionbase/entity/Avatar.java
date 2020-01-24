package com.ivantanin.questionbase.entity;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Base64;

@Entity
@Table(name = "avatar")
public class Avatar implements Serializable {

    @OneToOne(optional = false, mappedBy = "avatar")
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avatar_id;

    @Column(name = "image")
    private byte[] image;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setImage(String url) {
        try {
            BufferedImage image = ImageIO.read(new File(url));
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);

            ImageIO.write(image, "jpg", baos);
            baos.flush();
            this.image = Base64.getEncoder().encode(baos.toByteArray());
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getImage() { return this.image;}

    public Long getAvatarId() {return this.avatar_id;}

    @Override
    public String toString(){
        return "{" + this.avatar_id.toString() + ";" + Arrays.toString(this.image) + "}";

    }
}
