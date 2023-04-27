package dev.juunihana.adelaide.adelaide_api.mapper;

import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.entity.UserEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserMapper {

  @Mappings({
      @Mapping(target = "age", source = "dateOfBirth", qualifiedByName = "mapUserAge"),
      @Mapping(target = "info", source = "information")
  })
  UserProfileDTO userEntityToProfile(UserEntity userEntity);

  @Named("mapUserAge")
  default Integer mapUserAge(LocalDate dateOfBirth) {
    return Period.between(dateOfBirth, LocalDate.now()).getYears();
  }

  @Mappings({
      @Mapping(target = "information", source = "info")
  })
  UserEntity createUserToEntity(CreateUserProfileDTO dto);
}
