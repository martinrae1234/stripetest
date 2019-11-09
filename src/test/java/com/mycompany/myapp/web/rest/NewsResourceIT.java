package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.WebApp;
import com.mycompany.myapp.domain.News;
import com.mycompany.myapp.repository.NewsRepository;
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
 * Integration tests for the {@link NewsResource} REST controller.
 */
@SpringBootTest(classes = WebApp.class)
public class NewsResourceIT {

    private static final Boolean DEFAULT_ACTIVE_MESSAGE = false;
    private static final Boolean UPDATED_ACTIVE_MESSAGE = true;

    private static final byte[] DEFAULT_BANNER_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BANNER_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BANNER_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BANNER_PICTURE_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_DATE_OF_NEWS_CREATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_NEWS_CREATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BANNER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_NAME = "BBBBBBBBBB";

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
    private NewsRepository newsRepository;

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

    private MockMvc restNewsMockMvc;

    private News news;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NewsResource newsResource = new NewsResource(newsRepository);
        this.restNewsMockMvc = MockMvcBuilders.standaloneSetup(newsResource)
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
    public static News createEntity(EntityManager em) {
        News news = new News()
            .activeMessage(DEFAULT_ACTIVE_MESSAGE)
            .bannerPicture(DEFAULT_BANNER_PICTURE)
            .bannerPictureContentType(DEFAULT_BANNER_PICTURE_CONTENT_TYPE)
            .dateOfNewsCreation(DEFAULT_DATE_OF_NEWS_CREATION)
            .bannerName(DEFAULT_BANNER_NAME)
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
        return news;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static News createUpdatedEntity(EntityManager em) {
        News news = new News()
            .activeMessage(UPDATED_ACTIVE_MESSAGE)
            .bannerPicture(UPDATED_BANNER_PICTURE)
            .bannerPictureContentType(UPDATED_BANNER_PICTURE_CONTENT_TYPE)
            .dateOfNewsCreation(UPDATED_DATE_OF_NEWS_CREATION)
            .bannerName(UPDATED_BANNER_NAME)
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
        return news;
    }

    @BeforeEach
    public void initTest() {
        news = createEntity(em);
    }

    @Test
    @Transactional
    public void createNews() throws Exception {
        int databaseSizeBeforeCreate = newsRepository.findAll().size();

        // Create the News
        restNewsMockMvc.perform(post("/api/news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(news)))
            .andExpect(status().isCreated());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeCreate + 1);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.isActiveMessage()).isEqualTo(DEFAULT_ACTIVE_MESSAGE);
        assertThat(testNews.getBannerPicture()).isEqualTo(DEFAULT_BANNER_PICTURE);
        assertThat(testNews.getBannerPictureContentType()).isEqualTo(DEFAULT_BANNER_PICTURE_CONTENT_TYPE);
        assertThat(testNews.getDateOfNewsCreation()).isEqualTo(DEFAULT_DATE_OF_NEWS_CREATION);
        assertThat(testNews.getBannerName()).isEqualTo(DEFAULT_BANNER_NAME);
        assertThat(testNews.getDescriptionOne()).isEqualTo(DEFAULT_DESCRIPTION_ONE);
        assertThat(testNews.getPictureOne()).isEqualTo(DEFAULT_PICTURE_ONE);
        assertThat(testNews.getPictureOneContentType()).isEqualTo(DEFAULT_PICTURE_ONE_CONTENT_TYPE);
        assertThat(testNews.getDescriptionTwo()).isEqualTo(DEFAULT_DESCRIPTION_TWO);
        assertThat(testNews.getPictureTwo()).isEqualTo(DEFAULT_PICTURE_TWO);
        assertThat(testNews.getPictureTwoContentType()).isEqualTo(DEFAULT_PICTURE_TWO_CONTENT_TYPE);
        assertThat(testNews.getDescriptionThree()).isEqualTo(DEFAULT_DESCRIPTION_THREE);
        assertThat(testNews.getPictureThree()).isEqualTo(DEFAULT_PICTURE_THREE);
        assertThat(testNews.getPictureThreeContentType()).isEqualTo(DEFAULT_PICTURE_THREE_CONTENT_TYPE);
        assertThat(testNews.getBottomPicture()).isEqualTo(DEFAULT_BOTTOM_PICTURE);
        assertThat(testNews.getBottomPictureContentType()).isEqualTo(DEFAULT_BOTTOM_PICTURE_CONTENT_TYPE);
        assertThat(testNews.getBottomDescription()).isEqualTo(DEFAULT_BOTTOM_DESCRIPTION);
        assertThat(testNews.getButtonText()).isEqualTo(DEFAULT_BUTTON_TEXT);
        assertThat(testNews.getButtonURL()).isEqualTo(DEFAULT_BUTTON_URL);
        assertThat(testNews.getCreatedByUserId()).isEqualTo(DEFAULT_CREATED_BY_USER_ID);
        assertThat(testNews.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createNewsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = newsRepository.findAll().size();

        // Create the News with an existing ID
        news.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewsMockMvc.perform(post("/api/news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(news)))
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActiveMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setActiveMessage(null);

        // Create the News, which fails.

        restNewsMockMvc.perform(post("/api/news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(news)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBannerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setBannerName(null);

        // Create the News, which fails.

        restNewsMockMvc.perform(post("/api/news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(news)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBottomDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setBottomDescription(null);

        // Create the News, which fails.

        restNewsMockMvc.perform(post("/api/news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(news)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList
        restNewsMockMvc.perform(get("/api/news?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(news.getId().intValue())))
            .andExpect(jsonPath("$.[*].activeMessage").value(hasItem(DEFAULT_ACTIVE_MESSAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].bannerPictureContentType").value(hasItem(DEFAULT_BANNER_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bannerPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANNER_PICTURE))))
            .andExpect(jsonPath("$.[*].dateOfNewsCreation").value(hasItem(DEFAULT_DATE_OF_NEWS_CREATION.toString())))
            .andExpect(jsonPath("$.[*].bannerName").value(hasItem(DEFAULT_BANNER_NAME)))
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
    public void getNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get the news
        restNewsMockMvc.perform(get("/api/news/{id}", news.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(news.getId().intValue()))
            .andExpect(jsonPath("$.activeMessage").value(DEFAULT_ACTIVE_MESSAGE.booleanValue()))
            .andExpect(jsonPath("$.bannerPictureContentType").value(DEFAULT_BANNER_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.bannerPicture").value(Base64Utils.encodeToString(DEFAULT_BANNER_PICTURE)))
            .andExpect(jsonPath("$.dateOfNewsCreation").value(DEFAULT_DATE_OF_NEWS_CREATION.toString()))
            .andExpect(jsonPath("$.bannerName").value(DEFAULT_BANNER_NAME))
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
    public void getNonExistingNews() throws Exception {
        // Get the news
        restNewsMockMvc.perform(get("/api/news/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Update the news
        News updatedNews = newsRepository.findById(news.getId()).get();
        // Disconnect from session so that the updates on updatedNews are not directly saved in db
        em.detach(updatedNews);
        updatedNews
            .activeMessage(UPDATED_ACTIVE_MESSAGE)
            .bannerPicture(UPDATED_BANNER_PICTURE)
            .bannerPictureContentType(UPDATED_BANNER_PICTURE_CONTENT_TYPE)
            .dateOfNewsCreation(UPDATED_DATE_OF_NEWS_CREATION)
            .bannerName(UPDATED_BANNER_NAME)
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

        restNewsMockMvc.perform(put("/api/news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNews)))
            .andExpect(status().isOk());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.isActiveMessage()).isEqualTo(UPDATED_ACTIVE_MESSAGE);
        assertThat(testNews.getBannerPicture()).isEqualTo(UPDATED_BANNER_PICTURE);
        assertThat(testNews.getBannerPictureContentType()).isEqualTo(UPDATED_BANNER_PICTURE_CONTENT_TYPE);
        assertThat(testNews.getDateOfNewsCreation()).isEqualTo(UPDATED_DATE_OF_NEWS_CREATION);
        assertThat(testNews.getBannerName()).isEqualTo(UPDATED_BANNER_NAME);
        assertThat(testNews.getDescriptionOne()).isEqualTo(UPDATED_DESCRIPTION_ONE);
        assertThat(testNews.getPictureOne()).isEqualTo(UPDATED_PICTURE_ONE);
        assertThat(testNews.getPictureOneContentType()).isEqualTo(UPDATED_PICTURE_ONE_CONTENT_TYPE);
        assertThat(testNews.getDescriptionTwo()).isEqualTo(UPDATED_DESCRIPTION_TWO);
        assertThat(testNews.getPictureTwo()).isEqualTo(UPDATED_PICTURE_TWO);
        assertThat(testNews.getPictureTwoContentType()).isEqualTo(UPDATED_PICTURE_TWO_CONTENT_TYPE);
        assertThat(testNews.getDescriptionThree()).isEqualTo(UPDATED_DESCRIPTION_THREE);
        assertThat(testNews.getPictureThree()).isEqualTo(UPDATED_PICTURE_THREE);
        assertThat(testNews.getPictureThreeContentType()).isEqualTo(UPDATED_PICTURE_THREE_CONTENT_TYPE);
        assertThat(testNews.getBottomPicture()).isEqualTo(UPDATED_BOTTOM_PICTURE);
        assertThat(testNews.getBottomPictureContentType()).isEqualTo(UPDATED_BOTTOM_PICTURE_CONTENT_TYPE);
        assertThat(testNews.getBottomDescription()).isEqualTo(UPDATED_BOTTOM_DESCRIPTION);
        assertThat(testNews.getButtonText()).isEqualTo(UPDATED_BUTTON_TEXT);
        assertThat(testNews.getButtonURL()).isEqualTo(UPDATED_BUTTON_URL);
        assertThat(testNews.getCreatedByUserId()).isEqualTo(UPDATED_CREATED_BY_USER_ID);
        assertThat(testNews.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Create the News

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsMockMvc.perform(put("/api/news")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(news)))
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeDelete = newsRepository.findAll().size();

        // Delete the news
        restNewsMockMvc.perform(delete("/api/news/{id}", news.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(News.class);
        News news1 = new News();
        news1.setId(1L);
        News news2 = new News();
        news2.setId(news1.getId());
        assertThat(news1).isEqualTo(news2);
        news2.setId(2L);
        assertThat(news1).isNotEqualTo(news2);
        news1.setId(null);
        assertThat(news1).isNotEqualTo(news2);
    }
}
