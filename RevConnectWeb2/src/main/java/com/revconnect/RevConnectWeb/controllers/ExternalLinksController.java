package com.revconnect.RevConnectWeb.controllers;

import com.revconnect.RevConnectWeb.DTO.ExternalLinksDTO;
import com.revconnect.RevConnectWeb.entity.ExternalLinks;
import com.revconnect.RevConnectWeb.services.ExternalLinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/externalLinks")
public class ExternalLinksController {

    @Autowired
    private ExternalLinksService externalLinksService;

    // 1. Add a new external link
    @PostMapping("/add")
    public ResponseEntity<String> addExternalLink(@RequestBody ExternalLinksDTO externalLinksDTO) {
        externalLinksService.addExternalLink(externalLinksDTO);
        return ResponseEntity.ok("External link added successfully.");
    }

    // 2. Update an existing external link
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateExternalLink(@PathVariable Long id, @RequestBody ExternalLinksDTO externalLinksDTO) {
        externalLinksService.updateExternalLink(id, externalLinksDTO);
        return ResponseEntity.ok("External link updated successfully.");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExternalLinks>> getExternalLinksByUserId(@PathVariable Long userId) {
        List<ExternalLinks> externalLinks = externalLinksService.getExternalLinksByUserId(userId);
        return ResponseEntity.ok(externalLinks);
    }


    // 3. Delete an external link
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExternalLink(@PathVariable Long id) {
        externalLinksService.deleteExternalLink(id);
        return ResponseEntity.ok("External link deleted successfully.");
    }
}
