package dev.juunihana.adelaide.adelaide_api.mapper;

import dev.juunihana.adelaide.adelaide_api.dto.response.comment.CommentDTO;
import dev.juunihana.adelaide.adelaide_api.entity.CommentEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {
        UserMapper.class
    }
)
public abstract class CommentMapper {

  @Mappings(
      @Mapping(source = "timeCreated", target = "timeCreated", qualifiedByName = "mapTime")
  )
  public abstract CommentDTO commentToDto(CommentEntity comment);

  @Named("mapTime")
  protected String mapTime(LocalDateTime time) {
    if (time != null) {
      return time.format(DateTimeFormatter.ofPattern("hh:mm dd MMM, yyyy", Locale.ENGLISH));
    }
    return "";
  }
}
