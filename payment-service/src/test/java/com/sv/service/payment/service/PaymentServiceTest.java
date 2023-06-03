package com.sv.service.payment.service;

import com.sv.service.payment.domain.Payment;
import com.sv.service.payment.domain.PaymentStatus;
import com.sv.service.payment.dto.PaymentDTO;
import com.sv.service.payment.repository.PaymentRepository;
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

import static com.sv.service.payment.config.AppConstants.*;
import static com.sv.service.payment.domain.Payment.toEntity;
import static com.sv.service.payment.domain.PaymentStatus.COMPLETED;
import static com.sv.service.payment.domain.PaymentStatus.FAILED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;


@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;


    PaymentDTO paymentDTO1, paymentDTO2;

    private static final Long DEFAULT_ORDER_ID = 1L;
    private static final Long UPDATED_ORDER_ID = 2L;

    private static final PaymentStatus DEFAULT_STATUS = COMPLETED;
    private static final PaymentStatus UPDATED_STATUS = FAILED;

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    private static final Long DEFAULT_CREATED_DATE = 1L;
    private static final Long UPDATED_CREATED_DATE = 2L;

    private static final Long DEFAULT_MODIFIED_DATE = 1L;
    private static final Long UPDATED_MODIFIED_DATE = 2L;

    private static final String ENTITY_API_URL = "/api/payments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @BeforeEach
    void setUp() {

        paymentDTO1 = PaymentDTO.builder()
                .orderId(DEFAULT_ORDER_ID)
                .status(DEFAULT_STATUS)
                .total(DEFAULT_TOTAL)
                .build();

        paymentDTO2 = PaymentDTO.builder()
                .orderId(UPDATED_ORDER_ID)
                .status(UPDATED_STATUS)
                .total(UPDATED_TOTAL)
                .build();
    }

    @Test
    void save() {

        Payment savedPayment = toEntity(paymentDTO1);
        given(paymentRepository.save(any())).willReturn(savedPayment);

        PaymentDTO result = paymentService.save(paymentDTO1);
        assertNotNull(result);
        assertEquals(COMPLETED, result.status());
    }

    @Test
    void update() {

        Payment savedPayment = toEntity(paymentDTO1);
        given(paymentRepository.save(any())).willReturn(savedPayment);

        PaymentDTO result = paymentService.update(paymentDTO1);
        assertNotNull(result);
        assertEquals(COMPLETED, result.status());
    }

    @Test
    void partialUpdate() {
        paymentDTO1 = PaymentDTO.builder()
                .id(1L)
                .orderId(DEFAULT_ORDER_ID)
                .status(DEFAULT_STATUS)
                .total(DEFAULT_TOTAL)
                .build();

        Payment payment = toEntity(paymentDTO1);
        given(paymentRepository.save(any())).willReturn(payment);
        given(paymentRepository.findById(1L)).willReturn(Optional.ofNullable(payment));
        Optional<PaymentDTO> result = paymentService.partialUpdate(paymentDTO1);
        assertFalse(result.isEmpty());
    }

    @Test
    void findAll() {
        List<Payment> payments = Stream.of(paymentDTO1, paymentDTO2).map(Payment::toEntity).toList();
        Page<Payment> paymentPage = new PageImpl<>(payments);
        given(paymentRepository.findAll(any(Pageable.class))).willReturn(paymentPage);
        Page<PaymentDTO> result = paymentService.findAll(Integer.parseInt(DEFAULT_PAGE_NUMBER), Integer.parseInt(DEFAULT_PAGE_SIZE),DEFAULT_SORT_BY, DEFAULT_SORT_DIRECTION);
        assertEquals(2, result.getContent().size());
    }

    @Test
    void findOne() {
        paymentDTO2 = PaymentDTO.builder()
                .id(2L)
                .orderId(DEFAULT_ORDER_ID)
                .status(DEFAULT_STATUS)
                .total(DEFAULT_TOTAL)
                .build();
        Payment paymentFound = toEntity(paymentDTO2);
        given(paymentRepository.findById(2L)).willReturn(Optional.of(paymentFound));
        Optional<PaymentDTO> result = paymentService.findOne(2L);

        assertEquals(2L, result.get().id());

    }

    @Test
    void delete() {
        doNothing().when(paymentRepository).deleteById(1L);
        assertDoesNotThrow(() -> paymentService.delete(1L));
    }

    @Test
    void exist() {
        given(paymentRepository.existsById(1L)).willReturn(true);
        boolean result = paymentService.exist(1L);
        assertTrue(result);
    }
}