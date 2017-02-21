package com.work.fbpost.service;

import com.work.fbpost.service.dto.FbpostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Fbpost.
 */
public interface FbpostService {

    /**
     * Save a fbpost.
     *
     * @param fbpostDTO the entity to save
     * @return the persisted entity
     */
    FbpostDTO save(FbpostDTO fbpostDTO);

    /**
     *  Get all the fbposts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FbpostDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" fbpost.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FbpostDTO findOne(Long id);

    /**
     *  Delete the "id" fbpost.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
