package com.br.tasksmanager.util;

import com.br.tasksmanager.models.Comments;
import com.br.tasksmanager.models.Tasks;
import com.br.tasksmanager.models.Users;


import java.time.LocalDateTime;
public class CommentsCreate {
    public static Comments createComment(Users users, Tasks tasks){
        return Comments.builder()
                .user(users)
                .task(tasks)
                .comment("Comment 1")
                .createdAt(LocalDateTime.now())
                .build();
    }
    public static Comments createComment2(Users users, Tasks tasks){
        return Comments.builder()
                .user(users)
                .task(tasks)
                .comment("Comment 2")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
