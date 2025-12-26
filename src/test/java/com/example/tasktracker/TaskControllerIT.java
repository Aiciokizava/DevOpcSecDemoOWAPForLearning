package com.example.tasktracker;

import com.example.tasktracker.dto.CreateTaskRequest;
import com.example.tasktracker.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerIT {

    @Autowired
    TestRestTemplate http;

    @Test
    void createTask_thenGetById_returns200() {
        // create
        var req = new CreateTaskRequest("API task", "from test");
        ResponseEntity<String> createResp = http.postForEntity("/api/tasks", req, String.class);

        assertThat(createResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createResp.getBody()).contains("API task");

        // Extract id from JSON (простая проверка без лишних зависимостей)
        // Ожидаем что ответ содержит "id":<number>
        String body = createResp.getBody();
        assertThat(body).contains("\"id\":");

        String idStr = body.replaceAll(".*\"id\"\\s*:\\s*(\\d+).*", "$1");
        long id = Long.parseLong(idStr);

        // get
        ResponseEntity<String> getResp = http.getForEntity("/api/tasks/" + id, String.class);
        assertThat(getResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResp.getBody()).contains("API task");
    }

    @Test
    void listTasks_returns200() {
        ResponseEntity<String> resp = http.getForEntity("/api/tasks", String.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void listTasks_filterByStatus_returns200() {
        ResponseEntity<String> resp = http.getForEntity("/api/tasks?status=" + TaskStatus.TODO, String.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
