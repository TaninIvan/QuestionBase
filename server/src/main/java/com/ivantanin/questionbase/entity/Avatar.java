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

    @PersistenceContext
    private EntityManager em;

    @OneToOne(mappedBy = "avatar", cascade = CascadeType.REFRESH)
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

        em.getTransaction().begin();
        this.user = user;
        user.setAvatar(this);
        em.merge(user);
        em.getTransaction().commit();
    }

    public void setImage(String url) {
        try ( ByteArrayOutputStream baos = new ByteArrayOutputStream(1000)){
            BufferedImage image = ImageIO.read(new File(url));
            ImageIO.write(image, "jpg", baos);
            baos.flush();
            this.image = Base64.getEncoder().encode(baos.toByteArray());
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
