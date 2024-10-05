package dev.juunihana.adelaide.mapper;

import dev.juunihana.adelaide.dto.user.CreateUserDto;
import dev.juunihana.adelaide.dto.user.UpdateUserDto;
import dev.juunihana.adelaide.dto.user.UserFullDto;
import dev.juunihana.adelaide.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

  UserFullDto userEntityToDtoFull(UserEntity entity);

  UserEntity createUserToEntity(CreateUserDto dto);

  UserEntity updateUserToEntity(UpdateUserDto dto);
}
