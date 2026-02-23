// HashTagDTO.java
package com.revconnect.RevConnectWeb.DTO;

public class HashTagsDTO {
    private Long hashtagId;
    private String tag;
    private Long postId;

    public HashTagsDTO() {}

    public HashTagsDTO(Long hashtagId, String tag, Long postId) {
        this.hashtagId = hashtagId;
        this.tag = tag;
        this.postId = postId;
    }

    public Long getHashtagId() {
        return hashtagId;
    }

    public void setHashtagId(Long hashtagId) {
        this.hashtagId = hashtagId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}