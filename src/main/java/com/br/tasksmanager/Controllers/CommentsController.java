package com.br.tasksmanager.Controllers;

import com.br.tasksmanager.dtos.Comments.CommentRequestDto;
import com.br.tasksmanager.dtos.Comments.CommentUpdateDto;
import com.br.tasksmanager.models.Comments;
import com.br.tasksmanager.services.CommentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;

    @GetMapping("/getAllByUserId/{userId}")
    public ResponseEntity<List<Comments>> getAllByUserId(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(commentsService.findAllByUserId(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<Comments> create(@RequestBody @Valid CommentRequestDto commentRequestDto){
        return new ResponseEntity<>(commentsService.create(commentRequestDto), HttpStatus.CREATED);
    }

    @PatchMapping("/updateComment/{userId}/{taskId}")
    public ResponseEntity<Void> updateComment(@PathVariable("userId") Long userId,
                                                  @PathVariable("taskId") UUID taskId,
                                                  @RequestBody @Valid CommentUpdateDto commentUpdateDto){
        commentsService.update(userId, taskId, commentUpdateDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{userId}/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") Long userId, @PathVariable("commentId") UUID commentId){
        commentsService.deleteById(userId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
