package com.revconnect.RevConnectWeb.controllers;

import com.revconnect.RevConnectWeb.DTO.HashTagsDTO;
import com.revconnect.RevConnectWeb.entity.HashTags;
import com.revconnect.RevConnectWeb.services.HashTagsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//
//@RestController
//@RequestMapping("/hashtags")
//public class HashTagsController {
//    private final HashTagsService hashTagService;
//
//    public HashTagsController(HashTagsService hashTagService) {
//        this.hashTagService = hashTagService;
//    }
//
//    @PostMapping("/create")
//    public HashTagsDTO create(@RequestBody HashTagsDTO dto) {
//        return hashTagService.create(dto);
//    }
//
//    @GetMapping("/search")
//    public List<HashTags> search(@RequestParam String q) {
//        return hashTagService.search(q);
//    }
//
//    @PutMapping("/{id}")
//    public HashTagsDTO update(@PathVariable Long id, @RequestBody HashTagsDTO dto) {
//        return hashTagService.update(id, dto);
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable Long id) {
//        return hashTagService.delete(id);
//    }
//}
