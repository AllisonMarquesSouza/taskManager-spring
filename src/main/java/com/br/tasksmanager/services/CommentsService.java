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
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final UsersRepository usersRepository;
    private final TasksRepository tasksRepository;

    public List<Comments> findAllByTaskId(UUID taskId){
        return commentsRepository.findAllByTask_Id(taskId);
    }
    public List<Comments> findAllByUserId(Long userId){
        return commentsRepository.findAllByUser_Id(userId);
    }

    @Transactional
    public Comments create(CommentRequestDto commentDto){
        Users user = usersRepository.findById(commentDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Tasks task = tasksRepository.findById(commentDto.taskId())
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if(!tasksRepository.existsByUserIdAndId(commentDto.userId(), commentDto.taskId())){
            throw new BadRequestException("There is no user with such task");
        }

        Comments comment = new Comments(user, task, commentDto.comment());
        comment.setCreatedAt(LocalDateTime.now());
        return commentsRepository.save(comment);
    }

    @Transactional
    public void update(Long userId, UUID idComment, CommentUpdateDto commentDto){
        Comments commentToUpdate = commentsRepository.findById(idComment)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        if(commentsRepository.existsByUser_IdAndId(userId, idComment)){
            commentToUpdate.setComment(commentDto.comment());
            commentsRepository.save(commentToUpdate);
            return;
        }
        throw new BadRequestException("There is no such comment for this user, check the user id and comment id");
    }

    @Transactional
    public void deleteById(Long userId, UUID idComment){
        commentsRepository.findById(idComment)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        if(commentsRepository.existsByUser_IdAndId(userId, idComment)){
            commentsRepository.deleteById(idComment);
            return;
        }
        throw new BadRequestException("There is no such comment for this user, check the user id and comment id");
    }
}
