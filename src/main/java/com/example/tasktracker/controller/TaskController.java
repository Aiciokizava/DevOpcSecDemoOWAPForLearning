package com.example.tasktracker.controller;

import com.example.tasktracker.dto.CreateTaskRequest;
import com.example.tasktracker.dto.TaskResponse;
import com.example.tasktracker.dto.UpdateTaskRequest;
import com.example.tasktracker.model.TaskStatus;
import com.example.tasktracker.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@Valid @RequestBody CreateTaskRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public TaskResponse get(@PathVariable("id") long id) {
        return service.get(id);
    }

    @GetMapping
    public List<TaskResponse> list(@RequestParam(name = "status", required = false) TaskStatus status) {
        return service.list(status);
    }

    @PatchMapping("/{id}")
    public TaskResponse update(@PathVariable("id") long id, @Valid @RequestBody UpdateTaskRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }
}
