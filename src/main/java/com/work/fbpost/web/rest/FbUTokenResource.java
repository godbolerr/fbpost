package com.work.fbpost.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.work.fbpost.service.FbUTokenService;
import com.work.fbpost.web.rest.util.HeaderUtil;
import com.work.fbpost.service.dto.FbUTokenDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing FbUToken.
 */
@RestController
@RequestMapping("/api")
public class FbUTokenResource {

    private final Logger log = LoggerFactory.getLogger(FbUTokenResource.class);

    private static final String ENTITY_NAME = "fbUToken";
        
    private final FbUTokenService fbUTokenService;

    public FbUTokenResource(FbUTokenService fbUTokenService) {
        this.fbUTokenService = fbUTokenService;
    }

    /**
     * POST  /fb-u-tokens : Create a new fbUToken.
     *
     * @param fbUTokenDTO the fbUTokenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fbUTokenDTO, or with status 400 (Bad Request) if the fbUToken has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fb-u-tokens")
    @Timed
    public ResponseEntity<FbUTokenDTO> createFbUToken(@RequestBody FbUTokenDTO fbUTokenDTO) throws URISyntaxException {
        log.debug("REST request to save FbUToken : {}", fbUTokenDTO);
        if (fbUTokenDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fbUToken cannot already have an ID")).body(null);
        }
        FbUTokenDTO result = fbUTokenService.save(fbUTokenDTO);
        return ResponseEntity.created(new URI("/api/fb-u-tokens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fb-u-tokens : Updates an existing fbUToken.
     *
     * @param fbUTokenDTO the fbUTokenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fbUTokenDTO,
     * or with status 400 (Bad Request) if the fbUTokenDTO is not valid,
     * or with status 500 (Internal Server Error) if the fbUTokenDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fb-u-tokens")
    @Timed
    public ResponseEntity<FbUTokenDTO> updateFbUToken(@RequestBody FbUTokenDTO fbUTokenDTO) throws URISyntaxException {
        log.debug("REST request to update FbUToken : {}", fbUTokenDTO);
        if (fbUTokenDTO.getId() == null) {
            return createFbUToken(fbUTokenDTO);
        }
        FbUTokenDTO result = fbUTokenService.save(fbUTokenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fbUTokenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fb-u-tokens : get all the fbUTokens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fbUTokens in body
     */
    @GetMapping("/fb-u-tokens")
    @Timed
    public List<FbUTokenDTO> getAllFbUTokens() {
        log.debug("REST request to get all FbUTokens");
        return fbUTokenService.findAll();
    }

    /**
     * GET  /fb-u-tokens/:id : get the "id" fbUToken.
     *
     * @param id the id of the fbUTokenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fbUTokenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fb-u-tokens/{id}")
    @Timed
    public ResponseEntity<FbUTokenDTO> getFbUToken(@PathVariable Long id) {
        log.debug("REST request to get FbUToken : {}", id);
        FbUTokenDTO fbUTokenDTO = fbUTokenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fbUTokenDTO));
    }

    /**
     * DELETE  /fb-u-tokens/:id : delete the "id" fbUToken.
     *
     * @param id the id of the fbUTokenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fb-u-tokens/{id}")
    @Timed
    public ResponseEntity<Void> deleteFbUToken(@PathVariable Long id) {
        log.debug("REST request to delete FbUToken : {}", id);
        fbUTokenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
