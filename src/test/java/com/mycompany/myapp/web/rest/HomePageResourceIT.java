package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.WebApp;
import com.mycompany.myapp.domain.HomePage;
import com.mycompany.myapp.repository.HomePageRepository;
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
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.TypeOfProject;
/**
 * Integration tests for the {@link HomePageResource} REST controller.
 */
@SpringBootTest(classes = WebApp.class)
public class HomePageResourceIT {

    private static final Double DEFAULT_COST_OF_FEEDING_A_CHILD = 1D;
    private static final Double UPDATED_COST_OF_FEEDING_A_CHILD = 2D;

    private static final TypeOfProject DEFAULT_TYPE_OF_PROJECT = TypeOfProject.GENERALFUNDRAISING;
    private static final TypeOfProject UPDATED_TYPE_OF_PROJECT = TypeOfProject.SPONSORASCHOOL;

    @Autowired
    private HomePageRepository homePageRepository;

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

    private MockMvc restHomePageMockMvc;

    private HomePage homePage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HomePageResource homePageResource = new HomePageResource(homePageRepository);
        this.restHomePageMockMvc = MockMvcBuilders.standaloneSetup(homePageResource)
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
    public static HomePage createEntity(EntityManager em) {
        HomePage homePage = new HomePage()
            .costOfFeedingAChild(DEFAULT_COST_OF_FEEDING_A_CHILD)
            .typeOfProject(DEFAULT_TYPE_OF_PROJECT);
        return homePage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HomePage createUpdatedEntity(EntityManager em) {
        HomePage homePage = new HomePage()
            .costOfFeedingAChild(UPDATED_COST_OF_FEEDING_A_CHILD)
            .typeOfProject(UPDATED_TYPE_OF_PROJECT);
        return homePage;
    }

    @BeforeEach
    public void initTest() {
        homePage = createEntity(em);
    }

    @Test
    @Transactional
    public void createHomePage() throws Exception {
        int databaseSizeBeforeCreate = homePageRepository.findAll().size();

        // Create the HomePage
        restHomePageMockMvc.perform(post("/api/home-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(homePage)))
            .andExpect(status().isCreated());

        // Validate the HomePage in the database
        List<HomePage> homePageList = homePageRepository.findAll();
        assertThat(homePageList).hasSize(databaseSizeBeforeCreate + 1);
        HomePage testHomePage = homePageList.get(homePageList.size() - 1);
        assertThat(testHomePage.getCostOfFeedingAChild()).isEqualTo(DEFAULT_COST_OF_FEEDING_A_CHILD);
        assertThat(testHomePage.getTypeOfProject()).isEqualTo(DEFAULT_TYPE_OF_PROJECT);
    }

    @Test
    @Transactional
    public void createHomePageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = homePageRepository.findAll().size();

        // Create the HomePage with an existing ID
        homePage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHomePageMockMvc.perform(post("/api/home-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(homePage)))
            .andExpect(status().isBadRequest());

        // Validate the HomePage in the database
        List<HomePage> homePageList = homePageRepository.findAll();
        assertThat(homePageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHomePages() throws Exception {
        // Initialize the database
        homePageRepository.saveAndFlush(homePage);

        // Get all the homePageList
        restHomePageMockMvc.perform(get("/api/home-pages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(homePage.getId().intValue())))
            .andExpect(jsonPath("$.[*].costOfFeedingAChild").value(hasItem(DEFAULT_COST_OF_FEEDING_A_CHILD.doubleValue())))
            .andExpect(jsonPath("$.[*].typeOfProject").value(hasItem(DEFAULT_TYPE_OF_PROJECT.toString())));
    }
    
    @Test
    @Transactional
    public void getHomePage() throws Exception {
        // Initialize the database
        homePageRepository.saveAndFlush(homePage);

        // Get the homePage
        restHomePageMockMvc.perform(get("/api/home-pages/{id}", homePage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(homePage.getId().intValue()))
            .andExpect(jsonPath("$.costOfFeedingAChild").value(DEFAULT_COST_OF_FEEDING_A_CHILD.doubleValue()))
            .andExpect(jsonPath("$.typeOfProject").value(DEFAULT_TYPE_OF_PROJECT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHomePage() throws Exception {
        // Get the homePage
        restHomePageMockMvc.perform(get("/api/home-pages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHomePage() throws Exception {
        // Initialize the database
        homePageRepository.saveAndFlush(homePage);

        int databaseSizeBeforeUpdate = homePageRepository.findAll().size();

        // Update the homePage
        HomePage updatedHomePage = homePageRepository.findById(homePage.getId()).get();
        // Disconnect from session so that the updates on updatedHomePage are not directly saved in db
        em.detach(updatedHomePage);
        updatedHomePage
            .costOfFeedingAChild(UPDATED_COST_OF_FEEDING_A_CHILD)
            .typeOfProject(UPDATED_TYPE_OF_PROJECT);

        restHomePageMockMvc.perform(put("/api/home-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHomePage)))
            .andExpect(status().isOk());

        // Validate the HomePage in the database
        List<HomePage> homePageList = homePageRepository.findAll();
        assertThat(homePageList).hasSize(databaseSizeBeforeUpdate);
        HomePage testHomePage = homePageList.get(homePageList.size() - 1);
        assertThat(testHomePage.getCostOfFeedingAChild()).isEqualTo(UPDATED_COST_OF_FEEDING_A_CHILD);
        assertThat(testHomePage.getTypeOfProject()).isEqualTo(UPDATED_TYPE_OF_PROJECT);
    }

    @Test
    @Transactional
    public void updateNonExistingHomePage() throws Exception {
        int databaseSizeBeforeUpdate = homePageRepository.findAll().size();

        // Create the HomePage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHomePageMockMvc.perform(put("/api/home-pages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(homePage)))
            .andExpect(status().isBadRequest());

        // Validate the HomePage in the database
        List<HomePage> homePageList = homePageRepository.findAll();
        assertThat(homePageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHomePage() throws Exception {
        // Initialize the database
        homePageRepository.saveAndFlush(homePage);

        int databaseSizeBeforeDelete = homePageRepository.findAll().size();

        // Delete the homePage
        restHomePageMockMvc.perform(delete("/api/home-pages/{id}", homePage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HomePage> homePageList = homePageRepository.findAll();
        assertThat(homePageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HomePage.class);
        HomePage homePage1 = new HomePage();
        homePage1.setId(1L);
        HomePage homePage2 = new HomePage();
        homePage2.setId(homePage1.getId());
        assertThat(homePage1).isEqualTo(homePage2);
        homePage2.setId(2L);
        assertThat(homePage1).isNotEqualTo(homePage2);
        homePage1.setId(null);
        assertThat(homePage1).isNotEqualTo(homePage2);
    }
}
