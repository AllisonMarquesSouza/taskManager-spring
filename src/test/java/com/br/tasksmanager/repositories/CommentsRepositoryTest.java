package com.br.tasksmanager.repositories;

import com.br.tasksmanager.models.Comments;
import com.br.tasksmanager.models.Tasks;
import com.br.tasksmanager.models.Users;
import com.br.tasksmanager.util.CommentsCreate;
import com.br.tasksmanager.util.TasksCreate;
import com.br.tasksmanager.util.UsersCreate;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Log4j2
class CommentsRepositoryTest {
    @Autowired
    private TasksRepository tasksRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private CommentsRepository commentsRepository;

    @AfterEach
    void tearDown() {
        commentsRepository.deleteAll();
        tasksRepository.deleteAll();
        usersRepository.deleteAll();
    }

    @Test
    void findAllByTaskId_ExistingComments_ReturnsComments() {
        Users user = usersRepository.save(UsersCreate.createUser());
        Tasks task = tasksRepository.save(TasksCreate.createTask(user));
        Comments commentOne = commentsRepository.save(CommentsCreate.createComment(user, task));
        Comments commentTwo = commentsRepository.save(CommentsCreate.createComment2(user, task));

        List<Comments> allByTaskId = commentsRepository.findAllByTask_Id(task.getId());

        assertEquals(2, allByTaskId.size());
        assertEquals(commentOne, allByTaskId.getFirst());
        assertEquals(commentTwo, allByTaskId.getLast());
    }

    @Test
    void findAllByUserId_ExistingComments_ReturnsComments() {
        Users userOne = usersRepository.save(UsersCreate.createUser());
        Tasks taskOne = tasksRepository.save(TasksCreate.createTask(userOne));
        Users userTwo = usersRepository.save(UsersCreate.createUser2());
        Tasks taskTwo = tasksRepository.save(TasksCreate.createTask2(userTwo));

        Comments commentOne = commentsRepository.save(CommentsCreate.createComment(userOne, taskOne));
        Comments commentTwo = commentsRepository.save(CommentsCreate.createComment2(userTwo, taskTwo));

        List<Comments> allByUserId = commentsRepository.findAllByUser_Id(userOne.getId());

        assertEquals(1, allByUserId.size());
        assertEquals(commentOne, allByUserId.getFirst());
        assertNotEquals(commentOne, commentTwo);
    }

    @Test
    void existsByUserIdAndCommentId_WhenTaskBelongsToUser_ReturnsTrue() {
        Users user = usersRepository.save(UsersCreate.createUser());
        Tasks task = tasksRepository.save(TasksCreate.createTask(user));
        Comments comment = commentsRepository.save(CommentsCreate.createComment(user, task));

        boolean result = commentsRepository.existsByUser_IdAndId(user.getId(), comment.getId());

        assertTrue(result);
    }
}