package com.sv.orderdetailservice.service;

import com.sv.orderdetailservice.domain.dto.OrderDetailDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface OrderDetailService {

    /**
     * Save a OrderDetail.
     *
     * @param orderDetailDTO the entity to save.
     * @return the persisted entity.
     */
    OrderDetailDTO save(OrderDetailDTO orderDetailDTO);

    /**
     * Updates a OrderDetail.
     *
     * @param orderDetailDTO the entity to update.
     * @return the persisted entity.
     */
    OrderDetailDTO update(OrderDetailDTO orderDetailDTO);

    /**
     * Partially updates a OrderDetail.
     *
     * @param orderDetailDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrderDetailDTO> partialUpdate(OrderDetailDTO orderDetailDTO);

    /**
     * Get all the OrderDetails.
     *
     * @param pageNo the pagination information.
     * @param pageSize the pagination information.
     * @param sortBy the pagination information.
     * @param sortDir the pagination information.
     * @return the list of entities.
     */
    Page<OrderDetailDTO> findAll(int pageNo, int pageSize, String sortBy, String sortDir);

    /**
     * Get the "id" OrderDetail.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderDetailDTO> findOne(Long id);

    /**
     * Delete the "id" OrderDetail.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

}
