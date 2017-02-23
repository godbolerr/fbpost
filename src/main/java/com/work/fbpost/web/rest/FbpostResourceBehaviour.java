package com.work.fbpost.web.rest;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.work.fbpost.service.FbpostService;
import com.work.fbpost.service.dto.FbpostDTO;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Fbpost.
 */
@RestController
@RequestMapping("/api")
public class FbpostResourceBehaviour {

    private final Logger log = LoggerFactory.getLogger(FbpostResourceBehaviour.class);

    private static final String ENTITY_NAME = "fbpost";
        
    private final FbpostService fbpostService;

    public FbpostResourceBehaviour(FbpostService fbpostService) {
        this.fbpostService = fbpostService;
    }


    
    @GetMapping("/fbposts/getNext")
    @Timed
    public ResponseEntity<FbpostDTO> getNextFbpost() {
        FbpostDTO fbpostDTO = fbpostService.getNextPost();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fbpostDTO));
    }
    
    @GetMapping("/fbposts/updateObjectId/{id}/{objectId}")
    @Timed
    public ResponseEntity<FbpostDTO> updateObjectId(@PathVariable("id") Long id,@PathVariable("objectId") String objectId) {
        FbpostDTO fbpostDTO = fbpostService.updateObjectId(id, objectId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fbpostDTO));
    }    
    


}
