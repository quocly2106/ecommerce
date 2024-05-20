package com.ecommerce.library.model;

import java.util.Collection;
import java.util.List;

public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String usernName;
    private String country;
    private String phoneNumber;
    private String address;
    private String image;
    private Collection<Role> roles;
}
