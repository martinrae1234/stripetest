package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.WebApp;
import com.mycompany.myapp.domain.Commodity;
import com.mycompany.myapp.repository.CommodityRepository;
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
 * Integration tests for the {@link CommodityResource} REST controller.
 */
@SpringBootTest(classes = WebApp.class)
public class CommodityResourceIT {

    private static final LocalDate DEFAULT_DATE_OF_LAST_STOCK_CHECK = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_LAST_STOCK_CHECK = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Boolean DEFAULT_PERISHABLE = false;
    private static final Boolean UPDATED_PERISHABLE = true;

    private static final Double DEFAULT_AMOUNT_EXPIRABLE_IN_NEXT_3_MONTHS = 1D;
    private static final Double UPDATED_AMOUNT_EXPIRABLE_IN_NEXT_3_MONTHS = 2D;

    private static final Long DEFAULT_CREATED_BY_USER_ID = 1L;
    private static final Long UPDATED_CREATED_BY_USER_ID = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CommodityRepository commodityRepository;

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

    private MockMvc restCommodityMockMvc;

    private Commodity commodity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommodityResource commodityResource = new CommodityResource(commodityRepository);
        this.restCommodityMockMvc = MockMvcBuilders.standaloneSetup(commodityResource)
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
    public static Commodity createEntity(EntityManager em) {
        Commodity commodity = new Commodity()
            .dateOfLastStockCheck(DEFAULT_DATE_OF_LAST_STOCK_CHECK)
            .name(DEFAULT_NAME)
            .amount(DEFAULT_AMOUNT)
            .perishable(DEFAULT_PERISHABLE)
            .amountExpirableInNext3months(DEFAULT_AMOUNT_EXPIRABLE_IN_NEXT_3_MONTHS)
            .createdByUserId(DEFAULT_CREATED_BY_USER_ID)
            .createdDate(DEFAULT_CREATED_DATE);
        return commodity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commodity createUpdatedEntity(EntityManager em) {
        Commodity commodity = new Commodity()
            .dateOfLastStockCheck(UPDATED_DATE_OF_LAST_STOCK_CHECK)
            .name(UPDATED_NAME)
            .amount(UPDATED_AMOUNT)
            .perishable(UPDATED_PERISHABLE)
            .amountExpirableInNext3months(UPDATED_AMOUNT_EXPIRABLE_IN_NEXT_3_MONTHS)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);
        return commodity;
    }

    @BeforeEach
    public void initTest() {
        commodity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommodity() throws Exception {
        int databaseSizeBeforeCreate = commodityRepository.findAll().size();

        // Create the Commodity
        restCommodityMockMvc.perform(post("/api/commodities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commodity)))
            .andExpect(status().isCreated());

        // Validate the Commodity in the database
        List<Commodity> commodityList = commodityRepository.findAll();
        assertThat(commodityList).hasSize(databaseSizeBeforeCreate + 1);
        Commodity testCommodity = commodityList.get(commodityList.size() - 1);
        assertThat(testCommodity.getDateOfLastStockCheck()).isEqualTo(DEFAULT_DATE_OF_LAST_STOCK_CHECK);
        assertThat(testCommodity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCommodity.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCommodity.isPerishable()).isEqualTo(DEFAULT_PERISHABLE);
        assertThat(testCommodity.getAmountExpirableInNext3months()).isEqualTo(DEFAULT_AMOUNT_EXPIRABLE_IN_NEXT_3_MONTHS);
        assertThat(testCommodity.getCreatedByUserId()).isEqualTo(DEFAULT_CREATED_BY_USER_ID);
        assertThat(testCommodity.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createCommodityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commodityRepository.findAll().size();

        // Create the Commodity with an existing ID
        commodity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommodityMockMvc.perform(post("/api/commodities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commodity)))
            .andExpect(status().isBadRequest());

        // Validate the Commodity in the database
        List<Commodity> commodityList = commodityRepository.findAll();
        assertThat(commodityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCommodities() throws Exception {
        // Initialize the database
        commodityRepository.saveAndFlush(commodity);

        // Get all the commodityList
        restCommodityMockMvc.perform(get("/api/commodities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commodity.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateOfLastStockCheck").value(hasItem(DEFAULT_DATE_OF_LAST_STOCK_CHECK.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].perishable").value(hasItem(DEFAULT_PERISHABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].amountExpirableInNext3months").value(hasItem(DEFAULT_AMOUNT_EXPIRABLE_IN_NEXT_3_MONTHS.doubleValue())))
            .andExpect(jsonPath("$.[*].createdByUserId").value(hasItem(DEFAULT_CREATED_BY_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCommodity() throws Exception {
        // Initialize the database
        commodityRepository.saveAndFlush(commodity);

        // Get the commodity
        restCommodityMockMvc.perform(get("/api/commodities/{id}", commodity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commodity.getId().intValue()))
            .andExpect(jsonPath("$.dateOfLastStockCheck").value(DEFAULT_DATE_OF_LAST_STOCK_CHECK.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.perishable").value(DEFAULT_PERISHABLE.booleanValue()))
            .andExpect(jsonPath("$.amountExpirableInNext3months").value(DEFAULT_AMOUNT_EXPIRABLE_IN_NEXT_3_MONTHS.doubleValue()))
            .andExpect(jsonPath("$.createdByUserId").value(DEFAULT_CREATED_BY_USER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommodity() throws Exception {
        // Get the commodity
        restCommodityMockMvc.perform(get("/api/commodities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommodity() throws Exception {
        // Initialize the database
        commodityRepository.saveAndFlush(commodity);

        int databaseSizeBeforeUpdate = commodityRepository.findAll().size();

        // Update the commodity
        Commodity updatedCommodity = commodityRepository.findById(commodity.getId()).get();
        // Disconnect from session so that the updates on updatedCommodity are not directly saved in db
        em.detach(updatedCommodity);
        updatedCommodity
            .dateOfLastStockCheck(UPDATED_DATE_OF_LAST_STOCK_CHECK)
            .name(UPDATED_NAME)
            .amount(UPDATED_AMOUNT)
            .perishable(UPDATED_PERISHABLE)
            .amountExpirableInNext3months(UPDATED_AMOUNT_EXPIRABLE_IN_NEXT_3_MONTHS)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);

        restCommodityMockMvc.perform(put("/api/commodities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommodity)))
            .andExpect(status().isOk());

        // Validate the Commodity in the database
        List<Commodity> commodityList = commodityRepository.findAll();
        assertThat(commodityList).hasSize(databaseSizeBeforeUpdate);
        Commodity testCommodity = commodityList.get(commodityList.size() - 1);
        assertThat(testCommodity.getDateOfLastStockCheck()).isEqualTo(UPDATED_DATE_OF_LAST_STOCK_CHECK);
        assertThat(testCommodity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCommodity.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCommodity.isPerishable()).isEqualTo(UPDATED_PERISHABLE);
        assertThat(testCommodity.getAmountExpirableInNext3months()).isEqualTo(UPDATED_AMOUNT_EXPIRABLE_IN_NEXT_3_MONTHS);
        assertThat(testCommodity.getCreatedByUserId()).isEqualTo(UPDATED_CREATED_BY_USER_ID);
        assertThat(testCommodity.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommodity() throws Exception {
        int databaseSizeBeforeUpdate = commodityRepository.findAll().size();

        // Create the Commodity

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommodityMockMvc.perform(put("/api/commodities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commodity)))
            .andExpect(status().isBadRequest());

        // Validate the Commodity in the database
        List<Commodity> commodityList = commodityRepository.findAll();
        assertThat(commodityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommodity() throws Exception {
        // Initialize the database
        commodityRepository.saveAndFlush(commodity);

        int databaseSizeBeforeDelete = commodityRepository.findAll().size();

        // Delete the commodity
        restCommodityMockMvc.perform(delete("/api/commodities/{id}", commodity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commodity> commodityList = commodityRepository.findAll();
        assertThat(commodityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commodity.class);
        Commodity commodity1 = new Commodity();
        commodity1.setId(1L);
        Commodity commodity2 = new Commodity();
        commodity2.setId(commodity1.getId());
        assertThat(commodity1).isEqualTo(commodity2);
        commodity2.setId(2L);
        assertThat(commodity1).isNotEqualTo(commodity2);
        commodity1.setId(null);
        assertThat(commodity1).isNotEqualTo(commodity2);
    }
}
