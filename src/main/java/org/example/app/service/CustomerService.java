package org.example.app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.app.domain.customer.Customer;
import org.example.app.exceptions.CRUDException;
import org.example.app.repository.impl.CustomerRepository;
import org.example.app.response.ResponseTemplate;
import org.example.app.utils.HandlerExceptions;
import org.example.app.utils.validate.Validator;
import org.example.app.utils.validate.enums.EValidateQuery;
import org.example.app.utils.validate.validate_entity.ValidateAnswer;
import org.example.app.validation.body.BodyForValidate;
import org.example.app.validation.body.ValidationBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;

@Service("customerService")
public class CustomerService {

    @Autowired
    private CustomerRepository repository;
    private static final Logger SERVICE_LOGGER =
            LogManager.getLogger(CustomerRepository.class);
    private static final Logger CONSOLE_LOGGER =
            LogManager.getLogger("console_logger");

    private static final String CUSTOMER_NOT_FOUND = "Customer not found";
    private static final String CUSTOMER_SUCCESS = "Customer success";


    public ResponseEntity<ResponseTemplate<Customer>> create(BodyForValidate input) {
        HandlerExceptions<Customer> handlerExceptions = new HandlerExceptions<>();
        try {
            ValidationBody validationForm = new ValidationBody(repository, input);
            List<String> validationErrors = validationForm.getValidationFormErrors();
            if (!validationErrors.isEmpty()) {
                return handlerExceptions.handleValidationErrors(validationErrors);
            }

            Customer customer = new Customer(UUID.randomUUID(), input.getFirstName(), input.getLastName(), input.getEmail(), input.getPhoneNumber());
            if (!repository.create(customer)) {
                throw new CRUDException("Database operation failed");
            }
            repository.getLastEntity().ifPresent(customer1 -> customer.setId(customer1.getId()));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseTemplate<>(true, "Create customer success", customer, Collections.emptyList()));

        } catch (DataAccessException | CRUDException e) {
            return handlerExceptions.handleException("Create customer", e);
        }
    }

        public ResponseEntity<ResponseTemplate<List<Customer>>> getAll() {
            HandlerExceptions<List<Customer>> handlerExceptions = new HandlerExceptions<>();
        try{
            Optional<List<Customer>> optional = repository.getAll();
            List<Customer> customers = optional.orElseGet(ArrayList::new);
            ResponseTemplate<List<Customer>> successResponse = new ResponseTemplate<>(true,"Get customers success", customers, Collections.emptyList());
            return ResponseEntity.ok(successResponse);
        } catch (DataAccessException e) {
            SERVICE_LOGGER.error("Failed to get customers: {}", e.getMessage());
            CONSOLE_LOGGER.error("Failed to get customers: {}", e.getMessage());
            return handlerExceptions.handleException("Get customers", e);
        }
    }



    public ResponseEntity<ResponseTemplate<Customer>> fetchById(String id) {
        HandlerExceptions<Customer> handlerExceptions = new HandlerExceptions<>();
        Validator<EValidateQuery> validator = new Validator<>();
        ValidateAnswer validationAnswer = validator.validate(id, EValidateQuery.ID);
        if (!validationAnswer.isValid()) {
            return handlerExceptions.handleValidationErrors(validationAnswer.getErrorsList());
        }
        try {
            Long idLong = Long.parseLong(id);
            Optional<Customer> optional = repository.getById(idLong);
            return optional.map(customer ->
                    ResponseEntity.ok(new ResponseTemplate<>(true, CUSTOMER_SUCCESS, customer, Collections.emptyList())))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseTemplate<>(false, CUSTOMER_NOT_FOUND, null, List.of(CUSTOMER_NOT_FOUND))));
        } catch (DataAccessException e) {
            return handlerExceptions.handleException("Get customer", e);
        }
    }

    public ResponseEntity<ResponseTemplate<Customer>> update(String id, BodyForValidate input) {
        HandlerExceptions<Customer> handlerExceptions = new HandlerExceptions<>();
        Validator<EValidateQuery> validator = new Validator<>();
        ValidateAnswer validationAnswer = validator.validate(id, EValidateQuery.ID);
        if (!validationAnswer.isValid()) {
            return handlerExceptions.handleValidationErrors(validationAnswer.getErrorsList());
        }
        try {
            Long idLong = Long.parseLong(id);
            Optional<Customer> optional = repository.getById(idLong);
            if (optional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseTemplate<>(false, CUSTOMER_NOT_FOUND, null, List.of(CUSTOMER_NOT_FOUND)));
            }
            Customer customer = optional.get();
            ValidationBody validationForm = new ValidationBody(repository, input, idLong);
            List<String> validationErrorsList = validationForm.getValidationFormErrors();
            if (!validationErrorsList.isEmpty()) {
                return handlerExceptions.handleValidationErrors(validationErrorsList);
            }

            customer.setFirstName(input.getFirstName() == null ? customer.getFirstName() : input.getFirstName());
            customer.setLastName(input.getLastName() == null ? customer.getLastName() : input.getLastName());
            customer.setEmail(input.getEmail() == null ? customer.getEmail() : input.getEmail());
            customer.setPhoneNumber(input.getPhoneNumber() == null ? customer.getPhoneNumber() : input.getPhoneNumber());
            if (!repository.update(idLong, customer)) {
                throw new CRUDException("Database update operation failed");
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseTemplate<>(true, "Update customer success", customer, Collections.emptyList()));

        } catch (DataAccessException | CRUDException e) {
            return handlerExceptions.handleException("update customer", e);
        }
    }

    public ResponseEntity<ResponseTemplate<Customer>> delete(String id) {
        HandlerExceptions<Customer> handlerExceptions = new HandlerExceptions<>();
        Validator<EValidateQuery> validator = new Validator<>();
        ValidateAnswer validationAnswer = validator.validate(id, EValidateQuery.ID);
        if (!validationAnswer.isValid()) {
            return handlerExceptions.handleValidationErrors(validationAnswer.getErrorsList());
        }
        try {
            Long idLong = Long.parseLong(id);
            Optional<Customer> optional = repository.getById(idLong);
            if (optional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseTemplate<>(false, CUSTOMER_NOT_FOUND, null, List.of(CUSTOMER_NOT_FOUND)));
            }
            if (!repository.delete(idLong)) {
                throw new CRUDException("Database delete operation failed");
            }
            return ResponseEntity.ok(new ResponseTemplate<>(true, "Delete customer success", null, Collections.emptyList()));

        } catch (DataAccessException | CRUDException e) {
            return handlerExceptions.handleException("delete customer", e);
        }
    }
}
