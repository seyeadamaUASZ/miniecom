package com.sid.gl.service.impl;

import com.sid.gl.domain.Order;
import com.sid.gl.domain.Product;
import com.sid.gl.domain.User;
import com.sid.gl.repository.OrderRepository;
import com.sid.gl.repository.ProductRepository;
import com.sid.gl.repository.UserRepository;
import com.sid.gl.security.SecurityUtils;
import com.sid.gl.service.OrderService;
import com.sid.gl.service.dto.OrderDTO;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Order}.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Order save(Order order) {
        log.debug("Request to save Order : {}", order);
        return orderRepository.save(order);
    }

    @Override
    public Order saveOrderUser(final OrderDTO orderDTO) {
        Product product = productRepository.findById(orderDTO.getIdProduct()).orElseThrow(() -> new RuntimeException("product not found"));
        Order order = new Order();
        order.setProduct(product);
        //check the current user does the order
        User user = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);
        order.setQuantityOrder(orderDTO.getQuantity());

        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        log.debug("Request to update Order : {}", order);
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> partialUpdate(Order order) {
        log.debug("Request to partially update Order : {}", order);

        return orderRepository
            .findById(order.getId())
            .map(existingOrder -> {
                if (order.getQuantityOrder() != null) {
                    existingOrder.setQuantityOrder(order.getQuantityOrder());
                }
                if (order.getDate() != null) {
                    existingOrder.setDate(order.getDate());
                }

                return existingOrder;
            })
            .map(orderRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll(boolean isAdmin) {
        log.debug("Request to get all Orders");
        //return orderRepository.findAll();
        return isAdmin ? orderRepository.findAll() : orderRepository.findByUserIsCurrentUser();
    }

    public Page<Order> findAllWithEagerRelationships(Pageable pageable) {
        return orderRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }
}
