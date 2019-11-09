package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.WebApp;
import com.mycompany.myapp.domain.Donation;
import com.mycompany.myapp.repository.DonationRepository;
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
import com.mycompany.myapp.domain.enumeration.PaymentMethod;
import com.mycompany.myapp.domain.enumeration.Frequency;
import com.mycompany.myapp.domain.enumeration.CollectionDate;
import com.mycompany.myapp.domain.enumeration.CardType;
/**
 * Integration tests for the {@link DonationResource} REST controller.
 */
@SpringBootTest(classes = WebApp.class)
public class DonationResourceIT {

    private static final String DEFAULT_SALESFORCE_UID = "AAAAAAAAAA";
    private static final String UPDATED_SALESFORCE_UID = "BBBBBBBBBB";

    private static final Currency DEFAULT_CURRENCY = Currency.GBP;
    private static final Currency UPDATED_CURRENCY = Currency.USD;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final PaymentMethod DEFAULT_PAYMENT_METHOD = PaymentMethod.DIRECTDEBIT;
    private static final PaymentMethod UPDATED_PAYMENT_METHOD = PaymentMethod.CARDPAYMENT;

    private static final Frequency DEFAULT_FREQUENCY = Frequency.SINGLE;
    private static final Frequency UPDATED_FREQUENCY = Frequency.MONTHLY;

    private static final Boolean DEFAULT_AGE_CATEGORY = false;
    private static final Boolean UPDATED_AGE_CATEGORY = true;

    private static final Boolean DEFAULT_GIFT_AIDABLE = false;
    private static final Boolean UPDATED_GIFT_AIDABLE = true;

    private static final String DEFAULT_GIFT_AID_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_GIFT_AID_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_HOLDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_HOLDER_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACCOUNT_NUMBER = 1;
    private static final Integer UPDATED_ACCOUNT_NUMBER = 2;

    private static final Integer DEFAULT_SORTCODE = 1;
    private static final Integer UPDATED_SORTCODE = 2;

    private static final CollectionDate DEFAULT_COLLECTION_DATE = CollectionDate.FIRST;
    private static final CollectionDate UPDATED_COLLECTION_DATE = CollectionDate.ELEVENTH;

    private static final Boolean DEFAULT_IS_ACCOUNT_HOLDER = false;
    private static final Boolean UPDATED_IS_ACCOUNT_HOLDER = true;

    private static final CardType DEFAULT_CARD_TYPE = CardType.VISA;
    private static final CardType UPDATED_CARD_TYPE = CardType.MASTERCARD;

    private static final Long DEFAULT_CARD_NUMBER = 1L;
    private static final Long UPDATED_CARD_NUMBER = 2L;

    private static final Integer DEFAULT_EXPIRY_MONTH = 1;
    private static final Integer UPDATED_EXPIRY_MONTH = 2;

    private static final Integer DEFAULT_EXPIRY_YEAR = 1;
    private static final Integer UPDATED_EXPIRY_YEAR = 2;

    private static final Integer DEFAULT_CARD_SECURITY_CODE = 1;
    private static final Integer UPDATED_CARD_SECURITY_CODE = 2;

    private static final Long DEFAULT_CREATED_BY_USER_ID = 1L;
    private static final Long UPDATED_CREATED_BY_USER_ID = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DonationRepository donationRepository;

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

    private MockMvc restDonationMockMvc;

    private Donation donation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DonationResource donationResource = new DonationResource(donationRepository);
        this.restDonationMockMvc = MockMvcBuilders.standaloneSetup(donationResource)
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
    public static Donation createEntity(EntityManager em) {
        Donation donation = new Donation()
            .salesforceUID(DEFAULT_SALESFORCE_UID)
            .currency(DEFAULT_CURRENCY)
            .amount(DEFAULT_AMOUNT)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .frequency(DEFAULT_FREQUENCY)
            .ageCategory(DEFAULT_AGE_CATEGORY)
            .giftAidable(DEFAULT_GIFT_AIDABLE)
            .giftAidMessage(DEFAULT_GIFT_AID_MESSAGE)
            .accountHolderName(DEFAULT_ACCOUNT_HOLDER_NAME)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .sortcode(DEFAULT_SORTCODE)
            .collectionDate(DEFAULT_COLLECTION_DATE)
            .isAccountHolder(DEFAULT_IS_ACCOUNT_HOLDER)
            .cardType(DEFAULT_CARD_TYPE)
            .cardNumber(DEFAULT_CARD_NUMBER)
            .expiryMonth(DEFAULT_EXPIRY_MONTH)
            .expiryYear(DEFAULT_EXPIRY_YEAR)
            .cardSecurityCode(DEFAULT_CARD_SECURITY_CODE)
            .createdByUserId(DEFAULT_CREATED_BY_USER_ID)
            .createdDate(DEFAULT_CREATED_DATE);
        return donation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Donation createUpdatedEntity(EntityManager em) {
        Donation donation = new Donation()
            .salesforceUID(UPDATED_SALESFORCE_UID)
            .currency(UPDATED_CURRENCY)
            .amount(UPDATED_AMOUNT)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .frequency(UPDATED_FREQUENCY)
            .ageCategory(UPDATED_AGE_CATEGORY)
            .giftAidable(UPDATED_GIFT_AIDABLE)
            .giftAidMessage(UPDATED_GIFT_AID_MESSAGE)
            .accountHolderName(UPDATED_ACCOUNT_HOLDER_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .sortcode(UPDATED_SORTCODE)
            .collectionDate(UPDATED_COLLECTION_DATE)
            .isAccountHolder(UPDATED_IS_ACCOUNT_HOLDER)
            .cardType(UPDATED_CARD_TYPE)
            .cardNumber(UPDATED_CARD_NUMBER)
            .expiryMonth(UPDATED_EXPIRY_MONTH)
            .expiryYear(UPDATED_EXPIRY_YEAR)
            .cardSecurityCode(UPDATED_CARD_SECURITY_CODE)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);
        return donation;
    }

    @BeforeEach
    public void initTest() {
        donation = createEntity(em);
    }

    @Test
    @Transactional
    public void createDonation() throws Exception {
        int databaseSizeBeforeCreate = donationRepository.findAll().size();

        // Create the Donation
        restDonationMockMvc.perform(post("/api/donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donation)))
            .andExpect(status().isCreated());

        // Validate the Donation in the database
        List<Donation> donationList = donationRepository.findAll();
        assertThat(donationList).hasSize(databaseSizeBeforeCreate + 1);
        Donation testDonation = donationList.get(donationList.size() - 1);
        assertThat(testDonation.getSalesforceUID()).isEqualTo(DEFAULT_SALESFORCE_UID);
        assertThat(testDonation.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testDonation.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testDonation.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testDonation.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
        assertThat(testDonation.isAgeCategory()).isEqualTo(DEFAULT_AGE_CATEGORY);
        assertThat(testDonation.isGiftAidable()).isEqualTo(DEFAULT_GIFT_AIDABLE);
        assertThat(testDonation.getGiftAidMessage()).isEqualTo(DEFAULT_GIFT_AID_MESSAGE);
        assertThat(testDonation.getAccountHolderName()).isEqualTo(DEFAULT_ACCOUNT_HOLDER_NAME);
        assertThat(testDonation.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testDonation.getSortcode()).isEqualTo(DEFAULT_SORTCODE);
        assertThat(testDonation.getCollectionDate()).isEqualTo(DEFAULT_COLLECTION_DATE);
        assertThat(testDonation.isIsAccountHolder()).isEqualTo(DEFAULT_IS_ACCOUNT_HOLDER);
        assertThat(testDonation.getCardType()).isEqualTo(DEFAULT_CARD_TYPE);
        assertThat(testDonation.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testDonation.getExpiryMonth()).isEqualTo(DEFAULT_EXPIRY_MONTH);
        assertThat(testDonation.getExpiryYear()).isEqualTo(DEFAULT_EXPIRY_YEAR);
        assertThat(testDonation.getCardSecurityCode()).isEqualTo(DEFAULT_CARD_SECURITY_CODE);
        assertThat(testDonation.getCreatedByUserId()).isEqualTo(DEFAULT_CREATED_BY_USER_ID);
        assertThat(testDonation.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createDonationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = donationRepository.findAll().size();

        // Create the Donation with an existing ID
        donation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonationMockMvc.perform(post("/api/donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donation)))
            .andExpect(status().isBadRequest());

        // Validate the Donation in the database
        List<Donation> donationList = donationRepository.findAll();
        assertThat(donationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationRepository.findAll().size();
        // set the field null
        donation.setAmount(null);

        // Create the Donation, which fails.

        restDonationMockMvc.perform(post("/api/donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donation)))
            .andExpect(status().isBadRequest());

        List<Donation> donationList = donationRepository.findAll();
        assertThat(donationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationRepository.findAll().size();
        // set the field null
        donation.setPaymentMethod(null);

        // Create the Donation, which fails.

        restDonationMockMvc.perform(post("/api/donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donation)))
            .andExpect(status().isBadRequest());

        List<Donation> donationList = donationRepository.findAll();
        assertThat(donationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFrequencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationRepository.findAll().size();
        // set the field null
        donation.setFrequency(null);

        // Create the Donation, which fails.

        restDonationMockMvc.perform(post("/api/donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donation)))
            .andExpect(status().isBadRequest());

        List<Donation> donationList = donationRepository.findAll();
        assertThat(donationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationRepository.findAll().size();
        // set the field null
        donation.setAgeCategory(null);

        // Create the Donation, which fails.

        restDonationMockMvc.perform(post("/api/donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donation)))
            .andExpect(status().isBadRequest());

        List<Donation> donationList = donationRepository.findAll();
        assertThat(donationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGiftAidableIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationRepository.findAll().size();
        // set the field null
        donation.setGiftAidable(null);

        // Create the Donation, which fails.

        restDonationMockMvc.perform(post("/api/donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donation)))
            .andExpect(status().isBadRequest());

        List<Donation> donationList = donationRepository.findAll();
        assertThat(donationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGiftAidMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationRepository.findAll().size();
        // set the field null
        donation.setGiftAidMessage(null);

        // Create the Donation, which fails.

        restDonationMockMvc.perform(post("/api/donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donation)))
            .andExpect(status().isBadRequest());

        List<Donation> donationList = donationRepository.findAll();
        assertThat(donationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountHolderNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationRepository.findAll().size();
        // set the field null
        donation.setAccountHolderName(null);

        // Create the Donation, which fails.

        restDonationMockMvc.perform(post("/api/donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donation)))
            .andExpect(status().isBadRequest());

        List<Donation> donationList = donationRepository.findAll();
        assertThat(donationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCollectionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationRepository.findAll().size();
        // set the field null
        donation.setCollectionDate(null);

        // Create the Donation, which fails.

        restDonationMockMvc.perform(post("/api/donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donation)))
            .andExpect(status().isBadRequest());

        List<Donation> donationList = donationRepository.findAll();
        assertThat(donationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsAccountHolderIsRequired() throws Exception {
        int databaseSizeBeforeTest = donationRepository.findAll().size();
        // set the field null
        donation.setIsAccountHolder(null);

        // Create the Donation, which fails.

        restDonationMockMvc.perform(post("/api/donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donation)))
            .andExpect(status().isBadRequest());

        List<Donation> donationList = donationRepository.findAll();
        assertThat(donationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDonations() throws Exception {
        // Initialize the database
        donationRepository.saveAndFlush(donation);

        // Get all the donationList
        restDonationMockMvc.perform(get("/api/donations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donation.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesforceUID").value(hasItem(DEFAULT_SALESFORCE_UID)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].ageCategory").value(hasItem(DEFAULT_AGE_CATEGORY.booleanValue())))
            .andExpect(jsonPath("$.[*].giftAidable").value(hasItem(DEFAULT_GIFT_AIDABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].giftAidMessage").value(hasItem(DEFAULT_GIFT_AID_MESSAGE)))
            .andExpect(jsonPath("$.[*].accountHolderName").value(hasItem(DEFAULT_ACCOUNT_HOLDER_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].sortcode").value(hasItem(DEFAULT_SORTCODE)))
            .andExpect(jsonPath("$.[*].collectionDate").value(hasItem(DEFAULT_COLLECTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].isAccountHolder").value(hasItem(DEFAULT_IS_ACCOUNT_HOLDER.booleanValue())))
            .andExpect(jsonPath("$.[*].cardType").value(hasItem(DEFAULT_CARD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].expiryMonth").value(hasItem(DEFAULT_EXPIRY_MONTH)))
            .andExpect(jsonPath("$.[*].expiryYear").value(hasItem(DEFAULT_EXPIRY_YEAR)))
            .andExpect(jsonPath("$.[*].cardSecurityCode").value(hasItem(DEFAULT_CARD_SECURITY_CODE)))
            .andExpect(jsonPath("$.[*].createdByUserId").value(hasItem(DEFAULT_CREATED_BY_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDonation() throws Exception {
        // Initialize the database
        donationRepository.saveAndFlush(donation);

        // Get the donation
        restDonationMockMvc.perform(get("/api/donations/{id}", donation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(donation.getId().intValue()))
            .andExpect(jsonPath("$.salesforceUID").value(DEFAULT_SALESFORCE_UID))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.toString()))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY.toString()))
            .andExpect(jsonPath("$.ageCategory").value(DEFAULT_AGE_CATEGORY.booleanValue()))
            .andExpect(jsonPath("$.giftAidable").value(DEFAULT_GIFT_AIDABLE.booleanValue()))
            .andExpect(jsonPath("$.giftAidMessage").value(DEFAULT_GIFT_AID_MESSAGE))
            .andExpect(jsonPath("$.accountHolderName").value(DEFAULT_ACCOUNT_HOLDER_NAME))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.sortcode").value(DEFAULT_SORTCODE))
            .andExpect(jsonPath("$.collectionDate").value(DEFAULT_COLLECTION_DATE.toString()))
            .andExpect(jsonPath("$.isAccountHolder").value(DEFAULT_IS_ACCOUNT_HOLDER.booleanValue()))
            .andExpect(jsonPath("$.cardType").value(DEFAULT_CARD_TYPE.toString()))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER.intValue()))
            .andExpect(jsonPath("$.expiryMonth").value(DEFAULT_EXPIRY_MONTH))
            .andExpect(jsonPath("$.expiryYear").value(DEFAULT_EXPIRY_YEAR))
            .andExpect(jsonPath("$.cardSecurityCode").value(DEFAULT_CARD_SECURITY_CODE))
            .andExpect(jsonPath("$.createdByUserId").value(DEFAULT_CREATED_BY_USER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDonation() throws Exception {
        // Get the donation
        restDonationMockMvc.perform(get("/api/donations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDonation() throws Exception {
        // Initialize the database
        donationRepository.saveAndFlush(donation);

        int databaseSizeBeforeUpdate = donationRepository.findAll().size();

        // Update the donation
        Donation updatedDonation = donationRepository.findById(donation.getId()).get();
        // Disconnect from session so that the updates on updatedDonation are not directly saved in db
        em.detach(updatedDonation);
        updatedDonation
            .salesforceUID(UPDATED_SALESFORCE_UID)
            .currency(UPDATED_CURRENCY)
            .amount(UPDATED_AMOUNT)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .frequency(UPDATED_FREQUENCY)
            .ageCategory(UPDATED_AGE_CATEGORY)
            .giftAidable(UPDATED_GIFT_AIDABLE)
            .giftAidMessage(UPDATED_GIFT_AID_MESSAGE)
            .accountHolderName(UPDATED_ACCOUNT_HOLDER_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .sortcode(UPDATED_SORTCODE)
            .collectionDate(UPDATED_COLLECTION_DATE)
            .isAccountHolder(UPDATED_IS_ACCOUNT_HOLDER)
            .cardType(UPDATED_CARD_TYPE)
            .cardNumber(UPDATED_CARD_NUMBER)
            .expiryMonth(UPDATED_EXPIRY_MONTH)
            .expiryYear(UPDATED_EXPIRY_YEAR)
            .cardSecurityCode(UPDATED_CARD_SECURITY_CODE)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);

        restDonationMockMvc.perform(put("/api/donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDonation)))
            .andExpect(status().isOk());

        // Validate the Donation in the database
        List<Donation> donationList = donationRepository.findAll();
        assertThat(donationList).hasSize(databaseSizeBeforeUpdate);
        Donation testDonation = donationList.get(donationList.size() - 1);
        assertThat(testDonation.getSalesforceUID()).isEqualTo(UPDATED_SALESFORCE_UID);
        assertThat(testDonation.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testDonation.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDonation.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testDonation.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testDonation.isAgeCategory()).isEqualTo(UPDATED_AGE_CATEGORY);
        assertThat(testDonation.isGiftAidable()).isEqualTo(UPDATED_GIFT_AIDABLE);
        assertThat(testDonation.getGiftAidMessage()).isEqualTo(UPDATED_GIFT_AID_MESSAGE);
        assertThat(testDonation.getAccountHolderName()).isEqualTo(UPDATED_ACCOUNT_HOLDER_NAME);
        assertThat(testDonation.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testDonation.getSortcode()).isEqualTo(UPDATED_SORTCODE);
        assertThat(testDonation.getCollectionDate()).isEqualTo(UPDATED_COLLECTION_DATE);
        assertThat(testDonation.isIsAccountHolder()).isEqualTo(UPDATED_IS_ACCOUNT_HOLDER);
        assertThat(testDonation.getCardType()).isEqualTo(UPDATED_CARD_TYPE);
        assertThat(testDonation.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testDonation.getExpiryMonth()).isEqualTo(UPDATED_EXPIRY_MONTH);
        assertThat(testDonation.getExpiryYear()).isEqualTo(UPDATED_EXPIRY_YEAR);
        assertThat(testDonation.getCardSecurityCode()).isEqualTo(UPDATED_CARD_SECURITY_CODE);
        assertThat(testDonation.getCreatedByUserId()).isEqualTo(UPDATED_CREATED_BY_USER_ID);
        assertThat(testDonation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDonation() throws Exception {
        int databaseSizeBeforeUpdate = donationRepository.findAll().size();

        // Create the Donation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonationMockMvc.perform(put("/api/donations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(donation)))
            .andExpect(status().isBadRequest());

        // Validate the Donation in the database
        List<Donation> donationList = donationRepository.findAll();
        assertThat(donationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDonation() throws Exception {
        // Initialize the database
        donationRepository.saveAndFlush(donation);

        int databaseSizeBeforeDelete = donationRepository.findAll().size();

        // Delete the donation
        restDonationMockMvc.perform(delete("/api/donations/{id}", donation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Donation> donationList = donationRepository.findAll();
        assertThat(donationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Donation.class);
        Donation donation1 = new Donation();
        donation1.setId(1L);
        Donation donation2 = new Donation();
        donation2.setId(donation1.getId());
        assertThat(donation1).isEqualTo(donation2);
        donation2.setId(2L);
        assertThat(donation1).isNotEqualTo(donation2);
        donation1.setId(null);
        assertThat(donation1).isNotEqualTo(donation2);
    }
}
