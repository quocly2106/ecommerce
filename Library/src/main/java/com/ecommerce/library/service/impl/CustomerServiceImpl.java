package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.ShoppingCart;
import com.ecommerce.library.repository.CustomerRepository;
import com.ecommerce.library.repository.RoleRepository;
import com.ecommerce.library.repository.ShoppingCartRepository;
import com.ecommerce.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.lang.reflect.Array;
import java.util.Arrays;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Override
    public CustomerDto save(CustomerDto customerDto) {

        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customer.setRoles(Arrays.asList(repository.findByName("CUSTOMER")));
        ShoppingCart cart = new ShoppingCart();
        cart.setCustomer(customer);
        customer.setShoppingCart(cart);

        ShoppingCart savedCart = shoppingCartRepository.save(cart); // Save ShoppingCart first
        customer.setShoppingCart(savedCart);
        Customer customerSave = customerRepository.save(customer);
        return mapperDTO(customerSave);
    }

    @Override
    public Customer findByUsername(String username) {
        Customer customer = customerRepository.findByUsername(username);
        // Ensure ShoppingCart is initialized if it's null
        if (customer != null && customer.getShoppingCart() == null) {
            ShoppingCart cart = new ShoppingCart();
            cart.setCustomer(customer);
            customer.setShoppingCart(cart);
            shoppingCartRepository.save(cart); // Save the ShoppingCart
            customerRepository.save(customer); // Save the Customer with updated ShoppingCart
        }
        return customer;
    }

    @Override
    public Customer saveInfor(Customer customer) {
        Customer customer1 = customerRepository.findByUsername(customer.getUsername());
        customer1.setAddress(customer.getAddress());
        customer1.setCity(customer.getCity());
        customer1.setCountry(customer.getCountry());
        customer1.setPhoneNumber(customer.getPhoneNumber());
        if (customer1.getShoppingCart() == null) {
            ShoppingCart cart = new ShoppingCart();
            cart.setCustomer(customer1);
            customer1.setShoppingCart(cart);
            shoppingCartRepository.save(cart); // Save the ShoppingCart
        }
        return customerRepository.save(customer1);
    }

    private CustomerDto mapperDTO(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setUsername(customer.getUsername());
        customerDto.setPassword(customer.getPassword());
        return customerDto;
    }
}
