package com.revconnect.RevConnectWeb.controllers;


import com.revconnect.RevConnectWeb.DTO.CommentsDTO;
import com.revconnect.RevConnectWeb.services.CommentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentsController {
    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService){
        this.commentsService=commentsService;
    }

    //Posting comment by userId,PostId
    @PostMapping
    public CommentsDTO addComment(@RequestBody CommentsDTO dto) {
        return commentsService.addComment(dto);
    }

    // Update Comment
    @PutMapping("/{commentId}")
    public CommentsDTO updateComment(@PathVariable Long commentId,
                                     @RequestBody CommentsDTO dto) {
        return commentsService.updateComment(commentId, dto);
    }

    //Delete Comment
    @DeleteMapping("/{commentId}/{userId}")
    public String deleteComment(@PathVariable Long commentId,
                                @PathVariable Long userId) {
        commentsService.deleteComment(commentId, userId);
        return "Comment deleted successfully";
    }

    //Get Comments By Post
    @GetMapping("/post/{postId}")
    public List<CommentsDTO> getCommentsByPost(@PathVariable Long postId) {
        return commentsService.getCommentsByPost(postId);
    }

}
