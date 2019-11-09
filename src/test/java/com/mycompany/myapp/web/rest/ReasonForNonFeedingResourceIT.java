package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.WebApp;
import com.mycompany.myapp.domain.ReasonForNonFeeding;
import com.mycompany.myapp.repository.ReasonForNonFeedingRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReasonForNonFeedingResource} REST controller.
 */
@SpringBootTest(classes = WebApp.class)
public class ReasonForNonFeedingResourceIT {

    private static final Instant DEFAULT_DATE_OF_NON_FEEDING = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_NON_FEEDING = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REASON_FOR_NON_FEEDING = "AAAAAAAAAA";
    private static final String UPDATED_REASON_FOR_NON_FEEDING = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY_USER_ID = 1L;
    private static final Long UPDATED_CREATED_BY_USER_ID = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ReasonForNonFeedingRepository reasonForNonFeedingRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restReasonForNonFeedingMockMvc;

    private ReasonForNonFeeding reasonForNonFeeding;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReasonForNonFeedingResource reasonForNonFeedingResource = new ReasonForNonFeedingResource(reasonForNonFeedingRepository);
        this.restReasonForNonFeedingMockMvc = MockMvcBuilders.standaloneSetup(reasonForNonFeedingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReasonForNonFeeding createEntity(EntityManager em) {
        ReasonForNonFeeding reasonForNonFeeding = new ReasonForNonFeeding()
            .dateOfNonFeeding(DEFAULT_DATE_OF_NON_FEEDING)
            .reasonForNonFeeding(DEFAULT_REASON_FOR_NON_FEEDING)
            .createdByUserId(DEFAULT_CREATED_BY_USER_ID)
            .createdDate(DEFAULT_CREATED_DATE);
        return reasonForNonFeeding;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReasonForNonFeeding createUpdatedEntity(EntityManager em) {
        ReasonForNonFeeding reasonForNonFeeding = new ReasonForNonFeeding()
            .dateOfNonFeeding(UPDATED_DATE_OF_NON_FEEDING)
            .reasonForNonFeeding(UPDATED_REASON_FOR_NON_FEEDING)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);
        return reasonForNonFeeding;
    }

    @BeforeEach
    public void initTest() {
        reasonForNonFeeding = createEntity(em);
    }

    @Test
    @Transactional
    public void createReasonForNonFeeding() throws Exception {
        int databaseSizeBeforeCreate = reasonForNonFeedingRepository.findAll().size();

        // Create the ReasonForNonFeeding
        restReasonForNonFeedingMockMvc.perform(post("/api/reason-for-non-feedings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasonForNonFeeding)))
            .andExpect(status().isCreated());

        // Validate the ReasonForNonFeeding in the database
        List<ReasonForNonFeeding> reasonForNonFeedingList = reasonForNonFeedingRepository.findAll();
        assertThat(reasonForNonFeedingList).hasSize(databaseSizeBeforeCreate + 1);
        ReasonForNonFeeding testReasonForNonFeeding = reasonForNonFeedingList.get(reasonForNonFeedingList.size() - 1);
        assertThat(testReasonForNonFeeding.getDateOfNonFeeding()).isEqualTo(DEFAULT_DATE_OF_NON_FEEDING);
        assertThat(testReasonForNonFeeding.getReasonForNonFeeding()).isEqualTo(DEFAULT_REASON_FOR_NON_FEEDING);
        assertThat(testReasonForNonFeeding.getCreatedByUserId()).isEqualTo(DEFAULT_CREATED_BY_USER_ID);
        assertThat(testReasonForNonFeeding.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createReasonForNonFeedingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reasonForNonFeedingRepository.findAll().size();

        // Create the ReasonForNonFeeding with an existing ID
        reasonForNonFeeding.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReasonForNonFeedingMockMvc.perform(post("/api/reason-for-non-feedings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasonForNonFeeding)))
            .andExpect(status().isBadRequest());

        // Validate the ReasonForNonFeeding in the database
        List<ReasonForNonFeeding> reasonForNonFeedingList = reasonForNonFeedingRepository.findAll();
        assertThat(reasonForNonFeedingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReasonForNonFeedings() throws Exception {
        // Initialize the database
        reasonForNonFeedingRepository.saveAndFlush(reasonForNonFeeding);

        // Get all the reasonForNonFeedingList
        restReasonForNonFeedingMockMvc.perform(get("/api/reason-for-non-feedings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reasonForNonFeeding.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateOfNonFeeding").value(hasItem(DEFAULT_DATE_OF_NON_FEEDING.toString())))
            .andExpect(jsonPath("$.[*].reasonForNonFeeding").value(hasItem(DEFAULT_REASON_FOR_NON_FEEDING)))
            .andExpect(jsonPath("$.[*].createdByUserId").value(hasItem(DEFAULT_CREATED_BY_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getReasonForNonFeeding() throws Exception {
        // Initialize the database
        reasonForNonFeedingRepository.saveAndFlush(reasonForNonFeeding);

        // Get the reasonForNonFeeding
        restReasonForNonFeedingMockMvc.perform(get("/api/reason-for-non-feedings/{id}", reasonForNonFeeding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reasonForNonFeeding.getId().intValue()))
            .andExpect(jsonPath("$.dateOfNonFeeding").value(DEFAULT_DATE_OF_NON_FEEDING.toString()))
            .andExpect(jsonPath("$.reasonForNonFeeding").value(DEFAULT_REASON_FOR_NON_FEEDING))
            .andExpect(jsonPath("$.createdByUserId").value(DEFAULT_CREATED_BY_USER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReasonForNonFeeding() throws Exception {
        // Get the reasonForNonFeeding
        restReasonForNonFeedingMockMvc.perform(get("/api/reason-for-non-feedings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReasonForNonFeeding() throws Exception {
        // Initialize the database
        reasonForNonFeedingRepository.saveAndFlush(reasonForNonFeeding);

        int databaseSizeBeforeUpdate = reasonForNonFeedingRepository.findAll().size();

        // Update the reasonForNonFeeding
        ReasonForNonFeeding updatedReasonForNonFeeding = reasonForNonFeedingRepository.findById(reasonForNonFeeding.getId()).get();
        // Disconnect from session so that the updates on updatedReasonForNonFeeding are not directly saved in db
        em.detach(updatedReasonForNonFeeding);
        updatedReasonForNonFeeding
            .dateOfNonFeeding(UPDATED_DATE_OF_NON_FEEDING)
            .reasonForNonFeeding(UPDATED_REASON_FOR_NON_FEEDING)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);

        restReasonForNonFeedingMockMvc.perform(put("/api/reason-for-non-feedings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReasonForNonFeeding)))
            .andExpect(status().isOk());

        // Validate the ReasonForNonFeeding in the database
        List<ReasonForNonFeeding> reasonForNonFeedingList = reasonForNonFeedingRepository.findAll();
        assertThat(reasonForNonFeedingList).hasSize(databaseSizeBeforeUpdate);
        ReasonForNonFeeding testReasonForNonFeeding = reasonForNonFeedingList.get(reasonForNonFeedingList.size() - 1);
        assertThat(testReasonForNonFeeding.getDateOfNonFeeding()).isEqualTo(UPDATED_DATE_OF_NON_FEEDING);
        assertThat(testReasonForNonFeeding.getReasonForNonFeeding()).isEqualTo(UPDATED_REASON_FOR_NON_FEEDING);
        assertThat(testReasonForNonFeeding.getCreatedByUserId()).isEqualTo(UPDATED_CREATED_BY_USER_ID);
        assertThat(testReasonForNonFeeding.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingReasonForNonFeeding() throws Exception {
        int databaseSizeBeforeUpdate = reasonForNonFeedingRepository.findAll().size();

        // Create the ReasonForNonFeeding

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReasonForNonFeedingMockMvc.perform(put("/api/reason-for-non-feedings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reasonForNonFeeding)))
            .andExpect(status().isBadRequest());

        // Validate the ReasonForNonFeeding in the database
        List<ReasonForNonFeeding> reasonForNonFeedingList = reasonForNonFeedingRepository.findAll();
        assertThat(reasonForNonFeedingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReasonForNonFeeding() throws Exception {
        // Initialize the database
        reasonForNonFeedingRepository.saveAndFlush(reasonForNonFeeding);

        int databaseSizeBeforeDelete = reasonForNonFeedingRepository.findAll().size();

        // Delete the reasonForNonFeeding
        restReasonForNonFeedingMockMvc.perform(delete("/api/reason-for-non-feedings/{id}", reasonForNonFeeding.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReasonForNonFeeding> reasonForNonFeedingList = reasonForNonFeedingRepository.findAll();
        assertThat(reasonForNonFeedingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReasonForNonFeeding.class);
        ReasonForNonFeeding reasonForNonFeeding1 = new ReasonForNonFeeding();
        reasonForNonFeeding1.setId(1L);
        ReasonForNonFeeding reasonForNonFeeding2 = new ReasonForNonFeeding();
        reasonForNonFeeding2.setId(reasonForNonFeeding1.getId());
        assertThat(reasonForNonFeeding1).isEqualTo(reasonForNonFeeding2);
        reasonForNonFeeding2.setId(2L);
        assertThat(reasonForNonFeeding1).isNotEqualTo(reasonForNonFeeding2);
        reasonForNonFeeding1.setId(null);
        assertThat(reasonForNonFeeding1).isNotEqualTo(reasonForNonFeeding2);
    }
}
