package com.work.fbpost.service.impl;

import com.work.fbpost.service.FbpostService;
import com.work.fbpost.domain.Fbpost;
import com.work.fbpost.repository.FbpostRepository;
import com.work.fbpost.service.dto.FbpostDTO;
import com.work.fbpost.service.mapper.FbpostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Fbpost.
 */
@Service
@Transactional
public class FbpostServiceImpl implements FbpostService{

    private final Logger log = LoggerFactory.getLogger(FbpostServiceImpl.class);
    
    private final FbpostRepository fbpostRepository;

    private final FbpostMapper fbpostMapper;

    public FbpostServiceImpl(FbpostRepository fbpostRepository, FbpostMapper fbpostMapper) {
        this.fbpostRepository = fbpostRepository;
        this.fbpostMapper = fbpostMapper;
    }

    /**
     * Save a fbpost.
     *
     * @param fbpostDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FbpostDTO save(FbpostDTO fbpostDTO) {
        log.debug("Request to save Fbpost : {}", fbpostDTO);
        Fbpost fbpost = fbpostMapper.fbpostDTOToFbpost(fbpostDTO);
        fbpost = fbpostRepository.save(fbpost);
        FbpostDTO result = fbpostMapper.fbpostToFbpostDTO(fbpost);
        return result;
    }

    /**
     *  Get all the fbposts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FbpostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fbposts");
        Page<Fbpost> result = fbpostRepository.findAll(pageable);
        return result.map(fbpost -> fbpostMapper.fbpostToFbpostDTO(fbpost));
    }

    /**
     *  Get one fbpost by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FbpostDTO findOne(Long id) {
        log.debug("Request to get Fbpost : {}", id);
        Fbpost fbpost = fbpostRepository.findOne(id);
        FbpostDTO fbpostDTO = fbpostMapper.fbpostToFbpostDTO(fbpost);
        return fbpostDTO;
    }

    /**
     *  Delete the  fbpost by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fbpost : {}", id);
        fbpostRepository.delete(id);
    }
}
