package com.sid.gl.service.impl;

import com.sid.gl.domain.Authority;
import com.sid.gl.domain.Order;
import com.sid.gl.domain.Product;
import com.sid.gl.domain.User;
import com.sid.gl.repository.OrderRepository;
import com.sid.gl.repository.ProductRepository;
import com.sid.gl.repository.UserRepository;
import com.sid.gl.security.SecurityUtils;
import com.sid.gl.service.MailService;
import com.sid.gl.service.OrderService;
import com.sid.gl.service.ProductService;
import com.sid.gl.service.dto.OrderDTO;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
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
    private final ProductService productService;
    private final MailService mailService;
    private final MessageSource messageSource;

    public OrderServiceImpl(
        OrderRepository orderRepository,
        ProductRepository productRepository,
        UserRepository userRepository,
        ProductService productService,
        MailService mailService,
        MessageSource messageSource
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productService = productService;
        this.mailService = mailService;
        this.messageSource = messageSource;
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
        log.info("for the date ......");
        order.setDate(orderDTO.getDate());
        //update the quantity product
        Integer qtRest = product.getQuantity() - orderDTO.getQuantity();
        product.setQuantity(qtRest);
        productService.update(product);
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        String subject = "Command.action.initialized";
        //String args[]=null;
        //args[0]=product.getProductName();
        String content = messageSource.getMessage("command.description.detail", null, locale);
        mailService.sendEmail(user.getEmail(), subject, content, false, false);
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
    public List<Order> findAll() {
        log.debug("Request to get all Orders");
        User user = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .orElseThrow(() -> new RuntimeException("User not found"));

        //Authority[] authorities = user.getAuthorities().toArray(new Authority[user.getAuthorities().size()]);
        //String userAuthority = authorities[0].getName();
        if (user.isAdmin()) {
            return orderRepository.findAll();
        } else {
            return orderRepository.findByUserIsCurrentUser();
        }
        // return isAdmin ? orderRepository.findAll() : orderRepository.findByUserIsCurrentUser();
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
