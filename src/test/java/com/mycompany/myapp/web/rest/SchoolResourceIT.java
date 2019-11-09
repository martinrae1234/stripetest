package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.WebApp;
import com.mycompany.myapp.domain.School;
import com.mycompany.myapp.repository.SchoolRepository;
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

/**
 * Integration tests for the {@link SchoolResource} REST controller.
 */
@SpringBootTest(classes = WebApp.class)
public class SchoolResourceIT {

    private static final String DEFAULT_SALESFORCE_UID = "AAAAAAAAAA";
    private static final String UPDATED_SALESFORCE_UID = "BBBBBBBBBB";

    private static final String DEFAULT_LEGACY_SCHOOL_ID = "AAAAAAAAAA";
    private static final String UPDATED_LEGACY_SCHOOL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SCHOOL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SPONSORED = false;
    private static final Boolean UPDATED_SPONSORED = true;

    private static final Integer DEFAULT_ATTENDANCE_TOTAL = 1;
    private static final Integer UPDATED_ATTENDANCE_TOTAL = 2;

    private static final Integer DEFAULT_ATTENDANCE_BOYS = 1;
    private static final Integer UPDATED_ATTENDANCE_BOYS = 2;

    private static final Integer DEFAULT_ATTENDANCE_GIRLS = 1;
    private static final Integer UPDATED_ATTENDANCE_GIRLS = 2;

    private static final Integer DEFAULT_ENROLMENT_TOTAL = 1;
    private static final Integer UPDATED_ENROLMENT_TOTAL = 2;

    private static final Float DEFAULT_LOCATION_COORDINATE_X = 1F;
    private static final Float UPDATED_LOCATION_COORDINATE_X = 2F;

    private static final Float DEFAULT_LOCATION_COORDINATE_Y = 1F;
    private static final Float UPDATED_LOCATION_COORDINATE_Y = 2F;

    private static final byte[] DEFAULT_IMAGE_BANNER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_BANNER = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_BANNER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_BANNER_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TEXT_BANNER = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_BANNER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE_ONE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_ONE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_ONE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_ONE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_TWO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_TWO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_TWO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_TWO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_THREE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_THREE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_THREE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_THREE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_FOUR = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_FOUR = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_FOUR_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_FOUR_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_DATE_OF_LAST_STOCK_CHECK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_LAST_STOCK_CHECK = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATED_BY_USER_ID = 1L;
    private static final Long UPDATED_CREATED_BY_USER_ID = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SchoolRepository schoolRepository;

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

    private MockMvc restSchoolMockMvc;

    private School school;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SchoolResource schoolResource = new SchoolResource(schoolRepository);
        this.restSchoolMockMvc = MockMvcBuilders.standaloneSetup(schoolResource)
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
    public static School createEntity(EntityManager em) {
        School school = new School()
            .salesforceUID(DEFAULT_SALESFORCE_UID)
            .legacySchoolID(DEFAULT_LEGACY_SCHOOL_ID)
            .schoolName(DEFAULT_SCHOOL_NAME)
            .sponsored(DEFAULT_SPONSORED)
            .attendanceTotal(DEFAULT_ATTENDANCE_TOTAL)
            .attendanceBoys(DEFAULT_ATTENDANCE_BOYS)
            .attendanceGirls(DEFAULT_ATTENDANCE_GIRLS)
            .enrolmentTotal(DEFAULT_ENROLMENT_TOTAL)
            .locationCoordinateX(DEFAULT_LOCATION_COORDINATE_X)
            .locationCoordinateY(DEFAULT_LOCATION_COORDINATE_Y)
            .imageBanner(DEFAULT_IMAGE_BANNER)
            .imageBannerContentType(DEFAULT_IMAGE_BANNER_CONTENT_TYPE)
            .textBanner(DEFAULT_TEXT_BANNER)
            .imageOne(DEFAULT_IMAGE_ONE)
            .imageOneContentType(DEFAULT_IMAGE_ONE_CONTENT_TYPE)
            .imageTwo(DEFAULT_IMAGE_TWO)
            .imageTwoContentType(DEFAULT_IMAGE_TWO_CONTENT_TYPE)
            .imageThree(DEFAULT_IMAGE_THREE)
            .imageThreeContentType(DEFAULT_IMAGE_THREE_CONTENT_TYPE)
            .imageFour(DEFAULT_IMAGE_FOUR)
            .imageFourContentType(DEFAULT_IMAGE_FOUR_CONTENT_TYPE)
            .dateOfLastStockCheck(DEFAULT_DATE_OF_LAST_STOCK_CHECK)
            .createdByUserId(DEFAULT_CREATED_BY_USER_ID)
            .createdDate(DEFAULT_CREATED_DATE);
        return school;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static School createUpdatedEntity(EntityManager em) {
        School school = new School()
            .salesforceUID(UPDATED_SALESFORCE_UID)
            .legacySchoolID(UPDATED_LEGACY_SCHOOL_ID)
            .schoolName(UPDATED_SCHOOL_NAME)
            .sponsored(UPDATED_SPONSORED)
            .attendanceTotal(UPDATED_ATTENDANCE_TOTAL)
            .attendanceBoys(UPDATED_ATTENDANCE_BOYS)
            .attendanceGirls(UPDATED_ATTENDANCE_GIRLS)
            .enrolmentTotal(UPDATED_ENROLMENT_TOTAL)
            .locationCoordinateX(UPDATED_LOCATION_COORDINATE_X)
            .locationCoordinateY(UPDATED_LOCATION_COORDINATE_Y)
            .imageBanner(UPDATED_IMAGE_BANNER)
            .imageBannerContentType(UPDATED_IMAGE_BANNER_CONTENT_TYPE)
            .textBanner(UPDATED_TEXT_BANNER)
            .imageOne(UPDATED_IMAGE_ONE)
            .imageOneContentType(UPDATED_IMAGE_ONE_CONTENT_TYPE)
            .imageTwo(UPDATED_IMAGE_TWO)
            .imageTwoContentType(UPDATED_IMAGE_TWO_CONTENT_TYPE)
            .imageThree(UPDATED_IMAGE_THREE)
            .imageThreeContentType(UPDATED_IMAGE_THREE_CONTENT_TYPE)
            .imageFour(UPDATED_IMAGE_FOUR)
            .imageFourContentType(UPDATED_IMAGE_FOUR_CONTENT_TYPE)
            .dateOfLastStockCheck(UPDATED_DATE_OF_LAST_STOCK_CHECK)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);
        return school;
    }

    @BeforeEach
    public void initTest() {
        school = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchool() throws Exception {
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();

        // Create the School
        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isCreated());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeCreate + 1);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getSalesforceUID()).isEqualTo(DEFAULT_SALESFORCE_UID);
        assertThat(testSchool.getLegacySchoolID()).isEqualTo(DEFAULT_LEGACY_SCHOOL_ID);
        assertThat(testSchool.getSchoolName()).isEqualTo(DEFAULT_SCHOOL_NAME);
        assertThat(testSchool.isSponsored()).isEqualTo(DEFAULT_SPONSORED);
        assertThat(testSchool.getAttendanceTotal()).isEqualTo(DEFAULT_ATTENDANCE_TOTAL);
        assertThat(testSchool.getAttendanceBoys()).isEqualTo(DEFAULT_ATTENDANCE_BOYS);
        assertThat(testSchool.getAttendanceGirls()).isEqualTo(DEFAULT_ATTENDANCE_GIRLS);
        assertThat(testSchool.getEnrolmentTotal()).isEqualTo(DEFAULT_ENROLMENT_TOTAL);
        assertThat(testSchool.getLocationCoordinateX()).isEqualTo(DEFAULT_LOCATION_COORDINATE_X);
        assertThat(testSchool.getLocationCoordinateY()).isEqualTo(DEFAULT_LOCATION_COORDINATE_Y);
        assertThat(testSchool.getImageBanner()).isEqualTo(DEFAULT_IMAGE_BANNER);
        assertThat(testSchool.getImageBannerContentType()).isEqualTo(DEFAULT_IMAGE_BANNER_CONTENT_TYPE);
        assertThat(testSchool.getTextBanner()).isEqualTo(DEFAULT_TEXT_BANNER);
        assertThat(testSchool.getImageOne()).isEqualTo(DEFAULT_IMAGE_ONE);
        assertThat(testSchool.getImageOneContentType()).isEqualTo(DEFAULT_IMAGE_ONE_CONTENT_TYPE);
        assertThat(testSchool.getImageTwo()).isEqualTo(DEFAULT_IMAGE_TWO);
        assertThat(testSchool.getImageTwoContentType()).isEqualTo(DEFAULT_IMAGE_TWO_CONTENT_TYPE);
        assertThat(testSchool.getImageThree()).isEqualTo(DEFAULT_IMAGE_THREE);
        assertThat(testSchool.getImageThreeContentType()).isEqualTo(DEFAULT_IMAGE_THREE_CONTENT_TYPE);
        assertThat(testSchool.getImageFour()).isEqualTo(DEFAULT_IMAGE_FOUR);
        assertThat(testSchool.getImageFourContentType()).isEqualTo(DEFAULT_IMAGE_FOUR_CONTENT_TYPE);
        assertThat(testSchool.getDateOfLastStockCheck()).isEqualTo(DEFAULT_DATE_OF_LAST_STOCK_CHECK);
        assertThat(testSchool.getCreatedByUserId()).isEqualTo(DEFAULT_CREATED_BY_USER_ID);
        assertThat(testSchool.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createSchoolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();

        // Create the School with an existing ID
        school.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSchoolNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setSchoolName(null);

        // Create the School, which fails.

        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAttendanceBoysIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setAttendanceBoys(null);

        // Create the School, which fails.

        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAttendanceGirlsIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setAttendanceGirls(null);

        // Create the School, which fails.

        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnrolmentTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setEnrolmentTotal(null);

        // Create the School, which fails.

        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationCoordinateXIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setLocationCoordinateX(null);

        // Create the School, which fails.

        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationCoordinateYIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolRepository.findAll().size();
        // set the field null
        school.setLocationCoordinateY(null);

        // Create the School, which fails.

        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchools() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList
        restSchoolMockMvc.perform(get("/api/schools?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(school.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesforceUID").value(hasItem(DEFAULT_SALESFORCE_UID)))
            .andExpect(jsonPath("$.[*].legacySchoolID").value(hasItem(DEFAULT_LEGACY_SCHOOL_ID)))
            .andExpect(jsonPath("$.[*].schoolName").value(hasItem(DEFAULT_SCHOOL_NAME)))
            .andExpect(jsonPath("$.[*].sponsored").value(hasItem(DEFAULT_SPONSORED.booleanValue())))
            .andExpect(jsonPath("$.[*].attendanceTotal").value(hasItem(DEFAULT_ATTENDANCE_TOTAL)))
            .andExpect(jsonPath("$.[*].attendanceBoys").value(hasItem(DEFAULT_ATTENDANCE_BOYS)))
            .andExpect(jsonPath("$.[*].attendanceGirls").value(hasItem(DEFAULT_ATTENDANCE_GIRLS)))
            .andExpect(jsonPath("$.[*].enrolmentTotal").value(hasItem(DEFAULT_ENROLMENT_TOTAL)))
            .andExpect(jsonPath("$.[*].locationCoordinateX").value(hasItem(DEFAULT_LOCATION_COORDINATE_X.doubleValue())))
            .andExpect(jsonPath("$.[*].locationCoordinateY").value(hasItem(DEFAULT_LOCATION_COORDINATE_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].imageBannerContentType").value(hasItem(DEFAULT_IMAGE_BANNER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageBanner").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_BANNER))))
            .andExpect(jsonPath("$.[*].textBanner").value(hasItem(DEFAULT_TEXT_BANNER)))
            .andExpect(jsonPath("$.[*].imageOneContentType").value(hasItem(DEFAULT_IMAGE_ONE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageOne").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_ONE))))
            .andExpect(jsonPath("$.[*].imageTwoContentType").value(hasItem(DEFAULT_IMAGE_TWO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageTwo").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_TWO))))
            .andExpect(jsonPath("$.[*].imageThreeContentType").value(hasItem(DEFAULT_IMAGE_THREE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageThree").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_THREE))))
            .andExpect(jsonPath("$.[*].imageFourContentType").value(hasItem(DEFAULT_IMAGE_FOUR_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageFour").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_FOUR))))
            .andExpect(jsonPath("$.[*].dateOfLastStockCheck").value(hasItem(DEFAULT_DATE_OF_LAST_STOCK_CHECK.toString())))
            .andExpect(jsonPath("$.[*].createdByUserId").value(hasItem(DEFAULT_CREATED_BY_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get the school
        restSchoolMockMvc.perform(get("/api/schools/{id}", school.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(school.getId().intValue()))
            .andExpect(jsonPath("$.salesforceUID").value(DEFAULT_SALESFORCE_UID))
            .andExpect(jsonPath("$.legacySchoolID").value(DEFAULT_LEGACY_SCHOOL_ID))
            .andExpect(jsonPath("$.schoolName").value(DEFAULT_SCHOOL_NAME))
            .andExpect(jsonPath("$.sponsored").value(DEFAULT_SPONSORED.booleanValue()))
            .andExpect(jsonPath("$.attendanceTotal").value(DEFAULT_ATTENDANCE_TOTAL))
            .andExpect(jsonPath("$.attendanceBoys").value(DEFAULT_ATTENDANCE_BOYS))
            .andExpect(jsonPath("$.attendanceGirls").value(DEFAULT_ATTENDANCE_GIRLS))
            .andExpect(jsonPath("$.enrolmentTotal").value(DEFAULT_ENROLMENT_TOTAL))
            .andExpect(jsonPath("$.locationCoordinateX").value(DEFAULT_LOCATION_COORDINATE_X.doubleValue()))
            .andExpect(jsonPath("$.locationCoordinateY").value(DEFAULT_LOCATION_COORDINATE_Y.doubleValue()))
            .andExpect(jsonPath("$.imageBannerContentType").value(DEFAULT_IMAGE_BANNER_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageBanner").value(Base64Utils.encodeToString(DEFAULT_IMAGE_BANNER)))
            .andExpect(jsonPath("$.textBanner").value(DEFAULT_TEXT_BANNER))
            .andExpect(jsonPath("$.imageOneContentType").value(DEFAULT_IMAGE_ONE_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageOne").value(Base64Utils.encodeToString(DEFAULT_IMAGE_ONE)))
            .andExpect(jsonPath("$.imageTwoContentType").value(DEFAULT_IMAGE_TWO_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageTwo").value(Base64Utils.encodeToString(DEFAULT_IMAGE_TWO)))
            .andExpect(jsonPath("$.imageThreeContentType").value(DEFAULT_IMAGE_THREE_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageThree").value(Base64Utils.encodeToString(DEFAULT_IMAGE_THREE)))
            .andExpect(jsonPath("$.imageFourContentType").value(DEFAULT_IMAGE_FOUR_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageFour").value(Base64Utils.encodeToString(DEFAULT_IMAGE_FOUR)))
            .andExpect(jsonPath("$.dateOfLastStockCheck").value(DEFAULT_DATE_OF_LAST_STOCK_CHECK.toString()))
            .andExpect(jsonPath("$.createdByUserId").value(DEFAULT_CREATED_BY_USER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchool() throws Exception {
        // Get the school
        restSchoolMockMvc.perform(get("/api/schools/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Update the school
        School updatedSchool = schoolRepository.findById(school.getId()).get();
        // Disconnect from session so that the updates on updatedSchool are not directly saved in db
        em.detach(updatedSchool);
        updatedSchool
            .salesforceUID(UPDATED_SALESFORCE_UID)
            .legacySchoolID(UPDATED_LEGACY_SCHOOL_ID)
            .schoolName(UPDATED_SCHOOL_NAME)
            .sponsored(UPDATED_SPONSORED)
            .attendanceTotal(UPDATED_ATTENDANCE_TOTAL)
            .attendanceBoys(UPDATED_ATTENDANCE_BOYS)
            .attendanceGirls(UPDATED_ATTENDANCE_GIRLS)
            .enrolmentTotal(UPDATED_ENROLMENT_TOTAL)
            .locationCoordinateX(UPDATED_LOCATION_COORDINATE_X)
            .locationCoordinateY(UPDATED_LOCATION_COORDINATE_Y)
            .imageBanner(UPDATED_IMAGE_BANNER)
            .imageBannerContentType(UPDATED_IMAGE_BANNER_CONTENT_TYPE)
            .textBanner(UPDATED_TEXT_BANNER)
            .imageOne(UPDATED_IMAGE_ONE)
            .imageOneContentType(UPDATED_IMAGE_ONE_CONTENT_TYPE)
            .imageTwo(UPDATED_IMAGE_TWO)
            .imageTwoContentType(UPDATED_IMAGE_TWO_CONTENT_TYPE)
            .imageThree(UPDATED_IMAGE_THREE)
            .imageThreeContentType(UPDATED_IMAGE_THREE_CONTENT_TYPE)
            .imageFour(UPDATED_IMAGE_FOUR)
            .imageFourContentType(UPDATED_IMAGE_FOUR_CONTENT_TYPE)
            .dateOfLastStockCheck(UPDATED_DATE_OF_LAST_STOCK_CHECK)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);

        restSchoolMockMvc.perform(put("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSchool)))
            .andExpect(status().isOk());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getSalesforceUID()).isEqualTo(UPDATED_SALESFORCE_UID);
        assertThat(testSchool.getLegacySchoolID()).isEqualTo(UPDATED_LEGACY_SCHOOL_ID);
        assertThat(testSchool.getSchoolName()).isEqualTo(UPDATED_SCHOOL_NAME);
        assertThat(testSchool.isSponsored()).isEqualTo(UPDATED_SPONSORED);
        assertThat(testSchool.getAttendanceTotal()).isEqualTo(UPDATED_ATTENDANCE_TOTAL);
        assertThat(testSchool.getAttendanceBoys()).isEqualTo(UPDATED_ATTENDANCE_BOYS);
        assertThat(testSchool.getAttendanceGirls()).isEqualTo(UPDATED_ATTENDANCE_GIRLS);
        assertThat(testSchool.getEnrolmentTotal()).isEqualTo(UPDATED_ENROLMENT_TOTAL);
        assertThat(testSchool.getLocationCoordinateX()).isEqualTo(UPDATED_LOCATION_COORDINATE_X);
        assertThat(testSchool.getLocationCoordinateY()).isEqualTo(UPDATED_LOCATION_COORDINATE_Y);
        assertThat(testSchool.getImageBanner()).isEqualTo(UPDATED_IMAGE_BANNER);
        assertThat(testSchool.getImageBannerContentType()).isEqualTo(UPDATED_IMAGE_BANNER_CONTENT_TYPE);
        assertThat(testSchool.getTextBanner()).isEqualTo(UPDATED_TEXT_BANNER);
        assertThat(testSchool.getImageOne()).isEqualTo(UPDATED_IMAGE_ONE);
        assertThat(testSchool.getImageOneContentType()).isEqualTo(UPDATED_IMAGE_ONE_CONTENT_TYPE);
        assertThat(testSchool.getImageTwo()).isEqualTo(UPDATED_IMAGE_TWO);
        assertThat(testSchool.getImageTwoContentType()).isEqualTo(UPDATED_IMAGE_TWO_CONTENT_TYPE);
        assertThat(testSchool.getImageThree()).isEqualTo(UPDATED_IMAGE_THREE);
        assertThat(testSchool.getImageThreeContentType()).isEqualTo(UPDATED_IMAGE_THREE_CONTENT_TYPE);
        assertThat(testSchool.getImageFour()).isEqualTo(UPDATED_IMAGE_FOUR);
        assertThat(testSchool.getImageFourContentType()).isEqualTo(UPDATED_IMAGE_FOUR_CONTENT_TYPE);
        assertThat(testSchool.getDateOfLastStockCheck()).isEqualTo(UPDATED_DATE_OF_LAST_STOCK_CHECK);
        assertThat(testSchool.getCreatedByUserId()).isEqualTo(UPDATED_CREATED_BY_USER_ID);
        assertThat(testSchool.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Create the School

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolMockMvc.perform(put("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeDelete = schoolRepository.findAll().size();

        // Delete the school
        restSchoolMockMvc.perform(delete("/api/schools/{id}", school.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(School.class);
        School school1 = new School();
        school1.setId(1L);
        School school2 = new School();
        school2.setId(school1.getId());
        assertThat(school1).isEqualTo(school2);
        school2.setId(2L);
        assertThat(school1).isNotEqualTo(school2);
        school1.setId(null);
        assertThat(school1).isNotEqualTo(school2);
    }
}
