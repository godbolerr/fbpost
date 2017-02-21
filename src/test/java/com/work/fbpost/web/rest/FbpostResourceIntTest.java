package com.work.fbpost.web.rest;

import com.work.fbpost.FbpostApp;

import com.work.fbpost.domain.Fbpost;
import com.work.fbpost.repository.FbpostRepository;
import com.work.fbpost.service.FbpostService;
import com.work.fbpost.service.dto.FbpostDTO;
import com.work.fbpost.service.mapper.FbpostMapper;
import com.work.fbpost.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FbpostResource REST controller.
 *
 * @see FbpostResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FbpostApp.class)
public class FbpostResourceIntTest {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAPTION = "AAAAAAAAAA";
    private static final String UPDATED_CAPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_ON = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_ON = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECT_ID = "AAAAAAAAAA";
    private static final String UPDATED_OBJECT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRIVACY = "AAAAAAAAAA";
    private static final String UPDATED_PRIVACY = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private FbpostRepository fbpostRepository;

    @Autowired
    private FbpostMapper fbpostMapper;

    @Autowired
    private FbpostService fbpostService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFbpostMockMvc;

    private Fbpost fbpost;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FbpostResource fbpostResource = new FbpostResource(fbpostService);
        this.restFbpostMockMvc = MockMvcBuilders.standaloneSetup(fbpostResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fbpost createEntity(EntityManager em) {
        Fbpost fbpost = new Fbpost()
                .url(DEFAULT_URL)
                .description(DEFAULT_DESCRIPTION)
                .name(DEFAULT_NAME)
                .caption(DEFAULT_CAPTION)
                .place(DEFAULT_PLACE)
                .createdOn(DEFAULT_CREATED_ON)
                .createdBy(DEFAULT_CREATED_BY)
                .objectId(DEFAULT_OBJECT_ID)
                .privacy(DEFAULT_PRIVACY)
                .status(DEFAULT_STATUS);
        return fbpost;
    }

    @Before
    public void initTest() {
        fbpost = createEntity(em);
    }

    @Test
    @Transactional
    public void createFbpost() throws Exception {
        int databaseSizeBeforeCreate = fbpostRepository.findAll().size();

        // Create the Fbpost
        FbpostDTO fbpostDTO = fbpostMapper.fbpostToFbpostDTO(fbpost);

        restFbpostMockMvc.perform(post("/api/fbposts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fbpostDTO)))
            .andExpect(status().isCreated());

        // Validate the Fbpost in the database
        List<Fbpost> fbpostList = fbpostRepository.findAll();
        assertThat(fbpostList).hasSize(databaseSizeBeforeCreate + 1);
        Fbpost testFbpost = fbpostList.get(fbpostList.size() - 1);
        assertThat(testFbpost.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testFbpost.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFbpost.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFbpost.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testFbpost.getPlace()).isEqualTo(DEFAULT_PLACE);
        assertThat(testFbpost.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testFbpost.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFbpost.getObjectId()).isEqualTo(DEFAULT_OBJECT_ID);
        assertThat(testFbpost.getPrivacy()).isEqualTo(DEFAULT_PRIVACY);
        assertThat(testFbpost.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createFbpostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fbpostRepository.findAll().size();

        // Create the Fbpost with an existing ID
        Fbpost existingFbpost = new Fbpost();
        existingFbpost.setId(1L);
        FbpostDTO existingFbpostDTO = fbpostMapper.fbpostToFbpostDTO(existingFbpost);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFbpostMockMvc.perform(post("/api/fbposts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFbpostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Fbpost> fbpostList = fbpostRepository.findAll();
        assertThat(fbpostList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFbposts() throws Exception {
        // Initialize the database
        fbpostRepository.saveAndFlush(fbpost);

        // Get all the fbpostList
        restFbpostMockMvc.perform(get("/api/fbposts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fbpost.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION.toString())))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].objectId").value(hasItem(DEFAULT_OBJECT_ID.toString())))
            .andExpect(jsonPath("$.[*].privacy").value(hasItem(DEFAULT_PRIVACY.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getFbpost() throws Exception {
        // Initialize the database
        fbpostRepository.saveAndFlush(fbpost);

        // Get the fbpost
        restFbpostMockMvc.perform(get("/api/fbposts/{id}", fbpost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fbpost.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.caption").value(DEFAULT_CAPTION.toString()))
            .andExpect(jsonPath("$.place").value(DEFAULT_PLACE.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.objectId").value(DEFAULT_OBJECT_ID.toString()))
            .andExpect(jsonPath("$.privacy").value(DEFAULT_PRIVACY.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFbpost() throws Exception {
        // Get the fbpost
        restFbpostMockMvc.perform(get("/api/fbposts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFbpost() throws Exception {
        // Initialize the database
        fbpostRepository.saveAndFlush(fbpost);
        int databaseSizeBeforeUpdate = fbpostRepository.findAll().size();

        // Update the fbpost
        Fbpost updatedFbpost = fbpostRepository.findOne(fbpost.getId());
        updatedFbpost
                .url(UPDATED_URL)
                .description(UPDATED_DESCRIPTION)
                .name(UPDATED_NAME)
                .caption(UPDATED_CAPTION)
                .place(UPDATED_PLACE)
                .createdOn(UPDATED_CREATED_ON)
                .createdBy(UPDATED_CREATED_BY)
                .objectId(UPDATED_OBJECT_ID)
                .privacy(UPDATED_PRIVACY)
                .status(UPDATED_STATUS);
        FbpostDTO fbpostDTO = fbpostMapper.fbpostToFbpostDTO(updatedFbpost);

        restFbpostMockMvc.perform(put("/api/fbposts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fbpostDTO)))
            .andExpect(status().isOk());

        // Validate the Fbpost in the database
        List<Fbpost> fbpostList = fbpostRepository.findAll();
        assertThat(fbpostList).hasSize(databaseSizeBeforeUpdate);
        Fbpost testFbpost = fbpostList.get(fbpostList.size() - 1);
        assertThat(testFbpost.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testFbpost.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFbpost.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFbpost.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testFbpost.getPlace()).isEqualTo(UPDATED_PLACE);
        assertThat(testFbpost.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testFbpost.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFbpost.getObjectId()).isEqualTo(UPDATED_OBJECT_ID);
        assertThat(testFbpost.getPrivacy()).isEqualTo(UPDATED_PRIVACY);
        assertThat(testFbpost.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingFbpost() throws Exception {
        int databaseSizeBeforeUpdate = fbpostRepository.findAll().size();

        // Create the Fbpost
        FbpostDTO fbpostDTO = fbpostMapper.fbpostToFbpostDTO(fbpost);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFbpostMockMvc.perform(put("/api/fbposts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fbpostDTO)))
            .andExpect(status().isCreated());

        // Validate the Fbpost in the database
        List<Fbpost> fbpostList = fbpostRepository.findAll();
        assertThat(fbpostList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFbpost() throws Exception {
        // Initialize the database
        fbpostRepository.saveAndFlush(fbpost);
        int databaseSizeBeforeDelete = fbpostRepository.findAll().size();

        // Get the fbpost
        restFbpostMockMvc.perform(delete("/api/fbposts/{id}", fbpost.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fbpost> fbpostList = fbpostRepository.findAll();
        assertThat(fbpostList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fbpost.class);
    }
}
