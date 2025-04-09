package com.br.tasksmanager.repositories;

import com.br.tasksmanager.models.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, UUID> {
    List<Tasks> findAllByUserId(Long id);
    boolean existsByUserIdAndId(Long userId, UUID id);

    @Query(value = """
        SELECT * FROM "task-manager".public.tasks t
        WHERE  t.user_id = :userId
            AND (:name IS NULL OR t.name LIKE CONCAT('%', :name, '%'))
            AND (:description IS NULL OR t.description LIKE CONCAT('%', :description, '%'))
            AND (:priority IS NULL OR t.priority LIKE CONCAT('%', :priority, '%'))
            AND (:status IS NULL OR t.status LIKE CONCAT('%', :status, '%'))""",
            nativeQuery = true)
    List<Tasks> filter(@Param("userId") Long userId,
                       @Param("name") String name,
                       @Param("description") String description,
                       @Param("priority") String priority,
                       @Param("status") String status);
}
