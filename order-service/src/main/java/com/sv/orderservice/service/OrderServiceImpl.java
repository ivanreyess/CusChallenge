package com.sv.orderservice.service;

import com.sv.orderservice.domain.Order;
import com.sv.orderservice.dto.OrderDTO;
import com.sv.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sv.orderservice.domain.Order.toDto;
import static com.sv.orderservice.domain.Order.toEntity;

@Service
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        Order order = toEntity(orderDTO);
        order = orderRepository.save(order);
        return toDto(order);
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO) {
        return save(orderDTO);
    }

    @Override
    public Optional<OrderDTO> partialUpdate(OrderDTO orderDTO) {
        log.debug("Request to partially update Order : {}", orderDTO);

        return orderRepository
                .findById(orderDTO.id())
                .map(existingOrder -> {
                    if (orderDTO.name() != null) existingOrder.setName(orderDTO.name());
                    if (orderDTO.lastName() != null) existingOrder.setLastName(orderDTO.lastName());
                    if (orderDTO.total() != null) existingOrder.setTotal(orderDTO.total());
                    if (orderDTO.city() != null) existingOrder.setCity(orderDTO.city());
                    if (orderDTO.country() != null) existingOrder.setCountry(orderDTO.country());
                    if (orderDTO.email() != null) existingOrder.setEmail(orderDTO.email());
                    if (orderDTO.shippingAddress() != null) existingOrder.setShippingAddress(orderDTO.shippingAddress());
                    return existingOrder;
                })
                .map(orderRepository::save)
                .map(Order::toDto);
    }

    @Override
    public Page<OrderDTO> findAll(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Order> result = orderRepository.findAll(pageable);
        return result.map(Order::toDto);
    }

    @Override
    public Optional<OrderDTO> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id).map(Order::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }
}
