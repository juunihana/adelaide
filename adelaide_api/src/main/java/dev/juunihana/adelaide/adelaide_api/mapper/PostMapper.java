package dev.juunihana.adelaide.adelaide_api.mapper;

import dev.juunihana.adelaide.adelaide_api.dto.response.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.entity.PostEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PostMapper {

  @Mappings({
      @Mapping(source = "timeCreated", target = "timeCreated", qualifiedByName = "mapTime"),
      @Mapping(source = "timeEdited", target = "timeEdited", qualifiedByName = "mapTime"),
      @Mapping(source = "author", target = "author")
  })
  PostDTO postEntityToDTO(PostEntity post);

  @Named("mapTime")
  default String mapTime(LocalDateTime time) {
    if (time != null) {
      return time.format(DateTimeFormatter.ofPattern("hh:mm dd MMM, yyyy"));
    }
    return "";
  }
}
