package org.example.app.validation.body;

import lombok.Getter;
import org.example.app.repository.impl.CustomerRepository;
import org.example.app.utils.validate.Validator;
import org.example.app.utils.validate.enums.EValidateCustomer;
import org.example.app.utils.validate.enums.IValidateUnit;
import org.example.app.utils.validate.validate_entity.ValidateAnswer;

import java.util.ArrayList;
import java.util.List;


@Getter
public class ValidationBody {
    private final List<String> validationFormErrors = new ArrayList<>();
    private boolean isValidForm = true;
    private final BodyForValidate data;
    private final CustomerRepository repository;
    private Long id;


    public ValidationBody(CustomerRepository repository, BodyForValidate data) {
        this.repository = repository;
        this.data = data;
        validateFirstName(data.getFirstName());
        validateLastName(data.getLastName());
        validateEmail(data.getEmail());
        validatePhone(data.getPhoneNumber());
    }

    public ValidationBody(CustomerRepository repository, BodyForValidate data, Long id) {
        this.repository = repository;
        this.id = id;
        this.data = data;
        validateFirstName(data.getFirstName());
        validateLastName(data.getLastName());
        validateEmail(data.getEmail());
        validatePhone(data.getPhoneNumber());
    }


    private ValidateAnswer validateField(String value, IValidateUnit validationType) {
        Validator<IValidateUnit> validator = new Validator<>();
        if (id != null) {
            if (value == null) {
                ValidateAnswer answer = new ValidateAnswer();
                answer.setValid(true);
                return answer;
            }
            return validator.validate(value, validationType);
        } 
        if (value==null) {
            ValidateAnswer answer = new ValidateAnswer();
            answer.addError(" Is empty (required)");
            return answer;
        }
        return validator.validate(value, validationType);
    }

    private void validateFirstName(String firstName) {
            ValidateAnswer answer = validateField(firstName, EValidateCustomer.FIRST_NAME);
            if (!answer.isValid()) {
                isValidForm = false;
                validationFormErrors.add(String.format("firstName: %s", String.join(", ", answer.getErrorsList())));
            }

    }

    private void validateLastName(String  lastName) {
        ValidateAnswer answer = validateField(lastName, EValidateCustomer.LAST_NAME);
        if (!answer.isValid()) {
            isValidForm = false;
            validationFormErrors.add(String.format("lastName: %s", String.join(", ", answer.getErrorsList())));
        }
    }

    private void validateEmail(String  email) {
        ValidateAnswer answer = validateField(email, EValidateCustomer.EMAIL);
        if (!answer.isValid()) {
            isValidForm = false;
            validationFormErrors.add(String.format("email: %s", String.join(", ", answer.getErrorsList())));
        }
        if (id == null && isEmailExists(email)) {
            isValidForm = false;
            answer.addError("Email already exists");
            validationFormErrors.add(String.format("email: %s", String.join(", ", answer.getErrorsList())));
        }
        if (id != null && isEmailExists(email) && !isCustomerEmail(email, id)) {
            isValidForm = false;
            answer.addError("Email already exists");
            validationFormErrors.add(String.format("email: %s", String.join(", ", answer.getErrorsList())));
        }
    }

    private void validatePhone(String  phone) {
        ValidateAnswer answer = validateField(phone, EValidateCustomer.PHONE);
        if (!answer.isValid()) {
            isValidForm = false;
            validationFormErrors.add(String.format("phoneNumber: %s", String.join(", ", answer.getErrorsList())));
        }
    }

    private boolean isEmailExists(String email) {
        return repository.isEmailExists(email);
    }

    private boolean isCustomerEmail(String email, Long id) {
        return repository.isCustomerEmail(email, id);
    }
}
