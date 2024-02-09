package io.adelaide.adelaide.service;

import io.adelaide.adelaide.dto.TaskDTO;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface TaskService {

  TaskDTO findById(String id);

  List<TaskDTO> findAll();

  TaskDTO create(TaskDTO dto);

  TaskDTO update(String id, TaskDTO dto);

  void delete(String id);
}
