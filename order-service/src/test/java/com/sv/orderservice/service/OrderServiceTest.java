package com.sv.orderservice.service;

import com.sv.orderservice.domain.Order;
import com.sv.orderservice.dto.OrderDTO;
import com.sv.orderservice.dto.OrderDetailDTO;
import com.sv.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.sv.orderservice.config.AppConstants.*;
import static com.sv.orderservice.domain.Order.toDto;
import static com.sv.orderservice.domain.Order.toEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {


    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderDetailFeignService orderDetailService;

    OrderDTO orderDTO1, orderDTO2;

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


    @BeforeEach
    void setUp() {

        orderDTO1 = OrderDTO.builder()
                .id(1L)
                .total(DEFAULT_TOTAL)
                .name(DEFAULT_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .country(DEFAULT_COUNTRY)
                .city(DEFAULT_CITY)
                .shippingAddress(DEFAULT_SHIPPING_ADDRESS)
                .email(DEFAULT_EMAIL)
                .build();

        orderDTO2= OrderDTO.builder()
                .id(2L)
                .total(DEFAULT_TOTAL)
                .name(DEFAULT_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .country(DEFAULT_COUNTRY)
                .city(DEFAULT_CITY)
                .shippingAddress(DEFAULT_SHIPPING_ADDRESS)
                .email(DEFAULT_EMAIL)
                .build();
    }

    @Test
    void save() {
        Order order = toEntity(orderDTO1);
        given(orderRepository.save(any(Order.class))).willReturn(order);
        given(orderDetailService.getTotal()).willReturn(120d);

        OrderDTO result = orderService.save(orderDTO1);
        assertNotNull(result);
        assertEquals(1, result.id());
        assertEquals(DEFAULT_CITY, result.city());
    }

    @Test
    void update() {
        Order order = toEntity(orderDTO1);
        given(orderRepository.save(any(Order.class))).willReturn(order);
        given(orderDetailService.getTotal()).willReturn(120d);
        OrderDTO result = orderService.update(orderDTO1);
        assertNotNull(result);
        assertEquals(1, result.id());
        assertEquals(DEFAULT_CITY, result.city());
    }

    @Test
    void partialUpdate() {
        Order orderSaved = toEntity(orderDTO1);
        orderSaved.setTotal(100.00d);
        given(orderRepository.findById(any(Long.class))).willReturn(Optional.of(orderSaved));
        given(orderRepository.save(any(Order.class))).willReturn(orderSaved);
        Optional<OrderDTO> result = orderService.partialUpdate(toDto(orderSaved));
        assertFalse(result.isEmpty());
    }

    @Test
    void findAll() {
        List<Order> orders = Stream.of(orderDTO1, orderDTO2).map(Order::toEntity).toList();
        Page<Order> orderPage = new PageImpl<>(orders);
        given(orderRepository.findAll(any(Pageable.class))).willReturn(orderPage);
        Page<OrderDTO> result = orderService.findAll(Integer.parseInt(DEFAULT_PAGE_NUMBER), Integer.parseInt(DEFAULT_PAGE_SIZE),DEFAULT_SORT_BY, DEFAULT_SORT_DIRECTION);
        assertTrue(result.getContent().size() > 0);
        assertEquals(2, result.getContent().size());
    }

    @Test
    void findOne() {
        Order orderFound = toEntity(orderDTO2);
        given(orderRepository.findById(2L)).willReturn(Optional.of(orderFound));
        Optional<OrderDTO> result = orderService.findOne(2L);
        assertFalse(result.isEmpty());
    }

    @Test
    void findOneFailed() {
        given(orderRepository.findById(2L)).willReturn(Optional.empty());
        Optional<OrderDTO> result = orderService.findOne(2L);
        assertTrue(result.isEmpty());
    }

    @Test
    void delete() {
        doNothing().when(orderRepository).deleteById(1L);
        assertDoesNotThrow(() -> orderService.delete(1L));
    }
}