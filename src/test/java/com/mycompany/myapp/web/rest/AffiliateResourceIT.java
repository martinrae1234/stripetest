package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.WebApp;
import com.mycompany.myapp.domain.Affiliate;
import com.mycompany.myapp.repository.AffiliateRepository;
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

import com.mycompany.myapp.domain.enumeration.Currency;
/**
 * Integration tests for the {@link AffiliateResource} REST controller.
 */
@SpringBootTest(classes = WebApp.class)
public class AffiliateResourceIT {

    private static final String DEFAULT_SALESFORCE_UID = "AAAAAAAAAA";
    private static final String UPDATED_SALESFORCE_UID = "BBBBBBBBBB";

    private static final String DEFAULT_AFFILIATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AFFILIATE_NAME = "BBBBBBBBBB";

    private static final Currency DEFAULT_CURRENCY = Currency.GBP;
    private static final Currency UPDATED_CURRENCY = Currency.USD;

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

    private static final String DEFAULT_EMAIL = "G@H.n";
    private static final String UPDATED_EMAIL = "e@g6.$g";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Float DEFAULT_LOCATION_COORDINATE_X = 1F;
    private static final Float UPDATED_LOCATION_COORDINATE_X = 2F;

    private static final Float DEFAULT_LOCATION_COORDINATE_Y = 1F;
    private static final Float UPDATED_LOCATION_COORDINATE_Y = 2F;

    private static final String DEFAULT_DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_LANGUAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CARD_PAYMENT = false;
    private static final Boolean UPDATED_CARD_PAYMENT = true;

    private static final Boolean DEFAULT_SINGLE_CARD_PAYMENT = false;
    private static final Boolean UPDATED_SINGLE_CARD_PAYMENT = true;

    private static final Boolean DEFAULT_RECURRING_CARD_PAYMENT = false;
    private static final Boolean UPDATED_RECURRING_CARD_PAYMENT = true;

    private static final Boolean DEFAULT_DIRECT_DEBIT = false;
    private static final Boolean UPDATED_DIRECT_DEBIT = true;

    private static final Boolean DEFAULT_GIFT_AID = false;
    private static final Boolean UPDATED_GIFT_AID = true;

    private static final Boolean DEFAULT_GENERAL_FUNDRAISING = false;
    private static final Boolean UPDATED_GENERAL_FUNDRAISING = true;

    private static final Boolean DEFAULT_SCHOOL_FUNDRAISING = false;
    private static final Boolean UPDATED_SCHOOL_FUNDRAISING = true;

    private static final Long DEFAULT_CREATED_BY_USER_ID = 1L;
    private static final Long UPDATED_CREATED_BY_USER_ID = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AffiliateRepository affiliateRepository;

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

    private MockMvc restAffiliateMockMvc;

    private Affiliate affiliate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AffiliateResource affiliateResource = new AffiliateResource(affiliateRepository);
        this.restAffiliateMockMvc = MockMvcBuilders.standaloneSetup(affiliateResource)
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
    public static Affiliate createEntity(EntityManager em) {
        Affiliate affiliate = new Affiliate()
            .salesforceUID(DEFAULT_SALESFORCE_UID)
            .affiliateName(DEFAULT_AFFILIATE_NAME)
            .currency(DEFAULT_CURRENCY)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .city(DEFAULT_CITY)
            .county(DEFAULT_COUNTY)
            .postcode(DEFAULT_POSTCODE)
            .country(DEFAULT_COUNTRY)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .locationCoordinateX(DEFAULT_LOCATION_COORDINATE_X)
            .locationCoordinateY(DEFAULT_LOCATION_COORDINATE_Y)
            .defaultLanguage(DEFAULT_DEFAULT_LANGUAGE)
            .cardPayment(DEFAULT_CARD_PAYMENT)
            .singleCardPayment(DEFAULT_SINGLE_CARD_PAYMENT)
            .recurringCardPayment(DEFAULT_RECURRING_CARD_PAYMENT)
            .directDebit(DEFAULT_DIRECT_DEBIT)
            .giftAid(DEFAULT_GIFT_AID)
            .generalFundraising(DEFAULT_GENERAL_FUNDRAISING)
            .schoolFundraising(DEFAULT_SCHOOL_FUNDRAISING)
            .createdByUserId(DEFAULT_CREATED_BY_USER_ID)
            .createdDate(DEFAULT_CREATED_DATE);
        return affiliate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Affiliate createUpdatedEntity(EntityManager em) {
        Affiliate affiliate = new Affiliate()
            .salesforceUID(UPDATED_SALESFORCE_UID)
            .affiliateName(UPDATED_AFFILIATE_NAME)
            .currency(UPDATED_CURRENCY)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .city(UPDATED_CITY)
            .county(UPDATED_COUNTY)
            .postcode(UPDATED_POSTCODE)
            .country(UPDATED_COUNTRY)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .locationCoordinateX(UPDATED_LOCATION_COORDINATE_X)
            .locationCoordinateY(UPDATED_LOCATION_COORDINATE_Y)
            .defaultLanguage(UPDATED_DEFAULT_LANGUAGE)
            .cardPayment(UPDATED_CARD_PAYMENT)
            .singleCardPayment(UPDATED_SINGLE_CARD_PAYMENT)
            .recurringCardPayment(UPDATED_RECURRING_CARD_PAYMENT)
            .directDebit(UPDATED_DIRECT_DEBIT)
            .giftAid(UPDATED_GIFT_AID)
            .generalFundraising(UPDATED_GENERAL_FUNDRAISING)
            .schoolFundraising(UPDATED_SCHOOL_FUNDRAISING)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);
        return affiliate;
    }

    @BeforeEach
    public void initTest() {
        affiliate = createEntity(em);
    }

    @Test
    @Transactional
    public void createAffiliate() throws Exception {
        int databaseSizeBeforeCreate = affiliateRepository.findAll().size();

        // Create the Affiliate
        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isCreated());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeCreate + 1);
        Affiliate testAffiliate = affiliateList.get(affiliateList.size() - 1);
        assertThat(testAffiliate.getSalesforceUID()).isEqualTo(DEFAULT_SALESFORCE_UID);
        assertThat(testAffiliate.getAffiliateName()).isEqualTo(DEFAULT_AFFILIATE_NAME);
        assertThat(testAffiliate.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testAffiliate.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testAffiliate.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAffiliate.getCounty()).isEqualTo(DEFAULT_COUNTY);
        assertThat(testAffiliate.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testAffiliate.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testAffiliate.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAffiliate.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testAffiliate.getLocationCoordinateX()).isEqualTo(DEFAULT_LOCATION_COORDINATE_X);
        assertThat(testAffiliate.getLocationCoordinateY()).isEqualTo(DEFAULT_LOCATION_COORDINATE_Y);
        assertThat(testAffiliate.getDefaultLanguage()).isEqualTo(DEFAULT_DEFAULT_LANGUAGE);
        assertThat(testAffiliate.isCardPayment()).isEqualTo(DEFAULT_CARD_PAYMENT);
        assertThat(testAffiliate.isSingleCardPayment()).isEqualTo(DEFAULT_SINGLE_CARD_PAYMENT);
        assertThat(testAffiliate.isRecurringCardPayment()).isEqualTo(DEFAULT_RECURRING_CARD_PAYMENT);
        assertThat(testAffiliate.isDirectDebit()).isEqualTo(DEFAULT_DIRECT_DEBIT);
        assertThat(testAffiliate.isGiftAid()).isEqualTo(DEFAULT_GIFT_AID);
        assertThat(testAffiliate.isGeneralFundraising()).isEqualTo(DEFAULT_GENERAL_FUNDRAISING);
        assertThat(testAffiliate.isSchoolFundraising()).isEqualTo(DEFAULT_SCHOOL_FUNDRAISING);
        assertThat(testAffiliate.getCreatedByUserId()).isEqualTo(DEFAULT_CREATED_BY_USER_ID);
        assertThat(testAffiliate.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createAffiliateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = affiliateRepository.findAll().size();

        // Create the Affiliate with an existing ID
        affiliate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStreetAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setStreetAddress(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setCity(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountyIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setCounty(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setPostcode(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setCountry(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationCoordinateXIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setLocationCoordinateX(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationCoordinateYIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setLocationCoordinateY(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAffiliates() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        // Get all the affiliateList
        restAffiliateMockMvc.perform(get("/api/affiliates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affiliate.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesforceUID").value(hasItem(DEFAULT_SALESFORCE_UID)))
            .andExpect(jsonPath("$.[*].affiliateName").value(hasItem(DEFAULT_AFFILIATE_NAME)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].county").value(hasItem(DEFAULT_COUNTY)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].locationCoordinateX").value(hasItem(DEFAULT_LOCATION_COORDINATE_X.doubleValue())))
            .andExpect(jsonPath("$.[*].locationCoordinateY").value(hasItem(DEFAULT_LOCATION_COORDINATE_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].defaultLanguage").value(hasItem(DEFAULT_DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].cardPayment").value(hasItem(DEFAULT_CARD_PAYMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].singleCardPayment").value(hasItem(DEFAULT_SINGLE_CARD_PAYMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].recurringCardPayment").value(hasItem(DEFAULT_RECURRING_CARD_PAYMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].directDebit").value(hasItem(DEFAULT_DIRECT_DEBIT.booleanValue())))
            .andExpect(jsonPath("$.[*].giftAid").value(hasItem(DEFAULT_GIFT_AID.booleanValue())))
            .andExpect(jsonPath("$.[*].generalFundraising").value(hasItem(DEFAULT_GENERAL_FUNDRAISING.booleanValue())))
            .andExpect(jsonPath("$.[*].schoolFundraising").value(hasItem(DEFAULT_SCHOOL_FUNDRAISING.booleanValue())))
            .andExpect(jsonPath("$.[*].createdByUserId").value(hasItem(DEFAULT_CREATED_BY_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getAffiliate() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        // Get the affiliate
        restAffiliateMockMvc.perform(get("/api/affiliates/{id}", affiliate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(affiliate.getId().intValue()))
            .andExpect(jsonPath("$.salesforceUID").value(DEFAULT_SALESFORCE_UID))
            .andExpect(jsonPath("$.affiliateName").value(DEFAULT_AFFILIATE_NAME))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.county").value(DEFAULT_COUNTY))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.locationCoordinateX").value(DEFAULT_LOCATION_COORDINATE_X.doubleValue()))
            .andExpect(jsonPath("$.locationCoordinateY").value(DEFAULT_LOCATION_COORDINATE_Y.doubleValue()))
            .andExpect(jsonPath("$.defaultLanguage").value(DEFAULT_DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.cardPayment").value(DEFAULT_CARD_PAYMENT.booleanValue()))
            .andExpect(jsonPath("$.singleCardPayment").value(DEFAULT_SINGLE_CARD_PAYMENT.booleanValue()))
            .andExpect(jsonPath("$.recurringCardPayment").value(DEFAULT_RECURRING_CARD_PAYMENT.booleanValue()))
            .andExpect(jsonPath("$.directDebit").value(DEFAULT_DIRECT_DEBIT.booleanValue()))
            .andExpect(jsonPath("$.giftAid").value(DEFAULT_GIFT_AID.booleanValue()))
            .andExpect(jsonPath("$.generalFundraising").value(DEFAULT_GENERAL_FUNDRAISING.booleanValue()))
            .andExpect(jsonPath("$.schoolFundraising").value(DEFAULT_SCHOOL_FUNDRAISING.booleanValue()))
            .andExpect(jsonPath("$.createdByUserId").value(DEFAULT_CREATED_BY_USER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAffiliate() throws Exception {
        // Get the affiliate
        restAffiliateMockMvc.perform(get("/api/affiliates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAffiliate() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();

        // Update the affiliate
        Affiliate updatedAffiliate = affiliateRepository.findById(affiliate.getId()).get();
        // Disconnect from session so that the updates on updatedAffiliate are not directly saved in db
        em.detach(updatedAffiliate);
        updatedAffiliate
            .salesforceUID(UPDATED_SALESFORCE_UID)
            .affiliateName(UPDATED_AFFILIATE_NAME)
            .currency(UPDATED_CURRENCY)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .city(UPDATED_CITY)
            .county(UPDATED_COUNTY)
            .postcode(UPDATED_POSTCODE)
            .country(UPDATED_COUNTRY)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .locationCoordinateX(UPDATED_LOCATION_COORDINATE_X)
            .locationCoordinateY(UPDATED_LOCATION_COORDINATE_Y)
            .defaultLanguage(UPDATED_DEFAULT_LANGUAGE)
            .cardPayment(UPDATED_CARD_PAYMENT)
            .singleCardPayment(UPDATED_SINGLE_CARD_PAYMENT)
            .recurringCardPayment(UPDATED_RECURRING_CARD_PAYMENT)
            .directDebit(UPDATED_DIRECT_DEBIT)
            .giftAid(UPDATED_GIFT_AID)
            .generalFundraising(UPDATED_GENERAL_FUNDRAISING)
            .schoolFundraising(UPDATED_SCHOOL_FUNDRAISING)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);

        restAffiliateMockMvc.perform(put("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAffiliate)))
            .andExpect(status().isOk());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
        Affiliate testAffiliate = affiliateList.get(affiliateList.size() - 1);
        assertThat(testAffiliate.getSalesforceUID()).isEqualTo(UPDATED_SALESFORCE_UID);
        assertThat(testAffiliate.getAffiliateName()).isEqualTo(UPDATED_AFFILIATE_NAME);
        assertThat(testAffiliate.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testAffiliate.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testAffiliate.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAffiliate.getCounty()).isEqualTo(UPDATED_COUNTY);
        assertThat(testAffiliate.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testAffiliate.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAffiliate.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAffiliate.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testAffiliate.getLocationCoordinateX()).isEqualTo(UPDATED_LOCATION_COORDINATE_X);
        assertThat(testAffiliate.getLocationCoordinateY()).isEqualTo(UPDATED_LOCATION_COORDINATE_Y);
        assertThat(testAffiliate.getDefaultLanguage()).isEqualTo(UPDATED_DEFAULT_LANGUAGE);
        assertThat(testAffiliate.isCardPayment()).isEqualTo(UPDATED_CARD_PAYMENT);
        assertThat(testAffiliate.isSingleCardPayment()).isEqualTo(UPDATED_SINGLE_CARD_PAYMENT);
        assertThat(testAffiliate.isRecurringCardPayment()).isEqualTo(UPDATED_RECURRING_CARD_PAYMENT);
        assertThat(testAffiliate.isDirectDebit()).isEqualTo(UPDATED_DIRECT_DEBIT);
        assertThat(testAffiliate.isGiftAid()).isEqualTo(UPDATED_GIFT_AID);
        assertThat(testAffiliate.isGeneralFundraising()).isEqualTo(UPDATED_GENERAL_FUNDRAISING);
        assertThat(testAffiliate.isSchoolFundraising()).isEqualTo(UPDATED_SCHOOL_FUNDRAISING);
        assertThat(testAffiliate.getCreatedByUserId()).isEqualTo(UPDATED_CREATED_BY_USER_ID);
        assertThat(testAffiliate.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAffiliate() throws Exception {
        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();

        // Create the Affiliate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffiliateMockMvc.perform(put("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAffiliate() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        int databaseSizeBeforeDelete = affiliateRepository.findAll().size();

        // Delete the affiliate
        restAffiliateMockMvc.perform(delete("/api/affiliates/{id}", affiliate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Affiliate.class);
        Affiliate affiliate1 = new Affiliate();
        affiliate1.setId(1L);
        Affiliate affiliate2 = new Affiliate();
        affiliate2.setId(affiliate1.getId());
        assertThat(affiliate1).isEqualTo(affiliate2);
        affiliate2.setId(2L);
        assertThat(affiliate1).isNotEqualTo(affiliate2);
        affiliate1.setId(null);
        assertThat(affiliate1).isNotEqualTo(affiliate2);
    }
}
