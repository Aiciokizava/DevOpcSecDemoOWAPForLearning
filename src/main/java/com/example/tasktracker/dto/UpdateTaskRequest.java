package com.example.tasktracker.dto;

import com.example.tasktracker.model.TaskStatus;
import jakarta.validation.constraints.Size;

public record UpdateTaskRequest(
        @Size(min = 3, max = 120) String title,
        @Size(max = 1000) String description,
        TaskStatus status
) {}
