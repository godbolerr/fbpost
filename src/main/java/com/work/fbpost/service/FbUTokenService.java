package com.work.fbpost.service;

import com.work.fbpost.service.dto.FbUTokenDTO;
import java.util.List;

/**
 * Service Interface for managing FbUToken.
 */
public interface FbUTokenService {

    /**
     * Save a fbUToken.
     *
     * @param fbUTokenDTO the entity to save
     * @return the persisted entity
     */
    FbUTokenDTO save(FbUTokenDTO fbUTokenDTO);

    /**
     *  Get all the fbUTokens.
     *  
     *  @return the list of entities
     */
    List<FbUTokenDTO> findAll();

    /**
     *  Get the "id" fbUToken.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FbUTokenDTO findOne(Long id);

    /**
     *  Delete the "id" fbUToken.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
