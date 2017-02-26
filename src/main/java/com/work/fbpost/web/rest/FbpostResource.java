package com.work.fbpost.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.work.fbpost.service.FbpostService;
import com.work.fbpost.web.rest.util.HeaderUtil;
import com.work.fbpost.web.rest.util.PaginationUtil;
import com.work.fbpost.service.dto.FbpostDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Fbpost.
 */
@RestController
@RequestMapping("/api")
public class FbpostResource {

    private final Logger log = LoggerFactory.getLogger(FbpostResource.class);

    private static final String ENTITY_NAME = "fbpost";
        
    private final FbpostService fbpostService;

    public FbpostResource(FbpostService fbpostService) {
        this.fbpostService = fbpostService;
    }

    /**
     * POST  /fbposts : Create a new fbpost.
     *
     * @param fbpostDTO the fbpostDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fbpostDTO, or with status 400 (Bad Request) if the fbpost has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fbposts")
    @Timed
    public ResponseEntity<FbpostDTO> createFbpost(@RequestBody FbpostDTO fbpostDTO) throws URISyntaxException {
        log.debug("REST request to save Fbpost : {}", fbpostDTO);
        
        if ( fbpostDTO.getStatus() == null ) {
        	fbpostDTO.setStatus("NOT_READ");
        }
        
        if (fbpostDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fbpost cannot already have an ID")).body(null);
        }
        FbpostDTO result = fbpostService.save(fbpostDTO);
        return ResponseEntity.created(new URI("/api/fbposts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fbposts : Updates an existing fbpost.
     *
     * @param fbpostDTO the fbpostDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fbpostDTO,
     * or with status 400 (Bad Request) if the fbpostDTO is not valid,
     * or with status 500 (Internal Server Error) if the fbpostDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fbposts")
    @Timed
    public ResponseEntity<FbpostDTO> updateFbpost(@RequestBody FbpostDTO fbpostDTO) throws URISyntaxException {
        log.debug("REST request to update Fbpost : {}", fbpostDTO);
        if (fbpostDTO.getId() == null) {
            return createFbpost(fbpostDTO);
        }
        FbpostDTO result = fbpostService.save(fbpostDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fbpostDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fbposts : get all the fbposts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fbposts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/fbposts")
    @Timed
    public ResponseEntity<List<FbpostDTO>> getAllFbposts(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Fbposts");
        Page<FbpostDTO> page = fbpostService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fbposts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fbposts/:id : get the "id" fbpost.
     *
     * @param id the id of the fbpostDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fbpostDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fbposts/{id}")
    @Timed
    public ResponseEntity<FbpostDTO> getFbpost(@PathVariable Long id) {
        log.debug("REST request to get Fbpost : {}", id);
        FbpostDTO fbpostDTO = fbpostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fbpostDTO));
    }

    /**
     * DELETE  /fbposts/:id : delete the "id" fbpost.
     *
     * @param id the id of the fbpostDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fbposts/{id}")
    @Timed
    public ResponseEntity<Void> deleteFbpost(@PathVariable Long id) {
        log.debug("REST request to delete Fbpost : {}", id);
        fbpostService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
