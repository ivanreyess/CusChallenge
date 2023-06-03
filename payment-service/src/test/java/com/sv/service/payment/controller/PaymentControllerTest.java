package com.sv.service.payment.controller;

import com.sv.service.payment.domain.PaymentStatus;
import com.sv.service.payment.dto.PaymentDTO;
import com.sv.service.payment.service.PaymentService;
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

import static com.sv.service.payment.domain.PaymentStatus.COMPLETED;
import static com.sv.service.payment.domain.PaymentStatus.FAILED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

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

    @Autowired
    private MockMvc restPaymentMockMvc;

    @MockBean
    private PaymentService paymentService;

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
    @DisplayName("POST " + ENTITY_API_URL)
    void createPayment() throws Exception {

        given(paymentService.save(any(PaymentDTO.class))).willReturn(paymentDTO1);

        restPaymentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentDTO1)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("PUT " + ENTITY_API_URL_ID  + " OK")
    void updatePayment() throws Exception {
        paymentDTO2 = PaymentDTO.builder()
                .id(2L)
                .orderId(UPDATED_ORDER_ID)
                .status(UPDATED_STATUS)
                .total(UPDATED_TOTAL)
                .build();
        given(paymentService.exist(2L)).willReturn(true);
        given(paymentService.update(paymentDTO2)).willReturn(paymentDTO2);

        restPaymentMockMvc
                .perform(
                        put(ENTITY_API_URL_ID, 2L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(paymentDTO2))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH " + ENTITY_API_URL_ID  + " OK")
    void partialUpdatePayment() throws Exception {

        paymentDTO2 = PaymentDTO.builder()
                .id(2L)
                .orderId(UPDATED_ORDER_ID)
                .status(UPDATED_STATUS)
                .total(UPDATED_TOTAL)
                .build();
        given(paymentService.exist(2L)).willReturn(true);
        given(paymentService.partialUpdate(paymentDTO2)).willReturn(Optional.ofNullable(paymentDTO2));

        restPaymentMockMvc
                .perform(
                        patch(ENTITY_API_URL_ID, paymentDTO2.id())
                                .contentType("application/merge-patch+json")
                                .content(TestUtil.convertObjectToJsonBytes(paymentDTO2))
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET " + ENTITY_API_URL  + " OK")
    void getAllPayments() throws Exception {
        List<PaymentDTO> payments = Stream.of(paymentDTO1).toList();
        Page<PaymentDTO> paymentPage = new PageImpl<>(payments);

        given(paymentService.findAll(anyInt(), anyInt(), anyString(), anyString())).willReturn(paymentPage);

        restPaymentMockMvc
                .perform(get(ENTITY_API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

    }

    @Test
    @DisplayName("GET " + ENTITY_API_URL_ID  + " OK")
    void getPayment() throws Exception {
        paymentDTO1 = PaymentDTO.builder()
                .id(1L)
                .orderId(DEFAULT_ORDER_ID)
                .status(DEFAULT_STATUS)
                .total(DEFAULT_TOTAL)
                .build();

        given(paymentService.findOne(1L)).willReturn(Optional.ofNullable(paymentDTO1));

        restPaymentMockMvc
                .perform(get(ENTITY_API_URL_ID, paymentDTO1.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));


    }

    @Test
    @DisplayName("DELETE " + ENTITY_API_URL_ID  + " no content ")
    void deletePayment() throws Exception {
        doNothing().when(paymentService).delete(1L);
        restPaymentMockMvc.perform(delete(ENTITY_API_URL_ID, 1L))
                .andExpect(status().isNoContent());
    }
}