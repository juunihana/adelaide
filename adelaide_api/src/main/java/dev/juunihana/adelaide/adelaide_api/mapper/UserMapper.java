package dev.juunihana.adelaide.adelaide_api.mapper;

import dev.juunihana.adelaide.adelaide_api.configuration.CdnProperties;
import dev.juunihana.adelaide.adelaide_api.dto.request.user.CreateUserProfileDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserCompactDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.user.UserFullDTO;
import dev.juunihana.adelaide.adelaide_api.entity.UserEntity;
import java.time.LocalDate;
import java.time.Period;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public abstract class UserMapper {

  @Autowired
  protected CdnProperties cdnProperties;

  @Mappings({
      @Mapping(target = "age", source = "dateOfBirth", qualifiedByName = "mapUserAge"),
      @Mapping(target = "avatar", source = "avatar", qualifiedByName = "mapAvatar")
  })
  public abstract UserFullDTO userEntityToProfile(UserEntity userEntity);

  @Named("mapUserAge")
  public Integer mapUserAge(LocalDate dateOfBirth) {
    return Period.between(dateOfBirth, LocalDate.now()).getYears();
  }

  @Named("mapAvatar")
  public String mapAvatar(String avatarEntity) {
    if (StringUtils.hasLength(avatarEntity)) {
      return cdnProperties.getUrl() + avatarEntity;
    }
    return "";
  }

  public abstract UserEntity createUserToEntity(CreateUserProfileDTO dto);

  @Mappings({
      @Mapping(target = "avatar", source = "avatar", qualifiedByName = "mapAvatar")
  })
  public abstract UserCompactDTO userToShortProfile(UserEntity userEntity);
}
