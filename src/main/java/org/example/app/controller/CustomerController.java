package org.example.app.controller;

import org.example.app.domain.customer.Customer;
import org.example.app.response.ResponseTemplate;
import org.example.app.service.CustomerService;
import org.example.app.validation.body.BodyForValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("customers/create")
    public ResponseEntity<ResponseTemplate<Customer>> createCustomer(@RequestBody BodyForValidate input) {
        return customerService.create(input);
    }

    @GetMapping("customers/all")
    public ResponseEntity<ResponseTemplate<List<Customer>>>  getAllCustomers() {
        return customerService.getAll();
    }

    @GetMapping("customers/specific")
    public ResponseEntity<ResponseTemplate<Customer>> fetchUserById(@RequestParam("id") String id) {
        return customerService.fetchById(id);
    }

    @PutMapping("customers/specific")
    public ResponseEntity<ResponseTemplate<Customer>> updateCustomer(@RequestParam("id") String id, @RequestBody BodyForValidate input) {
            return customerService.update(id, input);
    }

    @DeleteMapping("customers/specific")
    public ResponseEntity<ResponseTemplate<Customer>> delete(@RequestParam("id") String id) {
        return customerService.delete(id);
    }
}
