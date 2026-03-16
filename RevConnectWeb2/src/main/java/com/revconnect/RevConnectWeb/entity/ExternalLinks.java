package com.revconnect.RevConnectWeb.entity;

import jakarta.persistence.*;

@Entity
@Table(name="external_links")
public class ExternalLinks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="link_id")
    private Long linkID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false)
    private String url;

    //Social Media, Portfolio, Website
    @Column(name="link_type")
    private String linkType;

    public ExternalLinks(){}

    public Long getLinkID() {
        return linkID;
    }

    public void setLinkID(Long linkID) {
        this.linkID = linkID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }
}
