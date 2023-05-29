package dev.juunihana.adelaide.adelaide_api.mapper;

import dev.juunihana.adelaide.adelaide_api.dto.response.vote.VoteDTO;
import dev.juunihana.adelaide.adelaide_api.entity.VoteEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {
        UserMapper.class
    }
)
public abstract class VoteMapper {

  public abstract VoteDTO voteToDto(VoteEntity voteEntity);
}
