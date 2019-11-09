package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.WebApp;
import com.mycompany.myapp.domain.Delivery;
import com.mycompany.myapp.repository.DeliveryRepository;
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
 * Integration tests for the {@link DeliveryResource} REST controller.
 */
@SpringBootTest(classes = WebApp.class)
public class DeliveryResourceIT {

    private static final LocalDate DEFAULT_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_AMOUNT_DELIVERED_RICE = 1D;
    private static final Double UPDATED_AMOUNT_DELIVERED_RICE = 2D;

    private static final Double DEFAULT_AMOUNT_DELIVERED_FLOUR = 1D;
    private static final Double UPDATED_AMOUNT_DELIVERED_FLOUR = 2D;

    private static final byte[] DEFAULT_DELIVERY_NOTE_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DELIVERY_NOTE_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DELIVERY_NOTE_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DELIVERY_NOTE_IMAGE_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_CREATED_BY_USER_ID = 1L;
    private static final Long UPDATED_CREATED_BY_USER_ID = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DeliveryRepository deliveryRepository;

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

    private MockMvc restDeliveryMockMvc;

    private Delivery delivery;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeliveryResource deliveryResource = new DeliveryResource(deliveryRepository);
        this.restDeliveryMockMvc = MockMvcBuilders.standaloneSetup(deliveryResource)
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
    public static Delivery createEntity(EntityManager em) {
        Delivery delivery = new Delivery()
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .amountDeliveredRice(DEFAULT_AMOUNT_DELIVERED_RICE)
            .amountDeliveredFlour(DEFAULT_AMOUNT_DELIVERED_FLOUR)
            .deliveryNoteImage(DEFAULT_DELIVERY_NOTE_IMAGE)
            .deliveryNoteImageContentType(DEFAULT_DELIVERY_NOTE_IMAGE_CONTENT_TYPE)
            .createdByUserId(DEFAULT_CREATED_BY_USER_ID)
            .createdDate(DEFAULT_CREATED_DATE);
        return delivery;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Delivery createUpdatedEntity(EntityManager em) {
        Delivery delivery = new Delivery()
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .amountDeliveredRice(UPDATED_AMOUNT_DELIVERED_RICE)
            .amountDeliveredFlour(UPDATED_AMOUNT_DELIVERED_FLOUR)
            .deliveryNoteImage(UPDATED_DELIVERY_NOTE_IMAGE)
            .deliveryNoteImageContentType(UPDATED_DELIVERY_NOTE_IMAGE_CONTENT_TYPE)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);
        return delivery;
    }

    @BeforeEach
    public void initTest() {
        delivery = createEntity(em);
    }

    @Test
    @Transactional
    public void createDelivery() throws Exception {
        int databaseSizeBeforeCreate = deliveryRepository.findAll().size();

        // Create the Delivery
        restDeliveryMockMvc.perform(post("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delivery)))
            .andExpect(status().isCreated());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeCreate + 1);
        Delivery testDelivery = deliveryList.get(deliveryList.size() - 1);
        assertThat(testDelivery.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testDelivery.getAmountDeliveredRice()).isEqualTo(DEFAULT_AMOUNT_DELIVERED_RICE);
        assertThat(testDelivery.getAmountDeliveredFlour()).isEqualTo(DEFAULT_AMOUNT_DELIVERED_FLOUR);
        assertThat(testDelivery.getDeliveryNoteImage()).isEqualTo(DEFAULT_DELIVERY_NOTE_IMAGE);
        assertThat(testDelivery.getDeliveryNoteImageContentType()).isEqualTo(DEFAULT_DELIVERY_NOTE_IMAGE_CONTENT_TYPE);
        assertThat(testDelivery.getCreatedByUserId()).isEqualTo(DEFAULT_CREATED_BY_USER_ID);
        assertThat(testDelivery.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createDeliveryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryRepository.findAll().size();

        // Create the Delivery with an existing ID
        delivery.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryMockMvc.perform(post("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delivery)))
            .andExpect(status().isBadRequest());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDeliveries() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get all the deliveryList
        restDeliveryMockMvc.perform(get("/api/deliveries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(delivery.getId().intValue())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].amountDeliveredRice").value(hasItem(DEFAULT_AMOUNT_DELIVERED_RICE.doubleValue())))
            .andExpect(jsonPath("$.[*].amountDeliveredFlour").value(hasItem(DEFAULT_AMOUNT_DELIVERED_FLOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].deliveryNoteImageContentType").value(hasItem(DEFAULT_DELIVERY_NOTE_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].deliveryNoteImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_DELIVERY_NOTE_IMAGE))))
            .andExpect(jsonPath("$.[*].createdByUserId").value(hasItem(DEFAULT_CREATED_BY_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDelivery() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        // Get the delivery
        restDeliveryMockMvc.perform(get("/api/deliveries/{id}", delivery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(delivery.getId().intValue()))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.amountDeliveredRice").value(DEFAULT_AMOUNT_DELIVERED_RICE.doubleValue()))
            .andExpect(jsonPath("$.amountDeliveredFlour").value(DEFAULT_AMOUNT_DELIVERED_FLOUR.doubleValue()))
            .andExpect(jsonPath("$.deliveryNoteImageContentType").value(DEFAULT_DELIVERY_NOTE_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.deliveryNoteImage").value(Base64Utils.encodeToString(DEFAULT_DELIVERY_NOTE_IMAGE)))
            .andExpect(jsonPath("$.createdByUserId").value(DEFAULT_CREATED_BY_USER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDelivery() throws Exception {
        // Get the delivery
        restDeliveryMockMvc.perform(get("/api/deliveries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDelivery() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();

        // Update the delivery
        Delivery updatedDelivery = deliveryRepository.findById(delivery.getId()).get();
        // Disconnect from session so that the updates on updatedDelivery are not directly saved in db
        em.detach(updatedDelivery);
        updatedDelivery
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .amountDeliveredRice(UPDATED_AMOUNT_DELIVERED_RICE)
            .amountDeliveredFlour(UPDATED_AMOUNT_DELIVERED_FLOUR)
            .deliveryNoteImage(UPDATED_DELIVERY_NOTE_IMAGE)
            .deliveryNoteImageContentType(UPDATED_DELIVERY_NOTE_IMAGE_CONTENT_TYPE)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);

        restDeliveryMockMvc.perform(put("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDelivery)))
            .andExpect(status().isOk());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
        Delivery testDelivery = deliveryList.get(deliveryList.size() - 1);
        assertThat(testDelivery.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testDelivery.getAmountDeliveredRice()).isEqualTo(UPDATED_AMOUNT_DELIVERED_RICE);
        assertThat(testDelivery.getAmountDeliveredFlour()).isEqualTo(UPDATED_AMOUNT_DELIVERED_FLOUR);
        assertThat(testDelivery.getDeliveryNoteImage()).isEqualTo(UPDATED_DELIVERY_NOTE_IMAGE);
        assertThat(testDelivery.getDeliveryNoteImageContentType()).isEqualTo(UPDATED_DELIVERY_NOTE_IMAGE_CONTENT_TYPE);
        assertThat(testDelivery.getCreatedByUserId()).isEqualTo(UPDATED_CREATED_BY_USER_ID);
        assertThat(testDelivery.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDelivery() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().size();

        // Create the Delivery

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryMockMvc.perform(put("/api/deliveries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(delivery)))
            .andExpect(status().isBadRequest());

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDelivery() throws Exception {
        // Initialize the database
        deliveryRepository.saveAndFlush(delivery);

        int databaseSizeBeforeDelete = deliveryRepository.findAll().size();

        // Delete the delivery
        restDeliveryMockMvc.perform(delete("/api/deliveries/{id}", delivery.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Delivery> deliveryList = deliveryRepository.findAll();
        assertThat(deliveryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Delivery.class);
        Delivery delivery1 = new Delivery();
        delivery1.setId(1L);
        Delivery delivery2 = new Delivery();
        delivery2.setId(delivery1.getId());
        assertThat(delivery1).isEqualTo(delivery2);
        delivery2.setId(2L);
        assertThat(delivery1).isNotEqualTo(delivery2);
        delivery1.setId(null);
        assertThat(delivery1).isNotEqualTo(delivery2);
    }
}
