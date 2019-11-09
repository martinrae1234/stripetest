package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.WebApp;
import com.mycompany.myapp.domain.Message;
import com.mycompany.myapp.repository.MessageRepository;
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
 * Integration tests for the {@link MessageResource} REST controller.
 */
@SpringBootTest(classes = WebApp.class)
public class MessageResourceIT {

    private static final Boolean DEFAULT_ACTIVE_MESSAGE = false;
    private static final Boolean UPDATED_ACTIVE_MESSAGE = true;

    private static final byte[] DEFAULT_BANNER_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BANNER_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BANNER_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BANNER_PICTURE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_BANNER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HEADING_ONE = "AAAAAAAAAA";
    private static final String UPDATED_HEADING_ONE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_ONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_ONE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_QUOTE_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_QUOTE_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_QUOTE_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_QUOTE_PICTURE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_QUOTE_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_QUOTE_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_HEADER_TWO = "AAAAAAAAAA";
    private static final String UPDATED_HEADER_TWO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_TWO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_TWO = "BBBBBBBBBB";

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
    private MessageRepository messageRepository;

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

    private MockMvc restMessageMockMvc;

    private Message message;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MessageResource messageResource = new MessageResource(messageRepository);
        this.restMessageMockMvc = MockMvcBuilders.standaloneSetup(messageResource)
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
    public static Message createEntity(EntityManager em) {
        Message message = new Message()
            .activeMessage(DEFAULT_ACTIVE_MESSAGE)
            .bannerPicture(DEFAULT_BANNER_PICTURE)
            .bannerPictureContentType(DEFAULT_BANNER_PICTURE_CONTENT_TYPE)
            .bannerName(DEFAULT_BANNER_NAME)
            .headingOne(DEFAULT_HEADING_ONE)
            .descriptionOne(DEFAULT_DESCRIPTION_ONE)
            .quotePicture(DEFAULT_QUOTE_PICTURE)
            .quotePictureContentType(DEFAULT_QUOTE_PICTURE_CONTENT_TYPE)
            .quoteContent(DEFAULT_QUOTE_CONTENT)
            .headerTwo(DEFAULT_HEADER_TWO)
            .descriptionTwo(DEFAULT_DESCRIPTION_TWO)
            .bottomPicture(DEFAULT_BOTTOM_PICTURE)
            .bottomPictureContentType(DEFAULT_BOTTOM_PICTURE_CONTENT_TYPE)
            .bottomDescription(DEFAULT_BOTTOM_DESCRIPTION)
            .buttonText(DEFAULT_BUTTON_TEXT)
            .buttonURL(DEFAULT_BUTTON_URL)
            .createdByUserId(DEFAULT_CREATED_BY_USER_ID)
            .createdDate(DEFAULT_CREATED_DATE);
        return message;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Message createUpdatedEntity(EntityManager em) {
        Message message = new Message()
            .activeMessage(UPDATED_ACTIVE_MESSAGE)
            .bannerPicture(UPDATED_BANNER_PICTURE)
            .bannerPictureContentType(UPDATED_BANNER_PICTURE_CONTENT_TYPE)
            .bannerName(UPDATED_BANNER_NAME)
            .headingOne(UPDATED_HEADING_ONE)
            .descriptionOne(UPDATED_DESCRIPTION_ONE)
            .quotePicture(UPDATED_QUOTE_PICTURE)
            .quotePictureContentType(UPDATED_QUOTE_PICTURE_CONTENT_TYPE)
            .quoteContent(UPDATED_QUOTE_CONTENT)
            .headerTwo(UPDATED_HEADER_TWO)
            .descriptionTwo(UPDATED_DESCRIPTION_TWO)
            .bottomPicture(UPDATED_BOTTOM_PICTURE)
            .bottomPictureContentType(UPDATED_BOTTOM_PICTURE_CONTENT_TYPE)
            .bottomDescription(UPDATED_BOTTOM_DESCRIPTION)
            .buttonText(UPDATED_BUTTON_TEXT)
            .buttonURL(UPDATED_BUTTON_URL)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);
        return message;
    }

    @BeforeEach
    public void initTest() {
        message = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessage() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // Create the Message
        restMessageMockMvc.perform(post("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(message)))
            .andExpect(status().isCreated());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate + 1);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.isActiveMessage()).isEqualTo(DEFAULT_ACTIVE_MESSAGE);
        assertThat(testMessage.getBannerPicture()).isEqualTo(DEFAULT_BANNER_PICTURE);
        assertThat(testMessage.getBannerPictureContentType()).isEqualTo(DEFAULT_BANNER_PICTURE_CONTENT_TYPE);
        assertThat(testMessage.getBannerName()).isEqualTo(DEFAULT_BANNER_NAME);
        assertThat(testMessage.getHeadingOne()).isEqualTo(DEFAULT_HEADING_ONE);
        assertThat(testMessage.getDescriptionOne()).isEqualTo(DEFAULT_DESCRIPTION_ONE);
        assertThat(testMessage.getQuotePicture()).isEqualTo(DEFAULT_QUOTE_PICTURE);
        assertThat(testMessage.getQuotePictureContentType()).isEqualTo(DEFAULT_QUOTE_PICTURE_CONTENT_TYPE);
        assertThat(testMessage.getQuoteContent()).isEqualTo(DEFAULT_QUOTE_CONTENT);
        assertThat(testMessage.getHeaderTwo()).isEqualTo(DEFAULT_HEADER_TWO);
        assertThat(testMessage.getDescriptionTwo()).isEqualTo(DEFAULT_DESCRIPTION_TWO);
        assertThat(testMessage.getBottomPicture()).isEqualTo(DEFAULT_BOTTOM_PICTURE);
        assertThat(testMessage.getBottomPictureContentType()).isEqualTo(DEFAULT_BOTTOM_PICTURE_CONTENT_TYPE);
        assertThat(testMessage.getBottomDescription()).isEqualTo(DEFAULT_BOTTOM_DESCRIPTION);
        assertThat(testMessage.getButtonText()).isEqualTo(DEFAULT_BUTTON_TEXT);
        assertThat(testMessage.getButtonURL()).isEqualTo(DEFAULT_BUTTON_URL);
        assertThat(testMessage.getCreatedByUserId()).isEqualTo(DEFAULT_CREATED_BY_USER_ID);
        assertThat(testMessage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // Create the Message with an existing ID
        message.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageMockMvc.perform(post("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(message)))
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActiveMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageRepository.findAll().size();
        // set the field null
        message.setActiveMessage(null);

        // Create the Message, which fails.

        restMessageMockMvc.perform(post("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(message)))
            .andExpect(status().isBadRequest());

        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBannerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageRepository.findAll().size();
        // set the field null
        message.setBannerName(null);

        // Create the Message, which fails.

        restMessageMockMvc.perform(post("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(message)))
            .andExpect(status().isBadRequest());

        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeadingOneIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageRepository.findAll().size();
        // set the field null
        message.setHeadingOne(null);

        // Create the Message, which fails.

        restMessageMockMvc.perform(post("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(message)))
            .andExpect(status().isBadRequest());

        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMessages() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList
        restMessageMockMvc.perform(get("/api/messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(message.getId().intValue())))
            .andExpect(jsonPath("$.[*].activeMessage").value(hasItem(DEFAULT_ACTIVE_MESSAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].bannerPictureContentType").value(hasItem(DEFAULT_BANNER_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bannerPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_BANNER_PICTURE))))
            .andExpect(jsonPath("$.[*].bannerName").value(hasItem(DEFAULT_BANNER_NAME)))
            .andExpect(jsonPath("$.[*].headingOne").value(hasItem(DEFAULT_HEADING_ONE)))
            .andExpect(jsonPath("$.[*].descriptionOne").value(hasItem(DEFAULT_DESCRIPTION_ONE.toString())))
            .andExpect(jsonPath("$.[*].quotePictureContentType").value(hasItem(DEFAULT_QUOTE_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].quotePicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_QUOTE_PICTURE))))
            .andExpect(jsonPath("$.[*].quoteContent").value(hasItem(DEFAULT_QUOTE_CONTENT)))
            .andExpect(jsonPath("$.[*].headerTwo").value(hasItem(DEFAULT_HEADER_TWO)))
            .andExpect(jsonPath("$.[*].descriptionTwo").value(hasItem(DEFAULT_DESCRIPTION_TWO.toString())))
            .andExpect(jsonPath("$.[*].bottomPictureContentType").value(hasItem(DEFAULT_BOTTOM_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bottomPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_BOTTOM_PICTURE))))
            .andExpect(jsonPath("$.[*].bottomDescription").value(hasItem(DEFAULT_BOTTOM_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].buttonText").value(hasItem(DEFAULT_BUTTON_TEXT)))
            .andExpect(jsonPath("$.[*].buttonURL").value(hasItem(DEFAULT_BUTTON_URL)))
            .andExpect(jsonPath("$.[*].createdByUserId").value(hasItem(DEFAULT_CREATED_BY_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get the message
        restMessageMockMvc.perform(get("/api/messages/{id}", message.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(message.getId().intValue()))
            .andExpect(jsonPath("$.activeMessage").value(DEFAULT_ACTIVE_MESSAGE.booleanValue()))
            .andExpect(jsonPath("$.bannerPictureContentType").value(DEFAULT_BANNER_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.bannerPicture").value(Base64Utils.encodeToString(DEFAULT_BANNER_PICTURE)))
            .andExpect(jsonPath("$.bannerName").value(DEFAULT_BANNER_NAME))
            .andExpect(jsonPath("$.headingOne").value(DEFAULT_HEADING_ONE))
            .andExpect(jsonPath("$.descriptionOne").value(DEFAULT_DESCRIPTION_ONE.toString()))
            .andExpect(jsonPath("$.quotePictureContentType").value(DEFAULT_QUOTE_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.quotePicture").value(Base64Utils.encodeToString(DEFAULT_QUOTE_PICTURE)))
            .andExpect(jsonPath("$.quoteContent").value(DEFAULT_QUOTE_CONTENT))
            .andExpect(jsonPath("$.headerTwo").value(DEFAULT_HEADER_TWO))
            .andExpect(jsonPath("$.descriptionTwo").value(DEFAULT_DESCRIPTION_TWO.toString()))
            .andExpect(jsonPath("$.bottomPictureContentType").value(DEFAULT_BOTTOM_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.bottomPicture").value(Base64Utils.encodeToString(DEFAULT_BOTTOM_PICTURE)))
            .andExpect(jsonPath("$.bottomDescription").value(DEFAULT_BOTTOM_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.buttonText").value(DEFAULT_BUTTON_TEXT))
            .andExpect(jsonPath("$.buttonURL").value(DEFAULT_BUTTON_URL))
            .andExpect(jsonPath("$.createdByUserId").value(DEFAULT_CREATED_BY_USER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMessage() throws Exception {
        // Get the message
        restMessageMockMvc.perform(get("/api/messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Update the message
        Message updatedMessage = messageRepository.findById(message.getId()).get();
        // Disconnect from session so that the updates on updatedMessage are not directly saved in db
        em.detach(updatedMessage);
        updatedMessage
            .activeMessage(UPDATED_ACTIVE_MESSAGE)
            .bannerPicture(UPDATED_BANNER_PICTURE)
            .bannerPictureContentType(UPDATED_BANNER_PICTURE_CONTENT_TYPE)
            .bannerName(UPDATED_BANNER_NAME)
            .headingOne(UPDATED_HEADING_ONE)
            .descriptionOne(UPDATED_DESCRIPTION_ONE)
            .quotePicture(UPDATED_QUOTE_PICTURE)
            .quotePictureContentType(UPDATED_QUOTE_PICTURE_CONTENT_TYPE)
            .quoteContent(UPDATED_QUOTE_CONTENT)
            .headerTwo(UPDATED_HEADER_TWO)
            .descriptionTwo(UPDATED_DESCRIPTION_TWO)
            .bottomPicture(UPDATED_BOTTOM_PICTURE)
            .bottomPictureContentType(UPDATED_BOTTOM_PICTURE_CONTENT_TYPE)
            .bottomDescription(UPDATED_BOTTOM_DESCRIPTION)
            .buttonText(UPDATED_BUTTON_TEXT)
            .buttonURL(UPDATED_BUTTON_URL)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);

        restMessageMockMvc.perform(put("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMessage)))
            .andExpect(status().isOk());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.isActiveMessage()).isEqualTo(UPDATED_ACTIVE_MESSAGE);
        assertThat(testMessage.getBannerPicture()).isEqualTo(UPDATED_BANNER_PICTURE);
        assertThat(testMessage.getBannerPictureContentType()).isEqualTo(UPDATED_BANNER_PICTURE_CONTENT_TYPE);
        assertThat(testMessage.getBannerName()).isEqualTo(UPDATED_BANNER_NAME);
        assertThat(testMessage.getHeadingOne()).isEqualTo(UPDATED_HEADING_ONE);
        assertThat(testMessage.getDescriptionOne()).isEqualTo(UPDATED_DESCRIPTION_ONE);
        assertThat(testMessage.getQuotePicture()).isEqualTo(UPDATED_QUOTE_PICTURE);
        assertThat(testMessage.getQuotePictureContentType()).isEqualTo(UPDATED_QUOTE_PICTURE_CONTENT_TYPE);
        assertThat(testMessage.getQuoteContent()).isEqualTo(UPDATED_QUOTE_CONTENT);
        assertThat(testMessage.getHeaderTwo()).isEqualTo(UPDATED_HEADER_TWO);
        assertThat(testMessage.getDescriptionTwo()).isEqualTo(UPDATED_DESCRIPTION_TWO);
        assertThat(testMessage.getBottomPicture()).isEqualTo(UPDATED_BOTTOM_PICTURE);
        assertThat(testMessage.getBottomPictureContentType()).isEqualTo(UPDATED_BOTTOM_PICTURE_CONTENT_TYPE);
        assertThat(testMessage.getBottomDescription()).isEqualTo(UPDATED_BOTTOM_DESCRIPTION);
        assertThat(testMessage.getButtonText()).isEqualTo(UPDATED_BUTTON_TEXT);
        assertThat(testMessage.getButtonURL()).isEqualTo(UPDATED_BUTTON_URL);
        assertThat(testMessage.getCreatedByUserId()).isEqualTo(UPDATED_CREATED_BY_USER_ID);
        assertThat(testMessage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Create the Message

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageMockMvc.perform(put("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(message)))
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        int databaseSizeBeforeDelete = messageRepository.findAll().size();

        // Delete the message
        restMessageMockMvc.perform(delete("/api/messages/{id}", message.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Message.class);
        Message message1 = new Message();
        message1.setId(1L);
        Message message2 = new Message();
        message2.setId(message1.getId());
        assertThat(message1).isEqualTo(message2);
        message2.setId(2L);
        assertThat(message1).isNotEqualTo(message2);
        message1.setId(null);
        assertThat(message1).isNotEqualTo(message2);
    }
}
