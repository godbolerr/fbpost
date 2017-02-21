package com.work.fbpost.service.mapper;

import com.work.fbpost.domain.*;
import com.work.fbpost.service.dto.FbpostDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Fbpost and its DTO FbpostDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FbpostMapper {

    FbpostDTO fbpostToFbpostDTO(Fbpost fbpost);

    List<FbpostDTO> fbpostsToFbpostDTOs(List<Fbpost> fbposts);

    Fbpost fbpostDTOToFbpost(FbpostDTO fbpostDTO);

    List<Fbpost> fbpostDTOsToFbposts(List<FbpostDTO> fbpostDTOs);
}
