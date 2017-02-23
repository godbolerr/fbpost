package com.work.fbpost.web.rest;

import com.work.fbpost.FbpostApp;

import com.work.fbpost.domain.FbUToken;
import com.work.fbpost.repository.FbUTokenRepository;
import com.work.fbpost.service.FbUTokenService;
import com.work.fbpost.service.dto.FbUTokenDTO;
import com.work.fbpost.service.mapper.FbUTokenMapper;
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
 * Test class for the FbUTokenResource REST controller.
 *
 * @see FbUTokenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FbpostApp.class)
public class FbUTokenResourceIntTest {

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    private static final String DEFAULT_U_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_U_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_ON = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_ON = "BBBBBBBBBB";

    @Autowired
    private FbUTokenRepository fbUTokenRepository;

    @Autowired
    private FbUTokenMapper fbUTokenMapper;

    @Autowired
    private FbUTokenService fbUTokenService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFbUTokenMockMvc;

    private FbUToken fbUToken;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FbUTokenResource fbUTokenResource = new FbUTokenResource(fbUTokenService);
        this.restFbUTokenMockMvc = MockMvcBuilders.standaloneSetup(fbUTokenResource)
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
    public static FbUToken createEntity(EntityManager em) {
        FbUToken fbUToken = new FbUToken()
                .userid(DEFAULT_USERID)
                .uToken(DEFAULT_U_TOKEN)
                .createdOn(DEFAULT_CREATED_ON);
        return fbUToken;
    }

    @Before
    public void initTest() {
        fbUToken = createEntity(em);
    }

    @Test
    @Transactional
    public void createFbUToken() throws Exception {
        int databaseSizeBeforeCreate = fbUTokenRepository.findAll().size();

        // Create the FbUToken
        FbUTokenDTO fbUTokenDTO = fbUTokenMapper.fbUTokenToFbUTokenDTO(fbUToken);

        restFbUTokenMockMvc.perform(post("/api/fb-u-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fbUTokenDTO)))
            .andExpect(status().isCreated());

        // Validate the FbUToken in the database
        List<FbUToken> fbUTokenList = fbUTokenRepository.findAll();
        assertThat(fbUTokenList).hasSize(databaseSizeBeforeCreate + 1);
        FbUToken testFbUToken = fbUTokenList.get(fbUTokenList.size() - 1);
        assertThat(testFbUToken.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testFbUToken.getuToken()).isEqualTo(DEFAULT_U_TOKEN);
        assertThat(testFbUToken.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    public void createFbUTokenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fbUTokenRepository.findAll().size();

        // Create the FbUToken with an existing ID
        FbUToken existingFbUToken = new FbUToken();
        existingFbUToken.setId(1L);
        FbUTokenDTO existingFbUTokenDTO = fbUTokenMapper.fbUTokenToFbUTokenDTO(existingFbUToken);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFbUTokenMockMvc.perform(post("/api/fb-u-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFbUTokenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<FbUToken> fbUTokenList = fbUTokenRepository.findAll();
        assertThat(fbUTokenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFbUTokens() throws Exception {
        // Initialize the database
        fbUTokenRepository.saveAndFlush(fbUToken);

        // Get all the fbUTokenList
        restFbUTokenMockMvc.perform(get("/api/fb-u-tokens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fbUToken.getId().intValue())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.toString())))
            .andExpect(jsonPath("$.[*].uToken").value(hasItem(DEFAULT_U_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }

    @Test
    @Transactional
    public void getFbUToken() throws Exception {
        // Initialize the database
        fbUTokenRepository.saveAndFlush(fbUToken);

        // Get the fbUToken
        restFbUTokenMockMvc.perform(get("/api/fb-u-tokens/{id}", fbUToken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fbUToken.getId().intValue()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID.toString()))
            .andExpect(jsonPath("$.uToken").value(DEFAULT_U_TOKEN.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFbUToken() throws Exception {
        // Get the fbUToken
        restFbUTokenMockMvc.perform(get("/api/fb-u-tokens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFbUToken() throws Exception {
        // Initialize the database
        fbUTokenRepository.saveAndFlush(fbUToken);
        int databaseSizeBeforeUpdate = fbUTokenRepository.findAll().size();

        // Update the fbUToken
        FbUToken updatedFbUToken = fbUTokenRepository.findOne(fbUToken.getId());
        updatedFbUToken
                .userid(UPDATED_USERID)
                .uToken(UPDATED_U_TOKEN)
                .createdOn(UPDATED_CREATED_ON);
        FbUTokenDTO fbUTokenDTO = fbUTokenMapper.fbUTokenToFbUTokenDTO(updatedFbUToken);

        restFbUTokenMockMvc.perform(put("/api/fb-u-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fbUTokenDTO)))
            .andExpect(status().isOk());

        // Validate the FbUToken in the database
        List<FbUToken> fbUTokenList = fbUTokenRepository.findAll();
        assertThat(fbUTokenList).hasSize(databaseSizeBeforeUpdate);
        FbUToken testFbUToken = fbUTokenList.get(fbUTokenList.size() - 1);
        assertThat(testFbUToken.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testFbUToken.getuToken()).isEqualTo(UPDATED_U_TOKEN);
        assertThat(testFbUToken.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingFbUToken() throws Exception {
        int databaseSizeBeforeUpdate = fbUTokenRepository.findAll().size();

        // Create the FbUToken
        FbUTokenDTO fbUTokenDTO = fbUTokenMapper.fbUTokenToFbUTokenDTO(fbUToken);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFbUTokenMockMvc.perform(put("/api/fb-u-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fbUTokenDTO)))
            .andExpect(status().isCreated());

        // Validate the FbUToken in the database
        List<FbUToken> fbUTokenList = fbUTokenRepository.findAll();
        assertThat(fbUTokenList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFbUToken() throws Exception {
        // Initialize the database
        fbUTokenRepository.saveAndFlush(fbUToken);
        int databaseSizeBeforeDelete = fbUTokenRepository.findAll().size();

        // Get the fbUToken
        restFbUTokenMockMvc.perform(delete("/api/fb-u-tokens/{id}", fbUToken.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FbUToken> fbUTokenList = fbUTokenRepository.findAll();
        assertThat(fbUTokenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FbUToken.class);
    }
}
