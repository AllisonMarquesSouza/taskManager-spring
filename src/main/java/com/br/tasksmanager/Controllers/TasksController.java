package com.br.tasksmanager.Controllers;

import com.br.tasksmanager.dtos.Tasks.*;
import com.br.tasksmanager.models.Tasks;
import com.br.tasksmanager.services.TasksService;
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
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "404", description = "Not found ", content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema())),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema()))
})
public class TasksController {
    private final TasksService tasksService;

    @Operation(
            description = "Get by Id",
            summary = "Get task by id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tasks.class))
                    )
            }
    )
    @GetMapping("/getById/{taskId}")
    public ResponseEntity<Tasks> getById(@PathVariable("taskId") UUID id){
        return ResponseEntity.ok(tasksService.getById(id));
    }

    @Operation(
            description = "Get all by user Id",
            summary = "Get all tasks by user id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(oneOf = {Tasks.class})))
                    )
            }
    )
    @GetMapping("/listAll/{userId}")
    public ResponseEntity<List<Tasks>> listAllByUserId(@PathVariable("userId") Long userId  ){
        return ResponseEntity.ok(tasksService.listAllByUserId(userId));
    }

    @Operation(
            description = "Filter",
            summary = "Filter tasks by user Id, name, description, priority and status",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(oneOf = {Tasks.class})))
                    )
            }
    )
    @GetMapping("/filter/{userId}")
    public ResponseEntity<List<Tasks>> filterByUserIdAndNameTaskNative(@PathVariable("userId") Long userId,
                                                                       @RequestParam(required = false) String name,
                                                                       @RequestParam(required = false) String description,
                                                                       @RequestParam(required = false) String priority,
                                                                       @RequestParam(required = false) String status){
        return ResponseEntity.ok(tasksService.filter(userId, name, description, priority, status));
    }

    @Operation(
            description = "Create",
            summary = "Create task",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tasks.class))
                    )
            }
    )
    @PostMapping("/create")
    public ResponseEntity<Tasks> create(@RequestBody @Valid TaskRequestDto taskRequestDto){
        return new ResponseEntity<>(tasksService.create(taskRequestDto), HttpStatus.CREATED);
    }

    @Operation(
            description = "Update name",
            summary = "Update name",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "204"
                    )
            }
    )
    @PatchMapping("/updateName/{userId}/{taskId}")
    public ResponseEntity<Void> updateName(@PathVariable("userId") Long userId, @PathVariable("taskId") UUID taskId,
                                           @RequestBody @Valid UpdateNameDto nameDto){
        tasksService.updateName(userId, taskId, nameDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            description = "Update description",
            summary = "Update description",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "204"
                    )
            }
    )
    @PatchMapping("/updateDescription/{userId}/{taskId}")
    public ResponseEntity<Void> updateDescription(@PathVariable("userId") Long userId, @PathVariable("taskId") UUID taskId,
                                           @RequestBody @Valid UpdateDescriptionDto descriptionDto){
        tasksService.updateDescription(userId, taskId, descriptionDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            description = "Update priority",
            summary = "Update priority",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "204"
                    )
            }
    )
    @PatchMapping("/updatePriority/{userId}/{taskId}")
    public ResponseEntity<Void> updatePriority(@PathVariable("userId") Long userId, @PathVariable("taskId") UUID taskId,
                                           @RequestBody @Valid UpdatePriorityDto priorityDto){
        tasksService.updatePriority(userId, taskId, priorityDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            description = "Update status",
            summary = "Update status",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "204"
                    )
            }
    )
    @PatchMapping("/updateStatus/{userId}/{taskId}")
    public ResponseEntity<Void> updateStatus(@PathVariable("userId") Long userId, @PathVariable("taskId") UUID taskId,
                                           @RequestBody @Valid UpdateStatusDto statusDto){
        tasksService.updateStatus(userId, taskId, statusDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            description = "Complete task",
            summary = "Complete task specified",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "204"
                    )
            }
    )
    @PatchMapping("/completeTask/{userId}/{taskId}")
    public ResponseEntity<Void> completeTask(@PathVariable("userId") Long userId, @PathVariable("taskId") UUID taskId){
        tasksService.completeTask(userId, taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            description = "Update due date",
            summary = "Update due date",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "204"
                    )
            }
    )
    @PatchMapping("/updateDueDate/{userId}/{taskId}")
    public ResponseEntity<Void> updateDueDate(@PathVariable("userId") Long userId, @PathVariable("taskId") UUID taskId,
                                           @RequestBody @Valid UpdateDueDateDto dueDateDto){
        tasksService.updateDueDate(userId, taskId, dueDateDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(
            description = "Delete",
            summary = "Delete task by user and task Ids",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "204"
                    )
            }
    )
    @DeleteMapping("/delete/{userId}/{taskId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") Long userId, @PathVariable("taskId") UUID id){
        tasksService.deleteById(userId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
