package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.WebApp;
import com.mycompany.myapp.domain.Supporter;
import com.mycompany.myapp.repository.SupporterRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.SupporterSalutation;
/**
 * Integration tests for the {@link SupporterResource} REST controller.
 */
@SpringBootTest(classes = WebApp.class)
public class SupporterResourceIT {

    private static final String DEFAULT_SALESFORCE_UID = "AAAAAAAAAA";
    private static final String UPDATED_SALESFORCE_UID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AGE_CATEGORY = false;
    private static final Boolean UPDATED_AGE_CATEGORY = true;

    private static final SupporterSalutation DEFAULT_SUPPORTER_SALUTATION = SupporterSalutation.MR;
    private static final SupporterSalutation UPDATED_SUPPORTER_SALUTATION = SupporterSalutation.MRS;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "o`@H\".u";
    private static final String UPDATED_EMAIL = "2q@OJ.}j";

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SUPPORTER_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SUPPORTER_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SUPPORTER_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SUPPORTER_PICTURE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_CONTACTABLE_BY_EMAIL = false;
    private static final Boolean UPDATED_CONTACTABLE_BY_EMAIL = true;

    private static final Boolean DEFAULT_CONTACTABLE_BY_POST = false;
    private static final Boolean UPDATED_CONTACTABLE_BY_POST = true;

    private static final Float DEFAULT_LOCATION_COORDINATE_X = 1F;
    private static final Float UPDATED_LOCATION_COORDINATE_X = 2F;

    private static final Float DEFAULT_LOCATION_COORDINATE_Y = 1F;
    private static final Float UPDATED_LOCATION_COORDINATE_Y = 2F;

    private static final String DEFAULT_FACEBOOK_URL = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK_URL = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM_URL = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER_URL = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY_USER_ID = 1L;
    private static final Long UPDATED_CREATED_BY_USER_ID = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SupporterRepository supporterRepository;

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

    private MockMvc restSupporterMockMvc;

    private Supporter supporter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupporterResource supporterResource = new SupporterResource(supporterRepository);
        this.restSupporterMockMvc = MockMvcBuilders.standaloneSetup(supporterResource)
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
    public static Supporter createEntity(EntityManager em) {
        Supporter supporter = new Supporter()
            .salesforceUID(DEFAULT_SALESFORCE_UID)
            .ageCategory(DEFAULT_AGE_CATEGORY)
            .supporterSalutation(DEFAULT_SUPPORTER_SALUTATION)
            .firstName(DEFAULT_FIRST_NAME)
            .secondName(DEFAULT_SECOND_NAME)
            .email(DEFAULT_EMAIL)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .city(DEFAULT_CITY)
            .county(DEFAULT_COUNTY)
            .postcode(DEFAULT_POSTCODE)
            .country(DEFAULT_COUNTRY)
            .supporterPicture(DEFAULT_SUPPORTER_PICTURE)
            .supporterPictureContentType(DEFAULT_SUPPORTER_PICTURE_CONTENT_TYPE)
            .contactableByEmail(DEFAULT_CONTACTABLE_BY_EMAIL)
            .contactableByPost(DEFAULT_CONTACTABLE_BY_POST)
            .locationCoordinateX(DEFAULT_LOCATION_COORDINATE_X)
            .locationCoordinateY(DEFAULT_LOCATION_COORDINATE_Y)
            .facebookUrl(DEFAULT_FACEBOOK_URL)
            .instagramUrl(DEFAULT_INSTAGRAM_URL)
            .twitterUrl(DEFAULT_TWITTER_URL)
            .createdByUserId(DEFAULT_CREATED_BY_USER_ID)
            .createdDate(DEFAULT_CREATED_DATE);
        return supporter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supporter createUpdatedEntity(EntityManager em) {
        Supporter supporter = new Supporter()
            .salesforceUID(UPDATED_SALESFORCE_UID)
            .ageCategory(UPDATED_AGE_CATEGORY)
            .supporterSalutation(UPDATED_SUPPORTER_SALUTATION)
            .firstName(UPDATED_FIRST_NAME)
            .secondName(UPDATED_SECOND_NAME)
            .email(UPDATED_EMAIL)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .city(UPDATED_CITY)
            .county(UPDATED_COUNTY)
            .postcode(UPDATED_POSTCODE)
            .country(UPDATED_COUNTRY)
            .supporterPicture(UPDATED_SUPPORTER_PICTURE)
            .supporterPictureContentType(UPDATED_SUPPORTER_PICTURE_CONTENT_TYPE)
            .contactableByEmail(UPDATED_CONTACTABLE_BY_EMAIL)
            .contactableByPost(UPDATED_CONTACTABLE_BY_POST)
            .locationCoordinateX(UPDATED_LOCATION_COORDINATE_X)
            .locationCoordinateY(UPDATED_LOCATION_COORDINATE_Y)
            .facebookUrl(UPDATED_FACEBOOK_URL)
            .instagramUrl(UPDATED_INSTAGRAM_URL)
            .twitterUrl(UPDATED_TWITTER_URL)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);
        return supporter;
    }

    @BeforeEach
    public void initTest() {
        supporter = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupporter() throws Exception {
        int databaseSizeBeforeCreate = supporterRepository.findAll().size();

        // Create the Supporter
        restSupporterMockMvc.perform(post("/api/supporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supporter)))
            .andExpect(status().isCreated());

        // Validate the Supporter in the database
        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeCreate + 1);
        Supporter testSupporter = supporterList.get(supporterList.size() - 1);
        assertThat(testSupporter.getSalesforceUID()).isEqualTo(DEFAULT_SALESFORCE_UID);
        assertThat(testSupporter.isAgeCategory()).isEqualTo(DEFAULT_AGE_CATEGORY);
        assertThat(testSupporter.getSupporterSalutation()).isEqualTo(DEFAULT_SUPPORTER_SALUTATION);
        assertThat(testSupporter.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testSupporter.getSecondName()).isEqualTo(DEFAULT_SECOND_NAME);
        assertThat(testSupporter.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSupporter.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testSupporter.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testSupporter.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testSupporter.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testSupporter.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testSupporter.getSupporterPicture()).isEqualTo(DEFAULT_SUPPORTER_PICTURE);
        assertThat(testSupporter.getSupporterPictureContentType()).isEqualTo(DEFAULT_SUPPORTER_PICTURE_CONTENT_TYPE);
        assertThat(testSupporter.isContactableByEmail()).isEqualTo(DEFAULT_CONTACTABLE_BY_EMAIL);
        assertThat(testSupporter.isContactableByPost()).isEqualTo(DEFAULT_CONTACTABLE_BY_POST);
        assertThat(testSupporter.getLocationCoordinateX()).isEqualTo(DEFAULT_LOCATION_COORDINATE_X);
        assertThat(testSupporter.getLocationCoordinateY()).isEqualTo(DEFAULT_LOCATION_COORDINATE_Y);
        assertThat(testSupporter.getFacebookUrl()).isEqualTo(DEFAULT_FACEBOOK_URL);
        assertThat(testSupporter.getInstagramUrl()).isEqualTo(DEFAULT_INSTAGRAM_URL);
        assertThat(testSupporter.getTwitterUrl()).isEqualTo(DEFAULT_TWITTER_URL);
        assertThat(testSupporter.getCreatedByUserId()).isEqualTo(DEFAULT_CREATED_BY_USER_ID);
        assertThat(testSupporter.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createSupporterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supporterRepository.findAll().size();

        // Create the Supporter with an existing ID
        supporter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupporterMockMvc.perform(post("/api/supporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supporter)))
            .andExpect(status().isBadRequest());

        // Validate the Supporter in the database
        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAgeCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = supporterRepository.findAll().size();
        // set the field null
        supporter.setAgeCategory(null);

        // Create the Supporter, which fails.

        restSupporterMockMvc.perform(post("/api/supporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supporter)))
            .andExpect(status().isBadRequest());

        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = supporterRepository.findAll().size();
        // set the field null
        supporter.setFirstName(null);

        // Create the Supporter, which fails.

        restSupporterMockMvc.perform(post("/api/supporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supporter)))
            .andExpect(status().isBadRequest());

        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSecondNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = supporterRepository.findAll().size();
        // set the field null
        supporter.setSecondName(null);

        // Create the Supporter, which fails.

        restSupporterMockMvc.perform(post("/api/supporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supporter)))
            .andExpect(status().isBadRequest());

        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStreetAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = supporterRepository.findAll().size();
        // set the field null
        supporter.setStreetAddress(null);

        // Create the Supporter, which fails.

        restSupporterMockMvc.perform(post("/api/supporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supporter)))
            .andExpect(status().isBadRequest());

        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = supporterRepository.findAll().size();
        // set the field null
        supporter.setCity(null);

        // Create the Supporter, which fails.

        restSupporterMockMvc.perform(post("/api/supporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supporter)))
            .andExpect(status().isBadRequest());

        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountyIsRequired() throws Exception {
        int databaseSizeBeforeTest = supporterRepository.findAll().size();
        // set the field null
        supporter.setCounty(null);

        // Create the Supporter, which fails.

        restSupporterMockMvc.perform(post("/api/supporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supporter)))
            .andExpect(status().isBadRequest());

        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = supporterRepository.findAll().size();
        // set the field null
        supporter.setPostcode(null);

        // Create the Supporter, which fails.

        restSupporterMockMvc.perform(post("/api/supporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supporter)))
            .andExpect(status().isBadRequest());

        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = supporterRepository.findAll().size();
        // set the field null
        supporter.setCountry(null);

        // Create the Supporter, which fails.

        restSupporterMockMvc.perform(post("/api/supporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supporter)))
            .andExpect(status().isBadRequest());

        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactableByEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = supporterRepository.findAll().size();
        // set the field null
        supporter.setContactableByEmail(null);

        // Create the Supporter, which fails.

        restSupporterMockMvc.perform(post("/api/supporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supporter)))
            .andExpect(status().isBadRequest());

        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactableByPostIsRequired() throws Exception {
        int databaseSizeBeforeTest = supporterRepository.findAll().size();
        // set the field null
        supporter.setContactableByPost(null);

        // Create the Supporter, which fails.

        restSupporterMockMvc.perform(post("/api/supporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supporter)))
            .andExpect(status().isBadRequest());

        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationCoordinateXIsRequired() throws Exception {
        int databaseSizeBeforeTest = supporterRepository.findAll().size();
        // set the field null
        supporter.setLocationCoordinateX(null);

        // Create the Supporter, which fails.

        restSupporterMockMvc.perform(post("/api/supporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supporter)))
            .andExpect(status().isBadRequest());

        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationCoordinateYIsRequired() throws Exception {
        int databaseSizeBeforeTest = supporterRepository.findAll().size();
        // set the field null
        supporter.setLocationCoordinateY(null);

        // Create the Supporter, which fails.

        restSupporterMockMvc.perform(post("/api/supporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supporter)))
            .andExpect(status().isBadRequest());

        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupporters() throws Exception {
        // Initialize the database
        supporterRepository.saveAndFlush(supporter);

        // Get all the supporterList
        restSupporterMockMvc.perform(get("/api/supporters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supporter.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesforceUID").value(hasItem(DEFAULT_SALESFORCE_UID)))
            .andExpect(jsonPath("$.[*].ageCategory").value(hasItem(DEFAULT_AGE_CATEGORY.booleanValue())))
            .andExpect(jsonPath("$.[*].supporterSalutation").value(hasItem(DEFAULT_SUPPORTER_SALUTATION.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].secondName").value(hasItem(DEFAULT_SECOND_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].supporterPictureContentType").value(hasItem(DEFAULT_SUPPORTER_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].supporterPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_SUPPORTER_PICTURE))))
            .andExpect(jsonPath("$.[*].contactableByEmail").value(hasItem(DEFAULT_CONTACTABLE_BY_EMAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].contactableByPost").value(hasItem(DEFAULT_CONTACTABLE_BY_POST.booleanValue())))
            .andExpect(jsonPath("$.[*].locationCoordinateX").value(hasItem(DEFAULT_LOCATION_COORDINATE_X.doubleValue())))
            .andExpect(jsonPath("$.[*].locationCoordinateY").value(hasItem(DEFAULT_LOCATION_COORDINATE_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].facebookUrl").value(hasItem(DEFAULT_FACEBOOK_URL)))
            .andExpect(jsonPath("$.[*].instagramUrl").value(hasItem(DEFAULT_INSTAGRAM_URL)))
            .andExpect(jsonPath("$.[*].twitterUrl").value(hasItem(DEFAULT_TWITTER_URL)))
            .andExpect(jsonPath("$.[*].createdByUserId").value(hasItem(DEFAULT_CREATED_BY_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getSupporter() throws Exception {
        // Initialize the database
        supporterRepository.saveAndFlush(supporter);

        // Get the supporter
        restSupporterMockMvc.perform(get("/api/supporters/{id}", supporter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supporter.getId().intValue()))
            .andExpect(jsonPath("$.salesforceUID").value(DEFAULT_SALESFORCE_UID))
            .andExpect(jsonPath("$.ageCategory").value(DEFAULT_AGE_CATEGORY.booleanValue()))
            .andExpect(jsonPath("$.supporterSalutation").value(DEFAULT_SUPPORTER_SALUTATION.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.secondName").value(DEFAULT_SECOND_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.county").value(DEFAULT_COUNTY))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.supporterPictureContentType").value(DEFAULT_SUPPORTER_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.supporterPicture").value(Base64Utils.encodeToString(DEFAULT_SUPPORTER_PICTURE)))
            .andExpect(jsonPath("$.contactableByEmail").value(DEFAULT_CONTACTABLE_BY_EMAIL.booleanValue()))
            .andExpect(jsonPath("$.contactableByPost").value(DEFAULT_CONTACTABLE_BY_POST.booleanValue()))
            .andExpect(jsonPath("$.locationCoordinateX").value(DEFAULT_LOCATION_COORDINATE_X.doubleValue()))
            .andExpect(jsonPath("$.locationCoordinateY").value(DEFAULT_LOCATION_COORDINATE_Y.doubleValue()))
            .andExpect(jsonPath("$.facebookUrl").value(DEFAULT_FACEBOOK_URL))
            .andExpect(jsonPath("$.instagramUrl").value(DEFAULT_INSTAGRAM_URL))
            .andExpect(jsonPath("$.twitterUrl").value(DEFAULT_TWITTER_URL))
            .andExpect(jsonPath("$.createdByUserId").value(DEFAULT_CREATED_BY_USER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSupporter() throws Exception {
        // Get the supporter
        restSupporterMockMvc.perform(get("/api/supporters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupporter() throws Exception {
        // Initialize the database
        supporterRepository.saveAndFlush(supporter);

        int databaseSizeBeforeUpdate = supporterRepository.findAll().size();

        // Update the supporter
        Supporter updatedSupporter = supporterRepository.findById(supporter.getId()).get();
        // Disconnect from session so that the updates on updatedSupporter are not directly saved in db
        em.detach(updatedSupporter);
        updatedSupporter
            .salesforceUID(UPDATED_SALESFORCE_UID)
            .ageCategory(UPDATED_AGE_CATEGORY)
            .supporterSalutation(UPDATED_SUPPORTER_SALUTATION)
            .firstName(UPDATED_FIRST_NAME)
            .secondName(UPDATED_SECOND_NAME)
            .email(UPDATED_EMAIL)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .city(UPDATED_CITY)
            .county(UPDATED_COUNTY)
            .postcode(UPDATED_POSTCODE)
            .country(UPDATED_COUNTRY)
            .supporterPicture(UPDATED_SUPPORTER_PICTURE)
            .supporterPictureContentType(UPDATED_SUPPORTER_PICTURE_CONTENT_TYPE)
            .contactableByEmail(UPDATED_CONTACTABLE_BY_EMAIL)
            .contactableByPost(UPDATED_CONTACTABLE_BY_POST)
            .locationCoordinateX(UPDATED_LOCATION_COORDINATE_X)
            .locationCoordinateY(UPDATED_LOCATION_COORDINATE_Y)
            .facebookUrl(UPDATED_FACEBOOK_URL)
            .instagramUrl(UPDATED_INSTAGRAM_URL)
            .twitterUrl(UPDATED_TWITTER_URL)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);

        restSupporterMockMvc.perform(put("/api/supporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSupporter)))
            .andExpect(status().isOk());

        // Validate the Supporter in the database
        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeUpdate);
        Supporter testSupporter = supporterList.get(supporterList.size() - 1);
        assertThat(testSupporter.getSalesforceUID()).isEqualTo(UPDATED_SALESFORCE_UID);
        assertThat(testSupporter.isAgeCategory()).isEqualTo(UPDATED_AGE_CATEGORY);
        assertThat(testSupporter.getSupporterSalutation()).isEqualTo(UPDATED_SUPPORTER_SALUTATION);
        assertThat(testSupporter.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testSupporter.getSecondName()).isEqualTo(UPDATED_SECOND_NAME);
        assertThat(testSupporter.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSupporter.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testSupporter.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testSupporter.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testSupporter.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testSupporter.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testSupporter.getSupporterPicture()).isEqualTo(UPDATED_SUPPORTER_PICTURE);
        assertThat(testSupporter.getSupporterPictureContentType()).isEqualTo(UPDATED_SUPPORTER_PICTURE_CONTENT_TYPE);
        assertThat(testSupporter.isContactableByEmail()).isEqualTo(UPDATED_CONTACTABLE_BY_EMAIL);
        assertThat(testSupporter.isContactableByPost()).isEqualTo(UPDATED_CONTACTABLE_BY_POST);
        assertThat(testSupporter.getLocationCoordinateX()).isEqualTo(UPDATED_LOCATION_COORDINATE_X);
        assertThat(testSupporter.getLocationCoordinateY()).isEqualTo(UPDATED_LOCATION_COORDINATE_Y);
        assertThat(testSupporter.getFacebookUrl()).isEqualTo(UPDATED_FACEBOOK_URL);
        assertThat(testSupporter.getInstagramUrl()).isEqualTo(UPDATED_INSTAGRAM_URL);
        assertThat(testSupporter.getTwitterUrl()).isEqualTo(UPDATED_TWITTER_URL);
        assertThat(testSupporter.getCreatedByUserId()).isEqualTo(UPDATED_CREATED_BY_USER_ID);
        assertThat(testSupporter.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSupporter() throws Exception {
        int databaseSizeBeforeUpdate = supporterRepository.findAll().size();

        // Create the Supporter

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupporterMockMvc.perform(put("/api/supporters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supporter)))
            .andExpect(status().isBadRequest());

        // Validate the Supporter in the database
        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupporter() throws Exception {
        // Initialize the database
        supporterRepository.saveAndFlush(supporter);

        int databaseSizeBeforeDelete = supporterRepository.findAll().size();

        // Delete the supporter
        restSupporterMockMvc.perform(delete("/api/supporters/{id}", supporter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Supporter.class);
        Supporter supporter1 = new Supporter();
        supporter1.setId(1L);
        Supporter supporter2 = new Supporter();
        supporter2.setId(supporter1.getId());
        assertThat(supporter1).isEqualTo(supporter2);
        supporter2.setId(2L);
        assertThat(supporter1).isNotEqualTo(supporter2);
        supporter1.setId(null);
        assertThat(supporter1).isNotEqualTo(supporter2);
    }
}
