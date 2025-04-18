package com.br.tasksmanager.services;

import com.br.tasksmanager.Enums.TaskPriority;
import com.br.tasksmanager.Enums.TaskStatus;
import com.br.tasksmanager.dtos.Tasks.*;
import com.br.tasksmanager.exceptions.BadRequestException;
import com.br.tasksmanager.models.Tasks;
import com.br.tasksmanager.models.Users;
import com.br.tasksmanager.repositories.TasksRepository;
import com.br.tasksmanager.repositories.UsersRepository;
import com.br.tasksmanager.util.TasksCreate;
import com.br.tasksmanager.util.UsersCreate;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TasksServiceTest {
    @Mock
    private TasksRepository tasksRepository;

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private TasksService tasksService;

    @Captor
    private ArgumentCaptor<Tasks> taskCaptor;



    @Test
    void getById_ExistingTask_ReturnsTask() {
        Tasks task = TasksCreate.createTaskService(UsersCreate.createUserService());
        
        when(tasksRepository.findById(task.getId()))
                .thenReturn(Optional.of(task));

        Tasks taskFound = tasksService.getById(task.getId());

        assertNotNull(taskFound);
        assertEquals(taskFound, task);
    }
    @Test
    void getById_NotExistingTask_ThrowEntityNotFoundException() {
        Tasks task = TasksCreate.createTaskService(UsersCreate.createUserService());
        
        when(tasksRepository.findById(task.getId()))
                .thenThrow(new EntityNotFoundException("Task not found"));

        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> tasksService.getById(task.getId()));

        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    void listAllByUserId_ExistingTasks_ReturnsTasks() {
        Users user = UsersCreate.createUserService();
        
        Tasks taskOne = TasksCreate.createTaskService(user);
        Tasks taskTwo = TasksCreate.createTask2(user);

        when(tasksRepository.findAllByUserId(user.getId()))
                .thenReturn(List.of(taskOne, taskTwo));

        List<Tasks> tasksList = tasksService.listAllByUserId(user.getId());

        assertEquals(2, tasksList.size());
        assertEquals(tasksList.getFirst(), taskOne);
        assertEquals(tasksList.getLast(), taskTwo);
    }

    @Test
    void filter_ExistingTasksAccordingToParameters_ReturnsTasks() {
        Users user = UsersCreate.createUserService();
        
        Tasks taskOne = TasksCreate.createTaskService(user);

        when(tasksRepository.filter(user.getId(), taskOne.getNameTask(), taskOne.getDescription(),
                taskOne.getPriority().name(), taskOne.getStatus().name()))
                .thenReturn(List.of(taskOne));

        List<Tasks> tasksList = tasksService
                .filter(user.getId(), taskOne.getNameTask(), taskOne.getDescription(),
                        taskOne.getPriority().name(), taskOne.getStatus().name());

        assertEquals(1, tasksList.size());
        assertEquals(tasksList.getFirst(), taskOne);
    }

    @Test
    void create_ValidTaskRequest_ReturnsSavedTask() {
        Users user = UsersCreate.createUserService();
        
        TaskRequestDto taskDto = TasksCreate.createTaskDto(user.getId());
        Tasks expectedTask = new Tasks(user, taskDto);

        when(usersRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(tasksRepository.save(Mockito.any(Tasks.class)))
                .thenReturn(expectedTask);

        Tasks taskToBeSave = tasksService.create(taskDto);

        assertNotNull(taskToBeSave);
        assertEquals(expectedTask, taskToBeSave);
    }

    @Test
    void create_NotValidRequestByUserId_ThrowEntityNotFoundException() {
        Users user = UsersCreate.createUserService();
        
        TaskRequestDto taskDto = TasksCreate.createTaskDto(user.getId());

        when(usersRepository.findById(user.getId()))
                .thenThrow(new EntityNotFoundException("User not found"));

        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> tasksService.create(taskDto));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void checkUserAndGetTasks_ExistingTaskWithSuchUser_ReturnsTask(){
        Users user = UsersCreate.createUserService();
        Tasks expectedTask = TasksCreate.createTaskService(user);

        when(usersRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(tasksRepository.findById(expectedTask.getId()))
                .thenReturn(Optional.of(expectedTask));
        when(tasksRepository.existsByUserIdAndId(user.getId(), expectedTask.getId()))
                .thenReturn(true);

        Tasks taskToBeReturned = tasksService.checkUserAndGetTasks(user.getId(), expectedTask.getId());

        assertNotNull(taskToBeReturned);
        assertEquals(expectedTask, taskToBeReturned);
    }

    @Test
    void checkUserAndGetTasks_NotExistingUser_ThrowEntityNotFoundException(){
        Users user = UsersCreate.createUserService();
        

        when(usersRepository.findById(user.getId()))
                .thenThrow(new EntityNotFoundException("User not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> tasksService.checkUserAndGetTasks(user.getId(), UUID.randomUUID()));


        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void checkUserAndGetTasks_NotExistingTask_ThrowEntityNotFoundException(){
        Users user = UsersCreate.createUserService();
        
        Tasks task = TasksCreate.createTaskService(user);
        
        when(usersRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        when(tasksRepository.findById(task.getId()))
                .thenThrow(new EntityNotFoundException("Task not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> tasksService.checkUserAndGetTasks(user.getId(), task.getId()));


        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    void checkUserAndGetTasks_NotExistingTaskWithSuchTask_ThrowBadRequestException(){
        Users user = UsersCreate.createUserService();
        
        Tasks task = TasksCreate.createTaskService(user);
        
        when(usersRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        when(tasksRepository.findById(task.getId()))
                .thenReturn(Optional.of(task));

        when(tasksRepository.existsByUserIdAndId(user.getId(), task.getId()))
                .thenReturn(false);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> tasksService.checkUserAndGetTasks(user.getId(), task.getId()));


        assertEquals("The user isn't the owner of this task!", exception.getMessage());
    }

    @Test
    void updateName_ExistingTask_Void() {
        Users user = UsersCreate.createUserService();
        Tasks task = TasksCreate.createTaskService(user);
        
        when(usersRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(tasksRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(tasksRepository.existsByUserIdAndId(user.getId(), task.getId())).thenReturn(true);
        when(tasksRepository.save(task)).thenReturn(task);

        tasksService.updateName(user.getId(), task.getId(), new UpdateNameDto("new name"));

        verify(tasksRepository, times(1)).save(taskCaptor.capture());

        Tasks capturedTask = taskCaptor.getValue();
        assertEquals("new name", capturedTask.getNameTask());
        assertNotNull(capturedTask.getUpdateAt());
        assertEquals(user, capturedTask.getUser());
    }

    @Test
    void updateDescription_ExistingTask_Void() {
        Users user = UsersCreate.createUserService();
        Tasks task = TasksCreate.createTaskService(user);

        when(usersRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(tasksRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(tasksRepository.existsByUserIdAndId(user.getId(), task.getId())).thenReturn(true);
        when(tasksRepository.save(task)).thenReturn(task);

        tasksService.updateDescription(user.getId(), task.getId(), new UpdateDescriptionDto("new description"));

        verify(tasksRepository, times(1)).save(taskCaptor.capture());

        Tasks capturedTask = taskCaptor.getValue();
        assertEquals("new description", capturedTask.getDescription());
        assertNotNull(capturedTask.getUpdateAt());
        assertEquals(user, capturedTask.getUser());
    }

    @Test
    void updatePriority__ExistingTask_Void() {
        Users user = UsersCreate.createUserService();
        Tasks task = TasksCreate.createTaskService(user);

        when(usersRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(tasksRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(tasksRepository.existsByUserIdAndId(user.getId(), task.getId())).thenReturn(true);
        when(tasksRepository.save(task)).thenReturn(task);

        tasksService.updatePriority(user.getId(), task.getId(), new UpdatePriorityDto(TaskPriority.LOW));

        verify(tasksRepository, times(1)).save(taskCaptor.capture());

        Tasks capturedTask = taskCaptor.getValue();
        assertEquals(TaskPriority.LOW, capturedTask.getPriority());
        assertNotNull(capturedTask.getUpdateAt());
        assertEquals(user, capturedTask.getUser());
    }

    @Test
    void updateStatus_ExistingTask_Void() {
        Users user = UsersCreate.createUserService();
        Tasks task = TasksCreate.createTaskService(user);

        when(usersRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(tasksRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(tasksRepository.existsByUserIdAndId(user.getId(), task.getId())).thenReturn(true);
        when(tasksRepository.save(task)).thenReturn(task);

        tasksService.updateStatus(user.getId(), task.getId(), new UpdateStatusDto(TaskStatus.PENDING));

        verify(tasksRepository, times(1)).save(taskCaptor.capture());

        Tasks capturedTask = taskCaptor.getValue();
        assertEquals(TaskStatus.PENDING, capturedTask.getStatus());
        assertNotNull(capturedTask.getUpdateAt());
        assertEquals(user, capturedTask.getUser());
    }

    @Test
    void completeTask_ExistingTask_Void() {
        Users user = UsersCreate.createUserService();
        Tasks task = TasksCreate.createTaskService(user);

        when(usersRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(tasksRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(tasksRepository.existsByUserIdAndId(user.getId(), task.getId())).thenReturn(true);
        when(tasksRepository.save(task)).thenReturn(task);

        tasksService.completeTask(user.getId(), task.getId());

        verify(tasksRepository, times(1)).save(taskCaptor.capture());

        Tasks capturedTask = taskCaptor.getValue();
        assertEquals(TaskStatus.COMPLETED, capturedTask.getStatus());
        assertNotNull(capturedTask.getUpdateAt());
        assertEquals(user, capturedTask.getUser());
    }

    @Test
    void updateDueDate_ExistingTask_Void() {
        Users user = UsersCreate.createUserService();
        Tasks task = TasksCreate.createTaskService(user);

        when(usersRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(tasksRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(tasksRepository.existsByUserIdAndId(user.getId(), task.getId())).thenReturn(true);
        when(tasksRepository.save(task)).thenReturn(task);

        tasksService.updateDueDate(user.getId(), task.getId(), new UpdateDueDateDto(LocalDate.of(2025, 10, 20)));

        verify(tasksRepository, times(1)).save(taskCaptor.capture());

        Tasks capturedTask = taskCaptor.getValue();
        assertEquals(LocalDate.of(2025, 10, 20), capturedTask.getDueDate());
        assertNotNull(capturedTask.getUpdateAt());
        assertEquals(user, capturedTask.getUser());
    }

    @Test
    void deleteById__ExistingTask_Void() {
        Users user = UsersCreate.createUserService();
        Tasks task = TasksCreate.createTaskService(user);

        when(usersRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(tasksRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(tasksRepository.existsByUserIdAndId(user.getId(), task.getId())).thenReturn(true);

        tasksService.deleteById(user.getId(), task.getId());

        verify(tasksRepository, times(1)).deleteById(task.getId());
    }
}