package io.adelaide.adelaide.service.impl;

import io.adelaide.adelaide.dto.TaskDTO;
import io.adelaide.adelaide.exception.NotFoundException;
import io.adelaide.adelaide.mapper.TaskMapper;
import io.adelaide.adelaide.repository.TaskRepository;
import io.adelaide.adelaide.service.TaskService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

  private final TaskRepository taskRepository;

  @Override
  public TaskDTO findById(String id) {
    return taskMapper.taskEntityToDTO(taskRepository.findById(UUID.fromString(id))
        .orElseThrow(NotFoundException::new));
  }

  @Override
  public List<TaskDTO> findAll() {
    return null;
  }

  @Override
  public TaskDTO create(TaskDTO dto) {
    return null;
  }

  @Override
  public TaskDTO update(String id, TaskDTO dto) {
    return null;
  }

  @Override
  public void delete(String id) {

  }
}
