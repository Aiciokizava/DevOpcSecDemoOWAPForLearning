package com.example.tasktracker.service;

import com.example.tasktracker.dto.CreateTaskRequest;
import com.example.tasktracker.dto.TaskResponse;
import com.example.tasktracker.dto.UpdateTaskRequest;
import com.example.tasktracker.model.Task;
import com.example.tasktracker.model.TaskStatus;
import com.example.tasktracker.repo.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public TaskResponse create(CreateTaskRequest req) {
        Task t = new Task(req.title(), req.description());
        Task saved = repo.save(t);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public TaskResponse get(long id) {
        Task t = repo.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        return toResponse(t);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> list(TaskStatus status) {
        List<Task> tasks = (status == null) ? repo.findAll() : repo.findByStatus(status);
        return tasks.stream().map(this::toResponse).toList();
    }

    @Transactional
    public TaskResponse update(long id, UpdateTaskRequest req) {
        Task t = repo.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        if (req.title() != null) t.setTitle(req.title());
        if (req.description() != null) t.setDescription(req.description());
        if (req.status() != null) t.setStatus(req.status());
        return toResponse(t);
    }

    @Transactional
    public void delete(long id) {
        if (!repo.existsById(id)) throw new TaskNotFoundException(id);
        repo.deleteById(id);
    }

    private TaskResponse toResponse(Task t) {
        return new TaskResponse(t.getId(), t.getTitle(), t.getDescription(), t.getStatus(), t.getCreatedAt());
    }
}
