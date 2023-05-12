package com.sid.gl.service;

import com.sid.gl.domain.Order;
import com.sid.gl.service.dto.OrderDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Order}.
 */
public interface OrderService {
    /**
     * Save a order.
     *
     * @param order the entity to save.
     * @return the persisted entity.
     */
    Order save(Order order);

    Order saveOrderUser(OrderDTO orderDTO);
    /**
     * Updates a order.
     *
     * @param order the entity to update.
     * @return the persisted entity.
     */
    Order update(Order order);

    /**
     * Partially updates a order.
     *
     * @param order the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Order> partialUpdate(Order order);

    /**
     * Get all the orders.
     *
     * @return the list of entities.
     */
    List<Order> findAll(boolean isAdmin);

    /**
     * Get all the orders with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Order> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" order.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Order> findOne(Long id);

    /**
     * Delete the "id" order.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
