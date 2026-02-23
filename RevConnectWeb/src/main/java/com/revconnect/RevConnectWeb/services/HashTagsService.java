// HashTagService.java
package com.revconnect.RevConnectWeb.services;

import com.revconnect.RevConnectWeb.DTO.HashTagsDTO;
import com.revconnect.RevConnectWeb.entity.HashTags;
import com.revconnect.RevConnectWeb.entity.Posts;
import com.revconnect.RevConnectWeb.entity.User;
import com.revconnect.RevConnectWeb.repository.HashTagsRepository;
import com.revconnect.RevConnectWeb.repository.PostRepository;
import com.revconnect.RevConnectWeb.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class HashTagsService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final HashTagsRepository hashTagRepository;

    public HashTagsService(UserRepository userRepository,
                                  PostRepository postRepository,
                                  HashTagsRepository hashTagRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.hashTagRepository = hashTagRepository;
    }



    private String normalizeTag(String t) {
        if (t == null) return "";
        String tag = t.trim();
        if (tag.isEmpty()) return "";
        if (!tag.startsWith("#")) tag = "#" + tag;
        return tag;
    }
}