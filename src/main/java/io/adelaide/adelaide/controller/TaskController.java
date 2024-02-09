package io.adelaide.adelaide.controller;

import io.adelaide.adelaide.dto.CreateTaskDTO;
import io.adelaide.adelaide.dto.TaskDTO;
import io.adelaide.adelaide.entity.TaskEntity;
import io.adelaide.adelaide.repository.TaskRepository;
import io.adelaide.adelaide.service.TaskService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;

  @GetMapping("/{id}")
  public TaskDTO getById(@PathVariable String id) {
    return taskService.findById(id);
  }

  @GetMapping("/all")
  public List<TaskEntity> getAll() {
    return null;
  }

  @PostMapping("/")
  public String create(@RequestBody CreateTaskDTO dto) {
    return null;
  }
}
