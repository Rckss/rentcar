package com.ricks.rntcr.web.rest;

import com.ricks.rntcr.NewprjApp;
import com.ricks.rntcr.domain.RentHistory;
import com.ricks.rntcr.domain.enumeration.Status;
import com.ricks.rntcr.repository.RentHistoryRepository;
import com.ricks.rntcr.service.CarService;
import com.ricks.rntcr.service.RentHistoryService;
import com.ricks.rntcr.service.UserService;
import com.ricks.rntcr.service.dto.RentHistoryDTO;
import com.ricks.rntcr.service.mapper.RentHistoryMapper;
import com.ricks.rntcr.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.ricks.rntcr.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Test class for the RentHistoryResource REST controller.
 *
 * @see RentHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewprjApp.class)
public class RentHistoryResourceIntTest {

    private static final LocalDate DEFAULT_REG_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REG_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_TOTAL_PAID = 1D;
    private static final Double UPDATED_TOTAL_PAID = 2D;

    private static final Status DEFAULT_STATUS = Status.RUNNING;
    private static final Status UPDATED_STATUS = Status.PENDING;

    @Autowired
    private RentHistoryRepository rentHistoryRepository;

    @Autowired
    private UserService usrServ;

    @Mock
    private RentHistoryRepository rentHistoryRepositoryMock;

    @Autowired
    private RentHistoryMapper rentHistoryMapper;


    @Mock
    private RentHistoryService rentHistoryServiceMock;

    @Autowired
    private RentHistoryService rentHistoryService;

    @Autowired
    private CarService carService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRentHistoryMockMvc;

    private RentHistory rentHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RentHistoryResource rentHistoryResource = new RentHistoryResource(rentHistoryService, usrServ, carService);
        this.restRentHistoryMockMvc = MockMvcBuilders.standaloneSetup(rentHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RentHistory createEntity(EntityManager em) {
        RentHistory rentHistory = new RentHistory()
            .regDate(DEFAULT_REG_DATE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .totalPaid(DEFAULT_TOTAL_PAID)
            .status(DEFAULT_STATUS);
        return rentHistory;
    }

    @Before
    public void initTest() {
        rentHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createRentHistory() throws Exception {
        int databaseSizeBeforeCreate = rentHistoryRepository.findAll().size();

        // Create the RentHistory
        RentHistoryDTO rentHistoryDTO = rentHistoryMapper.toDto(rentHistory);
        restRentHistoryMockMvc.perform(post("/api/rent-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the RentHistory in the database
        List<RentHistory> rentHistoryList = rentHistoryRepository.findAll();
        assertThat(rentHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        RentHistory testRentHistory = rentHistoryList.get(rentHistoryList.size() - 1);
        assertThat(testRentHistory.getRegDate()).isEqualTo(DEFAULT_REG_DATE);
        assertThat(testRentHistory.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testRentHistory.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testRentHistory.getTotalPaid()).isEqualTo(DEFAULT_TOTAL_PAID);
        assertThat(testRentHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createRentHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rentHistoryRepository.findAll().size();

        // Create the RentHistory with an existing ID
        rentHistory.setId(1L);
        RentHistoryDTO rentHistoryDTO = rentHistoryMapper.toDto(rentHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRentHistoryMockMvc.perform(post("/api/rent-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RentHistory in the database
        List<RentHistory> rentHistoryList = rentHistoryRepository.findAll();
        assertThat(rentHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRentHistories() throws Exception {
        // Initialize the database
        rentHistoryRepository.saveAndFlush(rentHistory);

        // Get all the rentHistoryList
        restRentHistoryMockMvc.perform(get("/api/rent-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rentHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].regDate").value(hasItem(DEFAULT_REG_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalPaid").value(hasItem(DEFAULT_TOTAL_PAID.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    public void getAllRentHistoriesWithEagerRelationshipsIsEnabled() throws Exception {
        RentHistoryResource rentHistoryResource = new RentHistoryResource(rentHistoryServiceMock, usrServ, carService);
        when(rentHistoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRentHistoryMockMvc = MockMvcBuilders.standaloneSetup(rentHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRentHistoryMockMvc.perform(get("/api/rent-histories?eagerload=true"))
        .andExpect(status().isOk());

        verify(rentHistoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllRentHistoriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        RentHistoryResource rentHistoryResource = new RentHistoryResource(rentHistoryServiceMock, usrServ, carService);
            when(rentHistoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRentHistoryMockMvc = MockMvcBuilders.standaloneSetup(rentHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRentHistoryMockMvc.perform(get("/api/rent-histories?eagerload=true"))
        .andExpect(status().isOk());

            verify(rentHistoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRentHistory() throws Exception {
        // Initialize the database
        rentHistoryRepository.saveAndFlush(rentHistory);

        // Get the rentHistory
        restRentHistoryMockMvc.perform(get("/api/rent-histories/{id}", rentHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rentHistory.getId().intValue()))
            .andExpect(jsonPath("$.regDate").value(DEFAULT_REG_DATE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.totalPaid").value(DEFAULT_TOTAL_PAID.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRentHistory() throws Exception {
        // Get the rentHistory
        restRentHistoryMockMvc.perform(get("/api/rent-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRentHistory() throws Exception {
        // Initialize the database
        rentHistoryRepository.saveAndFlush(rentHistory);

        int databaseSizeBeforeUpdate = rentHistoryRepository.findAll().size();

        // Update the rentHistory
        RentHistory updatedRentHistory = rentHistoryRepository.findById(rentHistory.getId()).get();
        // Disconnect from session so that the updates on updatedRentHistory are not directly saved in db
        em.detach(updatedRentHistory);
        updatedRentHistory
            .regDate(UPDATED_REG_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .totalPaid(UPDATED_TOTAL_PAID)
            .status(UPDATED_STATUS);
        RentHistoryDTO rentHistoryDTO = rentHistoryMapper.toDto(updatedRentHistory);

        restRentHistoryMockMvc.perform(put("/api/rent-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the RentHistory in the database
        List<RentHistory> rentHistoryList = rentHistoryRepository.findAll();
        assertThat(rentHistoryList).hasSize(databaseSizeBeforeUpdate);
        RentHistory testRentHistory = rentHistoryList.get(rentHistoryList.size() - 1);
        assertThat(testRentHistory.getRegDate()).isEqualTo(UPDATED_REG_DATE);
        assertThat(testRentHistory.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testRentHistory.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRentHistory.getTotalPaid()).isEqualTo(UPDATED_TOTAL_PAID);
        assertThat(testRentHistory.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingRentHistory() throws Exception {
        int databaseSizeBeforeUpdate = rentHistoryRepository.findAll().size();

        // Create the RentHistory
        RentHistoryDTO rentHistoryDTO = rentHistoryMapper.toDto(rentHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentHistoryMockMvc.perform(put("/api/rent-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rentHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RentHistory in the database
        List<RentHistory> rentHistoryList = rentHistoryRepository.findAll();
        assertThat(rentHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRentHistory() throws Exception {
        // Initialize the database
        rentHistoryRepository.saveAndFlush(rentHistory);

        int databaseSizeBeforeDelete = rentHistoryRepository.findAll().size();

        // Get the rentHistory
        restRentHistoryMockMvc.perform(delete("/api/rent-histories/{id}", rentHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RentHistory> rentHistoryList = rentHistoryRepository.findAll();
        assertThat(rentHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RentHistory.class);
        RentHistory rentHistory1 = new RentHistory();
        rentHistory1.setId(1L);
        RentHistory rentHistory2 = new RentHistory();
        rentHistory2.setId(rentHistory1.getId());
        assertThat(rentHistory1).isEqualTo(rentHistory2);
        rentHistory2.setId(2L);
        assertThat(rentHistory1).isNotEqualTo(rentHistory2);
        rentHistory1.setId(null);
        assertThat(rentHistory1).isNotEqualTo(rentHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RentHistoryDTO.class);
        RentHistoryDTO rentHistoryDTO1 = new RentHistoryDTO();
        rentHistoryDTO1.setId(1L);
        RentHistoryDTO rentHistoryDTO2 = new RentHistoryDTO();
        assertThat(rentHistoryDTO1).isNotEqualTo(rentHistoryDTO2);
        rentHistoryDTO2.setId(rentHistoryDTO1.getId());
        assertThat(rentHistoryDTO1).isEqualTo(rentHistoryDTO2);
        rentHistoryDTO2.setId(2L);
        assertThat(rentHistoryDTO1).isNotEqualTo(rentHistoryDTO2);
        rentHistoryDTO1.setId(null);
        assertThat(rentHistoryDTO1).isNotEqualTo(rentHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rentHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rentHistoryMapper.fromId(null)).isNull();
    }
}
