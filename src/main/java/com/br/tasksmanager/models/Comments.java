package com.br.tasksmanager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @JsonIgnore
    private Tasks task;

    @Column(name = "comment_text")
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Comments(Users user, Tasks task, String comment){
        this.user = user;
        this.task = task;
        this.comment = comment;
    }
}
