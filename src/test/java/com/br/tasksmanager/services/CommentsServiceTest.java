package com.br.tasksmanager.services;

import com.br.tasksmanager.dtos.Comments.CommentRequestDto;
import com.br.tasksmanager.dtos.Comments.CommentUpdateDto;
import com.br.tasksmanager.exceptions.BadRequestException;
import com.br.tasksmanager.models.Comments;
import com.br.tasksmanager.models.Tasks;
import com.br.tasksmanager.models.Users;
import com.br.tasksmanager.repositories.CommentsRepository;
import com.br.tasksmanager.repositories.TasksRepository;
import com.br.tasksmanager.repositories.UsersRepository;
import com.br.tasksmanager.util.CommentsCreate;
import com.br.tasksmanager.util.TasksCreate;
import com.br.tasksmanager.util.UsersCreate;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentsServiceTest {
    @Mock
    private CommentsRepository commentsRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private TasksRepository tasksRepository;

    @InjectMocks
    private CommentsService commentsService;

    @Test
    void findAllByTaskId_ExistingComments_ReturnsComments() {
        Users user = UsersCreate.createUser();
        Tasks task = TasksCreate.createTask(user);
        Comments comment = CommentsCreate.createComment(user, task);
        Comments comment2 = CommentsCreate.createComment2(user, task);

        when(commentsRepository.findAllByTask_Id(task.getId()))
                .thenReturn(List.of(comment, comment2));

        List<Comments> allCommentsByTaskId = commentsService.findAllByTaskId(task.getId());

        assertEquals(2, allCommentsByTaskId.size());
        assertEquals(comment, allCommentsByTaskId.getFirst());
        assertEquals(comment2, allCommentsByTaskId.getLast());
    }

    @Test
    void findAllByUserId_ExistingComments_ReturnsComments() {
        Users user = UsersCreate.createUser();
        Tasks task = TasksCreate.createTask(user);
        Comments comment = CommentsCreate.createComment(user, task);
        Comments comment2 = CommentsCreate.createComment2(user, task);

        when(commentsRepository.findAllByUser_Id(user.getId()))
                .thenReturn(List.of(comment, comment2));

        List<Comments> allCommentsByUserId = commentsService.findAllByUserId(user.getId());

        assertEquals(2, allCommentsByUserId.size());
        assertEquals(comment, allCommentsByUserId.getFirst());
        assertEquals(comment2, allCommentsByUserId.getLast());
    }

    @Test
    void create_ValidRequest_ReturnsComment() {
        Users user = UsersCreate.createUser();
        Tasks task = TasksCreate.createTask(user);
        Comments commentExpected = CommentsCreate.createComment(user, task);

        when(usersRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        when(tasksRepository.findById(task.getId()))
                .thenReturn(Optional.of(task));

        when(tasksRepository.existsByUserIdAndId(user.getId(), task.getId()))
                .thenReturn(true);

        when(commentsRepository.save(Mockito.any(Comments.class)))
                .thenReturn(commentExpected);

        Comments commentToSave = commentsService.create(
                CommentRequestDto.builder().userId(user.getId()).taskId(task.getId()).comment("Comment here").build());


        assertNotNull(commentToSave);
        assertEquals(commentExpected, commentToSave);
    }

    @Test
    void create_InvalidUser_ThrowEntityNotFoundException() {
        Users user = UsersCreate.createUser();
        Tasks task = TasksCreate.createTask(user);

        when(usersRepository.findById(user.getId()))
                .thenThrow(new EntityNotFoundException("User not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> commentsService.create(CommentRequestDto.builder()
                                .userId(user.getId()).taskId(task.getId()).comment("Comment here")
                                .build()));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void create_InvalidTask_ThrowEntityNotFoundException() {
        Users user = UsersCreate.createUser();
        Tasks task = TasksCreate.createTask(user);

        when(usersRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        when(tasksRepository.findById(task.getId()))
                .thenThrow(new EntityNotFoundException("Task not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> commentsService.create(CommentRequestDto.builder()
                                .userId(user.getId()).taskId(task.getId()).comment("Comment here")
                                .build()));

        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    void create_InvalidUserWithSuchTask_ThrowBadRequestException() {
        Users user = UsersCreate.createUser();
        Tasks task = TasksCreate.createTask(user);

        when(usersRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        when(tasksRepository.findById(task.getId()))
                .thenReturn(Optional.of(task));

        when(tasksRepository.existsByUserIdAndId(user.getId(), task.getId()))
                .thenThrow(new BadRequestException("There is no user with such task"));


        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> commentsService.create(CommentRequestDto.builder()
                                .userId(user.getId()).taskId(task.getId()).comment("Comment here")
                                .build()));

        assertEquals("There is no user with such task", exception.getMessage());
    }

    @Test
    void update_ExistingComment_Void() {
        Users user = UsersCreate.createUser();
        Tasks task = TasksCreate.createTask(user);
        Comments comment = CommentsCreate.createComment(user, task);

        when(commentsRepository.findById(comment.getId()))
                .thenReturn(Optional.of(comment));

        when(commentsRepository.existsByUser_IdAndId(user.getId(), comment.getId()))
                .thenReturn(true);

        when(commentsRepository.save(comment)).thenReturn(comment);

        commentsService.update(user.getId(), comment.getId(), CommentUpdateDto.builder().comment("new comment").build());

        ArgumentCaptor<Comments> commentCaptor = ArgumentCaptor.forClass(Comments.class);
        verify(commentsRepository, times(1)).save(commentCaptor.capture());

        Comments commentCaptured = commentCaptor.getValue();
        assertEquals("new comment", commentCaptured.getComment());
        assertEquals(commentCaptured.getUser(), user);
        assertEquals(commentCaptured.getTask(), task);
    }
    @Test
    void update_InvalidComment_ThrowEntityNotFoundException(){
        Users user = UsersCreate.createUser();
        Tasks task = TasksCreate.createTask(user);
        Comments comment = CommentsCreate.createComment(user, task);

        when(commentsRepository.findById(comment.getId()))
                .thenThrow(new EntityNotFoundException("Comment not found to update"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> commentsService.update(user.getId(), comment.getId(),
                        CommentUpdateDto.builder().comment("new comment").build()));

        assertEquals("Comment not found to update", exception.getMessage());
    }

    @Test
    void update_InvalidCommentWithSuchUser_ThrowBadRequestException(){
        Users user = UsersCreate.createUser();
        Tasks task = TasksCreate.createTask(user);
        Comments comment = CommentsCreate.createComment(user, task);

        when(commentsRepository.findById(comment.getId()))
                .thenReturn(Optional.of(comment));
        when(commentsRepository.existsByUser_IdAndId(user.getId(), comment.getId()))
                .thenReturn(false);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> commentsService.update(user.getId(), comment.getId(),
                        CommentUpdateDto.builder().comment("new comment").build()));

        assertEquals("There is no such comment for this user, check the user id and comment id", exception.getMessage());
    }

    @Test
    void deleteById_ExistingComment_Void() {
        Users user = UsersCreate.createUser();
        Tasks task = TasksCreate.createTask(user);
        Comments comment = CommentsCreate.createComment(user, task);

        when(commentsRepository.findById(comment.getId()))
                .thenReturn(Optional.of(comment));
        when(commentsRepository.existsByUser_IdAndId(user.getId(), comment.getId()))
                .thenReturn(true);

        commentsService.deleteById(user.getId(), comment.getId());

        verify(commentsRepository, times(1)).deleteById(comment.getId());
    }

    @Test
    void deleteById_InvalidComment_ThrowEntityNotFoundException() {
        Users user = UsersCreate.createUser();
        Tasks task = TasksCreate.createTask(user);
        Comments comment = CommentsCreate.createComment(user, task);

        when(commentsRepository.findById(comment.getId()))
                .thenThrow(new EntityNotFoundException("Comment not found"));

        EntityNotFoundException exception =
                assertThrows(EntityNotFoundException.class, () -> commentsService.deleteById(user.getId(), comment.getId()));

        assertEquals("Comment not found", exception.getMessage());
    }

    @Test
    void deleteById_InvalidCommentWithSuchUser_ThrowBadRequestException() {
        Users user = UsersCreate.createUser();
        Tasks task = TasksCreate.createTask(user);
        Comments comment = CommentsCreate.createComment(user, task);

        when(commentsRepository.findById(comment.getId()))
                .thenReturn(Optional.of(comment));
        when(commentsRepository.existsByUser_IdAndId(user.getId(), comment.getId()))
                .thenReturn(false);

        BadRequestException exception =
                assertThrows(BadRequestException.class, () -> commentsService.deleteById(user.getId(), comment.getId()));

        assertEquals("There is no such comment for this user, check the user id and comment id", exception.getMessage());
    }
}