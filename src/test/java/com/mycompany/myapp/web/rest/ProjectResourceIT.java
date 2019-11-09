package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.WebApp;
import com.mycompany.myapp.domain.Project;
import com.mycompany.myapp.repository.ProjectRepository;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.TypeOfProject;
/**
 * Integration tests for the {@link ProjectResource} REST controller.
 */
@SpringBootTest(classes = WebApp.class)
public class ProjectResourceIT {

    private static final String DEFAULT_SALESFORCE_UID = "AAAAAAAAAA";
    private static final String UPDATED_SALESFORCE_UID = "BBBBBBBBBB";

    private static final TypeOfProject DEFAULT_TYPE_OF_PROJECT = TypeOfProject.GENERALFUNDRAISING;
    private static final TypeOfProject UPDATED_TYPE_OF_PROJECT = TypeOfProject.SPONSORASCHOOL;

    private static final Double DEFAULT_FUNDRAISING_TARGET = 1D;
    private static final Double UPDATED_FUNDRAISING_TARGET = 2D;

    private static final Boolean DEFAULT_AGE_CATEGORY = false;
    private static final Boolean UPDATED_AGE_CATEGORY = true;

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PROJECT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PROJECT_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PROJECT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PROJECT_IMAGE_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_SPONSORSHIP_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SPONSORSHIP_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_SPONSORSHIP_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SPONSORSHIP_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATED_BY_USER_ID = 1L;
    private static final Long UPDATED_CREATED_BY_USER_ID = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProjectRepository projectRepository;

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

    private MockMvc restProjectMockMvc;

    private Project project;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectResource projectResource = new ProjectResource(projectRepository);
        this.restProjectMockMvc = MockMvcBuilders.standaloneSetup(projectResource)
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
    public static Project createEntity(EntityManager em) {
        Project project = new Project()
            .salesforceUID(DEFAULT_SALESFORCE_UID)
            .typeOfProject(DEFAULT_TYPE_OF_PROJECT)
            .fundraisingTarget(DEFAULT_FUNDRAISING_TARGET)
            .ageCategory(DEFAULT_AGE_CATEGORY)
            .projectName(DEFAULT_PROJECT_NAME)
            .projectDescription(DEFAULT_PROJECT_DESCRIPTION)
            .projectImage(DEFAULT_PROJECT_IMAGE)
            .projectImageContentType(DEFAULT_PROJECT_IMAGE_CONTENT_TYPE)
            .sponsorshipStart(DEFAULT_SPONSORSHIP_START)
            .sponsorshipEndDate(DEFAULT_SPONSORSHIP_END_DATE)
            .createdByUserId(DEFAULT_CREATED_BY_USER_ID)
            .createdDate(DEFAULT_CREATED_DATE);
        return project;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createUpdatedEntity(EntityManager em) {
        Project project = new Project()
            .salesforceUID(UPDATED_SALESFORCE_UID)
            .typeOfProject(UPDATED_TYPE_OF_PROJECT)
            .fundraisingTarget(UPDATED_FUNDRAISING_TARGET)
            .ageCategory(UPDATED_AGE_CATEGORY)
            .projectName(UPDATED_PROJECT_NAME)
            .projectDescription(UPDATED_PROJECT_DESCRIPTION)
            .projectImage(UPDATED_PROJECT_IMAGE)
            .projectImageContentType(UPDATED_PROJECT_IMAGE_CONTENT_TYPE)
            .sponsorshipStart(UPDATED_SPONSORSHIP_START)
            .sponsorshipEndDate(UPDATED_SPONSORSHIP_END_DATE)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);
        return project;
    }

    @BeforeEach
    public void initTest() {
        project = createEntity(em);
    }

    @Test
    @Transactional
    public void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // Create the Project
        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getSalesforceUID()).isEqualTo(DEFAULT_SALESFORCE_UID);
        assertThat(testProject.getTypeOfProject()).isEqualTo(DEFAULT_TYPE_OF_PROJECT);
        assertThat(testProject.getFundraisingTarget()).isEqualTo(DEFAULT_FUNDRAISING_TARGET);
        assertThat(testProject.isAgeCategory()).isEqualTo(DEFAULT_AGE_CATEGORY);
        assertThat(testProject.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testProject.getProjectDescription()).isEqualTo(DEFAULT_PROJECT_DESCRIPTION);
        assertThat(testProject.getProjectImage()).isEqualTo(DEFAULT_PROJECT_IMAGE);
        assertThat(testProject.getProjectImageContentType()).isEqualTo(DEFAULT_PROJECT_IMAGE_CONTENT_TYPE);
        assertThat(testProject.getSponsorshipStart()).isEqualTo(DEFAULT_SPONSORSHIP_START);
        assertThat(testProject.getSponsorshipEndDate()).isEqualTo(DEFAULT_SPONSORSHIP_END_DATE);
        assertThat(testProject.getCreatedByUserId()).isEqualTo(DEFAULT_CREATED_BY_USER_ID);
        assertThat(testProject.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createProjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // Create the Project with an existing ID
        project.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeOfProjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setTypeOfProject(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFundraisingTargetIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setFundraisingTarget(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setAgeCategory(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProjectNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectRepository.findAll().size();
        // set the field null
        project.setProjectName(null);

        // Create the Project, which fails.

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjects() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList
        restProjectMockMvc.perform(get("/api/projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesforceUID").value(hasItem(DEFAULT_SALESFORCE_UID)))
            .andExpect(jsonPath("$.[*].typeOfProject").value(hasItem(DEFAULT_TYPE_OF_PROJECT.toString())))
            .andExpect(jsonPath("$.[*].fundraisingTarget").value(hasItem(DEFAULT_FUNDRAISING_TARGET.doubleValue())))
            .andExpect(jsonPath("$.[*].ageCategory").value(hasItem(DEFAULT_AGE_CATEGORY.booleanValue())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].projectDescription").value(hasItem(DEFAULT_PROJECT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].projectImageContentType").value(hasItem(DEFAULT_PROJECT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].projectImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROJECT_IMAGE))))
            .andExpect(jsonPath("$.[*].sponsorshipStart").value(hasItem(DEFAULT_SPONSORSHIP_START.toString())))
            .andExpect(jsonPath("$.[*].sponsorshipEndDate").value(hasItem(DEFAULT_SPONSORSHIP_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdByUserId").value(hasItem(DEFAULT_CREATED_BY_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(project.getId().intValue()))
            .andExpect(jsonPath("$.salesforceUID").value(DEFAULT_SALESFORCE_UID))
            .andExpect(jsonPath("$.typeOfProject").value(DEFAULT_TYPE_OF_PROJECT.toString()))
            .andExpect(jsonPath("$.fundraisingTarget").value(DEFAULT_FUNDRAISING_TARGET.doubleValue()))
            .andExpect(jsonPath("$.ageCategory").value(DEFAULT_AGE_CATEGORY.booleanValue()))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME))
            .andExpect(jsonPath("$.projectDescription").value(DEFAULT_PROJECT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.projectImageContentType").value(DEFAULT_PROJECT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.projectImage").value(Base64Utils.encodeToString(DEFAULT_PROJECT_IMAGE)))
            .andExpect(jsonPath("$.sponsorshipStart").value(DEFAULT_SPONSORSHIP_START.toString()))
            .andExpect(jsonPath("$.sponsorshipEndDate").value(DEFAULT_SPONSORSHIP_END_DATE.toString()))
            .andExpect(jsonPath("$.createdByUserId").value(DEFAULT_CREATED_BY_USER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProject() throws Exception {
        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project
        Project updatedProject = projectRepository.findById(project.getId()).get();
        // Disconnect from session so that the updates on updatedProject are not directly saved in db
        em.detach(updatedProject);
        updatedProject
            .salesforceUID(UPDATED_SALESFORCE_UID)
            .typeOfProject(UPDATED_TYPE_OF_PROJECT)
            .fundraisingTarget(UPDATED_FUNDRAISING_TARGET)
            .ageCategory(UPDATED_AGE_CATEGORY)
            .projectName(UPDATED_PROJECT_NAME)
            .projectDescription(UPDATED_PROJECT_DESCRIPTION)
            .projectImage(UPDATED_PROJECT_IMAGE)
            .projectImageContentType(UPDATED_PROJECT_IMAGE_CONTENT_TYPE)
            .sponsorshipStart(UPDATED_SPONSORSHIP_START)
            .sponsorshipEndDate(UPDATED_SPONSORSHIP_END_DATE)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);

        restProjectMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProject)))
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getSalesforceUID()).isEqualTo(UPDATED_SALESFORCE_UID);
        assertThat(testProject.getTypeOfProject()).isEqualTo(UPDATED_TYPE_OF_PROJECT);
        assertThat(testProject.getFundraisingTarget()).isEqualTo(UPDATED_FUNDRAISING_TARGET);
        assertThat(testProject.isAgeCategory()).isEqualTo(UPDATED_AGE_CATEGORY);
        assertThat(testProject.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testProject.getProjectDescription()).isEqualTo(UPDATED_PROJECT_DESCRIPTION);
        assertThat(testProject.getProjectImage()).isEqualTo(UPDATED_PROJECT_IMAGE);
        assertThat(testProject.getProjectImageContentType()).isEqualTo(UPDATED_PROJECT_IMAGE_CONTENT_TYPE);
        assertThat(testProject.getSponsorshipStart()).isEqualTo(UPDATED_SPONSORSHIP_START);
        assertThat(testProject.getSponsorshipEndDate()).isEqualTo(UPDATED_SPONSORSHIP_END_DATE);
        assertThat(testProject.getCreatedByUserId()).isEqualTo(UPDATED_CREATED_BY_USER_ID);
        assertThat(testProject.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Create the Project

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeDelete = projectRepository.findAll().size();

        // Delete the project
        restProjectMockMvc.perform(delete("/api/projects/{id}", project.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Project.class);
        Project project1 = new Project();
        project1.setId(1L);
        Project project2 = new Project();
        project2.setId(project1.getId());
        assertThat(project1).isEqualTo(project2);
        project2.setId(2L);
        assertThat(project1).isNotEqualTo(project2);
        project1.setId(null);
        assertThat(project1).isNotEqualTo(project2);
    }
}
