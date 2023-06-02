package com.sv.orderdetailservice.service;

import com.sv.orderdetailservice.domain.OrderDetail;
import com.sv.orderdetailservice.domain.dto.ProductDTO;
import com.sv.orderdetailservice.repository.OrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderDetailServiceTest {

    @InjectMocks
    OrderDetailServiceImpl orderDetailService;

    @Mock
    OrderDetailRepository orderDetailRepository;

    OrderDetail orderDetail1, orderDetail2;
    ProductDTO productDTO1, productDTO2;

    @BeforeEach
    void setUp() {

        productDTO1 = ProductDTO.builder()
                .id(1)
                .title("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops")
                .price(109.95d)
                .description("Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday")
                .category("men's clothing")
                .image("https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg")
                .build();

        productDTO2 = ProductDTO.builder()
                .id(2)
                .title("Mens Casual Premium Slim Fit T-Shirts")
                .price(22.3d)
                .description("great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.")
                .category("men's clothing")
                .image("https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg")
                .build();
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void partialUpdate() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findOne() {
    }

    @Test
    void delete() {
    }
}