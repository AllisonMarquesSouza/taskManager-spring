package com.br.tasksmanager.services;

import com.br.tasksmanager.dtos.Tasks.*;
import com.br.tasksmanager.exceptions.BadRequestException;
import com.br.tasksmanager.models.Tasks;
import com.br.tasksmanager.models.Users;
import com.br.tasksmanager.repositories.TasksRepository;
import com.br.tasksmanager.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TasksService {
    private final TasksRepository tasksRepository;
    private final UserRepository userRepository;

    public List<Tasks> listAllByUserId(Long id){
        return tasksRepository.findAllByUserId(id);
    }

    public List<Tasks> filterByUserIdAndNameTaskNative(Long id, String name, String description,
                                                       String priority, String status){
        return tasksRepository.filter(id, name, description, priority, status);
    }

    @Transactional
    public Tasks create(TaskRequestDto taskRequestDto){
        Users user = userRepository.findById(taskRequestDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("User doesn't exist, check it"));

        Tasks task = new Tasks(user, taskRequestDto);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdateAt(LocalDateTime.now());

        return tasksRepository.save(task);
    }


    //updates
    private Tasks checkUserAndGetTasks(Long userId, UUID taskId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User doesn't exist, check it"));
        Tasks task = tasksRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task doesn't exist"));

        if(!tasksRepository.existsByUserIdAndId(userId, taskId)){
            throw new BadRequestException("The user isn't the owner of this task!");
        }
        return task;
    }

    @Transactional
    public void updateName(Long userId, UUID taskId, UpdateNameDto nameDto){
        Tasks task = checkUserAndGetTasks(userId, taskId);
        task.setUpdateAt(LocalDateTime.now());
        task.setNameTask(nameDto.name());
        tasksRepository.save(task);

    }
    @Transactional
    public void updateDescription(Long userId, UUID taskId, UpdateDescriptionDto descDto){
        Tasks task = checkUserAndGetTasks(userId, taskId);
        task.setUpdateAt(LocalDateTime.now());
        task.setDescription(descDto.description());
        tasksRepository.save(task);
    }

    @Transactional
    public void updatePriority(Long userId, UUID taskId, UpdatePriorityDto priorityDto){
        Tasks task = checkUserAndGetTasks(userId, taskId);
        task.setUpdateAt(LocalDateTime.now());
        task.setPriority(priorityDto.priority());
        tasksRepository.save(task);
    }

    @Transactional
    public void updateStatus(Long userId, UUID taskId, UpdateStatusDto statusDto){
        Tasks task = checkUserAndGetTasks(userId, taskId);
        task.setUpdateAt(LocalDateTime.now());
        task.setStatus(statusDto.status());
        tasksRepository.save(task);
    }

    @Transactional
    public void updateDueDate(Long userId, UUID taskId, UpdateDueDateDto dueDateDto) {
        Tasks task = checkUserAndGetTasks(userId, taskId);
        task.setUpdateAt(LocalDateTime.now());
        task.setDueDate(dueDateDto.dueDate());
        tasksRepository.save(task);
    }


    @Transactional
    public void deleteById(Long userID, UUID id){
        userRepository.findById(userID)
                .orElseThrow(() -> new EntityNotFoundException("User doesn't exist"));

        tasksRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task doesn't exist"));

        if(tasksRepository.existsByUserIdAndId(userID, id)){
            tasksRepository.deleteById(id);
            return;
        }
        throw new BadRequestException("The user isn't the owner of this task!");
    }
}
