package com.revconnect.RevConnectWeb.entity;


import jakarta.persistence.*;

@Entity
@Table(name="post_hashtags",
        uniqueConstraints = @UniqueConstraint(columnNames = {"post_id","hashtag_id"})
)
public class PostHashtags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id",nullable = false)
    private Posts post;

    @JoinColumn(name="hashtag_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private HashTags hashtag;

    public PostHashtags(){};

    public PostHashtags(Posts post, HashTags hashtag) {
        this.post = post;
        this.hashtag = hashtag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    public HashTags getHashtag() {
        return hashtag;
    }

    public void setHashtag(HashTags hashtag) {
        this.hashtag = hashtag;
    }
}
