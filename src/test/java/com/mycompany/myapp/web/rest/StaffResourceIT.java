package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.WebApp;
import com.mycompany.myapp.domain.Staff;
import com.mycompany.myapp.repository.StaffRepository;
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

import com.mycompany.myapp.domain.enumeration.TypeOfStaff;
/**
 * Integration tests for the {@link StaffResource} REST controller.
 */
@SpringBootTest(classes = WebApp.class)
public class StaffResourceIT {

    private static final String DEFAULT_SALESFORCE_UID = "AAAAAAAAAA";
    private static final String UPDATED_SALESFORCE_UID = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AFFILIATE = "AAAAAAAAAA";
    private static final String UPDATED_AFFILIATE = "BBBBBBBBBB";

    private static final TypeOfStaff DEFAULT_TYPE_OF_STAFF = TypeOfStaff.STAFF;
    private static final TypeOfStaff UPDATED_TYPE_OF_STAFF = TypeOfStaff.VOLUNTEER;

    private static final Float DEFAULT_LOCATION_COORDINATE_X = 1F;
    private static final Float UPDATED_LOCATION_COORDINATE_X = 2F;

    private static final Float DEFAULT_LOCATION_COORDINATE_Y = 1F;
    private static final Float UPDATED_LOCATION_COORDINATE_Y = 2F;

    private static final byte[] DEFAULT_STAFF_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_STAFF_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_STAFF_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_STAFF_PICTURE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_BY_USER_ID = 1L;
    private static final Long UPDATED_CREATED_BY_USER_ID = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private StaffRepository staffRepository;

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

    private MockMvc restStaffMockMvc;

    private Staff staff;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StaffResource staffResource = new StaffResource(staffRepository);
        this.restStaffMockMvc = MockMvcBuilders.standaloneSetup(staffResource)
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
    public static Staff createEntity(EntityManager em) {
        Staff staff = new Staff()
            .salesforceUID(DEFAULT_SALESFORCE_UID)
            .firstName(DEFAULT_FIRST_NAME)
            .secondName(DEFAULT_SECOND_NAME)
            .affiliate(DEFAULT_AFFILIATE)
            .typeOfStaff(DEFAULT_TYPE_OF_STAFF)
            .locationCoordinateX(DEFAULT_LOCATION_COORDINATE_X)
            .locationCoordinateY(DEFAULT_LOCATION_COORDINATE_Y)
            .staffPicture(DEFAULT_STAFF_PICTURE)
            .staffPictureContentType(DEFAULT_STAFF_PICTURE_CONTENT_TYPE)
            .position(DEFAULT_POSITION)
            .description(DEFAULT_DESCRIPTION)
            .createdByUserId(DEFAULT_CREATED_BY_USER_ID)
            .createdDate(DEFAULT_CREATED_DATE);
        return staff;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Staff createUpdatedEntity(EntityManager em) {
        Staff staff = new Staff()
            .salesforceUID(UPDATED_SALESFORCE_UID)
            .firstName(UPDATED_FIRST_NAME)
            .secondName(UPDATED_SECOND_NAME)
            .affiliate(UPDATED_AFFILIATE)
            .typeOfStaff(UPDATED_TYPE_OF_STAFF)
            .locationCoordinateX(UPDATED_LOCATION_COORDINATE_X)
            .locationCoordinateY(UPDATED_LOCATION_COORDINATE_Y)
            .staffPicture(UPDATED_STAFF_PICTURE)
            .staffPictureContentType(UPDATED_STAFF_PICTURE_CONTENT_TYPE)
            .position(UPDATED_POSITION)
            .description(UPDATED_DESCRIPTION)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);
        return staff;
    }

    @BeforeEach
    public void initTest() {
        staff = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaff() throws Exception {
        int databaseSizeBeforeCreate = staffRepository.findAll().size();

        // Create the Staff
        restStaffMockMvc.perform(post("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isCreated());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeCreate + 1);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getSalesforceUID()).isEqualTo(DEFAULT_SALESFORCE_UID);
        assertThat(testStaff.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testStaff.getSecondName()).isEqualTo(DEFAULT_SECOND_NAME);
        assertThat(testStaff.getAffiliate()).isEqualTo(DEFAULT_AFFILIATE);
        assertThat(testStaff.getTypeOfStaff()).isEqualTo(DEFAULT_TYPE_OF_STAFF);
        assertThat(testStaff.getLocationCoordinateX()).isEqualTo(DEFAULT_LOCATION_COORDINATE_X);
        assertThat(testStaff.getLocationCoordinateY()).isEqualTo(DEFAULT_LOCATION_COORDINATE_Y);
        assertThat(testStaff.getStaffPicture()).isEqualTo(DEFAULT_STAFF_PICTURE);
        assertThat(testStaff.getStaffPictureContentType()).isEqualTo(DEFAULT_STAFF_PICTURE_CONTENT_TYPE);
        assertThat(testStaff.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testStaff.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStaff.getCreatedByUserId()).isEqualTo(DEFAULT_CREATED_BY_USER_ID);
        assertThat(testStaff.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createStaffWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staffRepository.findAll().size();

        // Create the Staff with an existing ID
        staff.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaffMockMvc.perform(post("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = staffRepository.findAll().size();
        // set the field null
        staff.setFirstName(null);

        // Create the Staff, which fails.

        restStaffMockMvc.perform(post("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isBadRequest());

        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSecondNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = staffRepository.findAll().size();
        // set the field null
        staff.setSecondName(null);

        // Create the Staff, which fails.

        restStaffMockMvc.perform(post("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isBadRequest());

        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationCoordinateXIsRequired() throws Exception {
        int databaseSizeBeforeTest = staffRepository.findAll().size();
        // set the field null
        staff.setLocationCoordinateX(null);

        // Create the Staff, which fails.

        restStaffMockMvc.perform(post("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isBadRequest());

        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationCoordinateYIsRequired() throws Exception {
        int databaseSizeBeforeTest = staffRepository.findAll().size();
        // set the field null
        staff.setLocationCoordinateY(null);

        // Create the Staff, which fails.

        restStaffMockMvc.perform(post("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isBadRequest());

        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = staffRepository.findAll().size();
        // set the field null
        staff.setPosition(null);

        // Create the Staff, which fails.

        restStaffMockMvc.perform(post("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isBadRequest());

        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        // Get all the staffList
        restStaffMockMvc.perform(get("/api/staff?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staff.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesforceUID").value(hasItem(DEFAULT_SALESFORCE_UID)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].secondName").value(hasItem(DEFAULT_SECOND_NAME)))
            .andExpect(jsonPath("$.[*].affiliate").value(hasItem(DEFAULT_AFFILIATE)))
            .andExpect(jsonPath("$.[*].typeOfStaff").value(hasItem(DEFAULT_TYPE_OF_STAFF.toString())))
            .andExpect(jsonPath("$.[*].locationCoordinateX").value(hasItem(DEFAULT_LOCATION_COORDINATE_X.doubleValue())))
            .andExpect(jsonPath("$.[*].locationCoordinateY").value(hasItem(DEFAULT_LOCATION_COORDINATE_Y.doubleValue())))
            .andExpect(jsonPath("$.[*].staffPictureContentType").value(hasItem(DEFAULT_STAFF_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].staffPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_STAFF_PICTURE))))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createdByUserId").value(hasItem(DEFAULT_CREATED_BY_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        // Get the staff
        restStaffMockMvc.perform(get("/api/staff/{id}", staff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(staff.getId().intValue()))
            .andExpect(jsonPath("$.salesforceUID").value(DEFAULT_SALESFORCE_UID))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.secondName").value(DEFAULT_SECOND_NAME))
            .andExpect(jsonPath("$.affiliate").value(DEFAULT_AFFILIATE))
            .andExpect(jsonPath("$.typeOfStaff").value(DEFAULT_TYPE_OF_STAFF.toString()))
            .andExpect(jsonPath("$.locationCoordinateX").value(DEFAULT_LOCATION_COORDINATE_X.doubleValue()))
            .andExpect(jsonPath("$.locationCoordinateY").value(DEFAULT_LOCATION_COORDINATE_Y.doubleValue()))
            .andExpect(jsonPath("$.staffPictureContentType").value(DEFAULT_STAFF_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.staffPicture").value(Base64Utils.encodeToString(DEFAULT_STAFF_PICTURE)))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createdByUserId").value(DEFAULT_CREATED_BY_USER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStaff() throws Exception {
        // Get the staff
        restStaffMockMvc.perform(get("/api/staff/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        int databaseSizeBeforeUpdate = staffRepository.findAll().size();

        // Update the staff
        Staff updatedStaff = staffRepository.findById(staff.getId()).get();
        // Disconnect from session so that the updates on updatedStaff are not directly saved in db
        em.detach(updatedStaff);
        updatedStaff
            .salesforceUID(UPDATED_SALESFORCE_UID)
            .firstName(UPDATED_FIRST_NAME)
            .secondName(UPDATED_SECOND_NAME)
            .affiliate(UPDATED_AFFILIATE)
            .typeOfStaff(UPDATED_TYPE_OF_STAFF)
            .locationCoordinateX(UPDATED_LOCATION_COORDINATE_X)
            .locationCoordinateY(UPDATED_LOCATION_COORDINATE_Y)
            .staffPicture(UPDATED_STAFF_PICTURE)
            .staffPictureContentType(UPDATED_STAFF_PICTURE_CONTENT_TYPE)
            .position(UPDATED_POSITION)
            .description(UPDATED_DESCRIPTION)
            .createdByUserId(UPDATED_CREATED_BY_USER_ID)
            .createdDate(UPDATED_CREATED_DATE);

        restStaffMockMvc.perform(put("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStaff)))
            .andExpect(status().isOk());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
        Staff testStaff = staffList.get(staffList.size() - 1);
        assertThat(testStaff.getSalesforceUID()).isEqualTo(UPDATED_SALESFORCE_UID);
        assertThat(testStaff.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testStaff.getSecondName()).isEqualTo(UPDATED_SECOND_NAME);
        assertThat(testStaff.getAffiliate()).isEqualTo(UPDATED_AFFILIATE);
        assertThat(testStaff.getTypeOfStaff()).isEqualTo(UPDATED_TYPE_OF_STAFF);
        assertThat(testStaff.getLocationCoordinateX()).isEqualTo(UPDATED_LOCATION_COORDINATE_X);
        assertThat(testStaff.getLocationCoordinateY()).isEqualTo(UPDATED_LOCATION_COORDINATE_Y);
        assertThat(testStaff.getStaffPicture()).isEqualTo(UPDATED_STAFF_PICTURE);
        assertThat(testStaff.getStaffPictureContentType()).isEqualTo(UPDATED_STAFF_PICTURE_CONTENT_TYPE);
        assertThat(testStaff.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testStaff.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStaff.getCreatedByUserId()).isEqualTo(UPDATED_CREATED_BY_USER_ID);
        assertThat(testStaff.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingStaff() throws Exception {
        int databaseSizeBeforeUpdate = staffRepository.findAll().size();

        // Create the Staff

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaffMockMvc.perform(put("/api/staff")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(staff)))
            .andExpect(status().isBadRequest());

        // Validate the Staff in the database
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStaff() throws Exception {
        // Initialize the database
        staffRepository.saveAndFlush(staff);

        int databaseSizeBeforeDelete = staffRepository.findAll().size();

        // Delete the staff
        restStaffMockMvc.perform(delete("/api/staff/{id}", staff.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Staff> staffList = staffRepository.findAll();
        assertThat(staffList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Staff.class);
        Staff staff1 = new Staff();
        staff1.setId(1L);
        Staff staff2 = new Staff();
        staff2.setId(staff1.getId());
        assertThat(staff1).isEqualTo(staff2);
        staff2.setId(2L);
        assertThat(staff1).isNotEqualTo(staff2);
        staff1.setId(null);
        assertThat(staff1).isNotEqualTo(staff2);
    }
}
