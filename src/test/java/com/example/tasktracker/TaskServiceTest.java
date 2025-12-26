package com.example.tasktracker;

import com.example.tasktracker.dto.CreateTaskRequest;
import com.example.tasktracker.model.TaskStatus;
import com.example.tasktracker.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TaskServiceTest {

    @Autowired
    TaskService service;

    @Test
    void createAndGet() {
        var created = service.create(new CreateTaskRequest("My task", "desc"));
        var fetched = service.get(created.id());

        assertThat(fetched.title()).isEqualTo("My task");
        assertThat(fetched.description()).isEqualTo("desc");
        assertThat(fetched.status()).isEqualTo(TaskStatus.TODO);
        assertThat(fetched.createdAt()).isNotNull();
    }
}
