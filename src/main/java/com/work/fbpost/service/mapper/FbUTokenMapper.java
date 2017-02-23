package com.work.fbpost.service.mapper;

import com.work.fbpost.domain.*;
import com.work.fbpost.service.dto.FbUTokenDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity FbUToken and its DTO FbUTokenDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FbUTokenMapper {

    FbUTokenDTO fbUTokenToFbUTokenDTO(FbUToken fbUToken);

    List<FbUTokenDTO> fbUTokensToFbUTokenDTOs(List<FbUToken> fbUTokens);

    FbUToken fbUTokenDTOToFbUToken(FbUTokenDTO fbUTokenDTO);

    List<FbUToken> fbUTokenDTOsToFbUTokens(List<FbUTokenDTO> fbUTokenDTOs);
}
