package org.example.app.validation.body;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BodyForValidate {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
