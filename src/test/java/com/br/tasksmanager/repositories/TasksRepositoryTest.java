package com.br.tasksmanager.repositories;

import com.br.tasksmanager.Enums.TaskPriority;
import com.br.tasksmanager.Enums.TaskStatus;
import com.br.tasksmanager.models.Tasks;
import com.br.tasksmanager.models.Users;
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
class TasksRepositoryTest {
    @Autowired
    private TasksRepository tasksRepository;
    @Autowired
    private UsersRepository usersRepository;


    @AfterEach
    void tearDown() {
        tasksRepository.deleteAll();
        usersRepository.deleteAll();
    }

    @Test
    void findAllByUserId_ExistingTasks_ReturnsTasks() {
        Users user = usersRepository.save(UsersCreate.createUser());
        Tasks task = tasksRepository.save(TasksCreate.createTask(user));
        Tasks taskTwo = tasksRepository.save(TasksCreate.createTask2(user));

        List<Tasks> tasksByUserId = tasksRepository.findAllByUserId(user.getId());

        assertNotNull(tasksByUserId);
        assertEquals(2, tasksByUserId.size());
        assertEquals(tasksByUserId.getFirst(), task);
        assertEquals(tasksByUserId.getLast(), taskTwo);

    }

    @Test
    void filter_ExistingTasksByFilterPassed_ReturnsTaskFiltered() {
        Users user = usersRepository.save(UsersCreate.createUser());
        Tasks taskOne = tasksRepository.save(TasksCreate.createTask(user));
        Tasks taskTwo = tasksRepository.save(TasksCreate.createTask2(user));

        List<Tasks> result1 = tasksRepository.
                filterForH2test(user.getId(), "Study english", null,
                        TaskPriority.HIGH.name(), TaskStatus.COMPLETED.name());

        List<Tasks> result2 = tasksRepository
                .filterForH2test(user.getId(), "Learn Docker", null,
                        TaskPriority.MEDIUM.name(), TaskStatus.IN_PROGRESS.name());

        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals(1, result1.size());
        assertEquals(1, result2.size());
        assertEquals(result1.getFirst(), taskOne);
        assertEquals(result2.getFirst(), taskTwo);
    }

    @Test
    void existsByUserIdAndIdTask_WhenUserBelongsToTask_ReturnsTrue() {
        Users user = usersRepository.save(UsersCreate.createUser());
        Tasks task = tasksRepository.save(TasksCreate.createTask(user));

        boolean exists = tasksRepository.existsByUserIdAndId(user.getId(), task.getId());

        assertTrue(exists);
    }


}