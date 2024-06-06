package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }

    @PostMapping ("/do-register")
    public String processRegister(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                                  BindingResult result,
                                  Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
            String username = customerDto.getUsername();
            Customer customer = customerService.findByUsername(username);
            if (customer != null) {
                model.addAttribute("username", "Username have been registered");
                model.addAttribute("customerDto",customerDto);
            }
            if (customerDto.getPassword().equals(customerDto.getRepeatPassword()) && customer == null ) {
                CustomerDto customerDtoSave = customerService.save(customerDto);
                model.addAttribute("success", "Register successfully");
            }else if (!customerDto.getPassword().equals(customerDto.getRepeatPassword())){
                model.addAttribute("password", "Password is not same");
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
        }catch (Exception e){
            model.addAttribute("error","Server have ran some problems");
            model.addAttribute("customerDto", customerDto);

        }
        return "register";
    }
}
