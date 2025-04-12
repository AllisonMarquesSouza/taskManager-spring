package com.br.tasksmanager.repositories;

import com.br.tasksmanager.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, UUID> {
    List<Comments> findAllByTask_Id(UUID taskId);
    List<Comments> findAllByUser_Id(Long userId);
    boolean existsByUser_IdAndId(Long userId, UUID commentId);
}
