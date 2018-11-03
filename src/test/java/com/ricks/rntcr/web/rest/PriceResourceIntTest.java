package com.ricks.rntcr.web.rest;

import com.ricks.rntcr.NewprjApp;
import com.ricks.rntcr.domain.Price;
import com.ricks.rntcr.domain.enumeration.CarClass;
import com.ricks.rntcr.repository.PriceRepository;
import com.ricks.rntcr.service.PriceQueryService;
import com.ricks.rntcr.service.PriceService;
import com.ricks.rntcr.service.dto.PriceDTO;
import com.ricks.rntcr.service.mapper.PriceMapper;
import com.ricks.rntcr.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import java.util.List;

import static com.ricks.rntcr.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Test class for the PriceResource REST controller.
 *
 * @see PriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewprjApp.class)
public class PriceResourceIntTest {

    private static final LocalDate DEFAULT_ADJ_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADJ_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERIAL = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Integer DEFAULT_FACTOR_ONE = 1;
    private static final Integer UPDATED_FACTOR_ONE = 2;

    private static final Integer DEFAULT_FACTOR_TWO = 1;
    private static final Integer UPDATED_FACTOR_TWO = 2;

    private static final Integer DEFAULT_FACTOR_THREE = 1;
    private static final Integer UPDATED_FACTOR_THREE = 2;

    private static final Double DEFAULT_TAX = 1D;
    private static final Double UPDATED_TAX = 2D;

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    private static final CarClass DEFAULT_CLASSIFICATION = CarClass.AAA;
    private static final CarClass UPDATED_CLASSIFICATION = CarClass.BBB;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private PriceMapper priceMapper;
    
    @Autowired
    private PriceService priceService;

    @Autowired
    private PriceQueryService priceQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPriceMockMvc;

    private Price price;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PriceResource priceResource = new PriceResource(priceService, priceQueryService);
        this.restPriceMockMvc = MockMvcBuilders.standaloneSetup(priceResource)
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
    public static Price createEntity(EntityManager em) {
        Price price = new Price()
            .adjDate(DEFAULT_ADJ_DATE)
            .name(DEFAULT_NAME)
            .serial(DEFAULT_SERIAL)
            .price(DEFAULT_PRICE)
            .factorOne(DEFAULT_FACTOR_ONE)
            .factorTwo(DEFAULT_FACTOR_TWO)
            .factorThree(DEFAULT_FACTOR_THREE)
            .tax(DEFAULT_TAX)
            .total(DEFAULT_TOTAL)
            .classification(DEFAULT_CLASSIFICATION);
        return price;
    }

    @Before
    public void initTest() {
        price = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrice() throws Exception {
        int databaseSizeBeforeCreate = priceRepository.findAll().size();

        // Create the Price
        PriceDTO priceDTO = priceMapper.toDto(price);
        restPriceMockMvc.perform(post("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isCreated());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeCreate + 1);
        Price testPrice = priceList.get(priceList.size() - 1);
        assertThat(testPrice.getAdjDate()).isEqualTo(DEFAULT_ADJ_DATE);
        assertThat(testPrice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPrice.getSerial()).isEqualTo(DEFAULT_SERIAL);
        assertThat(testPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPrice.getFactorOne()).isEqualTo(DEFAULT_FACTOR_ONE);
        assertThat(testPrice.getFactorTwo()).isEqualTo(DEFAULT_FACTOR_TWO);
        assertThat(testPrice.getFactorThree()).isEqualTo(DEFAULT_FACTOR_THREE);
        assertThat(testPrice.getTax()).isEqualTo(DEFAULT_TAX);
        assertThat(testPrice.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testPrice.getClassification()).isEqualTo(DEFAULT_CLASSIFICATION);
    }

    @Test
    @Transactional
    public void createPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priceRepository.findAll().size();

        // Create the Price with an existing ID
        price.setId(1L);
        PriceDTO priceDTO = priceMapper.toDto(price);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceMockMvc.perform(post("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrices() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList
        restPriceMockMvc.perform(get("/api/prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(price.getId().intValue())))
            .andExpect(jsonPath("$.[*].adjDate").value(hasItem(DEFAULT_ADJ_DATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serial").value(hasItem(DEFAULT_SERIAL.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].factorOne").value(hasItem(DEFAULT_FACTOR_ONE)))
            .andExpect(jsonPath("$.[*].factorTwo").value(hasItem(DEFAULT_FACTOR_TWO)))
            .andExpect(jsonPath("$.[*].factorThree").value(hasItem(DEFAULT_FACTOR_THREE)))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].classification").value(hasItem(DEFAULT_CLASSIFICATION.toString())));
    }
    
    @Test
    @Transactional
    public void getPrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get the price
        restPriceMockMvc.perform(get("/api/prices/{id}", price.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(price.getId().intValue()))
            .andExpect(jsonPath("$.adjDate").value(DEFAULT_ADJ_DATE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.serial").value(DEFAULT_SERIAL.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.factorOne").value(DEFAULT_FACTOR_ONE))
            .andExpect(jsonPath("$.factorTwo").value(DEFAULT_FACTOR_TWO))
            .andExpect(jsonPath("$.factorThree").value(DEFAULT_FACTOR_THREE))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.doubleValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.classification").value(DEFAULT_CLASSIFICATION.toString()));
    }

    @Test
    @Transactional
    public void getAllPricesByAdjDateIsEqualToSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where adjDate equals to DEFAULT_ADJ_DATE
        defaultPriceShouldBeFound("adjDate.equals=" + DEFAULT_ADJ_DATE);

        // Get all the priceList where adjDate equals to UPDATED_ADJ_DATE
        defaultPriceShouldNotBeFound("adjDate.equals=" + UPDATED_ADJ_DATE);
    }

    @Test
    @Transactional
    public void getAllPricesByAdjDateIsInShouldWork() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where adjDate in DEFAULT_ADJ_DATE or UPDATED_ADJ_DATE
        defaultPriceShouldBeFound("adjDate.in=" + DEFAULT_ADJ_DATE + "," + UPDATED_ADJ_DATE);

        // Get all the priceList where adjDate equals to UPDATED_ADJ_DATE
        defaultPriceShouldNotBeFound("adjDate.in=" + UPDATED_ADJ_DATE);
    }

    @Test
    @Transactional
    public void getAllPricesByAdjDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where adjDate is not null
        defaultPriceShouldBeFound("adjDate.specified=true");

        // Get all the priceList where adjDate is null
        defaultPriceShouldNotBeFound("adjDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPricesByAdjDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where adjDate greater than or equals to DEFAULT_ADJ_DATE
        defaultPriceShouldBeFound("adjDate.greaterOrEqualThan=" + DEFAULT_ADJ_DATE);

        // Get all the priceList where adjDate greater than or equals to UPDATED_ADJ_DATE
        defaultPriceShouldNotBeFound("adjDate.greaterOrEqualThan=" + UPDATED_ADJ_DATE);
    }

    @Test
    @Transactional
    public void getAllPricesByAdjDateIsLessThanSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where adjDate less than or equals to DEFAULT_ADJ_DATE
        defaultPriceShouldNotBeFound("adjDate.lessThan=" + DEFAULT_ADJ_DATE);

        // Get all the priceList where adjDate less than or equals to UPDATED_ADJ_DATE
        defaultPriceShouldBeFound("adjDate.lessThan=" + UPDATED_ADJ_DATE);
    }


    @Test
    @Transactional
    public void getAllPricesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where name equals to DEFAULT_NAME
        defaultPriceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the priceList where name equals to UPDATED_NAME
        defaultPriceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPricesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPriceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the priceList where name equals to UPDATED_NAME
        defaultPriceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPricesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where name is not null
        defaultPriceShouldBeFound("name.specified=true");

        // Get all the priceList where name is null
        defaultPriceShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllPricesBySerialIsEqualToSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where serial equals to DEFAULT_SERIAL
        defaultPriceShouldBeFound("serial.equals=" + DEFAULT_SERIAL);

        // Get all the priceList where serial equals to UPDATED_SERIAL
        defaultPriceShouldNotBeFound("serial.equals=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllPricesBySerialIsInShouldWork() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where serial in DEFAULT_SERIAL or UPDATED_SERIAL
        defaultPriceShouldBeFound("serial.in=" + DEFAULT_SERIAL + "," + UPDATED_SERIAL);

        // Get all the priceList where serial equals to UPDATED_SERIAL
        defaultPriceShouldNotBeFound("serial.in=" + UPDATED_SERIAL);
    }

    @Test
    @Transactional
    public void getAllPricesBySerialIsNullOrNotNull() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where serial is not null
        defaultPriceShouldBeFound("serial.specified=true");

        // Get all the priceList where serial is null
        defaultPriceShouldNotBeFound("serial.specified=false");
    }

    @Test
    @Transactional
    public void getAllPricesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where price equals to DEFAULT_PRICE
        defaultPriceShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the priceList where price equals to UPDATED_PRICE
        defaultPriceShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllPricesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultPriceShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the priceList where price equals to UPDATED_PRICE
        defaultPriceShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllPricesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where price is not null
        defaultPriceShouldBeFound("price.specified=true");

        // Get all the priceList where price is null
        defaultPriceShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllPricesByFactorOneIsEqualToSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where factorOne equals to DEFAULT_FACTOR_ONE
        defaultPriceShouldBeFound("factorOne.equals=" + DEFAULT_FACTOR_ONE);

        // Get all the priceList where factorOne equals to UPDATED_FACTOR_ONE
        defaultPriceShouldNotBeFound("factorOne.equals=" + UPDATED_FACTOR_ONE);
    }

    @Test
    @Transactional
    public void getAllPricesByFactorOneIsInShouldWork() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where factorOne in DEFAULT_FACTOR_ONE or UPDATED_FACTOR_ONE
        defaultPriceShouldBeFound("factorOne.in=" + DEFAULT_FACTOR_ONE + "," + UPDATED_FACTOR_ONE);

        // Get all the priceList where factorOne equals to UPDATED_FACTOR_ONE
        defaultPriceShouldNotBeFound("factorOne.in=" + UPDATED_FACTOR_ONE);
    }

    @Test
    @Transactional
    public void getAllPricesByFactorOneIsNullOrNotNull() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where factorOne is not null
        defaultPriceShouldBeFound("factorOne.specified=true");

        // Get all the priceList where factorOne is null
        defaultPriceShouldNotBeFound("factorOne.specified=false");
    }

    @Test
    @Transactional
    public void getAllPricesByFactorOneIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where factorOne greater than or equals to DEFAULT_FACTOR_ONE
        defaultPriceShouldBeFound("factorOne.greaterOrEqualThan=" + DEFAULT_FACTOR_ONE);

        // Get all the priceList where factorOne greater than or equals to UPDATED_FACTOR_ONE
        defaultPriceShouldNotBeFound("factorOne.greaterOrEqualThan=" + UPDATED_FACTOR_ONE);
    }

    @Test
    @Transactional
    public void getAllPricesByFactorOneIsLessThanSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where factorOne less than or equals to DEFAULT_FACTOR_ONE
        defaultPriceShouldNotBeFound("factorOne.lessThan=" + DEFAULT_FACTOR_ONE);

        // Get all the priceList where factorOne less than or equals to UPDATED_FACTOR_ONE
        defaultPriceShouldBeFound("factorOne.lessThan=" + UPDATED_FACTOR_ONE);
    }


    @Test
    @Transactional
    public void getAllPricesByFactorTwoIsEqualToSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where factorTwo equals to DEFAULT_FACTOR_TWO
        defaultPriceShouldBeFound("factorTwo.equals=" + DEFAULT_FACTOR_TWO);

        // Get all the priceList where factorTwo equals to UPDATED_FACTOR_TWO
        defaultPriceShouldNotBeFound("factorTwo.equals=" + UPDATED_FACTOR_TWO);
    }

    @Test
    @Transactional
    public void getAllPricesByFactorTwoIsInShouldWork() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where factorTwo in DEFAULT_FACTOR_TWO or UPDATED_FACTOR_TWO
        defaultPriceShouldBeFound("factorTwo.in=" + DEFAULT_FACTOR_TWO + "," + UPDATED_FACTOR_TWO);

        // Get all the priceList where factorTwo equals to UPDATED_FACTOR_TWO
        defaultPriceShouldNotBeFound("factorTwo.in=" + UPDATED_FACTOR_TWO);
    }

    @Test
    @Transactional
    public void getAllPricesByFactorTwoIsNullOrNotNull() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where factorTwo is not null
        defaultPriceShouldBeFound("factorTwo.specified=true");

        // Get all the priceList where factorTwo is null
        defaultPriceShouldNotBeFound("factorTwo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPricesByFactorTwoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where factorTwo greater than or equals to DEFAULT_FACTOR_TWO
        defaultPriceShouldBeFound("factorTwo.greaterOrEqualThan=" + DEFAULT_FACTOR_TWO);

        // Get all the priceList where factorTwo greater than or equals to UPDATED_FACTOR_TWO
        defaultPriceShouldNotBeFound("factorTwo.greaterOrEqualThan=" + UPDATED_FACTOR_TWO);
    }

    @Test
    @Transactional
    public void getAllPricesByFactorTwoIsLessThanSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where factorTwo less than or equals to DEFAULT_FACTOR_TWO
        defaultPriceShouldNotBeFound("factorTwo.lessThan=" + DEFAULT_FACTOR_TWO);

        // Get all the priceList where factorTwo less than or equals to UPDATED_FACTOR_TWO
        defaultPriceShouldBeFound("factorTwo.lessThan=" + UPDATED_FACTOR_TWO);
    }


    @Test
    @Transactional
    public void getAllPricesByFactorThreeIsEqualToSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where factorThree equals to DEFAULT_FACTOR_THREE
        defaultPriceShouldBeFound("factorThree.equals=" + DEFAULT_FACTOR_THREE);

        // Get all the priceList where factorThree equals to UPDATED_FACTOR_THREE
        defaultPriceShouldNotBeFound("factorThree.equals=" + UPDATED_FACTOR_THREE);
    }

    @Test
    @Transactional
    public void getAllPricesByFactorThreeIsInShouldWork() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where factorThree in DEFAULT_FACTOR_THREE or UPDATED_FACTOR_THREE
        defaultPriceShouldBeFound("factorThree.in=" + DEFAULT_FACTOR_THREE + "," + UPDATED_FACTOR_THREE);

        // Get all the priceList where factorThree equals to UPDATED_FACTOR_THREE
        defaultPriceShouldNotBeFound("factorThree.in=" + UPDATED_FACTOR_THREE);
    }

    @Test
    @Transactional
    public void getAllPricesByFactorThreeIsNullOrNotNull() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where factorThree is not null
        defaultPriceShouldBeFound("factorThree.specified=true");

        // Get all the priceList where factorThree is null
        defaultPriceShouldNotBeFound("factorThree.specified=false");
    }

    @Test
    @Transactional
    public void getAllPricesByFactorThreeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where factorThree greater than or equals to DEFAULT_FACTOR_THREE
        defaultPriceShouldBeFound("factorThree.greaterOrEqualThan=" + DEFAULT_FACTOR_THREE);

        // Get all the priceList where factorThree greater than or equals to UPDATED_FACTOR_THREE
        defaultPriceShouldNotBeFound("factorThree.greaterOrEqualThan=" + UPDATED_FACTOR_THREE);
    }

    @Test
    @Transactional
    public void getAllPricesByFactorThreeIsLessThanSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where factorThree less than or equals to DEFAULT_FACTOR_THREE
        defaultPriceShouldNotBeFound("factorThree.lessThan=" + DEFAULT_FACTOR_THREE);

        // Get all the priceList where factorThree less than or equals to UPDATED_FACTOR_THREE
        defaultPriceShouldBeFound("factorThree.lessThan=" + UPDATED_FACTOR_THREE);
    }


    @Test
    @Transactional
    public void getAllPricesByTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where tax equals to DEFAULT_TAX
        defaultPriceShouldBeFound("tax.equals=" + DEFAULT_TAX);

        // Get all the priceList where tax equals to UPDATED_TAX
        defaultPriceShouldNotBeFound("tax.equals=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllPricesByTaxIsInShouldWork() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where tax in DEFAULT_TAX or UPDATED_TAX
        defaultPriceShouldBeFound("tax.in=" + DEFAULT_TAX + "," + UPDATED_TAX);

        // Get all the priceList where tax equals to UPDATED_TAX
        defaultPriceShouldNotBeFound("tax.in=" + UPDATED_TAX);
    }

    @Test
    @Transactional
    public void getAllPricesByTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where tax is not null
        defaultPriceShouldBeFound("tax.specified=true");

        // Get all the priceList where tax is null
        defaultPriceShouldNotBeFound("tax.specified=false");
    }

    @Test
    @Transactional
    public void getAllPricesByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where total equals to DEFAULT_TOTAL
        defaultPriceShouldBeFound("total.equals=" + DEFAULT_TOTAL);

        // Get all the priceList where total equals to UPDATED_TOTAL
        defaultPriceShouldNotBeFound("total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllPricesByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where total in DEFAULT_TOTAL or UPDATED_TOTAL
        defaultPriceShouldBeFound("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL);

        // Get all the priceList where total equals to UPDATED_TOTAL
        defaultPriceShouldNotBeFound("total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllPricesByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where total is not null
        defaultPriceShouldBeFound("total.specified=true");

        // Get all the priceList where total is null
        defaultPriceShouldNotBeFound("total.specified=false");
    }

    @Test
    @Transactional
    public void getAllPricesByClassificationIsEqualToSomething() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where classification equals to DEFAULT_CLASSIFICATION
        defaultPriceShouldBeFound("classification.equals=" + DEFAULT_CLASSIFICATION);

        // Get all the priceList where classification equals to UPDATED_CLASSIFICATION
        defaultPriceShouldNotBeFound("classification.equals=" + UPDATED_CLASSIFICATION);
    }

    @Test
    @Transactional
    public void getAllPricesByClassificationIsInShouldWork() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where classification in DEFAULT_CLASSIFICATION or UPDATED_CLASSIFICATION
        defaultPriceShouldBeFound("classification.in=" + DEFAULT_CLASSIFICATION + "," + UPDATED_CLASSIFICATION);

        // Get all the priceList where classification equals to UPDATED_CLASSIFICATION
        defaultPriceShouldNotBeFound("classification.in=" + UPDATED_CLASSIFICATION);
    }

    @Test
    @Transactional
    public void getAllPricesByClassificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList where classification is not null
        defaultPriceShouldBeFound("classification.specified=true");

        // Get all the priceList where classification is null
        defaultPriceShouldNotBeFound("classification.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPriceShouldBeFound(String filter) throws Exception {
        restPriceMockMvc.perform(get("/api/prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(price.getId().intValue())))
            .andExpect(jsonPath("$.[*].adjDate").value(hasItem(DEFAULT_ADJ_DATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serial").value(hasItem(DEFAULT_SERIAL.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].factorOne").value(hasItem(DEFAULT_FACTOR_ONE)))
            .andExpect(jsonPath("$.[*].factorTwo").value(hasItem(DEFAULT_FACTOR_TWO)))
            .andExpect(jsonPath("$.[*].factorThree").value(hasItem(DEFAULT_FACTOR_THREE)))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].classification").value(hasItem(DEFAULT_CLASSIFICATION.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPriceShouldNotBeFound(String filter) throws Exception {
        restPriceMockMvc.perform(get("/api/prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingPrice() throws Exception {
        // Get the price
        restPriceMockMvc.perform(get("/api/prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        int databaseSizeBeforeUpdate = priceRepository.findAll().size();

        // Update the price
        Price updatedPrice = priceRepository.findById(price.getId()).get();
        // Disconnect from session so that the updates on updatedPrice are not directly saved in db
        em.detach(updatedPrice);
        updatedPrice
            .adjDate(UPDATED_ADJ_DATE)
            .name(UPDATED_NAME)
            .serial(UPDATED_SERIAL)
            .price(UPDATED_PRICE)
            .factorOne(UPDATED_FACTOR_ONE)
            .factorTwo(UPDATED_FACTOR_TWO)
            .factorThree(UPDATED_FACTOR_THREE)
            .tax(UPDATED_TAX)
            .total(UPDATED_TOTAL)
            .classification(UPDATED_CLASSIFICATION);
        PriceDTO priceDTO = priceMapper.toDto(updatedPrice);

        restPriceMockMvc.perform(put("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isOk());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeUpdate);
        Price testPrice = priceList.get(priceList.size() - 1);
        assertThat(testPrice.getAdjDate()).isEqualTo(UPDATED_ADJ_DATE);
        assertThat(testPrice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPrice.getSerial()).isEqualTo(UPDATED_SERIAL);
        assertThat(testPrice.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPrice.getFactorOne()).isEqualTo(UPDATED_FACTOR_ONE);
        assertThat(testPrice.getFactorTwo()).isEqualTo(UPDATED_FACTOR_TWO);
        assertThat(testPrice.getFactorThree()).isEqualTo(UPDATED_FACTOR_THREE);
        assertThat(testPrice.getTax()).isEqualTo(UPDATED_TAX);
        assertThat(testPrice.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testPrice.getClassification()).isEqualTo(UPDATED_CLASSIFICATION);
    }

    @Test
    @Transactional
    public void updateNonExistingPrice() throws Exception {
        int databaseSizeBeforeUpdate = priceRepository.findAll().size();

        // Create the Price
        PriceDTO priceDTO = priceMapper.toDto(price);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriceMockMvc.perform(put("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        int databaseSizeBeforeDelete = priceRepository.findAll().size();

        // Get the price
        restPriceMockMvc.perform(delete("/api/prices/{id}", price.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Price.class);
        Price price1 = new Price();
        price1.setId(1L);
        Price price2 = new Price();
        price2.setId(price1.getId());
        assertThat(price1).isEqualTo(price2);
        price2.setId(2L);
        assertThat(price1).isNotEqualTo(price2);
        price1.setId(null);
        assertThat(price1).isNotEqualTo(price2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceDTO.class);
        PriceDTO priceDTO1 = new PriceDTO();
        priceDTO1.setId(1L);
        PriceDTO priceDTO2 = new PriceDTO();
        assertThat(priceDTO1).isNotEqualTo(priceDTO2);
        priceDTO2.setId(priceDTO1.getId());
        assertThat(priceDTO1).isEqualTo(priceDTO2);
        priceDTO2.setId(2L);
        assertThat(priceDTO1).isNotEqualTo(priceDTO2);
        priceDTO1.setId(null);
        assertThat(priceDTO1).isNotEqualTo(priceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(priceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(priceMapper.fromId(null)).isNull();
    }
}
