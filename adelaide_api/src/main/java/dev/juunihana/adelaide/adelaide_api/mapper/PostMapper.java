package dev.juunihana.adelaide.adelaide_api.mapper;

import dev.juunihana.adelaide.adelaide_api.dto.response.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.dto.response.vote.VoteDTO;
import dev.juunihana.adelaide.adelaide_api.entity.PostEntity;
import dev.juunihana.adelaide.adelaide_api.entity.VoteEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {
        UserMapper.class,
        CommentMapper.class,
        VoteEntity.class
    }
)
public abstract class PostMapper {

  @Mappings({
      @Mapping(source = "timeCreated", target = "timeCreated", qualifiedByName = "mapTime"),
      @Mapping(source = "timeEdited", target = "timeEdited", qualifiedByName = "mapTime"),
      @Mapping(source = "votes", target = "rating", qualifiedByName = "mapRating")
  })
  public abstract PostDTO postEntityToDTO(PostEntity post);

  @Named("mapRating")
  protected Long mapRating(Set<VoteEntity> votes) {
    return votes.stream().filter(VoteEntity::isUpVote).count() -
        votes.stream().filter(vote -> !vote.isUpVote()).count();
  }
}
