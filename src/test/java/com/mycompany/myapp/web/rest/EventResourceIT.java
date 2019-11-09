package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.WebApp;
import com.mycompany.myapp.domain.Event;
import com.mycompany.myapp.repository.EventRepository;
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
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EventResource} REST controller.
 */
@SpringBootTest(classes = WebApp.class)
public class EventResourceIT {

    private static final String DEFAULT_SALESFORCE_UID = "AAAAAAAAAA";
    private static final String UPDATED_SALESFORCE_UID = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EVENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EVENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_EVENT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_EVENT_BANNER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EVENT_BANNER = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_EVENT_BANNER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EVENT_BANNER_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_EVENT_PICTURE_ONE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EVENT_PICTURE_ONE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_EVENT_PICTURE_ONE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EVENT_PICTURE_ONE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_EVENT_PRICTURE_TWO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EVENT_PRICTURE_TWO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_EVENT_PRICTURE_TWO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EVENT_PRICTURE_TWO_CONTENT_TYPE = "image/png";

    private static final Float DEFAULT_LOCATION_COORDINATE_X = 1F;
    private static final Float UPDATED_LOCATION_COORDINATE_X = 2F;

    private static final Float DEFAULT_LOCATION_COORDINATE_Y = 1F;
    private static final Float UPDATED_LOCATION_COORDINATE_Y = 2F;

    private static final Integer DEFAULT_ATTENDEES = 1;
    private static final Integer UPDATED_ATTENDEES = 2;

    private static final Integer DEFAULT_MAXIMUM_ATTENDEES = 1;
    private static final Integer UPDATED_MAXIMUM_ATTENDEES = 2;

    private static final Long DEFAULT_CREATED_BY_USER_ID = 1L;
    private static final Long UPDATED_CREATED_BY_USER_ID = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EventRepository eventRepository;

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

    private MockMvc restEventMockMvc;

    private Event event;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EventResource eventResource = new EventResource(eventRepository);
        this.restEventMockMvc = MockMvcBuilders.standaloneSetup(eventResource)
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
    public static Event createEntity(EntityManager em) {
        Event event = new Event()
            .salesforceUID(DEFAULT_SALESFORCE_UID)
            .eventName(DEFAULT_EVENT_NAME)
            .eventDate(DEFAULT_EVENT_DATE)
            .eventDescription(DEFAULT_EVENT_DESCRIPTION)
            .eventBanner(DEFAULT_EVENT_BANNER)
            .eventBannerContentType(DEFAULT_EVENT_BANNER_CONTENT_TYPE)
            .eventPictureOne(DEFAULT_EVENT_PICTURE_ONE)
            .eventPictureOneContentType(DEFAULT_EVENT_PICTURE_ONE_CONTENT_TYPE)
            .eventPrictureTwo(DEFAULT_EVENT_PRICTURE_TWO)
            .eventPrictureTwoContentType(DEFAULT_EVENT_PRICTURE_TWO_CONTENT_TYPE)
            .locationCoordinateX(DEFAULT_LOCATION_COORDINATE_X)
            .locationCoordinateY(DEFAULT_LOCATION_COORDINATE_Y)
            .attendees(DEFAULT_ATTENDEES)
            .maximumAttendees(DEFAULT_MAXIMUM_ATTENDEES)
            .createdByUserId(DEFAULT_CREATED_BY_USER_ID)
            .createdDate(DEFAULT_CREATED_DATE);
        return event;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Event createUpdatedEntity(EntityManager em) {
        Event event = new Event()
            .salesforceUID(UPDATED_SALESFORCE_UID)
            .eventName(UPDATED_EVENT_NAME)
            .eventDate(UPDATED_EVENT_DATE)
            .eventDescription(UPDATED_EVENT_DESCRIPTION)
            .eventBanner(UPDATED_EVENT_BANNER)
            .eventBannerContentType(UPDATED_EVENT_BANNER_CONTENT_TYPE)
            .eventPictureOne(UPDATED_EVENT_PICTURE_ONE)
            .eventPictureOneContentType(UPDATED_EVENT_PICTURE_ONE_CONTENT_TYPE)
            .eventPrictureTwo(UPDATED_EVENT_PRICTURE_TWO)
            .eventPrictureTwoContentType(UPDATED_EVENT_PRICTURE_TWO_CONTENT_TYPE)
            .locationCoordinateX(UPDATED_LOCATION_COORDINATE_X)
            .locationCoordinateY(UPDATED_LOCATION_COORDINATE_Y)
            .attendees(UPDATED_ATTENDEES)
            .maximumAttendees(UPDATED_MAXIMUM_ATTENDEES)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);
        return event;
    }

    @BeforeEach
    public void initTest() {
        event = createEntity(em);
    }

    @Test
    @Transactional
    public void createEvent() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event
        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isCreated());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate + 1);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getSalesforceUID()).isEqualTo(DEFAULT_SALESFORCE_UID);
        assertThat(testEvent.getEventName()).isEqualTo(DEFAULT_EVENT_NAME);
        assertThat(testEvent.getEventDate()).isEqualTo(DEFAULT_EVENT_DATE);
        assertThat(testEvent.getEventDescription()).isEqualTo(DEFAULT_EVENT_DESCRIPTION);
        assertThat(testEvent.getEventBanner()).isEqualTo(DEFAULT_EVENT_BANNER);
        assertThat(testEvent.getEventBannerContentType()).isEqualTo(DEFAULT_EVENT_BANNER_CONTENT_TYPE);
        assertThat(testEvent.getEventPictureOne()).isEqualTo(DEFAULT_EVENT_PICTURE_ONE);
        assertThat(testEvent.getEventPictureOneContentType()).isEqualTo(DEFAULT_EVENT_PICTURE_ONE_CONTENT_TYPE);
        assertThat(testEvent.getEventPrictureTwo()).isEqualTo(DEFAULT_EVENT_PRICTURE_TWO);
        assertThat(testEvent.getEventPrictureTwoContentType()).isEqualTo(DEFAULT_EVENT_PRICTURE_TWO_CONTENT_TYPE);
        assertThat(testEvent.getLocationCoordinateX()).isEqualTo(DEFAULT_LOCATION_COORDINATE_X);
        assertThat(testEvent.getLocationCoordinateY()).isEqualTo(DEFAULT_LOCATION_COORDINATE_Y);
        assertThat(testEvent.getAttendees()).isEqualTo(DEFAULT_ATTENDEES);
        assertThat(testEvent.getMaximumAttendees()).isEqualTo(DEFAULT_MAXIMUM_ATTENDEES);
        assertThat(testEvent.getCreatedByUserId()).isEqualTo(DEFAULT_CREATED_BY_USER_ID);
        assertThat(testEvent.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventRepository.findAll().size();

        // Create the Event with an existing ID
        event.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLocationCoordinateXIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setLocationCoordinateX(null);

        // Create the Event, which fails.

        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isBadRequest());

        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationCoordinateYIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventRepository.findAll().size();
        // set the field null
        event.setLocationCoordinateY(null);

        // Create the Event, which fails.

        restEventMockMvc.perform(post("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isBadRequest());

        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEvents() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get all the eventList
        restEventMockMvc.perform(get("/api/events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(event.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesforceUID").value(hasItem(DEFAULT_SALESFORCE_UID)))
            .andExpect(jsonPath("$.[*].eventName").value(hasItem(DEFAULT_EVENT_NAME)))
            .andExpect(jsonPath("$.[*].eventDate").value(hasItem(sameInstant(DEFAULT_EVENT_DATE))))
            .andExpect(jsonPath("$.[*].eventDescription").value(hasItem(DEFAULT_EVENT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].eventBannerContentType").value(hasItem(DEFAULT_EVENT_BANNER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].eventBanner").value(hasItem(Base64Utils.encodeToString(DEFAULT_EVENT_BANNER))))
            .andExpect(jsonPath("$.[*].eventPictureOneContentType").value(hasItem(DEFAULT_EVENT_PICTURE_ONE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].eventPictureOne").value(hasItem(Base64Utils.encodeToString(DEFAULT_EVENT_PICTURE_ONE))))
            .andExpect(jsonPath("$.[*].eventPrictureTwoContentType").value(hasItem(DEFAULT_EVENT_PRICTURE_TWO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].eventPrictureTwo").value(hasItem(Base64Utils.encodeToString(DEFAULT_EVENT_PRICTURE_TWO))))
            .andExpect(jsonPath("$.[*].locationCoordinateX").value(hasItem(DEFAULT_LOCATION_COORDINATE_X.doubleValue())))
            .andExpect(jsonPath("$.[*].locationCoordinateY").value(hasItem(DEFAULT_LOCATION_COORDINATE_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].attendees").value(hasItem(DEFAULT_ATTENDEES)))
            .andExpect(jsonPath("$.[*].maximumAttendees").value(hasItem(DEFAULT_MAXIMUM_ATTENDEES)))
            .andExpect(jsonPath("$.[*].createdByUserId").value(hasItem(DEFAULT_CREATED_BY_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", event.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(event.getId().intValue()))
            .andExpect(jsonPath("$.salesforceUID").value(DEFAULT_SALESFORCE_UID))
            .andExpect(jsonPath("$.eventName").value(DEFAULT_EVENT_NAME))
            .andExpect(jsonPath("$.eventDate").value(sameInstant(DEFAULT_EVENT_DATE)))
            .andExpect(jsonPath("$.eventDescription").value(DEFAULT_EVENT_DESCRIPTION))
            .andExpect(jsonPath("$.eventBannerContentType").value(DEFAULT_EVENT_BANNER_CONTENT_TYPE))
            .andExpect(jsonPath("$.eventBanner").value(Base64Utils.encodeToString(DEFAULT_EVENT_BANNER)))
            .andExpect(jsonPath("$.eventPictureOneContentType").value(DEFAULT_EVENT_PICTURE_ONE_CONTENT_TYPE))
            .andExpect(jsonPath("$.eventPictureOne").value(Base64Utils.encodeToString(DEFAULT_EVENT_PICTURE_ONE)))
            .andExpect(jsonPath("$.eventPrictureTwoContentType").value(DEFAULT_EVENT_PRICTURE_TWO_CONTENT_TYPE))
            .andExpect(jsonPath("$.eventPrictureTwo").value(Base64Utils.encodeToString(DEFAULT_EVENT_PRICTURE_TWO)))
            .andExpect(jsonPath("$.locationCoordinateX").value(DEFAULT_LOCATION_COORDINATE_X.doubleValue()))
            .andExpect(jsonPath("$.locationCoordinateY").value(DEFAULT_LOCATION_COORDINATE_Y.doubleValue()))
            .andExpect(jsonPath("$.attendees").value(DEFAULT_ATTENDEES))
            .andExpect(jsonPath("$.maximumAttendees").value(DEFAULT_MAXIMUM_ATTENDEES))
            .andExpect(jsonPath("$.createdByUserId").value(DEFAULT_CREATED_BY_USER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEvent() throws Exception {
        // Get the event
        restEventMockMvc.perform(get("/api/events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Update the event
        Event updatedEvent = eventRepository.findById(event.getId()).get();
        // Disconnect from session so that the updates on updatedEvent are not directly saved in db
        em.detach(updatedEvent);
        updatedEvent
            .salesforceUID(UPDATED_SALESFORCE_UID)
            .eventName(UPDATED_EVENT_NAME)
            .eventDate(UPDATED_EVENT_DATE)
            .eventDescription(UPDATED_EVENT_DESCRIPTION)
            .eventBanner(UPDATED_EVENT_BANNER)
            .eventBannerContentType(UPDATED_EVENT_BANNER_CONTENT_TYPE)
            .eventPictureOne(UPDATED_EVENT_PICTURE_ONE)
            .eventPictureOneContentType(UPDATED_EVENT_PICTURE_ONE_CONTENT_TYPE)
            .eventPrictureTwo(UPDATED_EVENT_PRICTURE_TWO)
            .eventPrictureTwoContentType(UPDATED_EVENT_PRICTURE_TWO_CONTENT_TYPE)
            .locationCoordinateX(UPDATED_LOCATION_COORDINATE_X)
            .locationCoordinateY(UPDATED_LOCATION_COORDINATE_Y)
            .attendees(UPDATED_ATTENDEES)
            .maximumAttendees(UPDATED_MAXIMUM_ATTENDEES)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);

        restEventMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEvent)))
            .andExpect(status().isOk());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
        Event testEvent = eventList.get(eventList.size() - 1);
        assertThat(testEvent.getSalesforceUID()).isEqualTo(UPDATED_SALESFORCE_UID);
        assertThat(testEvent.getEventName()).isEqualTo(UPDATED_EVENT_NAME);
        assertThat(testEvent.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
        assertThat(testEvent.getEventDescription()).isEqualTo(UPDATED_EVENT_DESCRIPTION);
        assertThat(testEvent.getEventBanner()).isEqualTo(UPDATED_EVENT_BANNER);
        assertThat(testEvent.getEventBannerContentType()).isEqualTo(UPDATED_EVENT_BANNER_CONTENT_TYPE);
        assertThat(testEvent.getEventPictureOne()).isEqualTo(UPDATED_EVENT_PICTURE_ONE);
        assertThat(testEvent.getEventPictureOneContentType()).isEqualTo(UPDATED_EVENT_PICTURE_ONE_CONTENT_TYPE);
        assertThat(testEvent.getEventPrictureTwo()).isEqualTo(UPDATED_EVENT_PRICTURE_TWO);
        assertThat(testEvent.getEventPrictureTwoContentType()).isEqualTo(UPDATED_EVENT_PRICTURE_TWO_CONTENT_TYPE);
        assertThat(testEvent.getLocationCoordinateX()).isEqualTo(UPDATED_LOCATION_COORDINATE_X);
        assertThat(testEvent.getLocationCoordinateY()).isEqualTo(UPDATED_LOCATION_COORDINATE_Y);
        assertThat(testEvent.getAttendees()).isEqualTo(UPDATED_ATTENDEES);
        assertThat(testEvent.getMaximumAttendees()).isEqualTo(UPDATED_MAXIMUM_ATTENDEES);
        assertThat(testEvent.getCreatedByUserId()).isEqualTo(UPDATED_CREATED_BY_USER_ID);
        assertThat(testEvent.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEvent() throws Exception {
        int databaseSizeBeforeUpdate = eventRepository.findAll().size();

        // Create the Event

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventMockMvc.perform(put("/api/events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(event)))
            .andExpect(status().isBadRequest());

        // Validate the Event in the database
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEvent() throws Exception {
        // Initialize the database
        eventRepository.saveAndFlush(event);

        int databaseSizeBeforeDelete = eventRepository.findAll().size();

        // Delete the event
        restEventMockMvc.perform(delete("/api/events/{id}", event.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Event> eventList = eventRepository.findAll();
        assertThat(eventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Event.class);
        Event event1 = new Event();
        event1.setId(1L);
        Event event2 = new Event();
        event2.setId(event1.getId());
        assertThat(event1).isEqualTo(event2);
        event2.setId(2L);
        assertThat(event1).isNotEqualTo(event2);
        event1.setId(null);
        assertThat(event1).isNotEqualTo(event2);
    }
}
