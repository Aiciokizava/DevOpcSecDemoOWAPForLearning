package com.example.tasktracker.dto;

import com.example.tasktracker.model.TaskStatus;

import java.time.Instant;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        Instant createdAt
) {}
