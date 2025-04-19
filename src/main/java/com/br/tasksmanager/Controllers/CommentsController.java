package com.br.tasksmanager.Controllers;

import com.br.tasksmanager.dtos.Comments.CommentRequestDto;
import com.br.tasksmanager.dtos.Comments.CommentUpdateDto;
import com.br.tasksmanager.models.Comments;
import com.br.tasksmanager.services.CommentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Comments")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "404", description = "Not found ", content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema()))
})
public class CommentsController {
    private final CommentsService commentsService;

    @Operation(
            description = "Get by user Id",
            summary = "Get all comments by user Id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(oneOf = {Comments.class})))
                    )
            }
    )
    @GetMapping("/getAllByUserId/{userId}")
    public ResponseEntity<List<Comments>> getAllByUserId(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(commentsService.findAllByUserId(userId));
    }

    @Operation(
            description = "Create",
            summary = "Create a comment for the task",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Comments.class))
                    )
            }
    )
    @PostMapping("/create")
    public ResponseEntity<Comments> create(@RequestBody @Valid CommentRequestDto commentRequestDto){
        return new ResponseEntity<>(commentsService.create(commentRequestDto), HttpStatus.CREATED);
    }

    @Operation(
            description = "Update",
            summary = "Update a comment of the task",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "204"
                    )
            }
    )
    @PatchMapping("/updateComment/{userId}/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable("userId") Long userId,
                                                  @PathVariable("commentId") UUID commentId,
                                                  @RequestBody @Valid CommentUpdateDto commentUpdateDto){
        commentsService.update(userId, commentId, commentUpdateDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            description = "Delete",
            summary = "Delete the comment",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "204"
                    )
            }
    )
    @DeleteMapping("/delete/{userId}/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") Long userId, @PathVariable("commentId") UUID commentId){
        commentsService.deleteById(userId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
