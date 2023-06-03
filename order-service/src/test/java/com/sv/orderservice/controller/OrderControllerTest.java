package com.sv.orderservice.controller;

import com.sv.orderservice.dto.OrderDTO;
import com.sv.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATED_DATE = 1L;
    private static final Long UPDATED_CREATED_DATE = 2L;

    private static final Long DEFAULT_MODIFIED_DATE = 1L;
    private static final Long UPDATED_MODIFIED_DATE = 2L;

    private static final String ENTITY_API_URL = "/api/orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private MockMvc restOrderMockMvc;

    @MockBean
    private OrderService orderService;

    OrderDTO orderDTO1, orderDTO2;

    @BeforeEach
    void setUp() {

        orderDTO1 = OrderDTO.builder()
                .name(DEFAULT_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .city(DEFAULT_CITY)
                .country(DEFAULT_COUNTRY)
                .email(DEFAULT_EMAIL)
                .total(DEFAULT_TOTAL)
                .shippingAddress(DEFAULT_SHIPPING_ADDRESS)
                .createdDate(DEFAULT_CREATED_DATE)
                .modifiedDate(DEFAULT_MODIFIED_DATE)
                .build();


        orderDTO2 = OrderDTO.builder()
                .id(2L)
                .name(UPDATED_NAME)
                .lastName(UPDATED_LAST_NAME)
                .city(UPDATED_CITY)
                .country(UPDATED_COUNTRY)
                .email(UPDATED_EMAIL)
                .total(UPDATED_TOTAL)
                .shippingAddress(UPDATED_SHIPPING_ADDRESS)
                .createdDate(UPDATED_CREATED_DATE)
                .modifiedDate(UPDATED_MODIFIED_DATE)
                .build();

    }

    @Test
    @DisplayName("POST /api/orders")
    void createOrder() throws Exception {

        given(orderService.save(any())).willReturn(orderDTO1);

        restOrderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderDTO1)))
                .andExpect(status().isCreated());

        assertEquals(DEFAULT_CITY, orderDTO1.city());
        assertEquals(DEFAULT_COUNTRY, orderDTO1.country());
        assertEquals(DEFAULT_NAME, orderDTO1.name());

    }

    @Test
    @DisplayName("PUT " + ENTITY_API_URL_ID  + " OK")
    void updateOrder() throws Exception {
        given(orderService.exists(2L)).willReturn(true);
        given(orderService.update(orderDTO2)).willReturn(orderDTO2);

        restOrderMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, orderDTO2.id())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(orderDTO2))
                )
                .andExpect(status().isOk());

        assertEquals(UPDATED_NAME, orderDTO2.name());
        assertEquals(UPDATED_LAST_NAME, orderDTO2.lastName());
        assertEquals(UPDATED_CITY, orderDTO2.city());
        assertEquals(UPDATED_COUNTRY, orderDTO2.country());
        assertEquals(UPDATED_EMAIL, orderDTO2.email());
        assertEquals(UPDATED_SHIPPING_ADDRESS, orderDTO2.shippingAddress());
    }

    @Test
    @DisplayName("PATCH " + ENTITY_API_URL_ID  + " OK")
    void partialUpdateOrder() throws Exception {

        given(orderService.exists(2L)).willReturn(true);
        given(orderService.partialUpdate(orderDTO2)).willReturn(Optional.ofNullable(orderDTO2));

        restOrderMockMvc
                .perform(
                        patch(ENTITY_API_URL_ID, orderDTO2.id())
                                .contentType("application/merge-patch+json")
                                .content(TestUtil.convertObjectToJsonBytes(orderDTO2))
                )
                .andExpect(status().isOk());

        assertEquals(UPDATED_NAME, orderDTO2.name());
        assertEquals(UPDATED_LAST_NAME, orderDTO2.lastName());
        assertEquals(UPDATED_CITY, orderDTO2.city());
        assertEquals(UPDATED_COUNTRY, orderDTO2.country());
        assertEquals(UPDATED_EMAIL, orderDTO2.email());
        assertEquals(UPDATED_SHIPPING_ADDRESS, orderDTO2.shippingAddress());
    }

    @Test
    @DisplayName("GET " + ENTITY_API_URL  + " OK")
    void getAllOrders() throws Exception {
        List<OrderDTO> orders = Stream.of(orderDTO1).toList();
        Page<OrderDTO> orderPage = new PageImpl<>(orders);

        given(orderService.findAll(anyInt(), anyInt(), anyString(), anyString())).willReturn(orderPage);

        restOrderMockMvc
                .perform(get(ENTITY_API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
                .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
                .andExpect(jsonPath("$.[*].shippingAddress").value(hasItem(DEFAULT_SHIPPING_ADDRESS)));


    }

    @Test
    @DisplayName("GET " + ENTITY_API_URL_ID  + " OK")
    void getOrder() throws Exception {
        orderDTO1 = OrderDTO.builder()
                .id(1L)
                .name(DEFAULT_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .city(DEFAULT_CITY)
                .country(DEFAULT_COUNTRY)
                .email(DEFAULT_EMAIL)
                .total(DEFAULT_TOTAL)
                .shippingAddress(DEFAULT_SHIPPING_ADDRESS)
                .createdDate(DEFAULT_CREATED_DATE)
                .modifiedDate(DEFAULT_MODIFIED_DATE)
                .build();
        given(orderService.findOne(1L)).willReturn(Optional.ofNullable(orderDTO1));
        restOrderMockMvc
                .perform(get(ENTITY_API_URL_ID, orderDTO1.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(orderDTO1.id().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
                .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
                .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL))
                .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
                .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
                .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
                .andExpect(jsonPath("$.shippingAddress").value(DEFAULT_SHIPPING_ADDRESS));

    }

    @Test
    @DisplayName("DELETE " + ENTITY_API_URL_ID  + " no content ")
    void deleteOrder() throws Exception {
        doNothing().when(orderService).delete(1L);
        restOrderMockMvc.perform(delete(ENTITY_API_URL_ID, 1L))
                .andExpect(status().isNoContent());

    }
}