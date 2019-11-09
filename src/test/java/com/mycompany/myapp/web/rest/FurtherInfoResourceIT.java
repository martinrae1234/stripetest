package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.WebApp;
import com.mycompany.myapp.domain.FurtherInfo;
import com.mycompany.myapp.repository.FurtherInfoRepository;
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

/**
 * Integration tests for the {@link FurtherInfoResource} REST controller.
 */
@SpringBootTest(classes = WebApp.class)
public class FurtherInfoResourceIT {

    private static final Boolean DEFAULT_ACTIVE_FURTHER_INFO = false;
    private static final Boolean UPDATED_ACTIVE_FURTHER_INFO = true;

    private static final byte[] DEFAULT_BANNER_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BANNER_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BANNER_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BANNER_PICTURE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_BANNER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HEADER_ONE = "AAAAAAAAAA";
    private static final String UPDATED_HEADER_ONE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_ONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_ONE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PICTURE_ONE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE_ONE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICTURE_ONE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_ONE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION_TWO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_TWO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PICTURE_TWO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE_TWO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICTURE_TWO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_TWO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPTION_THREE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_THREE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PICTURE_THREE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE_THREE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICTURE_THREE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_THREE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_BOTTOM_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BOTTOM_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BOTTOM_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BOTTOM_PICTURE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_BOTTOM_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_BOTTOM_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BUTTON_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_BUTTON_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_BUTTON_URL = "AAAAAAAAAA";
    private static final String UPDATED_BUTTON_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY_USER_ID = 1L;
    private static final Long UPDATED_CREATED_BY_USER_ID = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private FurtherInfoRepository furtherInfoRepository;

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

    private MockMvc restFurtherInfoMockMvc;

    private FurtherInfo furtherInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FurtherInfoResource furtherInfoResource = new FurtherInfoResource(furtherInfoRepository);
        this.restFurtherInfoMockMvc = MockMvcBuilders.standaloneSetup(furtherInfoResource)
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
    public static FurtherInfo createEntity(EntityManager em) {
        FurtherInfo furtherInfo = new FurtherInfo()
            .activeFurtherInfo(DEFAULT_ACTIVE_FURTHER_INFO)
            .bannerPicture(DEFAULT_BANNER_PICTURE)
            .bannerPictureContentType(DEFAULT_BANNER_PICTURE_CONTENT_TYPE)
            .bannerName(DEFAULT_BANNER_NAME)
            .headerOne(DEFAULT_HEADER_ONE)
            .descriptionOne(DEFAULT_DESCRIPTION_ONE)
            .pictureOne(DEFAULT_PICTURE_ONE)
            .pictureOneContentType(DEFAULT_PICTURE_ONE_CONTENT_TYPE)
            .descriptionTwo(DEFAULT_DESCRIPTION_TWO)
            .pictureTwo(DEFAULT_PICTURE_TWO)
            .pictureTwoContentType(DEFAULT_PICTURE_TWO_CONTENT_TYPE)
            .descriptionThree(DEFAULT_DESCRIPTION_THREE)
            .pictureThree(DEFAULT_PICTURE_THREE)
            .pictureThreeContentType(DEFAULT_PICTURE_THREE_CONTENT_TYPE)
            .bottomPicture(DEFAULT_BOTTOM_PICTURE)
            .bottomPictureContentType(DEFAULT_BOTTOM_PICTURE_CONTENT_TYPE)
            .bottomDescription(DEFAULT_BOTTOM_DESCRIPTION)
            .buttonText(DEFAULT_BUTTON_TEXT)
            .buttonURL(DEFAULT_BUTTON_URL)
            .createdByUserId(DEFAULT_CREATED_BY_USER_ID)
            .createdDate(DEFAULT_CREATED_DATE);
        return furtherInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FurtherInfo createUpdatedEntity(EntityManager em) {
        FurtherInfo furtherInfo = new FurtherInfo()
            .activeFurtherInfo(UPDATED_ACTIVE_FURTHER_INFO)
            .bannerPicture(UPDATED_BANNER_PICTURE)
            .bannerPictureContentType(UPDATED_BANNER_PICTURE_CONTENT_TYPE)
            .bannerName(UPDATED_BANNER_NAME)
            .headerOne(UPDATED_HEADER_ONE)
            .descriptionOne(UPDATED_DESCRIPTION_ONE)
            .pictureOne(UPDATED_PICTURE_ONE)
            .pictureOneContentType(UPDATED_PICTURE_ONE_CONTENT_TYPE)
            .descriptionTwo(UPDATED_DESCRIPTION_TWO)
            .pictureTwo(UPDATED_PICTURE_TWO)
            .pictureTwoContentType(UPDATED_PICTURE_TWO_CONTENT_TYPE)
            .descriptionThree(UPDATED_DESCRIPTION_THREE)
            .pictureThree(UPDATED_PICTURE_THREE)
            .pictureThreeContentType(UPDATED_PICTURE_THREE_CONTENT_TYPE)
            .bottomPicture(UPDATED_BOTTOM_PICTURE)
            .bottomPictureContentType(UPDATED_BOTTOM_PICTURE_CONTENT_TYPE)
            .bottomDescription(UPDATED_BOTTOM_DESCRIPTION)
            .buttonText(UPDATED_BUTTON_TEXT)
            .buttonURL(UPDATED_BUTTON_URL)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);
        return furtherInfo;
    }

    @BeforeEach
    public void initTest() {
        furtherInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createFurtherInfo() throws Exception {
        int databaseSizeBeforeCreate = furtherInfoRepository.findAll().size();

        // Create the FurtherInfo
        restFurtherInfoMockMvc.perform(post("/api/further-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(furtherInfo)))
            .andExpect(status().isCreated());

        // Validate the FurtherInfo in the database
        List<FurtherInfo> furtherInfoList = furtherInfoRepository.findAll();
        assertThat(furtherInfoList).hasSize(databaseSizeBeforeCreate + 1);
        FurtherInfo testFurtherInfo = furtherInfoList.get(furtherInfoList.size() - 1);
        assertThat(testFurtherInfo.isActiveFurtherInfo()).isEqualTo(DEFAULT_ACTIVE_FURTHER_INFO);
        assertThat(testFurtherInfo.getBannerPicture()).isEqualTo(DEFAULT_BANNER_PICTURE);
        assertThat(testFurtherInfo.getBannerPictureContentType()).isEqualTo(DEFAULT_BANNER_PICTURE_CONTENT_TYPE);
        assertThat(testFurtherInfo.getBannerName()).isEqualTo(DEFAULT_BANNER_NAME);
        assertThat(testFurtherInfo.getHeaderOne()).isEqualTo(DEFAULT_HEADER_ONE);
        assertThat(testFurtherInfo.getDescriptionOne()).isEqualTo(DEFAULT_DESCRIPTION_ONE);
        assertThat(testFurtherInfo.getPictureOne()).isEqualTo(DEFAULT_PICTURE_ONE);
        assertThat(testFurtherInfo.getPictureOneContentType()).isEqualTo(DEFAULT_PICTURE_ONE_CONTENT_TYPE);
        assertThat(testFurtherInfo.getDescriptionTwo()).isEqualTo(DEFAULT_DESCRIPTION_TWO);
        assertThat(testFurtherInfo.getPictureTwo()).isEqualTo(DEFAULT_PICTURE_TWO);
        assertThat(testFurtherInfo.getPictureTwoContentType()).isEqualTo(DEFAULT_PICTURE_TWO_CONTENT_TYPE);
        assertThat(testFurtherInfo.getDescriptionThree()).isEqualTo(DEFAULT_DESCRIPTION_THREE);
        assertThat(testFurtherInfo.getPictureThree()).isEqualTo(DEFAULT_PICTURE_THREE);
        assertThat(testFurtherInfo.getPictureThreeContentType()).isEqualTo(DEFAULT_PICTURE_THREE_CONTENT_TYPE);
        assertThat(testFurtherInfo.getBottomPicture()).isEqualTo(DEFAULT_BOTTOM_PICTURE);
        assertThat(testFurtherInfo.getBottomPictureContentType()).isEqualTo(DEFAULT_BOTTOM_PICTURE_CONTENT_TYPE);
        assertThat(testFurtherInfo.getBottomDescription()).isEqualTo(DEFAULT_BOTTOM_DESCRIPTION);
        assertThat(testFurtherInfo.getButtonText()).isEqualTo(DEFAULT_BUTTON_TEXT);
        assertThat(testFurtherInfo.getButtonURL()).isEqualTo(DEFAULT_BUTTON_URL);
        assertThat(testFurtherInfo.getCreatedByUserId()).isEqualTo(DEFAULT_CREATED_BY_USER_ID);
        assertThat(testFurtherInfo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createFurtherInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = furtherInfoRepository.findAll().size();

        // Create the FurtherInfo with an existing ID
        furtherInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFurtherInfoMockMvc.perform(post("/api/further-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(furtherInfo)))
            .andExpect(status().isBadRequest());

        // Validate the FurtherInfo in the database
        List<FurtherInfo> furtherInfoList = furtherInfoRepository.findAll();
        assertThat(furtherInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActiveFurtherInfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = furtherInfoRepository.findAll().size();
        // set the field null
        furtherInfo.setActiveFurtherInfo(null);

        // Create the FurtherInfo, which fails.

        restFurtherInfoMockMvc.perform(post("/api/further-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(furtherInfo)))
            .andExpect(status().isBadRequest());

        List<FurtherInfo> furtherInfoList = furtherInfoRepository.findAll();
        assertThat(furtherInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBannerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = furtherInfoRepository.findAll().size();
        // set the field null
        furtherInfo.setBannerName(null);

        // Create the FurtherInfo, which fails.

        restFurtherInfoMockMvc.perform(post("/api/further-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(furtherInfo)))
            .andExpect(status().isBadRequest());

        List<FurtherInfo> furtherInfoList = furtherInfoRepository.findAll();
        assertThat(furtherInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeaderOneIsRequired() throws Exception {
        int databaseSizeBeforeTest = furtherInfoRepository.findAll().size();
        // set the field null
        furtherInfo.setHeaderOne(null);

        // Create the FurtherInfo, which fails.

        restFurtherInfoMockMvc.perform(post("/api/further-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(furtherInfo)))
            .andExpect(status().isBadRequest());

        List<FurtherInfo> furtherInfoList = furtherInfoRepository.findAll();
        assertThat(furtherInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBottomDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = furtherInfoRepository.findAll().size();
        // set the field null
        furtherInfo.setBottomDescription(null);

        // Create the FurtherInfo, which fails.

        restFurtherInfoMockMvc.perform(post("/api/further-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(furtherInfo)))
            .andExpect(status().isBadRequest());

        List<FurtherInfo> furtherInfoList = furtherInfoRepository.findAll();
        assertThat(furtherInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFurtherInfos() throws Exception {
        // Initialize the database
        furtherInfoRepository.saveAndFlush(furtherInfo);

        // Get all the furtherInfoList
        restFurtherInfoMockMvc.perform(get("/api/further-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(furtherInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].activeFurtherInfo").value(hasItem(DEFAULT_ACTIVE_FURTHER_INFO.booleanValue())))
            .andExpect(jsonPath("$.[*].bannerPictureContentType").value(hasItem(DEFAULT_BANNER_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bannerPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANNER_PICTURE))))
            .andExpect(jsonPath("$.[*].bannerName").value(hasItem(DEFAULT_BANNER_NAME)))
            .andExpect(jsonPath("$.[*].headerOne").value(hasItem(DEFAULT_HEADER_ONE)))
            .andExpect(jsonPath("$.[*].descriptionOne").value(hasItem(DEFAULT_DESCRIPTION_ONE.toString())))
            .andExpect(jsonPath("$.[*].pictureOneContentType").value(hasItem(DEFAULT_PICTURE_ONE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pictureOne").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE_ONE))))
            .andExpect(jsonPath("$.[*].descriptionTwo").value(hasItem(DEFAULT_DESCRIPTION_TWO.toString())))
            .andExpect(jsonPath("$.[*].pictureTwoContentType").value(hasItem(DEFAULT_PICTURE_TWO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pictureTwo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE_TWO))))
            .andExpect(jsonPath("$.[*].descriptionThree").value(hasItem(DEFAULT_DESCRIPTION_THREE.toString())))
            .andExpect(jsonPath("$.[*].pictureThreeContentType").value(hasItem(DEFAULT_PICTURE_THREE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pictureThree").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE_THREE))))
            .andExpect(jsonPath("$.[*].bottomPictureContentType").value(hasItem(DEFAULT_BOTTOM_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bottomPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_BOTTOM_PICTURE))))
            .andExpect(jsonPath("$.[*].bottomDescription").value(hasItem(DEFAULT_BOTTOM_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].buttonText").value(hasItem(DEFAULT_BUTTON_TEXT)))
            .andExpect(jsonPath("$.[*].buttonURL").value(hasItem(DEFAULT_BUTTON_URL)))
            .andExpect(jsonPath("$.[*].createdByUserId").value(hasItem(DEFAULT_CREATED_BY_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getFurtherInfo() throws Exception {
        // Initialize the database
        furtherInfoRepository.saveAndFlush(furtherInfo);

        // Get the furtherInfo
        restFurtherInfoMockMvc.perform(get("/api/further-infos/{id}", furtherInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(furtherInfo.getId().intValue()))
            .andExpect(jsonPath("$.activeFurtherInfo").value(DEFAULT_ACTIVE_FURTHER_INFO.booleanValue()))
            .andExpect(jsonPath("$.bannerPictureContentType").value(DEFAULT_BANNER_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.bannerPicture").value(Base64Utils.encodeToString(DEFAULT_BANNER_PICTURE)))
            .andExpect(jsonPath("$.bannerName").value(DEFAULT_BANNER_NAME))
            .andExpect(jsonPath("$.headerOne").value(DEFAULT_HEADER_ONE))
            .andExpect(jsonPath("$.descriptionOne").value(DEFAULT_DESCRIPTION_ONE.toString()))
            .andExpect(jsonPath("$.pictureOneContentType").value(DEFAULT_PICTURE_ONE_CONTENT_TYPE))
            .andExpect(jsonPath("$.pictureOne").value(Base64Utils.encodeToString(DEFAULT_PICTURE_ONE)))
            .andExpect(jsonPath("$.descriptionTwo").value(DEFAULT_DESCRIPTION_TWO.toString()))
            .andExpect(jsonPath("$.pictureTwoContentType").value(DEFAULT_PICTURE_TWO_CONTENT_TYPE))
            .andExpect(jsonPath("$.pictureTwo").value(Base64Utils.encodeToString(DEFAULT_PICTURE_TWO)))
            .andExpect(jsonPath("$.descriptionThree").value(DEFAULT_DESCRIPTION_THREE.toString()))
            .andExpect(jsonPath("$.pictureThreeContentType").value(DEFAULT_PICTURE_THREE_CONTENT_TYPE))
            .andExpect(jsonPath("$.pictureThree").value(Base64Utils.encodeToString(DEFAULT_PICTURE_THREE)))
            .andExpect(jsonPath("$.bottomPictureContentType").value(DEFAULT_BOTTOM_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.bottomPicture").value(Base64Utils.encodeToString(DEFAULT_BOTTOM_PICTURE)))
            .andExpect(jsonPath("$.bottomDescription").value(DEFAULT_BOTTOM_DESCRIPTION))
            .andExpect(jsonPath("$.buttonText").value(DEFAULT_BUTTON_TEXT))
            .andExpect(jsonPath("$.buttonURL").value(DEFAULT_BUTTON_URL))
            .andExpect(jsonPath("$.createdByUserId").value(DEFAULT_CREATED_BY_USER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFurtherInfo() throws Exception {
        // Get the furtherInfo
        restFurtherInfoMockMvc.perform(get("/api/further-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFurtherInfo() throws Exception {
        // Initialize the database
        furtherInfoRepository.saveAndFlush(furtherInfo);

        int databaseSizeBeforeUpdate = furtherInfoRepository.findAll().size();

        // Update the furtherInfo
        FurtherInfo updatedFurtherInfo = furtherInfoRepository.findById(furtherInfo.getId()).get();
        // Disconnect from session so that the updates on updatedFurtherInfo are not directly saved in db
        em.detach(updatedFurtherInfo);
        updatedFurtherInfo
            .activeFurtherInfo(UPDATED_ACTIVE_FURTHER_INFO)
            .bannerPicture(UPDATED_BANNER_PICTURE)
            .bannerPictureContentType(UPDATED_BANNER_PICTURE_CONTENT_TYPE)
            .bannerName(UPDATED_BANNER_NAME)
            .headerOne(UPDATED_HEADER_ONE)
            .descriptionOne(UPDATED_DESCRIPTION_ONE)
            .pictureOne(UPDATED_PICTURE_ONE)
            .pictureOneContentType(UPDATED_PICTURE_ONE_CONTENT_TYPE)
            .descriptionTwo(UPDATED_DESCRIPTION_TWO)
            .pictureTwo(UPDATED_PICTURE_TWO)
            .pictureTwoContentType(UPDATED_PICTURE_TWO_CONTENT_TYPE)
            .descriptionThree(UPDATED_DESCRIPTION_THREE)
            .pictureThree(UPDATED_PICTURE_THREE)
            .pictureThreeContentType(UPDATED_PICTURE_THREE_CONTENT_TYPE)
            .bottomPicture(UPDATED_BOTTOM_PICTURE)
            .bottomPictureContentType(UPDATED_BOTTOM_PICTURE_CONTENT_TYPE)
            .bottomDescription(UPDATED_BOTTOM_DESCRIPTION)
            .buttonText(UPDATED_BUTTON_TEXT)
            .buttonURL(UPDATED_BUTTON_URL)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);

        restFurtherInfoMockMvc.perform(put("/api/further-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFurtherInfo)))
            .andExpect(status().isOk());

        // Validate the FurtherInfo in the database
        List<FurtherInfo> furtherInfoList = furtherInfoRepository.findAll();
        assertThat(furtherInfoList).hasSize(databaseSizeBeforeUpdate);
        FurtherInfo testFurtherInfo = furtherInfoList.get(furtherInfoList.size() - 1);
        assertThat(testFurtherInfo.isActiveFurtherInfo()).isEqualTo(UPDATED_ACTIVE_FURTHER_INFO);
        assertThat(testFurtherInfo.getBannerPicture()).isEqualTo(UPDATED_BANNER_PICTURE);
        assertThat(testFurtherInfo.getBannerPictureContentType()).isEqualTo(UPDATED_BANNER_PICTURE_CONTENT_TYPE);
        assertThat(testFurtherInfo.getBannerName()).isEqualTo(UPDATED_BANNER_NAME);
        assertThat(testFurtherInfo.getHeaderOne()).isEqualTo(UPDATED_HEADER_ONE);
        assertThat(testFurtherInfo.getDescriptionOne()).isEqualTo(UPDATED_DESCRIPTION_ONE);
        assertThat(testFurtherInfo.getPictureOne()).isEqualTo(UPDATED_PICTURE_ONE);
        assertThat(testFurtherInfo.getPictureOneContentType()).isEqualTo(UPDATED_PICTURE_ONE_CONTENT_TYPE);
        assertThat(testFurtherInfo.getDescriptionTwo()).isEqualTo(UPDATED_DESCRIPTION_TWO);
        assertThat(testFurtherInfo.getPictureTwo()).isEqualTo(UPDATED_PICTURE_TWO);
        assertThat(testFurtherInfo.getPictureTwoContentType()).isEqualTo(UPDATED_PICTURE_TWO_CONTENT_TYPE);
        assertThat(testFurtherInfo.getDescriptionThree()).isEqualTo(UPDATED_DESCRIPTION_THREE);
        assertThat(testFurtherInfo.getPictureThree()).isEqualTo(UPDATED_PICTURE_THREE);
        assertThat(testFurtherInfo.getPictureThreeContentType()).isEqualTo(UPDATED_PICTURE_THREE_CONTENT_TYPE);
        assertThat(testFurtherInfo.getBottomPicture()).isEqualTo(UPDATED_BOTTOM_PICTURE);
        assertThat(testFurtherInfo.getBottomPictureContentType()).isEqualTo(UPDATED_BOTTOM_PICTURE_CONTENT_TYPE);
        assertThat(testFurtherInfo.getBottomDescription()).isEqualTo(UPDATED_BOTTOM_DESCRIPTION);
        assertThat(testFurtherInfo.getButtonText()).isEqualTo(UPDATED_BUTTON_TEXT);
        assertThat(testFurtherInfo.getButtonURL()).isEqualTo(UPDATED_BUTTON_URL);
        assertThat(testFurtherInfo.getCreatedByUserId()).isEqualTo(UPDATED_CREATED_BY_USER_ID);
        assertThat(testFurtherInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFurtherInfo() throws Exception {
        int databaseSizeBeforeUpdate = furtherInfoRepository.findAll().size();

        // Create the FurtherInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFurtherInfoMockMvc.perform(put("/api/further-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(furtherInfo)))
            .andExpect(status().isBadRequest());

        // Validate the FurtherInfo in the database
        List<FurtherInfo> furtherInfoList = furtherInfoRepository.findAll();
        assertThat(furtherInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFurtherInfo() throws Exception {
        // Initialize the database
        furtherInfoRepository.saveAndFlush(furtherInfo);

        int databaseSizeBeforeDelete = furtherInfoRepository.findAll().size();

        // Delete the furtherInfo
        restFurtherInfoMockMvc.perform(delete("/api/further-infos/{id}", furtherInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FurtherInfo> furtherInfoList = furtherInfoRepository.findAll();
        assertThat(furtherInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FurtherInfo.class);
        FurtherInfo furtherInfo1 = new FurtherInfo();
        furtherInfo1.setId(1L);
        FurtherInfo furtherInfo2 = new FurtherInfo();
        furtherInfo2.setId(furtherInfo1.getId());
        assertThat(furtherInfo1).isEqualTo(furtherInfo2);
        furtherInfo2.setId(2L);
        assertThat(furtherInfo1).isNotEqualTo(furtherInfo2);
        furtherInfo1.setId(null);
        assertThat(furtherInfo1).isNotEqualTo(furtherInfo2);
    }
}
