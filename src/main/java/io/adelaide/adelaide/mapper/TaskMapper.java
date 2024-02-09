package io.adelaide.adelaide.mapper;

import io.adelaide.adelaide.dto.CreateTaskDTO;
import io.adelaide.adelaide.dto.TaskDTO;
import io.adelaide.adelaide.entity.TaskEntity;
import org.mapstruct.Mapper;

@Mapper
public interface TaskMapper {

  TaskDTO taskEntityToDTO(TaskEntity entity);

  TaskEntity taskDTOToEntity(TaskDTO dto);

  TaskEntity taskDTOToEntity(CreateTaskDTO dto);
}
