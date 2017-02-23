package com.work.fbpost.service.impl;

import com.work.fbpost.service.FbUTokenService;
import com.work.fbpost.domain.FbUToken;
import com.work.fbpost.repository.FbUTokenRepository;
import com.work.fbpost.service.dto.FbUTokenDTO;
import com.work.fbpost.service.mapper.FbUTokenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing FbUToken.
 */
@Service
@Transactional
public class FbUTokenServiceImpl implements FbUTokenService{

    private final Logger log = LoggerFactory.getLogger(FbUTokenServiceImpl.class);
    
    private final FbUTokenRepository fbUTokenRepository;

    private final FbUTokenMapper fbUTokenMapper;

    public FbUTokenServiceImpl(FbUTokenRepository fbUTokenRepository, FbUTokenMapper fbUTokenMapper) {
        this.fbUTokenRepository = fbUTokenRepository;
        this.fbUTokenMapper = fbUTokenMapper;
    }

    /**
     * Save a fbUToken.
     *
     * @param fbUTokenDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FbUTokenDTO save(FbUTokenDTO fbUTokenDTO) {
        log.debug("Request to save FbUToken : {}", fbUTokenDTO);
        FbUToken fbUToken = fbUTokenMapper.fbUTokenDTOToFbUToken(fbUTokenDTO);
        fbUToken = fbUTokenRepository.save(fbUToken);
        FbUTokenDTO result = fbUTokenMapper.fbUTokenToFbUTokenDTO(fbUToken);
        return result;
    }

    /**
     *  Get all the fbUTokens.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<FbUTokenDTO> findAll() {
        log.debug("Request to get all FbUTokens");
        List<FbUTokenDTO> result = fbUTokenRepository.findAll().stream()
            .map(fbUTokenMapper::fbUTokenToFbUTokenDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one fbUToken by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FbUTokenDTO findOne(Long id) {
        log.debug("Request to get FbUToken : {}", id);
        FbUToken fbUToken = fbUTokenRepository.findOne(id);
        FbUTokenDTO fbUTokenDTO = fbUTokenMapper.fbUTokenToFbUTokenDTO(fbUToken);
        return fbUTokenDTO;
    }

    /**
     *  Delete the  fbUToken by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FbUToken : {}", id);
        fbUTokenRepository.delete(id);
    }
}
