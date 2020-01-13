package com.ivantanin.questionbase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
public class Topic {

    @Id
    @Column(name = "topic_name", nullable = false)
    private String topic_name;

    @ManyToMany(mappedBy = "questionTopics")
    private Collection<Question> questions;

}
