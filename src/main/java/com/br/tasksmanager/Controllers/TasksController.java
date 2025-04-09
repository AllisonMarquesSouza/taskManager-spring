package com.br.tasksmanager.Controllers;

import com.br.tasksmanager.dtos.Tasks.*;
import com.br.tasksmanager.models.Tasks;
import com.br.tasksmanager.services.TasksService;
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
public class TasksController {
    private final TasksService tasksService;

    @GetMapping("/listAll/{userId}")
    public ResponseEntity<List<Tasks>> listAllByUserId(@PathVariable("userId") Long userId  ){
        return ResponseEntity.ok(tasksService.listAllByUserId(userId));
    }

    @GetMapping("/filter/{userId}")
    public ResponseEntity<List<Tasks>> filterByUserIdAndNameTaskNative(@PathVariable("userId") Long userId,
                                                                       @RequestParam(required = false) String name,
                                                                       @RequestParam(required = false) String description,
                                                                       @RequestParam(required = false) String priority,
                                                                       @RequestParam(required = false) String status){
        return ResponseEntity.ok(tasksService.filterByUserIdAndNameTaskNative(userId, name, description, priority, status));
    }

    @PostMapping("/create")
    public ResponseEntity<Tasks> create(@RequestBody @Valid TaskRequestDto taskRequestDto){
        return new ResponseEntity<>(tasksService.create(taskRequestDto), HttpStatus.CREATED);
    }

    @PatchMapping("/updateName/{userId}/{taskId}")
    public ResponseEntity<Void> updateName(@PathVariable("userId") Long userId, @PathVariable("taskId") UUID taskId,
                                           @RequestBody @Valid UpdateNameDto nameDto){
        tasksService.updateName(userId, taskId, nameDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/updateDescription/{userId}/{taskId}")
    public ResponseEntity<Void> updateDescription(@PathVariable("userId") Long userId, @PathVariable("taskId") UUID taskId,
                                           @RequestBody @Valid UpdateDescriptionDto descriptionDto){
        tasksService.updateDescription(userId, taskId, descriptionDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/updatePriority/{userId}/{taskId}")
    public ResponseEntity<Void> updatePriority(@PathVariable("userId") Long userId, @PathVariable("taskId") UUID taskId,
                                           @RequestBody @Valid UpdatePriorityDto priorityDto){
        tasksService.updatePriority(userId, taskId, priorityDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/updateStatus/{userId}/{taskId}")
    public ResponseEntity<Void> updateStatus(@PathVariable("userId") Long userId, @PathVariable("taskId") UUID taskId,
                                           @RequestBody @Valid UpdateStatusDto statusDto){
        tasksService.updateStatus(userId, taskId, statusDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/updateDueDate/{userId}/{taskId}")
    public ResponseEntity<Void> updateDueDate(@PathVariable("userId") Long userId, @PathVariable("taskId") UUID taskId,
                                           @RequestBody @Valid UpdateDueDateDto dueDateDto){
        tasksService.updateDueDate(userId, taskId, dueDateDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/delete/{userId}/{taskId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") Long userId, @PathVariable("taskId") UUID id){
        tasksService.deleteById(userId, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
