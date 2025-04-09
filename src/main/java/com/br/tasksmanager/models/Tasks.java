package com.br.tasksmanager.models;

import com.br.tasksmanager.Enums.TaskPriority;
import com.br.tasksmanager.Enums.TaskStatus;
import com.br.tasksmanager.dtos.Tasks.TaskRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user;

    @Column(name = "name")
    private String nameTask;

    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    public Tasks(Users user, TaskRequestDto requestDto){
        this.user = user;
        this.nameTask = requestDto.nameTask();
        this.description = requestDto.description();
        this.priority = requestDto.priority();
        this.status = requestDto.status();
        this.dueDate = requestDto.dueDate();
    }

}
