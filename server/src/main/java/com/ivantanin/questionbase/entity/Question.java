package com.ivantanin.questionbase.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Questions")
@ToString(of = {"id", "topic", "text", "answer", "reward", "author", "creationData"})
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "text")
    private String text;

    @Column(name = "answer")
    private String answer;

    @Column(name = "reward")
    private Integer reward;

    @Column(name = "author")
    private String author;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd hh:mm:ss")
    private LocalDateTime creationDate;

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
