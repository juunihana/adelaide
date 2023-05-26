package dev.juunihana.adelaide.adelaide_api.mapper;

import dev.juunihana.adelaide.adelaide_api.dto.response.post.PostDTO;
import dev.juunihana.adelaide.adelaide_api.entity.PostEntity;
import dev.juunihana.adelaide.adelaide_api.entity.VoteEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        UserMapper.class
    }
)
public abstract class PostMapper {

  @Mappings({
      @Mapping(source = "timeCreated", target = "timeCreated", qualifiedByName = "mapTime"),
      @Mapping(source = "timeEdited", target = "timeEdited", qualifiedByName = "mapTime"),
      @Mapping(source = "author", target = "author"),
      @Mapping(source = "votes", target = "upVotes", qualifiedByName = "mapUpVotes"),
      @Mapping(source = "votes", target = "downVotes", qualifiedByName = "mapDownVotes"),
  })
  public abstract PostDTO postEntityToDTO(PostEntity post);

  @Named("mapTime")
  protected String mapTime(LocalDateTime time) {
    if (time != null) {
      return time.format(DateTimeFormatter.ofPattern("hh:mm dd MMM, yyyy"));
    }
    return "";
  }

  @Named("mapUpVotes")
  protected Integer mapUpVotes(Set<VoteEntity> votes) {
    return Math.toIntExact(votes.stream()
        .filter(VoteEntity::isUpvote)
        .count());
  }

  @Named("mapDownVotes")
  protected Integer mapDownVotes(Set<VoteEntity> votes) {
    return Math.toIntExact(votes.stream()
        .filter(vote -> !vote.isUpvote())
        .count());
  }
}
