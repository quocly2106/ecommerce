package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.CartItem;
import com.ecommerce.library.model.Order;
import com.ecommerce.library.model.OrderDetail;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.repository.CartItemRepository;
import com.ecommerce.library.repository.OrderDetailRepository;
import com.ecommerce.library.repository.OrderRepository;
import com.ecommerce.library.repository.ShoppingCartRepository;
import com.ecommerce.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public void saveOrder(ShoppingCart cart) {
        if (cart == null || cart.getCustomer() == null) {
            throw new IllegalArgumentException("Shopping cart or customer cannot be null");
        }

        Order order = new Order();
        order.setOrderStatus("PENDING");
        order.setOrderDate(new Date());
        order.setCustomer(cart.getCustomer());
        order.setTotalPrice(cart.getTotalPrices());

        // Save the order first
        orderRepository.save(order);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (CartItem item : cart.getCartItem()) {
            OrderDetail existingOrderDetail = orderDetailRepository.findByOrderAndProduct(order, item.getProduct());
            if (existingOrderDetail == null) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setQuantity(item.getQuantity());
                orderDetail.setProduct(item.getProduct());
                orderDetail.setUnitPrice(item.getProduct().getCostPrice());
                orderDetail.setTotalPrice(item.getQuantity() * item.getProduct().getCostPrice());
                orderDetailList.add(orderDetail);
                orderDetailRepository.save(orderDetail);
            } else {
                existingOrderDetail.setQuantity(existingOrderDetail.getQuantity() + item.getQuantity());
                existingOrderDetail.setTotalPrice(existingOrderDetail.getTotalPrice() + (item.getQuantity() * item.getProduct().getCostPrice()));
                orderDetailRepository.save(existingOrderDetail);
            }
            cartItemRepository.delete(item);
        }

        order.setOrderDetailList(orderDetailList);
        cart.setCartItem(new HashSet<>());
        cart.setTotalItems(0);
        cart.setTotalPrices(0);
        cartRepository.save(cart);
    }

    @Override
    public void acceptOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setDeliveryDate(new Date());
        order.setOrderStatus("SHIPPING");
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
