package org.example.app.utils.validate.validate_entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ValidateAnswer {
    @Setter
    private boolean isValid;
    @Getter
    private final List<String> errorsList = new ArrayList<>();

    public ValidateAnswer() {
            this.isValid = false;
        }

    public boolean isValid() {
        return this.isValid;
    }

    public void addError(String error) {
        this.errorsList.add(error);
    }

}
